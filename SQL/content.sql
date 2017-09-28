/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : wyq_db

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2017-09-28 11:58:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `attribute` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `author` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `keyword` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `link` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `similar` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `source` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  `text` varchar(4096) COLLATE utf8mb4_bin DEFAULT NULL,
  `time` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52731 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
