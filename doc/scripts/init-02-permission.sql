insert into menu(id, parent_id, text, jhi_group, link, external_link, target, icon, hide, description, menu_type, menu_link, menu_order, menu_height) values
    (  1,       null,   '功能导航',        true,     '',                                          '',         '',         '',                                false,   '', 0, '', 0, 800),
    (  2,          1,      '首页',         false,    '/dashboard',                                '',         '',         'anticon anticon-dashboard',       false,   '', 1, '', 0, 800),
    (  3,		   1, 	'系统功能',        false,    '/system',                                   '',         '',         'anticon anticon-user',            false,	  '', 0, '', 9, 800),
    (  4,          3, 	   '导航菜单',     false,    '/system/menu',                              '',         '',         '', 				                 false,	  '', 1, '', 9, 800),
    (  5,	       3, 	   '角色权限',     false,    '/system/role',                              '',         '',         '', 				                 false,	  '', 1, '', 9, 800),
    (  6,	       3, 	   '用户管理',     false,    '/system/user',                              '',         '',         '',                                false,	  '', 1, '', 9, 800),
    (  7,		   1, 	'管理功能',        false,    '/admin',                                    '',         '',         'anticon anticon-user',            false,	  '', 0, '', 9, 800),
    (  8,          7, 	   '资源监控',     false,    '/admin/metrics',                            '',         '',         '', 				                 false,	  '', 1, '', 9, 800),
    (  9,	       7, 	   '服务状态',     false,    '/admin/health',                             '',         '',         '', 				                 false,	  '', 1, '', 9, 800),
    ( 10,	       7, 	   '审计（审核）', false,    '/admin/audits',                             '',         '',         '',                                 false,   '', 1, '', 9, 800),
    (100,	       1, 	'常用导航',        false,    '/report',                                   '',         '',         '', 				                 false,	  '', 0, '', 1, 800),
    (101,	     100, 	   '百度-外链',    false,    '',   'http://www.baidu.com',         '_blank',         '', 				         false,	  '', 2, 'http://www.baidu.com', 1, 800),
    (102,	     100, 	   '百度-内链',    false,    '/report/bi/102',         '',         '',         '', 				                 false,	  '', 1, 'http://www.baidu.com', 1, 800);

insert into role(id, role_name) values
	(3, '系统管理员'),
	(4, '普通用户');

insert into role_menu(role_id, menu_id) values
    (3, 2       ),

	(3, 4     ),

    (3, 5     ),
	(3, 6     ),
	(3, 101     ),
	(3, 102     ),

    (4, 2       ),

	(4, 4     ),

    (4, 5     ),
	(4, 6     ),
	(4, 101     ),
	(4, 102     );

insert into user_extends(id, username, gender, user_id) values
    (3, 'admin', 'MALE', 3),
    (4, 'user',  'MALE', 4);
insert into user_extends_role(user_extends_id, role_id) values
    (3, 3),
    (4, 4);

