ALTER TABLE posts
ADD user_id int
/
ALTER TABLE posts
ADD FOREIGN KEY (user_id) REFERENCES USERS(ID)
/