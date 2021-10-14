package com.todo;

import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todolist.txt");
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			//사용법을 매번 표현하지 않고 hel명령어 입력 시에 나타나도록 수정
			//menu.propmpt()추가
			isList = false;
			String choice = sc.nextLine();
			switch (choice) {
				
			case "help":
				Menu.displaymenu();
				break;
					
			case "add":
				TodoUtil.createItem(l);
				break;
				
			case "comp":
				TodoUtil.completeItem(l);
				break;
				
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "del_comp":
				TodoUtil.compdelItem(l);
				break;
					
			case "edit":
				TodoUtil.updateItem(l);
				break;
					
			case "ls":
				TodoUtil.listAll(l);
				break;
					
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
	
			case "ls_name_asc":
				System.out.println("제목순으로 정렬하였습니다");
				TodoUtil.listAll(l, "title", 1);
				break;
					
			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다");
				TodoUtil.listAll(l, "due_date", 1);
				break;
					
			case "ls_date_desc":
				System.out.println("날짜역순으로 정렬하였습니다");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "ls_comp":
				System.out.println("완료된것만 정렬하였습니다");
				TodoUtil.ComplistAll(l);
				break;
				
			case "ls_week":
				System.out.println("이번주 할 일입니다");
				TodoUtil.WeeklistAll(l);
				break;
				
			case "ls_priority":
				System.out.println("중요도순으로 정렬하였습니다");
				TodoUtil.listAll(l, "priority", 0);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;

			case "exit":
				quit = true;
				break;					
			default:
				System.out.println("enter mentioned command (commands - help)");
				break;
			}
			if(isList) l.listAll();
		} while (!quit);
	}
}
