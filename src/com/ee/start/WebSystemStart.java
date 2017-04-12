package com.ee.start;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.ee.Facade.UserFacade;

@ApplicationPath("/rest")
public class WebSystemStart extends Application{
	
	
	public WebSystemStart() {
		UserFacade fc = new UserFacade();
		fc.createTableIfNeeded();
	}
	
}
