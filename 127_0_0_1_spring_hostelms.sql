--
-- Database: `spring_hostelms`
--
CREATE DATABASE IF NOT EXISTS `spring_hostelms` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `spring_hostelms`;

-- --------------------------------------------------------

--
-- Table structure for table `blocks`
--

CREATE TABLE `blocks` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `updationdate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `blocks`
--

INSERT INTO `blocks` (`id`, `name`, `updationdate`) VALUES
(1, 'Block A', '2025-03-14 06:34:16'),
(2, 'Block B', '2025-03-14 06:34:24'),
(3, 'Block C', '2025-03-14 06:34:30');

-- --------------------------------------------------------

--
-- Table structure for table `complaint`
--

CREATE TABLE `complaint` (
  `id` int(11) NOT NULL,
  `ctitle` varchar(255) NOT NULL,
  `cdescription` text NOT NULL,
  `student` int(11) NOT NULL,
  `room` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `complaint`
--

INSERT INTO `complaint` (`id`, `ctitle`, `cdescription`, `student`, `room`, `status`, `creationdate`) VALUES
(1, 'sfdf', 'sdafdsaf\r\nfl;jdsfds;af\r\nkds;fl;dsf', 13, 2, 2, '2025-03-15 10:14:24');

-- --------------------------------------------------------

--
-- Table structure for table `complaint_comments`
--

CREATE TABLE `complaint_comments` (
  `id` int(11) NOT NULL,
  `complaint` int(11) NOT NULL,
  `user` varchar(500) NOT NULL,
  `comment` text NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `complaint_comments`
--

INSERT INTO `complaint_comments` (`id`, `complaint`, `user`, `comment`, `creationdate`) VALUES
(1, 1, 'admin(#1)', 'sadsaf,dskfjdsf', '2025-03-15 10:20:23'),
(2, 1, 'test(#13)', 'sadafsdn,bdsnbf ', '2025-03-15 10:21:03'),
(3, 1, 'admin(#1)', 'sadsafdf', '2025-03-15 10:22:00'),
(4, 1, 'test(#13)', 'saddafdsf', '2025-03-15 10:22:16'),
(5, 1, 'admin(#1)', 'sadsadsa', '2025-03-15 10:22:36');

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `department` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`id`, `department`, `name`) VALUES
(1, 2, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`id`, `name`) VALUES
(2, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `noticeboard`
--

CREATE TABLE `noticeboard` (
  `id` int(11) NOT NULL,
  `subject` varchar(500) NOT NULL,
  `message` text NOT NULL,
  `status` int(11) NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `updationdate` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `noticeboard`
--

INSERT INTO `noticeboard` (`id`, `subject`, `message`, `status`, `creationdate`, `updationdate`) VALUES
(1, 'test12', '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse gravida consectetur nibh ac eleifend. Nullam vestibulum tellus eu est convallis, quis euismod libero ullamcorper. Ut sed lacus hendrerit, rhoncus mi eget, lobortis urna. Quisque lectus ipsum, tincidunt vitae augue et, bibendum malesuada lectus. Phasellus laoreet elit vel nulla facilisis, a sodales nunc hendrerit. Fusce non erat vitae turpis vestibulum varius sed et diam. Vestibulum elementum auctor imperdiet. Maecenas faucibus, ligula faucibus molestie viverra, lorem nisl efficitur est, et gravida mi dolor vel nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi ipsum leo, finibus eu faucibus quis, molestie quis quam.</p><p>Mauris sit amet nisl interdum, pretium quam ac, porta est. Sed sed elit eu arcu fermentum blandit in a ante. Vivamus maximus sapien ut laoreet interdum. Fusce eros magna, placerat sit amet ligula ac, venenatis condimentum magna. Donec auctor nisi quis iaculis pretium. Integer iaculis pharetra dui, nec congue tortor lobortis in. Donec congue mollis magna, et tempus metus molestie ac. Phasellus vitae elementum leo.</p><p>Nam id tellus turpis. Maecenas finibus sed turpis eget tincidunt. Nulla gravida velit mauris, sed pellentesque velit iaculis a. Vestibulum at ornare diam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Duis at risus eu dui elementum sagittis. Cras mollis velit vitae velit facilisis iaculis. Nunc a placerat urna. Aliquam erat volutpat. Cras tempor imperdiet neque, sit amet interdum velit lacinia sit amet.</p><p>Vivamus nisl nunc, commodo at orci eget, porta interdum augue. Nam et lacus nec nisl feugiat vulputate. Aliquam iaculis magna ac ante ullamcorper, eget condimentum dui faucibus. Quisque posuere, purus vitae venenatis aliquam, nulla orci volutpat arcu, id viverra justo quam quis lorem. Nulla facilisi. Vivamus neque velit, bibendum sit amet volutpat ut, lacinia nec ligula. Sed egestas dolor vel est molestie, ac tempor diam gravida. Quisque vehicula et libero ut accumsan.</p>', 1, '2025-03-14 08:54:23', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `registrationrequests`
--

CREATE TABLE `registrationrequests` (
  `id` int(11) NOT NULL,
  `emailid` varchar(255) NOT NULL,
  `fullname` varchar(500) NOT NULL,
  `mobilenumber` varchar(15) NOT NULL,
  `password` text NOT NULL,
  `department` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `regno` varchar(100) NOT NULL,
  `dob` date NOT NULL,
  `address` text NOT NULL,
  `parentname` varchar(255) NOT NULL,
  `parentmobile` varchar(20) NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `registrationrequests`
--

INSERT INTO `registrationrequests` (`id`, `emailid`, `fullname`, `mobilenumber`, `password`, `department`, `course`, `year`, `gender`, `regno`, `dob`, `address`, `parentname`, `parentmobile`, `creationdate`, `status`) VALUES
(1, 'test2@gmail.com', 'test', '8553305228', 'VGVzdEAxMjM=', 2, 1, 1, 'Male', '1232', '2026-02-01', 'sadkashkd\r\nsfldsf\r\ndsllkfhgdjkassfdsfhdskf\r\ndskhfkjdsf', 'test', '2132134214', '2025-03-15 07:54:00', 2);

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `id` int(11) NOT NULL,
  `roomno` varchar(100) NOT NULL,
  `floor` int(11) NOT NULL,
  `block` int(11) NOT NULL,
  `maxpersons` int(11) NOT NULL,
  `creationtime` timestamp NOT NULL DEFAULT current_timestamp(),
  `updationtime` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`id`, `roomno`, `floor`, `block`, `maxpersons`, `creationtime`, `updationtime`) VALUES
(1, '101', 0, 1, 3, '2025-03-14 07:43:10', NULL),
(2, '102', 0, 1, 3, '2025-03-15 06:03:23', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `room_students`
--

CREATE TABLE `room_students` (
  `id` int(11) NOT NULL,
  `room` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  `fromdt` date NOT NULL,
  `todt` date DEFAULT NULL,
  `monthlyrent` float NOT NULL,
  `status` int(11) NOT NULL,
  `creationtime` timestamp NOT NULL DEFAULT current_timestamp(),
  `updationtime` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `room_students`
--

INSERT INTO `room_students` (`id`, `room`, `student`, `fromdt`, `todt`, `monthlyrent`, `status`, `creationtime`, `updationtime`) VALUES
(1, 1, 13, '2025-02-01', '2025-02-13', 4000, 0, '2025-03-15 06:04:27', NULL),
(2, 2, 13, '2025-02-14', NULL, 3000, 1, '2025-03-15 06:05:15', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(11) NOT NULL,
  `skey` varchar(255) NOT NULL,
  `sdesc` text NOT NULL,
  `sval` text NOT NULL,
  `updationdate` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`id`, `skey`, `sdesc`, `sval`, `updationdate`) VALUES
(1, 'meal_rate', 'Per Meal Rate(INR)', '50', '2025-03-14 18:20:53'),
(3, 'meal_perday', 'Meals Per Day', '2', '2025-03-14 18:20:53'),
(4, 'other_charges', 'Other Charges', '100', '2025-03-14 18:20:53');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `regno` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `department` int(11) NOT NULL,
  `course` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `address` text NOT NULL,
  `parentname` varchar(255) NOT NULL,
  `parentmobile` varchar(20) NOT NULL,
  `deposit` float NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `user`, `gender`, `regno`, `dob`, `department`, `course`, `year`, `address`, `parentname`, `parentmobile`, `deposit`, `creationdate`, `status`) VALUES
(1, 13, 'Male', '1234', '2018-02-01', 2, 1, 2, 'sadsadsa\r\nsakfhjdksfdf;kdskfl\r\nkldsfhkjdshfkl', 'test', '2132134214', 4000, '2025-03-14 15:04:48', 1),
(2, 14, 'Male', '1235', '2000-01-01', 2, 1, 1, 'asfsd\r\nasdafhksdaf\r\ndsa;fhdshalf', 'test', '2132134214', 1000, '2025-03-14 16:09:40', 1),
(4, 16, 'Male', '1232', '2026-02-01', 2, 1, 1, 'sadkashkd\r\nsfldsf\r\ndsllkfhgdjkassfdsfhdskf\r\ndskhfkjdsf', 'test', '2132134214', 4000, '2025-03-15 08:02:59', 1);

-- --------------------------------------------------------

--
-- Table structure for table `student_monthlyfees`
--

CREATE TABLE `student_monthlyfees` (
  `id` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `room` int(11) NOT NULL,
  `days` int(11) NOT NULL,
  `month` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `rent` float NOT NULL,
  `mess` float NOT NULL,
  `other` float NOT NULL,
  `totalfees` float NOT NULL,
  `paid` int(11) NOT NULL,
  `paiddate` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `paidby` varchar(100) DEFAULT NULL,
  `creationtime` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student_monthlyfees`
