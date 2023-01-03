-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bendan
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bendan_chat_record`
--

DROP TABLE IF EXISTS `bendan_chat_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_chat_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `from_id` bigint DEFAULT NULL COMMENT '自己id',
  `to_id` bigint DEFAULT NULL COMMENT '用户id',
  `send_content` text NOT NULL COMMENT '发送内容',
  `send_type` tinyint DEFAULT NULL COMMENT '发送类型【0文本，1图片，2语音，3视频】',
  `send_time_length` bigint DEFAULT NULL COMMENT '发送时长',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天记录表\r\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_chat_record`
--

LOCK TABLES `bendan_chat_record` WRITE;
/*!40000 ALTER TABLE `bendan_chat_record` DISABLE KEYS */;
INSERT INTO `bendan_chat_record` VALUES (1,1,1,'打撒打撒阿德 ',0,NULL,'2022-12-29 20:36:56'),(2,1,6,'大苏打盛大的',0,NULL,'2022-12-29 21:50:03'),(3,1,6,'大大阿达是不不不',0,NULL,'2022-12-29 21:50:26'),(4,1,6,'大大是',0,NULL,'2022-12-29 22:35:25'),(5,1,6,'大大是端点 ',0,NULL,'2022-12-29 22:36:16'),(6,1,2,'大大撒旦',0,NULL,'2022-12-29 22:40:23'),(7,1,3,'的撒旦撒旦',0,NULL,'2022-12-29 22:46:01'),(8,2,1,'大苏打大',0,NULL,'2022-12-29 23:27:53'),(9,2,1,'的撒大大',0,NULL,'2022-12-29 23:28:01'),(10,2,1,'阿松大大大大',0,NULL,'2022-12-29 23:30:37'),(11,2,1,'的撒大大大大',0,NULL,'2022-12-29 23:31:12'),(12,2,1,'活的挺好的很好的活动分工的',0,NULL,'2022-12-29 23:35:49'),(13,2,1,'大苏打萨达',0,NULL,'2022-12-29 23:37:10'),(14,1,3,'阿三大苏打撒旦倒萨倒萨',0,NULL,'2022-12-29 23:37:14'),(15,1,3,'dasdad ',0,NULL,'2022-12-30 14:43:28'),(16,1,2,'dasdasdsa ',0,NULL,'2022-12-30 14:53:47'),(17,1,5,'dsadsadad',0,NULL,'2022-12-30 14:59:27'),(18,1,5,'dsadsadasd',0,NULL,'2022-12-30 16:36:02'),(19,1,5,'dsadqweqwewewewwq',0,NULL,'2022-12-30 16:36:09'),(20,1,6,'jkbbjkjkjkhjhj',0,NULL,'2022-12-30 18:45:17'),(21,1,2,'wengx',0,NULL,'2022-12-30 20:01:16'),(22,1,2,'大苏打大',0,NULL,'2022-12-30 21:00:23'),(23,1,2,'大大撒旦',0,NULL,'2022-12-30 21:00:30'),(24,1,2,'大苏打萨达',0,NULL,'2022-12-30 21:00:44'),(25,1,2,'你好',0,NULL,'2022-12-30 22:39:28'),(26,1,2,'老六',0,NULL,'2022-12-30 22:46:22'),(27,2,1,'gg',0,NULL,'2022-12-30 22:47:09'),(28,2,1,'Astaire',0,NULL,'2022-12-30 22:50:16'),(29,2,1,'的点点滴滴',0,NULL,'2022-12-30 22:57:27'),(30,2,1,'ddddd',0,NULL,'2022-12-30 23:00:33'),(31,2,1,'dasddddadasda',0,NULL,'2022-12-30 23:01:03'),(32,1,2,'顶顶顶顶顶',0,NULL,'2022-12-30 23:04:35'),(33,2,1,'lol',0,NULL,'2022-12-30 23:10:24'),(34,1,2,'大时代圣埃蒂安',0,NULL,'2022-12-30 23:12:15'),(35,2,1,'罗丹是猪\n',0,NULL,'2023-01-01 20:58:39'),(36,1,2,'dsdsada',0,NULL,'2023-01-01 21:15:41'),(37,1,2,'ddddd ',0,NULL,'2023-01-01 21:18:24'),(38,2,1,'warning',0,NULL,'2023-01-01 21:18:35'),(39,1,2,'你哈皮',0,NULL,'2023-01-01 21:20:12'),(44,1,2,'的撒大大',0,NULL,'2023-01-02 00:08:50'),(45,1,2,'大大大大大',0,NULL,'2023-01-02 00:08:54');
/*!40000 ALTER TABLE `bendan_chat_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_oss`
--

DROP TABLE IF EXISTS `bendan_oss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_oss` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `url` text NOT NULL COMMENT '文件路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_oss`
--

