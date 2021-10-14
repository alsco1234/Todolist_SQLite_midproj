package com.todo.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private String place;
    private int id;
    private int is_completed;
    private int priority;
    
    public TodoItem(int id, String title, int priority, String desc, String place, String category, String due_date,
			String current_date, int is_completed) {
    	this.id = id;
    	this.title=title;
        this.desc=desc;
        this.place=place;
        this.setCategory(category);
        this.current_date = current_date;
        this.due_date = due_date;
        this.is_completed = is_completed;
        this.setPriority(priority);
	}
    
    public TodoItem(int is_completed, String title, String desc, String category, String current_date, String due_date) {
    	this.title=title;
        this.desc=desc;
        this.setCategory(category);
        this.current_date = current_date;
        this.due_date = due_date;
        this.is_completed = is_completed;
	}
    
    public TodoItem(String title, int priority, String desc, String place, String category, String due_date) {
    	this.title=title;
        this.desc=desc;
        this.place=place;
        this.setCategory(category);
        SimpleDateFormat sDate = new SimpleDateFormat("MM.dd");
        this.current_date = sDate.format(new Date());
        this.due_date = due_date;
        this.is_completed = 0;
        this.setPriority(priority);
	}

	public String toString() {
		String is;
		if(is_completed==0) is = " [ ] ";
		else is = " [V] ";
    	return id +is+ title + "("+priority + ")\t" + desc + "\t<" + place + ">\t" + current_date + "\t" + category + "\t" + due_date;
    }

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getId() {
		return Integer.toString(id);
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPriority() {
		return Integer.toString(priority);
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
