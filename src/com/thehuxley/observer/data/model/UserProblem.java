package com.thehuxley.observer.data.model;

/** 
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.1
 * @since huxley-avaliador 1.0.0
 */
public class UserProblem {
		private long id;
		private long version;
		private int status;
		private long userId;
		private long problemId;
		public static final int STATUS_CORRECT = 1;
		public static final int STATUS_TRIED = 2;
        public static final int CORRECT = 1;
        public static final int TRIED = 2;
        public static final int NEVER_TRIED = 3;
        public static final int SIMILARITY_STATUS_NOT_MATCHED = 1;
        public static final int SIMILARITY_STATUS_MATCHED = 2;
        public static final int SIMILARITY_STATUS_TEACHER_PLAGIUM = 3;



		public void setId(long id) {
			this.id = id;
		}
		public long getId() {
			return id;
		}
		public void setVersion(long version) {
			this.version = version;
		}
		public long getVersion() {
			return version;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public long getUserId() {
			return userId;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public int getStatus() {
			return status;
		}
		public void setProblemId(long problemId) {
			this.problemId = problemId;
		}
		public long getProblemId() {
			return problemId;
		}
		
}
