-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2025 at 12:25 AM
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
-- Database: `teluwell`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `Idbooking` char(5) NOT NULL,
  `tglbooking` date DEFAULT NULL,
  `jammulaib` time DEFAULT NULL,
  `jamselesaib` time DEFAULT NULL,
  `metode` varchar(10) DEFAULT NULL,
  `nim` char(12) NOT NULL,
  `Idjadwal` char(5) NOT NULL,
  `Kodekslg` char(5) NOT NULL,
  `status` enum('ACTIVE','COMPLETED','CANCELLED') DEFAULT 'ACTIVE',
  `cancelled_at` timestamp NULL DEFAULT NULL,
  `completed_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`Idbooking`, `tglbooking`, `jammulaib`, `jamselesaib`, `metode`, `nim`, `Idjadwal`, `Kodekslg`, `status`, `cancelled_at`, `completed_at`) VALUES
('B0001', '2025-06-09', '08:00:00', '09:00:00', 'Offline', '707012400001', 'J0001', 'K001', 'ACTIVE', NULL, NULL),
('B0002', '2025-06-09', '10:00:00', '11:00:00', 'Offline', '707012400002', 'J0002', 'K003', 'ACTIVE', NULL, NULL),
('B0003', '2025-06-10', '08:00:00', '09:00:00', 'Offline', '707012400003', 'J0003', 'K002', 'ACTIVE', NULL, NULL),
('B0004', '2025-06-10', '13:00:00', '14:00:00', 'Online', '707012400004', 'J0004', 'K004', 'ACTIVE', NULL, NULL),
('B0005', '2025-06-11', '09:00:00', '10:00:00', 'Offline', '707012400005', 'J0005', 'K005', 'ACTIVE', NULL, NULL),
('B0006', '2025-06-11', '11:00:00', '12:00:00', 'Offline', '707012400006', 'J0012', 'K006', 'ACTIVE', NULL, NULL),
('B0007', '2025-06-12', '14:00:00', '15:00:00', 'Online', '707012400001', 'J0006', 'K0001', 'ACTIVE', NULL, NULL),
('B0008', '2025-06-12', '08:00:00', '09:00:00', 'Offline', '707012400002', 'J0007', 'K0007', 'COMPLETED', NULL, NULL),
('B0009', '2025-06-13', '08:00:00', '09:00:00', 'Offline', '707012400001', 'J0001', 'K008', 'COMPLETED', NULL, '2025-06-09 20:07:52'),
('B0010', '2025-06-13', '10:00:00', '11:00:00', 'Online', '707012400002', 'J0002', 'K009', 'ACTIVE', NULL, NULL),
('B0011', '2025-06-14', '09:00:00', '10:00:00', 'Offline', '707012400003', 'J0003', 'K010', 'ACTIVE', NULL, NULL),
('B0012', '2025-06-14', '13:00:00', '14:00:00', 'Online', '707012400004', 'J0004', 'K011', 'CANCELLED', '2025-06-09 20:07:52', NULL),
('B0013', '2025-06-15', '08:00:00', '09:00:00', 'Offline', '707012400005', 'J0005', 'K012', 'COMPLETED', NULL, '2025-06-09 20:07:52'),
('B0014', '2025-06-15', '14:00:00', '15:00:00', 'Online', '707012400006', 'J0006', 'K013', 'ACTIVE', NULL, NULL),
('B0015', '2025-06-16', '10:00:00', '11:00:00', 'Offline', '707012400001', 'J0007', 'K014', 'CANCELLED', '2025-06-09 22:13:52', NULL),
('B0016', '2025-06-16', '11:00:00', '12:00:00', 'Online', '707012400002', 'J0010', 'K015', 'COMPLETED', NULL, '2025-06-09 20:07:52'),
('B0017', '2025-06-17', '08:00:00', '09:00:00', 'Offline', '707012400003', 'J0011', 'K016', 'ACTIVE', NULL, NULL),
('B0018', '2025-06-17', '13:00:00', '14:00:00', 'Online', '707012400004', 'J0012', 'K017', 'CANCELLED', '2025-06-09 20:07:52', NULL),
('B0019', '2025-06-18', '09:00:00', '10:00:00', 'Offline', '707012400005', 'J0013', 'K018', 'ACTIVE', NULL, NULL),
('B0020', '2025-06-18', '15:00:00', '16:00:00', 'Online', '707012400006', 'J0014', 'K019', 'COMPLETED', NULL, '2025-06-09 20:07:52'),
('B021', '2025-06-30', '05:10:00', '06:10:00', 'Online', '707012400001', 'J011', 'K020', 'CANCELLED', '2025-06-09 22:13:50', NULL),
('B022', '2025-06-14', '05:15:00', '06:15:00', 'Offline', '707012400001', 'J012', 'K021', 'ACTIVE', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `fakultas`
--

CREATE TABLE `fakultas` (
  `Idfakultas` char(5) NOT NULL,
  `nmfakultas` varchar(50) NOT NULL,
  `kdfakultas` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fakultas`
