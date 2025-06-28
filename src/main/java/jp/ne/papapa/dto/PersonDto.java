package jp.ne.papapa.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * Personテーブル取得用のDTO
 */
@Setter
@Getter
public class PersonDto {

    /** 名前 */
    private String name;

    /** 性別 */
    private String sex;

    /** 生年月日 */
    private LocalDate birthDay;
}
