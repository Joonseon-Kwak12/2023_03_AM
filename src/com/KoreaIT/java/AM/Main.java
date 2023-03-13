package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//브라우저, DB 없이 명령어를 입력하는 방식으로 콘솔에서 운영되는 게시판이라고 가정
//1. 게시글 관련 기능(Article 혹은 Post), 2. 회원 관련 기능(Member)
public class Main {
	static List<Article> articles = new ArrayList<>();
	static List<Member> members = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("==프로그램 시작==");

		makeTestData();
		makeMemberTestData();

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 3;
		int lastMemberId = 3;
		
		while (true) {
			System.out.print("명령어 >> ");
			String command = sc.nextLine().trim();
			//
			//
			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}
			//
			//
			if (command.equals("addt3st")) {
				if (lastArticleId != 0) {
					System.out.println("다른 글이 있을 경우 addt3st는 사용할 수 없습니다.");
					TestArticles.isAddedByTest = true;
					continue;
				} else {
					System.out.println("테스트용 데이터를 불러옵니다.");
					TestArticles.addTestArticles(articles);
					TestArticles.isAddedByTest = true;
					lastArticleId = articles.size();
					continue;
				}
			}
			//
			//
			if (command.equals("exit")) { // 이런 코드는 따로 빼준다.
				break;
			}
			//
			// article list
			if (command.startsWith("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
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
						continue;
					}
				}

