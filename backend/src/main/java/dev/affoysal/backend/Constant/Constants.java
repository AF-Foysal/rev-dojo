package dev.affoysal.backend.Constant;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class Constants {
    public static final String ROLE = "role";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITIES = "authorities";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String STUDENT_AUTHORITIES = "course:r,post:r,grade:r";
    public static final String INSTRUCTOR_AUTHORITIES = "course:r,course:u,post:c,post:r,post:u,post:d,grade:r,grade:u";
    public static final String ADMIN_AUTHORITIES = "user:c,user:r,user:u,user:d,course:c,course:r,course:u,course:d,post:c,post:r,post:u,post:d,grade:r,grade:u";

    public static final String BASE_PATH = "/**";
    public static final String LOGIN_PATH = "/user/login";
    public static final String[] PUBLIC_ROUTES = {
            "/user/reset-password/reset", "/user/verify/reset-password",
            "/user/reset-password", "/user/stream", "/user/id",
            "/user/login", "/user/register",
            "/user/new/password", "/user/verify",
            "/user/refresh/token", "/user/reset-password",
            "/user/verify/account", "/user/verify/password", "/user/verify/code" };
    public static final String[] PUBLIC_URLS = { "/user/reset-password/reset/**", "/user/verify/reset-password/**",
            "/user/reset-password/**", "/user/login/**", "/user/verify/account/**",
            "/user/register/**", "/user/new/password/**", "/user/verify/**", "/user/reset-password/**",
            "/user/verify/password/**" };
    public static final String EMPTY_VALUE = "Empty value";
    public static final String REV_DOJO_LLC = "REV DOJO LLC";
    public static final int STRENGTH = 12;
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