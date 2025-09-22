# Class Dojo

## Stories

### :zero: Authentication & Account Management

- [ ] CD-001: As a **User**, I can **verify** my email and **create** my password.
- [ ] CD-002: As a **User**, I can **login** and get a **JWT** token.
- [ ] CD-003: As a **User**, I can **reset my password**, if I forget it.
- [ ] CD-004: As a **System**, I can **verify** emails before allowing login.

### :one: Student

- [ ] CD-101: As a **Student**, I can **view the classes I'm enrolled in**.
- [ ] CD-102: As a **Student**, I can **view my grades** in each class.

### :two: Instructor

- [ ] CD-201: As an **Instructor**, I can **edit classes info**.
- [ ] CD-202: As an **Instructor**, I can **view all enrolled Students** in my classes.
- [ ] CD-203: As an **Instructor**, I can **post announcements and materials** on a class board.
- [ ] CD-204: As an **Instructor**, I can **record and update grades** for each student in my classes.

### :three: Admin

- [ ] CD-301: As an **Admin**, I can **create and verify Student and Instructor** accounts.
- [ ] CD-302: As an **Admin**, I can *assign SINGLE roles (STUDENT, INSTRUCTOR)** to any user.
- [ ] CD-303: As an **Admin**, I can **manage all classes and students**.

### :four: System
- [ ] CD-401: As a **System**, I enforce **RBAC** so each role only sees its permitted pages. 

## ERD

![ERD](ERD.png)

```
user [icon: user, color: blue] {
  id string pk
  role_id string fk
  first_name string
  last_name string
  email string @unique
  password string
  verified boolean 
  is_active boolean
  created_at timestamp @default(now)
  updated_at timestamp @default(now)
  }

  role [icon: key, color: red]{
    id string pk
    role_name string
    created_at timestamp @default(now)
    updated_at timestamp @default(now)
  
  }

  course [icon: book-open, color: green]{
    id string pk
    course_name string
    description string
    instructor_id string fk
    created_at timestamp @default(now)
    updated_at timestamp @default(now)
  }

  enrollment [icon: link, color: orange]{
    id string pk
    user_id string fk
    course_id string fk
    status string
    created_at timestamp @default(now)
    updated_at timestamp @default(now)
  }  

  grade [icon: check-circle, color: purple ]{
    id string pk
    user_id string fk
    course_id string fk
    score number
    created_at timestamp @default(now)
    updated_at timestamp @default(now)
  }

  post [icon: inbox, color: yellow] {
  id string pk
  course_id string fk
  instructor_id string fk
  title string
  content text
  type string
  created_at timestamp @default(now)
  updated_at timestamp @default(now)
}


  role.id < user.role_id

  user.id < course.instructor_id

  user.id < enrollment.user_id
  course.id < enrollment.course_id

  user.id < grade.user_id
  course.id < grade.course_id

  user.id < post.instructor_id
  course.id < post.course_id
```

## Tech Stack

### Backend

- Spring Boot
- Spring WEB
- Spring Data JPA
- Spring Security + JWT
- Spring Mail
- PostgreSQL
- Lombok
- Validation
- MapStruct
- SpringDoc OpenAPI

### Frontend

- React

### CI/CD

- Docker