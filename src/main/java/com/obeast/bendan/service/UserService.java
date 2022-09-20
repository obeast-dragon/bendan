package com.obeast.bendan.service;

import com.obeast.bendan.po.User;

public interface UserService {
    User saveUser(User user);

    User checkUser(String username,String password);

    User getUserByNickname(String nickname);
}
