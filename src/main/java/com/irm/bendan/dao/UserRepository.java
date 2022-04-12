package com.irm.bendan.dao;

import com.irm.bendan.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

    User findByNickname(String nickname);

}
