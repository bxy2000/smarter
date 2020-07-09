## 依赖
1. jdk 1.8+
2. mysql 5.7+
3. .NET 4.6.1(存在C:\Windows\Microsoft.NET\Framework\v4.0.30319文件夹即可)

## 安装
1. 以管理员身份进入命令行或者powershell
2. 运行以下命令：
```
smarter.exe install
```

## 开启windows服务
1. 控制面板->管理工具->服务，找到smarter服务
2. 运行smarter服务，可以配置启动类型为自动，这样开机即可启动
3. 服务运行前，根据情况修改application.yml，主要是数据库链接，用户名/密码，以及web服务器端口8080
4. 系统运行后会自动创建数据库，并且添加初始化数据

## 停止服务
同上操作

## 卸载服务
1. 以管理员身份进入命令行或者powershell
2. 运行以下命令：
```
smarter.exe uninstall
```

## 验证
1. 浏览器打开http://localhost:8080
2. 用户名/密码：admin/admin 或者 user/user
3. 进入系统后，常用导航/百度-内链、百度外链为测试数据，可以删除
