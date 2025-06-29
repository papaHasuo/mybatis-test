package jp.ne.papapa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.ne.papapa.dto.PersonDto;
import jp.ne.papapa.entity.PersonEntity;

/**
 * Personテーブル用Dao
 */
@Mapper
public interface PersonDao {
    
    /**
     * IDからユーザを取得する
     * @param id
     * @return ユーザ情報
     */
    public PersonDto getPersonById(@Param("id") String id);

    /**
     * ユーザを登録する
     * 
     * @param person 登録するユーザ情報
     * @return 登録されたユーザの数
     */
    public int insertPerson(PersonEntity person);
}
