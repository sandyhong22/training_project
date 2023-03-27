CREATE SCHEMA IF NOT EXISTS `demo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE TABLE `user`
(
    `user_id`            int unsigned NOT NULL AUTO_INCREMENT,
    `username`           varchar(45)  NOT NULL,
    `email`              varchar(45)  NOT NULL,
    `password`           varchar(256) NOT NULL,
    `created_date`       timestamp(6) NOT NULL,
    `last_modified_date` timestamp(6) NOT NULL,
    `name`               varchar(255) NOT NULL,
    `role`               varchar(45)  NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `attendance`
(
    `id`             int unsigned NOT NULL AUTO_INCREMENT,
    `username`       varchar(45) NOT NULL,
    `date`           date        NOT NULL,
    `clock_in_time`  timestamp(6),
    `clock_out_time` timestamp(6),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `attendance_records`
(
    `id`          int         NOT NULL AUTO_INCREMENT,
    `username`    varchar(45) NOT NULL,
    `work_record` varchar(45) NOT NULL,
    `date`        date        NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
