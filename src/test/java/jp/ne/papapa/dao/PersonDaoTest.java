package jp.ne.papapa.dao;

import jp.ne.papapa.dto.PersonDto;
import jp.ne.papapa.entity.PersonEntity;
import jp.ne.papapa.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DBRider
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
            session = sqlSessionFactory.openSession();
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

    @Test
    @DataSet(value = "dataset/PersonDao/input/PERSON.csv")
    void testGetPersonById_正常に取得できる() {
        // 想定データ用意
        PersonDto expected = new PersonDto();
        expected.setName("田中太郎");
        expected.setSex("M");
        expected.setBirthDay(LocalDate.of(1990, 1, 1));

        // 実行
        PersonDto actual = personDao.getPersonById("P001");

        // 検証
        assertAll("PersonDto fields comparison",
                () -> assertEquals(expected.getName(), actual.getName(), "Name should match"),
                () -> assertEquals(expected.getSex(), actual.getSex(), "Sex should match"),
                () -> assertEquals(expected.getBirthDay(), actual.getBirthDay(), "BirthDay should match"));
    }

    @Test
    @DataSet(value = "dataset/PersonDao/input/PERSON.csv")
    void testGetPersonById_存在しないID() {
        // 実行
        PersonDto actual = personDao.getPersonById("P999");

        // 検証
        assertNull(actual, "PersonDto should be null for non-existent ID");
    }
}
