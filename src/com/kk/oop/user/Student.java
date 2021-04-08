package com.kk.oop.user;

import java.util.List;

import com.kk.oop.Course;
import com.kk.oop.department.Department;

public class Student extends User {
	private int rollNumber;
	private List<Course> coursesToLearn;
	private Department department;
	
	public Student(int rollnumber, String name, Department department) {
		this.rollNumber = rollnumber;
		super.setName(name);
		this.department = department;
	}
	
	public int getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public List<Course> getCoursesToLearn() {
		return coursesToLearn;
	}
	public void setCoursesToLearn(List<Course> coursesToLearn) {
		this.coursesToLearn = coursesToLearn;
	}

	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	void subscribeCourse(Course course) {
		if( !coursesToLearn.contains(course) ) {
			this.coursesToLearn.add(course);
			System.out.println(this.getName() + " subscribes to \"" + course.getName() + "\" course.");
		}
		else
			System.out.println(this.getName() + " already subscribes to \"" + course.getName() + "\" course.");
	}
}
