# Rev Dojo

## User Account

### New Account

- [ ] RD-001: Allow users to **create** new account using email and password.
- [ ] RD-002: Disable all created accounts until **verified**.
- [ ] RD-003: Send an email with a link to confirm new user account.
- [ ] RD-004: Allow login for an account **only** after **verification**.

### Reset Password

- [ ] RD-101: Allow users to **reset** their password.
- [ ] RD-102: Send a link to users' emails to reset password.
- [ ] RD-103: Present screen with form to reset password when link is clicked.
- [ ] RD-104: On password reset success, user should be able to login using new password.

### Log in

- [ ] RD-201: Allow users to enter email and password to **log in**.
- [ ] RD-202: User locked for 30 minutes after **3 failed attempts**.

### Profile

- [ ] RD-301: Allow users to update basic information while logged in.
- [ ] RD-302: Allow users to update password while logged in.

## Homepage

### Course List

- [ ] RD-401: Show list of all classes on the homepage.
- [ ] RD-402: Show snippet of details for each class.
- [ ] RD-403: Allow to click on a class to view class page.

### Course Details

- [ ] RD-501: Show all posts, materials, class list, and details when clicked on.
- [ ] RD-502: Materials should be downloadable.

## Access Control

### Admin Role

- [ ] RD-601: **Assign** roles to verified users. Cannot assign **ADMIN**.
- [ ] RD-602: **Create** classes and **enroll** Students.
- [ ] RD-603: **Assign** Instructors to courses.

### Instructor Role

- [ ] RD-701: **Edit** assigned class info.
- [ ] RD-702: **View** all enrolled Students in classes.
- [ ] RD-703: **Post** announcements and materials on class board.
- [ ] RD-704: **Record** and **update** grades for each student in classes.

### Student Role

- [ ] RD-801: **View** enrolled classes.
- [ ] RD-802: **View** grade for each class.

## Audit Trail

- [ ] RD-901: **Track** any user that creates/updates an entity.
- [ ] RD-902: **Track** when an entity is created/updated.

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

### Frontend

- React

### Database

- PostgreSQL

### CI/RD

- Docker
