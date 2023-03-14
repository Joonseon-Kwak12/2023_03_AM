package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Member;

public abstract class Controller {
	protected static Member loginedMember = null; // static으로 안 해놓으면 정보가 유지되지 않아서 member login 후 article write 등을 할 수가 없음
	
	public static boolean isLogined() {
		return loginedMember != null;
	}
	
	public abstract void doAction(String actionMethodName, String command);
	
	public abstract void makeTestData();
	// 각 컨트롤러에서 오버라이딩
}