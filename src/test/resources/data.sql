-- students
INSERT INTO students (id, full_name, furigana, nickname, email_address, address, age, gender, remark, is_deleted) VALUES
(1, '山田 太郎', 'ヤマダ タロウ', 'タロウ', 'taro.yamada@example.com', '東京都渋谷区', 20, '男性', NULL, FALSE),
(2, '鈴木 花子', 'スズキ ハナコ', 'ハナ', 'hanako.suzuki@example.com', '大阪府大阪市', 22, '女性', NULL, FALSE),
(3, '佐藤 健太', 'サトウ ケンタ', NULL, 'kenta.sato@example.com', '福岡県福岡市', 25, '男性', NULL, FALSE),
(4, '田中 美咲', 'タナカ ミサキ', 'ミサキ', 'misaki.tanaka@example.com', '愛知県名古屋市', 21, '女性', NULL, FALSE),
(5, '高橋 雄一', 'タカハシ ユウイチ', NULL, 'yuichi.takahashi@example.com', '北海道札幌市', 23, '男性', NULL, FALSE);
-- 明示的にAUTO_INCREMENTのカウンタを6以降に進める
ALTER TABLE students ALTER COLUMN id RESTART WITH 6;


-- students_courses
INSERT INTO students_courses (id, student_id, course_name, course_start_day, course_completion_day) VALUES
(101, 1, 'Java基礎', '2024-04-01', '2024-06-30'),
(102, 5, 'Spring Boot実践', '2024-07-15', '2024-09-30'),
(103, 2, 'データサイエンス入門', '2024-05-10', '2024-08-10'),
(104, 3, 'SQLデータベース', '2024-06-01', '2024-07-31'),
(105, 4, 'フロントエンド開発', '2024-04-20', '2024-09-20');

-- application_status（修正済：存在する student_course_id のみ使用）
INSERT INTO application_status (student_course_id, status) VALUES
(101, 'KARI_MOSIKOMI'),
(102, 'HON_MOSIKOMI'),
(103, 'JUKOCHU'),
(104, 'JUKO_SHURYO'),
(105, 'KARI_MOSIKOMI');
