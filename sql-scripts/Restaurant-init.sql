-- create user only if not exsits 
GRANT USAGE ON *.* TO 'taskuserAE'@'localhost' IDENTIFIED BY 'Ai3ndjj1';

DROP USER 'taskuserAE'@'localhost';
CREATE USER 'taskuserAE'@'localhost' IDENTIFIED BY 'Ai3ndjj1';

GRANT ALL PRIVILEGES ON * . * TO 'taskuserAE'@'localhost';

DROP DATABASE  IF EXISTS `Restaurant`;
CREATE DATABASE  IF NOT EXISTS `Restaurant`;
USE `Restaurant`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` INT auto_increment,
  `name` VARCHAR(70) NOT NULL, 
  `email` VARCHAR(70) NOT NULL UNIQUE,
  `password` CHAR(68) NOT NULL,
  `mobile` CHAR(15) NOT NULL UNIQUE, 
  `is_admin` BOOLEAN DEFAULT false NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables` (
  `table_id` INT auto_increment, 
  `table_size` INT NOT NULL,
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE `reservations` (
  `reservation_id` INT auto_increment,
  `user_id` INT NOT NULL, 
  `table_id` INT NOT NULL, 
  `reservation_start_time` TIMESTAMP NOT NULL,
  `reservation_end_time` TIMESTAMP NOT NULL,
  `reservation_size` INT  NOT NULL ,
  PRIMARY KEY (`reservation_id`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`), 
  CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`table_id`) REFERENCES `tables` (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;





INSERT INTO `users` 
VALUES 
(1,'admin','admin@test.com','$2a$10$51n1fXRx2akCfpNOhFO/Z.7CBoh8d7ofcaCVDZUJ.GXAhnD6H1dc2','01200000000', true);
-- email: "admin@test.com"
-- password: "admin"

INSERT INTO `tables` 
VALUES 
(1,2),
(2,2),	
(3,2),
(4,2),
(5,5),
(6,5),
(7,5),
(8,5),
(9,5),
(10,5),
(11,5),
(12,10),
(13,10);


--
--  Fill Tables with some data
--
-- INSERT INTO `restaurant`.`reservations` (`reservation_id`, `user_id`, `table_id`, `reservation_start_time`, `reservation_end_time`, `reservation_size`) VALUES
-- ('1', '1', '7', '2021-01-01 20:00:00', '2021-01-01 21:00:00', '2');

-- INSERT INTO `restaurant`.`reservations` (`reservation_id`, `user_id`, `table_id`, `reservation_start_time`, `reservation_end_time`, `reservation_size`) VALUES 
-- ('2', '1', '7', '2021-01-01 18:00:00', '2021-01-01 19:00:00', '2');




