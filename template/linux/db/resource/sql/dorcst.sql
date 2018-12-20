/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 10.2.14-MariaDB-log : Database - dorcst
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dorcst` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dorcst`;

/*Table structure for table `cron_config` */

-- creat by wenchao

DROP TABLE IF EXISTS `cron_config`;

CREATE TABLE `cron_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `crons` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `cron_config` */

/*Table structure for table `dst_consultation` */

DROP TABLE IF EXISTS `dst_consultation`;

CREATE TABLE `dst_consultation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `commander` varchar(32) DEFAULT NULL COMMENT '主持人',
  `cons_status` int(11) DEFAULT NULL COMMENT '会诊状态',
  `dept_name` varchar(64) DEFAULT NULL COMMENT '科室名称',
  `dept_no` varchar(32) DEFAULT NULL COMMENT '科室编码',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `hosp_name` varchar(256) DEFAULT NULL COMMENT '医院名称',
  `hosp_no` varchar(32) DEFAULT NULL COMMENT '医院编码',
  `patient_id` varchar(32) NOT NULL COMMENT '患者',
  `patient_name` varchar(64) NOT NULL COMMENT '患者姓名',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `doctor_no` varchar(32) DEFAULT NULL COMMENT '员工号',
  `join_url` varchar(1024) DEFAULT NULL COMMENT '加入地址',
  `meeting_id` varchar(128) DEFAULT NULL COMMENT '会议ID',
  `meeting_number` varchar(128) DEFAULT NULL COMMENT '会议编号',
  `password` varchar(32) DEFAULT NULL COMMENT '会议密码',
  `start_url` varchar(1024) DEFAULT NULL COMMENT '发起地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_consultation` */

/*Table structure for table `dst_dept_info` */

DROP TABLE IF EXISTS `dst_dept_info`;

CREATE TABLE `dst_dept_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `dept_name` varchar(32) NOT NULL COMMENT '科室名称',
  `dept_no` varchar(32) NOT NULL COMMENT '科室编号',
  `parent_no` varchar(32) DEFAULT NULL COMMENT '上级科室编号',
  `total_bed_num` varchar(32) DEFAULT NULL COMMENT '床位总数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_dept_info` */

/*Table structure for table `dst_diagnosis` */

DROP TABLE IF EXISTS `dst_diagnosis`;

CREATE TABLE `dst_diagnosis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `diagnosis_desc` varchar(512) DEFAULT NULL COMMENT '诊断描述',
  `diagnosis_time` datetime DEFAULT NULL COMMENT '诊断日期',
  `diagnosis_type` varchar(32) DEFAULT NULL COMMENT '诊断类型',
  `icd_no` varchar(32) DEFAULT NULL COMMENT 'ICD编码',
  `origin_key` varchar(128) NOT NULL COMMENT '原有主键',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=60490 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_diagnosis` */

/*Table structure for table `dst_emr` */

DROP TABLE IF EXISTS `dst_emr`;

