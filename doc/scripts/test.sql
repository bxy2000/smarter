insert into organization(id, parent_id, code, name) values
(10,      null,     '10',         '计算机系' ),
(1010,    10,       '1010',           '计算机专业'),
(101001,  1010,     '101001',             '计算机1班'),
(101002,  1010,     '101002',             '计算机2班'),
(1011,    10,       '1011',           '自动化专业'),
(101101,  1011,     '101101',             '自动化1班'),
(101102,  1011,     '101102',             '自动化2班');

insert into jhi_user(id, login, password_hash, email, activated, created_by, created_date) values
(5, '10001', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', '10001@local.com', 1, 'system', '2000-12-12'),
(6, '10002', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', '10002@local.com', 1, 'system', '2000-12-12'),
(7, '10003', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', '10003@local.com', 1, 'system', '2000-12-12'),
(8, '10004', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', '10004@local.com', 1, 'system', '2000-12-12');

insert into user_extends(id, username, gender, user_id, organization_id) values
    (5, '张三', 'MALE',   5, 101001, 101001),
    (6, '李四', 'FEMALE', 6, 101001, 101001),
    (7, '王五', 'MALE',   7, 101002, 101002),
    (8, '赵六', 'FEMALE', 8, 101002, 101002);
