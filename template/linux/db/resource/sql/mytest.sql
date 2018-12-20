/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 10.2.14-MariaDB-log : Database - mytest
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mytest` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mytest`;

/*Table structure for table `system_user` */

DROP TABLE IF EXISTS `system_user`;

CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `password` varchar(32) DEFAULT '123456',
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `system_user` */


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
