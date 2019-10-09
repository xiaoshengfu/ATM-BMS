CREATE DATABASE atm_bms;
USE atm_bms;
CREATE TABLE `user` (
	`uId` VARCHAR(15) NOT NULL PRIMARY KEY,
	`name` VARCHAR(10) NOT NULL,
	`password` VARCHAR(50) NOT NULL DEFAULT 'e10adc3949ba59abbe56e057f20f883e',
	`remainingSum` DOUBLE DEFAULT '10000'
);
CREATE TABLE `trading_record` (
	`tId` VARCHAR(32) NOT NULL PRIMARY KEY,
	`uId` VARCHAR(15) NOT NULL,
	`relateuId` VARCHAR(15) NOT NULL,
	`operationData` TIMESTAMP NOT NULL,
	`businessType` VARCHAR(10) NOT NULL,
	`operationAmount` DOUBLE,
	CONSTRAINT FOREIGN KEY (`uId`) REFERENCES `user` (`uId`),
	CONSTRAINT FOREIGN KEY (`relateuId`) REFERENCES `user` (`uId`)	
)