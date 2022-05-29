 CREATE TABLE captcha_codes
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    time datetime(6) NOT NULL,
	code varchar(255) DEFAULT NULL,
    secret_code varchar(255) NOT NULL
	)
/
