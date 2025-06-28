package jp.ne.papapa;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import jp.ne.papapa.dao.PersonDao;
import jp.ne.papapa.dto.PersonDto;
import jp.ne.papapa.util.MyBatisUtil;

public class Main {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();

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
}