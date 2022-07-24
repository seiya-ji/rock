package com.pz.rock.core.context;

import org.springframework.context.ApplicationEvent;

public class AppLoadApplicationEvent extends ApplicationEvent{

	private Callback callback;
	
	public AppLoadApplicationEvent(Object source, Callback callback) {
		super(source);
		this.callback = callback;
	}
	
	public Callback getCallback() {
		return callback;
	}
	
	public interface Callback{
		boolean apply();
	}

}
