/*
Navicat MySQL Data Transfer

Source Server         : gesac
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : check

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-02-23 16:14:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stuNumber` varchar(100) NOT NULL,
  `planName` varchar(40) NOT NULL,
  `planType` varchar(40) NOT NULL,
  `upName` varchar(40) DEFAULT NULL,
  `upTime` date DEFAULT NULL,
  `planState` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES ('1', 'ge14444', '7月份', '月度', 'ge14476', '2019-02-22', '已上传');
INSERT INTO `plan` VALUES ('2', 'ge14474', '8月份', '月度', 'ge14476', '2019-02-22', '已上传');
INSERT INTO `plan` VALUES ('5', 'ge14444', '半年计划', '年度', null, null, '删除');
INSERT INTO `plan` VALUES ('6', 'ge14444', '半年计划', '年度', 'ge14476', '2019-02-23', '已上传');
INSERT INTO `plan` VALUES ('7', 'ge14474', '7月计划', '月度', 'ge14476', '2019-02-22', '已上传');
INSERT INTO `plan` VALUES ('8', 'ge14474', '8月计划', '月度', 'ge14476', '2019-02-22', '已上传');
INSERT INTO `plan` VALUES ('9', 'ge14444', '9月计划', '月度', 'ge14476', '2019-02-22', '已上传');
INSERT INTO `plan` VALUES ('10', 'ge14474', '10月计划', '月度', null, null, '未上传');
INSERT INTO `plan` VALUES ('11', 'ge14444', '10月计划', '月度', 'ge14476', '2019-02-23', '已上传');
INSERT INTO `plan` VALUES ('12', 'ge14444', '8月计划', '月度', 'ge14476', '2019-02-23', '已上传');
INSERT INTO `plan` VALUES ('17', 'ge14474', '2月培训计划', '月度', null, null, '未上传');
INSERT INTO `plan` VALUES ('18', 'ge14444', '2月培训计划', '月度', null, null, '未上传');
INSERT INTO `plan` VALUES ('19', 'ge14430', '2月培训计划', '月度', null, null, '未上传');
INSERT INTO `plan` VALUES ('20', 'ge14445', '2月培训计划', '月度', null, null, '未上传');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stuNumber` varchar(20) NOT NULL,
  `reportName` varchar(40) NOT NULL,
  `reportType` varchar(10) NOT NULL,
  `reportState` varchar(10) NOT NULL,
  `upTime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES ('1', 'ge14444', '半年总结', '年度', '已上传', '2019-02-23');
INSERT INTO `report` VALUES ('2', 'ge14444', '7月总结', '月度', '删除', null);
INSERT INTO `report` VALUES ('3', 'ge14444', '7月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('4', 'ge14474', '7月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('5', 'ge14430', '7月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('6', 'ge14445', '7月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('7', 'ge14474', '8月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('8', 'ge14444', '8月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('9', 'ge14444', '9月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('10', 'ge14444', '10月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('11', 'ge14444', '11月总结', '月度', '已上传', '2019-02-22');
INSERT INTO `report` VALUES ('12', 'ge14444', '12月总结', '月度', '已上传', '2019-02-23');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stuNumber` varchar(100) NOT NULL,
  `tutorNumber` varchar(40) NOT NULL,
  `createTime` date DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  `state` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stuNumber` (`stuNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', 'ge14474', 'ge14475', '2019-02-20', '2019-02-20', '有效');
INSERT INTO `teacher` VALUES ('2', 'ge14444', 'ge14423', '2019-02-21', '2019-02-21', '有效');
INSERT INTO `teacher` VALUES ('3', 'ge14430', 'ge14431', '2019-02-21', '2019-02-21', '有效');
INSERT INTO `teacher` VALUES ('4', 'ge14445', 'ge14442', '2019-02-21', '2019-02-21', '有效');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `geNumber` varchar(100) NOT NULL,
  `geName` varchar(100) NOT NULL,
  `password` varchar(40) NOT NULL,
  `role` varchar(40) NOT NULL COMMENT '角色：admin，user，visitor',
  `authority` varchar(40) NOT NULL COMMENT '角色：admin，user，visitor',
  `userState` varchar(100) NOT NULL COMMENT '用户状态：0有效，1无效',
  `createTime` date DEFAULT NULL,
  `updateTime` date DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `geNumber` (`geNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ge14476', 'lll', 'ge14476', 'admin', 'admin', '有效', '2019-02-19', '2019-02-20', '18740084869', null);
INSERT INTO `user` VALUES ('2', 'ge14475', 'aaa', 'ge14475', 'tutor', 'tutor', '有效', '2019-02-12', '2019-02-20', '18650118062', null);
INSERT INTO `user` VALUES ('3', 'ge14474', 'adsa', 'ge14474', 'student', 'student', '有效', '2019-02-10', '2019-02-20', null, null);
INSERT INTO `user` VALUES ('9', 'ge14444', 'sssss', 'ge14444', 'student', 'student', '有效', '2019-02-20', '2019-02-20', '', '');
INSERT INTO `user` VALUES ('10', 'ge14470', 'uuuuuuu', 'ge14470', 'student', 'student', '删除', '2019-02-20', '2019-02-20', '18940084869', 'liu.wenxiang@cxtc.com');
INSERT INTO `user` VALUES ('11', 'ge12212', 'qwqweqew', 'ge12212', 'tutor', 'tutor', '删除', '2019-02-20', '2019-02-20', '', '');
INSERT INTO `user` VALUES ('15', 'ge14430', 'gggg', 'ge14430', 'student', 'student', '有效', '2019-02-21', '2019-02-21', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('16', 'ge14445', 'bbb', 'ge14445', 'student', 'student', '有效', '2019-02-21', '2019-02-21', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('17', 'ge14423', 'ccc', 'ge14423', 'tutor', 'tutor', '有效', '2019-02-21', '2019-02-21', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('18', 'ge14431', 'ddd', 'ge14431', 'tutor', 'tutor', '有效', '2019-02-21', '2019-02-21', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('19', 'ge14442', 'fff', 'ge14442', 'tutor', 'tutor', '删除', '2019-02-21', '2019-02-22', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('21', 'ge14428', 'aaa', '123456', 'tutor', 'tutor', '有效', '2019-02-21', '2019-02-21', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('24', 'ge14411', 'aaa', '123456', 'tutor', 'tutor', '删除', '2019-02-22', '2019-02-22', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('25', 'ge14414', 'aaa', '123456', 'tutor', 'tutor', '删除', '2019-02-22', '2019-02-22', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('26', 'ge14141', 'aaa', '123456', 'tutor', 'tutor', '删除', '2019-02-22', '2019-02-22', '18940084869', 'asdasd ');
INSERT INTO `user` VALUES ('27', 'ge14149', 'aaa', '123456', 'tutor', 'tutor', '删除', '2019-02-22', '2019-02-22', '18940084869', 'asdasd ');
