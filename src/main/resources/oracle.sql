-- ----------------------------
-- Table structure for fund_info
-- ----------------------------
--DROP TABLE fund_info;
CREATE TABLE fund_info (
id NUMBER(11) NOT NULL ,
fcode VARCHAR2(50) NULL ,
fname VARCHAR2(50) NULL ,
userid NUMBER(11) NULL 
);
COMMENT ON TABLE fund_info IS '基金产品信息表';
COMMENT ON COLUMN fund_info.id IS '主键ID';
COMMENT ON COLUMN fund_info.fcode IS '基金代码';
COMMENT ON COLUMN fund_info.fname IS '基金名称';
COMMENT ON COLUMN fund_info.userid IS '对应管理人ID';

-- ----------------------------
-- Table structure for fund_managers
-- ----------------------------
--DROP TABLE fund_managers;
CREATE TABLE fund_managers (
id NUMBER(11) NOT NULL ,
ename VARCHAR2(50) NULL ,
cname VARCHAR2(50) NOT NULL ,
ctype VARCHAR2(50) NOT NULL ,
itime DATE NULL ,
otime DATE NULL ,
resume CLOB NULL 
);
COMMENT ON TABLE fund_managers IS '基金经理信息表';
COMMENT ON COLUMN fund_managers.ename IS '人员英文简称';
COMMENT ON COLUMN fund_managers.cname IS '人员中文名字';
COMMENT ON COLUMN fund_managers.ctype IS '人员类型';
COMMENT ON COLUMN fund_managers.itime IS '入职日期';
COMMENT ON COLUMN fund_managers.otime IS '离职日期';
COMMENT ON COLUMN fund_managers.resume IS '个人简历';

-- ----------------------------
-- Table structure for grouptree
-- ----------------------------
--DROP TABLE grouptree;
CREATE TABLE grouptree (
groupid NUMBER(11) NULL ,
ztreeid NUMBER(11) NULL 
);
COMMENT ON COLUMN grouptree.groupid IS '权限ID';
COMMENT ON COLUMN grouptree.ztreeid IS '菜单ID';

-- ----------------------------
-- Table structure for guser
-- ----------------------------
--DROP TABLE guser;
CREATE TABLE guser (
id NUMBER(11) NOT NULL ,
gactive VARCHAR2(50) NULL ,
birthday DATE NULL ,
cname VARCHAR2(12) NOT NULL ,
documents VARCHAR2(10) NULL ,
email VARCHAR2(35) NULL ,
ename VARCHAR2(15) NOT NULL ,
entryDate DATE NULL ,
exitDate DATE NULL ,
gender VARCHAR2(4) NULL ,
jobNo VARCHAR2(15) NULL ,
idcard VARCHAR2(18) NULL ,
locked VARCHAR2(5) NULL ,
marriage VARCHAR2(10) NULL ,
phone VARCHAR2(18) NULL ,
pwd VARCHAR2(32) NULL ,
telExt VARCHAR2(5) NULL ,
gtype VARCHAR2(10) NULL ,
generic VARCHAR2(50) NULL 
);

COMMENT ON COLUMN guser.gactive IS '账户是否启用';
COMMENT ON COLUMN guser.birthday IS '生日';
COMMENT ON COLUMN guser.cname IS '用户中文名称';
COMMENT ON COLUMN guser.documents IS '证件类型';
COMMENT ON COLUMN guser.email IS '邮箱';
COMMENT ON COLUMN guser.ename IS '用户登录ID';
COMMENT ON COLUMN guser.entryDate IS '入职日期';
COMMENT ON COLUMN guser.exitDate IS '离职日期';
COMMENT ON COLUMN guser.gender IS '性别';
COMMENT ON COLUMN guser.jobNo IS '工号';
COMMENT ON COLUMN guser.idcard IS '证件账号';
COMMENT ON COLUMN guser.locked IS '账号是否被锁';
COMMENT ON COLUMN guser.marriage IS '是否已婚';
COMMENT ON COLUMN guser.phone IS '手机号';
COMMENT ON COLUMN guser.pwd IS '密码';
COMMENT ON COLUMN guser.telExt IS '分机号';
COMMENT ON COLUMN guser.gtype IS '用户类型';

-- ----------------------------
-- Table structure for ico
-- ----------------------------
--DROP TABLE ico;
CREATE TABLE ico (
id NUMBER(11) NOT NULL ,
ico VARCHAR2(50) NOT NULL ,
type VARCHAR2(50) NULL 
);
COMMENT ON TABLE ico IS '收集AmazeUI用于菜单或者其他的图标样式';

