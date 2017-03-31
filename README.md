#java 接口文档

##遇到的问题
* mybatis中使用like在mysql中表达式可以用like "%"#{name}"%"，但是h2数据库中不能支持，根据我收集的资料来看like concat(concat('%',#{name}),'%')可以支持h2,mysql,oracle
* h2数据库中datetime类型或者timestamp类型中默认是23位长度2016-08-13 20:43:44.402 而mysql中是19位2016-08-13 20:43:44，然而在使用between时h2数据库因为后面的3位会匹配不到那条数据，因为传参时默认变成了2016-08-13 20:43:44.000
* 今天发现form表单自动提交情况说明：  <br/>
	1.默认情况下，单个输入框，无论按钮的type="submit"还是type="button"类型，回车即提交。   <br/>
	2.当type="submit"时，无论有几个type="text"输入框，回车均表示提交。（<button>按钮默认的type为submit）   <br/>
    3.当type="button"时，且存在多个输入框，回车不提交。（button） <br/>
    解决方案： <br/>
	1.解决单个输入框的回车即提交问题，可以增加一个隐藏的input="text" display='none'; 然后type类型为button。 <br/>
 	2.在form表单或input中加入：onkeydown="if(event.keyCode==13){return false;}" <br/>

##心得
* 关于学习h2数据库的一些心得
* h2是java开发的数据库，上手相对来说比较简单直接在java环境下>> -cp E:\repository\com\h2database\h2\1.3.172\h2-1.3.172.jar org.h2.tools.Server -web可以直接启用。
* 配置url=jdbc:h2:file:~/.h2/bkm;MODE=MySQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1;MVCC=true文件方式后如果修改脚本需要删除对应.db文件后执行新脚本
* 在spring中init脚本中不能完全把mysql的脚本丢过来用，对于部分编码格式和语法h2不支持，详细可以看schema.sql文件语法。
* 在spring中使用h2数据库控制台，需要把完整的JDBC URL路径配置到连接控制台中才能使用该库jdbc:h2:file:~/.h2/bkm;MODE=MySQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1;MVCC=true
* Oracle在insert的语境中默认字符串长度为4000，所以在insert中clob字段大于4000会insert错误ora-01704. 而通过测试MySql没有这样限制，可以正常insert.
* mybatis中如果使用selectOne调用Insert的SQL语句理论上是可以执行的，经过测试普通的insert语句没有问，但是当使用selectKey来绑定主键ID的时候会导致selectKey不会执行的情况，在执行SQL时还是注意按照mybatis的规范来写避免这样的问题。
* 使用h2数据库时，mybatis中foreach使用union all进行select连接时里面的变量表达式只能写${id},如果写成#{id}会报类型错。但是如果用in(foreach)却没有这样的问题。可能是H2的bug.
* 关于重写hashCode和equals方法需要注意记住的事情:
尽量保证使用对象的同一个属性来生成hashCode()和equals()两个方法。在我们的案例中,我们使用员工id。
eqauls方法必须保证一致（如果对象没有被修改，equals应该返回相同的值）
任何时候只要a.equals(b),那么a.hashCode()必须和b.hashCode()相等。
两者必须同时重写。
* 在SpringMVC中Controller中如果使用respose.getWriter就不能使用@ResponseBody注解，否则报错java.lang.IllegalStateException: WRITER* 先就这么多。后续再补充

### 数据库

```
-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.27 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 bkm 的数据库结构
CREATE DATABASE IF NOT EXISTS `bkm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bkm`;


-- 导出  表 bkm.fund_info 结构
CREATE TABLE IF NOT EXISTS `fund_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fcode` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '基金代码',
  `fname` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '基金名称',
  `userid` int(11) DEFAULT NULL COMMENT '对应管理人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金产品信息表';

-- 数据导出被取消选择。


-- 导出  表 bkm.fund_managers 结构
CREATE TABLE IF NOT EXISTS `fund_managers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '人员英文简称',
  `cname` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '人员中文名字',
  `ctype` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '人员类型',
  `itime` date DEFAULT NULL COMMENT '入职日期',
  `otime` date DEFAULT NULL COMMENT '离职日期',
  `resume` text COLLATE utf8mb4_unicode_ci COMMENT '个人简历',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='基金经理信息表';

