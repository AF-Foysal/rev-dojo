package dev.affoysal.backend.Constant;

public class Constants {
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String STUDENT_AUTHORITIES = "course:r,post:r,grades:r";
    public static final String INSTRUCTOR_AUTHORITIES = "course:r, course:u,post:c,post:r,post:u,post:d,grades:r,grades:u";
    public static final String ADMIN_AUTHORITIES = "user:c,user:r,user:u,user:d,course:c,course:r,course:u,course:d,post:c,post:r,post:u,post:d,grades:r,grades:u";
}

/*
 * Authority Categories:
 * 1. User
 * 2. Course
 * 3. Post
 * 4. Grades
 * 
 * Student:
 * Course: Read
 * Post: Read
 * Grades: Read
 * 
 * Instructor:
 * Course: Read, Update
 * Post: Create, Read, Update, Delete
 * Grades: Create, Read, Update
 * 
 * Admin:
 * User: Create, Read, Update, Delete
 * Course: Create, Read, Update, Delete
 * Post: Create, Read, Update, Delete
 * Grades: Read, Update
 * 
 * 
 * 
 */