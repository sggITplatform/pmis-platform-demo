/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50016
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50016
File Encoding         : 65001

Date: 2014-10-16 23:20:42
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `activity_permission_tab`
-- ----------------------------
DROP TABLE IF EXISTS `activity_permission_tab`;
CREATE TABLE `activity_permission_tab` (
  `ID` decimal(19,0) NOT NULL,
  `ACTIVITY_ID` varchar(255) default NULL,
  `ASSIGNED_USER` varchar(255) default NULL,
  `GRANTED_GROUPS` varchar(255) default NULL,
  `GRANTED_USERS` varchar(255) default NULL,
  `PROCESS_DEF_ID` varchar(255) default NULL,
  `OP_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity_permission_tab
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(10) default NULL,
  `password` varchar(10) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `vacation_request_tab`
-- ----------------------------
DROP TABLE IF EXISTS `vacation_request_tab`;
CREATE TABLE `vacation_request_tab` (
  `MOTIVATION` text,
  `DAYS` bigint(20) default NULL,
  `PROCESSID` varchar(250) default NULL,
  `ID` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vacation_request_tab
-- ----------------------------

-- ----------------------------
-- Procedure structure for `fun1`
-- ----------------------------
DROP PROCEDURE IF EXISTS `fun1`;
DELIMITER ;;
CREATE PROCEDURE `fun1`(in name varchar(20), in age bigint, out Result bigint)
BEGIN
	insert into person (name,age)
 		values (name, age);
select Result=last_insert_id();
END
;;
DELIMITER ;
