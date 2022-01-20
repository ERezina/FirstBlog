ALTER TABLE post_comments
ADD user_id int
/
ALTER TABLE post_comments
ADD FOREIGN KEY (user_id) REFERENCES users(ID)
/