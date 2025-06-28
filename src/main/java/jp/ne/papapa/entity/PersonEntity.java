package jp.ne.papapa.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Personテーブルのエンティティクラス
 */
@Getter
@Setter
public class PersonEntity {

    /** ID */
    private String id;

    /** 名前 */
    private String name;

    /** 性別 */
    private String sex;

    /** 生年月日 */
    private LocalDate birthDay;

    /** 作成日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
