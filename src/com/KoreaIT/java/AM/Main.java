package com.KoreaIT.java.AM;

import java.util.Scanner;

public class Main {
	static int lastArticleId = 0;

	public static void main(String[] args) {
		System.out.println("==프로그램 시작==");

		Scanner sc = new Scanner(System.in);
		// int articleNum = finalArticleNum + 1; 여기에 넣으면 "1번글이 생성되었습니다."만 나옴

		while (true) {
			System.out.print("명령어 >> ");
			String command = sc.nextLine();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}

			if (command.equals("exit")) { // 이런 코드는 따로 빼준다.
				break;
			}

			if (command.equals("article list")) {
				System.out.println("게시글이 없습니다.");
			} else if (command.equals("article write")) {
				int articleId = lastArticleId + 1;
				System.out.print("제목: ");
				String title = sc.nextLine();
				System.out.print("내용: ");
				String body = sc.nextLine();
				System.out.printf("%d번글이 생성되었습니다.\n", articleId);
				++lastArticleId;
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("==프로그램 끝==");

		sc.close(); // 자원 사용 종료를 위해 원칙적으로는 꺼줘야함
	}
}
//브라우저, DB 없이 명령어를 입력하는 방식으로 콘솔에서 운영되는 게시판이라고 가정
// 1. 게시글 관련 기능(Article 혹은 Post), 2. 회원 관련 기능(Member)