--

INSERT INTO `student_monthlyfees` (`id`, `user`, `room`, `days`, `month`, `year`, `rent`, `mess`, `other`, `totalfees`, `paid`, `paiddate`, `paidby`, `creationtime`) VALUES
(1, 13, 1, 13, 2, 2025, 1857, 1300, 100, 3257, 1, '2025-03-15 06:47:05', 'Cheque', '2025-03-15 06:05:26'),
(2, 13, 2, 14, 2, 2025, 1500, 1400, 0, 2900, 0, NULL, NULL, '2025-03-15 06:05:26');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `fullname` varchar(500) NOT NULL,
  `mobilenumber` varchar(15) NOT NULL,
  `password` text NOT NULL,
  `type` varchar(255) NOT NULL,
  `updationdate` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `creationdate` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `fullname`, `mobilenumber`, `password`, `type`, `updationdate`, `creationdate`, `status`) VALUES
(1, 'admin@admin.com', 'admin', '2134124324', '$2a$10$iyXW3kzWNwWhy5kYOIfAb.v18FhwDgHttcOZloT6iUG9vhoYuNpYq', 'ADMIN', '2025-01-31 12:04:40', '2025-01-28 16:43:48', 1),
(13, 'test@gmail.com', 'test', '1111111112', '$2a$10$DSTwmebfUg91ZzC/QqugdOGFLjpN.xdnzpC4cZms.8f0aSBEEVLrO', 'STUDENT', '2025-03-15 08:15:17', '2025-03-14 15:04:48', 1),
(14, 'test1@gmail.com', 'testA', '8553305227', '$2a$10$J1h/K8edikvffBEfX3vG1OVEm2r0sI4WwA6xGxi/Aw64x3GVJvaM2', 'STUDENT', NULL, '2025-03-14 16:09:40', 1),
(16, 'test2@gmail.com', 'test', '8553305228', '$2a$10$DSTwmebfUg91ZzC/QqugdOGFLjpN.xdnzpC4cZms.8f0aSBEEVLrO', 'STUDENT', NULL, '2025-03-15 08:02:58', 1);

