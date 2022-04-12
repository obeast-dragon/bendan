package com.irm.bendan.service;

import com.irm.bendan.po.User;

public interface UserService {
    User saveUser(User user);

    User checkUser(String username,String password);

    User getUserByNickname(String nickname);
}
