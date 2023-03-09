package com.KoreaIT.java.AM.temp;

import java.util.ArrayList;
import java.util.Scanner;

public class Temp {
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
                Article.list();

            } else if (command.equals("article write")) {
                System.out.print("제목: ");
                String title = sc.nextLine();
                System.out.print("내용: ");
                String body = sc.nextLine();

                Article.write(title, body);

            } else if (command.equals("article read")) {
                System.out.print("읽을 글의 번호를 입력해주세요. >> ");
                String stringArticleId = sc.nextLine();

                Article.read(stringArticleId);

            } else {
                System.out.println("존재하지 않는 명령어입니다.");
            }
        }

        System.out.println("==프로그램 끝==");

        sc.close(); // 자원 사용 종료를 위해 원칙적으로는 꺼줘야함
    }
}

class Article {
    static ArrayList<Article> articles = new ArrayList<>();
    static int lastArticleId = 0;
    private int articleId;
    String title;
    String body;

    static void list() {
        if (articles.size() == 0) {
            System.out.println("게시글이 없습니다.");
        } else {
            System.out.println("번호     /    제목");
            for (int i = articles.size() - 1; i >= 0; i--) {
                int articleId = articles.get(i).articleId;
                String title = articles.get(i).title;
                System.out.printf("  %d     /    %s\n", articleId, title);
            }
        }
    }

    static void list2() {
        if (articles.size() == 0) {
            System.out.println("게시글이 없습니다.");
        } else {
            System.out.println("번호     /    제목");
            articles.stream()
                    .map(article -> " " + article.articleId + "      /    " + article.title)
                    .forEach(System.out::println);
        }
    }

    static void write(String title, String body) {
        Article article = new Article();
        article.articleId = Article.lastArticleId + 1;
        ++Article.lastArticleId;

        article.title = title;
        article.body = body;
        articles.add(article);

        System.out.printf("%d번글이 생성되었습니다.\n", article.articleId);
    }

    static void read(String stringArticleId) {
        int articleId = Integer.parseInt(stringArticleId);
        if (articleId > lastArticleId || articleId < 1) {
            System.out.println("입력하신 글번호는 없는 글번호입니다.");
        } else {
            System.out.println("제목:\n" + articles.get(articleId - 1).title);
            System.out.println("내용:\n" + articles.get(articleId - 1).body);
        }
    }
}

class Member {
    int memberId;
    String name;

}