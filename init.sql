/*
 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : springtest
 Date: 2024-03-20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 创建数据库
-- ----------------------------
DROP DATABASE IF EXISTS springtest;
CREATE DATABASE springtest CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE springtest;

-- ----------------------------
-- 创建学生表
-- ----------------------------
DROP TABLE IF EXISTS student;
CREATE TABLE student (
    sno VARCHAR(20) PRIMARY KEY COMMENT '学号',
    sname VARCHAR(50) NOT NULL COMMENT '姓名',
    ssex CHAR(2) COMMENT '性别',
    sage INT COMMENT '年龄',
    sdept VARCHAR(50) COMMENT '院系'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

-- ----------------------------
-- 创建课程表
-- ----------------------------
DROP TABLE IF EXISTS course;
CREATE TABLE course (
    cno VARCHAR(20) PRIMARY KEY COMMENT '课程号',
    cname VARCHAR(50) NOT NULL COMMENT '课程名',
    credit INT COMMENT '学分',
    cdept VARCHAR(50) COMMENT '开课院系'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- ----------------------------
-- 创建选课表
-- ----------------------------
DROP TABLE IF EXISTS sc;
CREATE TABLE sc (
    sno VARCHAR(20) COMMENT '学号',
    cno VARCHAR(20) COMMENT '课程号',
    grade DECIMAL(5,2) COMMENT '成绩',
    PRIMARY KEY (sno, cno),
    CONSTRAINT fk_sc_student FOREIGN KEY (sno) REFERENCES student(sno) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_sc_course FOREIGN KEY (cno) REFERENCES course(cno) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='选课表';

-- ----------------------------
-- 插入学生测试数据
-- ----------------------------
INSERT INTO student (sno, sname, ssex, sage, sdept) VALUES
('2024001', '张三', '男', 20, '计算机系'),
('2024002', '李四', '女', 19, '数学系'),
('2024003', '王五', '男', 21, '物理系'),
('2024004', '赵六', '女', 20, '计算机系'),
('2024005', '钱七', '男', 22, '数学系');

-- ----------------------------
-- 插入课程测试数据
-- ----------------------------
INSERT INTO course (cno, cname, credit, cdept) VALUES
('C001', '计算机网络', 4, '计算机系'),
('C002', '高等数学', 5, '数学系'),
('C003', '大学物理', 4, '物理系'),
('C004', '数据结构', 4, '计算机系'),
('C005', '线性代数', 3, '数学系');

-- ----------------------------
-- 插入选课测试数据
-- ----------------------------
INSERT INTO sc (sno, cno, grade) VALUES
('2024001', 'C001', 85.5),
('2024001', 'C002', 90.0),
('2024002', 'C001', 88.0),
('2024002', 'C003', 85.5),
('2024003', 'C003', 92.5),
('2024003', 'C004', 89.0),
('2024004', 'C001', 85.0),
('2024004', 'C004', 88.5),
('2024005', 'C002', 90.5),
('2024005', 'C005', 93.0);

SET FOREIGN_KEY_CHECKS = 1; 