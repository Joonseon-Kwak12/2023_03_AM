package com.KoreaIT.java.AM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.AM.dto.Member;

public class MemberDao extends Dao {
	List<Member> members;
	
	public MemberDao() {
		members = new ArrayList<>();
	}
	
	public int getLastId() {
		return lastId;
	}
	
	public int setNewId() {
		return lastId + 1;
	}
	
	public int getMemberIndexByLoginId(String loginId) {
		for (Member member : members) {
			int i = 0;
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public void add(Member member) {
		members.add(member);
		lastId++;
	}

	public Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return null;
		}
		return members.get(index);
	}

}
