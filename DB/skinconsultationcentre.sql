

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `skinconsultationcentre`
--

-- --------------------------------------------------------

--
-- Table structure for table `consultation`
--

CREATE TABLE `consultation` (
  `consultationDate` date NOT NULL,
  `cost` int(11) NOT NULL,
  `notes` varchar(4000) NOT NULL,
  `patientID` varchar(20) NOT NULL,
  `medicalLicenceNumber` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE `doctor` (
  `name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `mobileNumber` int(11) NOT NULL,
  `medicalLicenceNumber` varchar(20) NOT NULL,
  `specialisation` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



-- --------------------------------------------------------

--
-- Table structure for table `doctorconsultation`
--

CREATE TABLE `doctorconsultation` (
  `medicalLicenceNumber` varchar(20) NOT NULL,
  `consultationDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `mobileNumber` int(11) NOT NULL,
  `patientID` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Indexes for dumped tables
--

--
-- Indexes for table `doctor`
--
ALTER TABLE `doctor`
  ADD PRIMARY KEY (`medicalLicenceNumber`);

--
-- Indexes for table `doctorconsultation`
--
ALTER TABLE `doctorconsultation`
  ADD PRIMARY KEY (`medicalLicenceNumber`,`consultationDate`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patientID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
