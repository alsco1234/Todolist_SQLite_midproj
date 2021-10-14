package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import com.todo.service.DBconnect;

public class TodoList {
	private List<TodoItem> list;
	private static Connection conn = null;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DBconnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, place, priority)"
					+ " values (?, ?, ?, ?, ?, ?, ?);";
	PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getPlace());
			pstmt.setString(7, t.getPriority());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int compItem(int num) {
		String sql = "update list set is_completed=1"
				+ " where id ="+num+";";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, place=?, priority=?"
						+ "where id =?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getPlace());
			pstmt.setString(7, t.getPriority());
			pstmt.setString(8, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				int priority = rs.getInt("priority");
				int is_complete = rs.getInt("is_completed");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(id, title, priority, description, place, category, due_date, current_date, is_complete);
				t.setId(id);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				int priority = rs.getInt("priority");
				int is_complete = rs.getInt("is_completed");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(id, title, priority, description, place, category, due_date, current_date, is_complete);
				list.add(t);
			}
		 pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count =0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		int i=0;
		for (TodoItem myitem : list) {
			i++;
			System.out.println(i + "] " + myitem.getTitle() + " : " + myitem.getDesc()+ " : " + myitem.getCurrent_date() + " : " + myitem.getCategory()+ " : " + myitem.getDue_date());
		}
	}
	
public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
	ArrayList<TodoItem> list = new ArrayList<TodoItem>();
	Statement stmt;
	try {
		stmt = conn.createStatement();
		String sql = "SELECT * FROM list ORDER BY " + orderby;
		if( ordering ==0) sql += " desc;";
		else sql+= ";";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			int id = rs.getInt("id");
			String category = rs.getString("category");
			String title = rs.getString("title");
			int priority = rs.getInt("priority");
			int is_complete = rs.getInt("is_completed");
			String description = rs.getString("memo");
			String place = rs.getString("place");
			String due_date = rs.getString("due_date");
			String current_date = rs.getString("current_date");
			TodoItem t = new TodoItem(id, title, priority, description, place, category, due_date, current_date, is_complete);
			list.add(t);
		}
		stmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return list;
}

public ArrayList<TodoItem> getCompList(){
	ArrayList<TodoItem> list = new ArrayList<TodoItem>();
	Statement stmt;
	try {
		stmt = conn.createStatement();
		String sql = "SELECT * FROM list WHERE is_completed=1;";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			int id = rs.getInt("id");
			String category = rs.getString("category");
			String title = rs.getString("title");
			int priority = rs.getInt("priority");
			int is_complete = rs.getInt("is_completed");
			String description = rs.getString("memo");
			String place = rs.getString("place");
			String due_date = rs.getString("due_date");
			String current_date = rs.getString("current_date");
			TodoItem t = new TodoItem(id, title, priority, description, place, category, due_date, current_date, is_complete);
			list.add(t);
		}
		stmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return list;
}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				int priority = rs.getInt("priority");
				int is_complete = rs.getInt("is_completed");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(id, title, priority, description, place, category, due_date, current_date, is_complete);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public TodoItem indexOf(int num) {
		return list.get(num);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}

	public int size() {
		return list.size();
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (is_completed, title, memo, category, current_date, due_date)"
						+ " values (?,?,?,?,?,?);";
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				int is_completed = Integer.parseInt(st.nextToken());
				String title = st.nextToken();
				String category = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
			
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, is_completed);
				pstmt.setString(2, title);
				pstmt.setString(3, description);
				pstmt.setString(4, category);
				pstmt.setString(5, current_date);
				pstmt.setString(6, due_date);
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<TodoItem>  getWeekList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				int priority = rs.getInt("priority");
				int is_complete = rs.getInt("is_completed");
				String description = rs.getString("memo");
				String place = rs.getString("place");
				String due_date = rs.getString("due_date");
				//날짜세기
				SimpleDateFormat sDate = new SimpleDateFormat("MM.dd");
		        String curtime = sDate.format(new Date());
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(id, title, priority, description, place, category, due_date, current_date, is_complete);
				if(Float.parseFloat(due_date) - Float.parseFloat(curtime) < 0.07) list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int compedItem() {
		PreparedStatement pstmt;
		int count=0;
		try {
			String sql = "SELECT * FROM list WHERE is_completed==1;";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				deleteItem(id);
				count++;
			}
		 pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