-- ----------------------------
-- Table structure for legal
-- ----------------------------
--DROP TABLE legal;
CREATE TABLE legal (
id NUMBER(11) NOT NULL ,
cdate DATE NULL ,
title VARCHAR2(2500) NULL ,
issued VARCHAR2(50) NULL ,
remark CLOB NULL ,
tid NUMBER(11) NULL 
);

COMMENT ON TABLE legal IS '法律法规表';
COMMENT ON COLUMN legal.id IS '主键ID';
COMMENT ON COLUMN legal.cdate IS '颁布日期';
COMMENT ON COLUMN legal.title IS '主题';
COMMENT ON COLUMN legal.issued IS '发文号';
COMMENT ON COLUMN legal.remark IS '备注内容';
COMMENT ON COLUMN legal.tid IS '对应legal_tree主键ID';

-- ----------------------------
-- Table structure for legal_tree
-- ----------------------------
--DROP TABLE legal_tree;
CREATE TABLE legal_tree (
id NUMBER(11) NOT NULL ,
pid NUMBER(11) NULL ,
name VARCHAR2(50) NULL ,
title VARCHAR2(50) NULL ,
remark VARCHAR2(50) NULL ,
node NUMBER(50) NULL 
);
COMMENT ON TABLE legal_tree IS '法律法规目录树 ';
COMMENT ON COLUMN legal_tree.id IS '主键ID';
COMMENT ON COLUMN legal_tree.pid IS '树节点父级ID';
COMMENT ON COLUMN legal_tree.name IS '树节点名称';
COMMENT ON COLUMN legal_tree.title IS '树节点显示标题';
COMMENT ON COLUMN legal_tree.remark IS '备注说明';
COMMENT ON COLUMN legal_tree.node IS '排序节点';

-- ----------------------------
-- Table structure for logs
-- ----------------------------
--DROP TABLE logs;
CREATE TABLE logs (
id NUMBER(11) NOT NULL ,
ctime DATE NOT NULL ,
cmode VARCHAR2(50) NOT NULL ,
cfunc VARCHAR2(50) NOT NULL ,
userid NUMBER(11) NULL ,
uname VARCHAR2(50) NULL ,
title VARCHAR2(50) NOT NULL ,
content CLOB NOT NULL ,
cid NUMBER(11) NULL 
);
COMMENT ON TABLE logs IS '操作日志表';
COMMENT ON COLUMN logs.id IS '主键';
COMMENT ON COLUMN logs.ctime IS '操作时间';
COMMENT ON COLUMN logs.cmode IS '操作方式（ADD新增，EDIT修改，DELETE删除）';
COMMENT ON COLUMN logs.cfunc IS '功能';
COMMENT ON COLUMN logs.userid IS '操作人ID';
COMMENT ON COLUMN logs.uname IS '操作人名称';
COMMENT ON COLUMN logs.title IS '标题描述';
COMMENT ON COLUMN logs.content IS '日志信息记录';

-- ----------------------------
-- Table structure for mo_info
-- ----------------------------
--DROP TABLE mo_info;
CREATE TABLE mo_info (
id NUMBER(11) NOT NULL ,
cname VARCHAR2(50) NOT NULL ,
orgtype VARCHAR2(50) NULL ,
addr CLOB NULL ,
contacts VARCHAR2(50) NULL ,
cnumber VARCHAR2(50) NULL ,
ctype VARCHAR2(50) NOT NULL 
);
COMMENT ON TABLE mo_info IS '机构信息,托管人信息等维护';
COMMENT ON COLUMN mo_info.id IS '唯一ID主键';
COMMENT ON COLUMN mo_info.cname IS '名称/托管人，机构';
COMMENT ON COLUMN mo_info.orgtype IS '机构类别';
COMMENT ON COLUMN mo_info.addr IS '地址';
COMMENT ON COLUMN mo_info.contacts IS '联系人';
COMMENT ON COLUMN mo_info.cnumber IS '联系电话';
COMMENT ON COLUMN mo_info.ctype IS '前端对应类型：托管人/机构';

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
--DROP TABLE persistent_logins;
CREATE TABLE persistent_logins (
username VARCHAR2(50) NULL ,
series VARCHAR2(50) NOT NULL ,
token VARCHAR2(50) NULL ,
last_used DATE NULL 
);
COMMENT ON TABLE persistent_logins IS 'spring security 保存用户登录状态remember-me';

