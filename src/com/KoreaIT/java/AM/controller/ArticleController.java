package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.service.ArticleService;
import com.KoreaIT.java.AM.service.MemberService;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController extends Controller {
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void doAction(String actionMethodName, String command) {
		this.actionMethodName = actionMethodName;
		this.command = command;

		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다.");
			break;
		}
	}

	private void doWrite() {
		System.out.print("제목: ");
		String title = sc.nextLine();
		System.out.print("내용: ");
		String body = sc.nextLine();
		String regDate = Util.getNowDateTimeStr();

		int id = articleService.setNewId();
		Article article = new Article(id, regDate, regDate, loginedMember.id, title, body);
		articleService.add(article);

		System.out.printf("%d번글이 생성되었습니다.\n", article.id);
	}

	private void showList() {
		String searchKeyword = command.substring("article list".length()).trim();

		List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);

		if (forPrintArticles.size() == 0) {
			System.out.println("게시글이 없습니다.");
			return;
		}

//		if (forPrintArticles.size() ==0)
//
//		if (searchKeyword.length() > 0) {
//			System.out.println("searchKeyword : " + searchKeyword);
//			forPrintArticles = new ArrayList<>();
//
//			for (Article article : Container.articleDao.articles) {
//				if (article.title.contains(searchKeyword)) {
//					forPrintArticles.add(article);
//				}
//			}
//			if (forPrintArticles.size() == 0) {
//				System.out.println("검색 결과가 없습니다");
//				return;
//			}
//		}
		String writerName = null;
		List<Member> members = memberService.getMembers();

		System.out.println("   번호   //   제목   //   조회   //   작성자   ");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			for (Member member : members) {
				if (article.id == member.id) {
					writerName = member.name;
					break;
				}
			}
			System.out.printf("   %d   //   %s   //   %d   //   %s   \n", article.id, article.title, article.hit,
					writerName);
		}
	}

	private void showDetail() {
		String[] cmdDiv = command.split(" ");
		if (cmdDiv.length != 3) {
			System.out.println("명령어를 확인해주세요.");
			return;
		}
		int id;
		try {
			id = Integer.parseInt(cmdDiv[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호를 숫자로 입력해주세요.");
			return;
		}
		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		
		String writerName = null;
		List<Member> members = memberService.getMembers();
		for (Member member : members) {
			if (member.id == foundArticle.memberId) {
				writerName = member.name;
				break;
			}
		}
		foundArticle.hit++;
		System.out.println("번호: " + foundArticle.id);
		System.out.println("작성날짜: " + foundArticle.regDate);
		System.out.println("수정날짜: " + foundArticle.updateDate);
		System.out.println("작성자: " + writerName); // 여기 메소드 수정해야함
		System.out.println("제목: " + foundArticle.title);
		System.out.println("내용: " + foundArticle.body);
		System.out.println("조회수: " + foundArticle.hit);
	}

	private void doModify() {
		String[] cmdDiv = command.split(" ");
		if (cmdDiv.length != 3) {
			System.out.println("명령어를 확인해주세요.");
			return;
		}

		int id;

		try {
			id = Integer.parseInt(cmdDiv[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호를 숫자로 입력해주세요.");
			return;
		}

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}

		System.out.print("수정할 제목: ");
		String title = sc.nextLine();
		System.out.print("수정할 내용: ");
		String body = sc.nextLine();
		String modDate = Util.getNowDateTimeStr();

		foundArticle.title = title;
		foundArticle.body = body;
		foundArticle.updateDate = modDate;
		System.out.println(foundArticle.id + "번 게시물이 수정되었습니다.");
	}

	private void doDelete() {
		String[] cmdDiv = command.split(" ");
		if (cmdDiv.length != 3) {
			System.out.println("명령어를 확인해주세요.");
			return;
		}

		int id;

		try {
			id = Integer.parseInt(cmdDiv[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호를 숫자로 입력해주세요.");
			return;
		}

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}

		articleService.remove(foundArticle);
		System.out.println(id + "번 게시물이 삭제되었습니다.");
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
		articleService
				.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 2, "1번글 제목임", "1번글 내용", 11));
		articleService
				.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 2, "2번글 제목", "2번글 내용임", 22));
		articleService.add(
				new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 3, "3번글 제목입니다.", "3번글 내용이다/", 33));
	}
}
