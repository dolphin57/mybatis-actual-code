package io.dolphin.mapper;

import io.dolphin.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    public User findUserById (int userId);

    @Select("select * from user where id=#{id}")
    User selectUser(int userId);
}
