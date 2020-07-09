## 依赖
1. jdk 1.8+
2. mysql 5.7+

## 安装
1. 把linux文件夹下内容拷贝到任意文件夹下
2. 根据情况修改application.yml，主要是数据库链接，用户名/密码，以及web服务器端口8080
3. 系统运行后会自动创建数据库，并且添加初始化数据
4. 有时需要把smarter.sh文件转换成unix格式（通过vi命令）
5. 给smarter.sh文件赋予执行权限
```
chmod +x smarter.sh
```

## 运行
```
启动
./smarter.sh start
停止
./smarter.sh stop
重启
./smarter.sh restart
状态
./smarter.sh status
```

## 验证
1. 浏览器打开http://192.168.xxx.xxx:8080
2. 用户名/密码：admin/admin 或者 user/user
3. 进入系统后，常用导航/百度-内链、百度外链为测试数据，可以删除
