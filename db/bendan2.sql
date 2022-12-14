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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COMMENT='菜单(权限)表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bendan_sys_menu`
--

LOCK TABLES `bendan_sys_menu` WRITE;
/*!40000 ALTER TABLE `bendan_sys_menu` DISABLE KEYS */;
INSERT INTO `bendan_sys_menu` VALUES (1,0,'/','用户管理',0,NULL,0,0,'team-outlined',2,2,'2022-12-05 02:18:52','2022-12-14 15:19:43'),(2,2,NULL,'用户新增',1,'sys_user_add',0,0,NULL,2,2,'2022-12-05 02:18:52','2022-12-05 02:18:52'),(3,2,NULL,'用户查询',1,'sys_user_query',0,0,NULL,2,2,'2022-12-05 02:18:52','2022-12-05 02:18:52'),(4,2,NULL,'用户删除',1,'sys_user_del',0,0,NULL,2,2,'2022-12-05 02:18:52','2022-12-05 02:18:52'),(5,2,NULL,'用户修改',1,'sys_user_edit',0,0,NULL,2,2,'2022-12-05 02:18:52','2022-12-05 02:18:52');
/*!40000 ALTER TABLE `bendan_sys_menu` ENABLE KEYS */;
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
INSERT INTO `bendan_sys_role_menu` VALUES (1,1);
/*!40000 ALTER TABLE `bendan_sys_role_menu` ENABLE KEYS */;
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
INSERT INTO `bendan_sys_user_role` VALUES (2,1);
/*!40000 ALTER TABLE `bendan_sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-14 16:58:29
