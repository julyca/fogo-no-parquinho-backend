CREATE DATABASE fogo_no_parquinho;
USE fogo_no_parquinho;

CREATE TABLE UserRoles (
    id          INT NOT NULL AUTO_INCREMENT,
    roleName    TEXT NOT NULL,
    PRIMARY KEY (id)
);
INSERT INTO UserRoles (roleName) VALUES ('aluno'); 
SELECT * FROM UserRoles;

CREATE TABLE User (
    id              INT NOT NULL AUTO_INCREMENT,
    username        TEXT NOT NULL,
    password        TEXT NOT NULL,
    fullName        TEXT NOT NULL,
    code            TEXT NOT NULL,
    creationTime    DATETIME,
    roleId          INT,
    PRIMARY KEY (id),
    FOREIGN KEY (roleId) REFERENCES UserRoles(id)
);

INSERT INTO User (username,password,fullName,code,creationTime,roleId) VALUES ('admin','odeiomuitotudoisso','Add Min in','admin',NOW(),1)
SELECT * FROM User;

CREATE TABLE Subject (
    id              INT NOT NULL AUTO_INCREMENT,
    code            TEXT NOT NULL,
    name            TEXT NOT NULL,
    description     TEXT NOT NULL,
    creationTime    DATETIME,
    PRIMARY KEY (id)
);
INSERT INTO Subject (code,name,description,creationTime) VALUES ('YYYYYYYYY', 'linguagem', 'magia de campones', NOW()); 
SELECT * FROM Subject;

CREATE TABLE UserSubjects (
    id              INT NOT NULL AUTO_INCREMENT,
    userId          INT,
    subjectId       INT,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES User(id),
    FOREIGN KEY (subjectId) REFERENCES Subject(id)
);
INSERT INTO UserSubjects (userId,subjectId) VALUES (1,1); 
SELECT * FROM UserSubjects;

CREATE TABLE Review (
    id              INT NOT NULL AUTO_INCREMENT,
    score           INT NOT NULL,
    feedback        TEXT NOT NULL,
    creationTime    DATETIME,
    reviewerId      INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (reviewerId) REFERENCES User(id)
);
INSERT INTO Review (feedback,score,creationTime,reviewerId) VALUES ('odei omuito tudo isso',3,NOW(),1)
SELECT * FROM Review;

CREATE TABLE UserReviews (
    id                  INT NOT NULL AUTO_INCREMENT,
    reviewId          	INT NOT NULL,
    reviewedUserId      INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (reviewId) REFERENCES Review(id),
    FOREIGN KEY (reviewedUserId) REFERENCES User(id)
);

CREATE TABLE SubjectReviews (
    id                  INT NOT NULL AUTO_INCREMENT,
    reviewId            INT NOT NULL,
    reviewedSubjectId   INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (reviewId) REFERENCES Review(id),
    FOREIGN KEY (reviewedSubjectId) REFERENCES Subject(id)
);