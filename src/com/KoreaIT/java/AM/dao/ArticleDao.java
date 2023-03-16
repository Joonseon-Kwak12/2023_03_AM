package com.KoreaIT.java.AM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;

public class ArticleDao extends Dao {
	List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public int setNewId() {
		return lastId + 1;
	}

	public int getLastId() {
		return lastId;
	}

	public int getArticleIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public String getMemberNameByArticle(Article article) { // 안 쓰는 중
		for (Member member : Container.memberDao.members) {
			if (member.id == article.memberId) {
				return member.name;
			}
		}
		return "확인할 수 없는 작성자";
	}

	public Article getArticleById(int id) {
		int index = getArticleIndexById(id);
		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	public void add(Article article) {
		articles.add(article);
		lastId++;
	}

	public void remove(Article foundArticle) {
		articles.remove(foundArticle);
	}

	public List<Article> getArticles(String searchKeyword) {
		if (searchKeyword.length() > 0 && searchKeyword != null) {
			System.out.println("검색어: searchKeyword");

			List<Article> forPrintArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}
			return forPrintArticles;
		}
		return articles;
	}

}
