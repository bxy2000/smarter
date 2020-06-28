
insert into menu(id, parent_id, text, jhi_group, link, external_link, target, icon, hide, description) values
    (10,          null,     '功能导航',                      true,      '',                              '',         '',         '',                                false,  ''),
    (10100,       10,       '首页',                          false,     '/dashboard',                    '',         '',         'anticon anticon-dashboard',       false,  ''),
    (10101,       10,       '统计报表',                      true,      '/report',                      '',         '',         'anticon anticon-profile',         false,  ''),
    (1010101,     10101,    '大屏展示',                      false,     '/report/bi',              '',         '',         '',                                false,  ''),

    (10910,		  10, 	    '系统功能',		                false,      '/system',                      '',         '',         'anticon anticon-user',           false,	''),
    (1091001,	  10910, 	'全局资源', 	                false,      '/system/menu',                 '',         '',         '', 				              false,	''),
    (1091002,	  10910, 	'角色权限', 	                false,      '/system/role',                 '',         '',         '', 				              false,	''),
    (1091003,	  10910, 	'组织机构', 	                false,	    '/system/organization',         '',         '',         '', 				              false,	''),
    (1091004,	  10910, 	'用户管理', 	                false,	    '/system/user',                 '',         '',         '', 				              false,	'');
insert into role(id, role_name) values
	(3, '系统管理员'),
	(4, '普通用户');

insert into role_menu(role_id, menu_id) values
    (3, 10100       ),

	(3, 1010101     ),

    (3, 1091001     ),
	(3, 1091002     ),
	(3, 1091003     ),
	(3, 1091004     ),

    (4, 10100       ),

	(4, 1010101     ),

    (4, 1091001     ),
	(4, 1091002     ),
	(4, 1091003     ),
	(4, 1091004     );

insert into user_extends(id, username, gender, user_id) values
    (3, 'admin', 'MALE', 3),
    (4, 'user',  'MALE', 4);
insert into user_extends_role(user_extends_id, role_id) values
    (3, 3),
    (4, 4);

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
