1.搭建开发环境

	1.0 运行环境
		保证可以运行的
		jdk 1.8
		tomcat 7.0.73
		请配置tomcat和jdk的环境变量
		配置成功运行tomcat目录bin下startup.bat windows运行成功有个cmd框会出现
		然后登陆http://localhost:8080/TimeCapsule可登录到首页。

	1.1 导开发包
		dom4j开发包
		jstl开发包
		beanUtils开发包
		log4j开发包
		mysql-connector开发包
	
		c3po连接池
		dbutils
		fileupload组件
		io包
	1.2 创建组织程序的包
		timeCapsule.domain
		timeCapsule.dao
		timeCapsule.dao.impl
		timeCapsule.service
		timeCapsule.service.impl
		timeCapsule.web.controller  (处理请求的servlet)
		timeCapsule.web.UI       (给用户提供用户界面)
		timeCapsule.utils
		junit.test
		
		WEB-INF/jsp   保存网站所有jsp
	
	1.3 改造数据库应用
		1.导入数据库驱动
		2.为应用创建相应的库和表
		3.改造dao

	数据库源代码


		create database timeCapsule;
		use timeCapsule;
		CREATE TABLE `users` (
		  `id` varchar(40) NOT NULL,
		  `username` varchar(40) NOT NULL,
		  `password` varchar(40) NOT NULL,
		  `email` varchar(40) NOT NULL,
		  `nickname` varchar(40) NOT NULL,
		  PRIMARY KEY (`id`),
		  UNIQUE KEY `username` (`username`),
		  UNIQUE KEY `email` (`email`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
		CREATE TABLE `capsules` (
		  `id` varchar(40) NOT NULL,
		  `capsulename` varchar(40) NOT NULL,
		  `savedate` datetime NOT NULL,
		  `readdate` datetime NOT NULL,
		  `description` varchar(500) NOT NULL DEFAULT '',
		  `email` varchar(50) DEFAULT NULL,
		  `isreaded` tinyint(1) NOT NULL DEFAULT '0',
		  `sendnum` tinyint(3) NOT NULL DEFAULT '0',
		  `user_id` varchar(40) DEFAULT NULL,
		  PRIMARY KEY (`id`),
		  KEY `user_id_FK` (`user_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
		
		CREATE TABLE `upcapsulefiles` (
		  `id` varchar(40) NOT NULL,
		  `uuidname` varchar(100) NOT NULL,
		  `filename` varchar(100) NOT NULL DEFAULT '',
		  `savepath` varchar(255) NOT NULL,
		  `uptime` datetime NOT NULL,
		  `description` varchar(255) DEFAULT NULL,
		  `capsule_id` varchar(40) DEFAULT NULL,
		  PRIMARY KEY (`id`),
		  UNIQUE KEY `uuidname` (`uuidname`),
		  KEY `capsule_id_FK` (`capsule_id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
		
		CREATE TABLE `privilege` (
		  `id` varchar(40) NOT NULL,
		  `name` varchar(40) NOT NULL,
		  `description` varchar(255) DEFAULT NULL,
		  PRIMARY KEY (`id`),
		  UNIQUE KEY `name` (`name`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		
		create table users_privilege(
			users_id varchar(40),
			privilege_id varchar(40),
			primary key(users_id,privilege_id),
			constraint users_id_FK foreign key(users_id) references users(id),
			constraint  privilege_id foreign key(privilege_id) references  privilege(id)
		);
		
		管理员要数据库手动注册，因为还没有做出管理员添加权限。权限目前只有一个管理员权限。要在权限表注入管理员权限
		insert into privilege (id,name,description)value('123456','管理员权限','最吊那个');
		
		然后自己注册或者数据表手动插入一个用户做管理员
		在users_privilege关系表插入用户和权限关系
		insert into users_privilege(users_id,privilege_id)value('aaa','123456')
		上面'aaa'替换你管理员的id，123456为权限id
		
		1.4 filter过滤器
			解决全局乱码问题                       勾
			解决全局转码敏感字符            勾
			解决全局敏感字符                      勾
			解决全局压缩问题                     勾
			解决自动登录问题                    勾
			自动登录方案：                          勾
				登陆之后即使关闭网页，只要没有按退出登陆，下次访问可以直接登陆。
				一段时间没有访问（两天内）则不自动登录。在其他机器登陆则原来机器无法自动登陆。
				cookie值设为autologin=username:state:expirestime:md5(state:password:expirestime:username)
				state:isOK,noOK
			
			解决缓冲问题
			
			
			
		1.5  防盗链
		          防止表单重复提交
			验证码
			禁cookie session还能运行

		1.6 用注解，解决权限管理
			实现了管理员权限管理，没有实现权限添加
			
		1.7 监听器实现 Email自动发送  勾
		
			增删查改Eamil实现
			
			#_注意！必须修改_
			
			注 ：mail.properties配置文件中
			fromEmailAdress和emailAuthorityKey
			把两处替换成QQ邮箱和你邮件的授权码
			（以后再改成配置文件获取）
			授权码获取方法：
			http://jingyan.baidu.com/article/fedf0737af2b4035ac8977ea.html
			
			也可以改成其他邮箱，请修改
			/TimeCapsule/src/mail.properties
			中mail.smtp.host=smtp.qq.com
			后面对应的smtp服务器
			
			EmailBusinessServiceImpl.sendEmail(Email email)已经实现了复杂邮件的发出，怎么实现可以看测试
			/TimeCapsule/src/junit/test/service/MailTest.java
			以后再实现网页输入邮件发送。
			但QQ邮箱貌似无法发复杂邮件给他，以后在解决
			
			
			