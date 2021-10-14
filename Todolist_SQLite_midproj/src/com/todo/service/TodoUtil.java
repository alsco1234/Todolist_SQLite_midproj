package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSet;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		String title, desc, place, category, due_date;
		int priority;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "*** Create item ***\n"
				+ "title : ");
		
		title = sc.nextLine();
		for (TodoItem item : list.getList()) {
			if (item.getTitle().equals(title)) {
				System.out.println("제목은 중복될 수 없습니다.");
				return;
			}
		}
		System.out.print("priority : ");
		priority= Integer.parseInt(sc.nextLine());
		System.out.print("description : ");
		desc = sc.nextLine();
		System.out.print("place : ");
		place = sc.nextLine();
		System.out.print("category : ");
		category = sc.nextLine();
		System.out.print("due_date : ");
		due_date = sc.nextLine();
		TodoItem t = new TodoItem(title, priority, desc, place, category, due_date);
		if(list.addItem(t) > 0)
			System.out.println("add item into list!");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 삭제]\n삭제할 항목의 번호들을 입력하시오 > ");
		//한줄받아서
		String line = sc.nextLine();
		//쪼개서 배열에 저장한다음
		String[] arr = line.split(" ");
		//배열만큼 돌려서 하나하나인덱스 넣고 삭제하자
		for(int i=0; i<arr.length; i++) {
			int index = Integer.parseInt(arr[i]);
			l.deleteItem(index);
		}
		System.out.println(arr.length +"개를 삭제었습니다.");
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "*** Edit Item ***\n"
				+ "num : ");
		int index = Integer.parseInt(sc.nextLine());
		
		System.out.print("new title : ");
		String new_title = sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목은 중복될 수 없습니다.");
			return;
		}
		System.out.print("new priority : ");
		int new_priority= Integer.parseInt(sc.nextLine());
		System.out.print("new description : ");
		String new_desc = sc.nextLine();
		System.out.print("new place : ");
		String new_place = sc.nextLine();
		System.out.print("new category : ");
		String new_category = sc.nextLine();
		System.out.print("new due_date : ");
		String new_due_date = sc.nextLine();
		TodoItem t = new TodoItem(new_title, new_priority, new_desc, new_place, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
			System.out.println("수정되었습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void findList(TodoList l, String keyword) {
		int count =0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void ComplistAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getCompList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("[카테고리 목록, 총 %d개]\n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("[총 %d개의 항목을 찾았습니다.]\n", count);
	}

	public static void completeItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 삭제]\n삭제할 항목의 번호들을 입력하시오 > ");
		//한줄받아서
		String line = sc.nextLine();
		//쪼개서 배열에 저장한다음
		String[] arr = line.split(" ");
		//배열만큼 돌려서 하나하나인덱스 넣고 삭제하자
		for(int i=0; i<arr.length; i++) {
			int index = Integer.parseInt(arr[i]);
			l.compItem(index);
		}
		System.out.println(arr.length +"개를 완료체크하습니다.");
	}

	public static void WeeklistAll(TodoList l) {
		System.out.printf("[이번주 할일 목록]\n");
		for (TodoItem item : l.getWeekList()) {
			System.out.println(item.toString());
		}
	}

	public static void compdelItem(TodoList l) {
		int count = l.compedItem();
		System.out.printf("[총 %d개의 완료된 항목을 삭제하습니다.]\n", count);
	}
}
