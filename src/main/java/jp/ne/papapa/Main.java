package jp.ne.papapa;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import jp.ne.papapa.dao.PersonDao;
import jp.ne.papapa.dto.PersonDto;
import jp.ne.papapa.entity.PersonEntity;
import jp.ne.papapa.util.MyBatisUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();

        // Person取得サンプル関数を呼び出す
        selectPersonSample(sqlSessionFactory);
        // Personを登録するサンプル関数を呼び出す
        insertPersonSample(sqlSessionFactory);
    }

    /**
     * Personを取得するサンプル関数
     * 
     * @param sqlSessionFactory SqlSessionFactoryのインスタンス
     */
    public static void selectPersonSample(SqlSessionFactory sqlSessionFactory) {
        
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PersonDao personDao = session.getMapper(PersonDao.class);
            PersonDto person = personDao.getPersonById("00001"); // 例: ID=1のユーザー取得
            if (person != null) {
                System.out.println("Name: " + person.getName());
                System.out.println("Sex: " + person.getSex());
                System.out.println("BirthDay: " + person.getBirthDay());
            } else {
                System.out.println("ユーザーが見つかりませんでした。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("データ取得時にエラーが発生しました。");
        }
    }

    /**
     * Personを登録するサンプル関数
     * 
     * @param sqlSessionFactory SqlSessionFactoryのインスタンス
     */
    public static void insertPersonSample(SqlSessionFactory sqlSessionFactory) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PersonDao personDao = session.getMapper(PersonDao.class);
            LocalDateTime now = LocalDateTime.now();
            PersonEntity entity = new PersonEntity();
            entity.setId("00003");
            entity.setName("新規太郎");
            entity.setSex("M");
            entity.setBirthDay(LocalDate.of(2000, 4, 1));
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            int count = personDao.insertPerson(entity);
            session.commit();
            System.out.println(count + "件 登録しました。");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("登録時にエラーが発生しました。");
        }
    }
}