CREATE TABLE posts (
  id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  date datetime(6) NOT NULL,
  is_active int NOT NULL,
  moderation_status enum('NEW','ACCEPTED','DECLINED') NOT NULL,
  text varchar(255) NOT NULL,
  title varchar(255) NOT NULL,
  view_count int NOT NULL,
  moderator_id int,
  FOREIGN KEY (moderator_id) REFERENCES users(id)
)
/