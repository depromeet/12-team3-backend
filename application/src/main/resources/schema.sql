DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `device_id`  varchar(200) NOT NULL COMMENT '기기 식별자 해시값',
    `status_cd`  varchar(20)  NOT NULL COMMENT '회원 상태',
    `created_at` datetime     NOT NULL,
    `updated_at` datetime NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `user`
    ADD UNIQUE unique_device_id (device_id);

DROP TABLE IF EXISTS `user_suggestion`;
CREATE TABLE `user_suggestion`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`         bigint(20) NOT NULL,
    `type_suggestion` varchar(200) NULL COMMENT '타입 문의',
    `item_suggestion` varchar(200) NULL COMMENT '아이템 문의',
    `created_at`      datetime NOT NULL,
    `updated_at`      datetime NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `type`       varchar(20)  NOT NULL,
    `user_id`    bigint(20) NOT NULL,
    `name`       varchar(200) NOT NULL,
    `emoji`      VARCHAR(255) NOT NULL,
    `created_at` datetime     NOT NULL,
    `updated_at` datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_name` (`name`)
);

DROP TABLE IF EXISTS `recommend_item`;
CREATE TABLE `recommend_item`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `template_id` bigint(20) NOT NULL,
    `category_id` bigint(20) NOT NULL,
    `item_name`   varchar(20) NOT NULL,
    `item_emoji`  varchar(255) NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `template_id` bigint(20) NOT NULL,
    `category_id` bigint(20) NOT NULL,
    `name`        varchar(20) NOT NULL,
    `emoji`       varchar(255) NULL,
    `alarm_id`    bigint(20) NOT NULL,
    `is_take`     tinyInt(1) NOT NULL,
    `created_at`  datetime    NOT NULL,
    `updated_at`  datetime    NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `alarm_message_history`;
CREATE TABLE `alarm_message_history`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `alarm_id`         bigint(20) NOT NULL,
    `item_id`          bigint(20) NOT NULL,
    `template_id`      bigint(20) NOT NULL,
    `text_template_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `recommend_template`;
CREATE TABLE `recommend_template`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `category_id`   bigint(20) NOT NULL,
    `template_name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `template`;
CREATE TABLE `template`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`           bigint(20) NOT NULL,
    `category_id`       bigint(20) NOT NULL,
    `template_name`     VARCHAR(50) NOT NULL,
    `is_reserved_alarm` tinyint(1) NOT NULL,
    `is_pin`            tinyint(1) NOT NULL,
    `created_at`        datetime    NOT NULL,
    `updated_at`        datetime NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `push_token`    VARCHAR(100) NULL,
    `template_id`   bigint(20) NULL,
    `item_id`       bigint(20) NULL,
    `type`          VARCHAR(20) NOT NULL COMMENT '템플릿 알람, 아이템 알람 구분',
    `reserve_time`  datetime    NOT NULL COMMENT '예약 시간',
    `is_send`       tinyint(1) NOT NULL,
    `is_activated`  tinyint(1) NOT NULL,
    `alarm_form_id` bigint(20) NOT NULL,
    `created_at`    datetime    NOT NULL,
    `updated_at`    datetime NULL,
    PRIMARY KEY (`id`)
);
