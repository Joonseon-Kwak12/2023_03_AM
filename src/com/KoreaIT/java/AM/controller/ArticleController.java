package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController {
	List<Article> articles;
	private Scanner sc;
	
	public ArticleController(List<Article> articles, Scanner sc) {
		this.articles = articles;
		this.sc =sc;
	}
	
	int lastArticleId = 3;
	
	public void doWrite() {
		System.out.print("제목: ");
		String title = sc.nextLine();
		System.out.print("내용: ");
		String body = sc.nextLine();
		String regDate = Util.getNowDateTimeStr();

		int articleId = ++lastArticleId;
		Article article = new Article(articleId, regDate, regDate, title, body);
		articles.add(article);

		System.out.printf("%d번글이 생성되었습니다.\n", article.id);
	}

	public void showList(String command) {
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

		System.out.println(" 번호  //  제목    //  조회  ");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			System.out.printf("  %d   //   %s   //   %d  \n", article.id, article.title, article.hit);
		}
	}
	
	public void showDetail(String command) {
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
		System.out.println("조회수: " + foundArticle.hit);
		System.out.println("제목: " + foundArticle.title);
		System.out.println("내용: " + foundArticle.body);		
	}
	
	public void doModify(String command) {
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
	
	public void doDelete(String command) {
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

		int foundIndex = getArticleIndexByArticleId(id);

		if (foundIndex == -1) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
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
	
	public List<Article> makeTestData() {
		articles.add(new Article(1, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "1번글 제목", "1번글 내용", 11));
		articles.add(new Article(2, "2022-12-02 12:12:12", "2022-12-02 12:12:12", "2번글 제목", "2번글 내용", 22));
		articles.add(new Article(3, "2022-12-03 12:12:12", "2022-12-03 12:12:12", "3번글 제목", "3번글 내용", 33));
		return articles;
	}
}
