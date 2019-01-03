-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: nikken_db
-- ------------------------------------------------------
-- Server version	5.7.22

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
-- Table structure for table `adminprocesos`
--

DROP TABLE IF EXISTS `adminprocesos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adminprocesos` (
  `idAdmin` int(11) NOT NULL AUTO_INCREMENT,
  `tipoProceso` varchar(45) DEFAULT NULL,
  `indHorario` char(1) DEFAULT NULL,
  `indEjecucion` char(1) DEFAULT NULL,
  `estadoProceso` varchar(45) DEFAULT NULL,
  `copiaCarbon` text,
  `fechaManual` date DEFAULT NULL,
  `paisPrueba` varchar(45) DEFAULT NULL,
  `correosPrueba` text,
  `usuarioModif` varchar(45) DEFAULT NULL,
  `fechaModif` date DEFAULT NULL,
  PRIMARY KEY (`idAdmin`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `horarios`
--

DROP TABLE IF EXISTS `horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `horarios` (
  `idHorarios` int(11) NOT NULL AUTO_INCREMENT,
  `horario` time DEFAULT NULL,
  `idAdmin` int(11) NOT NULL,
  PRIMARY KEY (`idHorarios`,`idAdmin`),
  KEY `fk_horarios_adminprocesos_idx` (`idAdmin`),
  CONSTRAINT `fk_horarios_adminprocesos` FOREIGN KEY (`idAdmin`) REFERENCES `adminprocesos` (`idAdmin`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `intervalos`
--

DROP TABLE IF EXISTS `intervalos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `intervalos` (
  `idIntervalos` int(11) NOT NULL AUTO_INCREMENT,
  `horaInicial` time DEFAULT NULL,
  `horaFinal` time DEFAULT NULL,
  `intervalo` int(11) DEFAULT NULL,
  `idAdmin` int(11) NOT NULL,
  PRIMARY KEY (`idIntervalos`,`idAdmin`),
  KEY `fk_intervalos_adminprocesos1_idx` (`idAdmin`),
  CONSTRAINT `fk_intervalos_adminprocesos1` FOREIGN KEY (`idAdmin`) REFERENCES `adminprocesos` (`idAdmin`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logprocdetalle`
--

DROP TABLE IF EXISTS `logprocdetalle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logprocdetalle` (
  `idLogprocdetalle` int(11) NOT NULL AUTO_INCREMENT,
  `numAsesor` varchar(45) DEFAULT NULL,
  `correoAsesor` varchar(105) DEFAULT NULL,
  `tipoDocumento` varchar(45) DEFAULT NULL,
  `numDocumento` varchar(45) DEFAULT NULL,
  `estadoRegistro` varchar(45) DEFAULT NULL,
  `motivoNoEnvio` varchar(45) DEFAULT NULL,
  `idLogprocesos` int(11) NOT NULL,
  PRIMARY KEY (`idLogprocdetalle`,`idLogprocesos`),
  KEY `fk_logprocdetalle_logprocesos1_idx` (`idLogprocesos`),
  CONSTRAINT `fk_logprocdetalle_logprocesos1` FOREIGN KEY (`idLogprocesos`) REFERENCES `logprocesos` (`idLogprocesos`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2105 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `logprocesos`
--

DROP TABLE IF EXISTS `logprocesos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logprocesos` (
  `idLogprocesos` int(11) NOT NULL AUTO_INCREMENT,
  `fechaEjecucion` datetime DEFAULT NULL,
  `fechaTerminado` datetime DEFAULT NULL,
  `totalRegistros` int(11) DEFAULT NULL,
  `totalCorrectos` int(11) DEFAULT NULL,
  `totalErrores` int(11) DEFAULT NULL,
  `comentario` text,
  `idAdmin` int(11) NOT NULL,
  PRIMARY KEY (`idLogprocesos`,`idAdmin`),
  KEY `fk_logprocesos_adminprocesos1_idx` (`idAdmin`),
  CONSTRAINT `fk_logprocesos_adminprocesos1` FOREIGN KEY (`idAdmin`) REFERENCES `adminprocesos` (`idAdmin`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nikken_data`
--

DROP TABLE IF EXISTS `nikken_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nikken_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CardCode` varchar(25) DEFAULT NULL,
  `DocNum` varchar(250) DEFAULT NULL,
  `U_Orden` varchar(25) DEFAULT NULL,
  `U_Estafeta` varchar(1000) DEFAULT NULL,
  `ItemCode` varchar(25) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `Quantity` varchar(25) DEFAULT NULL,
  `E_Mail` varchar(500) DEFAULT NULL,
  `Fecha_documento` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4202 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nikken_data_summary`
--

DROP TABLE IF EXISTS `nikken_data_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `nikken_data_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `CardCode` varchar(25) DEFAULT NULL,
  `U_Orden` varchar(25) DEFAULT NULL,
  `U_Estafeta` varchar(1000) DEFAULT NULL,
  `Fecha_documento` varchar(25) DEFAULT NULL,
  `E_Mail` varchar(500) DEFAULT NULL,
  `total_items` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1053 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `publicidad`
--

DROP TABLE IF EXISTS `publicidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publicidad` (
  `idPublicidad` int(11) NOT NULL,
  `paisPublic` varchar(45) DEFAULT NULL,
  `urlFacebook` varchar(255) DEFAULT NULL,
  `urlInstagram` varchar(255) DEFAULT NULL,
  `urlTwitter` varchar(255) DEFAULT NULL,
  `urlYoutube` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idPublicidad`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'nikken_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-27 23:46:59
