package com.thehuxley.observer.data.model;

/** 
 * @author Marcio Augusto Guimarães
 * @author Romero Malaquias
 * @version 1.1.0
 * @since huxley-avaliador 1.0.0
 */
public class Language {
	
	private long id;
	private long version;
	private String execParams;
	private String plagConfig;
	private String name;
	private String compileParams;
	private String compile;
	private String script;
	private String extension;
	
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

	public String getExecParams() {
		return execParams;
	}

	public void setExecParams(String execParams) {
		this.execParams = execParams;
	}

	public String getPlagConfig() {
		return plagConfig;
	}

	public void setPlagConfig(String plagConfig) {
		this.plagConfig = plagConfig;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompileParams() {
		return compileParams;
	}

	public void setCompileParams(String compileParams) {
		this.compileParams = compileParams;
	}

	public String getCompile() {
		return compile;
	}

	public void setCompile(String compile) {
		this.compile = compile;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
}
