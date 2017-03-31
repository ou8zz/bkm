-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.27 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

-- 导出  表 bkm.fund_info 结构
CREATE TABLE IF NOT EXISTS `fund_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fcode` varchar(50) DEFAULT NULL COMMENT '基金代码',
  `fname` varchar(50) DEFAULT NULL COMMENT '基金名称',
  `userid` int(11) DEFAULT NULL COMMENT '对应管理人ID',
  PRIMARY KEY (`id`)
);

-- 导出  表 bkm.fund_managers 结构
CREATE TABLE IF NOT EXISTS `fund_managers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(50)  DEFAULT NULL COMMENT '人员英文简称',
  `cname` varchar(50)  NOT NULL COMMENT '人员中文名字',
  `ctype` varchar(50)  NOT NULL COMMENT '人员类型',
  `itime` date DEFAULT NULL COMMENT '入职日期',
  `otime` date DEFAULT NULL COMMENT '离职日期',
  `resume` text  COMMENT '个人简历',
  PRIMARY KEY (`id`)
);

-- 导出  表 bkm.grouptree 结构
CREATE TABLE IF NOT EXISTS `grouptree` (
  `groupid` int(10) DEFAULT NULL COMMENT '权限ID',
  `ztreeid` int(10) DEFAULT NULL COMMENT '菜单ID'
);

-- 导出  表 bkm.guser 结构
CREATE TABLE IF NOT EXISTS `guser` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gactive` varchar(50) COMMENT '账户是否启用',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `cname` varchar(12) NOT NULL COMMENT '用户中文名称',
  `documents` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `email` varchar(35) DEFAULT NULL COMMENT '邮箱',
  `ename` varchar(15) NOT NULL COMMENT '用户登录ID',
  `entryDate` date DEFAULT NULL COMMENT '入职日期',
  `exitDate` date DEFAULT NULL COMMENT '离职日期',
  `gender` varchar(4) DEFAULT NULL COMMENT '性别',
  `jobNo` varchar(15) DEFAULT NULL COMMENT '工号',
  `idcard` varchar(18) DEFAULT NULL COMMENT '证件账号',
  `locked` varchar(5) COMMENT '账号是否被锁',
  `marriage` varchar(10) DEFAULT NULL COMMENT '是否已婚',
  `phone` varchar(18) DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(32) DEFAULT NULL COMMENT '密码',
  `telExt` varchar(5) DEFAULT NULL COMMENT '分机号',
  `gtype` varchar(10) DEFAULT NULL COMMENT '用户类型',
  `generic` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ename` (`ename`)
);

-- 导出  表 bkm.ico 结构
CREATE TABLE IF NOT EXISTS `ico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ico` varchar(50) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


-- 导出  表 bkm.logs 结构
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` DATETIME NOT NULL,
  `cmode` varchar(50),
  `cfunc` varchar(50),
  `userid` int(11),
  `uname` varchar(50),
  `title` varchar(50),
  `content` text,
  `cid` int(11),
  KEY `id` (`id`)
);

-- 导出  表 bkm.persistent_logins 结构
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `username` varchar(50) DEFAULT NULL,
  `series` varchar(50) NOT NULL,
  `token` varchar(50) DEFAULT NULL,
  `last_used` date DEFAULT NULL,
  PRIMARY KEY (`series`)
);

-- 导出  表 bkm.section_config 结构
CREATE TABLE IF NOT EXISTS `section_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(50) DEFAULT NULL COMMENT '章节目录',
  `content` text COMMENT '章节内容',
  PRIMARY KEY (`id`)
);

-- 导出  表 bkm.ugroup 结构
CREATE TABLE IF NOT EXISTS `ugroup` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cname` varchar(20) NOT NULL,
  `ename` varchar(15) NOT NULL,
  `gtype` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- 导出  表 bkm.usergroup 结构
CREATE TABLE IF NOT EXISTS `usergroup` (
  `userid` int(10) unsigned NOT NULL,
  `gid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userid`,`gid`),
  KEY `FK12E9C174B3D6F19E` (`userid`),
  KEY `FK12E9C174C60C751E` (`gid`),
  CONSTRAINT `FK12E9C174B3D6F19E` FOREIGN KEY (`userid`) REFERENCES `guser` (`id`),
  CONSTRAINT `FK12E9C174C60C751E` FOREIGN KEY (`gid`) REFERENCES `ugroup` (`id`)
);

-- 导出  表 bkm.ztree 结构
CREATE TABLE IF NOT EXISTS `ztree` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ico` varchar(30) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `node` varchar(10) NOT NULL,
  `position` varchar(30) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `parentid` int(10) unsigned DEFAULT NULL,
  `menuType` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `mo_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID主键',
  `cname` varchar(50) NOT NULL DEFAULT NULL COMMENT '名称/托管人，机构',
  `orgtype` varchar(50) DEFAULT NULL COMMENT '机构类别',
  `addr` varchar(2000) DEFAULT NULL COMMENT '地址',
  `contacts` varchar(50) DEFAULT NULL COMMENT '联系人',
  `cnumber` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `ctype` varchar(50) NOT NULL DEFAULT '0' COMMENT '前端对应类型：托管人/机构',
  PRIMARY KEY (`id`)
);

CREATE TABLE `legal_tree` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`pid` INT(11) NULL DEFAULT NULL COMMENT '树节点父级ID',
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT '树节点名称',
	`title` VARCHAR(50) NULL DEFAULT NULL COMMENT '树节点显示标题',
	`remark` VARCHAR(50) NULL DEFAULT NULL COMMENT '备注说明',
	`node` VARCHAR(50) NULL DEFAULT NULL COMMENT '排序节点',
	PRIMARY KEY (`id`)
);

CREATE TABLE `legal` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`cdate` VARCHAR(50) NULL DEFAULT NULL COMMENT '颁布日期',
	`title` VARCHAR(1500) NULL DEFAULT NULL COMMENT '主题',
	`issued` VARCHAR(50) NULL DEFAULT NULL COMMENT '发文号',
	`remark` VARCHAR(50) NULL DEFAULT NULL COMMENT '备注内容',
	`tid` INT(11) NULL DEFAULT NULL COMMENT '对应legal_tree主键ID',
	PRIMARY KEY (`id`)
);

