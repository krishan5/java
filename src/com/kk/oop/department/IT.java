package com.kk.oop.department;

import java.util.List;

import com.kk.oop.Course;

public class IT extends Department {
	
	public IT(String code, String name, List<Course> courses) {
		super.setCode(code);
		super.setName(name);
		super.setAvailableCourses(courses);
	}
	
}