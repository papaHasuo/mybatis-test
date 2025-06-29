package jp.ne.papapa.dao;

import jp.ne.papapa.dto.PersonDto;
import jp.ne.papapa.entity.PersonEntity;
import jp.ne.papapa.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {

    private static SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private PersonDao personDao;

    @BeforeAll
    static void setup() {
        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @BeforeEach
    void init() {
        try {
            session = sqlSessionFactory.openSession()
            personDao = session.getMapper(PersonDao.class);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to initialize PersonDao: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        if (session != null) {
            session.close();
        }
    }
}
