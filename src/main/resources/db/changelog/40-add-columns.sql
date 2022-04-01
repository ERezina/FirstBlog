ALTER TABLE post_votes
ADD value_votes int NOT NULL
/
CREATE TABLE tags
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name varchar(255) NOT NULL
 )
/
CREATE TABLE tag2post
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	 post_id int NOT NULL,
	 tag_id int NOT NULL
 )
/