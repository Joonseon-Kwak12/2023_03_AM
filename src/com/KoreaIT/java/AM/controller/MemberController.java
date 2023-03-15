package com.KoreaIT.java.AM.controller;

import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.service.MemberService;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private MemberService memberService;

	public MemberController(Scanner sc) {
		this.sc = sc;
		memberService = Container.memberService;
	}

	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "profile":
			showProfile();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다.");
			break;
		}
	}

	private void doJoin() {
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
		int id = Container.memberDao.setNewId();
		Member member = new Member(id, regDate, regDate, loginId, loginPw, memberName);
		Container.memberDao.add(member);

		System.out.printf("%d번 회원이 가입되었습니다\n", id);
	}

	//
	//
	private void doLogin() {
		Member member = null;
		String loginId = null;
		String loginPw = null;

		while (true) {
			System.out.print("로그인 아이디: ");
			loginId = sc.nextLine();

			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("로그인 비밀번호: ");
			loginPw = sc.nextLine();

			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			break;
		}
		
		member = getMemberByLoginId(loginId);
		if (member == null) {
			System.out.println("일치하는 회원 아이디가 없습니다.");
			return;
		}
		if (!member.loginPw.equals(loginPw)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}

		loginedMember = member;
		System.out.printf("로그인 성공! %s님 반갑습니다.\n", loginedMember.name);
	}

	//
	//
	private void doLogout() {
		if (!isLogined()) {
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}
		loginedMember = null;
		System.out.println("로그아웃 되었습니다.");
	}

	//
	//
	private void showProfile() {
		System.out.println("== 현재 로그인된 회원 정보 ==");
		System.out.printf("나의 회원번호: %d\n", loginedMember.id);
		System.out.printf("로그인 아이디: %s\n", loginedMember.loginId);
		System.out.printf("이름: %s\n", loginedMember.name);
	}

	//
	//
	private int getMemberIndexByLoginId(String loginId) {
		return memberService.getMemberIndexByLoginId(loginId);
	}

	private boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return true;
		}

		return false;
	}

	private Member getMemberByLoginId(String loginId) {
		return memberService.getMemberByLoginId(loginId);	
	}

	protected Member getLoginedMember() {
		return loginedMember;
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		Container.memberDao
				.add(new Member(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "test1", "test1", "1철수"));
		Container.memberDao
				.add(new Member(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "test2", "test2", "2철수"));
		Container.memberDao
				.add(new Member(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "test3", "test3", "3철수"));
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
