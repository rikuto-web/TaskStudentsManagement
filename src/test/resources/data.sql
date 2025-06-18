
INSERT INTO students (full_name, furigana, nickname, email_address, address, age, gender, remark, is_deleted) VALUES
('山田 太郎', 'ヤマダ タロウ', 'タロウ', 'taro.yamada@example.com', '東京都渋谷区', 20, '男性', NULL, FALSE),
('鈴木 花子', 'スズキ ハナコ', 'ハナ', 'hanako.suzuki@example.com', '大阪府大阪市', 22, '女性', 'Javaコース受講予定', FALSE),
('佐藤 健太', 'サトウ ケンタ', NULL, 'kenta.sato@example.com', '福岡県福岡市', 25, '男性', NULL, FALSE),
('田中 美咲', 'タナカ ミサキ', 'ミサキ', 'misaki.tanaka@example.com', '愛知県名古屋市', 21, '女性', 'Python興味あり', FALSE),
('高橋 雄一', 'タカハシ ユウイチ', NULL, 'yuichi.takahashi@example.com', '北海道札幌市', 23, '男性', 'Web開発コース検討中', FALSE);


INSERT INTO students_courses (student_id, course_name, course_start_day, course_completion_day) VALUES
(1, 'Java基礎', '2024-04-01', '2024-06-30'),
(5, 'Spring Boot実践', '2024-07-15', '2024-09-30'),
(2, 'データサイエンス入門', '2024-05-10', '2024-08-10'),
(3, 'SQLデータベース', '2024-06-01', '2024-07-31'),
(4, 'フロントエンド開発', '2024-04-20', '2024-09-20');

INSERT INTO application_status (student_course_id, status) VALUES
(101, 'KARI_MOSIKOMI'),
(999, 'HON_MOSIKOMI'),
(103, 'JUKOCHU'),
(104, 'JUKO_SHURYO'),
(105, 'KARI_MOSIKOMI');