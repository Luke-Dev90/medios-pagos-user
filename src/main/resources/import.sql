INSERT INTO USERS (email,password,name,last_name , enabled) VALUES ('lucas@email.com','$2a$10$PaGcBtcJc43WZSs7jLhpvO853tfiCl0HvrbcYeG0ERF5IdnGNgMuW','lucas','chalela' , true);
INSERT INTO USERS (email,password,name,last_name, enabled) VALUES ('santiago@email.com','$2a$10$JMxx3nAnAhDLKZPAinVD5uPiwQ52oEqIvbM4OaS/hO2rBgq4zQOv.','santiago','chalela', true);

INSERT INTO ROLES (name) VALUES ("ROLE_USER");
INSERT INTO ROLES (name) VALUES ("ROLE_ADMIN");

INSERT INTO users_roles (user_id, role_id ) VALUES ( 1 , 1);
INSERT INTO users_roles (user_id, role_id ) VALUES ( 2 , 2);