				System.out.println(" 번호  //  제목    //  조회  ");
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					Article article = forPrintArticles.get(i);
					System.out.printf("  %d   //   %s   //   %d  \n", article.articleId, article.title, article.hits);
				}

				//
				//
				// article write
			} else if (command.equals("article write")) {
				System.out.print("제목: ");
				String title = sc.nextLine();
				System.out.print("내용: ");
				String body = sc.nextLine();
				String regDate = Util.getNowDateTimeStr();

				int articleId = ++lastArticleId;
				Article article = new Article(articleId, regDate, regDate, title, body);
				articles.add(article);

				System.out.printf("%d번글이 생성되었습니다.\n", article.articleId);
				//
				//
				// article detail
			} else if (command.startsWith("article detail")) {
				String[] cmdDiv = command.split(" ");
				if (cmdDiv.length != 3) {
					System.out.println("명령어를 확인해주세요.");
					continue;
				}
				int articleId;
				try {
					articleId = Integer.parseInt(cmdDiv[2]);
				} catch (NumberFormatException e) {
					System.out.println("게시물 번호를 숫자로 입력해주세요.");
					continue;
				}
				Article foundArticle = getArticleByArticleId(articleId);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", articleId);
					continue;
				}
				foundArticle.hits++;
				System.out.println("번호: " + foundArticle.articleId);
				System.out.println("작성날짜: " + foundArticle.regDate);
				System.out.println("수정날짜: " + foundArticle.modDate);
				System.out.println("조회수: " + foundArticle.hits);
				System.out.println("제목: " + foundArticle.title);
				System.out.println("내용: " + foundArticle.body);
				//
				//
				// article delete
			} else if (command.startsWith("article delete")) {
				String[] cmdDiv = command.split(" ");
				if (cmdDiv.length != 3) {
					System.out.println("명령어를 확인해주세요.");
					continue;
				}
				int articleId;
				try {
					articleId = Integer.parseInt(cmdDiv[2]);
				} catch (NumberFormatException e) {
					System.out.println("게시물 번호를 숫자로 입력해주세요.");
					continue;
				}

				int foundIndex = getArticleIndexByArticleId(articleId);

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", articleId);
					continue;
				}

				articles.remove(foundIndex);
				System.out.println(articleId + "번 게시물이 삭제되었습니다.");
				//
				//
				// article modify
			} else if (command.startsWith("article modify")) {
				String[] cmdDiv = command.split(" ");
				if (cmdDiv.length != 3) {
					System.out.println("명령어를 확인해주세요.");
					continue;
				}
				int articleId;
				try {
					articleId = Integer.parseInt(cmdDiv[2]);
				} catch (NumberFormatException e) {
					System.out.println("게시물 번호를 숫자로 입력해주세요.");
					continue;
				}

				Article foundArticle = getArticleByArticleId(articleId);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다.\n", articleId);
					continue;
				}
				System.out.print("수정할 제목: ");
				String title = sc.nextLine();
				System.out.print("수정할 내용: ");
				String body = sc.nextLine();
				String modDate = Util.getNowDateTimeStr();

				foundArticle.title = title;
				foundArticle.body = body;
				foundArticle.modDate = modDate;
				System.out.println(foundArticle.articleId + "번 게시물이 수정되었습니다.");
				//
				//
				// member join
			} else if (command.equals("member join")) {
//				System.out.print("사용할 아이디를 입력해주세요 >> ");
//				String memberLoginId = sc.nextLine();
//				if (isDuplicationMemberId(memberLoginId)) {
//					do {
//						System.out.printf("이미 사용중인 아이디입니다.");
//						System.out.print("사용할 아이디를 다시 입력해주세요 >> ");
//						memberLoginId = sc.nextLine();
//					} while (isDuplicationMemberId(memberLoginId));
//				}
//				System.out.printf("입력된 아이디는 %s입니다.\n", memberLoginId);
//				System.out.print("사용할 비밀번호를 입력해주세요 >> ");
//				String memberLoginPw = sc.nextLine();
//				System.out.print("이름을 입력해주세요 >> ");
//				String memberName = sc.nextLine();
//
//				String memberRegDate = Util.getNowDateTimeStr();
//				int memberId = ++lastMemberId;
//				members.add(new Member(memberId, memberRegDate, memberRegDate, memberLoginId, memberLoginPw, memberName));
//				System.out.printf("%d번 회원님 가입이 완료되었습니다.\n회원님의 아이디는 %s입니다.\n", memberId, memberLoginId);
				int memberId = lastMemberId + 1;
				
				String memberLoginId = null;
				while (true) {
					System.out.print("로그인 아이디 : ");
					memberLoginId = sc.nextLine();
					
					if (isJoinableLoginId(memberLoginId) == false) {
						System.out.println("이미 사용중인 아이디입니다");
						continue;
					}
					break;
				}

				String memberLoginPw;
				while (true) {
					System.out.print("로그인 비밀번호 : ");
					memberLoginPw = sc.nextLine();
					System.out.print("로그인 비밀번호 확인: ");
					String loginPwConfirm = sc.nextLine();
					
					if (!memberLoginPw.equals(loginPwConfirm)) {
						System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
						continue;
					}
					
					break;
				}
				System.out.print("이름 : ");
				String memberName = sc.nextLine();

				String memberRegDate = Util.getNowDateTimeStr();
				Member member = new Member(memberId, memberRegDate, memberRegDate, memberLoginId, memberLoginPw, memberName);
				members.add(member);

				System.out.printf("%d번 회원이 가입되었습니다\n", memberId);
				lastMemberId++;

				//
				//
				//
			} else if (command.equals("member list")) {
				//
				//
				// 존재하지 않는 명령어 출력
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("==프로그램 끝==");

		sc.close(); // 자원 사용 종료를 위해 원칙적으로는 꺼줘야함
	}

	private static int getArticleIndexByArticleId(int articleId) {
		for (Article article : articles) {
			if (article.articleId == articleId) {
				return articles.indexOf(article);
			}
		}
		return -1;
	}

	private static Article getArticleByArticleId(int articleId) {
		int index = getArticleIndexByArticleId(articleId);
		if (index != -1) {
			return articles.get(index);
		}
		return null;
	}

	private static boolean isDuplicationMemberId(String memberLoginId) { // 내가 작성한 것 일단 남겨놓음
		for (Member member : members) {
			if (member.memberLoginId.equals(memberLoginId)) { // equals가 아니라 ==로 하면 안 됨....
				return true;
			}
		}
		return false;
	}
	
	private static boolean isJoinableLoginId(String memberLoginId) {
		int index = getMemberIndexByMemberLoginId(memberLoginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	private static int getMemberIndexByMemberLoginId(String memberLoginId) {
		int i = 0;
		for (Member member : members) {
			if (member.memberLoginId.equals(memberLoginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	static List<Article> makeTestData() {
		articles.add(new Article(1, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "1번글 제목", "1번글 내용", 11));
		articles.add(new Article(2, "2022-12-02 12:12:12", "2022-12-02 12:12:12", "2번글 제목", "2번글 내용", 22));
		articles.add(new Article(3, "2022-12-03 12:12:12", "2022-12-03 12:12:12", "3번글 제목", "3번글 내용", 33));
		return articles;
	}

	static List<Member> makeMemberTestData() {
		members.add(new Member(1, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "abc11", "abc1!", "김철수"));
		members.add(new Member(2, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "abc22", "abc2@", "이철수"));
		members.add(new Member(3, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "abc33", "abc3#", "박철수"));
		return members;
	}

	static int splitCommand(String command) {
		return 0;
	}
}

class Article {
	int articleId;
	String regDate;
	String modDate;
	String title;
	String body;
	int hits;

	Article(int articleId, String regDate, String modDate, String title, String body) {
		this(articleId, regDate, modDate, title, body, 0);
	}

	Article(int articleId, String regDate, String modDate, String title, String body, int hits) {
		this.articleId = articleId;
		this.regDate = regDate;
		this.modDate = modDate;
		this.title = title;
		this.body = body;
		this.hits = hits;
	}
}

class Member {
	int memberId;
	String memberRegDate;
	String memberModDate;
	String memberLoginId;
	String memberLoginPw;
	String memberName;

	Member(int memberId, String memberRegDate, String memberModDate, String memberLoginId, String memberLoginPw, String memberName) {
		this.memberId = memberId;
		this.memberRegDate = memberRegDate;
		this.memberModDate = memberModDate;
		this.memberLoginId = memberLoginId;
		this.memberLoginPw = memberLoginPw;
		this.memberName = memberName;
	}
}

class TestArticles {
	static boolean isAddedByTest = false;

	public static void addTestArticles(List articles) {
		articles.add(new Article(1, "2023-01-01 12:12:12", "2023-01-01 12:12:12", "1번글 제목", "1번글 내용"));
		articles.add(new Article(2, "2023-01-02 12:12:12", "2023-01-02 12:12:12", "2번글 제목", "2번글 내용"));
		articles.add(new Article(3, "2023-01-04 12:12:12", "2023-01-16 12:12:12", "3번글 제목", "3번글 내용"));
		articles.add(new Article(4, "2023-01-07 12:12:12", "2023-01-07 12:12:12", "4번글 제목", "4번글 내용"));
		articles.add(new Article(5, "2023-01-07 12:12:12", "2023-01-07 12:12:12", "5번글 제목", "5번글 내용"));
		articles.add(new Article(6, "2023-01-10 12:12:12", "2023-01-10 12:12:12", "6번글 제목", "6번글 내용"));
		articles.add(new Article(7, "2023-01-15 12:12:12", "2023-01-15 12:12:12", "7번글 제목", "7번글 내용"));
		articles.add(new Article(8, "2023-01-16 12:12:12", "2023-01-25 12:12:12", "8번글 제목", "8번글 내용"));
		articles.add(new Article(9, "2023-01-18 12:12:12", "2023-01-18 12:12:12", "9번글 제목", "9번글 내용"));
		articles.add(new Article(10, "2023-01-20 12:12:12", "2023-01-25 12:12:12", "10번글 제목", "10번글 내용"));
		articles.add(new Article(11, "2023-01-25 12:12:12", "2023-01-25 12:12:12", "11번글 제목", "11번글 내용"));
		articles.add(new Article(12, "2023-01-31 12:12:12", "2023-01-31 12:12:12", "12번글 제목", "12번글 내용"));
		articles.add(new Article(13, "2023-02-01 12:12:12", "2023-02-01 12:12:12", "13번글 제목", "13번글 내용"));
	}
}