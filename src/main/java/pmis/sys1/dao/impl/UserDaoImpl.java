/**   
 * @Title: RdpBaseDaoImpl.java 
 * @Package com.szewec.ssdp.framework.core.dao.impl 
 * @Description: 用一句话描述该文件做什么 
 * Copyright: Copyright (c) 2014 
 * Company:wuwh team by iss
 * @author: wuwh   
 * @date: 2014年6月11日 下午8:19:22 
 * @version: V1.0
 * update Release(文件修正记录)
 * <pre>
 * author--updateDate--description----------------------Flag————
 * wuwh    2014-5-1    测试codesyle                      #wuwh001
 *
 *
 *
 * </pre>
 *
 */
package pmis.sys1.dao.impl;

import org.springframework.stereotype.Component;

import pmis.sys1.dao.UserDao;
import pmis.sys1.entity.User;
import pmis.web.support.dao.EntityDao;
import pmis.web.support.dao.impl.HibernateBasedDao;

@Component
public class UserDaoImpl extends HibernateBasedDao<User, String> implements EntityDao<User, String>, UserDao
{

}
