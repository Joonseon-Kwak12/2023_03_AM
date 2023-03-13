package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController {
	List<Member> members;
	private Scanner sc;

	public MemberController(List<Member> members, Scanner sc) {
		this.members = members;
		this.sc = sc;
	}

	int lastMemberId = 0;

	public void doJoin() {
//		System.out.print("사용할 아이디를 입력해주세요 >> ");
//		String memberLoginId = sc.nextLine();
//		if (isDuplicationMemberId(memberLoginId)) {
//			do {
//				System.out.printf("이미 사용중인 아이디입니다.");
//				System.out.print("사용할 아이디를 다시 입력해주세요 >> ");
//				memberLoginId = sc.nextLine();
//			} while (isDuplicationMemberId(memberLoginId));
//		}
//		System.out.printf("입력된 아이디는 %s입니다.\n", memberLoginId);
//		System.out.print("사용할 비밀번호를 입력해주세요 >> ");
//		String memberLoginPw = sc.nextLine();
//		System.out.print("이름을 입력해주세요 >> ");
//		String memberName = sc.nextLine();
//
//		String memberRegDate = Util.getNowDateTimeStr();
//		int memberId = ++lastMemberId;
//		members.add(new Member(memberId, memberRegDate, memberRegDate, memberLoginId, memberLoginPw, memberName));
//		System.out.printf("%d번 회원님 가입이 완료되었습니다.\n회원님의 아이디는 %s입니다.\n", memberId, memberLoginId);
		int id = lastMemberId + 1;
		String loginId = null;
		
		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine();

			if (isJoinableLoginId(loginId) == false) {
				System.out.println("이미 사용중인 아이디입니다");
				continue;
			}
			break;
		}

		String loginPw;
		while (true) {
			System.out.print("로그인 비밀번호 : ");
			loginPw = sc.nextLine();
			System.out.print("로그인 비밀번호 확인: ");
			String loginPwConfirm = sc.nextLine();

			if (!loginPw.equals(loginPwConfirm)) {
				System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
				continue;
			}

			break;
		}
		System.out.print("이름 : ");
		String memberName = sc.nextLine();

		String regDate = Util.getNowDateTimeStr();
		Member member = new Member(id, regDate, regDate, loginId, loginPw, memberName);
		members.add(member);

		System.out.printf("%d번 회원이 가입되었습니다\n", id);
		lastMemberId++;

		//
		//
	}
		
	private boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}
	
	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public List<Member> makeMemberTestData() {
		members.add(new Member(1, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "abc11", "abc1!", "김철수"));
		members.add(new Member(2, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "abc22", "abc2@", "이철수"));
		members.add(new Member(3, "2022-12-01 12:12:12", "2022-12-01 12:12:12", "abc33", "abc3#", "박철수"));
		return members;
	}
	
//	private boolean isDuplicationMemberId(String memberLoginId) { // 내가 작성한 것 일단 남겨놓음
//		for (Member member : members) {
//			if (member.loginId.equals(memberLoginId)) { // equals가 아니라 ==로 하면 안 됨....
//				return true;
//			}
//		}
//		return false;
//	}
	//
//	if (command.equals("addt3st")) {
//		if (lastArticleId != 0) {
//			System.out.println("다른 글이 있을 경우 addt3st는 사용할 수 없습니다.");
//			TestArticles.isAddedByTest = true;
//			continue;
//		} else {
//			System.out.println("테스트용 데이터를 불러옵니다.");
//			TestArticles.addTestArticles(articles);
//			TestArticles.isAddedByTest = true;
//			lastArticleId = articles.size();
//			continue;
//		}
//	}
	//
	
}
