package com.KoreaIT.java.AM;

//브라우저, DB 없이 명령어를 입력하는 방식으로 콘솔에서 운영되는 게시판이라고 가정
//1. 게시글 관련 기능(Article 혹은 Post), 2. 회원 관련 기능(Member)
public class Main {

	public static void main(String[] args) {
		new App().start();
	}
}