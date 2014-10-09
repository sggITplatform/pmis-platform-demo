/**
 * ESL (Enterprise Standard Loader)
 * Copyright 2013 Baidu Inc. All rights reserved.
 * 
 * @file CSS Loader-Plugin
 * @author errorrik(errorrik@gmail.com)
 */

// ���������ݲ�֧�ַ�����Ϊ���ܺϲ���plugin��loader�
// ֻ����ʱʹ�þ���id
define( 'css', {
    load: function ( resourceId, req, load, config ) {
        var link = document.createElement( 'link' );
        link.setAttribute( 'rel', 'stylesheet' );
        link.setAttribute( 'type', 'text/css' );
        link.setAttribute( 'href', req.toUrl( resourceId ) );

        var parent = document.getElementsByTagName( 'head' )[ 0 ] 
            || document.body;
        parent.appendChild( link );

        parent = null;
        link = null;

        load( true );
    }
} );
