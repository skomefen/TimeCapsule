package timeCapsule.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Properties;



import timeCapsule.domain.Privilege;
import timeCapsule.domain.User;
import timeCapsule.exception.SecurityException;
import timeCapsule.utils.Permission;

public class ServiceFactory {
	private static ServiceFactory instance = new ServiceFactory();
	private Properties config = null;
	private ServiceFactory(){
		config = new Properties();
		InputStream in = ServiceFactory.class.getClassLoader().getResourceAsStream("service.properties");
		try {
			config.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	public static ServiceFactory getInstance(){
		return instance;
	}
	@SuppressWarnings("unchecked")
	public <T> T createService(Class<T> clazz,User user){
		String name = clazz.getSimpleName();
		String className = config.getProperty(name);
		try {
			T service = (T) clazz.forName(className).newInstance();
			return (T) Proxy.newProxyInstance(ServiceFactory.class.getClassLoader(), service.getClass().getInterfaces(), new serviceHandler(service,user)); 
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			throw new RuntimeException();
		}
	}
	
	class serviceHandler implements InvocationHandler{
		
		Object service;
		User user;
		public serviceHandler(Object service,User user) {
			this.service=service;
			this.user = user;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			

			//�õ�web����õķ���
			String methodname = method.getName();
			
			//���䴦��ʵ��������Ӧ�ķ����������ʵ���󷽷�����û��Ȩ��ע��
			Method  realMethod = service.getClass().getMethod(methodname, method.getParameterTypes());
			Permission permission = realMethod.getAnnotation(Permission.class);
			if(permission==null){
				return method.invoke(service, args);
			}
			
			//��ʵ������Ӧ�ķ�������Ȩ��ע�⣬��õ����ʸ÷�����Ҫ��Ȩ��
			Privilege p = new Privilege(permission.value());
			
			//����û��Ƿ���Ȩ��
			//�õ��û����е�Ȩ��
			if(user==null){
				throw new SecurityException("û�е�½����������");
			}
			
			Method getUserAllPrivilege = service.getClass().getMethod("getUserAllPrivilege", User.class);
			List<Privilege> list = (List<Privilege>) getUserAllPrivilege.invoke(service, user);
			if(list.contains(p)){
				return method.invoke(service, args);
			}
			throw new SecurityException("��û��Ȩ��");
		}
		
	}
}
