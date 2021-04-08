package com.kk.oop.department;

import java.util.List;

import com.kk.oop.Course;

public class Department {
	private String code;
	private String name;
	private List<Course> availableCourses;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Course> getAvailableCourses() {
		return availableCourses;
	}
	public void setAvailableCourses(List<Course> courses) {
		this.availableCourses = courses;
	}
	public void addAvailableCourses(Course course) {
		this.availableCourses.add(course);
	}
}