CREATE TABLE `dst_emr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator_id` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `creator_name` varchar(32) DEFAULT NULL COMMENT '创建者姓名',
  `emr_id` varchar(128) NOT NULL COMMENT '电子病历ID',
  `emr_name` varchar(256) DEFAULT NULL COMMENT '节点名字',
  `file_visit_type` tinyint(1) DEFAULT NULL COMMENT '文档属性',
  `lastmodify_id` varchar(32) DEFAULT NULL COMMENT '最后修改者id',
  `lastmodify_name` varchar(32) DEFAULT NULL COMMENT '最后修改者姓名',
  `lastmodify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父ID',
  `parent_name` varchar(32) DEFAULT NULL COMMENT '父名称',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `visit_id` varchar(32) NOT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_emr_id` (`emr_id`) USING BTREE,
  KEY `idx_emr_patient_id` (`patient_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=123478 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_emr` */

/*Table structure for table `dst_emr_content` */

DROP TABLE IF EXISTS `dst_emr_content`;

CREATE TABLE `dst_emr_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `emr_content` longtext DEFAULT NULL COMMENT '电子病历文本内容',
  `emr_id` varchar(128) NOT NULL COMMENT 'ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_emr_id` (`emr_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=122761 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_emr_content` */

/*Table structure for table `dst_equipment` */

DROP TABLE IF EXISTS `dst_equipment`;

CREATE TABLE `dst_equipment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `equipment_channel` varchar(32) DEFAULT NULL COMMENT '设备通道',
  `equipment_ip` varchar(32) DEFAULT NULL COMMENT '设备IP',
  `equipment_name` varchar(32) NOT NULL COMMENT '设备名字',
  `equipment_no` varchar(32) DEFAULT NULL COMMENT '设备编号',
  `equipment_type` varchar(16) DEFAULT NULL COMMENT '设备类型',
  `ismove` varchar(32) DEFAULT NULL COMMENT '是否移动',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父ID',
  `room_id` varchar(32) NOT NULL COMMENT '手术室ID',
  `video_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_equipment` */

/*Table structure for table `dst_exam` */

DROP TABLE IF EXISTS `dst_exam`;

CREATE TABLE `dst_exam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `exam_deptid` varchar(32) DEFAULT NULL COMMENT '检查科室编码',
  `exam_deptname` varchar(32) DEFAULT NULL COMMENT '检查科室名称',
  `exam_desc` text DEFAULT NULL COMMENT '检查结论 ',
  `exam_id` varchar(128) NOT NULL COMMENT '检查ID',
  `exam_item` varchar(512) NOT NULL COMMENT '检查项目',
  `exam_method` varchar(128) DEFAULT NULL COMMENT '检查方法',
  `exam_modality` varchar(32) DEFAULT NULL COMMENT '检查类别',
  `exam_part` varchar(32) DEFAULT NULL COMMENT '检查部位名称',
  `exam_reportor` varchar(32) DEFAULT NULL COMMENT '报告医生姓名',
  `exam_reporttime` datetime DEFAULT NULL COMMENT '检查报告时间',
  `exam_resultcode` varchar(32) NOT NULL COMMENT '检查结果代码   ',
  `exam_time` datetime NOT NULL COMMENT '检查时间',
  `exec_doctorname` varchar(32) DEFAULT NULL COMMENT '执行医生姓名',
  `image_desc` text DEFAULT NULL COMMENT '影像所见/描述',
  `origin_key` varchar(128) DEFAULT NULL COMMENT '原有主键',
  `path_type` varchar(32) DEFAULT NULL COMMENT '路径类型',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `sub_deptid` varchar(32) DEFAULT NULL COMMENT '送检科室编码',
  `sub_deptname` varchar(32) DEFAULT NULL COMMENT '送检科室名称',
  `sub_doctorname` varchar(32) DEFAULT NULL COMMENT '送检医生姓名',
  `sub_time` datetime DEFAULT NULL COMMENT '送检时间',
  `verify_doctorname` varchar(32) DEFAULT NULL COMMENT '审核医生',
  `verify_time` datetime DEFAULT NULL COMMENT '审核时间',
  `visit_id` varchar(32) NOT NULL COMMENT '住院次数',
  `exam_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_origin_key` (`origin_key`) USING BTREE,
  KEY `idx_exam_id` (`exam_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=40013 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_exam` */

/*Table structure for table `dst_exam_imagepath` */

DROP TABLE IF EXISTS `dst_exam_imagepath`;

CREATE TABLE `dst_exam_imagepath` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `exam_id` varchar(32) NOT NULL COMMENT '检查ID',
  `image_id` varchar(32) DEFAULT NULL COMMENT '图像ID',
  `image_path` varchar(256) NOT NULL COMMENT '图像路径',
  `seriesu_id` varchar(256) DEFAULT NULL COMMENT '序列ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=26402 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_exam_imagepath` */

/*Table structure for table `dst_ftp` */

DROP TABLE IF EXISTS `dst_ftp`;

CREATE TABLE `dst_ftp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `ftp_dir` varchar(256) DEFAULT NULL COMMENT 'FTP根目录',
  `ftp_password` varchar(32) DEFAULT NULL COMMENT 'FTP密码',
  `ftp_username` varchar(32) DEFAULT NULL COMMENT 'FTP账号',
  `room_id` varchar(32) NOT NULL COMMENT '手术室ID',
  `ftp_address` varchar(256) DEFAULT NULL COMMENT 'FTPip地址',
  `ftp_port` varchar(256) DEFAULT NULL COMMENT 'FTP端口',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_ftp` */

/*Table structure for table `dst_his_record` */

DROP TABLE IF EXISTS `dst_his_record`;

CREATE TABLE `dst_his_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `doctor_no` varchar(32) NOT NULL COMMENT '医生',
  `his_no` varchar(32) DEFAULT NULL COMMENT '浏览目标',
  `his_type` varchar(32) NOT NULL COMMENT '浏览类型',
  `view_time` datetime DEFAULT NULL COMMENT '浏览时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=580 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_his_record` */

/*Table structure for table `dst_hospital_info` */

DROP TABLE IF EXISTS `dst_hospital_info`;

CREATE TABLE `dst_hospital_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL COMMENT '城市',
  `country` varchar(32) DEFAULT NULL COMMENT '国家',
  `hosp_addr` varchar(256) DEFAULT NULL COMMENT '医院地址',
  `hosp_class` varchar(32) NOT NULL COMMENT '等级',
  `hosp_gps` varchar(128) DEFAULT NULL COMMENT '医院定位',
  `hosp_image` varchar(256) DEFAULT NULL COMMENT '医院图片',
  `hosp_info` varchar(512) DEFAULT NULL COMMENT '医院信息',
  `hosp_name` varchar(128) NOT NULL COMMENT '医院名字',
  `hosp_no` varchar(32) NOT NULL COMMENT '医院编号',
  `parent_id` bigint(22) DEFAULT NULL COMMENT '上级医院',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_hospital_info` */

/*Table structure for table `dst_lis` */

DROP TABLE IF EXISTS `dst_lis`;

CREATE TABLE `dst_lis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `apply_doctor` varchar(32) DEFAULT NULL COMMENT '申请医生',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `execute_time` datetime DEFAULT NULL COMMENT '送检时间',
  `lis_diagnoise` mediumtext DEFAULT NULL COMMENT '检验诊断',
  `lis_groupname` varchar(256) DEFAULT NULL COMMENT '检验项目',
  `lis_id` varchar(32) NOT NULL COMMENT '检验单号',
  `lis_specimen` varchar(32) DEFAULT NULL COMMENT '采样标本',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_lsh` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `report_doctor` varchar(32) DEFAULT NULL COMMENT '报告医生',
  `report_time` datetime NOT NULL COMMENT '报告时间',
  `visit_id` varchar(32) NOT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_lis_id` (`lis_id`) USING BTREE,
  KEY `idx_lis_patient_id` (`patient_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=188525 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_lis` */

/*Table structure for table `dst_lis_item` */

DROP TABLE IF EXISTS `dst_lis_item`;

CREATE TABLE `dst_lis_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `lis_id` varchar(32) NOT NULL COMMENT '检验单号',
  `lis_item_ch_name` varchar(32) DEFAULT NULL COMMENT '检验中文名称',
  `lis_item_en_name` varchar(32) DEFAULT NULL COMMENT '检验英文名称',
  `lis_result_code` varchar(32) DEFAULT NULL COMMENT '检验结果代码',
  `result_reference` varchar(32) DEFAULT NULL COMMENT '参考范围',
  `result_unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `result_value` varchar(32) DEFAULT NULL COMMENT '检验结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=2845706 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_lis_item` */

/*Table structure for table `dst_message` */

DROP TABLE IF EXISTS `dst_message`;

CREATE TABLE `dst_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `sender_no` varchar(255) DEFAULT '',
  `content` varchar(2000) DEFAULT '' COMMENT '用户消息',
  `message_status` int(11) DEFAULT NULL COMMENT '消息状态',
  `type` varchar(255) DEFAULT NULL COMMENT '消息类型',
  `push_type` varchar(255) DEFAULT NULL COMMENT '消息推送类型',
  `creat_time` datetime DEFAULT NULL COMMENT '创建时间',
  `display_type` varchar(256) DEFAULT NULL COMMENT '消息显示的类型',
  `receiver_no` varchar(256) DEFAULT NULL COMMENT '消息接收人工号',
  `event_object_id` varchar(256) DEFAULT '' COMMENT '事件主体id',
  `receiver_name` varchar(256) DEFAULT '',
  `sender_name` varchar(256) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1321 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_message` */

/*Table structure for table `dst_op_environment` */

DROP TABLE IF EXISTS `dst_op_environment`;

CREATE TABLE `dst_op_environment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `analog0` varchar(32) DEFAULT NULL COMMENT 'PM1.0(CF=1)',
  `analog1` varchar(32) DEFAULT NULL COMMENT 'PM2.5(CF=1)',
  `analog10` varchar(32) DEFAULT NULL COMMENT '5.0um(颗粒物个数)',
  `analog11` varchar(32) DEFAULT NULL COMMENT '10um(颗粒物个数)',
  `analog12` varchar(32) DEFAULT NULL COMMENT 'HCHO(甲醛浓度)',
  `analog2` varchar(32) DEFAULT NULL COMMENT 'PM10(CF=1)',
  `analog3` varchar(32) DEFAULT NULL COMMENT 'PM1.0(大气压)',
  `analog4` varchar(32) DEFAULT NULL COMMENT 'PM2.5(大气压)',
  `analog5` varchar(32) DEFAULT NULL COMMENT 'PM10(大气压)',
  `analog6` varchar(32) DEFAULT NULL COMMENT '0.3um(颗粒物个数)',
  `analog7` varchar(32) DEFAULT NULL COMMENT '0.5um(颗粒物个数)',
  `analog8` varchar(32) DEFAULT NULL COMMENT '1.0um(颗粒物个数)',
  `analog9` varchar(32) DEFAULT NULL COMMENT '2.5um(颗粒物个数)',
  `argstatus` varchar(32) DEFAULT NULL COMMENT '真空吸引',
  `co2value` varchar(32) DEFAULT NULL COMMENT '二氧化碳',
  `fourbrstatus` varchar(32) DEFAULT NULL COMMENT '4BR压缩气',
  `humidity` varchar(32) DEFAULT NULL COMMENT '湿度',
  `negapresskey` varchar(32) DEFAULT NULL COMMENT '房间正负压',
  `nigrtstatus` varchar(32) DEFAULT NULL COMMENT '笑气',
  `nitstatus` varchar(32) DEFAULT NULL COMMENT '氩气',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `oxstatus` varchar(32) DEFAULT NULL COMMENT '氧气',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `roompress` varchar(32) DEFAULT NULL COMMENT '房间压差',
  `sevenbrstatus` varchar(32) DEFAULT NULL COMMENT '7BR压缩气',
  `temp` varchar(32) DEFAULT NULL COMMENT '温度',
  `update_time` datetime DEFAULT NULL COMMENT '时间',
  `vacstatus` varchar(32) DEFAULT NULL COMMENT '氮气',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_op_environment` */

/*Table structure for table `dst_op_image` */

DROP TABLE IF EXISTS `dst_op_image`;

CREATE TABLE `dst_op_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(256) DEFAULT '',
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(256) DEFAULT '',
  `image_name` varchar(256) DEFAULT NULL COMMENT '手术截图',
  `operation_id` varchar(32) DEFAULT NULL COMMENT '手术流水号',
  `image_status` int(12) DEFAULT NULL COMMENT '0:未上传 ;1:已上传',
  `image_time` datetime DEFAULT NULL,
  `patient_id` varchar(256) DEFAULT '',
  `image_path` varchar(256) DEFAULT NULL,
  `video_channel` varchar(255) DEFAULT '' COMMENT '图片来源所属通道口',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=776 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_op_image` */

/*Table structure for table `dst_op_vital_signs` */

DROP TABLE IF EXISTS `dst_op_vital_signs`;

CREATE TABLE `dst_op_vital_signs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `abpdia` varchar(32) DEFAULT NULL COMMENT '有创舒张压 ',
  `abpmean` varchar(32) DEFAULT NULL COMMENT '有创平均压',
  `abpsys` varchar(32) DEFAULT NULL COMMENT '有创收缩压',
  `awrr` varchar(32) DEFAULT NULL COMMENT '呼吸频率',
  `cardiac_output` varchar(32) DEFAULT NULL COMMENT '心输出量',
  `collect_time` datetime DEFAULT NULL COMMENT '收集时间',
  `cpat` varchar(32) DEFAULT NULL COMMENT '肺顺应性',
  `cvpmean` varchar(32) DEFAULT NULL COMMENT 'CVP',
  `depth_monitor_eeg` varchar(32) DEFAULT NULL COMMENT '脑电意识深度监测',
  `etco2` varchar(32) DEFAULT NULL COMMENT 'EtCO2',
  `exmac` varchar(32) DEFAULT NULL COMMENT 'MAC',
  `expiratory_pressure` varchar(32) DEFAULT NULL COMMENT '呼气末正压',
  `heart_rate` varchar(32) DEFAULT NULL COMMENT '心率',
  `minute_ventilation` varchar(32) DEFAULT NULL COMMENT '分钟通气量',
  `nbpdia` varchar(32) DEFAULT NULL COMMENT '无创舒张压',
  `nbpmean` varchar(32) DEFAULT NULL COMMENT '无创平均压',
  `nbpsys` varchar(32) DEFAULT NULL COMMENT '无创收缩压',
  `o2_concentration` varchar(32) DEFAULT NULL COMMENT 'O2浓度',
  `o2_flow` varchar(32) DEFAULT NULL COMMENT 'O2流量',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `oxygen_saturation` varchar(32) DEFAULT NULL COMMENT '血氧饱和度',
  `patient_id` varchar(32) DEFAULT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `peak` varchar(32) DEFAULT NULL COMMENT '气道压',
  `pls` varchar(32) DEFAULT NULL COMMENT 'PLS',
  `pulse` varchar(32) DEFAULT NULL COMMENT '脉搏',
  `svv` varchar(32) DEFAULT NULL COMMENT '每搏量变异',
  `temp` varchar(32) DEFAULT NULL COMMENT '体温',
  `tidal_volume` varchar(32) DEFAULT NULL COMMENT '潮气量',
  `update_time` datetime DEFAULT NULL COMMENT '时间',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  `artdia` varchar(32) DEFAULT NULL COMMENT 'Art?????',
  `artmeap` varchar(32) DEFAULT NULL COMMENT 'Art?????',
  `artsys` varchar(32) DEFAULT NULL COMMENT 'Art?????',
  `bis1` varchar(32) DEFAULT NULL COMMENT '????',
  `bsr` varchar(32) DEFAULT NULL COMMENT '???',
  `co` varchar(32) DEFAULT NULL COMMENT '????',
  `cvpdia` varchar(32) DEFAULT NULL COMMENT '???????',
  `cvpsys` varchar(32) DEFAULT NULL COMMENT '???????',
  `fio2` varchar(32) DEFAULT NULL COMMENT '?????',
  `hr` varchar(32) DEFAULT NULL COMMENT '??',
  `n2oe` varchar(32) DEFAULT NULL COMMENT 'n2o?????',
  `n2oi` varchar(32) DEFAULT NULL COMMENT 'n2o?????',
  `nmt_t1` varchar(32) DEFAULT NULL COMMENT 'nmt?????',
  `o22` varchar(32) DEFAULT NULL COMMENT 'O2??',
  `o2f` varchar(32) DEFAULT NULL COMMENT 'O2??',
  `peep` varchar(32) DEFAULT NULL COMMENT '?????',
  `pplat` varchar(32) DEFAULT NULL COMMENT '???',
  `pr` varchar(32) DEFAULT NULL COMMENT '??',
  `pvc` varchar(32) DEFAULT NULL COMMENT '???????',
  `re` varchar(32) DEFAULT NULL COMMENT '???',
  `resp` varchar(32) DEFAULT NULL COMMENT '????',
  `se` varchar(32) DEFAULT NULL COMMENT '???',
  `spo2` varchar(32) DEFAULT NULL COMMENT '?????',
  `t1` varchar(32) DEFAULT NULL COMMENT '??',
  `t2` varchar(32) DEFAULT NULL COMMENT '??',
  `t3` varchar(32) DEFAULT NULL COMMENT '??',
  `t4` varchar(32) DEFAULT NULL COMMENT '??',
  `tofcount` varchar(32) DEFAULT NULL COMMENT 'nmt?????',
  `tratio` varchar(32) DEFAULT NULL COMMENT 'nmt?????',
  `ve` varchar(32) DEFAULT NULL COMMENT '?????',
  `vte` varchar(32) DEFAULT NULL COMMENT '???',
  `artmean` varchar(32) DEFAULT NULL COMMENT '有创平均压',
  `co2` varchar(32) DEFAULT NULL COMMENT '呼气末CO2分压',
  `co2i` varchar(32) DEFAULT NULL COMMENT '吸入CO2分压',
  `comp` varchar(32) DEFAULT NULL COMMENT '未知',
  `mac` varchar(32) DEFAULT NULL COMMENT '最低肺泡有效浓度',
  `o2e` varchar(32) DEFAULT NULL COMMENT '呼出O2浓度',
  `vt` varchar(32) DEFAULT NULL COMMENT '潮气量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=187591 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_op_vital_signs` */

/*Table structure for table `dst_operation_equipment` */

DROP TABLE IF EXISTS `dst_operation_equipment`;

CREATE TABLE `dst_operation_equipment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_create_time` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `operation_id` int(11) DEFAULT NULL COMMENT '手术记录id',
  `channel` varchar(255) DEFAULT '' COMMENT '手术记录视频源id',
  `equipment_name` varchar(255) DEFAULT '' COMMENT '设备名称',
  `equipmenet_no` varchar(255) DEFAULT '' COMMENT '设备编号',
  `equipment_type` varchar(255) DEFAULT '' COMMENT '设备类型',
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_equipment` */

/*Table structure for table `dst_operation_event` */

DROP TABLE IF EXISTS `dst_operation_event`;

CREATE TABLE `dst_operation_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `amount` varchar(32) DEFAULT NULL COMMENT '数量',
  `bill_type` varchar(32) DEFAULT NULL COMMENT '账单类型',
  `collect_time` datetime NOT NULL COMMENT '收集时间',
  `event_name` varchar(256) DEFAULT NULL COMMENT '事件名',
  `event_type` varchar(32) DEFAULT NULL COMMENT '事件类型',
  `event_value` varchar(32) DEFAULT NULL COMMENT '值',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `origin_key` varchar(128) DEFAULT NULL COMMENT '原有主键',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  `update_time` datetime DEFAULT NULL COMMENT '时间',
  `usage_method` varchar(32) DEFAULT NULL COMMENT '用法',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=4715 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_event` */

/*Table structure for table `dst_operation_medicine` */

DROP TABLE IF EXISTS `dst_operation_medicine`;

CREATE TABLE `dst_operation_medicine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `collect_time` datetime DEFAULT NULL COMMENT '收集时间',
  `medicine_name` varchar(32) DEFAULT NULL COMMENT '药物名称',
  `medicine_type` varchar(32) DEFAULT NULL COMMENT '药物类型',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `origin_key` varchar(128) DEFAULT NULL COMMENT '原有主键',
  `single_gross` double(9,2) DEFAULT NULL COMMENT '单次总量',
  `single_gross_edit` varchar(32) DEFAULT NULL COMMENT '单次总量类型',
  `single_unit` varchar(32) DEFAULT NULL COMMENT '单次总量单位',
  `succession_gross` double(9,2) DEFAULT NULL COMMENT '持续总量',
  `succession_gross_edit` varchar(32) DEFAULT NULL COMMENT '持续总量类型',
  `succession_unit` varchar(32) DEFAULT NULL COMMENT '持续总量单位',
  `update_time` datetime DEFAULT NULL COMMENT '时间',
  `usage_potency` varchar(32) DEFAULT NULL COMMENT '药物浓度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=16991 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_medicine` */

/*Table structure for table `dst_operation_plan` */

DROP TABLE IF EXISTS `dst_operation_plan`;

CREATE TABLE `dst_operation_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `doctor_no` varchar(32) NOT NULL COMMENT '手术医生工号',
  `operation_doctor` varchar(32) DEFAULT NULL COMMENT '手术医生名称',
  `anes_method_id` varchar(32) DEFAULT NULL COMMENT '麻醉方式',
  `anesthesia_apply_dept_id` varchar(32) DEFAULT NULL COMMENT '申请麻醉科室id',
  `anesthesia_assistant` varchar(32) DEFAULT NULL COMMENT '麻醉助手',
  `anesthesia_opercode` varchar(32) DEFAULT NULL COMMENT '麻醉安排操作人id',
  `anesthesia_opername` varchar(32) DEFAULT NULL COMMENT '麻醉安排操作人',
  `anesthesia_opertime` datetime DEFAULT NULL COMMENT '麻醉安排操作时间',
  `arranged` varchar(32) DEFAULT NULL COMMENT '安排标识',
  `asa` varchar(32) DEFAULT NULL COMMENT 'ASA分级',
  `assistant` varchar(32) DEFAULT NULL COMMENT '助手',
  `assistant_id` varchar(32) DEFAULT NULL COMMENT '麻醉助手id',
  `exp_anesthesia` varchar(32) DEFAULT NULL COMMENT '额外麻醉医师',
  `exp_anesthesia_id` varchar(32) DEFAULT NULL COMMENT '额外麻醉医师id',
  `mostly_anesthesia` varchar(32) DEFAULT NULL COMMENT '麻醉师',
  `mostly_anesthesia_id` varchar(32) DEFAULT NULL COMMENT '麻醉师id',
  `old_operation_id` varchar(32) DEFAULT NULL COMMENT '原流水号',
  `operation_apply_dept_id` varchar(32) DEFAULT NULL COMMENT '申请手术科室id',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `operation_opercode` varchar(32) DEFAULT NULL COMMENT '手术安排操作人id',
  `operation_opername` varchar(32) DEFAULT NULL COMMENT '手术安排操作人',
  `operation_opertime` datetime DEFAULT NULL COMMENT '手术安排操作时间',
  `operation_platform_num` varchar(32) DEFAULT NULL COMMENT '医生手术台号',
  `operation_platform_seq` varchar(32) DEFAULT NULL COMMENT '医生手术台序',
  `operation_platform_type` varchar(32) DEFAULT NULL COMMENT '医生手术台安排（正台，加台）',
  `operation_room_id` varchar(256) NOT NULL COMMENT '手术间id',
  `operation_room_name` varchar(256) DEFAULT NULL COMMENT '手术间名称',
  `operation_status` varchar(32) DEFAULT NULL COMMENT '手术状态',
  `operation_time` datetime DEFAULT NULL COMMENT '手术时间',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `patrol_nurse_one_id` varchar(32) DEFAULT NULL COMMENT '巡回护士1id',
  `patrol_nurse_one_name` varchar(32) DEFAULT NULL COMMENT '巡回护士1',
  `patrol_nurse_two_id` varchar(32) DEFAULT NULL COMMENT '巡回护士2id',
  `patrol_nurse_two_name` varchar(32) DEFAULT NULL COMMENT '巡回护士2',
  `perfusion_one_id` varchar(32) DEFAULT NULL COMMENT '灌注医师一id',
  `perfusion_one_name` varchar(32) DEFAULT NULL COMMENT '灌注医师一姓名',
  `perfusion_two_id` varchar(32) DEFAULT NULL COMMENT '灌注医师二id',
  `perfusion_two_name` varchar(32) DEFAULT NULL COMMENT '灌注医师二姓名',
  `study_anesthesia` varchar(32) DEFAULT NULL COMMENT '进修麻醉医师',
  `study_anesthesia_id` varchar(32) DEFAULT NULL COMMENT '进修麻醉医师id',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  `washhand_nurse_one_id` varchar(32) DEFAULT NULL COMMENT '洗手护士1id',
  `washhand_nurse_one_name` varchar(32) DEFAULT NULL COMMENT '洗手护士1',
  `washhand_nurse_two_id` varchar(32) DEFAULT NULL COMMENT '洗手护士2id',
  `washhand_nurse_two_name` varchar(32) DEFAULT NULL COMMENT '洗手护士2',
  `operation_name` varchar(256) NOT NULL COMMENT '手术名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_operation_id` (`operation_id`) USING BTREE,
  KEY `idx_patient_id` (`patient_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=915 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_plan` */

/*Table structure for table `dst_operation_record` */

DROP TABLE IF EXISTS `dst_operation_record`;

CREATE TABLE `dst_operation_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `analgesia_anesthetist` varchar(32) DEFAULT NULL COMMENT '镇痛开立医生',
  `analgesia_type` varchar(32) DEFAULT NULL COMMENT '镇痛方式',
  `anesthesia_befor_medication` varchar(256) DEFAULT NULL COMMENT '麻醉前用药',
  `anesthesia_endtime` datetime DEFAULT NULL COMMENT '麻醉结束时间',
  `anesthesia_method` varchar(32) DEFAULT NULL COMMENT '麻醉方法',
  `anesthesia_starttime` datetime DEFAULT NULL COMMENT '麻醉开始时间',
  `area` varchar(32) DEFAULT NULL COMMENT '体表面积',
  `asa` varchar(32) DEFAULT NULL COMMENT 'ASA分级',
  `befor_operation_diagnosis` varchar(256) DEFAULT NULL COMMENT '术前诊断',
  `befor_special_situation` varchar(256) DEFAULT NULL COMMENT '术前特殊情况',
  `blood_quantily` varchar(32) DEFAULT NULL COMMENT '输血量',
  `create_time` datetime DEFAULT NULL COMMENT '建手术单时间',
  `document_class` varchar(32) DEFAULT NULL COMMENT '单据状态',
  `finish_time` datetime DEFAULT NULL COMMENT '封单时间',
  `finish_user_id` varchar(32) DEFAULT NULL COMMENT '封单用户id',
  `infusion_quantily` varchar(32) DEFAULT NULL COMMENT '输液量',
  `inroom_time` datetime DEFAULT NULL COMMENT '进入手术室时间',
  `operation_doctor` varchar(32) DEFAULT NULL COMMENT '手术医生',
  `operation_doctor_code` varchar(32) DEFAULT NULL COMMENT '医生工号',
  `operation_endtime` datetime DEFAULT NULL COMMENT '手术结束时间',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `operation_name` varchar(256) DEFAULT NULL COMMENT '手术名称',
  `operation_room_id` varchar(32) DEFAULT NULL COMMENT '手术房间号',
  `operation_starttime` datetime DEFAULT NULL COMMENT '手术开始时间',
  `operation_type` varchar(32) DEFAULT NULL COMMENT '手术类型',
  `output_quantily` varchar(32) DEFAULT NULL COMMENT '出量',
  `outroom_time` datetime DEFAULT NULL COMMENT '离开手术室时间',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `position` varchar(32) DEFAULT NULL COMMENT '手术体位',
  `postoper_diagnosis` varchar(256) DEFAULT NULL COMMENT '术后诊断',
  `preoperative_fasting` varchar(32) DEFAULT NULL COMMENT '术前禁食',
  `relieve_pain` varchar(256) DEFAULT NULL COMMENT '术后镇痛相关',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  `ai` varchar(32) DEFAULT NULL COMMENT 'ai??',
  `environment` varchar(32) DEFAULT NULL COMMENT '????',
  `life` varchar(32) DEFAULT NULL COMMENT '????',
  `onlive` varchar(32) DEFAULT NULL COMMENT '????',
  `openness` varchar(32) DEFAULT NULL COMMENT '????',
  `oper_status` int(11) DEFAULT NULL COMMENT '????',
  `vedio_all` varchar(32) DEFAULT NULL COMMENT '????',
  `vedio_oper` varchar(32) DEFAULT NULL COMMENT '????',
  `video_all` varchar(32) DEFAULT NULL COMMENT '全景摄像',
  `video_oper` varchar(32) DEFAULT NULL COMMENT '术野摄像',
  `upload_status` int(12) DEFAULT NULL COMMENT '手术资料上传状态',
  `transcribe_status` varchar(32) DEFAULT NULL COMMENT '录制状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `NewIndex1` (`patient_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=406 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_record` */

/*Table structure for table `dst_operation_room` */

DROP TABLE IF EXISTS `dst_operation_room`;

CREATE TABLE `dst_operation_room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `room_name` varchar(32) NOT NULL COMMENT '手术室名称',
  `room_sort` varchar(32) DEFAULT NULL COMMENT '手术室排序号',
  `room_status` varchar(32) NOT NULL COMMENT '手术室状态',
  `room_type` varchar(32) DEFAULT NULL COMMENT '手术室类型',
  `room_id` varchar(32) NOT NULL COMMENT '手术室id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_room` */

/*Table structure for table `dst_operation_video` */

DROP TABLE IF EXISTS `dst_operation_video`;

CREATE TABLE `dst_operation_video` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `camera1` varchar(32) DEFAULT NULL COMMENT '摄像头1',
  `camera2` varchar(32) DEFAULT NULL COMMENT '摄像头2',
  `doctor_no` bigint(22) DEFAULT NULL COMMENT '录制者',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `image_path1` varchar(256) DEFAULT NULL COMMENT '第一帧图片1',
  `image_path2` varchar(256) DEFAULT NULL COMMENT '第二帧图片2',
  `live_path1` varchar(256) DEFAULT NULL COMMENT '直播路径1',
  `live_path2` varchar(256) DEFAULT NULL COMMENT '直播路径2',
  `online` varchar(32) DEFAULT NULL COMMENT '直播手术录像',
  `online_status` varchar(32) DEFAULT NULL COMMENT '直播状态',
  `openness` varchar(32) DEFAULT NULL COMMENT '公开手术录像',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `operation_name` varchar(32) DEFAULT NULL COMMENT '手术名称',
  `operation_room_id` bigint(22) DEFAULT NULL COMMENT '手术室ID',
  `operation_type` varchar(32) DEFAULT NULL COMMENT '手术类型',
  `patient_id` varchar(32) DEFAULT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  `vod_path1` varchar(256) DEFAULT NULL COMMENT '点播路径1',
  `vod_path2` varchar(256) DEFAULT NULL COMMENT '点播路径2',
  `camera_type` varchar(32) DEFAULT NULL COMMENT '?????',
  `img_path` varchar(256) DEFAULT NULL COMMENT '???',
  `live_path` varchar(256) DEFAULT NULL COMMENT '????',
  `record_status` varchar(32) DEFAULT NULL COMMENT '????',
  `vod_path` varchar(256) DEFAULT NULL COMMENT '????',
  `video_name` varchar(256) DEFAULT NULL COMMENT '视频文件名',
  `video_path` varchar(256) DEFAULT NULL COMMENT '视频路径',
  `video_status` int(12) DEFAULT NULL COMMENT '视频状态(0:未上传;1:正在上传;2:已上传)',
  `video_channel` varchar(255) DEFAULT NULL COMMENT '视频来源通道口',
  `video_type` int(20) DEFAULT NULL COMMENT '视频来源类型',
  `long_time` varchar(256) DEFAULT NULL COMMENT '视频来源通道',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1399 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_video` */

/*Table structure for table `dst_operation_vital_si` */

DROP TABLE IF EXISTS `dst_operation_vital_si`;

CREATE TABLE `dst_operation_vital_si` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `heart_rate` varchar(32) DEFAULT NULL COMMENT '心率',
  `nbpdia` varchar(32) DEFAULT NULL COMMENT '无创舒张压',
  `nbpmean` varchar(32) DEFAULT NULL COMMENT '无创平均压',
  `nbpsys` varchar(32) DEFAULT NULL COMMENT '无创收缩压',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `oxygen_saturation` varchar(32) DEFAULT NULL COMMENT '血氧饱和度',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `pulse` varchar(32) DEFAULT NULL COMMENT '脉搏',
  `temp` varchar(32) DEFAULT NULL COMMENT '体温',
  `update_time` date NOT NULL COMMENT '时间',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_operation_vital_si` */

/*Table structure for table `dst_opetration_video_item` */

DROP TABLE IF EXISTS `dst_opetration_video_item`;

CREATE TABLE `dst_opetration_video_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `camera_name` varchar(32) NOT NULL COMMENT '摄像头名称',
  `camera_type` varchar(32) NOT NULL COMMENT '摄像头类型',
  `operation_id` varchar(32) NOT NULL COMMENT '手术流水号',
  `video_channel` varchar(32) NOT NULL COMMENT '手术记录所用的通道号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_opetration_video_item` */

/*Table structure for table `dst_patient` */

DROP TABLE IF EXISTS `dst_patient`;

CREATE TABLE `dst_patient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `blood_type` varchar(32) DEFAULT NULL COMMENT '血型',
  `career` varchar(32) DEFAULT NULL COMMENT '职业',
  `charge_type` varchar(32) DEFAULT NULL COMMENT '住院费别',
  `country` varchar(32) DEFAULT NULL COMMENT '国籍',
  `date_of_birth` date DEFAULT NULL COMMENT '出生日期',
  `gender` varchar(32) DEFAULT NULL COMMENT '性别',
  `height` varchar(32) DEFAULT NULL COMMENT '身高',
  `home_addr` varchar(256) DEFAULT NULL COMMENT '家庭地址',
  `home_phone` varchar(32) DEFAULT NULL COMMENT '家庭电话',
  `idno` varchar(32) DEFAULT NULL COMMENT '身份证',
  `marriage` varchar(32) DEFAULT NULL COMMENT '婚姻情况',
  `mate_addr` varchar(256) DEFAULT NULL COMMENT '联系人地址',
  `mate_phone` varchar(32) DEFAULT NULL COMMENT '联系人电话',
  `medical_insurance` varchar(32) DEFAULT NULL COMMENT '医疗保险证',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `nation` varchar(32) DEFAULT NULL COMMENT '民族',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) NOT NULL COMMENT '住院流水号',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `sync_time` datetime DEFAULT NULL COMMENT '同步时间',
  `update_time` datetime NOT NULL COMMENT '数据更新时间',
  `visit_id` varchar(32) NOT NULL COMMENT '当前住院次数',
  `weight` varchar(32) DEFAULT NULL COMMENT '体重',
  `work_phone` varchar(32) DEFAULT NULL COMMENT '单位电话',
  `work_unit` varchar(256) DEFAULT NULL COMMENT '工作单位',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_patient_id` (`patient_id`) USING BTREE,
  KEY `idx_patient_id` (`patient_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=13878 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_patient` */

/*Table structure for table `dst_patient_copy` */

DROP TABLE IF EXISTS `dst_patient_copy`;

CREATE TABLE `dst_patient_copy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `blood_type` varchar(32) DEFAULT NULL COMMENT '血型',
  `career` varchar(32) DEFAULT NULL COMMENT '职业',
  `charge_type` varchar(32) DEFAULT NULL COMMENT '住院费别',
  `country` varchar(32) DEFAULT NULL COMMENT '国籍',
  `date_of_birth` date DEFAULT NULL COMMENT '出生日期',
  `gender` varchar(32) DEFAULT NULL COMMENT '性别',
  `height` varchar(32) DEFAULT NULL COMMENT '身高',
  `home_addr` varchar(256) DEFAULT NULL COMMENT '家庭地址',
  `home_phone` varchar(32) DEFAULT NULL COMMENT '家庭电话',
  `idno` varchar(32) DEFAULT NULL COMMENT '身份证',
  `marriage` varchar(32) DEFAULT NULL COMMENT '婚姻情况',
  `mate_addr` varchar(256) DEFAULT NULL COMMENT '联系人地址',
  `mate_phone` varchar(32) DEFAULT NULL COMMENT '联系人电话',
  `medical_insurance` varchar(32) DEFAULT NULL COMMENT '医疗保险证',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `nation` varchar(32) DEFAULT NULL COMMENT '民族',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `sync_time` datetime NOT NULL COMMENT '同步时间',
  `update_time` datetime NOT NULL COMMENT '数据更新时间',
  `weight` varchar(32) DEFAULT NULL COMMENT '体重',
  `work_phone` varchar(32) DEFAULT NULL COMMENT '单位电话',
  `work_unit` varchar(256) DEFAULT NULL COMMENT '工作单位',
  `patient_sn` varchar(32) NOT NULL COMMENT '住院流水号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=13876 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_patient_copy` */

/*Table structure for table `dst_patient_in` */

DROP TABLE IF EXISTS `dst_patient_in`;

CREATE TABLE `dst_patient_in` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `bed_no` varchar(32) DEFAULT NULL COMMENT '床号',
  `dept_name` varchar(32) DEFAULT NULL COMMENT '科室名称',
  `dept_no` varchar(32) DEFAULT NULL COMMENT '科室编码',
  `doctor_name` varchar(32) DEFAULT NULL COMMENT '责任医生',
  `doctor_no` varchar(32) DEFAULT NULL COMMENT '医生工号',
  `hosp_name` varchar(128) DEFAULT NULL COMMENT '医院名字',
  `hosp_no` varchar(32) DEFAULT NULL COMMENT '医院编码',
  `in_datetime` datetime NOT NULL COMMENT '入院时间',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `nurse_name` varchar(32) DEFAULT NULL COMMENT '责任护士',
  `nurse_no` varchar(32) DEFAULT NULL COMMENT '护士工号',
  `out_datetime` datetime DEFAULT NULL COMMENT '出院时间',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) NOT NULL COMMENT '住院流水号',
  `sync_time` datetime NOT NULL COMMENT '同步时间',
  `visit_id` varchar(32) NOT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_patient_sn` (`patient_sn`) USING BTREE,
  KEY `idx_patient_id` (`patient_id`) USING BTREE,
  KEY `dept_no` (`dept_no`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=10676 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_patient_in` */

/*Table structure for table `dst_patient_inhospital` */

DROP TABLE IF EXISTS `dst_patient_inhospital`;

CREATE TABLE `dst_patient_inhospital` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `in_datetime` datetime NOT NULL COMMENT '入院时间',
  `out_datetime` datetime DEFAULT NULL COMMENT '出院时间',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) NOT NULL COMMENT '住院流水号',
  `sync_time` datetime NOT NULL COMMENT '同步时间',
  `visit_id` varchar(32) NOT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_patient_inhospital` */

/*Table structure for table `dst_patient_order` */

DROP TABLE IF EXISTS `dst_patient_order`;

CREATE TABLE `dst_patient_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `order_avg` varchar(32) DEFAULT NULL COMMENT '医嘱频次',
  `order_class` varchar(32) DEFAULT NULL COMMENT '医嘱类别',
  `order_creatdept` varchar(32) DEFAULT NULL COMMENT '开嘱科室',
  `order_creatdoctor` varchar(32) DEFAULT NULL COMMENT '开嘱医生',
  `order_createtime` datetime NOT NULL COMMENT '医嘱创建时间',
  `order_group` varchar(32) DEFAULT NULL COMMENT '组号',
  `order_name` varchar(256) NOT NULL COMMENT '医嘱名称',
  `order_num` varchar(32) DEFAULT NULL COMMENT '医嘱数量',
  `order_nurse` varchar(32) DEFAULT NULL COMMENT '执行护士',
  `order_startdate` datetime NOT NULL COMMENT '开始日期',
  `order_state` varchar(32) DEFAULT NULL COMMENT '执行状态',
  `order_stopdate` datetime DEFAULT NULL COMMENT '结束日期（计划）',
  `order_stopdoctor` varchar(32) DEFAULT NULL COMMENT '停嘱医生',
  `order_stoptime` datetime DEFAULT NULL COMMENT '医嘱停止时间（实际）',
  `order_type` varchar(32) DEFAULT NULL COMMENT '医嘱类型',
  `order_usage` varchar(256) DEFAULT NULL COMMENT '用法',
  `origin_key` varchar(128) DEFAULT NULL COMMENT '原有主键',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `visit_id` varchar(32) NOT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_origin_key` (`origin_key`) USING BTREE,
  KEY `idx_patient_id` (`patient_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1395962 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_patient_order` */

/*Table structure for table `dst_preoperative` */

DROP TABLE IF EXISTS `dst_preoperative`;

CREATE TABLE `dst_preoperative` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `content` text DEFAULT NULL COMMENT '表单内容',
  `dept_name` varchar(64) DEFAULT NULL COMMENT '科室名称',
  `dept_no` varchar(32) NOT NULL COMMENT '科室编码',
  `doctor_no` varchar(32) NOT NULL COMMENT '评估医生',
  `evaluate_time` datetime DEFAULT NULL COMMENT '评估时间',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `type` varchar(32) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_preoperative` */

/*Table structure for table `dst_preoperative_mobile` */

DROP TABLE IF EXISTS `dst_preoperative_mobile`;

CREATE TABLE `dst_preoperative_mobile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `operation_time` datetime DEFAULT NULL COMMENT '手术时间',
  `patient_id` varchar(32) NOT NULL COMMENT '患者id',
  `preoperative_dept_name` varchar(32) DEFAULT NULL COMMENT '访视病区',
  `preoperative_dept_no` varchar(32) DEFAULT NULL COMMENT '访视病区id',
  `preoperative_doc_id` varchar(32) DEFAULT NULL COMMENT '访视医生id',
  `preoperative_doc_name` varchar(32) DEFAULT NULL COMMENT '访视医生',
  `preoperative_id` varchar(32) NOT NULL COMMENT '访视id',
  `preoperative_time` datetime DEFAULT NULL COMMENT '访视时间',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_preoperative_mobile` */

/*Table structure for table `dst_preoperative_mobile_item` */

DROP TABLE IF EXISTS `dst_preoperative_mobile_item`;

CREATE TABLE `dst_preoperative_mobile_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `anes_general_anesthesia` varchar(64) DEFAULT NULL COMMENT '麻醉全身麻醉',
  `anes_instrument` varchar(64) DEFAULT NULL COMMENT '术中器械',
  `anes_medicine` varchar(64) DEFAULT NULL COMMENT '麻醉术前用药',
  `anes_nerve_block` varchar(64) DEFAULT NULL COMMENT '麻醉神经阻滞',
  `anes_oper_between` varchar(64) DEFAULT NULL COMMENT '麻醉术中',
  `anes_pasting` varchar(32) DEFAULT NULL COMMENT '麻醉术前禁食饮',
  `anes_spinal_canal` varchar(64) DEFAULT NULL COMMENT '麻醉椎管',
  `anes_strategy` varchar(64) DEFAULT NULL COMMENT '麻醉对策',
  `ass_blood_cruor` varchar(128) DEFAULT NULL COMMENT '辅助凝血功能',
  `ass_blood_routine` varchar(128) DEFAULT NULL COMMENT '辅助血常规',
  `ass_chest_x` varchar(512) DEFAULT NULL COMMENT '辅助胸部X线',
  `ass_ekg` varchar(512) DEFAULT NULL COMMENT '辅助心电图',
  `ass_electrolyte` varchar(128) DEFAULT NULL COMMENT '辅助电解质',
  `ass_liver_kidney` varchar(128) DEFAULT NULL COMMENT '辅助肝肾功能',
  `bed_diagnosis` varchar(512) DEFAULT NULL COMMENT '临床诊断',
  `item_sync` varchar(32) DEFAULT NULL COMMENT '访视单同步',
  `mallampati_cervical_act` varchar(32) DEFAULT NULL COMMENT '气道颈椎活动',
  `mallampati_grading` varchar(32) DEFAULT NULL COMMENT '气道分级',
  `mallampati_open_mouth` varchar(32) DEFAULT NULL COMMENT '气道张口度',
  `mallampati_tooth` varchar(32) DEFAULT NULL COMMENT '气道牙齿',
  `nol_blood_pressure` varchar(32) DEFAULT NULL COMMENT '一般血压',
  `nol_breathe` varchar(32) DEFAULT NULL COMMENT '一般呼吸',
  `nol_physique` varchar(32) DEFAULT NULL COMMENT '一般体格',
  `nol_pulse` varchar(32) DEFAULT NULL COMMENT '一般脉搏',
  `oper_risk_assessment` varchar(128) DEFAULT NULL COMMENT '手术风险评估',
  `operation_time` datetime DEFAULT NULL COMMENT '手术时间',
  `organ_blood_system` varchar(64) DEFAULT NULL COMMENT '器官血液系统',
  `organ_cardiovascular_grade` varchar(64) DEFAULT NULL COMMENT '器官心功能分级',
  `organ_cardiovascular_system` varchar(64) DEFAULT NULL COMMENT '器官心血管系统',
  `organ_chest` varchar(64) DEFAULT NULL COMMENT '器官胸片',
  `organ_digestive_system` varchar(64) DEFAULT NULL COMMENT '器官消化系统',
  `organ_endocrine_system` varchar(64) DEFAULT NULL COMMENT '器官内分泌系统',
  `organ_other` varchar(128) DEFAULT NULL COMMENT '器官其他特殊',
  `organ_respiratory_system` varchar(64) DEFAULT NULL COMMENT '器官呼吸系统',
  `organ_spine` varchar(64) DEFAULT NULL COMMENT '器官脊柱',
  `organ_urinary_system` varchar(64) DEFAULT NULL COMMENT '器官泌尿系统',
  `past_blood_transfusion` varchar(32) DEFAULT NULL COMMENT '既往输血',
  `past_dependent_drug` varchar(32) DEFAULT NULL COMMENT '既往依赖药物',
  `past_drug_food_allergy` varchar(32) DEFAULT NULL COMMENT '既往药物食物过敏',
  `past_surgical_anesthesia` varchar(32) DEFAULT NULL COMMENT '既往手术麻醉',
  `patient_asa` varchar(32) DEFAULT NULL COMMENT '病人ASA',
  `patient_id` varchar(32) NOT NULL COMMENT '患者住院流水号',
  `plan_operation` varchar(512) DEFAULT NULL COMMENT '拟行手术',
  `preoperative_id` varchar(32) NOT NULL COMMENT '访视id',
  `sign_path` varchar(256) DEFAULT NULL COMMENT '签字图片路径',
  `visit_id` varchar(32) DEFAULT NULL COMMENT '住院次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_preoperative_mobile_item` */

/*Table structure for table `dst_reply` */

DROP TABLE IF EXISTS `dst_reply`;

CREATE TABLE `dst_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `article_id` varchar(32) DEFAULT NULL COMMENT '父评论id',
  `comment` varchar(1000) DEFAULT '' COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '回复时间',
  `is_delete` int(3) DEFAULT NULL COMMENT '是否删除',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父评论',
  `receiver_name` varchar(256) DEFAULT NULL COMMENT '被回复/评论人名字',
  `receiver_no` varchar(256) DEFAULT NULL COMMENT '被回复/评论人id',
  `reply_status` int(11) DEFAULT NULL COMMENT '评论/回复状态 ',
  `reply_type` varchar(256) DEFAULT NULL COMMENT '回复时间',
  `revertive_id` bigint(20) DEFAULT NULL COMMENT '回复回复的回复id ',
  `sender_name` varchar(32) DEFAULT NULL COMMENT '回复/评论人名字',
  `sender_no` varchar(32) DEFAULT NULL COMMENT '回复/评论人id',
  `content` varchar(256) DEFAULT NULL COMMENT '评论内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_reply` */

/*Table structure for table `dst_user_dept` */

DROP TABLE IF EXISTS `dst_user_dept`;

CREATE TABLE `dst_user_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `dept_no` varchar(32) NOT NULL COMMENT '科室编码',
  `doctor_no` varchar(32) NOT NULL COMMENT '用户工号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=11118 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_user_dept` */

/*Table structure for table `dst_user_info` */

DROP TABLE IF EXISTS `dst_user_info`;

CREATE TABLE `dst_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL COMMENT '出生时间',
  `dept_name` varchar(32) DEFAULT NULL COMMENT '科室名称',
  `dept_no` varchar(32) DEFAULT NULL COMMENT '科室编号',
  `doctor_no` varchar(32) NOT NULL COMMENT '用户工号',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(32) DEFAULT NULL COMMENT '性别',
  `goodat` varchar(255) DEFAULT NULL COMMENT '擅长简介',
  `head_image` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `hosp_name` varchar(128) DEFAULT NULL COMMENT '医院名字',
  `hosp_no` varchar(32) DEFAULT NULL COMMENT '医院编号',
  `idno` varchar(256) DEFAULT NULL COMMENT '身份证号',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `password` varchar(32) NOT NULL COMMENT '登陆密码',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `title` varchar(32) NOT NULL COMMENT '职称职别',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_doctor_no` (`doctor_no`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=7001 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_user_info` */

insert  into `dst_user_info`(`id`,`record_version`,`sys_create_time`,`sys_create_user`,`sys_update_time`,`sys_update_user`,`date_of_birth`,`dept_name`,`dept_no`,`doctor_no`,`email`,`gender`,`goodat`,`head_image`,`hosp_name`,`hosp_no`,`idno`,`name`,`password`,`phone`,`title`) values (1,0,'2018-06-05 06:37:24',NULL,'2018-06-05 06:37:24',NULL,'1969-10-23','骨科','0042','admin',NULL,'1',NULL,NULL,'中智达信演示医院','72549786X44010511A1001','111111111111111','超级管理员','123456','13800000000','5901');

/*Table structure for table `dst_user_log` */

DROP TABLE IF EXISTS `dst_user_log`;

CREATE TABLE `dst_user_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `doctor_no` varchar(32) NOT NULL COMMENT '医生',
  `ip` varchar(255) NOT NULL COMMENT '登陆IP',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_user_log` */

/*Table structure for table `dst_user_patient` */

DROP TABLE IF EXISTS `dst_user_patient`;

CREATE TABLE `dst_user_patient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `doctor_no` varchar(32) NOT NULL COMMENT '用户工号',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1593 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `dst_user_patient` */

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

/*Data for the table `hibernate_sequence` */

/*Table structure for table `operation_vital` */

DROP TABLE IF EXISTS `operation_vital`;

CREATE TABLE `operation_vital` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `collect_date` datetime DEFAULT NULL COMMENT '日期',
  `item_code` varchar(32) DEFAULT NULL COMMENT '编码',
  `item_name` varchar(32) DEFAULT NULL COMMENT '名称',
  `item_value` varchar(32) DEFAULT NULL COMMENT '值',
  `operation_id` varchar(64) NOT NULL COMMENT '手术流水号',
  `patient_id` varchar(32) NOT NULL COMMENT '住院病历号',
  `patient_sn` varchar(32) DEFAULT NULL COMMENT '住院流水号',
  `unit` varchar(32) DEFAULT NULL COMMENT '单位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `operation_vital` */

/*Table structure for table `room_equipment` */

DROP TABLE IF EXISTS `room_equipment`;

CREATE TABLE `room_equipment` (
  `id` bigint(20) NOT NULL,
  `room_id` bigint(20) DEFAULT NULL COMMENT '手术室id',
  `equipment_id` bigint(255) DEFAULT NULL COMMENT '设备id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `room_equipment` */

/*Table structure for table `sync_mark` */

DROP TABLE IF EXISTS `sync_mark`;

CREATE TABLE `sync_mark` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `delay_time` bigint(20) DEFAULT NULL,
  `record_mark` varchar(255) DEFAULT NULL,
  `task_type` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sync_mark` */

/*Table structure for table `sync_task` */

DROP TABLE IF EXISTS `sync_task`;

CREATE TABLE `sync_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `excute_priority` int(11) DEFAULT NULL,
  `excute_result` varchar(255) DEFAULT NULL,
  `excute_time` datetime DEFAULT NULL,
  `paras` varchar(255) DEFAULT NULL,
  `task_status` int(11) DEFAULT NULL,
  `task_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sync_task` */

/*Table structure for table `sync_updatedata_record` */

DROP TABLE IF EXISTS `sync_updatedata_record`;

CREATE TABLE `sync_updatedata_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(32) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(32) DEFAULT NULL,
  `database` varchar(32) DEFAULT NULL COMMENT '连接的数据库',
  `sqltext` varchar(255) DEFAULT NULL COMMENT 'sql语句',
  `datatype` varchar(32) DEFAULT NULL COMMENT '数据类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sync_updatedata_record` */

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `dict_code` varchar(32) NOT NULL COMMENT '编码',
  `dict_desc` varchar(128) DEFAULT NULL COMMENT '描述',
  `dict_name` varchar(64) NOT NULL COMMENT '名称',
  `dict_order` int(11) DEFAULT NULL COMMENT '排序',
  `parent_id` bigint(22) NOT NULL COMMENT '上級',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=328 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_dict` */

/*Table structure for table `sys_resource` */

DROP TABLE IF EXISTS `sys_resource`;

CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `icon` varchar(256) DEFAULT NULL COMMENT '图标',
  `parent_id` bigint(20) NOT NULL COMMENT '父ID',
  `remark` varchar(1024) DEFAULT NULL COMMENT '描述',
  `res_code` varchar(64) NOT NULL COMMENT '编号',
  `res_name` varchar(256) DEFAULT NULL COMMENT '名称',
  `res_type` int(11) DEFAULT NULL COMMENT '权限分类(0:菜单,1:按钮)',
  `url` varchar(512) DEFAULT NULL COMMENT '地址',
  `zindex` varchar(64) NOT NULL COMMENT '资源排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_resource` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL COMMENT '角色描述',
  `role_code` varchar(32) NOT NULL COMMENT '角色编号',
  `role_name` varchar(256) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role` */

/*Table structure for table `sys_role_res` */

DROP TABLE IF EXISTS `sys_role_res`;

CREATE TABLE `sys_role_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `res_id` bigint(20) NOT NULL COMMENT '资源表ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_role_res` */

/*Table structure for table `sys_user_usergroup` */

DROP TABLE IF EXISTS `sys_user_usergroup`;

CREATE TABLE `sys_user_usergroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `usergroup_id` bigint(20) NOT NULL COMMENT '用户组ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_user_usergroup` */

/*Table structure for table `sys_usergroup` */

DROP TABLE IF EXISTS `sys_usergroup`;

CREATE TABLE `sys_usergroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL COMMENT '组描述',
  `usergroup_code` varchar(32) NOT NULL COMMENT '组编号',
  `usergroup_name` varchar(256) NOT NULL COMMENT '组名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_usergroup` */

/*Table structure for table `sys_usergroup_role` */

DROP TABLE IF EXISTS `sys_usergroup_role`;

CREATE TABLE `sys_usergroup_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `usergroup_id` bigint(20) NOT NULL COMMENT '用户组ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_usergroup_role` */

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

/*Table structure for table `web_article` */

DROP TABLE IF EXISTS `web_article`;

CREATE TABLE `web_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `article_author` varchar(64) DEFAULT NULL COMMENT '作者',
  `article_click` int(11) DEFAULT NULL COMMENT '点击数',
  `article_content` text DEFAULT NULL COMMENT '网站内容',
  `article_keywords` varchar(512) DEFAULT NULL COMMENT '关键词',
  `article_status` int(11) NOT NULL COMMENT '审核状态',
  `article_title` varchar(512) NOT NULL COMMENT '新闻标题',
  `index_show` int(11) DEFAULT NULL COMMENT '首页显示',
  `nav_id` int(11) DEFAULT NULL COMMENT '栏目ID',
  `title_img` varchar(512) DEFAULT NULL COMMENT '标题图',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_article` */

/*Table structure for table `web_case` */

DROP TABLE IF EXISTS `web_case`;

CREATE TABLE `web_case` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `case_content` text DEFAULT NULL COMMENT '内容',
  `case_name` varchar(64) NOT NULL COMMENT '案例名称',
  `case_picture` varchar(256) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_case` */

/*Table structure for table `web_content` */

DROP TABLE IF EXISTS `web_content`;

CREATE TABLE `web_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `content_key` varchar(32) NOT NULL COMMENT '内容位置',
  `content_main` longtext NOT NULL COMMENT '内容正文',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_content` */

/*Table structure for table `web_dict` */

DROP TABLE IF EXISTS `web_dict`;

CREATE TABLE `web_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `dict_code` varchar(64) NOT NULL COMMENT '编码',
  `dict_desc` varchar(512) DEFAULT NULL COMMENT '字段描述',
  `dict_name` varchar(256) NOT NULL COMMENT '名称',
  `dict_order` int(11) DEFAULT NULL COMMENT '字段排序',
  `parent_id` int(22) NOT NULL COMMENT '上级ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_dict` */

/*Table structure for table `web_nav` */

DROP TABLE IF EXISTS `web_nav`;

CREATE TABLE `web_nav` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `nav_title` varchar(64) NOT NULL COMMENT '栏目名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级栏目',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_nav` */

/*Table structure for table `web_nav_article` */

DROP TABLE IF EXISTS `web_nav_article`;

CREATE TABLE `web_nav_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `article_id` int(11) NOT NULL COMMENT '新闻ID',
  `nav_id` int(11) DEFAULT NULL COMMENT '栏目ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_nav_article` */

/*Table structure for table `web_product` */

DROP TABLE IF EXISTS `web_product`;

CREATE TABLE `web_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `nav_id` int(11) DEFAULT NULL COMMENT '栏目ID',
  `product_content` text DEFAULT NULL COMMENT '内容',
  `product_picture` varchar(256) DEFAULT NULL COMMENT '缩略图',
  `product_subtitle` varchar(512) DEFAULT NULL COMMENT '副标题',
  `product_title` varchar(512) NOT NULL COMMENT '标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_product` */

/*Table structure for table `web_setting` */

DROP TABLE IF EXISTS `web_setting`;

CREATE TABLE `web_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `record_version` bigint(20) DEFAULT 0,
  `sys_create_time` datetime DEFAULT NULL,
  `sys_create_user` varchar(255) DEFAULT NULL,
  `sys_update_time` datetime DEFAULT NULL,
  `sys_update_user` varchar(255) DEFAULT NULL,
  `setting_key` varchar(64) NOT NULL COMMENT '配置项',
  `setting_value` varchar(512) NOT NULL COMMENT '配置内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `web_setting` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
