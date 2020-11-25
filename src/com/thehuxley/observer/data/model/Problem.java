package com.thehuxley.observer.data.model;
import com.thehuxley.observer.data.Configurator;

/** 
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.1
 * @since huxley-avaliador 1.0.0
 */
public class Problem {
		private long id;
		private long version;
		private int timeLimit;
		private int evaluationDetail;
		private int code;
		private int level;
		private double nd;
		private String name;
		private String status;
		private long userApprovedId;
		private long userSuggestId;
		
		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		
		public long getVersion() {
			return version;
		}
		
		public void setVersion(long version) {
			this.version = version;
		}
		
		public int getTimeLimit() {
			return timeLimit;
		}
		
		public void setTimeLimit(int timeLimit) {
			this.timeLimit = timeLimit;
		}
		
		public int getEvaluationDetail() {
			return evaluationDetail;
		}
		
		public void setEvaluationDetail(int evaluationDetail) {
			this.evaluationDetail = evaluationDetail;
		}
		
		public int getCode() {
			return code;
		}
		
		public void setCode(int code) {
			this.code = code;
		}
						
		public int getLevel() {
			return level;
		}
		
		public void setLevel(int level) {
			this.level = level;
		}
		
		public double getNd() {
			return nd;
		}
		
		public void setNd(double nd) {
			this.nd = nd;
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
		
		public long getUserApprovedId() {
			return userApprovedId;
		}
		
		public void setUserApprovedId(long userApprovedId) {
			this.userApprovedId = userApprovedId;
		}
		
		public long getUserSuggestId() {
			return userSuggestId;
		}
		
		public void setUserSuggestId(long userSuggestId) {
			this.userSuggestId = userSuggestId;
		}
				
		public String mountProblemRoot(){  
			  return Configurator.getProperty("problemdb.dir") + this.id + System.getProperty("file.separator");
		}
		
		public String getInput(){
			return mountProblemRoot()+"input.in";
		}
		
		public String getOutput(){
			return mountProblemRoot()+"output.in";
		}
}
