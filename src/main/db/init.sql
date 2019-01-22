DROP DATABASE IF EXISTS `my_project_db`;

CREATE DATABASE `my_project_db` DEFAULT CHARACTER SET utf8;

USE `my_project_db`;

CREATE TABLE `users` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
    `login` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`balance` BIGINT NOT NULL,
	`role` INTEGER NOT NULL,
	`status` BOOLEAN NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `services` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(255) NOT NULL,
    `price` BIGINT NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `bills` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`user_id` INTEGER,
    `service_id` INTEGER,
	`bill` BIGINT NOT NULL,
	`paid` BIGINT NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

ALTER TABLE `bills`
ADD CONSTRAINT `user_to_services`
FOREIGN KEY (`user_id`)
REFERENCES `users`(`id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT;

ALTER TABLE `bills`
ADD CONSTRAINT `services_to_user`
FOREIGN KEY (`service_id`)
REFERENCES `services`(`id`)
ON UPDATE RESTRICT
ON DELETE RESTRICT;

INSERT INTO `users`
(`id`, `login`, `password`, `name`, `balance`, `role`, `status`)
VALUES
(1, "lol1", "qwerty", "Vasya Pupkin", 900000, 0, true),
(2, "lol2", "qwerty", "Petya Pupkin", 1000000, 0, false),
(3, "lol3", "qwerty", "Maria", 500000, 0, true),
(4, "lol4", "qwerty", "Kolya", 200000, 0, false),
(5, "viktorika", "qwerty", "Victory", 200000, 0, true),
(6, "lol5", "qwerty", "Name", 200000, 0, false),
(7, "lol6", "qwerty", "Kolya", 100000, 0, true),
(8, "admin", "root", "Admin_name", 0, 1, true);

INSERT INTO `services`
(`id`, `title`, `price`)
VALUES
(1, "Smart", 100),
(2, "Max", 800),
(3, "Middle", 300),
(4, "Unlimit", 1000),
(5, "Big", 500);

INSERT INTO `bills`
(`id`, `user_id`, `service_id`, `bill`, `paid`)
VALUES
(1, 1, 1, 500, 300),
(2, 1, 2, 100, 100),
(3, 2, 1, 1000, 1000),
(4, 3, 1, 50, 50),
(5, 4, 1, 50, 50),
(6, 4, 2, 500, 400);