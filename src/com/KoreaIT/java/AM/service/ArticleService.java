package com.KoreaIT.java.AM.service;

import java.util.List;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dao.ArticleDao;
import com.KoreaIT.java.AM.dto.Article;

public class ArticleService {
	
	private ArticleDao articleDao;
	
	public ArticleService() {
		this.articleDao = Container.articleDao;
	}
	
	public void add(Article article) {
		
	}

	public List<Article> getForPrintArticles(String searchKeyword) {
		List<Article> articles = Container.articleDao.getArticles(searchKeyword);
		
		return articles;
	}
	
	
}
