-- MySQL dump 10.13  Distrib 5.5.23, for Win64 (x86)
--
-- Host: localhost    Database: payments_system
-- ------------------------------------------------------
-- Server version	5.5.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id_account` bigint(20) NOT NULL AUTO_INCREMENT,
  `balance` double DEFAULT NULL,
  `account_state` enum('LOCKED','WORKING','DELETED') DEFAULT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_account`),
  KEY `FK_mfisqiulqmeyv1vpx65qqcbvq` (`id_user`),
  CONSTRAINT `FK_mfisqiulqmeyv1vpx65qqcbvq` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,756,'LOCKED',1),(2,-44,'WORKING',5),(3,200,'LOCKED',23),(4,1000,'WORKING',23),(5,500,'WORKING',23),(6,1000,'WORKING',24),(7,490,'WORKING',24),(8,0,'WORKING',NULL),(9,49960,'LOCKED',24),(10,200,'WORKING',26),(11,700,'WORKING',26),(12,300,'WORKING',26),(13,1100,'WORKING',26),(14,500,'WORKING',26);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id_user` bigint(20) NOT NULL,
  `city` varchar(15) DEFAULT NULL,
  `flat` varchar(5) DEFAULT NULL,
  `home` varchar(7) DEFAULT NULL,
  `street` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Minsk','105','6','Yankovskogo'),(3,'Zodino','2','1','lllddhh'),(21,'Minsk','105','6','Yankovskogo'),(22,'dfgh','5','4','fhh'),(23,'hjk','6','5','ghj'),(24,'dghj','6','5','fghj'),(25,'fghj','6','5','dfghj'),(26,'Minsk','6','5','Goreckogo');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passport`
--

DROP TABLE IF EXISTS `passport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passport` (
  `id_user` bigint(20) NOT NULL,
  `date_of_issue` date DEFAULT NULL,
  `issued` varchar(50) DEFAULT NULL,
  `number` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passport`
--

LOCK TABLES `passport` WRITE;
/*!40000 ALTER TABLE `passport` DISABLE KEYS */;
INSERT INTO `passport` VALUES (1,'2014-05-15','Minskiy ROVD','MP1234567'),(3,'2014-05-23','Godinski GOVD','MC23456745'),(21,'2014-05-15','Minskiy ROVD','MP1234567'),(22,NULL,'Godinskim Govd','MC34567894'),(23,NULL,'Godinskim Govd','MC34567894'),(24,NULL,'Godinskim Govd','MC34567894'),(25,'2016-09-12','Godinskim Govd','MC34567894'),(26,'2016-09-04','Godinskim Govd','MC34567894');
/*!40000 ALTER TABLE `passport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `id_payment` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount_payment` double DEFAULT NULL,
  `description` varchar(120) DEFAULT NULL,
  `pay_date` date DEFAULT NULL,
  `id_account_destination` bigint(20) DEFAULT NULL,
  `id_account_source` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_payment`),
  KEY `FK_gv89tybue65ek7y2uh6l7rv5i` (`id_account_destination`),
  KEY `FK_c59dyicb5yc16afrobpgmtpgt` (`id_account_source`),
  CONSTRAINT `FK_c59dyicb5yc16afrobpgmtpgt` FOREIGN KEY (`id_account_source`) REFERENCES `account` (`id_account`),
  CONSTRAINT `FK_gv89tybue65ek7y2uh6l7rv5i` FOREIGN KEY (`id_account_destination`) REFERENCES `account` (`id_account`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,200,'Money transfer','2016-10-03',12,10),(2,500,'Money transfer','2016-10-03',14,13),(3,100,'Money transfer','2016-10-03',12,10);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id_role` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(20) DEFAULT NULL,
  `first_name` varchar(15) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `login` varchar(10) DEFAULT NULL,
  `middle_name` varchar(15) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `id_role` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `UK_ew1hvam8uwaknuaellwhqchhb` (`login`),
  KEY `FK_k8y42vh44lwf9u6ypkgyjpf4k` (`id_role`),
  CONSTRAINT `FK_k8y42vh44lwf9u6ypkgyjpf4k` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ret@mail.ru','Anna','Ivanova','ter','Antonovna','3af8212b2bee9ac54115a6fc5d455ca8','375447547878',2),(5,'ret@mail.ru','Anna','Ivanova','qwerty','Antonovna','3af8212b2bee9ac54115a6fc5d45','375447547878',2),(23,'ekat.novik@gmail.com','dfg','fgh','zxc','fgh','$2a$10$DB7qfjHH34MvKGUVEyCUzeqlBq2OHLx5a75a6G4qJzYuqexndnbyq','375447543800',2),(24,'ekat.novik@gmail.com','dffgh','fghj','abc','fghj','$2a$10$5twVG2q4maktfbPrI8DmseGEskB/IcPCnONWn10rA9u5YRK9hkenO','375447543800',2),(26,'ekat.novik@gmail.com','Kate','Novik','user','Igorevna','$2a$10$YhpboNNP3wUTcVnAYYQN8OuaAiIZ5KnUogf2Y0Bko.ELBUrVA..RW','375447543800',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-03 16:26:01
