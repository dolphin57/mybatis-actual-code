package io.dolphin;

import io.dolphin.entity.User;
import io.dolphin.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class UserMapperTest {

    @Test
    public void findUserById() throws IOException {
        String resource = "mybatis-config.xml";
        // 将XML配置文件构建为Configuration配置类
        Reader reader = Resources.getResourceAsReader(resource);
        // 通过加载配置文件流构建一个SqlSessionFactory   解析xml文件 1
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        // 数据源 执行器  DefaultSqlSession 2
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            // 执行查询 底层执行jdbc 3
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.findUserById(1);
            System.out.println(user.getName());
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }
}
