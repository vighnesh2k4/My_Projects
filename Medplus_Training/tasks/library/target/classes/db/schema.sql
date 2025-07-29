CREATE TABLE IF NOT EXISTS books(
bookid int primary key auto_increment,
title varchar(255) not null,
author varchar(255) not null,
category varchar(100) not null,
status char(1) not null check (status in ('A','I')),
availability char(1) not null check (availability in ('A','I'))
);
CREATE TABLE IF NOT EXISTS members (
    memberid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mobile BIGINT NOT NULL UNIQUE,
    gender CHAR(1) NOT NULL CHECK (Gender IN ('M', 'F')),
   	address VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS issue_records (
    issueid INT PRIMARY KEY AUTO_INCREMENT,
    bookid INT NOT NULL,
    memberid INT NOT NULL,
    status CHAR(1) NOT NULL CHECK (Status IN ('I', 'R')),
    issuedate DATE NOT NULL,
    returndate DATE,
    FOREIGN KEY (bookid) REFERENCES books(bookid),
    FOREIGN KEY (memberid) REFERENCES members(memberid)
);