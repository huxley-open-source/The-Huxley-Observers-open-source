package com.thehuxley.observer.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author Marcio Augusto Guimarães
 * @author Rodrigo Paes
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public final class Configurator {
	
	private static Properties prop;
	static Logger logger = LoggerFactory.getLogger(Configurator.class);
	private static final String FILENAME = "observers-conf.properties";
	
	private Configurator() {}
	
	public static Properties getProperties() {
		if (prop == null) {
			if (logger.isInfoEnabled()) {
				logger.info("Carregando arquivo de configuração...");
			}
			prop = new Properties();
			try {
                if (System.getenv("HUXLEY_SERVICE_HOME") != null) {
				    prop.load(new FileInputStream(System.getenv("HUXLEY_SERVICE_HOME") + System.getProperty("file.separator") + FILENAME));
                } else {
                    prop.load(new FileInputStream(FILENAME));
                }
			} catch (FileNotFoundException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Não foi possível encontrar o arquivo de configuração.");
				}
				e.printStackTrace();
			} catch (IOException e) {
				if (logger.isErrorEnabled()) {
					logger.error("Não foi possível ler o arquivo de configuração.");
				}
				e.printStackTrace();
			}
		}
		
		return prop;
	}
	
	public static String getProperty(String key){
		return getProperties().getProperty(key);
	}

}
