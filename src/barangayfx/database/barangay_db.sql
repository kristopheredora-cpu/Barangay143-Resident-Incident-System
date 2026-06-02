-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 02, 2026 at 08:22 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `barangay_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `incidents`
--

CREATE TABLE `incidents` (
  `id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `reported_by` varchar(100) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Open',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `incidents`
--

INSERT INTO `incidents` (`id`, `description`, `location`, `reported_by`, `status`, `created_at`) VALUES
(1, 'Noise complaint', 'Blk 4, Purok 3', 'Maria Santos', 'Open', '2026-04-20 07:15:35'),
(2, 'Flooding on road', 'Purok 2', 'Jose Dela Cruz', 'Pending', '2026-04-20 07:15:35'),
(3, 'Broken streetlight', 'Purok 1, Blk 2', 'Ana Reyes', 'Resolved', '2026-04-20 07:15:35'),
(7, 'Noise complaint', 'Blk 4 Purok 1', 'Faith Robles', 'Open', '2026-04-22 16:41:42'),
(8, 'Lost item', 'Market Area', 'Noel Fajardo', 'Resolved', '2026-04-22 16:41:42'),
(9, 'Street obstruction', 'Main Road', 'Shiela Padilla', 'Open', '2026-04-22 16:41:42'),
(10, 'Suspicious activity', 'Barangay Hall', 'Vincent Alonzo', 'Open', '2026-04-22 16:41:42'),
(11, 'Vandalism', 'Covered Court', 'Trisha Mercado', 'Open', '2026-04-22 16:41:42'),
(12, 'Theft report', 'Sari-sari Store', 'Ronald Chavez', 'Open', '2026-04-22 16:41:42'),
(13, 'Illegal parking', 'School Zone', 'Camille Soriano', 'Open', '2026-04-22 16:41:42'),
(14, 'Public disturbance', 'Basketball Court', 'Edgar Luna', 'Open', '2026-04-22 16:41:42'),
(15, 'Lost pet', 'Purok 2', 'Joy Lim', 'Open', '2026-04-22 16:41:42'),
(16, 'Garbage issue', 'Riverside', 'Arvin Tan', 'Open', '2026-04-22 16:41:42'),
(17, 'Water leak', 'Blk 6', 'Hazel Ong', 'Open', '2026-04-22 16:41:42'),
(18, 'Power outage', 'Purok 3', 'Dennis Sy', 'Open', '2026-04-22 16:41:42'),
(19, 'Road damage', 'Highway', 'Irene Go', 'Open', '2026-04-22 16:41:42'),
(20, 'Trespassing', 'Private Lot', 'Patrick Yu', 'Open', '2026-04-22 16:41:42'),
(21, 'Fire incident', 'Kitchen Area', 'Rose Tan', 'Open', '2026-04-22 16:41:42'),
(22, 'Animal nuisance', 'Backyard Area', 'Albert Chua', 'Open', '2026-04-22 16:41:42'),
(23, 'Noise complaint', 'Purok 5', 'Grace Co', 'Open', '2026-04-22 16:41:42'),
(24, 'Illegal vending', 'Sidewalk', 'Leo Ang', 'Open', '2026-04-22 16:41:42'),
(25, 'Stolen Tricycle', 'Purok 2', 'Rodrigo De Leon', 'Open', '2026-05-07 15:06:56');

-- --------------------------------------------------------

--
-- Table structure for table `inventory`
--

