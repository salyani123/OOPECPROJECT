-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 01, 2023 at 07:44 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parking_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `parking_lot`
--

CREATE TABLE `parking_lot` (
  `ID` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `capacity` int(11) NOT NULL,
  `is_open` tinyint(1) NOT NULL,
  `occupied_slots` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `parking_pass`
--

CREATE TABLE `parking_pass` (
  `ID` varchar(36) NOT NULL,
  `vehicle_id` varchar(36) NOT NULL,
  `valid_from` timestamp NULL DEFAULT NULL,
  `valid_to` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `parking_slot`
--

CREATE TABLE `parking_slot` (
  `ID` varchar(36) NOT NULL,
  `number` int(11) NOT NULL,
  `parking_lot_id` varchar(36) NOT NULL,
  `is_occupied` tinyint(1) NOT NULL DEFAULT 0,
  `vehicle_id` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `ID` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `ID` varchar(36) NOT NULL,
  `license_plate` varchar(20) NOT NULL,
  `student_id` varchar(36) NOT NULL,
  `make` varchar(20) NOT NULL,
  `model` varchar(20) NOT NULL,
  `color` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `parking_lot`
--
ALTER TABLE `parking_lot`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `parking_pass`
--
ALTER TABLE `parking_pass`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `vehicle_id` (`vehicle_id`);

--
-- Indexes for table `parking_slot`
--
ALTER TABLE `parking_slot`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `parking_lot_id` (`parking_lot_id`),
  ADD KEY `vehicle_id` (`vehicle_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `student_id` (`student_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `parking_pass`
--
ALTER TABLE `parking_pass`
  ADD CONSTRAINT `parking_pass_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`ID`);

--
-- Constraints for table `parking_slot`
--
ALTER TABLE `parking_slot`
  ADD CONSTRAINT `parking_slot_ibfk_1` FOREIGN KEY (`parking_lot_id`) REFERENCES `parking_lot` (`ID`),
  ADD CONSTRAINT `parking_slot_ibfk_2` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`ID`);

--
-- Constraints for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `vehicle_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
