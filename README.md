# Rev Drive

## User Account

**New Account**
- [x] RD-001: The application should allow users to create a new account using basic information, email(all emails are unique), and password. 
- [x] RD-002: The application should disabled all newly created accounts until verified.
- [x] RD-003: The application should send an email with a link to confirm new user account.
- [x] RD-004: Only after verifying a new account should a user be able to log into the application.

**Log In**
- [x] RD-101: The application should allow users to enter an email and password to log in.
- [x] RD-102: If MFA is set up, the application should ask for a QR code after entering correct email and password.
- [x] RD-103: After 6 failed login attempts, user account should be locked for 15 minutes (mitigate brute force attack).
- [x] RD-104: After 90 days, user password should expire therefore can't log in until password is updated (password rotation).

**Reset Password**
- [x] RD-201: The application should allow users to reset their password.
- [x] RD-202: The application should send a link to users' email to reset their password (link to be invalid after being clicked on).
- [x] RD-203: The application should present a screen with a form to reset password when the link is clicked.
- [x] RD-204: If a password is reset successfully, user should be able to log in using the new password.
- [x] RD-205: The application should allow users to reset their password as many times as they need.

**MFA (Multi-Factor Authentication)**
- [x] RD-301: The application should allow users to set up Multi-Factor Authentication to help secure their account.
- [x] RD-302: Multi-Factor Authentication should use a QR code on users' mobile phone.
- [x] RD-303: The application should allow users to scan a QR code using an authenticator application on their phone to set up Multi-Factor Authentication.
- [x] RD-304: The application should ask users to enter the QR code from their mobile phone authenticator application in order to log in successfully.

**Profile**
- [x] RD-401: The application should allow users to update their basic information while logged in.
- [x] RD-402: The application should allow users to update their password while logged in.
- [x] RD-403: The application should allow users to update their account settings while logged in.
- [x] RD-404: The application should allow users to update their profile picture while logged in.

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

### Frontend

- React

### Database

- PostgreSQL

### CI/RD

- Docker
