DROP VIEW IF EXISTS `comment_view`;
DROP VIEW IF EXISTS `article_view`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `folder_item`;
DROP TABLE IF EXISTS `attachment_index`;
DROP TABLE IF EXISTS `article_like`;
DROP TABLE IF EXISTS `article`;
DROP TABLE IF EXISTS `folder`;
DROP TABLE IF EXISTS `bookmark`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `internal_id` bigint(20) NOT NULL PRIMARY KEY auto_increment COMMENT '내부 분류 아이디',
    `username` varchar(30) NOT NULL UNIQUE COMMENT '로그인 할 때 사용할 것',
    `password` varchar(256) NOT NULL COMMENT 'SHA1',
    `email` varchar(320) NOT NULL COMMENT '이메일 ID 부분은 최대 64자 + @ + 도메인은 255자까지 320자',
    `nickname` varchar(15) NOT NULL UNIQUE COMMENT '닉네임 15자 제한',
    `profile_image` varchar(36) NULL DEFAULT NULL COMMENT 'UUID는 36글자로 구성',
    `role` varchar(15) NOT NULL COMMENT 'For Spring Security'
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `bookmark` (
    `id` bigint(20) PRIMARY KEY auto_increment,
    `owner` bigint(20) NOT NULL,
    `title` varchar(100) NOT NULL COMMENT '제목 100자 제한',
    `memo` TEXT DEFAULT NULL COMMENT '메모가 없을 수도 있으니 NULLABLE',
    `url` TEXT NOT NULL,
    `created_at` datetime NOT NULL DEFAULT now(),
    `is_shared` tinyint(1) NOT NULL DEFAULT 0 COMMENT '공유가 켜졌는가',
    `is_stared` tinyint(1) NOT NULL DEFAULT 0 COMMENT '즐겨찾기 여부',
    FOREIGN KEY (`owner`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `folder` (
  `id` bigint(20) PRIMARY KEY auto_increment,
  `owner` bigint(20) NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '제목 100자 제한',
  `memo` TEXT DEFAULT NULL COMMENT '메모가 없을 수도 있으니 NULLABLE',
  `thumbnail` varchar(36) DEFAULT NULL COMMENT 'UUID는 36글자로 구성',
  `created_at` datetime NOT NULL DEFAULT now(),
  `is_shared` tinyint(1) NOT NULL DEFAULT 0 COMMENT '공유가 켜졌는가',
  `is_stared` tinyint(1) NOT NULL DEFAULT 0 COMMENT '즐겨찾기로 등록되었는가',
  FOREIGN KEY (`owner`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `folder_item` (
   `id` bigint(20) PRIMARY KEY auto_increment,
   `bookmark_id` bigint(20) NOT NULL,
   `parent_folder` bigint(20) NOT NULL,
   `created_at` datetime NOT NULL DEFAULT now(),
   FOREIGN KEY (`bookmark_id`) REFERENCES `bookmark` (`id`) ON DELETE CASCADE,
   FOREIGN KEY (`parent_folder`) REFERENCES `folder` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `article` (
   `id` bigint(20) NOT NULL PRIMARY KEY auto_increment,
   `author_id` bigint(20) NOT NULL,
   `title` varchar(100) NOT NULL COMMENT '제목 100자 제한',
   `content` TEXT NOT NULL,
   `created_at` datetime NOT NULL DEFAULT now(),
   `view_count` int(11) NOT NULL DEFAULT 0 COMMENT '조회수',
   `suggest_bookmark` bigint(20) DEFAULT NULL COMMENT '추천하는 북마크 ID',
   `suggest_folder` bigint(20) DEFAULT NULL COMMENT '추천하는 폴더 ID',
   FOREIGN KEY (`author_id`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE,
   FOREIGN KEY (`suggest_bookmark`) REFERENCES `bookmark` (`id`) ON DELETE CASCADE,
   FOREIGN KEY (`author_id`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `comment` (
   `id` bigint(20) NOT NULL PRIMARY KEY auto_increment,
   `author_id` bigint(20) NOT NULL,
   `article_id` bigint(20) NOT NULL,
   `content` varchar(1000) NOT NULL COMMENT '댓글 1,000자 제한',
   `created_at` datetime NOT NULL DEFAULT now(),
   FOREIGN KEY (`author_id`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE,
   FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `attachment_index` (
    `id` bigint(20) NOT NULL PRIMARY KEY auto_increment,
    `original_filename` varchar(256) NOT NULL COMMENT '파일 이름 최대 256자',
    `renamed_filename` varchar(36) NOT NULL COMMENT 'UUID는 36글자로 구성',
    `article_id` bigint(20) NOT NULL,
    FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `article_like` (
    `id` bigint(20) NOT NULL PRIMARY KEY auto_increment,
    `user_id` bigint(20) NOT NULL,
    `article_id` bigint(20) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE,
    FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE VIEW `article_view` AS (
  SELECT a.id AS `id`, a.content AS `content`, a.created_at AS `created_at`,
         a.view_count AS `view_count`, a.author_id AS `author_id`, u.nickname AS `author_nickname`,
         (SELECT count(*) AS count FROM article_like WHERE article_like.article_id = a.id) AS `like_count`
    FROM `article` a
    JOIN `user` u
    ON a.author_id = u.internal_id
);

CREATE VIEW `comment_view` AS (
  SELECT c.id AS `id`, c.content AS `content`,
     c.created_at AS `created_at`, c.author_id AS `author_id`, u.nickname AS `author_nickname`
  FROM `comment` c
    JOIN `user` u ON c.author_id = u.internal_id
);

CREATE VIEW `folder_view` AS (
  SELECT f.id AS `id`, f.owner AS `owner`, f.title AS `title`, f.memo AS `memo`, f.thumbnail AS thumbnail,
         f.created_at AS `created_at`, f.is_shared as `is_shared`, f.is_stared as `is_stared`,
         (SELECT COUNT(*) FROM folder_item fi WHERE fi.parent_folder = id) AS `count`
  FROM `folder` f
  );