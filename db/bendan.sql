DROP TABLE IF EXISTS `bendan_sys_user`;

CREATE TABLE `bendan_sys_user`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT comment '用户ID',
    `username`     VARCHAR(64)  not null comment '用户名',
    `password`     VARCHAR(256) not null comment '密码',
    `nick_name`    VARCHAR(64)  DEFAULT NULL comment '昵称',
    `real_name`    VARCHAR(64)  DEFAULT NULL comment '真实姓名',
    `phone_number` VARCHAR(16)  DEFAULT NULL comment '手机号',
    `avatar`       VARCHAR(256) DEFAULT NULL comment '头像',
    `email`        VARCHAR(64)  not null comment '邮箱',
    `secret`       VARCHAR(512) DEFAULT NULL comment '密文',
    `lock_status`  INT          DEFAULT 0 comment '锁定状态（0-正常，1-锁定）',
    `gender`       INT          DEFAULT -1 comment '性别   (-1 未知 0 女性  1 男性)',
    `create_id`    BIGINT       DEFAULT NULL comment '创建人id',
    `update_id`    BIGINT       DEFAULT NULL comment '更新人id',
    `update_time`  DATETIME     DEFAULT NULL comment '创建时间',
    `create_time`  DATETIME     DEFAULT NULL comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci comment '用户表';

DROP TABLE IF EXISTS `bendan_sys_role`;
CREATE TABLE `bendan_sys_role`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT comment 'id',
    `name`          VARCHAR(64)  not null  comment '角色名字',
    `code`          VARCHAR(64)  not null comment '角色码',
    `role_describe` VARCHAR(256) DEFAULT NULL comment '角色描述',
    `del`           INT          DEFAULT 0 comment '逻辑删除标记(0--正常 1--删除)',
    `create_id`    BIGINT       DEFAULT NULL comment '创建人id',
    `update_id`    BIGINT       DEFAULT NULL comment '更新人id',
    `update_time`  DATETIME     DEFAULT NULL comment '创建时间',
    `create_time`  DATETIME     DEFAULT NULL comment '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci comment '角色表';



DROP TABLE IF EXISTS `bendan_sys_menu`;
CREATE TABLE `bendan_sys_menu`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT comment '菜单id',
    `parent_id`            BIGINT       DEFAULT NULL comment '菜单父ID',
    `name`                 VARCHAR(64) DEFAULT NULL comment '菜单名',
    `type`                 INT          DEFAULT NULL comment '是否可跳转，(0 不可跳转  1 可跳转)',
    `icon`                 VARCHAR(64) DEFAULT NULL comment '图标',
    `path`                 VARCHAR(64) DEFAULT NULL comment '路径',
    `sort`                 INT          DEFAULT NULL comment '排序;数字越越靠后',
    `status`               INT          DEFAULT NULL comment '逻辑删除标记(0--正常 1--关闭 2--删除)',
    `create_id`    BIGINT       DEFAULT NULL comment '创建人id',
    `update_id`    BIGINT       DEFAULT NULL comment '更新人id',
    `update_time`  DATETIME     DEFAULT NULL comment '创建时间',
    `create_time`  DATETIME     DEFAULT NULL comment '更新时间',
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci comment '菜单(权限)表';