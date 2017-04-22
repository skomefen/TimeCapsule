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
			

			//得到web层调用的方法
			String methodname = method.getName();
			
			//反射处真实对象上相应的方法，检查真实对象方法上有没有权限注解
			Method  realMethod = service.getClass().getMethod(methodname, method.getParameterTypes());
			Permission permission = realMethod.getAnnotation(Permission.class);
			if(permission==null){
				return method.invoke(service, args);
			}
			
			//真实对象相应的方法上有权限注解，则得到访问该方法需要的权限
			Privilege p = new Privilege(permission.value());
			
			//检查用户是否有权限
			//得到用户所有的权限
			if(user==null){
				throw new SecurityException("没有登陆，不给你用");
			}
			
			Method getUserAllPrivilege = service.getClass().getMethod("getUserAllPrivilege", User.class);
			List<Privilege> list = (List<Privilege>) getUserAllPrivilege.invoke(service, user);
			if(list.contains(p)){
				return method.invoke(service, args);
			}
			throw new SecurityException("你没有权限");
		}
		
	}
}
