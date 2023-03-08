package com.KoreaIT.java.AM;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("==프로그램 시작==");
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("명령어 >> ");
			String command = sc.nextLine();
			
			if (command.equals("exit")) {
				break;
			}
		}
		
		System.out.println("==프로그램 끝==");
		
		sc.close(); // 자원 사용 종료를 위해 원칙적으로는 꺼줘야함
	}
}
//브라우저, DB 없이 명령어를 입력하는 방식으로 콘솔에서 운영되는 게시판이라고 가정
// 1. 게시글 관련 기능(Article 혹은 Post), 2. 회원 관련 기능(Member)