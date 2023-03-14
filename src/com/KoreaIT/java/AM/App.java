package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.controller.Controller;
import com.KoreaIT.java.AM.controller.MemberController;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;

public class App {

	List<Article> articles;
	List<Member> members;

	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	void start() {
		System.out.println("==프로그램 시작==");

		Scanner sc = new Scanner(System.in);

		ArticleController articleController = new ArticleController(sc);
		MemberController memberController = new MemberController(sc);

		Controller controller;

		articleController.makeTestData();
		memberController.makeTestData();

		while (true) {
			System.out.print("명령어 >> ");
			String command = sc.nextLine().trim();
			//
			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}
			//
			if (command.equals("exit")) { // 이런 코드는 따로 빼준다.
				break;
			}
			//
			String[] commandDiv = command.split(" ");

			if (commandDiv.length == 1) {
				System.out.println("명령어를 확인해주세요.");
				continue;
			}

			String controllerName = commandDiv[0];
			String actionMethodName = commandDiv[1];

			String forLoginCheck = controllerName + "/" + actionMethodName;

			controller = null;

			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}

			switch (forLoginCheck) {
			case "article/write":
			case "article/modify":
			case "article/delete":
			case "member/logout":
			case "member/profile":
				if (!Controller.isLogined()) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}
				break;
			case "member/join":
			case "member/login":
				if (Controller.isLogined()) {
					System.out.println("로그아웃 후 이용해주세요.");
					continue;
				}
				break;
			}

			controller.doAction(actionMethodName, command);
			//
//			if (command.equals("article write")) {
//				articleController.doWrite();
//			} else if (command.startsWith("article list")) {
//				articleController.showList(command);
//			} else if (command.startsWith("article detail")) {
//				articleController.showDetail(command);
//			} else if (command.startsWith("article delete")) {
//				articleController.doDelete(command);
//			} else if (command.startsWith("article modify")) {
//				articleController.doModify(command);
//			} else if (command.equals("member join")) {
//				memberController.doJoin();
//			} else if (command.equals("member list")) {
//				// 아직 구현 안 함
//			} else {
//				System.out.println("존재하지 않는 명령어입니다.");
//			}
		}
		System.out.println("==프로그램 끝==");
		sc.close(); // 자원 사용 종료를 위해 원칙적으로는 꺼줘야함
	}
}

//class TestArticles {
//	boolean isAddedByTest = false;
//
//	public static void addTestArticles(List articles) {
//		articles.add(new Article(1, "2023-01-01 12:12:12", "2023-01-01 12:12:12", "1번글 제목", "1번글 내용"));
//		articles.add(new Article(2, "2023-01-02 12:12:12", "2023-01-02 12:12:12", "2번글 제목", "2번글 내용"));
//		articles.add(new Article(3, "2023-01-04 12:12:12", "2023-01-16 12:12:12", "3번글 제목", "3번글 내용"));
//		articles.add(new Article(4, "2023-01-07 12:12:12", "2023-01-07 12:12:12", "4번글 제목", "4번글 내용"));
//		articles.add(new Article(5, "2023-01-07 12:12:12", "2023-01-07 12:12:12", "5번글 제목", "5번글 내용"));
//		articles.add(new Article(6, "2023-01-10 12:12:12", "2023-01-10 12:12:12", "6번글 제목", "6번글 내용"));
//		articles.add(new Article(7, "2023-01-15 12:12:12", "2023-01-15 12:12:12", "7번글 제목", "7번글 내용"));
//		articles.add(new Article(8, "2023-01-16 12:12:12", "2023-01-25 12:12:12", "8번글 제목", "8번글 내용"));
//		articles.add(new Article(9, "2023-01-18 12:12:12", "2023-01-18 12:12:12", "9번글 제목", "9번글 내용"));
//		articles.add(new Article(10, "2023-01-20 12:12:12", "2023-01-25 12:12:12", "10번글 제목", "10번글 내용"));
//		articles.add(new Article(11, "2023-01-25 12:12:12", "2023-01-25 12:12:12", "11번글 제목", "11번글 내용"));
//		articles.add(new Article(12, "2023-01-31 12:12:12", "2023-01-31 12:12:12", "12번글 제목", "12번글 내용"));
//		articles.add(new Article(13, "2023-02-01 12:12:12", "2023-02-01 12:12:12", "13번글 제목", "13번글 내용"));
//	}
//}