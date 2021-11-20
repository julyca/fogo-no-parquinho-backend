CREATE DATABASE fogo_no_parquinho;
USE fogo_no_parquinho;

CREATE TABLE UserRoles (
    id          INT NOT NULL AUTO_INCREMENT,
    roleName    TEXT NOT NULL,
    PRIMARY KEY (id)
);

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


CREATE TABLE Subject (
    id              INT NOT NULL AUTO_INCREMENT,
    code            TEXT NOT NULL,
    name            TEXT NOT NULL,
    description     TEXT NOT NULL,
    creationTime    DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE UserSubjects (
    id              INT NOT NULL AUTO_INCREMENT,
    userId          INT,
    subjectId       INT,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES User(id),
    FOREIGN KEY (subjectId) REFERENCES Subject(id)
);


CREATE TABLE Review (
    id              INT NOT NULL AUTO_INCREMENT,
    score           INT NOT NULL,
    feedback        TEXT NOT NULL,
    creationTime    DATETIME,
    reviewerId      INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (reviewerId) REFERENCES User(id)
);


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

INSERT INTO UserRoles (roleName) VALUES ('aluno'),('professor'); 
SELECT * FROM UserRoles;

INSERT INTO User (username,password,fullName,code,creationTime,roleId) VALUES
('admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','Add Min in','admin',NOW(),2),
('DIO','0a37cc47dcf2b8ef3612e05ab5fed768343971dd649e97c33385cf6a0be72639','Kono DIO da!','354896',NOW(),1),
('kalc','558317a6ff37a69f526d1950c9a91f17b512c9bce2df3012521c464695526bae','Infiltrado da FEI','777777',NOW(),1),
('rangeru','9d6cddfd427c0c5c82a854ad1df90fb60ef609274e50f537b1bd5a2d16d2cba1','Rang','595485',NOW(),1),
('dono','3e1277efea165f3d88b887b56d0c802ba26a38a9037e35bf2976c31d4ee3cbf2','Dona','048657',NOW(),1),
('jorjao','80d50c336feed1be62feca328a8efd2ad4e69a1d30a4775c9fd3e64dfa7ad510','Jorj�o','5555555',NOW(),2),
('furlano','e7a90381498109efe5501ec479f31405f9ffde1ba403fd2e0a9921d4fdd037ee','Furlano','249248',NOW(),2),
('flexao','3b305631fd0d2060a0a53e611ff1f7d45fa8547fa663efaa56bddc69a40efdc4','Deus da Flexxx','258426',NOW(),2),
('lentidao','0488db4d4986ba43c513de26bc85d6505d0c8600322d34b945648374a23ccaee','5min em 2h','584268',NOW(),2),
('chorao','1cbe75749f79348fc0840344facf5d36b9f6d2dc954dc5a3fe6f772e4358cee0','Chor�o','248657',NOW(),1);
SELECT * FROM User;

INSERT INTO Subject (code,name,description,creationTime) VALUES
('ECM251', 'Linguagens de Programa��o I', 'magia de campones', NOW()),
('ECM225', 'Sistemas Operacionais', 'AULA DO FURLAN', NOW()),
('ECM253', 'Linguagens Formais, Aut�matos e Compiladores', '404', NOW()),
('ECM306', 'T�picos Avan�ados em Estruturas de Dados', 'A mimir', NOW()),
('ECM303', 'Sistemas de Controle', 'I dont know', NOW()),
('ECM304', 'Circuitos El�tricos', 'Eletroboom is god, also Serj�o is god', NOW());
SELECT * FROM Subject;

INSERT INTO Review (feedback,score,creationTime,reviewerId) VALUES
('odeio muito tudo isso, como esse professor quer que eu estude? Quem ele pensa que ele �? Tem como dar nota negativa nesse coisa?',1,NOW(),(SELECT id FROM `User` WHERE username LIKE 'chorao')),
('odeio muito tudo isso, como esse professor quer que eu estude? Quem ele pensa que ele �? Tem como dar nota negativa nesse coisa??',1,NOW(),(SELECT id FROM `User` WHERE username LIKE 'chorao')),
('odeio muito tudo isso, como esse professor quer que eu estude? Quem ele pensa que ele �? Tem como dar nota negativa nesse coisa???',1,NOW(),(SELECT id FROM `User` WHERE username LIKE 'chorao')),
('Come�ou ruim, mas hoje � uma das melhores aulas',8,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Capacitor goes Boom',7,NOW(),(SELECT id FROM `User` WHERE username LIKE 'kalc')),
('MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA',1,NOW(),(SELECT id FROM `User` WHERE username LIKE 'DIO')),
('Esse maluco chora de tudo, pela mor',3,NOW(),(SELECT id FROM `User` WHERE username LIKE 'kalc')),
('Nunca v� esse maluco',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'dono')),
('I SLEEP',3,NOW(),(SELECT id FROM `User` WHERE username LIKE 'kalc')),
('Cocaine is a hell of a drug',8,NOW(),(SELECT id FROM `User` WHERE username LIKE 'kalc')),
('Nunca conheci um maluco loro t�o foda',9,NOW(),(SELECT id FROM `User` WHERE username LIKE 'dono')),
('Porque esse chor�o quer tanto aula presencial se ele s� dorme na aula?',3,NOW(),(SELECT id FROM `User` WHERE username LIKE 'dono')),
('Eu n�o sei o que esse professor usa para ficar t�o animado as 7:40 da manh�, mas eu quero!',8,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Jorj�o � um deus vivo, que hom�o',10,NOW(),(SELECT id FROM `User` WHERE username LIKE 'dono')),
('Definitivamente umas das revela��es do ano, professor Furlano se superou para se adaptar ao novo ambiente online',8,NOW(),(SELECT id FROM `User` WHERE username LIKE 'dono')),
('Definitivamente umas das revela��es do ano, professor Furlano se superou para se adaptar ao novo ambiente online',8,NOW(),(SELECT id FROM `User` WHERE username LIKE 'dono')),
('Eu legitimamente n�o tenho ideia do que est� acontecendo...',1,NOW(),(SELECT id FROM `User` WHERE username LIKE 'dono')),
('Eu legitimamente n�o tenho ideia do que est� acontecendo...',1,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Eu sou a unica pessoa que est� do lado desse professor!',9,NOW(),(SELECT id FROM `User` WHERE username LIKE 'chorao')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru')),
('Redacted',5,NOW(),(SELECT id FROM `User` WHERE username LIKE 'rangeru'));
SELECT * FROM Review;

INSERT INTO UserSubjects (userId,subjectId) VALUES
((SELECT id FROM `User` WHERE username LIKE 'jorjao'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM304')),
((SELECT id FROM `User` WHERE username LIKE 'furlano'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM253')),
((SELECT id FROM `User` WHERE username LIKE 'furlano'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM225')),
((SELECT id FROM `User` WHERE username LIKE 'furlano'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM306')),
((SELECT id FROM `User` WHERE username LIKE 'dono'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM251'));
SELECT * FROM UserSubjects;

INSERT INTO UserReviews (reviewId,reviewedUserId) VALUES
((SELECT id FROM `Review` WHERE feedback LIKE 'Esse maluco chora de tudo, pela mor'),(SELECT id FROM `User` WHERE username LIKE 'chorao')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Nunca v� esse maluco'),(SELECT id FROM `User` WHERE username LIKE 'kalc')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Cocaine is a hell of a drug'),(SELECT id FROM `User` WHERE username LIKE 'dono')),
((SELECT id FROM `Review` WHERE feedback LIKE 'odeio muito tudo isso, como esse professor quer que eu estude? Quem ele pensa que ele �? Tem como dar nota negativa nesse coisa???'),(SELECT id FROM `User` WHERE username LIKE 'furlano')),
((SELECT id FROM `Review` WHERE feedback LIKE 'odeio muito tudo isso, como esse professor quer que eu estude? Quem ele pensa que ele �? Tem como dar nota negativa nesse coisa??'),(SELECT id FROM `User` WHERE username LIKE 'jorjao')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Nunca conheci um maluco loro t�o foda'),(SELECT id FROM `User` WHERE username LIKE 'DIO'))
((SELECT id FROM `Review` WHERE feedback LIKE 'Porque esse chor�o quer tanto aula presencial se ele s� dorme na aula?'),(SELECT id FROM `User` WHERE username LIKE 'chorao')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Eu n�o sei o que esse professor usa para ficar t�o animado as 7:40 da manh�, mas eu quero!'),(SELECT id FROM `User` WHERE username LIKE 'furlano'))
((SELECT id FROM `Review` WHERE feedback LIKE 'Jorj�o � um deus vivo, que hom�o'),(SELECT id FROM `User` WHERE username LIKE 'jorjao'));
SELECT * FROM UserReviews;

INSERT INTO SubjectReviews (reviewId,reviewedSubjectId) VALUES
((SELECT id FROM `Review` WHERE feedback LIKE 'odeio muito tudo isso, como esse professor quer que eu estude? Quem ele pensa que ele �? Tem como dar nota negativa nesse coisa?'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM251')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Come�ou ruim, mas hoje � uma das melhores aulas'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM253')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Capacitor goes Boom'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM304')),
((SELECT id FROM `Review` WHERE feedback LIKE 'MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA MUDA'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM303')),
((SELECT id FROM `Review` WHERE feedback LIKE 'I SLEEP'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM306')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Definitivamente umas das revela��es do ano, professor Furlano se superou para se adaptar ao novo ambiente online'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM225')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Definitivamente umas das revela��es do ano, professor Furlano se superou para se adaptar ao novo ambiente online'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM253')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Eu legitimamente n�o tenho ideia do que est� acontecendo...'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM303')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Eu legitimamente n�o tenho ideia do que est� acontecendo...'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM303')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Eu sou a unica pessoa que est� do lado desse professor!'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM303')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM251')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM253')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM253')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM251')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM251')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM304')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM304')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM304')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM225')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM225')),
((SELECT id FROM `Review` WHERE feedback LIKE 'Redacted'),(SELECT id FROM `Subject` WHERE code LIKE 'ECM225'));
SELECT * FROM SubjectReviews;
