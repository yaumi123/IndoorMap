package com.tq.indoormap.greenDao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.tq.indoormap.entity.Shop;
import com.tq.indoormap.entity.User;

import com.tq.indoormap.greenDao.ShopDao;
import com.tq.indoormap.greenDao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig shopDaoConfig;
    private final DaoConfig userDaoConfig;

    private final ShopDao shopDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        shopDaoConfig = daoConfigMap.get(ShopDao.class).clone();
        shopDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        shopDao = new ShopDao(shopDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Shop.class, shopDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        shopDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public ShopDao getShopDao() {
        return shopDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
