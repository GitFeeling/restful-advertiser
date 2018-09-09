package com.gitfeeling.restadvertiser.common;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DBFactory {
	
	private static EmbeddedDatabase db;
	
	public static EmbeddedDatabase getDB() {
		if (db == null) {
			db = new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
		            .addScript("schema.sql")
		            .addScript("data.sql")
		            .build();			
		}
		return db;
	}

}
