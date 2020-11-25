package com.thehuxley.observer.data.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourcesUtil {
	static Logger logger = LoggerFactory.getLogger(ResourcesUtil.class);

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
				statement = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	

	public static void closeBufferedWriter(BufferedWriter bW) {
		if (bW != null) {
			try {
				bW.close();
				bW = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	
	public static void closeBufferedReader(BufferedReader bR){
		if (bR != null) {
			try {
				bR.close();
				bR = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	
	
	
	public static void closeDataInputStream(DataInputStream dI) {
		if (dI != null) {
			try {
				dI.close();
				dI = null;
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}
	
	public static void closeProcess(Process proc) {
		if (proc != null) {
			try {
					proc.destroy();
			} catch (Exception e1) {
				logger.error(e1.getMessage());
			}
		}
	}
}
