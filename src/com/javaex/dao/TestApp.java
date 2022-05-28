package com.javaex.dao;

import java.util.List;

import com.javaex.vo.GuestBookVo;

public class TestApp {

	public static void main(String[] args) {

		 GuestBookDao guestBookDao = new GuestBookDao();
		 //GuestBookVo v02 = new GuestBookVo("못난이", "뚱땡이","잘가", "2001-11-13"); 
		 
		 //guestBookDao.guestBookInsert(v02);
			
		 
		 //guestBookDao.guestBookDelete(5);
		  
		 //guestBookDao.guestBookUpdate("못난이", "뚱땡이","잘가", "2001-11-11", 1);
		 
		
		 List<GuestBookVo> guestBookList = guestBookDao.guestBookList();
		
		 for(int i = 0; i < guestBookList.size(); i++) {
			 System.out.println(guestBookList.get(i).toString());
		 }
	}

}
