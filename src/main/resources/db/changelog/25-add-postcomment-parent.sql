ALTER TABLE post_comments
ADD parent int
/
ALTER TABLE post_comments
ADD FOREIGN KEY (parent) REFERENCES post_comments(ID)
/