package com.example.citrusdemo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.citrusdemo.beans.User;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {

}
