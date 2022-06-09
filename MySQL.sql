SHOW DATABASES;
SHOW TABLES;

USE class;

create table Faculty
(
    facID   int auto_increment comment '院系编号',
    facName varchar(20) not null comment '学院名称',
    constraint Faculty_pk
        primary key (facID)
)
    comment '院系';

create unique index Faculty_facID_uindex
    on Faculty (facID);

create unique index Faculty_facName_uindex
    on Faculty (facName);

create table Major
(
    majorID   int auto_increment comment '专业编号',
    majorName varchar(20) not null comment '专业名称',
    facID     int         not null comment '隶属院系',
    constraint Major_pk
        primary key (majorID),
    constraint Major_facID_fk
        foreign key (facID) references faculty (facID)
)
    comment '专业';

create unique index Major_majorID_uindex
    on Major (majorID);

create unique index Major_majorName_uindex
    on Major (majorName);

create table Class
(
    classID  bigint auto_increment comment '班级编号',
    majorID  int not null comment '隶属专业',
    grade    int not null comment '年级',
    classNum int not null comment '班号',
    constraint Class_pk
        primary key (classID),
    constraint Class_majorID_fk
        foreign key (majorID) references major (majorID)
)
    comment '班级';

create unique index Class_classID_uindex
    on Class (classID);

create table Student
(
    stuID     bigint auto_increment comment '学号',
    stuName   varchar(10)     not null comment '学生姓名',
    stuGender enum ('m', 'f') not null comment '性别',
    classID   bigint          not null comment '隶属班级',
    passwd    char(128)       not null comment '学生密码',
    salt      char(32)        not null comment '加盐',
    constraint Student_pk
        primary key (stuID),
    constraint Student_classID_fk
        foreign key (classID) references class (classID)
)
    comment '学生';

create unique index Student_stuID_uindex
    on Student (stuID);

create table Teacher
(
    tchID     bigint auto_increment comment '工号',
    tchName   varchar(20)     not null comment '教师姓名',
    tchGender enum ('m', 'f') not null comment '教师性别',
    facID     int             not null comment '隶属院系',
    passwd    char(128)       not null comment '教师密码',
    salt      char(32)        not null comment '加盐',
    constraint Teacher_pk
        primary key (tchID),
    constraint Teacher_facID_fk
        foreign key (facID) references faculty (facID)
)
    comment '教师';

create unique index Teacher_tchID_uindex
    on Teacher (tchID);

create table Subject
(
    subID   int auto_increment comment '科目编号',
    subName varchar(40) not null comment '科目名',
    constraint Subject_pk
        primary key (subID)
)
    comment '科目';

create unique index Subject_subID_uindex
    on Subject (subID);

create unique index Subject_subName_uindex
    on Subject (subName);

create table Course
(
    courID       bigint auto_increment comment '课程编号',
    subID        int       not null comment '隶属科目',
    tchID        bigint    not null comment '授课教师',
    courTimeFrom timestamp not null,
    courTimeEnd  timestamp not null,
    constraint Course_pk
        primary key (courID),
    constraint Course_subID_fk
        foreign key (subID) references Subject(subID),
    constraint Course_tchID_fk
        foreign key (tchID) references Teacher(tchID)
)
    comment '课程';

create unique index Course_courID_uindex
    on Course (courID);

create table Attendance
(
    attID    char(36)                  not null comment '考勤编号',
    courID    bigint                    not null comment '隶属课程',
    stuID     int                       not null comment '隶属学生',
    attTime    timestamp                 null comment '考勤时间',
    attStatus enum ('not_signed', 'signed', 'absence', 'personal_leave', 'sick_leave', 'public_holiday', 'late', 'leave_early') default 'not_signed' not null comment '考勤状态',
    constraint Attendance_pk
        primary key (attID)
)
    comment '考勤';

create unique index Attendance_courID_uindex
    on Attendance (attID);

select *
from faculty;

ALTER TABLE faculty
    AUTO_INCREMENT = 19;

INSERT INTO faculty
values (2, '计算机学院'),
       (18, '工程训练中心');


SELECT EXISTS(SELECT 1 FROM faculty WHERE facName = '信息学院');


SELECT attID,
       attTime,
       attStatus,
       student.stuID,
       stuName,
       stuGender,
       student.passwd,
       student.salt,
       class.classID,
       grade,
       classNum,
       major.majorID,
       majorName,
       course.courID,
       courTimeFrom,
       courTimeEnd,
       subject.subID,
       teacher.tchID,
       tchName,
       tchGender,
       teacher.passwd,
       teacher.salt,
       faculty.facID,
       facName
FROM attendance,
     student,
     class,
     major,
     course,
     subject,
     teacher,
     faculty
where attendance.courID = course.courID
  AND attendance.stuID = student.stuID
  AND class.classID = student.classID
  AND class.majorID = major.majorID
  AND major.facID = faculty.facID
  AND course.subID = subject.subID
  AND course.tchID = teacher.tchID
  AND teacher.facID = faculty.facID
ORDER BY courID, attTime, attStatus;
