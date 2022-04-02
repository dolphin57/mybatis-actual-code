package io.dolphin.mapper;

import io.dolphin.entity.User;

import java.util.List;

public interface UserMapper {
    public void insert(User user);

    public User findUserById (int userId);

    public List<User> findAllUsers();
}
