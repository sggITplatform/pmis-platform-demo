/**
 * ESL (Enterprise Standard Loader)
 * Copyright 2013 Baidu Inc. All rights reserved.
 * 
 * @file Browser�˱�׼������������AMD�淶
 * @author errorrik(errorrik@gmail.com)
 *         Firede(firede@firede.us)
 */

var define;
var require;

(function ( global ) {
    // "mod"��ͷ�ı�������Ϊ�ڲ�ģ�������
    // Ϊ���ѹ���ʣ���ʹ��function��object��װ
    
    /**
     * ģ������
     * 
     * @inner
     * @type {Object}
     */
    var modModules = {};

    var MODULE_STATE_PRE_DEFINED = 1;
    var MODULE_STATE_PRE_ANALYZED = 2;
    var MODULE_STATE_ANALYZED = 3;
    var MODULE_STATE_READY = 4;
    var MODULE_STATE_DEFINED = 5;

    /**
     * ȫ��require����
     * 
     * @inner
     * @type {Function}
     */
    var actualGlobalRequire = createLocalRequire( '' );

    /**
     * ��ʱ���Ѷ�ʱ��
     * 
     * @inner
     * @type {number}
     */
    var waitTimeout;

    /**
     * ����ģ��
     * 
     * @param {string|Array} requireId ģ��id��ģ��id���飬
     * @param {Function=} callback ������ɵĻص�����
     * @return {*}
     */
    function require( requireId, callback ) {
        assertNotContainRelativeId( requireId );
        
        // ��ʱ����
        var timeout = requireConf.waitSeconds;
        if ( isArray( requireId ) && timeout ) {
            if ( waitTimeout ) {
                clearTimeout( waitTimeout );
            }
            waitTimeout = setTimeout( waitTimeoutNotice, timeout * 1000 );
        }

        return actualGlobalRequire( requireId, callback );
    }

    /**
     * ��ģ���ʶת������Ե�url
     * 
     * @param {string} id ģ���ʶ
     * @return {string}
     */
    require.toUrl = toUrl;

    /**
     * ��ʱ���Ѻ���
     * 
     * @inner
     */
    function waitTimeoutNotice() {
        var hangModules = [];
        var missModules = [];
        var missModulesMap = {};
        var hasError;

        for ( var id in modModules ) {
            if ( !modIsDefined( id ) ) {
                hangModules.push( id );
                hasError = 1;
            }

            each(
                modModules[ id ].realDeps || [],
                function ( depId ) {
                    if ( !modModules[ depId ] && !missModulesMap[ depId ] ) {
                        hasError = 1;
                        missModules.push( depId );
                        missModulesMap[ depId ] = 1;
                    }
                }
            );
        }

        if ( hasError ) {
            throw new Error( '[MODULE_TIMEOUT]Hang( ' 
                + ( hangModules.join( ', ' ) || 'none' )
                + ' ) Miss( '
                + ( missModules.join( ', ' ) || 'none' )
                + ' )'
            );
        }
    }

    /**
     * �������ģ�鶨��Ķ�ʱ��
     * 
     * @inner
     * @type {number}
     */
    var tryDefineTimeout;

    /**
     * ����ģ��
     * 
     * @param {string=} id ģ���ʶ
     * @param {Array=} dependencies ����ģ���б�
     * @param {Function=} factory ����ģ��Ĺ�������
     */
    function define() {
        var argsLen = arguments.length;
        if ( !argsLen ) {
            return;
        }

        var id;
        var dependencies;
        var factory = arguments[ --argsLen ];

        while ( argsLen-- ) {
            var arg = arguments[ argsLen ];

            if ( isString( arg ) ) {
                id = arg;
            }
            else if ( isArray( arg ) ) {
                dependencies = arg;
            }
        }
        
        // ����window�������
        // esl�������Ϊbrowser�˵�loader
        // �հ���global�����������ڣ�
        //     define��require�������Ա��ҵ��û��Զ��������
        var opera = window.opera;

        // IE��ͨ��current script��data-require-id��ȡ��ǰid
        if ( 
            !id 
            && document.attachEvent 
            && (!(opera && opera.toString() === '[object Opera]')) 
        ) {
            var currentScript = getCurrentScript();
            id = currentScript && currentScript.getAttribute('data-require-id');
        }

        // ������������
        // Ĭ��Ϊ['require', 'exports', 'module']
        dependencies = dependencies || ['require', 'exports', 'module'];
        if ( id ) {
            modPreDefine( id, dependencies, factory );

            // �ڲ�Զ��δ���������define
            // define��������ҳ����ĳ���ط����ã���һ�����ڶ������ļ���requireװ��
            if ( tryDefineTimeout ) {
                clearTimeout( tryDefineTimeout );
            }
            tryDefineTimeout = setTimeout( modPreAnalyse, 10 );
        }
        else {
            // ��¼����������У���load��readystatechange�д���
            wait4PreDefines.push( {
                deps    : dependencies,
                factory : factory
            } );
        }
    }

    define.amd = {};

    /**
     * ��ȡ��Ӧ״̬��ģ���б�
     * 
     * @inner
     * @param {number} state ״̬��
     * @return {Array}
     */
    function modGetByState( state ) {
        var modules = [];
        for ( var key in modModules ) {
            var module = modModules[ key ];
            if ( module.state == state ) {
                modules.push( module );
            }
        }

        return modules;
    }

    /**
     * ģ�����û�ȡ����
     * 
     * @inner
     * @return {Object} ģ�����ö���
     */
    function moduleConfigGetter() {
        var conf = requireConf.config[ this.id ];
        if ( conf && typeof conf === 'object' ) {
            return conf;
        }

        return {};
    }

    /**
     * Ԥ����ģ��
     * 
     * @inner
     * @param {string} id ģ���ʶ
     * @param {Array.<string>} dependencies ��ʽ����������ģ���б�
     * @param {*} factory ģ�鶨�庯����ģ�����
     */
    function modPreDefine( id, dependencies, factory ) {
        if ( modExists( id ) ) {
            return;
        }

        var module = {
            id       : id,
            deps     : dependencies,
            factory  : factory,
            exports  : {},
            config   : moduleConfigGetter,
            state    : MODULE_STATE_PRE_DEFINED,
            hardDeps : {}
        };

        // ��ģ��Ԥ����defining������
        modModules[ id ] = module;
    }

    /**
     * Ԥ����ģ��
     * 
     * ���ȣ���ɶ�factory�����������ķ�����ȡ
     * Ȼ�󣬳��Լ���"��Դ��������ģ��"
     * 
     * ��Ҫ�ȼ���ģ���ԭ���ǣ����ģ�鲻���ڣ��޷�����resourceId normalize��
     * modAnalyse��ɺ���������������������������ģ��ļ���
     * 
     * @inner
     * @param {Object} modules ģ�����
     */
    function modPreAnalyse() {
        var pluginModuleIds = [];
        var pluginModuleIdsMap = {};
        var modules = modGetByState( MODULE_STATE_PRE_DEFINED );

        each(
            modules,
            function ( module ) {
                // ����ʵ����Ҫ���ص�����
                var realDepends = module.deps.slice( 0 );
                module.realDeps = realDepends;

                // ����function body�е�require
                // ���������ʽ����������Ϊ���ܿ��ǣ����Բ�����factoryBody
                // AMD�淶��˵����`SHOULD NOT`���������ﻹ�Ƿ�����
                var factory = module.factory;
                var requireRule = /require\(\s*(['"'])([^'"]+)\1\s*\)/g;
                var commentRule = /(\/\*([\s\S]*?)\*\/|([^:]|^)\/\/(.*)$)/mg;
                if ( isFunction( factory ) ) {
                    factory.toString()
                        .replace( commentRule, '' )
                        .replace( requireRule, function ( $0, $1, $2 ) {
                            realDepends.push( $2 );
                        });
                }

                // ����resource���ص�plugin module id
                each(
                    realDepends,
                    function ( dependId ) {
                        var idInfo = parseId( dependId );
                        if ( idInfo.resource ) {
                            var plugId = normalize( idInfo.module, module.id );
                            if ( !pluginModuleIdsMap[ plugId ] ) {
                                pluginModuleIds.push( plugId );
                                pluginModuleIdsMap[ plugId ] = 1;
                            }
                        }
                    }
                );

                module.state = MODULE_STATE_PRE_ANALYZED;
            }
        );

        nativeRequire( pluginModuleIds, function () {
            modAnalyse( modules );
        } );
    }

    /**
     * ����ģ��
     * ����������id����normalize������ɷ����������Լ�����������ģ��
     * 
     * @inner
     * @param {Array} modules ģ������б�
     */
    function modAnalyse( modules ) {
        var requireModules = [];

        each(
            modules,
            function ( module ) {
                if ( module.state !== MODULE_STATE_PRE_ANALYZED ) {
                    return;
                }

                var id = module.id;

                // �Բ�������������������normalize
                var depends = module.deps;
                var hardDepends = module.hardDeps;
                var hardDependsCount = isFunction( module.factory )
                    ? module.factory.length
                    : 0;

                each(
                    depends,
                    function ( dependId, index ) {
                        dependId = normalize( dependId, id );
                        depends[ index ] = dependId;

                        if ( index < hardDependsCount ) {
                            hardDepends[ dependId ] = 1;
                        }
                    }
                );

                // ����ģ��id normalize������ȥ����Ҫ��������ȥ��������ģ���У�
                // 1. �ڲ�ģ�飺require/exports/module
                // 2. �ظ�ģ�飺dependencies�������ڲ�require�����ظ�
                // 3. ��ģ�飺dependencies��ʹ���߿���д��
                var realDepends = module.realDeps;
                var len = realDepends.length;
                var existsDepend = {};
                
                while ( len-- ) {
                    // �˴����ϲ���ѭ�������ظ�normalize����Ϊdeps��realDeps���ظ���
                    // Ϊ�����߼��ֽ��������Ͳ����Ż�����
                    var dependId = normalize( realDepends[ len ], id );
                    if ( !dependId
                         || dependId in existsDepend
                         || dependId in BUILDIN_MODULE
                    ) {
                        realDepends.splice( len, 1 );
                    }
                    else {
                        existsDepend[ dependId ] = 1;
                        realDepends[ len ] = dependId;

                        // ��ʵ������ѹ����������У�����ͳһ����require
                        requireModules.push( dependId );
                    }
                }

                module.realDepsIndex = existsDepend;
                module.state = MODULE_STATE_ANALYZED;

                modWaitDependenciesLoaded( module );
                modInvokeFactoryDependOn( id );
            }
        );

        nativeRequire( requireModules );
    }

    /**
     * �ȴ�ģ�������������
     * ������ɺ��Ե���factory���ģ�鶨��
     * 
     * @inner
     * @param {Object} module ģ�����
     */
    function modWaitDependenciesLoaded( module ) {
        var id = module.id;

        module.invokeFactory = invokeFactory;
        invokeFactory();

        // ���ڱ���������������ѭ������
        var checkingLevel = 0;

        /**
         * �ж������������
         * 
         * @inner
         * @return {boolean}
         */
        function checkInvokeReadyState() {
            checkingLevel++;

            var isReady = 1;
            var tryDeps = [];

            each(
                module.realDeps,
                function ( depId ) {
                    if ( !modIsAnalyzed( depId ) ) {
                        isReady = 0;
                    }
                    else if ( !modIsDefined( depId ) ) {
                        switch ( modHasCircularDependency( id, depId ) ) {
                            case CIRCULAR_DEP_UNREADY:
                            case CIRCULAR_DEP_NO:
                                isReady = 0;
                                break;
                            case CIRCULAR_DEP_YES:
                                if ( module.hardDeps[ depId ] ) {
                                    tryDeps.push( depId );
                                }
                                break;
                        }
                    }
                    
                    return !!isReady;
                }
            );

            
            // ֻ�е�������ѭ��������װ���ˣ���ȥ���Դ���Ӳ����ģ��ĳ�ʼ��
            isReady && each(
                tryDeps,
                function ( depId ) {
                    modTryInvokeFactory( depId );
                }
            );

            isReady = isReady && tryDeps.length === 0;
            isReady && (module.state = MODULE_STATE_READY);

            checkingLevel--;
            return isReady;
        }

        /**
         * ��ʼ��ģ��
         * 
         * @inner
         */
        function invokeFactory() {
            if ( module.state == MODULE_STATE_DEFINED 
                || checkingLevel > 1
                || !checkInvokeReadyState()
            ) {
                return;
            }

            // ����factory������ʼ��module
            try {
                var factory = module.factory;
                var exports = isFunction( factory )
                    ? factory.apply( 
                        global, 
                        modGetModulesExports( 
                            module.deps, 
                            {
                                require : createLocalRequire( id ),
                                exports : module.exports,
                                module  : module
                            } 
                        ) 
                    )
                    : factory;

                if ( typeof exports != 'undefined' ) {
                    module.exports = exports;
                }

                module.state = MODULE_STATE_DEFINED;
                module.invokeFactory = null;
            } 
            catch ( ex ) {
                if ( /^\[MODULE_MISS\]"([^"]+)/.test( ex.message ) ) {
                    // ����˵����factory�������У���require��ģ������Ҫ��
                    // ���԰�������Ӳ������
                    module.hardDeps[ RegExp.$1 ] = 1;
                    return;
                }

                throw ex;
            }
            
            
            modInvokeFactoryDependOn( id );
            modFireDefined( id );
        }
    }

    /**
     * ����ģ��id���飬��ȡ���exports����
     * ����ģ���ʼ����factory������require��callback��������
     * 
     * @inner
     * @param {Array} modules ģ��id����
     * @param {Object} buildinModules �ڽ�ģ�����
     * @return {Array}
     */
    function modGetModulesExports( modules, buildinModules ) {
        var args = [];
        each( 
            modules,
            function ( moduleId, index ) {
                args[ index ] = 
                    buildinModules[ moduleId ]
                    || modGetModuleExports( moduleId );
            } 
        );

        return args;
    }

    var CIRCULAR_DEP_UNREADY = 0;
    var CIRCULAR_DEP_NO = 1;
    var CIRCULAR_DEP_YES = 2;

    /**
     * �ж�source�Ƿ���target����������
     *
     * @inner
     * @return {number}
     */
    function modHasCircularDependency( source, target, meet ) {
        if ( !modIsAnalyzed( target ) ) {
            return CIRCULAR_DEP_UNREADY;
        }

        meet = meet || {};
        meet[ target ] = 1;
        
        if ( target == source ) {
            return CIRCULAR_DEP_YES;
        }

        var module = modGetModule( target );
        var depends = module && module.realDeps;
        
        
        if ( depends ) {
            var len = depends.length;

            while ( len-- ) {
                var dependId = depends[ len ];
                if ( meet[ dependId ] ) { 
                    continue;
                }

                var state = modHasCircularDependency( source, dependId, meet );
                switch ( state ) {
                    case CIRCULAR_DEP_UNREADY:
                    case CIRCULAR_DEP_YES:
                        return state;
                }
            }
        }

        return CIRCULAR_DEP_NO;
    }

    /**
     * �������Լ���ģ�鳢�Գ�ʼ��
     * 
     * @inner
     * @param {string} id ģ��id
     */
    function modInvokeFactoryDependOn( id ) {
        for ( var key in modModules ) {
            var realDeps = modModules[ key ].realDepsIndex || {};
            realDeps[ id ] && modTryInvokeFactory( key );
        }
    }

    /**
     * ����ִ��ģ��factory����������ģ���ʼ��
     * 
     * @inner
     * @param {string} id ģ��id
     */
    function modTryInvokeFactory( id ) {
        var module = modModules[ id ];

        if ( module && module.invokeFactory ) {
            module.invokeFactory();
        }
    }

    /**
     * ģ�鶨����ɵ��¼�������
     * 
     * @inner
     * @type {Array}
     */
    var modDefinedListener = [];

    /**
     * ģ�鶨������¼����������Ƴ�����
     * 
     * @inner
     * @type {Array}
     */
    var modRemoveListenerIndex = [];

    /**
     * ģ�鶨������¼�fire�㼶
     * 
     * @inner
     * @type {number}
     */
    var modFireLevel = 0;

    /**
     * �ɷ�ģ�鶨������¼�
     * 
     * @inner
     * @param {string} id ģ���ʶ
     */
    function modFireDefined( id ) {
        modFireLevel++;
        each( 
            modDefinedListener,
            function ( listener ) {
                listener && listener( id );
            }
        );
        modFireLevel--;

        modSweepDefinedListener();
    }

    /**
     * ����ģ�鶨������¼�������
     * modRemoveDefinedListenerʱֻ�����
     * ��modFireDefinedִ���������
     * 
     * @inner
     * @param {Function} listener ģ�鶨�������
     */
    function modSweepDefinedListener() {
        if ( modFireLevel < 1 ) {
            modRemoveListenerIndex.sort( 
                function ( a, b ) { return b - a; } 
            );

            each( 
                modRemoveListenerIndex,
                function ( index ) {
                    modDefinedListener.splice( index, 1 );
                }
            );
            
            modRemoveListenerIndex = [];
        }
    }

    /**
     * �Ƴ�ģ�鶨�������
     * 
     * @inner
     * @param {Function} listener ģ�鶨�������
     */
    function modRemoveDefinedListener( listener ) {
        each(
            modDefinedListener,
            function ( item, index ) {
                if ( listener == item ) {
                    modRemoveListenerIndex.push( index );
                }
            }
        );
    }

    /**
     * ���ģ�鶨�������
     * 
     * @inner
     * @param {Function} listener ģ�鶨�������
     */
    function modAddDefinedListener( listener ) {
        modDefinedListener.push( listener );
    }

    /**
     * �ж�ģ���Ƿ����
     * 
     * @inner
     * @param {string} id ģ���ʶ
     * @return {boolean}
     */
    function modExists( id ) {
        return id in modModules;
    }

    /**
     * �ж�ģ���Ƿ��Ѷ������
     * 
     * @inner
     * @param {string} id ģ���ʶ
     * @return {boolean}
     */
    function modIsDefined( id ) {
        return modExists( id ) 
            && modModules[ id ].state == MODULE_STATE_DEFINED;
    }

    /**
     * �ж�ģ���Ƿ��ѷ������
     * 
     * @inner
     * @param {string} id ģ���ʶ
     * @return {boolean}
     */
    function modIsAnalyzed( id ) {
        return modExists( id ) 
            && modModules[ id ].state >= MODULE_STATE_ANALYZED;
    }

    /**
     * ��ȡģ���exports
     * 
     * @inner
     * @param {string} id ģ���ʶ
     * @return {*}
     */
    function modGetModuleExports( id ) {
        if ( modIsDefined( id ) ) {
            return modModules[ id ].exports;
        }

        return null;
    }

    /**
     * ��ȡģ��
     * 
     * @inner
     * @param {string} id ģ���ʶ
     * @return {Object}
     */
    function modGetModule( id ) {
        return modModules[ id ];
    }

    /**
     * �����Դ
     * 
     * @inner
     * @param {string} resourceId ��Դ��ʶ
     * @param {*} value ��Դ����
     */
    function modAddResource( resourceId, value ) {
        modModules[ resourceId ] = {
            exports: value || true,
            state: MODULE_STATE_DEFINED
        };

        modInvokeFactoryDependOn( resourceId );
        modFireDefined( resourceId );
    }

    /**
     * �ڽ�module���Ƽ���
     * 
     * @inner
     * @type {Object}
     */
    var BUILDIN_MODULE = {
        require : require,
        exports : 1,
        module  : 1
    };

    /**
     * δԤ�����ģ�鼯��
     * ��Ҫ�洢������ʽdefine��ģ��
     * 
     * @inner
     * @type {Array}
     */
    var wait4PreDefines = [];

    /**
     * ���ģ��Ԥ����
     * 
     * @inner
     */
    function completePreDefine( currentId ) {
        var preDefines = wait4PreDefines.slice( 0 );

        wait4PreDefines.length = 0;
        wait4PreDefines = [];

        // Ԥ����ģ�飺
        // ��ʱ�����ģ�鶼������define��ģ��
        each(
            preDefines,
            function ( module ) {
                var id = module.id || currentId;
                modPreDefine( id, module.deps, module.factory );
            }
        );

        modPreAnalyse();
    }
    
    /**
     * ��ȡģ��
     * 
     * @param {string|Array} ids ģ�����ƻ�ģ�������б�
     * @param {Function=} callback ��ȡģ�����ʱ�Ļص�����
     * @return {Object}
     */
    function nativeRequire( ids, callback, baseId ) {
        callback = callback || new Function();
        baseId = baseId || '';

        // ���� https://github.com/amdjs/amdjs-api/wiki/require
        // It MUST throw an error if the module has not 
        // already been loaded and evaluated.
        if ( isString( ids ) ) {
            if ( !modIsDefined( ids ) ) {
                throw new Error( '[MODULE_MISS]"' + ids + '" is not exists!' );
            }

            return modGetModuleExports( ids );
        }

        if ( !isArray( ids ) ) {
            return;
        }
        
        if ( ids.length === 0 ) {
            callback();
            return;
        }
        
        var isCallbackCalled = 0;
        modAddDefinedListener( tryFinishRequire );
        each(
            ids,
            function ( id ) {
                if ( id in BUILDIN_MODULE ) {
                    return;
                } 

                ( id.indexOf( '!' ) > 0 
                    ? loadResource
                    : loadModule
                )( id, baseId );
            }
        );

        tryFinishRequire();
        
        /**
         * �������require������callback
         * ��ģ����������ģ�鶼������ʱ����
         * 
         * @inner
         */
        function tryFinishRequire() {
            if ( isCallbackCalled ) {
                return;
            }

            var visitedModule = {};

            /**
             * �ж��Ƿ�����ģ�鶼�Ѿ�������ɣ�������������ģ��
             * 
             * @inner
             * @param {Array} modules ֱ��ģ���ʶ�б�
             * @return {boolean}
             */
            function isAllInited( modules ) {
                var allInited = 1;
                each(
                    modules,
                    function ( id ) {
                        if ( visitedModule[ id ] ) {
                            return;
                        }
                        visitedModule[ id ] = 1;

                        if ( BUILDIN_MODULE[ id ] ) {
                            return;
                        }

                        if ( 
                            !modIsDefined( id ) 
                            || !isAllInited( modGetModule( id ).realDeps )
                        ) {
                            allInited = 0;
                            return false;
                        }
                    }
                );

                return allInited;
            }

            // ��Ⲣ����callback
            if ( isAllInited( ids ) ) {
                isCallbackCalled = 1;
                modRemoveDefinedListener( tryFinishRequire );

                callback.apply( 
                    global, 
                    modGetModulesExports( ids, BUILDIN_MODULE )
                );
            }
        }
    }

    /**
     * ���ڼ��ص�ģ���б�
     * 
     * @inner
     * @type {Object}
     */
    var loadingModules = {};

    /**
     * ����ģ��
     * 
     * @inner
     * @param {string} moduleId ģ���ʶ
     */
    function loadModule( moduleId ) {
        if ( loadingModules[ moduleId ] ) {
            return;
        }
        
        if ( modExists( moduleId ) ) {
            modAnalyse( [ modGetModule( moduleId ) ] );
            return;
        }
        
        loadingModules[ moduleId ] = 1;

        // ����script��ǩ
        // 
        // ���ﲻ�ҽ�onerror�Ĵ�����
        // ��Ϊ�߼��������devtool��console���ᱨ��
        // ��throwһ��Error���һ����
        var script = document.createElement( 'script' );
        script.setAttribute( 'data-require-id', moduleId );
        script.src = toUrl( moduleId ) ;
        script.async = true;
        if ( script.readyState ) {
            script.onreadystatechange = loadedListener;
        }
        else {
            script.onload = loadedListener;
        }
        appendScript( script );

        /**
         * script��ǩ������ɵ��¼�������
         * 
         * @inner
         */
        function loadedListener() {
            var readyState = script.readyState;
            if ( 
                typeof readyState == 'undefined'
                || /^(loaded|complete)$/.test( readyState )
            ) {
                script.onload = script.onreadystatechange = null;
                script = null;

                completePreDefine( moduleId );
                delete loadingModules[ moduleId ];
            }
        }
    }

    /**
     * ������Դ
     * 
     * @inner
     * @param {string} pluginAndResource �������Դ��ʶ
     * @param {string} baseId ��ǰ������ģ���ʶ
     */
    function loadResource( pluginAndResource, baseId ) {
        var idInfo = parseId( pluginAndResource );
        var pluginId = idInfo.module;
        var resourceId = idInfo.resource;

        /**
         * plugin������ɵĻص�����
         * 
         * @inner
         * @param {*} value resource��ֵ
         */
        function pluginOnload( value ) {
            modAddResource( pluginAndResource, value );
        }

        /**
         * �÷�������pluginʹ�ü��ص���Դ����ģ��
         * 
         * @param {string} name ģ��id
         * @param {string} body ģ�������ַ���
         */
        pluginOnload.fromText = function ( id, text ) {
            new Function( text )();
            completePreDefine( id );
        };

        /**
         * ������Դ
         * 
         * @inner
         * @param {Object} plugin ���ڼ�����Դ�Ĳ��ģ��
         */
        function load( plugin ) {
            if ( !modIsDefined( pluginAndResource ) ) {
                plugin.load( 
                    resourceId, 
                    createLocalRequire( baseId ),
                    pluginOnload,
                    moduleConfigGetter.call( { id: pluginAndResource } )
                );
            }
        }

        if ( !modIsDefined( pluginId ) ) {
            nativeRequire( [ pluginId ], load ); 
        }
        else {
            load( modGetModuleExports( pluginId ) );
        }
    }

    /**
     * require����
     * 
     * @inner
     * @type {Object}
     */
    var requireConf = { 
        baseUrl     : './',
        paths       : {},
        config      : {},
        map         : {},
        packages    : [],
        waitSeconds : 0,
        urlArgs     : {}
    };

    /**
     * ��ϵ�ǰ���������û������������
     * 
     * @inner
     * @param {string} name ����������
     * @param {Any} value �û������������ֵ
     */
    function mixConfig( name, value ) {
        var originValue = requireConf[ name ];
        var type = typeof originValue;
        if ( type == 'string' || type == 'number' ) {
            requireConf[ name ] = value;
        }
        else if ( isArray( originValue ) ) {
            each( value, function ( item ) {
                originValue.push( item );
            } );
        }
        else {
            for ( var key in value ) {
                originValue[ key ] = value[ key ];
            }
        }
    }

    /**
     * ����require
     * 
     * @param {Object} conf ���ö���
     */
    require.config = function ( conf ) {
        // �򵥵Ķദ���û�����Ҫ֧��
        // ����ʵ�ָ���Ϊ����mix
        for ( var key in requireConf ) {
            if ( conf.hasOwnProperty( key ) ) {
                var confItem = conf[ key ];
                if ( key == 'urlArgs' && isString( confItem ) ) {
                    defaultUrlArgs = confItem;
                }
                else {
                    mixConfig( key, confItem );
                }
            }
        }
        
        createConfIndex();
    };

    // ��ʼ��ʱ��Ҫ������������
    createConfIndex();

    /**
     * ����������Ϣ�ڲ�����
     * 
     * @inner
     */
    function createConfIndex() {
        requireConf.baseUrl = requireConf.baseUrl.replace( /\/$/, '' ) + '/';
        createPathsIndex();
        createMappingIdIndex();
        createPackagesIndex();
        createUrlArgsIndex();
    }

    /**
     * packages�ڲ�����
     * 
     * @inner
     * @type {Array}
     */
    var packagesIndex;

    /**
     * ����packages�ڲ�����
     * 
     * @inner
     */
    function createPackagesIndex() {
        packagesIndex = [];
        each( 
            requireConf.packages,
            function ( packageConf ) {
                var pkg = packageConf;
                if ( isString( packageConf ) ) {
                    pkg = {
                        name: packageConf.split('/')[ 0 ],
                        location: packageConf,
                        main: 'main'
                    };
                }
                
                pkg.location = pkg.location || pkg.name;
                pkg.main = (pkg.main || 'main').replace(/\.js$/i, '');
                packagesIndex.push( pkg );
            }
        );

        packagesIndex.sort( createDescSorter( 'name' ) );
    }

    /**
     * paths�ڲ�����
     * 
     * @inner
     * @type {Array}
     */
    var pathsIndex;

    /**
     * ����paths�ڲ�����
     * 
     * @inner
     */
    function createPathsIndex() {
        pathsIndex = kv2List( requireConf.paths );
        pathsIndex.sort( createDescSorter() );
    }

    /**
     * Ĭ�ϵ�urlArgs
     * 
     * @inner
     * @type {string}
     */
    var defaultUrlArgs;

    /**
     * urlArgs�ڲ�����
     * 
     * @inner
     * @type {Array}
     */
    var urlArgsIndex;

    /**
     * ����urlArgs�ڲ�����
     * 
     * @inner
     */
    function createUrlArgsIndex() {
        urlArgsIndex = kv2List( requireConf.urlArgs );
        urlArgsIndex.sort( createDescSorter() );
    }

    /**
     * mapping�ڲ�����
     * 
     * @inner
     * @type {Array}
     */
    var mappingIdIndex;
    
    /**
     * ����mapping�ڲ�����
     * 
     * @inner
     */
    function createMappingIdIndex() {
        mappingIdIndex = [];
        
        mappingIdIndex = kv2List( requireConf.map );
        mappingIdIndex.sort( createDescSorter() );

        each(
            mappingIdIndex,
            function ( item ) {
                var key = item.k;
                item.v = kv2List( item.v );
                item.v.sort( createDescSorter() );
                item.reg = key == '*'
                    ? /^/
                    : createPrefixRegexp( key );
            }
        );
    }

    /**
     * ��`ģ���ʶ+'.extension'`��ʽ���ַ���ת������Ե�url
     * 
     * @inner
     * @param {string} source Դ�ַ���
     * @return {string}
     */
    function toUrl( source ) {
        // ���� ģ���ʶ �� .extension
        var extReg = /(\.[a-z0-9]+)$/i;
        var queryReg = /(\?[^#]*)$/i;
        var extname = '.js';
        var id = source;
        var query = '';

        if ( queryReg.test( source ) ) {
            query = RegExp.$1;
            source = source.replace( queryReg, '' );
        }

        if ( extReg.test( source ) ) {
            extname = RegExp.$1;
            id = source.replace( extReg, '' );
        }

        // ģ���ʶ�Ϸ��Լ��
        if ( !MODULE_ID_REG.test( id ) ) {
            return source;
        }
        
        var url = id;

        // paths�����ƥ��
        var isPathMap;
        each( pathsIndex, function ( item ) {
            var key = item.k;
            if ( createPrefixRegexp( key ).test( id ) ) {
                url = url.replace( key, item.v );
                isPathMap = 1;
                return false;
            }
        } );

        // packages�����ƥ��
        if ( !isPathMap ) {
            each( 
                packagesIndex,
                function ( packageConf ) {
                    var name = packageConf.name;
                    if ( createPrefixRegexp( name ).test( id ) ) {
                        url = url.replace( name, packageConf.location );
                        return false;
                    }
                }
            );
        }

        // ���·��ʱ������baseUrl
        if ( !/^([a-z]{2,10}:\/)?\//i.test( url ) ) {
            url = requireConf.baseUrl + url;
        }

        // ���� .extension �� query
        url += extname + query;


        var isUrlArgsAppended;

        /**
         * Ϊurl����urlArgs
         * 
         * @inner
         * @param {string} args urlArgs��
         */
        function appendUrlArgs( args ) {
            if ( !isUrlArgsAppended ) {
                url += ( url.indexOf( '?' ) > 0 ? '&' : '?' ) + args;
                isUrlArgsAppended = 1;
            }
        }
        
        // urlArgs�����ƥ��
        each( urlArgsIndex, function ( item ) {
            if ( createPrefixRegexp( item.k ).test( id ) ) {
                appendUrlArgs( item.v );
                return false;
            }
        } );
        defaultUrlArgs && appendUrlArgs( defaultUrlArgs );

        return url;
    }

    /**
     * ����local require����
     * 
     * @inner
     * @param {number} baseId ��ǰmodule id
     * @return {Function}
     */
    function createLocalRequire( baseId ) {
        var requiredCache = {};
        function req( requireId, callback ) {
            if ( isString( requireId ) ) {
                var requiredModule;
                if ( !( requiredModule = requiredCache[ requireId ] ) ) {
                    requiredModule = nativeRequire( 
                        normalize( requireId, baseId ), 
                        callback, 
                        baseId 
                    );
                    requiredCache[ requireId ] = requiredModule;
                }
                
                return requiredModule;
            }
            else if ( isArray( requireId ) ) {
                // �����Ƿ���resourceʹ�õ�pluginû����
                var unloadedPluginModules = [];
                each( 
                    requireId, 
                    function ( id ) { 
                        var idInfo = parseId( id );
                        var pluginId = normalize( idInfo.module, baseId );
                        if ( idInfo.resource && !modIsDefined( pluginId ) ) {
                            unloadedPluginModules.push( pluginId );
                        }
                    }
                );

                // ����ģ��
                nativeRequire( 
                    unloadedPluginModules, 
                    function () {
                        var ids = [];
                        each( 
                            requireId, 
                            function ( id ) { 
                                ids.push( normalize( id, baseId ) ); 
                            } 
                        );
                        nativeRequire( ids, callback, baseId );
                    }, 
                    baseId
                );
            }
        }

        /**
         * ��[module ID] + '.extension'��ʽ���ַ���ת����url
         * 
         * @inner
         * @param {string} source ����������ʽ��Դ�ַ���
         * @return {string} 
         */
        req.toUrl = function ( id ) {
            return toUrl( normalize( id, baseId ) );
        };

        return req;
    }

    /**
     * id normalize��
     * 
     * @inner
     * @param {string} id ��Ҫnormalize��ģ���ʶ
     * @param {string} baseId ��ǰ������ģ���ʶ
     * @return {string}
     */
    function normalize( id, baseId ) {
        if ( !id ) {
            return '';
        }

        var idInfo = parseId( id );
        if ( !idInfo ) {
            return id;
        }

        var resourceId = idInfo.resource;
        var moduleId = relative2absolute( idInfo.module, baseId );

        each(
            packagesIndex,
            function ( packageConf ) {
                var name = packageConf.name;
                var main = name + '/' + packageConf.main;
                if ( name == moduleId
                ) {
                    moduleId = moduleId.replace( name, main );
                    return false;
                }
            }
        );

        moduleId = mappingId( moduleId, baseId );
        
        if ( resourceId ) {
            var module = modGetModuleExports( moduleId );
            resourceId = module && module.normalize
                ? module.normalize( 
                    resourceId, 
                    function ( resId ) {
                        return normalize( resId, baseId );
                    }
                  )
                : normalize( resourceId, baseId );
            
            return moduleId + '!' + resourceId;
        }
        
        return moduleId;
    }

    /**
     * ���idת���ɾ���id
     * 
     * @inner
     * @param {string} id Ҫת����id
     * @param {string} baseId ��ǰ���ڻ���id
     * @return {string}
     */
    function relative2absolute( id, baseId ) {
        if ( /^\.{1,2}/.test( id ) ) {
            var basePath = baseId.split( '/' );
            var namePath = id.split( '/' );
            var baseLen = basePath.length - 1;
            var nameLen = namePath.length;
            var cutBaseTerms = 0;
            var cutNameTerms = 0;

            pathLoop: for ( var i = 0; i < nameLen; i++ ) {
                var term = namePath[ i ];
                switch ( term ) {
                    case '..':
                        if ( cutBaseTerms < baseLen ) {
                            cutBaseTerms++;
                            cutNameTerms++;
                        }
                        else {
                            break pathLoop;
                        }
                        break;
                    case '.':
                        cutNameTerms++;
                        break;
                    default:
                        break pathLoop;
                }
            }

            basePath.length = baseLen - cutBaseTerms;
            namePath = namePath.slice( cutNameTerms );

            basePath.push.apply( basePath, namePath );
            return basePath.join( '/' );
        }

        return id;
    }

    /**
     * ȷ��require��ģ��id���������id������global require����ǰԤ�����Ը��ٵĴ������
     * 
     * @inner
     * @param {string|Array} requireId require��ģ��id
     */
    function assertNotContainRelativeId( requireId ) {
        var invalidIds = [];

        /**
         * ���ģ��id�Ƿ�relative id
         * 
         * @inner
         * @param {string} id ģ��id
         */
        function monitor( id ) {
            if ( /^\.{1,2}/.test( id ) ) {
                invalidIds.push( id );
            }
        }

        if ( isString( requireId ) ) {
            monitor( requireId );
        }
        else {
            each( 
                requireId, 
                function ( id ) {
                    monitor( id );
                }
            );
        }

        // �������idʱ��ֱ���׳�����
        if ( invalidIds.length > 0 ) {
            throw new Error(
                '[REQUIRE_FATAL]Relative ID is not allowed in global require: ' 
                + invalidIds.join( ', ' )
            );
        }
    }

    /**
     * ģ��id����
     * 
     * @const
     * @inner
     * @type {RegExp}
     */
    var MODULE_ID_REG = /^[-_a-z0-9\.]+(\/[-_a-z0-9\.]+)*$/i;

    /**
     * ����id�����ش���module��resource���Ե�Object
     * 
     * @inner
     * @param {string} id ��ʶ
     * @return {Object}
     */
    function parseId( id ) {
        var segs = id.split( '!' );

        if ( MODULE_ID_REG.test( segs[ 0 ] ) ) {
            return {
                module   : segs[ 0 ],
                resource : segs[ 1 ] || ''
            };
        }

        return null;
    }

    /**
     * ����map�������idӳ��
     * 
     * @inner
     * @param {string} id ģ��id
     * @param {string} baseId ��ǰ������ģ��id
     * @return {string}
     */
    function mappingId( id, baseId ) {
        each( 
            mappingIdIndex, 
            function ( item ) {
                if ( item.reg.test( baseId ) ) {

                    each( item.v, function ( mapData ) {
                        var key = mapData.k;
                        var rule = createPrefixRegexp( key );
                        
                        if ( rule.test( id ) ) {
                            id = id.replace( key, mapData.v );
                            return false;
                        }
                    } );

                    return false;
                }
            }
        );

        return id;
    }

    /**
     * ����������ת�������飬����ÿ���Ǵ���k��v��Object
     * 
     * @inner
     * @param {Object} source ��������
     * @return {Array.<Object>}
     */
    function kv2List( source ) {
        var list = [];
        for ( var key in source ) {
            if ( source.hasOwnProperty( key ) ) {
                list.push( {
                    k: key, 
                    v: source[ key ]
                } );
            }
        }

        return list;
    }

    // ��лrequirejs��ͨ��currentlyAddingScript�����Ͼ�ie
    // 
    // For some cache cases in IE 6-8, the script executes before the end
    // of the appendChild execution, so to tie an anonymous define
    // call to the module name (which is stored on the node), hold on
    // to a reference to this node, but clear after the DOM insertion.
    var currentlyAddingScript;
    var interactiveScript;

    /**
     * ��ȡ��ǰscript��ǩ
     * ����ie��defineδָ��module idʱ��ȡid
     * 
     * @inner
     * @return {HTMLDocument}
     */
    function getCurrentScript() {
        if ( currentlyAddingScript ) {
            return currentlyAddingScript;
        }
        else if ( 
            interactiveScript 
            && interactiveScript.readyState == 'interactive'
        ) {
            return interactiveScript;
        }
        else {
            var scripts = document.getElementsByTagName( 'script' );
            var scriptLen = scripts.length;
            while ( scriptLen-- ) {
                var script = scripts[ scriptLen ];
                if ( script.readyState == 'interactive' ) {
                    interactiveScript = script;
                    return script;
                }
            }
        }
    }

    /**
     * ��ҳ���в���script��ǩ
     * 
     * @inner
     * @param {HTMLScriptElement} script script��ǩ
     */
    function appendScript( script ) {
        currentlyAddingScript = script;

        var doc = document;
        (doc.getElementsByTagName('head')[0] || doc.body).appendChild( script );
        
        currentlyAddingScript = null;
    }

    /**
     * ����idǰ׺ƥ����������
     * 
     * @inner
     * @param {string} prefix idǰ׺
     * @return {RegExp}
     */
    function createPrefixRegexp( prefix ) {
        return new RegExp( '^' + prefix + '(/|$)' );
    }

    /**
     * �ж϶����Ƿ���������
     * 
     * @inner
     * @param {*} obj Ҫ�жϵĶ���
     * @return {boolean}
     */
    function isArray( obj ) {
        return obj instanceof Array;
    }

    /**
     * �ж϶����Ƿ�������
     * 
     * @inner
     * @param {*} obj Ҫ�жϵĶ���
     * @return {boolean}
     */
    function isFunction( obj ) {
        return typeof obj == 'function';
    }

    /**
     * �ж��Ƿ��ַ���
     * 
     * @inner
     * @param {*} obj Ҫ�жϵĶ���
     * @return {boolean}
     */
    function isString( obj ) {
        return typeof obj == 'string';
    }

    /**
     * ѭ���������鼯��
     * 
     * @inner
     * @param {Array} source ����Դ
     * @param {function(Array,Number):boolean} iterator ��������
     */
    function each( source, iterator ) {
        if ( isArray( source ) ) {
            for ( var i = 0, len = source.length; i < len; i++ ) {
                if ( iterator( source[ i ], i ) === false ) {
                    break;
                }
            }
        }
    }

    /**
     * ���������ַ�������������
     * 
     * @inner
     * @param {string} property �����������
     * @return {Function}
     */
    function createDescSorter( property ) {
        property = property || 'k';

        return function ( a, b ) {
            var aValue = a[ property ];
            var bValue = b[ property ];

            if ( bValue == '*' ) {
                return -1;
            }

            if ( aValue == '*' ) {
                return 1;
            }

            return bValue.length - aValue.length;
        };
    }

    // ��¶ȫ�ֶ���
    global.define = define;
    global.require = require;
})( this );