LOCK TABLES `bendan_oss` WRITE;
/*!40000 ALTER TABLE `bendan_oss` DISABLE KEYS */;
INSERT INTO `bendan_oss` VALUES (1,'1-派克特 - 你我他.mp3','http://175.178.183.32:9000/bendan/1-%E6%B4%BE%E5%85%8B%E7%89%B9%20-%20%E4%BD%A0%E6%88%91%E4%BB%96.mp3?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioroot%2F20230103%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20230103T031937Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=8542814ff3a2ec9ab60f701cfeb0d4b5a2b640b169464cb02562ff824ef10e91','2023-01-03 11:19:37','2023-01-03 11:19:37');
/*!40000 ALTER TABLE `bendan_oss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_menu`
--

DROP TABLE IF EXISTS `bendan_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '菜单父ID',
  `path` varchar(64) DEFAULT NULL COMMENT '路径',
  `name` varchar(64) NOT NULL COMMENT '菜单名',
  `level` int NOT NULL DEFAULT '0' COMMENT '层级',
  `purview_name` varchar(48) DEFAULT NULL COMMENT '权限标识名',
  `status` int DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--关闭 2--删除)',
  `sort` int DEFAULT '0' COMMENT '排序;数字越越靠后',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `create_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_id` bigint DEFAULT NULL COMMENT '更新人id',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bendan_sys_menu_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 COMMENT='菜单(权限)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_menu`
--

LOCK TABLES `bendan_sys_menu` WRITE;
/*!40000 ALTER TABLE `bendan_sys_menu` DISABLE KEYS */;
INSERT INTO `bendan_sys_menu` VALUES (17,0,'/home/index','首页',0,NULL,0,0,'HomeOutlined',NULL,NULL,'2022-12-20 13:43:33','2022-12-20 13:43:33'),(18,0,'/auth','权限管理',0,NULL,0,1,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(19,18,'/auth/user','用户管理',0,NULL,0,1,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(20,18,'/auth/role','角色管理',0,NULL,0,2,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(21,18,'/auth/menu','菜单管理',0,NULL,0,3,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34'),(22,0,'/chat','聊天室',0,NULL,0,3,'PaperClipOutlined',NULL,NULL,'2022-12-20 13:43:34','2022-12-20 13:43:34');
/*!40000 ALTER TABLE `bendan_sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_role`
--

DROP TABLE IF EXISTS `bendan_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) NOT NULL COMMENT '角色名字',
  `code` varchar(64) NOT NULL COMMENT '角色码',
  `role_describe` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `del` int DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
  `create_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_id` bigint DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bendan_sys_role_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_role`
--

LOCK TABLES `bendan_sys_role` WRITE;
/*!40000 ALTER TABLE `bendan_sys_role` DISABLE KEYS */;
INSERT INTO `bendan_sys_role` VALUES (1,'管理员','ROLE_ADMIN','管理员',0,2,2,'2022-12-01 20:12:31','2022-12-01 20:12:31'),(2,'普通用户','GENERAL_USER','普通用户',0,2,2,'2022-12-01 20:12:31','2022-12-01 20:12:31');
/*!40000 ALTER TABLE `bendan_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_role_menu`
--

DROP TABLE IF EXISTS `bendan_sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_role_menu`
--

LOCK TABLES `bendan_sys_role_menu` WRITE;
/*!40000 ALTER TABLE `bendan_sys_role_menu` DISABLE KEYS */;
INSERT INTO `bendan_sys_role_menu` VALUES (1,17),(1,18),(1,19),(1,20),(1,21),(1,22);
/*!40000 ALTER TABLE `bendan_sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_user`
--

DROP TABLE IF EXISTS `bendan_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `phone_number` varchar(16) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `email` varchar(64) NOT NULL COMMENT '邮箱',
  `status` int DEFAULT '0' COMMENT '用户状态（0-正常  1-锁定  2-删除）',
  `gender` int DEFAULT '-1' COMMENT '性别   (-1 未知 0 女性  1 男性)',
  `create_id` bigint DEFAULT NULL COMMENT '创建人id',
  `update_id` bigint DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_user`
--

LOCK TABLES `bendan_sys_user` WRITE;
/*!40000 ALTER TABLE `bendan_sys_user` DISABLE KEYS */;
INSERT INTO `bendan_sys_user` VALUES (1,'admin','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','admin',NULL,'10086',NULL,'obeast.gym@gmail.com',0,-1,NULL,1,'2022-12-13 11:37:42','2022-12-06 15:47:01'),(2,'user','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','user',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,2,2,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(3,'test1','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test1',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,2,2,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(4,'test2','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test2',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,2,2,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(5,'test3','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test3',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,2,2,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(6,'test4','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test4',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,2,2,'2022-12-11 13:34:20','2022-12-10 16:13:41'),(7,'test5','{bcrypt}$2a$10$fCA278EjJnQ0E818k/W7ceLAINeuz4rFXzFzEaKL5gdP5cEIViCSK','test5',NULL,'10086111008','http://dummyimage.com/100x100','t.wss@qq.com',0,-1,2,2,'2022-12-11 13:34:20','2022-12-10 16:13:41');
/*!40000 ALTER TABLE `bendan_sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bendan_sys_user_role`
--

DROP TABLE IF EXISTS `bendan_sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bendan_sys_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_user_role`
--

LOCK TABLES `bendan_sys_user_role` WRITE;
/*!40000 ALTER TABLE `bendan_sys_user_role` DISABLE KEYS */;
INSERT INTO `bendan_sys_user_role` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `bendan_sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend_rel`
--

DROP TABLE IF EXISTS `friend_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friend_rel` (
  `user_a` bigint DEFAULT NULL,
  `user_b` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend_rel`
--

LOCK TABLES `friend_rel` WRITE;
/*!40000 ALTER TABLE `friend_rel` DISABLE KEYS */;
INSERT INTO `friend_rel` VALUES (1,2),(1,3),(4,1),(1,5),(1,6),(2,3);
/*!40000 ALTER TABLE `friend_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_registered_client`
--

DROP TABLE IF EXISTS `oauth2_registered_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_registered_client` (
  `id` varchar(100) NOT NULL,
  `client_id` varchar(100) NOT NULL,
  `client_id_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `client_secret` varchar(200) DEFAULT NULL,
  `client_secret_expires_at` timestamp NULL DEFAULT NULL,
  `client_name` varchar(200) NOT NULL,
  `client_authentication_methods` varchar(1000) NOT NULL,
  `authorization_grant_types` varchar(1000) NOT NULL,
  `redirect_uris` varchar(1000) DEFAULT NULL,
  `scopes` varchar(1000) NOT NULL,
  `client_settings` varchar(2000) NOT NULL,
  `token_settings` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_registered_client`
--

LOCK TABLES `oauth2_registered_client` WRITE;
/*!40000 ALTER TABLE `oauth2_registered_client` DISABLE KEYS */;
INSERT INTO `oauth2_registered_client` VALUES ('81d68e38-9cc3-4534-b1d7-4dc812a38c5b','web','2022-12-07 05:13:19','{bcrypt}$2a$10$tRLdMGxOH/Mzg7Tv89lOBetliDlZgRN4ltIviv4YQrSwBs0sP6fOm',NULL,'81d68e38-9cc3-4534-b1d7-4dc812a38c5b','client_secret_basic','refresh_token,password,client_credentials,authorization_code','http://127.0.0.1:18812/authorized','all,openid,message.read,message.write','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",43200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",259200.000000000]}');
/*!40000 ALTER TABLE `oauth2_registered_client` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-03 11:30:27