CREATE TABLE `inventory` (
  `id` int(11) NOT NULL,
  `item_name` varchar(150) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `stock_status` varchar(50) NOT NULL DEFAULT 'In Stock',
  `location` varchar(100) DEFAULT NULL,
  `last_updated` date DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `inventory`
--

INSERT INTO `inventory` (`id`, `item_name`, `quantity`, `stock_status`, `location`, `last_updated`, `notes`) VALUES
(1, 'First Aid Kit', 5, 'In Stock', 'Storage A', '2026-04-23', ''),
(2, 'Rice (50kg sacks)', 25, 'In Stock', 'Storage Room A', '2026-04-20', 'For relief distribution'),
(3, 'Canned Goods (Assorted)', 120, 'In Stock', 'Storage Room A', '2026-04-21', 'Includes sardines and corned beef'),
(4, 'Bottled Water (1L)', 200, 'In Stock', 'Storage Room B', '2026-04-21', 'Emergency supply'),
(5, 'Face Masks (Boxes)', 75, 'In Stock', 'Health Center Cabinet', '2026-04-22', 'For health programs'),
(6, 'Alcohol (500ml)', 60, 'Low Stock', 'Health Center Cabinet', '2026-04-22', 'Needs restock soon'),
(7, 'First Aid Kits', 15, 'In Stock', 'Health Center Cabinet', '2026-04-18', 'Complete kits'),
(8, 'Plastic Chairs', 100, 'In Stock', 'Barangay Hall', '2026-04-15', 'Used for events'),
(9, 'Folding Tables', 20, 'In Stock', 'Barangay Hall Storage', '2026-04-15', 'For meetings'),
(10, 'Fire Extinguishers', 10, 'In Stock', 'Hallway Corners', '2026-04-10', 'Checked and functional'),
(11, 'Flashlights', 30, 'In Stock', 'Emergency Cabinet', '2026-04-19', 'Battery included'),
(12, 'Batteries (AA)', 150, 'In Stock', 'Emergency Cabinet', '2026-04-19', 'For flashlights'),
(13, 'Megaphone', 5, 'In Stock', 'Office Room', '2026-04-17', 'Used for announcements'),
(14, 'Logbooks', 40, 'In Stock', 'Office Room', '2026-04-16', 'For records'),
(15, 'Ballpens (Boxes)', 25, 'In Stock', 'Office Room', '2026-04-16', 'Office supplies'),
(16, 'Printer Ink', 8, 'Low Stock', 'Office Room', '2026-04-18', 'Black and colored ink'),
(17, 'Cleaning Supplies', 35, 'In Stock', 'Janitor Closet', '2026-04-20', 'Includes detergents'),
(18, 'Garbage Bags (Large)', 100, 'In Stock', 'Janitor Closet', '2026-04-20', 'For waste management'),
(19, 'Wheelchairs', 3, 'In Stock', 'Health Center', '2026-04-12', 'For emergency use'),
(20, 'Stretchers', 2, 'In Stock', 'Health Center', '2026-04-12', 'Emergency transport'),
(21, 'Generator', 1, 'In Stock', 'Equipment Room', '2026-04-05', 'Backup power'),
(22, 'Extension Wires', 12, 'In Stock', 'Equipment Room', '2026-04-14', 'For events'),
(23, 'Tarpaulins', 10, 'In Stock', 'Storage Room B', '2026-04-11', 'Reusable'),
(24, 'Raincoats', 25, 'In Stock', 'Emergency Cabinet', '2026-04-19', 'For disaster response'),
(25, 'Boots (Pairs)', 20, 'In Stock', 'Emergency Cabinet', '2026-04-19', 'Various sizes'),
(26, 'Whistles', 50, 'In Stock', 'Emergency Cabinet', '2026-04-19', 'For volunteers'),
(27, 'First Aid Kits', 12, 'In Stock', 'Storage A', '2026-04-20', 'Check expiry dates monthly'),
(28, 'Megaphone', 3, 'In Stock', 'Storage B', '2026-04-18', ''),
(29, 'Folding Tables', 8, 'In Stock', 'Barangay Hall', '2026-04-15', ''),
(30, 'Plastic Chairs', 25, 'In Stock', 'Barangay Hall', '2026-04-15', ''),
(31, 'Fire Extinguishers', 2, 'Low Stock', 'Storage A', '2026-04-10', 'Request refill soon'),
(32, 'Garbage Bags (box)', 1, 'Low Stock', 'Storage C', '2026-04-19', 'Order 5 more boxes');

-- --------------------------------------------------------

--
-- Table structure for table `residents`
--

CREATE TABLE `residents` (
  `id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthdate` varchar(20) DEFAULT NULL,
  `contact_no` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Active',
  `gender` varchar(10) DEFAULT 'Male',
  `civil_status` varchar(20) DEFAULT 'Single',
  `occupation` varchar(100) DEFAULT '',
  `email` varchar(100) DEFAULT '',
  `emergency_contact` varchar(100) DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `date_registered` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `residents`
--

INSERT INTO `residents` (`id`, `full_name`, `address`, `birthdate`, `contact_no`, `status`, `gender`, `civil_status`, `occupation`, `email`, `emergency_contact`, `created_at`, `date_registered`) VALUES
(1, 'Maria Santos', 'Purok 1, Barangay 143', '1959-06-15', '09123456789', 'Active', 'Female', 'Single', 'Retired', 'maria.santos1@gmail.com', '09685525273', '2026-04-20 07:15:35', '2026-04-24'),
(2, 'Jose Dela Cruz', 'Blk 5 Purok 2', '1990-03-22', '09234567890', 'Pending', 'Male', 'Single', 'Vendor', 'jose.dela.cruz2@gmail.com', '09101825182', '2026-04-20 07:15:35', '2026-04-22'),
(3, 'Ana Reyes', 'Purok 3, Blk 1', '1991-09-10', '09345678901', 'Active', 'Female', 'Single', 'Teacher', 'ana.reyes3@gmail.com', '09636833609', '2026-04-20 07:15:35', '2026-04-28'),
(56, 'Pedro Reyes', 'Purok 3, Barangay 143', '1992-08-10', '09000235950', 'Active', 'Male', 'Single', 'Teacher', 'pedro.reyes56@gmail.com', '09697410296', '2026-04-22 09:41:00', '2026-05-01'),
(57, 'Mark Garcia', 'Purok 2, Barangay 143', '1992-12-20', '09451998269', 'Pending', 'Male', 'Married', 'Office Staff', 'mark.garcia57@gmail.com', '09998317737', '2026-04-22 09:41:00', '2026-05-01'),
(58, 'Carla Mendoza', 'Purok 3, Barangay 143', '1992-03-28', '09623327983', 'Active', 'Female', 'Single', 'Office Staff', 'carla.mendoza58@gmail.com', '09935342507', '2026-04-22 09:41:00', '2026-04-28'),
(59, 'John Cruz', 'Purok 1, Barangay 143', '1993-11-14', '09683182289', 'Pending', 'Male', 'Single', 'Security Guard', 'john.cruz59@gmail.com', '09372823174', '2026-04-22 09:41:00', '2026-04-26'),
(60, 'Angelica Ramos', 'Purok 2, Barangay 143', '1993-03-08', '09030978844', 'Active', 'Female', 'Single', 'Cashier', 'angelica.ramos60@gmail.com', '09271607717', '2026-04-22 09:41:00', '2026-04-25'),
(61, 'Kevin Torres', 'Purok 3, Barangay 143', '1996-01-23', '09179388447', 'Pending', 'Male', 'Single', 'Driver', 'kevin.torres61@gmail.com', '09162393137', '2026-04-22 09:41:00', '2026-04-28'),
(62, 'Jasmine Flores', 'Purok 1, Barangay 143', '1998-01-14', '09284388982', 'Active', 'Female', 'Single', 'Student', 'jasmine.flores62@gmail.com', '09458044093', '2026-04-22 09:41:00', '2026-04-29'),
(63, 'Paul Aquino', 'Purok 2, Barangay 143', '1996-06-24', '09177749979', 'Pending', 'Male', 'Single', 'Security Guard', 'paul.aquino63@gmail.com', '09958914202', '2026-04-22 09:41:00', '2026-04-30'),
(64, 'Rica Bautista', 'Purok 3, Barangay 143', '1997-01-15', '09521438824', 'Active', 'Female', 'Single', 'Driver', 'rica.bautista64@gmail.com', '09645947155', '2026-04-22 09:41:00', '2026-04-26'),
(65, 'Carlo Villanueva', 'Purok 1, Barangay 143', '1996-09-18', '09365411568', 'Pending', 'Male', 'Single', 'Driver', 'carlo.villanueva65@gmail.com', '09785090416', '2026-04-22 09:41:00', '2026-04-24'),
(66, 'Lea Navarro', 'Purok 2, Barangay 143', '2001-06-16', '09513185845', 'Active', 'Female', 'Single', 'Construction Worker', 'lea.navarro66@gmail.com', '09566661616', '2026-04-22 09:41:00', '2026-04-22'),
(67, 'Bryan Castillo', 'Purok 3, Barangay 143', '2007-02-20', '09676133412', 'Pending', 'Male', 'Single', 'Student', 'bryan.castillo67@gmail.com', '09935489540', '2026-04-22 09:41:00', '2026-04-23'),
(68, 'Denise Ortega', 'Purok 1, Barangay 143', '1999-06-17', '09259718526', 'Active', 'Female', 'Single', 'Office Staff', 'denise.ortega68@gmail.com', '09339301081', '2026-04-22 09:41:00', '2026-04-27'),
(69, 'Joshua Herrera', 'Purok 2, Barangay 143', '1991-01-07', '09963023751', 'Pending', 'Male', 'Single', 'Security Guard', 'joshua.herrera69@gmail.com', '09285870372', '2026-04-22 09:41:00', '2026-04-29'),
(70, 'Nicole Salazar', 'Purok 3, Barangay 143', '1993-06-22', '09938930073', 'Active', 'Female', 'Single', 'Vendor', 'nicole.salazar70@gmail.com', '09408502041', '2026-04-22 09:41:00', '2026-04-28'),
(71, 'Adrian Gutierrez', 'Purok 1, Barangay 143', '1997-07-17', '09259513783', 'Pending', 'Male', 'Single', 'Teacher', 'adrian.gutierrez71@gmail.com', '09861664722', '2026-04-22 09:41:00', '2026-04-22'),
(72, 'Michelle Dominguez', 'Purok 2, Barangay 143', '1992-09-11', '09112267761', 'Active', 'Female', 'Married', 'Office Staff', 'michelle.dominguez72@gmail.com', '09503624141', '2026-04-22 09:41:00', '2026-04-25'),
(73, 'Jerome Pineda', 'Purok 3, Barangay 143', '1994-02-18', '09393465061', 'Pending', 'Male', 'Single', 'Security Guard', 'jerome.pineda73@gmail.com', '09124985291', '2026-04-22 09:41:00', '2026-04-24'),
(74, 'Karen Espinoza', 'Purok 1, Barangay 143', '1996-06-21', '09936281967', 'Active', 'Female', 'Single', 'Office Staff', 'karen.espinoza74@gmail.com', '09200972931', '2026-04-22 09:41:00', '2026-04-29'),
(75, 'Patrick Valencia', 'Purok 2, Barangay 143', '1995-09-25', '09925567744', 'Pending', 'Male', 'Single', 'Vendor', 'patrick.valencia75@gmail.com', '09303233117', '2026-04-22 09:41:00', '2026-04-24'),
(76, 'Bea Cabrera', 'Purok 3, Barangay 143', '1990-09-20', '09047651266', 'Active', 'Female', 'Married', 'Construction Worker', 'bea.cabrera76@gmail.com', '09132381715', '2026-04-22 09:41:00', '2026-04-30'),
(77, 'Lester Molina', 'Purok 1, Barangay 143', '1992-03-15', '09947496966', 'Pending', 'Male', 'Single', 'Student', 'lester.molina77@gmail.com', '09803244467', '2026-04-22 09:41:00', '2026-04-29'),
(78, 'Faith Robles', 'Purok 2, Barangay 143', '1997-02-17', '09673429233', 'Active', 'Female', 'Single', 'Vendor', 'faith.robles78@gmail.com', '09585252561', '2026-04-22 09:41:00', '2026-04-28'),
(79, 'Noel Fajardo', 'Purok 3, Barangay 143', '1999-12-17', '09663909382', 'Pending', 'Male', 'Married', 'Cashier', 'noel.fajardo79@gmail.com', '09132605754', '2026-04-22 09:41:00', '2026-04-26'),
(80, 'Shiela Padilla', 'Purok 1, Barangay 143', '2001-12-13', '09486948950', 'Active', 'Female', 'Married', 'Office Staff', 'shiela.padilla80@gmail.com', '09406642606', '2026-04-22 09:41:00', '2026-05-01'),
(81, 'Vincent Alonzo', 'Purok 2, Barangay 143', '1992-12-06', '09466141484', 'Pending', 'Male', 'Single', 'Construction Worker', 'vincent.alonzo81@gmail.com', '09351054644', '2026-04-22 09:41:00', '2026-04-30'),
(82, 'Trisha Mercado', 'Purok 3, Barangay 143', '1998-04-06', '09295247478', 'Active', 'Female', 'Single', 'Cashier', 'trisha.mercado82@gmail.com', '09205022645', '2026-04-22 09:41:00', '2026-05-01'),
(83, 'Ronald Chavez', 'Purok 1, Barangay 143', '1992-11-23', '09520791513', 'Pending', 'Male', 'Single', 'Vendor', 'ronald.chavez83@gmail.com', '09823337697', '2026-04-22 09:41:00', '2026-04-22'),
(84, 'Camille Soriano', 'Purok 2, Barangay 143', '2000-05-18', '09929838323', 'Active', 'Female', 'Single', 'Construction Worker', 'camille.soriano84@gmail.com', '09231405823', '2026-04-22 09:41:00', '2026-04-23'),
(85, 'Edgar Luna', 'Purok 3, Barangay 143', '1996-10-22', '09635106553', 'Pending', 'Male', 'Single', 'Vendor', 'edgar.luna85@gmail.com', '09982349485', '2026-04-22 09:41:00', '2026-04-22'),
(86, 'Joy Lim', 'Purok 1, Barangay 143', '1996-01-24', '09767662045', 'Active', 'Female', 'Single', 'Vendor', 'joy.lim86@gmail.com', '09540003108', '2026-04-22 09:41:00', '2026-04-26'),
(87, 'Arvin Tan', 'Purok 2, Barangay 143', '1991-02-27', '09152740047', 'Pending', 'Male', 'Single', 'Construction Worker', 'arvin.tan87@gmail.com', '09676814027', '2026-04-22 09:41:00', '2026-04-30'),
(88, 'Hazel Ong', 'Purok 3, Barangay 143', '1990-12-25', '09802100630', 'Active', 'Female', 'Single', 'Student', 'hazel.ong88@gmail.com', '09165039738', '2026-04-22 09:41:00', '2026-04-22'),
(89, 'Dennis Sy', 'Purok 1, Barangay 143', '1991-01-10', '09591900989', 'Pending', 'Male', 'Single', 'Driver', 'dennis.sy89@gmail.com', '09529201732', '2026-04-22 09:41:00', '2026-05-01'),
(90, 'Irene Go', 'Purok 2, Barangay 143', '1997-06-25', '09170732850', 'Active', 'Female', 'Single', 'Teacher', 'irene.go90@gmail.com', '09280067116', '2026-04-22 09:41:00', '2026-04-29'),
(91, 'Patrick Yu', 'Purok 3, Barangay 143', '1997-06-20', '09273567401', 'Pending', 'Male', 'Single', 'Student', 'patrick.yu91@gmail.com', '09216923997', '2026-04-22 09:41:00', '2026-04-26'),
(92, 'Rose Tan', 'Purok 1, Barangay 143', '1992-04-22', '09716154267', 'Active', 'Female', 'Married', 'Construction Worker', 'rose.tan92@gmail.com', '09992109120', '2026-04-22 09:41:00', '2026-04-25'),
(93, 'Albert Chua', 'Purok 2, Barangay 143', '1992-07-12', '09849448891', 'Pending', 'Male', 'Single', 'Construction Worker', 'albert.chua93@gmail.com', '09811783195', '2026-04-22 09:41:00', '2026-04-28'),
(94, 'Grace Co', 'Purok 3, Barangay 143', '1999-01-11', '09268646207', 'Active', 'Female', 'Married', 'Office Staff', 'grace.co94@gmail.com', '09555710883', '2026-04-22 09:41:00', '2026-04-30'),
(95, 'Leo Ang', 'Purok 1, Barangay 143', '1992-12-11', '09709048522', 'Pending', 'Male', 'Single', 'Security Guard', 'leo.ang95@gmail.com', '09238725886', '2026-04-22 09:41:00', '2026-04-27'),
(96, 'Kim Tan', 'Purok 2, Barangay 143', '1990-12-27', '09690688550', 'Active', 'Female', 'Single', 'Vendor', 'kim.tan96@gmail.com', '09693878465', '2026-04-22 09:41:00', '2026-04-27'),
(97, 'Nina Lim', 'Purok 3, Barangay 143', '1995-07-04', '09976885142', 'Pending', 'Female', 'Married', 'Security Guard', 'nina.lim97@gmail.com', '09664679821', '2026-04-22 09:41:00', '2026-04-24'),
(98, 'Rico Ong', 'Purok 1, Barangay 143', '1992-08-02', '09347648516', 'Active', 'Male', 'Married', 'Driver', 'rico.ong98@gmail.com', '09439668649', '2026-04-22 09:41:00', '2026-04-24'),
(99, 'Ella Sy', 'Purok 2, Barangay 143', '1992-07-19', '09198941382', 'Pending', 'Female', 'Single', 'Driver', 'ella.sy99@gmail.com', '09327138238', '2026-04-22 09:41:00', '2026-04-28'),
(100, 'Marco Go', 'Purok 3, Barangay 143', '1992-07-06', '09525963322', 'Active', 'Male', 'Single', 'Teacher', 'marco.go100@gmail.com', '09100006988', '2026-04-22 09:41:00', '2026-04-30'),
(101, 'Liza Yu', 'Purok 1, Barangay 143', '1994-04-22', '09283896583', 'Active', 'Female', 'Single', 'Barangay Secretary', 'liza.yu101@gmail.com', '09267828097', '2026-04-22 09:41:00', '2026-04-30'),
(102, 'Bryan Co', 'Purok 2, Barangay 143', '2000-11-18', '09814091854', 'Active', 'Male', 'Single', 'Security Guard', 'bryan.co102@gmail.com', '09926060519', '2026-04-22 09:41:00', '2026-04-27'),
(103, 'Cristopher Dabu', 'Purok 1, Barangay 143', '2007-07-27', '09123456781', 'Active', 'Male', 'Single', 'Engineer', 'cristopher.dabu103@gmail.com', '09453484452', '2026-04-22 09:43:55', '2026-04-28'),
(104, 'Drey Custodio', 'Purok 2, Barangay 143', '2007-12-06', '09123456782', 'Active', 'Male', 'Single', 'President', 'drey.custodio104@gmail.com', '09675123633', '2026-04-22 09:43:55', '2026-04-27'),
(105, 'Kristopher Edora', 'Purok 3, Barangay 143', '2007-05-25', '09608274743', 'Active', 'Male', 'Single', 'Coach', 'kristopher.edora105@gmail.com', '09565811966', '2026-04-22 09:43:55', '2026-04-29'),
(106, 'Allan Rivera', 'Purok 1, Barangay 143', '1993-02-11', '09123456001', 'Active', 'Male', 'Single', 'Vendor', 'allan.rivera106@gmail.com', '09236620671', '2026-04-22 16:51:28', '2026-05-01'),
(107, 'Brian Santos', 'Purok 2, Barangay 143', '1995-07-19', '09123456002', 'Active', 'Male', 'Single', 'Office Staff', 'brian.santos107@gmail.com', '09252732056', '2026-04-22 16:51:28', '2026-05-02'),
(108, 'Catherine Lopez', 'Purok 3, Barangay 143', '1998-03-25', '09123456003', 'Active', 'Female', 'Single', 'Vendor', 'catherine.lopez108@gmail.com', '09529807833', '2026-04-22 16:51:28', '2026-04-28'),
(109, 'Daniel Flores', 'Purok 1, Barangay 143', '1991-09-14', '09123456004', 'Active', 'Male', 'Single', 'Teacher', 'daniel.flores109@gmail.com', '09660862620', '2026-04-22 16:51:28', '2026-04-29'),
(110, 'Erika Ramos', 'Purok 2, Barangay 143', '1996-12-05', '09123456005', 'Active', 'Female', 'Single', 'Cashier', 'erika.ramos110@gmail.com', '09964199593', '2026-04-22 16:51:28', '2026-05-02'),
(111, 'Francis Mendoza', 'Purok 3, Barangay 143', '1994-06-30', '09123456006', 'Active', 'Male', 'Single', 'Office Staff', 'francis.mendoza111@gmail.com', '09992678193', '2026-04-22 16:51:28', '2026-04-23'),
(112, 'Gina Torres', 'Purok 1, Barangay 143', '1997-08-21', '09123456007', 'Active', 'Female', 'Single', 'Student', 'gina.torres112@gmail.com', '09480867973', '2026-04-22 16:51:28', '2026-05-01'),
(113, 'Henry Cruz', 'Purok 2, Barangay 143', '1992-01-18', '09123456008', 'Active', 'Male', 'Married', 'Office Staff', 'henry.cruz113@gmail.com', '09167128107', '2026-04-22 16:51:28', '2026-04-29'),
(114, 'Ivy Bautista', 'Purok 3, Barangay 143', '1999-04-12', '09123456009', 'Active', 'Female', 'Single', 'Office Staff', 'ivy.bautista114@gmail.com', '09586814758', '2026-04-22 16:51:28', '2026-04-23'),
(115, 'Jake Navarro', 'Purok 1, Barangay 143', '1993-10-27', '09123456010', 'Active', 'Male', 'Single', 'Security Guard', 'jake.navarro115@gmail.com', '09332723264', '2026-04-22 16:51:28', '2026-04-25'),
(116, 'Karen Castillo', 'Purok 2, Barangay 143', '1996-02-08', '09123456011', 'Active', 'Female', 'Single', 'Vendor', 'karen.castillo116@gmail.com', '09173864086', '2026-04-22 16:51:28', '2026-04-26'),
(117, 'Leo Herrera', 'Purok 3, Barangay 143', '1991-11-22', '09123456012', 'Active', 'Male', 'Single', 'Cashier', 'leo.herrera117@gmail.com', '09625600243', '2026-04-22 16:51:28', '2026-04-26'),
(118, 'Mia Salazar', 'Purok 1, Barangay 143', '1998-05-09', '09123456013', 'Active', 'Female', 'Single', 'Office Staff', 'mia.salazar118@gmail.com', '09553690632', '2026-04-22 16:51:28', '2026-05-01'),
(119, 'Noah Gutierrez', 'Purok 2, Barangay 143', '1997-07-15', '09123456014', 'Active', 'Male', 'Married', 'Cashier', 'noah.gutierrez119@gmail.com', '09604045472', '2026-04-22 16:51:28', '2026-05-02'),
(120, 'Olivia Dominguez', 'Purok 3, Barangay 143', '1994-03-03', '09123456015', 'Active', 'Female', 'Married', 'Driver', 'olivia.dominguez120@gmail.com', '09273992172', '2026-04-22 16:51:28', '2026-04-25'),
(121, 'Peter Pineda', 'Purok 1, Barangay 143', '1995-09-29', '09123456016', 'Active', 'Male', 'Single', 'Construction Worker', 'peter.pineda121@gmail.com', '09410147459', '2026-04-22 16:51:28', '2026-04-25'),
(122, 'Queen Espinoza', 'Purok 2, Barangay 143', '1992-06-17', '09123456017', 'Active', 'Female', 'Single', 'Office Staff', 'queen.espinoza122@gmail.com', '09414009562', '2026-04-22 16:51:28', '2026-04-30'),
(123, 'Ryan Valencia', 'Purok 3, Barangay 143', '1996-01-11', '09123456018', 'Active', 'Male', 'Single', 'Construction Worker', 'ryan.valencia123@gmail.com', '09646785086', '2026-04-22 16:51:28', '2026-04-29'),
(124, 'Sophia Cabrera', 'Purok 1, Barangay 143', '1990-12-02', '09123456019', 'Active', 'Female', 'Single', 'Security Guard', 'sophia.cabrera124@gmail.com', '09264550197', '2026-04-22 16:51:28', '2026-05-02'),
(125, 'Tony Molina', 'Purok 1, Barangay 143', '1971-04-20', '09123456020', 'Active', 'Male', 'Married', 'Barangay Tanod', 'tony.molina125@gmail.com', '09300819868', '2026-04-22 16:51:28', '2026-04-27'),
(126, 'Ulysses Robles', 'Purok 3, Barangay 143', '1997-02-14', '09123456021', 'Active', 'Male', 'Single', 'Security Guard', 'ulysses.robles126@gmail.com', '09922735643', '2026-04-22 16:51:28', '2026-04-28'),
(127, 'Victor Fajardo', 'Purok 1, Barangay 143', '1999-08-06', '09123456022', 'Active', 'Male', 'Single', 'Driver', 'victor.fajardo127@gmail.com', '09196716503', '2026-04-22 16:51:28', '2026-04-23'),
(128, 'Wendy Padilla', 'Purok 2, Barangay 143', '2000-10-10', '09123456023', 'Active', 'Female', 'Single', 'Construction Worker', 'wendy.padilla128@gmail.com', '09923459319', '2026-04-22 16:51:28', '2026-04-24'),
(129, 'Xander Alonzo', 'Purok 3, Barangay 143', '1992-05-05', '09123456024', 'Active', 'Male', 'Single', 'Driver', 'xander.alonzo129@gmail.com', '09365401451', '2026-04-22 16:51:28', '2026-05-02'),
(130, 'Yasmin Mercado', 'Purok 1, Barangay 143', '1998-07-28', '09123456025', 'Active', 'Female', 'Married', 'Office Staff', 'yasmin.mercado130@gmail.com', '09898715925', '2026-04-22 16:51:28', '2026-04-28'),
(131, 'Zack Chavez', 'Purok 2, Barangay 143', '1994-11-13', '09123456026', 'Active', 'Male', 'Single', 'Driver', 'zack.chavez131@gmail.com', '09131436388', '2026-04-22 16:51:28', '2026-04-30'),
(132, 'Aaron Soriano', 'Purok 3, Barangay 143', '2001-01-01', '09123456027', 'Active', 'Male', 'Single', 'Cashier', 'aaron.soriano132@gmail.com', '09849582471', '2026-04-22 16:51:28', '2026-04-26'),
(133, 'Bianca Luna', 'Purok 1, Barangay 143', '1996-06-16', '09123456028', 'Active', 'Female', 'Single', 'Driver', 'bianca.luna133@gmail.com', '09717816769', '2026-04-22 16:51:28', '2026-04-23'),
(134, 'Cedric Villanueva', 'Purok 2, Barangay 143', '1995-02-09', '09123456029', 'Active', 'Male', 'Single', 'Student', 'cedric.villanueva134@gmail.com', '09486616305', '2026-04-22 16:51:28', '2026-05-01'),
(135, 'Diana Aguilar', 'Purok 3, Barangay 143', '1993-12-24', '09123456030', 'Active', 'Female', 'Single', 'Construction Worker', 'diana.aguilar135@gmail.com', '09848253109', '2026-04-22 16:51:28', '2026-04-28'),
(136, 'Ethan Delgado', 'Purok 1, Barangay 143', '1991-03-07', '09123456031', 'Active', 'Male', 'Married', 'Vendor', 'ethan.delgado136@gmail.com', '09921610008', '2026-04-22 16:51:28', '2026-04-29'),
(137, 'Fiona Reyes', 'Purok 2, Barangay 143', '1997-09-18', '09123456032', 'Active', 'Female', 'Single', 'Vendor', 'fiona.reyes137@gmail.com', '09625968180', '2026-04-22 16:51:28', '2026-04-23'),
(138, 'George Castillo', 'Purok 3, Barangay 143', '1992-07-21', '09123456033', 'Active', 'Male', 'Single', 'Security Guard', 'george.castillo138@gmail.com', '09193985696', '2026-04-22 16:51:28', '2026-04-28'),
(139, 'Hannah Lee', 'Purok 1, Barangay 143', '2006-12-16', '09123456034', 'Pending', 'Female', 'Single', 'Driver', 'hannah.lee139@gmail.com', '09617716233', '2026-04-22 16:51:28', '2026-04-25'),
(140, 'Ian Bautista', 'Purok 2, Barangay 143', '1998-11-30', '09123456035', 'Active', 'Male', 'Single', 'Vendor', 'ian.bautista140@gmail.com', '09279686400', '2026-04-22 16:51:28', '2026-05-01'),
(141, 'Jade Cruz', 'Purok 3, Barangay 143', '1996-08-08', '09123456036', 'Active', 'Female', 'Single', 'Construction Worker', 'jade.cruz141@gmail.com', '09496575158', '2026-04-22 16:51:28', '2026-04-30'),
(142, 'Kyle Navarro', 'Purok 1, Barangay 143', '1990-06-06', '09123456037', 'Active', 'Male', 'Single', 'Teacher', 'kyle.navarro142@gmail.com', '09874481371', '2026-04-22 16:51:28', '2026-04-23'),
(143, 'Lara Flores', 'Purok 2, Barangay 143', '1993-01-19', '09123456038', 'Active', 'Female', 'Single', 'Cashier', 'lara.flores143@gmail.com', '09950981897', '2026-04-22 16:51:28', '2026-05-01'),
(144, 'Mason Torres', 'Purok 3, Barangay 143', '1997-05-23', '09123456039', 'Active', 'Male', 'Single', 'Cashier', 'mason.torres144@gmail.com', '09467556270', '2026-04-22 16:51:28', '2026-04-24'),
(145, 'Nina Garcia', 'Purok 1, Barangay 143', '1999-09-09', '09123456040', 'Active', 'Female', 'Married', 'Security Guard', 'nina.garcia145@gmail.com', '09227991816', '2026-04-22 16:51:28', '2026-04-27'),
(146, 'Oscar Mendoza', 'Purok 2, Barangay 143', '1992-10-31', '09123456041', 'Active', 'Male', 'Single', 'Office Staff', 'oscar.mendoza146@gmail.com', '09415327464', '2026-04-22 16:51:28', '2026-05-01'),
(147, 'Paula Aquino', 'Purok 3, Barangay 143', '1995-12-12', '09123456042', 'Active', 'Female', 'Single', 'Vendor', 'paula.aquino147@gmail.com', '09270419588', '2026-04-22 16:51:28', '2026-05-01'),
(148, 'Quinn Herrera', 'Purok 1, Barangay 143', '1998-02-02', '09123456043', 'Active', 'Female', 'Single', 'Security Guard', 'quinn.herrera148@gmail.com', '09719726997', '2026-04-22 16:51:28', '2026-04-28'),
(149, 'Ralph Salazar', 'Purok 2, Barangay 143', '1994-07-07', '09123456044', 'Active', 'Male', 'Single', 'Cashier', 'ralph.salazar149@gmail.com', '09976832421', '2026-04-22 16:51:28', '2026-04-26'),
(150, 'Sofia Gutierrez', 'Purok 3, Barangay 143', '1991-11-11', '09123456045', 'Active', 'Female', 'Single', 'Office Staff', 'sofia.gutierrez150@gmail.com', '09413966246', '2026-04-22 16:51:28', '2026-04-23'),
(151, 'Trent Dominguez', 'Purok 1, Barangay 143', '1996-03-03', '09123456046', 'Active', 'Male', 'Single', 'Vendor', 'trent.dominguez151@gmail.com', '09642825821', '2026-04-22 16:51:28', '2026-05-02'),
(152, 'Una Pineda', 'Purok 2, Barangay 143', '1997-06-26', '09123456047', 'Active', 'Female', 'Married', 'Security Guard', 'una.pineda152@gmail.com', '09848978537', '2026-04-22 16:51:28', '2026-04-23'),
(153, 'Vince Espinoza', 'Purok 3, Barangay 143', '1993-08-15', '09123456048', 'Active', 'Male', 'Single', 'Security Guard', 'vince.espinoza153@gmail.com', '09361987287', '2026-04-22 16:51:28', '2026-04-27'),
(154, 'Will Valencia', 'Purok 1, Barangay 143', '1992-04-04', '09123456049', 'Active', 'Male', 'Married', 'Driver', 'will.valencia154@gmail.com', '09836563939', '2026-04-22 16:51:28', '2026-04-27'),
(155, 'Xena Cabrera', 'Purok 2, Barangay 143', '1999-01-20', '09123456050', 'Active', 'Female', 'Widowed', 'Construction Worker', 'xena.cabrera155@gmail.com', '09651459895', '2026-04-22 16:51:28', '2026-04-29'),
(156, 'Mary Grace Gutierrez', 'Purok 3, Brangay 143', '1999-01-01', '09000000000', 'Active', 'Female', 'Married', 'Cashier', 'mary.grace.piattos156@gmail.com', '09123456045', '2026-04-27 05:32:53', '2026-05-05'),
(157, 'Gabo Pardines', 'Purok 3, Brangay 143', '2007-02-08', '09670000067', 'Active', 'Male', 'Separated', 'Musician', 'gabo.pardines157@gmail.com', '09275921052', '2026-04-30 07:18:51', '2026-05-09'),
(158, 'Rodrigo De Leon', 'Purok 2, Barangay 143', '1984-05-07', '09876543211', 'Active', 'Male', 'Single', 'Tricycle Druiver', 'rodrigo.dl05@hgmail.com', '09887564872', '2026-05-07 14:17:26', '2026-05-07'),
(159, 'Cora Santos', 'Purok 2, Barangay 143', '1966-02-18', '09886453987', 'Active', 'Female', 'Single', 'Store Owner', 'corasan02@gmail.com', '09847354123', '2026-05-07 14:21:35', '2026-05-07'),
(160, 'Dante Gulapa', 'Purok 1, Barangay 143', '1976-03-09', '09854325987', 'Active', 'Male', 'Single', 'Barangay Captain', 'dante.gulapa@gmail.com', '09657432453', '2026-05-07 14:23:46', '2026-05-07'),
(161, 'Jennelyn Dela Cruz', 'Purok 1, Barangay 143', '1998-03-20', '09768657984', 'Active', 'Female', 'Single', 'Call center Agent', 'jen.delacruz123@gmail.com', '09764536765', '2026-05-07 15:11:54', '2026-05-07');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `incidents`
--
ALTER TABLE `incidents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inventory`
--
ALTER TABLE `inventory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `residents`
--
ALTER TABLE `residents`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `incidents`
--
ALTER TABLE `incidents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `inventory`
--
ALTER TABLE `inventory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `residents`
--
ALTER TABLE `residents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=163;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
