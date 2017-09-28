/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : wyq_db

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-09-28 11:58:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for keyword
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(1024) DEFAULT NULL,
  `lastprocesstime` bigint(20) DEFAULT NULL,
  `memo` bigint(20) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of keyword
-- ----------------------------
INSERT INTO `keyword` VALUES ('1', '小米', '1506547727662', null, '0');
INSERT INTO `keyword` VALUES ('2', '苹果', '1505995915154', null, '0');
INSERT INTO `keyword` VALUES ('3', '乐视', '1506511759378', null, '0');
INSERT INTO `keyword` VALUES ('4', '华为', '1505995918206', null, '0');
INSERT INTO `keyword` VALUES ('5', '魅族', '1506547727740', null, '0');
INSERT INTO `keyword` VALUES ('6', 'OPPO', '1505995933383', null, '0');
INSERT INTO `keyword` VALUES ('7', '绝地求生', '1505995930348', null, '0');
INSERT INTO `keyword` VALUES ('8', '锤子', '1505995927305', null, '0');
INSERT INTO `keyword` VALUES ('9', 'Vivo', '1506511759125', null, '0');
INSERT INTO `keyword` VALUES ('10', '吃鸡', '1506547727694', null, '0');
INSERT INTO `keyword` VALUES ('11', '爱奇艺', '1505995921240', null, '0');
INSERT INTO `keyword` VALUES ('12', '腾讯视频', '1506533204418', null, '0');
INSERT INTO `keyword` VALUES ('13', '优酷', '1506547727709', null, '0');
INSERT INTO `keyword` VALUES ('14', '王者荣耀', '1506533204403', null, '0');
INSERT INTO `keyword` VALUES ('15', '国庆', '1505995924279', null, '0');
INSERT INTO `keyword` VALUES ('16', '南京天气', '1506533204372', null, '0');
INSERT INTO `keyword` VALUES ('17', '南京建邺', '1506533204340', null, '0');
