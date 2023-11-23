# 数据库初始化

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 文章表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';


INSERT INTO user (userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole, createTime, updateTime, isDelete) VALUES ('user1', 'password1', 'unionid1', 'openid1', '张三', 'https://cn.bing.com/images/search?view=detailV2&ccid=E3hh3MoR&id=B9F1159AB0D9E13696DA8678FC59D8371865A8C9&thid=OIP-C.E3hh3MoR99LL5_oJetuhgAAAAA&mediaurl=https%3A%2F%2Fts1.cn.mm.bing.net%2Fth%2Fid%2FR-C.137861dcca11f7d2cbe7fa097adba180%3Frik%3DyahlGDfYWfx4hg%26riu%3Dhttp%253a%252f%252fqimg.hxnews.com%252f2019%252f0422%252f1555901528746.jpg%26ehk%3DG%252bn7p%252fcEHNO01ZoMp5Wg%252f7I0hEj565j65805xeGhoUk%253d%26risl%3D%26pid%3DImgRaw%26r%3D0&exph=315&expw=440&q=%e8%94%a1%e5%be%90%e5%9d%a4%e5%a4%b4%e5%83%8f%e5%9b%be%e7%89%87&simid=608021250587493689&form=IRPRST&ck=B40126F13958DEB5B391D4828194AFC4&selectedindex=110&ajaxhist=0&ajaxserp=0&vt=0&sim=11', '真爱粉', 'user', '2023-09-21 09:12:46', '2023-09-21 09:12:46', 0);

INSERT INTO user (userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole, createTime, updateTime, isDelete) VALUES ('user2', 'password2', 'unionid2', 'openid2', '李四', 'https://c-ssl.duitang.com/uploads/blog/202207/26/20220726130434_ba935.jpg', '小黑子', 'user', '2023-09-21 09:12:46', '2023-09-21 09:12:46', 0);

INSERT INTO user (userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole, createTime, updateTime, isDelete) VALUES ('user3', 'password3', 'unionid3', 'openid3', '王五', 'https://c-ssl.duitang.com/uploads/blog/202207/26/20220726130434_b267d.jpg', 'ikun', 'user', '2023-09-21 09:12:46', '2023-09-21 09:12:46', 0);


CREATE USER canal IDENTIFIED BY 'canal';
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
-- GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' ;
FLUSH PRIVILEGES;