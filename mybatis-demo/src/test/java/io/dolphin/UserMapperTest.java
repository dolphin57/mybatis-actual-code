package io.dolphin;

import io.dolphin.entity.User;
import io.dolphin.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class UserMapperTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() {
        String resource = "mybatis-config.xml";
        // 将XML配置文件构建为Configuration配置类
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过加载配置文件流构建一个SqlSessionFactory   解析xml文件 1
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

    }

    /**
     * 基于StatementId的方式去执行SQL
     * <mapper resource="EmpMapper.xml"/>
     */
    @Test
    public void findUserById() {
        // 数据源 执行器  DefaultSqlSession 2
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            User user = sqlSession.selectOne("io.dolphin.mapper.UserMapper.findUserById", 1);
            System.out.println(user.getName());
        }
    }

    /**
     * 基于接口绑定的方式
     *  1.新建数据访问层的接口：  POJOMapper
     *  2.添加mapper中对应的操作的方法
     *      1.方法名要和mapper中对应的操作的节点的id要一致
     *      2.返回类型要和mapper中对应的操作的节点的resultType要一致
     *      3.mapper中对应的操作的节点的参数必须要在方法的参数中声明
     *  3.Mapper.xml 中的namespace必须要和接口的完整限定名要一致
     *  4.修改mybatis全局配置文件中的mappers,采用接口绑定的方式:
     *        <mapper class="cn.tulingxueyuan.mapper.EmpMapper"></mapper>
     *  5.一定要将mapper.xml和接口放在同一级目录中，只需要在resources新建和接口同样结构的文件夹就行了，生成就会合并在一起
     *
     * @throws IOException
     */
    @Test
    public void testMapper(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.findUserById(1);
            System.out.println(user.getName());
        }
    }

    /**
     * 基于注解的方式
     * 1.在接口方法上面写上对应的注解
     *@Select("select * from emp where id=#{id}")
     * 注意：
     *      注解可以和xml共用， 但是不能同时存在方法对应的xml的id
     *
     */
    @Test
    public void test03(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUser(1);
            System.out.println(user.getName());
        }
    }
}
