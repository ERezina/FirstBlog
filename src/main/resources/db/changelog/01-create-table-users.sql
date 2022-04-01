 CREATE TABLE users
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	code varchar(255) DEFAULT NULL,
	email varchar(255) NOT NULL,
	is_moderator int NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    password varchar(255) NOT NULL,
    photo varchar(255) DEFAULT NULL,
    reg_time datetime(6) NOT NULL
 )
/
