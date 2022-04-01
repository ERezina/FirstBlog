CREATE TABLE post_votes (
  id int NOT NULL AUTO_INCREMENT,
  time datetime NOT NULL,
  post_id int NOT NULL,
  user_id int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (post_id) REFERENCES posts(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
 )
/
