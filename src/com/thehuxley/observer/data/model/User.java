package com.thehuxley.observer.data.model;

import java.util.Date;

/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class User {

		private long id;
		private long version;
		private String passwordHash;
		private String username;
		private String email;
		private String name;
		private String status;
		private Date lastLogin;
		private int topCoderPosition;
		
		public void setId(long id) {
			this.id = id;
		}
		
		public long getId() {
			return id;
		}

		public long getVersion() {
			return version;
		}

		public void setVersion(long version) {
			this.version = version;
		}

		public String getPasswordHash() {
			return passwordHash;
		}

		public void setPasswordHash(String passwordHash) {
			this.passwordHash = passwordHash;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getLastLogin() {
			return lastLogin;
		}

		public void setLastLogin(Date lastLogin) {
			this.lastLogin = lastLogin;
		}

		public int getTopCoderPosition() {
			return topCoderPosition;
		}

		public void setTopCoderPosition(int topCoderPosition) {
			this.topCoderPosition = topCoderPosition;
		}		
			
}
