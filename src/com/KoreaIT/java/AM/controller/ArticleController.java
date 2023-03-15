package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController extends Controller {
	List<Article> articles;
	private Scanner sc;
	private String command;
	private String actionMethodName;

	int lastArticleId = 0;

	public ArticleController(Scanner sc) {
		this.articles = Container.articleDao.articles;
		this.sc = sc;
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

		int articleId = ++lastArticleId;
		Article article = new Article(articleId, regDate, regDate, loginedMember.id, title, body);
		articles.add(article);

		System.out.printf("%d번글이 생성되었습니다.\n", article.id);
	}

	private void showList() {
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		String searchKeyword = command.substring("article list".length()).trim();

		List<Article> forPrintArticles = articles;

		if (searchKeyword.length() > 0) {
			System.out.println("searchKeyword : " + searchKeyword);
			forPrintArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}
			if (forPrintArticles.size() == 0) {
				System.out.println("검색 결과가 없습니다");
				return;
			}
		}

		String writerName = null;

		List<Member> members = Container.memberDao.members;

		System.out.println("   번호   //   제목   //   조회   //   작성자   ");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			for (Member member : members) {
				if (article.memberId == member.id) {
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
		int articleId;
		try {
			articleId = Integer.parseInt(cmdDiv[2]);
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호를 숫자로 입력해주세요.");
			return;
		}
		Article foundArticle = getArticleByArticleId(articleId);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", articleId);
			return;
		}
		foundArticle.hit++;
		
		System.out.println("번호: " + foundArticle.id);
		System.out.println("작성날짜: " + foundArticle.regDate);
		System.out.println("수정날짜: " + foundArticle.updateDate);
		System.out.println("작성자: " + getMemberNameByArticle(foundArticle));
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

		Article foundArticle = getArticleByArticleId(id);

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

		int foundIndex = getArticleIndexByArticleId(id); // 사실 여기서 index 기준이 아니라 object 기준으로 작업해도 전혀 상관이 없다.

		if (foundIndex == -1) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		if (articles.get(foundIndex).memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}

		articles.remove(foundIndex);
		System.out.println(id + "번 게시물이 삭제되었습니다.");
	}

	private int getArticleIndexByArticleId(int articleId) {
		for (Article article : articles) {
			if (article.id == articleId) {
				return articles.indexOf(article);
			}
		}
		return -1;
	}

	private Article getArticleByArticleId(int articleId) {
		int index = getArticleIndexByArticleId(articleId);
		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	private String getMemberNameByArticle(Article article) {
		String writerName = null;

		List<Member> members = Container.memberDao.members;

		for (Member member : members) {
			if (member.id == article.memberId) {
				writerName = member.name;
				return writerName;
			}
		}
		return "확인할 수 없는 작성자";
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 2, "1번글 제목임", "1번글 내용", 11));
		articles.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 2, "2번글 제목", "2번글 내용임", 22));
		articles.add(
				new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 3, "3번글 제목입니다.", "3번글 내용이다/", 33));
		lastArticleId = articles.get(articles.size() - 1).id;
	}
}
