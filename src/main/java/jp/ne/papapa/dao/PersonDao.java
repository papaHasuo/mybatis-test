package jp.ne.papapa.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.ne.papapa.dto.PersonDto;

/**
 * Userテーブル用Dao
 */
@Mapper
public interface PersonDao {
    
    /**
     * IDからユーザを取得する
     * @param id
     * @return ユーザ情報
     */
    public PersonDto getPersonById(@Param("id") String id);
}
