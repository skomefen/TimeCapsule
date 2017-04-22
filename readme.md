1.���������

	1.0 ���л���
		��֤�������е�
		jdk 1.8
		tomcat 7.0.73
		������tomcat��jdk�Ļ�������
		���óɹ�����tomcatĿ¼bin��startup.bat windows���гɹ��и�cmd������
		Ȼ���½http://localhost:8080/TimeCapsule�ɵ�¼����ҳ��

	1.1 ��������
		dom4j������
		jstl������
		beanUtils������
		log4j������
		mysql-connector������
	
		c3po���ӳ�
		dbutils
		fileupload���
		io��
	1.2 ������֯����İ�
		timeCapsule.domain
		timeCapsule.dao
		timeCapsule.dao.impl
		timeCapsule.service
		timeCapsule.service.impl
		timeCapsule.web.controller  (���������servlet)
		timeCapsule.web.UI       (���û��ṩ�û�����)
		timeCapsule.utils
		junit.test
		
		WEB-INF/jsp   ������վ����jsp
	
	1.3 �������ݿ�Ӧ��
		1.�������ݿ�����
		2.ΪӦ�ô�����Ӧ�Ŀ�ͱ�
		3.����dao

	���ݿ�Դ����


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
		
		����ԱҪ���ݿ��ֶ�ע�ᣬ��Ϊ��û����������Ա���Ȩ�ޡ�Ȩ��Ŀǰֻ��һ������ԱȨ�ޡ�Ҫ��Ȩ�ޱ�ע�����ԱȨ��
		insert into privilege (id,name,description)value('123456','����ԱȨ��','����Ǹ�');
		
		Ȼ���Լ�ע��������ݱ��ֶ�����һ���û�������Ա
		��users_privilege��ϵ������û���Ȩ�޹�ϵ
		insert into users_privilege(users_id,privilege_id)value('aaa','123456')
		����'aaa'�滻�����Ա��id��123456ΪȨ��id
		
		1.4 filter������
			���ȫ����������                       ��
			���ȫ��ת�������ַ�            ��
			���ȫ�������ַ�                      ��
			���ȫ��ѹ������                     ��
			����Զ���¼����                    ��
			�Զ���¼������                          ��
				��½֮��ʹ�ر���ҳ��ֻҪû�а��˳���½���´η��ʿ���ֱ�ӵ�½��
				һ��ʱ��û�з��ʣ������ڣ����Զ���¼��������������½��ԭ�������޷��Զ���½��
				cookieֵ��Ϊautologin=username:state:expirestime:md5(state:password:expirestime:username)
				state:isOK,noOK
			
			�����������
			
			
			
		1.5  ������
		          ��ֹ���ظ��ύ
			��֤��
			��cookie session��������

		1.6 ��ע�⣬���Ȩ�޹���
			ʵ���˹���ԱȨ�޹���û��ʵ��Ȩ�����
			
		1.7 ������ʵ�� Email�Զ�����  ��
		
			��ɾ���Eamilʵ��
			
			#_ע�⣡�����޸�_
			
			ע ��mail.properties�����ļ���
			fromEmailAdress��emailAuthorityKey
			�������滻��QQ��������ʼ�����Ȩ��
			���Ժ��ٸĳ������ļ���ȡ��
			��Ȩ���ȡ������
			http://jingyan.baidu.com/article/fedf0737af2b4035ac8977ea.html
			
			Ҳ���Ըĳ��������䣬���޸�
			/TimeCapsule/src/mail.properties
			��mail.smtp.host=smtp.qq.com
			�����Ӧ��smtp������
			
			EmailBusinessServiceImpl.sendEmail(Email email)�Ѿ�ʵ���˸����ʼ��ķ�������ôʵ�ֿ��Կ�����
			/TimeCapsule/src/junit/test/service/MailTest.java
			�Ժ���ʵ����ҳ�����ʼ����͡�
			��QQ����ò���޷��������ʼ��������Ժ��ڽ��
			
			
			