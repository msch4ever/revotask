--/*
--DROP TABLE IF EXISTS USER;
--CREATE TABLE USER
--(
--  USERID    BIGINT PRIMARY KEY NOT NULL,
--  USERNAME  VARCHAR(255),
--  ACCOUNTID BIGINT,
--  CONSTRAINT ACCOUNT_FK_CONSTRAINT FOREIGN KEY (ACCOUNTID) REFERENCES ACCOUNT (ACCOUNTID)
--);
--
--DROP TABLE IF EXISTS ACCOUNT;
--CREATE TABLE ACCOUNT
--(
--  ACCOUNTID   BIGINT PRIMARY KEY NOT NULL,
--  ACCOUNTNAME VARCHAR(255),
--  BALANCE     DECIMAL(19, 2)
--);
--
--DROP TABLE IF EXISTS TRANSFER;
--CREATE TABLE TRANSFER
--(
--  TRANSFERID               BIGINT PRIMARY KEY NOT NULL,
--  SOURCEACCOUNTID          BIGINT             NOT NULL,
--  DESTINATIONACCOUNTID     BIGINT             NOT NULL,
--  AMOUNT                   DECIMAL(19, 2),
--  SOURCESTARTBALANCE       DECIMAL(19, 2),
--  SOURCERESULTBALANCE      DECIMAL(19, 2),
--  DESTINATIONSTARTBALANCE  DECIMAL(19, 2),
--  DESTINATIONRESULTBALANCE DECIMAL(19, 2),
--  ENTRYTIME                TIMESTAMP
--);
--
--DROP TABLE IF EXISTS LEDGER;
--CREATE TABLE LEDGER
--(
--  LEDGERID     BIGINT PRIMARY KEY NOT NULL,
--  ACCOUNTID    BIGINT             NOT NULL,
--  AMOUNT       DECIMAL(19, 2),
--  STARTBALANCE DECIMAL(19, 2),
--  ENDBALANCE   DECIMAL(19, 2),
--  EVENTTYPE    VARCHAR(255),
--  ENTRYTIME    TIMESTAMP
--);
--
--*/