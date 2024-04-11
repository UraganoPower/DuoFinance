drop database if exists trivia;
create database trivia;
use trivia;

create table role (
	roleId int primary key auto_increment,
    type varchar(100) not null
);

create table user (
	userId int primary key auto_increment,
    username varchar(100) not null,
    email varchar(100) not null,
    password varchar(100) not null,
    roleId int,

    foreign key (roleId) references role(roleId)
);

create table game (
	gameId int primary key auto_increment,
    score int not null,
    userId int,

    foreign key (userId) references user(userId)
);

create table question (
	questionId int primary key auto_increment,
    questionText varchar(2000) not null, -- what is th
    choiceA varchar(2000) not null,
    choiceB varchar(2000) not null,
    choiceC varchar(2000) not null,
    answer varchar(2000) not null
);

create table game_question (
	gameId int,
    questionId int,

    foreign key (gameId) references game(gameId),
    foreign key (questionId) references question(questionId)
);


-- inserting roles inside the role table
insert into role (type) values ("admin");
insert into role (type) values ("basic");

-- inserting users with admin as a role
insert into user (username, email, password, roleId) values ("admin", "admin@gmail.com", "password", 1);

-- inserting users with basic as a role
insert into user (username, email, password, roleId) values ("basic", "basic@gmail.com", "password", 2);

-- inserting questions
INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is the term for the risk that an investment's value will change due to a change in the absolute level in any other interest rate relationship?",
    "Market Risk",
    "Credit Risk",
    "Interest Rate Risk",
    "Interest Rate Risk"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is the term for the general increase in prices and fall in the purchasing value of money?",
    "Deflation",
    "Inflation",
    "Stagflation",
    "Inflation"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is a debt security, similar to an IOU, where the issuer owes the holders a debt and is obliged to pay them interest or to repay the principal at a later date?",
    "Stock",
    "Mutual Fund",
    "Bond",
    "Bond"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is the marketplace where buyers and sellers trade shares of public companies?",
    "Forex Market",
    "Commodity Market",
    "Stock Market",
    "Stock Market"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is the distribution of a portion of a company's earnings, decided by the board of directors, to a class of its shareholders?",
    "Capital Gain",
    "Dividend",
    "Interest",
    "Dividend"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is a type of investment vehicle consisting of a portfolio of stocks, bonds, or other securities?",
    "Stock",
    "Mutual Fund",
    "Bond",
    "Mutual Fund"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is the process by which a private company can go public by sale of its stocks to general public?",
    "Merger",
    "Acquisition",
    "Initial Public Offering",
    "Initial Public Offering"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "Which financial statement reports a company's assets, liabilities, and shareholders' equity at a specific point in time?",
    "Income Statement",
    "Balance Sheet",
    "Cash Flow Statement",
    "Balance Sheet"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is a one-year period that companies and governments use for financial reporting and budgeting?",
    "Calendar Year",
    "Fiscal Year",
    "Leap Year",
    "Fiscal Year"
);

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer)
VALUES (
    "What is a digital or virtual form of currency that uses cryptography for security?",
    "Forex",
    "Cryptocurrency",
    "Commodity",
    "Cryptocurrency"
);