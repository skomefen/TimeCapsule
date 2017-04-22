package timeCapsule.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import timeCapsule.web.controller.LoginServlet;

public class ConfigFactory {
	
	private Properties config = null;
	private static ConfigFactory configFactory = new ConfigFactory();
	
	private int loginexpirestime;
	private String webpath;
	private String key;
	
	private ConfigFactory(){
		config = new Properties();
		InputStream in = LoginServlet.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			config.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static ConfigFactory getInstance() {
		return configFactory;
	}

	public int getLoginexpirestime() {
		return Integer.parseInt(config.getProperty("loginvalidtime"));
	}

	public String getWebpath() {
		return config.getProperty("webpath");
	}

	public String getKey() {
		return config.getProperty("key");
	}
	
	

}
