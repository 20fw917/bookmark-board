DROP VIEW IF EXISTS `comment_view`;
DROP VIEW IF EXISTS `article_view`;
DROP VIEW IF EXISTS `folder_view`;
DROP VIEW IF EXISTS `bookmark_view`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `folder_item`;
DROP TABLE IF EXISTS `article_like`;
DROP TABLE IF EXISTS `folder_like`;
DROP TABLE IF EXISTS `bookmark_like`;
DROP TABLE IF EXISTS `attachment_index`;
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
    `profile_image` varchar(60) NULL DEFAULT NULL COMMENT 'UUID는 36글자로 구성 + Type + extension',
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
  `thumbnail` varchar(60) DEFAULT NULL COMMENT 'UUID는 36글자로 구성 + Type + extension',
  `created_at` datetime NOT NULL DEFAULT now(),
  `is_shared` tinyint(1) NOT NULL DEFAULT 0 COMMENT '공유가 켜졌는가',
  `is_stared` tinyint(1) NOT NULL DEFAULT 0 COMMENT '즐겨찾기로 등록되었는가',
  FOREIGN KEY (`owner`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `folder_item` (
   `id` bigint(20) PRIMARY KEY auto_increment,
   `bookmark_id` bigint(20) NOT NULL,
   `parent_folder` bigint(20) NOT NULL,
   FOREIGN KEY (`bookmark_id`) REFERENCES `bookmark` (`id`) ON DELETE CASCADE,
   FOREIGN KEY (`parent_folder`) REFERENCES `folder` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `folder_like` (
    `id` bigint(20) NOT NULL PRIMARY KEY auto_increment,
    `user_id` bigint(20) NOT NULL,
    `folder_id` bigint(20) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE,
    FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `bookmark_like` (
   `id` bigint(20) NOT NULL PRIMARY KEY auto_increment,
   `user_id` bigint(20) NOT NULL,
   `bookmark_id` bigint(20) NOT NULL,
   FOREIGN KEY (`user_id`) REFERENCES `user` (`internal_id`) ON DELETE CASCADE,
   FOREIGN KEY (`bookmark_id`) REFERENCES `bookmark` (`id`) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE VIEW `folder_view` AS (
  SELECT f.id AS `id`, f.owner AS `owner`, f.title AS `title`, f.memo AS `memo`, f.thumbnail AS `thumbnail`,
         f.created_at AS `created_at`, f.is_shared as `is_shared`, f.is_stared as `is_stared`,
         (SELECT COUNT(*) FROM folder_item fi WHERE fi.parent_folder = f.id) AS `item_count`,
         (SELECT nickname FROM user u WHERE u.internal_id = f.owner) AS `author_nickname`,
         (SELECT COUNT(*) FROM folder_like fl WHERE fl.folder_id = f.id) AS `like_count`
  FROM `folder` f
);

CREATE VIEW `bookmark_view` AS (
     SELECT b.id AS `id`, b.owner AS `owner`, b.title AS `title`, b.memo AS `memo`, b.url AS `url`,
            b.created_at AS `created_at`, b.is_shared as `is_shared`, b.is_stared as `is_stared`,
            (SELECT nickname FROM user u WHERE u.internal_id = b.owner) AS `author_nickname`,
            (SELECT COUNT(*) FROM bookmark_like bl WHERE bl.bookmark_id = b.id) AS `like_count`
     FROM `bookmark` b
);