INSERT INTO post_comments(parent,post_id,text,time,user_id)
VALUES (null,1, 'всё хорошо',sysdate(),1  )
/
INSERT INTO post_comments(parent,post_id,text,time,user_id)
VALUES (null,1, 'всё НЕ хорошо',sysdate(),2  )
/
INSERT INTO post_comments(parent,post_id,text,time,user_id)
VALUES (null,2, 'всё хорошо хорошо хорошо',sysdate(),1  )
/
INSERT INTO post_comments(parent,post_id,text,time,user_id)
VALUES (3,2, 'всё всё всё всё',sysdate(),3  )
/