-- 数据导出被取消选择。


-- 导出  表 bkm.grouptree 结构
CREATE TABLE IF NOT EXISTS `grouptree` (
  `groupid` int(10) DEFAULT NULL COMMENT '权限ID',
  `ztreeid` int(10) DEFAULT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


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
  `generic` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ename` (`ename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 bkm.ico 结构
CREATE TABLE IF NOT EXISTS `ico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ico` varchar(50) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收集AmazeUI用于菜单或者其他的图标样式';

-- 数据导出被取消选择。


-- 导出  表 bkm.legal 结构
CREATE TABLE IF NOT EXISTS `legal` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cdate` varchar(50) DEFAULT NULL COMMENT '颁布日期',
  `title` varchar(1500) DEFAULT NULL COMMENT '主题',
  `issued` varchar(50) DEFAULT NULL COMMENT '发文号',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注内容',
  `tid` int(11) DEFAULT NULL COMMENT '对应legal_tree主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法律法规表';

-- 数据导出被取消选择。


-- 导出  表 bkm.legal_tree 结构
CREATE TABLE IF NOT EXISTS `legal_tree` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` int(11) DEFAULT NULL COMMENT '树节点父级ID',
  `name` varchar(50) DEFAULT NULL COMMENT '树节点名称',
  `title` varchar(50) DEFAULT NULL COMMENT '树节点显示标题',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注说明',
  `node` varchar(50) DEFAULT NULL COMMENT '排序节点',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='法律法规目录树 ';

-- 数据导出被取消选择。


-- 导出  表 bkm.logs 结构
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ctime` datetime NOT NULL COMMENT '操作时间',
  `cmode` varchar(50) NOT NULL COMMENT '操作方式（ADD新增，EDIT修改，DELETE删除）',
  `cfunc` varchar(50) NOT NULL COMMENT '功能',
  `userid` int(11) DEFAULT NULL COMMENT '操作人ID',
  `uname` varchar(50) DEFAULT NULL COMMENT '操作人名称',
  `title` varchar(50) NOT NULL COMMENT '标题描述',
  `content` text NOT NULL COMMENT '日志信息记录',
  `cid` int(11) DEFAULT NULL,
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- 数据导出被取消选择。


-- 导出  表 bkm.mo_info 结构
CREATE TABLE IF NOT EXISTS `mo_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID主键',
  `cname` varchar(50) NOT NULL COMMENT '名称/托管人，机构',
  `orgtype` varchar(50) DEFAULT NULL COMMENT '机构类别',
  `addr` varchar(2000) DEFAULT NULL COMMENT '地址',
  `contacts` varchar(50) DEFAULT NULL COMMENT '联系人',
  `cnumber` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `ctype` varchar(50) NOT NULL COMMENT '前端对应类型：托管人/机构',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构信息,托管人信息等维护';

-- 数据导出被取消选择。


-- 导出  表 bkm.persistent_logins 结构
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `username` varchar(50) DEFAULT NULL,
  `series` varchar(50) NOT NULL,
  `token` varchar(50) DEFAULT NULL,
  `last_used` date DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='spring security 保存用户登录状态remember-me';

-- 数据导出被取消选择。


-- 导出  表 bkm.section_config 结构
CREATE TABLE IF NOT EXISTS `section_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(50) DEFAULT NULL COMMENT '章节目录',
  `content` text COMMENT '章节内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='章节配置表';

-- 数据导出被取消选择。


-- 导出  表 bkm.ugroup 结构
CREATE TABLE IF NOT EXISTS `ugroup` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cname` varchar(20) NOT NULL,
  `ename` varchar(15) NOT NULL,
  `gtype` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 bkm.usergroup 结构
CREATE TABLE IF NOT EXISTS `usergroup` (
  `userid` int(10) unsigned NOT NULL,
  `gid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userid`,`gid`),
  KEY `FK12E9C174B3D6F19E` (`userid`),
  KEY `FK12E9C174C60C751E` (`gid`),
  CONSTRAINT `FK12E9C174B3D6F19E` FOREIGN KEY (`userid`) REFERENCES `guser` (`id`),
  CONSTRAINT `FK12E9C174C60C751E` FOREIGN KEY (`gid`) REFERENCES `ugroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


```