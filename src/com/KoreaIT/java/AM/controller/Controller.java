package com.KoreaIT.java.AM.controller;

public abstract class Controller {
	public abstract void doAction(String actionMethodName, String command);
	// 각 컨트롤러에서 오버라이딩
}