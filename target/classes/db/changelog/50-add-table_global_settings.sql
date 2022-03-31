CREATE TABLE global_settings
(
	ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	code varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	value_settings varchar(255) NOT NULL
 )
/
INSERT INTO global_settings(code,name,value_settings)
VALUES ('MULTIUSER_MODE','Многопользовательский режим','YES')
/
INSERT INTO global_settings(code,name,value_settings)
VALUES ('POST_PREMODERATION','Премодерация постов','YES')
/
INSERT INTO global_settings(code,name,value_settings)
VALUES ('STATISTICS_IS_PUBLIC','Показывать всем статистику блога','NO')
/