-- ----------------------------
-- Table structure for section_config
-- ----------------------------
--DROP TABLE section_config;
CREATE TABLE section_config (
id NUMBER(11) NOT NULL ,
node NUMBER(10) NULL ,
title VARCHAR2(50) NULL ,
content CLOB NULL 
);
COMMENT ON TABLE section_config IS '章节配置表';
COMMENT ON COLUMN section_config.id IS '主键ID';
COMMENT ON COLUMN section_config.node IS '排序编号';
COMMENT ON COLUMN section_config.title IS '章节目录';
COMMENT ON COLUMN section_config.content IS '章节内容';

-- ----------------------------
-- Table structure for ugroup
-- ----------------------------
--DROP TABLE ugroup;
CREATE TABLE ugroup (
id NUMBER(11) NOT NULL ,
cname VARCHAR2(20) NOT NULL ,
ename VARCHAR2(15) NOT NULL ,
gtype VARCHAR2(15) NULL 
);

-- ----------------------------
-- Table structure for usergroup
-- ----------------------------
--DROP TABLE usergroup;
CREATE TABLE usergroup (
userid NUMBER(11) NOT NULL ,
gid NUMBER(11) NOT NULL 
);

-- ----------------------------
-- Table structure for ztree
-- ----------------------------
--DROP TABLE ztree;
CREATE TABLE ztree (
id NUMBER(11) NOT NULL ,
ico VARCHAR2(30) NULL ,
name VARCHAR2(30) NOT NULL ,
node NUMBER(30) NOT NULL ,
position VARCHAR2(30) NULL ,
role VARCHAR2(30) NULL ,
title VARCHAR2(30) NULL ,
url VARCHAR2(50) NULL ,
parentid NUMBER(11) NULL ,
menuType NUMBER(4) NULL 
);

-- ----------------------------
-- Primary Key structure for table fund_info
-- ----------------------------
ALTER TABLE fund_info ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table fund_managers
-- ----------------------------
ALTER TABLE fund_managers ADD PRIMARY KEY (id);

-- ----------------------------
-- Indexes structure for table guser
-- ----------------------------
CREATE UNIQUE INDEX ename
ON guser (ename );

-- ----------------------------
-- Primary Key structure for table guser
-- ----------------------------
ALTER TABLE guser ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table ico
-- ----------------------------
ALTER TABLE ico ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table legal
-- ----------------------------
ALTER TABLE legal ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table legal_tree
-- ----------------------------
ALTER TABLE legal_tree ADD PRIMARY KEY (id);

-- ----------------------------
-- Indexes structure for table logs
-- ----------------------------
CREATE INDEX id
ON logs (id );

-- ----------------------------
-- Primary Key structure for table mo_info
-- ----------------------------
ALTER TABLE mo_info ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table persistent_logins
-- ----------------------------
ALTER TABLE persistent_logins ADD PRIMARY KEY (series);

-- ----------------------------
-- Primary Key structure for table section_config
-- ----------------------------
ALTER TABLE section_config ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table ugroup
-- ----------------------------
ALTER TABLE ugroup ADD PRIMARY KEY (id);

-- ----------------------------
-- Indexes structure for table usergroup
-- ----------------------------
CREATE INDEX FK12E9C174B3D6F19E
ON usergroup (userid );
CREATE INDEX FK12E9C174C60C751E
ON usergroup (gid );

-- ----------------------------
-- Primary Key structure for table usergroup
-- ----------------------------
ALTER TABLE usergroup ADD PRIMARY KEY (userid, gid);

-- ----------------------------
-- Primary Key structure for table ztree
-- ----------------------------
ALTER TABLE ztree ADD PRIMARY KEY (id);

-- Create sequence by logs
create sequence logs_id
minvalue 1
maxvalue 99999999
start with 440
increment by 1
cache 20;

-- Create sequence by fund_info and fund_managers using sequeces
create sequence fund_id
minvalue 1
maxvalue 99999999
start with 100
increment by 1
cache 20;

-- Create sequence by guser
create sequence guser_id
minvalue 1
maxvalue 99999999
start with 440
increment by 1
cache 20;

-- Create sequence by legal
create sequence legal_id
minvalue 1
maxvalue 99999999
start with 100
increment by 1
cache 20;

-- Create sequence by moinfo
create sequence moinfo_id
minvalue 1
maxvalue 99999999
start with 100
increment by 1
cache 20;

-- Create sequence by section
create sequence section_id
minvalue 1
maxvalue 99999999
start with 100
increment by 1
cache 20;

-- Create sequence by ugroup
create sequence ugroup_id
minvalue 1
maxvalue 99999999
start with 100
increment by 1
cache 20;

-- Create sequence by ztree
create sequence ztree_id
minvalue 1
maxvalue 99999999
start with 100
increment by 1
cache 20;