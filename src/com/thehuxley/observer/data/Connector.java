package com.thehuxley.observer.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public class Connector {

	private static Connection connection;
	static Logger logger = LoggerFactory.getLogger(Connector.class);
	private static boolean test;

	private Connector() {
		test = false;
	}

	public static Connection getConnection() {

		Properties properties = new Properties();
		try {

			if (connection == null) {
				properties = Configurator.getProperties();
				String url = "jdbc:mysql://localhost"
						+ (properties.getProperty("data.port") == null ? ""
								: (":" + properties.getProperty("data.port")))
						+ "/";
				if (!test) {
					url += properties.getProperty("data.database");
				} else {
					url += properties.getProperty("data.test.database");
				}
                url+="?autoReconnect=true";
				String userName = properties.getProperty("data.username");
				String password = properties.getProperty("data.password");

				if (logger.isInfoEnabled()) {
					logger.info("Criando uma conexão com o banco de dados...");
				}

				Class.forName("com.mysql.jdbc.Driver").newInstance();

				if (logger.isDebugEnabled()) {
					logger.debug("url = " + url + "\nusername = " + userName);
				}

				connection = DriverManager.getConnection(url, userName,
						password);

				if (logger.isInfoEnabled()) {
					logger.info("Conexão com o banco de dados estabelecida.");
				}
			}

		} catch (InstantiationException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Erro durante a conexão com o banco de dados.");
			}
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Erro durante a conexão com o banco de dados.");
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Erro durante a conexão com o banco de dados.");
			}
			e.printStackTrace();
		} catch (SQLException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Erro durante a conexão com o banco de dados.");
			}
			e.printStackTrace();
		}

		return connection;
	}

	public static void setTestMode() {
		test = true;
	}

}
