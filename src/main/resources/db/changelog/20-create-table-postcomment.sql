CREATE TABLE post_comments (
  id int NOT NULL AUTO_INCREMENT,
  text varchar(255) NOT NULL,
  time datetime NOT NULL,
  post_id int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (post_id) REFERENCES posts(ID)
  )
/