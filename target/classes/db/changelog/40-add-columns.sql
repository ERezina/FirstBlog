ALTER TABLE post_votes
ADD value_votes int NOT NULL
/
CREATE TABLE tags
(
	ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name varchar(255) NOT NULL
 )
/
CREATE TABLE tag2post
(
	ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	 post_id int NOT NULL,
	 tag_id int NOT NULL
 )
/