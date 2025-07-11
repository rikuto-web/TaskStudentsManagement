# 受講生管理システム

## 概要
本システムは、オンラインスクール [RaiseTech] のJava/Spring Boot講座で作成した受講生管理アプリケーションです（講座No.44 時点）

受講生の基本情報・受講コース・申込状況を登録／更新／検索／論理削除することができます。

## カリキュラム進捗と取り組み内容
動画視聴はすべて完了しており、現在はポートフォリオ制作と同時進行でAWS構築（EC2、RDS）まで完了済みです。

カリキュラム上は 1:1:1 のデータ構成でしたが、より実践的な 1:N:1 構成に変更し、以下の改善に取り組みました：

- ドメイン設計の再構築（1:N:1対応）

- ネストされたデータ構造のConverterをメソッド分離し保守性を向上

- Swagger（springdoc-openapi）のUI品質向上のためアノテーション整理

- GitHub PagesでAPI仕様書をWeb公開

- Javadocによるソースコードの補足

## 使用技術

| 項目         | 内容                                |
|--------------|-------------------------------------|
| 言語         | Java 21                             |
| フレームワーク | Spring Boot 3.3.0                   |
| ビルドツール | Gradle                              |
| DB           | MySQL（本番） / H2（テスト）        |
| ORマッパー   | MyBatis 3.0.4                        |
| APIドキュメント | springdoc-openapi 2.8.8           |
| バリデーション | Hibernate Validator (JSR-380)      |
| テスト       | JUnit5, Mockito, H2DB               |

## 機能一覧

- 受講生一覧の取得（全件）
- 受講生IDでの検索
- 性別による絞り込み検索
- 新規受講生の登録（コース・申込情報含む）
- 受講生情報の更新（備考／申込状況／論理削除）


## APIエンドポイント一覧

| 機能名               | メソッド | エンドポイント                       | 概要                                  |
|----------------------|----------|--------------------------------------|---------------------------------------|
| 受講生一覧検索        | GET      | `/studentList`                       | 全受講生を取得                        |
| 受講生詳細検索        | GET      | `/student/{id}`                      | 指定IDの受講生を取得                  |
| 受講生詳細登録        | POST     | `/registerStudent`                   | 新規登録（コース・申込含む）          |
| 受講生詳細更新        | PUT      | `/updateStudent`                     | 受講生の情報を更新（論理削除含む）    |
| 性別による検索        | GET      | `/students/gender/{gender}`          | 性別での受講生フィルタ                 |

## API仕様書

▶ [Swagger UI（GitHub Pages 公開）](https://rikuto-web.github.io/TaskStudentsManagement/)

## JUnitテスト結果
<img width="1813" height="775" alt="スクリーンショット 2025-07-11 203935" src="https://github.com/user-attachments/assets/55a866e3-0859-4900-9aa1-7bf1bca94092" />

## Postman実行結果
### 登録


https://github.com/user-attachments/assets/7a753b8d-d431-4ce7-a9a0-f94bc803a63d


### 一覧検索


https://github.com/user-attachments/assets/0ac85299-06c3-428e-95ea-f33b40204972


### ID検索（受講生ID:5で検索*上記登録動画の結果を検索しています）


https://github.com/user-attachments/assets/c261545b-eebe-4658-bd0b-7a0af8f497f0


### 性別(男性)での検索


https://github.com/user-attachments/assets/b526a500-21c0-4428-938c-30f5d9020952


### 更新（受講生ID5の備考と申込状況の更新を行っています）


https://github.com/user-attachments/assets/4ac11999-3bb5-41b9-8452-1ef07e9cb85a


### ID検索で存在しないIDと整数以外での検索を行った結果


https://github.com/user-attachments/assets/4a9e1107-57ac-468e-a169-1f568b45fccd


### 登録時にバリデーションエラーを発生させた結果


https://github.com/user-attachments/assets/8fe30148-c6c2-416d-82ac-4e68accd07f8


### 更新時に空欄で処理した結果


https://github.com/user-attachments/assets/c7d2177a-2668-416a-86fd-af84f709f94d

