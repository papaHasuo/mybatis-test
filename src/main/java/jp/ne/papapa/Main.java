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
            PersonDao userDao = session.getMapper(PersonDao.class);
            PersonDto user = userDao.getPersonById("00001"); // 例: ID=1のユーザー取得
            if (user != null) {
                System.out.println("Name: " + user.getName());
                System.out.println("SexCd: " + user.getSex());
                System.out.println("BirthDay: " + user.getBirthDay());
            } else {
                System.out.println("ユーザーが見つかりませんでした。");
            }
        }

    }
}