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

INSERT INTO question (questionText, choiceA, choiceB, choiceC, answer) VALUES
('Which of the following is a measure of the dispersion of a set of data points in a statistical distribution?', 'Standard Deviation', 'Mean', 'Median', 'Standard Deviation'),
('Which type of financial instrument represents ownership in a corporation and entitles the holder to a share of the company\'s profits?', 'Bond', 'Option', 'Stock', 'Stock'),
('What term is used to describe the process of converting inputs into outputs in order to create value?', 'Investing', 'Trading', 'Production', 'Production'),
('Which economic indicator measures the total value of goods and services produced within a country during a specific time period?', 'GDP (Gross Domestic Product)', 'CPI (Consumer Price Index)', 'PPI (Producer Price Index)', 'GDP (Gross Domestic Product)'),
('What is the term for the amount of money charged by a lender to a borrower for the use of assets?', 'Interest', 'Dividend', 'Yield', 'Interest'),
('Which financial ratio measures a company\'s ability to meet its short-term obligations with its most liquid assets?', 'Debt-to-Equity Ratio', 'Current Ratio', 'Profit Margin', 'Current Ratio'),
('What is the term for a sudden and significant decline in the value of a market?', 'Correction', 'Recession', 'Crash', 'Crash'),
('Which financial market is known for trading financial instruments with maturities of one year or less?', 'Money Market', 'Capital Market', 'Derivatives Market', 'Money Market'),
('What type of financial institution accepts deposits from the public and creates credit?', 'Credit Union', 'Hedge Fund', 'Bank', 'Bank'),
('What term is used to describe the process of spreading investments across different assets to reduce risk?', 'Diversification', 'Leverage', 'Arbitrage', 'Diversification'),
('Which of the following is an indicator of the average level of prices for goods and services commonly purchased by households in a country?', 'PPI (Producer Price Index)', 'CPI (Consumer Price Index)', 'GDP (Gross Domestic Product)', 'CPI (Consumer Price Index)'),
('What type of financial instrument gives the holder the right, but not the obligation, to buy or sell an asset at a predetermined price before or at a specified date?', 'Stock', 'Option', 'Bond', 'Option'),
('Which financial statement provides a snapshot of a company\'s financial position at a specific point in time?', 'Income Statement', 'Cash Flow Statement', 'Balance Sheet', 'Balance Sheet'),
('What term is used to describe the process of borrowing money to invest and potentially amplify returns?', 'Diversification', 'Leverage', 'Arbitrage', 'Leverage'),
('Which type of risk arises from factors such as changes in government regulations, natural disasters, or geopolitical events?', 'Systemic Risk', 'Credit Risk', 'Operational Risk', 'Systemic Risk'),
('What is the term for the total return on an investment expressed as a percentage of the original investment over a specific time period?', 'Yield', 'Interest', 'Dividend', 'Yield'),
('Which of the following is a financial metric that measures a company\'s profitability by dividing its net income by its revenue?', 'Current Ratio', 'Profit Margin', 'Debt-to-Equity Ratio', 'Profit Margin'),
('What type of financial market facilitates the buying and selling of financial instruments such as stocks, bonds, and currencies?', 'Derivatives Market', 'Money Market', 'Capital Market', 'Capital Market'),
('What term is used to describe the process of buying and selling financial instruments in the financial markets?', 'Investing', 'Trading', 'Production', 'Trading');
