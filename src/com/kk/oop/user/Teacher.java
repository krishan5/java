package com.kk.oop.user;

import java.util.List;

import com.kk.oop.Course;
import com.kk.oop.department.Department;

public class Teacher extends User {
	private Department department;
	private List<Course> coursesToTeach;
	
	public Teacher(String name, Department department) {
		super.setName(name);
		this.department = department;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public List<Course> getCoursesToTeach() {
		return coursesToTeach;
	}
	public void setCoursesToTeach(List<Course> coursesToTeach) {
		this.coursesToTeach = coursesToTeach;
	}
	
	public void subscribeToCourse(Course course) {
		if( !coursesToTeach.contains(course) ) {
			this.coursesToTeach.add(course);
			System.out.println(this.getName() + " can teach \"" + course.getName() + "\" course.");
		}
		else
			System.out.println(this.getName() + " already teaching \"" + course.getName() + "\" course.");
	}
}
