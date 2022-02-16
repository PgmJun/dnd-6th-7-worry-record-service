create table USER (
userId bigint generated by default as identity,
username varchar(255) NOT NULL,
email varchar(100) ,
role varchar(100)  NOT NULL,
kakaoId varchar(100)  NOT NULL,
imgUrl varchar(100)  NOT NULL,
refreshToken varchar(1000),
primary key(userId)
);

INSERT INTO CATEGORY VALUES (1,'CAREER');
INSERT INTO CATEGORY VALUES (2,'RELATIONSHIP');
INSERT INTO CATEGORY VALUES (3,'COMPANY');
INSERT INTO CATEGORY VALUES (4,'ECONOMY');
INSERT INTO CATEGORY VALUES (5,'STUDY');
INSERT INTO CATEGORY VALUES (6,'FAMILY');
INSERT INTO CATEGORY VALUES (7,'HEALTH');