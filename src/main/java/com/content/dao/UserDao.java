package com.content.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.content.model.User;

public interface UserDao extends JpaRepository<User, Long> {
	User findByUserName(String email);
	User findByPassword(String password);

}
