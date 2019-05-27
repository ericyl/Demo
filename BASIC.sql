
-- ----------------------------
-- Table structure for DEPT
-- ----------------------------
CREATE TABLE  DEPT(
  DID VARCHAR2(100 BYTE) NOT NULL ,
  DEPT_NAME VARCHAR2(100 BYTE) ,
  PARENT_ID VARCHAR2(100 BYTE) NOT NULL
);

-- ----------------------------
-- Table structure for OPERATE
-- ----------------------------
CREATE TABLE OPERATE (
  OID VARCHAR2(100 BYTE) NOT NULL ,
  OPERATE_NAME VARCHAR2(100 BYTE) ,
  URL VARCHAR2(100 BYTE) ,
  METHOD VARCHAR2(100 BYTE)
);

-- ----------------------------
-- Records of OPERATE
-- ----------------------------
Insert into OPERATE (OID,OPERATE_NAME,URL,METHOD) values ('20190518-d8c62e4c-4573-4ee3-9b64-28982babb29a','首页','/index.html','GET');
COMMIT;

-- ----------------------------
-- Table structure for ROLE
-- ----------------------------
CREATE TABLE ROLE (
  RID VARCHAR2(100 BYTE) NOT NULL ,
  ROLE_NAME VARCHAR2(100 BYTE) ,
  ROLE_KEY VARCHAR2(100 BYTE) ,
  ROOT NUMBER(1) DEFAULT 0  NOT NULL
);

-- ----------------------------
-- Records of ROLE
-- ----------------------------
INSERT INTO ROLE VALUES ('1', '超级管理员', 'ADMIN', '1');
COMMIT;

-- ----------------------------
-- Table structure for ROLE_OPERATE
-- ----------------------------
CREATE TABLE ROLE_OPERATE (
  ROID VARCHAR2(100 BYTE) NOT NULL ,
  OID VARCHAR2(100 BYTE) ,
  RID VARCHAR2(100 BYTE)
);

-- ----------------------------
-- Records of ROLE_OPERATE
-- ----------------------------
Insert into ROLE_OPERATE (ROID,OID,RID) values ('20190518-86c8a0fe-d471-4cbc-8eb1-ef298e360bc0','20190518-d8c62e4c-4573-4ee3-9b64-28982babb29a','1');
COMMIT;

-- ----------------------------
-- Table structure for USER_ROLE
-- ----------------------------
CREATE TABLE USER_ROLE (
  URID VARCHAR2(100 BYTE) NOT NULL ,
  USER_ID VARCHAR2(100 BYTE) ,
  RID VARCHAR2(100 BYTE)
);

-- ----------------------------
-- Records of USER_ROLE
-- ----------------------------
INSERT INTO USER_ROLE VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for USERS
-- ----------------------------
CREATE TABLE USERS (
  USER_ID VARCHAR2(100 BYTE) NOT NULL ,
  USERNAME VARCHAR2(100 BYTE) ,
  PASSWORD VARCHAR2(100 BYTE) ,
  ZHNAME VARCHAR2(100 BYTE) ,
  STATUS NUMBER(1) ,
  DID VARCHAR2(100 BYTE) ,
  PHONE VARCHAR2(100 BYTE)
);

-- ----------------------------
-- Records of USERS
-- ----------------------------
INSERT INTO USERS VALUES ('1', 'admin', '$2a$10$QsFtrA8uM426WT9JOq9OCeFzdPtTwdG.aLSHzzocSD3VgubVFMtnW', '超级管理员', '1', '1', null);
COMMIT;

-- ----------------------------
-- Primary Key structure for table DEPT
-- ----------------------------
ALTER TABLE DEPT ADD CONSTRAINT "DEPT_PK" PRIMARY KEY (DID);

-- ----------------------------
-- Checks structure for table DEPT
-- ----------------------------
ALTER TABLE DEPT ADD CONSTRAINT "SYS_C008772" CHECK (PARENT_ID IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table OPERATE
-- ----------------------------
ALTER TABLE OPERATE ADD CONSTRAINT "OPERATE_PK" PRIMARY KEY (OID);

-- ----------------------------
-- Indexes structure for table OPERATE
-- ----------------------------
CREATE UNIQUE INDEX "OPERATE_UINDEX"
  ON OPERATE (METHOD ASC, URL ASC);

-- ----------------------------
-- Primary Key structure for table ROLE
-- ----------------------------
ALTER TABLE ROLE ADD CONSTRAINT "ROLE_PK" PRIMARY KEY (RID);

-- ----------------------------
-- Checks structure for table ROLE
-- ----------------------------
ALTER TABLE ROLE ADD CONSTRAINT "SYS_C008775" CHECK (ROOT IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Indexes structure for table ROLE
-- ----------------------------
CREATE UNIQUE INDEX "ROLE_ROLE_KEY_UINDEX"
  ON ROLE (ROLE_KEY ASC);

-- ----------------------------
-- Primary Key structure for table ROLE_OPERATE
-- ----------------------------
ALTER TABLE ROLE_OPERATE ADD CONSTRAINT "ROLE_OPERATE_PK" PRIMARY KEY (ROID);

-- ----------------------------
-- Primary Key structure for table USER_ROLE
-- ----------------------------
ALTER TABLE USER_ROLE ADD CONSTRAINT "USER_ROLE_PK" PRIMARY KEY (URID);

-- ----------------------------
-- Primary Key structure for table USERS
-- ----------------------------
ALTER TABLE USERS ADD CONSTRAINT "USERS_PK" PRIMARY KEY (USER_ID);

-- ----------------------------
-- Indexes structure for table USERS
-- ----------------------------
CREATE UNIQUE INDEX "USERS_USERNAME_UINDEX"
  ON USERS (USERNAME ASC);
