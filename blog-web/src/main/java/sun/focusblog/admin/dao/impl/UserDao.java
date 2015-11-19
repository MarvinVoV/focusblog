package sun.focusblog.admin.dao.impl;

import org.springframework.stereotype.Repository;
import sun.focusblog.admin.dao.IUserDao;
import sun.focusblog.admin.domain.auth.User;
import sun.focusblog.admin.context.CacheNameSpace;
import sun.focusblog.admin.dao.BaseDao;
import sun.focusblog.framework.cache.annotation.Cacheable;
import sun.focusblog.framework.cache.util.ExpireConstants;

/**
 * Created by root on 2015/11/7.
 */
@Repository
public class UserDao extends BaseDao implements IUserDao {

    @Cacheable(namespace = CacheNameSpace.CACHE_USER, fieldsKey = {"#userId"}, expire = ExpireConstants.ONE_DAY)
    @Override
    public User query(String userId) {
        return getSqlSession().selectOne(buildStatement(NAMESPACE, "query"),
                getParamsBuilder()
                        .put("userId", userId)
                        .build()
        );
    }


    @Override
    public boolean persistUser(User user) {
        return getSqlSession().insert(buildStatement(NAMESPACE, "persistUser"), user) > 0;
    }

    @Override
    public boolean persistUserRole(String userId, int roleId) {
        return getSqlSession().insert(buildStatement(NAMESPACE, "persistUserRole"),
                getParamsBuilder()
                        .put("userId", userId)
                        .put("roleId", roleId)
                        .build()
        ) > 0;
    }
}