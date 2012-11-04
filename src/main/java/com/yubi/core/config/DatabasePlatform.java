package com.yubi.core.config;

public enum DatabasePlatform {
	
	MY_SQL("com.mysql.jdbc.Driver") {
		
		public String getUrl(String host, int port, String databaseName) {
			return String.format("jdbc:mysql://%s:%d/%s", host, port, databaseName);
		}
		
	};
	
	private String driverName;
	
	private DatabasePlatform(String driverName) {
		this.driverName = driverName;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
	public abstract String getUrl(String host, int port, String databaseName);
}
