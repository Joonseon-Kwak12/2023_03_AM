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
	
	public Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return null;
		}
		return members.get(index);
	}
	
	public boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);
		if (index == -1) {
			return true;
		} 
		return false;
	}

	public List<Member> getMembers() {
		return members;
	}
	
	public void add(Member member) {
		members.add(member);
		lastId++;
	}

}
