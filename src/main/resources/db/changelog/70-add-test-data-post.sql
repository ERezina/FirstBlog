INSERT INTO posts(date,is_active,moderation_status,text_text,title,view_count,moderator_id,user_id)
VALUES (SYSDATE(), 1, 'ACCEPTED','текс текст текст','Заголовок',1,1,2  )
/
INSERT INTO posts(date,is_active,moderation_status,text_text,title,view_count,moderator_id,user_id)
VALUES (SYSDATE(), 0, 'NEW','текс2 текст2 текст2','Заголовок2',3,1,3  )
/
INSERT INTO posts(date,is_active,moderation_status,text_text,title,view_count,moderator_id,user_id)
VALUES (SYSDATE(), 1, 'ACCEPTED','<head> да да да</head> <tag> текст3</tag> текст3 текст3','Заголовок3',10,1,2  )
/

