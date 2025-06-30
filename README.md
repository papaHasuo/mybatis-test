# MyBatis Test Project

MyBatisとSQL Serverを使用したJavaのサンプルプロジェクトです。
**Database Rider**を用いたデータベーステストに重点を置いて、Mybatisで実装されたPersonテーブル用Daoのテストを実装しています。

## 概要

このプロジェクトは、**Database Riderフレームワーク**を使用したデータベーステストの実装方法を学ぶためのサンプルアプリケーションです。MyBatisとSQL Serverを組み合わせて、Personエンティティの基本的な操作（取得・登録）を実装し、包括的なテストスイートを提供しています。

特にDatabase Riderを活用することで、以下を実現しています：
- CSVファイルによるテストデータの管理
- データベース状態の事前設定と期待値検証
- テスト間でのデータベース状態の分離

## 技術スタック

- **Java**: 21
- **フレームワーク**: MyBatis 3.5.15
- **データベース**: Microsoft SQL Server
- **ビルドツール**: Apache Maven
- **テストフレームワーク**: JUnit 5
- **データベーステストフレームワーク**: Database Rider 1.44.0 ⭐
- **その他**: Lombok

### Database Riderの特徴

Database Riderは、データベーステストを簡素化する強力なフレームワークです：
- **宣言的テストデータ管理**: CSVファイルでテストデータを定義
- **データセット比較**: 期待値との自動比較機能
- **トランザクション管理**: テスト間での確実なデータ分離
- **JUnit 5統合**: アノテーションベースの簡潔なテスト記述

## プロジェクト構成

```
src/
├── main/
│   ├── java/
│   │   └── jp/ne/papapa/
│   │       ├── Main.java              # メインクラス
│   │       ├── dao/
│   │       │   └── PersonDao.java     # Personテーブル用DAO
│   │       ├── dto/
│   │       │   └── PersonDto.java     # データ転送オブジェクト
│   │       ├── entity/
│   │       │   └── PersonEntity.java  # エンティティクラス
│   │       └── util/
│   │           └── MyBatisUtil.java   # MyBatis設定ユーティリティ
│   └── resources/
│       ├── mybatis-config.xml         # MyBatis設定ファイル
│       ├── mapper/
│       │   └── PersonMapper.xml       # SQLマッピングファイル
│       └── sql/
│           └── create_person_table.sql # テーブル作成SQL
└── test/
    ├── java/
    │   └── jp/ne/papapa/dao/
    │       └── PersonDaoTest.java      # DAOテストクラス
    └── resources/
        ├── dbunit.yaml                 # Database Rider設定
        └── dataset/                    # テストデータセット
```

## 前提条件

- Java 21以上
- Apache Maven 3.6以上
- Microsoft SQL Server（ローカル環境）

## セットアップ

### 1. SQL Serverの準備

SQL Serverをローカル環境で起動し、以下の接続情報でアクセスできることを確認してください：

### 2. データベーステーブルの作成

`src/main/resources/sql/create_person_table.sql`を実行して、PERSONテーブルを作成してください：

```sql
CREATE TABLE PERSON (
    ID VARCHAR(50) NOT NULL PRIMARY KEY,
    NAME NVARCHAR(100) NOT NULL,
    SEX VARCHAR(10),
    BIRTHDAY DATE,
    CREATED_AT DATETIME2 NOT NULL DEFAULT GETDATE(),
    UPDATED_AT DATETIME2 NOT NULL DEFAULT GETDATE()
);
```

### 3. プロジェクトのビルド

```bash
mvn clean compile
```

## 実行方法

### テストの実行

Database Riderを使用したテストを実行：
```bash
mvn test
```

## 主な機能

### PersonDao

- `getPersonById(String id)`: IDを指定してPersonを取得
- `insertPerson(PersonEntity person)`: 新しいPersonを登録

## データベース設定

MyBatisの設定は`src/main/resources/mybatis-config.xml`で管理されています。データベース接続情報を変更する場合は、このファイルを編集してください。

```xml
<dataSource type="POOLED">
    <property name="driver" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
    <property name="url" value="jdbc:sqlserver://localhost:1433;databaseName=*****;trustServerCertificate=true" />
    <property name="username" value="*****" />
    <property name="password" value="*****" />
</dataSource>
```

## テスト

### Database Riderの設定

テスト設定は`src/test/resources/dbunit.yaml`で管理されています：

```yaml
connectionConfig:
  driver: com.microsoft.sqlserver.jdbc.SQLServerDriver
  url: "jdbc:sqlserver://localhost:1433;databaseName=*****;trustServerCertificate=true"
  user: *****
  password: *****
```

### テストデータの管理

Database Riderでは、CSVファイルでテストデータを管理します：

#### 入力データ (`src/test/resources/dataset/PersonDao/input/`)

**PERSON.csv** - テスト実行前の初期データ
```csv
ID,NAME,SEX,BIRTHDAY,CREATED_AT,UPDATED_AT
00001,テスト太郎,M,1990-01-01,2023-01-01 00:00:00,2023-01-01 00:00:00
00002,テスト花子,F,1992-05-15,2023-01-01 00:00:00,2023-01-01 00:00:00
```

**table-ordering.txt** - テーブル挿入の順番
```
PERSON
```

#### 期待値データ (`src/test/resources/dataset/PersonDao/expected/`)

**PERSON.csv** - テスト実行後の期待される状態
```csv
ID,NAME,SEX,BIRTHDAY,CREATED_AT,UPDATED_AT
00001,テスト太郎,M,1990-01-01,2023-01-01 00:00:00,2023-01-01 00:00:00
00002,テスト花子,F,1992-05-15,2023-01-01 00:00:00,2023-01-01 00:00:00
00003,新規太郎,M,2000-04-01,??,??
```

### テストクラスの実装例

`PersonDaoTest.java`では、以下のようにDatabase Riderアノテーションを使用：

```java
@DBRider
class PersonDaoTest {
    
    @Test
    @Dataset("dataset/PersonDao/input/PERSON.csv")
    @ExpectedDataSet("dataset/PersonDao/expected/PERSON.csv")
    void testInsertPerson() {
        // テスト実装
    }
}
```

### 今回用いたDatabase Riderの主な機能

1. **@DataSet**: テスト開始前のデータベース状態を定義
2. **@ExpectedDataSet**: テスト後の期待される状態を定義

### テスト実行方法

#### 個別テスト実行
```bash
mvn test -Dtest=PersonDaoTest
```

#### 特定のテストメソッド実行
```bash
mvn test -Dtest=PersonDaoTest#testInsertPerson
```

#### 全テスト実行（詳細ログ付き）
```bash
mvn test -Ddbunit.debug=true
```

## トラブルシューティング

### Database Riderテスト関連

#### テストデータが正しく読み込まれない
- CSVファイルの文字エンコーディングがUTF-8であることを確認
- カラム名がデータベースのカラム名と一致することを確認
- table-ordering.txtでテーブルの依存関係を正しく定義

#### データベース接続エラー
- SQL Serverが起動していることを確認
- `dbunit.yaml`の接続情報が正しいことを確認
- テスト用データベースのアクセス権限を確認

### データベース接続エラー

- SQL Serverが起動していることを確認してください
- 接続情報（ユーザー名、パスワード、ポート番号）が正しいことを確認してください
- ファイアウォールの設定を確認してください

### ビルドエラー

- Java 21以上がインストールされていることを確認してください
- Mavenの設定が正しいことを確認してください

## 作成者

papapa (jp.ne.papapa)
