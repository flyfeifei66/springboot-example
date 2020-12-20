CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` varchar(32) NOT NULL COMMENT '公司ID',
  `company_name` varchar(32) DEFAULT NULL COMMENT '公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `dept_name` varchar(32) DEFAULT NULL COMMENT '部门名称',
  `parent_dept_id` varchar(32) DEFAULT NULL COMMENT '上级部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `staff_id` varchar(32) DEFAULT NULL COMMENT '员工ID',
  `staff_name` varchar(32) DEFAULT NULL COMMENT '员工姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;