package org.yxc.example.service;

import org.yxc.example.dao.UserDao;
import org.yxc.example.entity.User;

/**
 * Created by yangxiuchu on 2017/12/22.
 */
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void update(Long userId, String username) {
        this.userDao.update(new User(userId, username, null, null));
    }

}
