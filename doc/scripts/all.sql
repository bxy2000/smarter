
insert into menu(id, parent_id, text, jhi_group, link, external_link, target, icon, hide, description) values
    (10,          null,     '功能导航',                      true,      '',                              '',         '',         '',                                false,  ''),
    (10100,       10,       '首页',                          false,     '/dashboard',                    '',         '',         'anticon anticon-dashboard',       false,  ''),
    (10101,       10,       '报表分类1',                      true,      '/report',                      '',         '',         'anticon anticon-profile',         false,  ''),
    (1010101,     10101,    '报表1',                      false,     '/report/bi/1',              '',         '',         '',                                false,  ''),
    (1010102,     10101,    '报表2',                      false,     '/report/bi/2',              '',         '',         '',                                false,  ''),
    (1010103,     10101,    '报表3',                      false,     '/report/bi/3',              '',         '',         '',                                false,  ''),
    (1010104,     10101,    '报表4',                      false,     '/report/bi/4',              '',         '',         '',                                false,  ''),
    (1010105,     10101,    '报表5',                      false,     '/report/bi/5',              '',         '',         '',                                false,  ''),
    (1010106,     10101,    '报表6',                      false,     '/report/bi/6',              '',         '',         '',                                false,  ''),
    (1010107,     10101,    '报表7',                      false,     '/report/bi/7',              '',         '',         '',                                false,  ''),
    (1010108,     10101,    '报表8',                      false,     '/report/bi/8',              '',         '',         '',                                false,  ''),
    (1010109,     10101,    '报表9',                      false,     '/report/bi/9',              '',         '',         '',                                false,  ''),
    (1010110,     10101,    '报表10',                      false,     '/report/bi/10',              '',         '',         '',                                false,  ''),
    (1010111,     10101,    '报表11',                      false,     '/report/bi/11',              '',         '',         '',                                false,  ''),
    (1010112,     10101,    '报表12',                      false,     '/report/bi/12',              '',         '',         '',                                false,  ''),
    (1010113,     10101,    '报表13',                      false,     '/report/bi/13',              '',         '',         '',                                false,  ''),
    (1010114,     10101,    '报表14',                      false,     '/report/bi/14',              '',         '',         '',                                false,  ''),
    (1010115,     10101,    '报表15',                      false,     '/report/bi/15',              '',         '',         '',                                false,  ''),
    (1010116,     10101,    '报表16',                      false,     '/report/bi/16',              '',         '',         '',                                false,  ''),
    (1010117,     10101,    '报表17',                      false,     '/report/bi/17',              '',         '',         '',                                false,  ''),
    (1010118,     10101,    '报表18',                      false,     '/report/bi/18',              '',         '',         '',                                false,  ''),
    (1010119,     10101,    '报表19',                      false,     '/report/bi/19',              '',         '',         '',                                false,  ''),

    (10102,       10,       '报表分类2',                      true,      '/report',                      '',         '',         'anticon anticon-profile',         false,  ''),
    (1010201,     10102,    '报表1',                      false,     '/report/bi/1',              '',         '',         '',                                false,  ''),
    (1010202,     10102,    '报表2',                      false,     '/report/bi/2',              '',         '',         '',                                false,  ''),
    (1010203,     10102,    '报表3',                      false,     '/report/bi/3',              '',         '',         '',                                false,  ''),
    (1010204,     10102,    '报表4',                      false,     '/report/bi/4',              '',         '',         '',                                false,  ''),
    (1010205,     10102,    '报表5',                      false,     '/report/bi/5',              '',         '',         '',                                false,  ''),
    (1010206,     10102,    '报表6',                      false,     '/report/bi/6',              '',         '',         '',                                false,  ''),
    (1010207,     10102,    '报表7',                      false,     '/report/bi/7',              '',         '',         '',                                false,  ''),
    (1010208,     10102,    '报表8',                      false,     '/report/bi/8',              '',         '',         '',                                false,  ''),
    (1010209,     10102,    '报表9',                      false,     '/report/bi/9',              '',         '',         '',                                false,  ''),
    (1010210,     10102,    '报表10',                      false,     '/report/bi/10',              '',         '',         '',                                false,  ''),
    (1010211,     10102,    '报表11',                      false,     '/report/bi/11',              '',         '',         '',                                false,  ''),
    (1010212,     10102,    '报表12',                      false,     '/report/bi/12',              '',         '',         '',                                false,  ''),
    (1010213,     10102,    '报表13',                      false,     '/report/bi/13',              '',         '',         '',                                false,  ''),
    (1010214,     10102,    '报表14',                      false,     '/report/bi/14',              '',         '',         '',                                false,  ''),
    (1010215,     10102,    '报表15',                      false,     '/report/bi/15',              '',         '',         '',                                false,  ''),
    (1010216,     10102,    '报表16',                      false,     '/report/bi/16',              '',         '',         '',                                false,  ''),
    (1010217,     10102,    '报表17',                      false,     '/report/bi/17',              '',         '',         '',                                false,  ''),
    (1010218,     10102,    '报表18',                      false,     '/report/bi/18',              '',         '',         '',                                false,  ''),
    (1010219,     10102,    '报表19',                      false,     '/report/bi/19',              '',         '',         '',                                false,  ''),

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