--

INSERT INTO `fakultas` (`Idfakultas`, `nmfakultas`, `kdfakultas`) VALUES
('FEB', 'Fakultas Ekonomi dan Bisnis', 'FEB'),
('FIF', 'Fakultas Informatika', 'FIF'),
('FIK', 'Fakultas Industri Kreatif', 'FIK'),
('FIT', 'Fakultas Ilmu Terapan', 'FIT'),
('FKB', 'Fakultas Komunikasi dan Bisnis', 'FKB'),
('FKS', 'Fakultas Komunikasi & Ilmu Sosial', 'FKS'),
('FRI', 'Fakultas Rekayasa Industri', 'FRI'),
('FTE', 'Fakultas Teknik Elektro', 'FTE');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `Idfeed` char(5) NOT NULL,
  `hasilfeed` varchar(255) DEFAULT NULL,
  `tglfeed` date DEFAULT NULL,
  `Kodekslg` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`Idfeed`, `hasilfeed`, `tglfeed`, `Kodekslg`) VALUES
('F001', 'Sangat Memuaskan Konseling dengan hani sangat memberikan pengalaman yang sangat hebat!!!', '2025-06-10', 'K0001');

-- --------------------------------------------------------

--
-- Table structure for table `jadwal`
--

CREATE TABLE `jadwal` (
  `Idjadwal` char(5) NOT NULL,
  `harij` varchar(10) NOT NULL,
  `jammulaij` time DEFAULT NULL,
  `jamselesaij` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jadwal`
--

INSERT INTO `jadwal` (`Idjadwal`, `harij`, `jammulaij`, `jamselesaij`) VALUES
('J0001', 'Monday', '08:00:00', '09:00:00'),
('J0002', 'Monday', '10:00:00', '11:00:00'),
('J0003', 'Tuesday', '08:00:00', '09:00:00'),
('J0004', 'Tuesday', '13:00:00', '14:00:00'),
('J0005', 'Wednesday', '09:00:00', '10:00:00'),
('J0006', 'Thursday', '14:00:00', '15:00:00'),
('J0007', 'Friday', '08:00:00', '09:00:00'),
('J0008', 'Friday', '05:56:00', '09:56:00'),
('J0009', 'Wednesday', '04:26:00', '05:26:00'),
('J0010', 'Monday', '09:00:00', '10:00:00'),
('J0011', 'Tuesday', '10:00:00', '11:00:00'),
('J0012', 'Wednesday', '11:00:00', '12:00:00'),
('J0013', 'Thursday', '09:00:00', '10:00:00'),
('J0014', 'Friday', '10:00:00', '11:00:00'),
('J010', 'Friday', '14:47:00', '15:47:00'),
('J011', 'Monday', '05:10:00', '06:10:00'),
('J012', 'Saturday', '05:15:00', '06:15:00');

-- --------------------------------------------------------

--
-- Table structure for table `jadwalkons`
--

CREATE TABLE `jadwalkons` (
  `Idjadwal_kons` char(5) NOT NULL,
  `Idjadwal` char(5) NOT NULL,
  `Idkonselor` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `jadwalkons`
--

INSERT INTO `jadwalkons` (`Idjadwal_kons`, `Idjadwal`, `Idkonselor`) VALUES
('JK003', 'J0003', 'K0002'),
('JK004', 'J0005', 'K0002'),
('JK005', 'J0006', 'K0003'),
('JK006', 'J0007', 'K0003'),
('JK010', 'J0008', 'K0001'),
('JK011', 'J0010', 'K0001'),
('JK012', 'J0014', 'K0001'),
('JK008', 'J010', 'K0001'),
('JK013', 'J011', 'K0001'),
('JK014', 'J012', 'K0001');

-- --------------------------------------------------------

--
-- Table structure for table `konseling`
--

CREATE TABLE `konseling` (
  `Kodekslg` char(5) NOT NULL,
  `ruangan` varchar(20) DEFAULT NULL,
  `tglkslg` date DEFAULT NULL,
  `jammulai` time DEFAULT NULL,
  `jamselesai` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konseling`
--

INSERT INTO `konseling` (`Kodekslg`, `ruangan`, `tglkslg`, `jammulai`, `jamselesai`) VALUES
('K0001', 'Online/Offline', '2025-06-06', '05:56:00', '09:56:00'),
('K0007', 'Online/Offline', '2025-06-09', '09:00:00', '10:00:00'),
('K001', 'Ruang Konseling', '2025-06-03', '08:00:00', '09:00:00'),
('K002', 'Online', '2025-06-04', '04:26:00', '05:26:00'),
('K003', 'Ruang Konseling', '2025-06-04', '09:00:00', '10:00:00'),
('K004', 'Online', '2025-06-20', '14:47:00', '15:47:00'),
('K005', 'Ruang Konseling', '2025-06-06', '14:47:00', '15:47:00'),
('K006', 'Ruang Konseling', '2025-06-05', '14:00:00', '15:00:00'),
('K008', 'Ruang Konseling', '2025-06-13', '08:00:00', '09:00:00'),
('K009', 'Online', '2025-06-13', '10:00:00', '11:00:00'),
('K010', 'Ruang Konseling', '2025-06-14', '09:00:00', '10:00:00'),
('K011', 'Online', '2025-06-14', '13:00:00', '14:00:00'),
('K012', 'Ruang Konseling', '2025-06-15', '08:00:00', '09:00:00'),
('K013', 'Online', '2025-06-15', '14:00:00', '15:00:00'),
('K014', 'Ruang Konseling', '2025-06-16', '10:00:00', '11:00:00'),
('K015', 'Online', '2025-06-16', '11:00:00', '12:00:00'),
('K016', 'Ruang Konseling', '2025-06-17', '08:00:00', '09:00:00'),
('K017', 'Online', '2025-06-17', '13:00:00', '14:00:00'),
('K018', 'Ruang Konseling', '2025-06-18', '09:00:00', '10:00:00'),
('K019', 'Online', '2025-06-18', '15:00:00', '16:00:00'),
('K020', 'Online', '2025-06-30', '05:10:00', '06:10:00'),
('K021', 'Ruang Konseling', '2025-06-14', '05:15:00', '06:15:00');

-- --------------------------------------------------------

--
-- Table structure for table `konselor`
--

CREATE TABLE `konselor` (
  `Idkonselor` char(5) NOT NULL,
  `jdwlkonf` varchar(100) DEFAULT NULL,
  `emailkons` varchar(25) DEFAULT NULL,
  `nmkonselor` varchar(100) NOT NULL,
  `spesialisasi` varchar(50) DEFAULT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `konselor`
--

INSERT INTO `konselor` (`Idkonselor`, `jdwlkonf`, `emailkons`, `nmkonselor`, `spesialisasi`, `password`) VALUES
('K0001', 'Senin-Rabu 08:00-12:00', 'anantha@gmail.com', 'Dr. Muhammad Anantha Mahardika Ridwan', 'Konseling Akademik', '123'),
('K0002', 'Selasa-Kamis 13:00-17:00', 'tyas@gmail.com', 'Mumpuni Tyas, M.Psi', 'Konseling Psikologi', '123'),
('K0003', 'Senin-Jumat 09:00-15:00', 'hani@gmail.com', 'Hani Nadia, M.Psi', 'Konseling Karir', '123'),
('K0004', 'Senin-Jumat 10:00-14:00', 'joko@gmail.com', 'Joko Widodo, M.Psi', 'Konseling Keluarga', '123'),
('K0005', 'Selasa-Kamis 08:00-12:00', 'sopo@gmail.com', 'Sopo Jarwo, M.Pd', 'Konseling Pendidikan', '123'),
('K0006', 'Senin-Rabu 13:00-17:00', 'adit@gmail.com', 'Aditya Rahman, M.Psi', 'Konseling Sosial', '123');

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `nim` char(12) NOT NULL,
  `email` varchar(25) DEFAULT NULL,
  `nmdepan` varchar(25) NOT NULL,
  `nmbelakang` varchar(25) DEFAULT NULL,
  `jk` varchar(10) DEFAULT NULL,
  `Idprodi` char(5) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`nim`, `email`, `nmdepan`, `nmbelakang`, `jk`, `Idprodi`, `password`) VALUES
('707012400001', 'tha@gmail.com', 'Tha', 'Dika', 'Laki-laki', 'P0030', 'tha'),
('707012400002', 'tyas@gmail.com', 'Tyas', 'Sari', 'Perempuan', 'P0005', '123'),
('707012400003', 'hani@gmail.com', 'Hani', 'Putri', 'Perempuan', 'P0012', '123'),
('707012400004', 'mahardika@gmail.com', 'Mahardika', 'Rahman', 'Laki-laki', 'P0008', '123'),
('707012400005', 'mumpuni@gmail.com', 'Mumpuni', 'Dewi', 'Perempuan', 'P0015', '123'),
('707012400006', 'nadia@gmail.com', 'Nadia', 'Sari', 'Perempuan', 'P0020', '123'),
('999999999', 'ridwan@gmail.com', 'Ahmad', 'Ridwan', 'Laki-laki', 'P0030', 'ridwan');

-- --------------------------------------------------------

--
-- Table structure for table `prodi`
--

CREATE TABLE `prodi` (
  `Idprodi` char(5) NOT NULL,
  `nmprodi` varchar(50) NOT NULL,
  `jenjang` varchar(10) DEFAULT NULL,
  `kdprodi` varchar(10) NOT NULL,
  `Idfakultas` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `prodi`
--

INSERT INTO `prodi` (`Idprodi`, `nmprodi`, `jenjang`, `kdprodi`, `Idfakultas`) VALUES
('P0005', 'Informatika', 'S1', 'IF', 'FIF'),
('P0008', 'Teknik Industri', 'D3', 'TI-D3', 'FRI'),
('P0012', 'Akuntansi', 'S1', 'AKT', 'FEB'),
('P0015', 'Ilmu Komunikasi', 'S1', 'ILKOM', 'FKS'),
('P0020', 'Desain Komunikasi Visual', 'S1', 'DKV', 'FIK'),
('P0030', 'Sistem Informasi Kota Cerdas', 'S1 Terapan', 'SIKC', 'FIT'),
('P0031', 'Teknik Telekomunikasi', 'S1', 'TT', 'FTE'),
('P0032', 'Teknik Telekomunikasi (International Class)', 'S1', 'TT-IC', 'FTE'),
('P0033', 'Teknik Elektro', 'S1', 'TE', 'FTE'),
('P0034', 'Teknik Komputer', 'S1', 'TK', 'FTE'),
('P0035', 'Teknik Biomedis', 'S1', 'TB', 'FTE'),
('P0036', 'Teknik Fisika', 'S1', 'TF', 'FTE'),
('P0037', 'Teknik Sistem Energi', 'S1', 'TSE', 'FTE'),
('P0038', 'Teknik Logistik', 'S1', 'TL', 'FRI'),
('P0039', 'Sistem Informasi', 'D3', 'SI-D3', 'FRI'),
('P0041', 'Teknologi Informasi', 'S1', 'TI', 'FIF'),
('P0042', 'Rekayasa Perangkat Lunak', 'S1', 'RPL', 'FIF'),
('P0043', 'Data Science', 'S1', 'DS', 'FIF'),
('P0044', 'PJJ Informatika', 'S1', 'PJJ-IF', 'FIF'),
('P0045', 'Teknologi Informasi (Cyber Security)', 'S1', 'TI-CS', 'FIF'),
('P0046', 'Manajemen Bisnis Telekomunikasi & Informatika', 'S1', 'MBTI', 'FEB'),
('P0047', 'Administrasi Bisnis', 'S1', 'AB', 'FEB'),
('P0048', 'Digital Business', 'S1', 'DB', 'FEB'),
('P0049', 'Hubungan Masyarakat Digital', 'S1', 'HUMAS', 'FEB'),
('P0050', 'Penyiaran Digital', 'S1', 'PD', 'FEB'),
('P0051', 'Digital Public Relations', 'S1', 'DPR', 'FKS'),
('P0052', 'Digital Content Broadcasting', 'S1', 'DCB', 'FKS'),
('P0053', 'Psikologi (Digital Psychology)', 'S1', 'PSI', 'FKS'),
('P0054', 'Industrial Design', 'S1', 'ID', 'FIK'),
('P0055', 'Desain Interior', 'S1', 'DI', 'FIK'),
('P0056', 'Kriya (Fashion & Textile Design)', 'S1', 'KRIYA', 'FIK'),
('P0057', 'Seni Rupa', 'S1', 'SR', 'FIK'),
('P0058', 'Film dan Animasi', 'S1', 'FA', 'FIK'),
('P0059', 'Teknologi Rekayasa Multimedia', 'D4', 'TRM', 'FIT'),
('P0060', 'Teknologi Rekayasa Perangkat Lunak', 'D4', 'TRPL', 'FIT'),
('P0061', 'Rekayasa Keamanan Siber', 'D4', 'RKS', 'FIT'),
('P0062', 'Sistem Informasi Akuntansi', 'D4', 'SIA', 'FIT'),
('P0063', 'Teknologi Komputer', 'D4', 'TK-D4', 'FIT');

-- --------------------------------------------------------

--
-- Table structure for table `teleponkons`
--

CREATE TABLE `teleponkons` (
  `telpkons` varchar(13) NOT NULL,
  `Idkonselor` char(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teleponkons`
--

INSERT INTO `teleponkons` (`telpkons`, `Idkonselor`) VALUES
('080101010101', 'K0005'),
('080202020202', 'K0006'),
('080303030303', 'K0006'),
('0811111111111', 'K0001'),
('082222222222', 'K0001'),
('083333333333', 'K0002'),
('084444444444', 'K0002'),
('085555555555', 'K0003'),
('086666666666', 'K0003'),
('087777777777', 'K0004'),
('088888888888', 'K0004'),
('089999999999', 'K0005');

-- --------------------------------------------------------

--
-- Table structure for table `teleponmhs`
--

CREATE TABLE `teleponmhs` (
  `telpmhs` varchar(13) NOT NULL,
  `nim` char(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teleponmhs`
--

INSERT INTO `teleponmhs` (`telpmhs`, `nim`) VALUES
('081234567890', '707012400001'),
('081234567891', '707012400002'),
('081234567892', '707012400003'),
('081234567893', '707012400004'),
('081234567894', '707012400005'),
('081234567895', '707012400006');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`Idbooking`),
  ADD UNIQUE KEY `Kodekslg` (`Kodekslg`),
  ADD KEY `FK_Booking_Mahasiswa` (`nim`),
  ADD KEY `FK_Booking_Jadwal` (`Idjadwal`);

--
-- Indexes for table `fakultas`
--
ALTER TABLE `fakultas`
  ADD PRIMARY KEY (`Idfakultas`),
  ADD UNIQUE KEY `kdfakultas` (`kdfakultas`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`Idfeed`),
  ADD UNIQUE KEY `Kodekslg` (`Kodekslg`);

--
-- Indexes for table `jadwal`
--
ALTER TABLE `jadwal`
  ADD PRIMARY KEY (`Idjadwal`);

--
-- Indexes for table `jadwalkons`
--
ALTER TABLE `jadwalkons`
  ADD PRIMARY KEY (`Idjadwal_kons`),
  ADD UNIQUE KEY `Idjadwal` (`Idjadwal`,`Idkonselor`),
  ADD KEY `FK_JadwalKons_Konselor` (`Idkonselor`);

--
-- Indexes for table `konseling`
--
ALTER TABLE `konseling`
  ADD PRIMARY KEY (`Kodekslg`);

--
-- Indexes for table `konselor`
--
ALTER TABLE `konselor`
  ADD PRIMARY KEY (`Idkonselor`),
  ADD UNIQUE KEY `emailkons` (`emailkons`);

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`nim`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `FK_Mahasiswa_Prodi` (`Idprodi`);

--
-- Indexes for table `prodi`
--
ALTER TABLE `prodi`
  ADD PRIMARY KEY (`Idprodi`),
  ADD UNIQUE KEY `nmprodi` (`nmprodi`),
  ADD UNIQUE KEY `kdprodi` (`kdprodi`),
  ADD KEY `FK_Prodi_Fakultas` (`Idfakultas`);

--
-- Indexes for table `teleponkons`
--
ALTER TABLE `teleponkons`
  ADD PRIMARY KEY (`telpkons`,`Idkonselor`),
  ADD KEY `FK_TeleponKons_Konselor` (`Idkonselor`);

--
-- Indexes for table `teleponmhs`
--
ALTER TABLE `teleponmhs`
  ADD PRIMARY KEY (`telpmhs`,`nim`),
  ADD KEY `FK_TeleponMhs_Mahasiswa` (`nim`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `FK_Booking_Jadwal` FOREIGN KEY (`Idjadwal`) REFERENCES `jadwal` (`Idjadwal`),
  ADD CONSTRAINT `FK_Booking_Konseling` FOREIGN KEY (`Kodekslg`) REFERENCES `konseling` (`Kodekslg`),
  ADD CONSTRAINT `FK_Booking_Mahasiswa` FOREIGN KEY (`nim`) REFERENCES `mahasiswa` (`nim`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `FK_Feedback_Konseling` FOREIGN KEY (`Kodekslg`) REFERENCES `konseling` (`Kodekslg`);

--
-- Constraints for table `jadwalkons`
--
ALTER TABLE `jadwalkons`
  ADD CONSTRAINT `FK_JadwalKons_Jadwal` FOREIGN KEY (`Idjadwal`) REFERENCES `jadwal` (`Idjadwal`),
  ADD CONSTRAINT `FK_JadwalKons_Konselor` FOREIGN KEY (`Idkonselor`) REFERENCES `konselor` (`Idkonselor`);

--
-- Constraints for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD CONSTRAINT `FK_Mahasiswa_Prodi` FOREIGN KEY (`Idprodi`) REFERENCES `prodi` (`Idprodi`);

--
-- Constraints for table `prodi`
--
ALTER TABLE `prodi`
  ADD CONSTRAINT `FK_Prodi_Fakultas` FOREIGN KEY (`Idfakultas`) REFERENCES `fakultas` (`Idfakultas`);

--
-- Constraints for table `teleponkons`
--
ALTER TABLE `teleponkons`
  ADD CONSTRAINT `FK_TeleponKons_Konselor` FOREIGN KEY (`Idkonselor`) REFERENCES `konselor` (`Idkonselor`);

--
-- Constraints for table `teleponmhs`
--
ALTER TABLE `teleponmhs`
  ADD CONSTRAINT `FK_TeleponMhs_Mahasiswa` FOREIGN KEY (`nim`) REFERENCES `mahasiswa` (`nim`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