-- --------------------------------------------------------

--
-- Table structure for table `visitors`
--

CREATE TABLE `visitors` (
  `id` int(11) NOT NULL,
  `student` int(11) NOT NULL,
  `room` int(11) NOT NULL,
  `visitorname` varchar(500) NOT NULL,
  `purpose` varchar(500) NOT NULL,
  `visitingtime` varchar(500) NOT NULL,
  `creationtime` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `visitors`
--

INSERT INTO `visitors` (`id`, `student`, `room`, `visitorname`, `purpose`, `visitingtime`, `creationtime`, `status`) VALUES
(1, 13, 2, 'asdqwew', 'test', 'Mar 16,2025 04:03 pm To Mar 16,2025 05:03 pm', '2025-03-15 10:33:20', 0),
(2, 13, 2, 'sdasd', 'asddsfsd', 'Mar 16,2025 04:10 pm To Mar 16,2025 05:10 pm', '2025-03-15 10:40:18', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blocks`
--
ALTER TABLE `blocks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `complaint`
--
ALTER TABLE `complaint`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `complaint_comments`
--
ALTER TABLE `complaint_comments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `noticeboard`
--
ALTER TABLE `noticeboard`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `registrationrequests`
--
ALTER TABLE `registrationrequests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `room_students`
--
ALTER TABLE `room_students`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `skey` (`skey`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `regno` (`regno`);

--
-- Indexes for table `student_monthlyfees`
--
ALTER TABLE `student_monthlyfees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `visitors`
--
ALTER TABLE `visitors`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blocks`
--
ALTER TABLE `blocks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `complaint`
--
ALTER TABLE `complaint`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `complaint_comments`
--
ALTER TABLE `complaint_comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `noticeboard`
--
ALTER TABLE `noticeboard`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `registrationrequests`
--
ALTER TABLE `registrationrequests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `room_students`
--
ALTER TABLE `room_students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `student_monthlyfees`
--
ALTER TABLE `student_monthlyfees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `visitors`
--
ALTER TABLE `visitors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
