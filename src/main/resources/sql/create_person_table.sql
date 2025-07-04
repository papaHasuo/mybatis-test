-- PERSONテーブル作成SQL (SQL Server用)

CREATE TABLE PERSON (
    ID VARCHAR(50) NOT NULL PRIMARY KEY,
    NAME NVARCHAR(100) NOT NULL,
    SEX VARCHAR(10),
    BIRTHDAY DATE,
    CREATED_AT DATETIME2 NOT NULL DEFAULT GETDATE(),
    UPDATED_AT DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- インデックス作成（任意）
CREATE INDEX IX_PERSON_NAME ON PERSON(NAME);
CREATE INDEX IX_PERSON_BIRTHDAY ON PERSON(BIRTHDAY);

-- サンプルデータ挿入
INSERT INTO PERSON (ID, NAME, SEX, BIRTHDAY, CREATED_AT, UPDATED_AT) VALUES 
('P001', '田中太郎', '男性', '1990-01-01', GETDATE(), GETDATE()),
('P002', '佐藤花子', '女性', '1992-05-15', GETDATE(), GETDATE()),
('P003', '山田次郎', '男性', '1988-12-25', GETDATE(), GETDATE());
