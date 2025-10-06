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

## Document Management

**Document List**
- [ ] RD-501: The application should show a list of all the documents uploaded in the homepage.
- [ ] RD-502: The application should show some details (name, size, owner, type, etc) about each document in the list.
- [ ] RD-503: The application should allow logged in users to upload new documents.
- [ ] RD-504: The application should have pagination for the document list.
- [ ] RD-505: The application should allow to set how many documents to display per page.
- [ ] RD-506: The application should allow to search documents by name (result should also include pagination).
- [ ] RD-507: The application should allow to click on a document to see more details.

**Document Details**
- [ ] RD-601: The application should show details of a document when clicked on.
- [ ] RD-602: The document details should include document owner.
- [ ] RD-603: The application should allow to update the name and description of a document (in detail page).
- [ ] RD-604: The application should allow to download a document (in detail page).
- [ ] RD-605: The application should allow to delete the document (in detail page).

## Access Control
**User Role**
- [ ] RD-701: The application should give roles to users.
- [ ] RD-702: The application roles should contain specific permissions (authorities).
- [ ] RD-703: The application roles should grant different access levels.
- [ ] RD-704: The application should allow only users with proper roles to be able to perform certain actions.
- [ ] RD-705: The application should only allow non-user role users to update account settings.
- [ ] RD-706: The application should only allow non-user role users to update account roles.
- [ ] RD-707: The application should only allow users with "delete" document permission to delete documents.
- [ ] RD-708: The application should only allow non-user role users to view other users in the system.

## Audit Trail
- [ ] RD-801: The application should keep track of who created an entity (user, document, etc).
- [ ] RD-802: The application should keep track of when an entity (user, document, etc) was created.
- [ ] RD-803: The application should keep track of who updated an entity (user, document, etc).
- [ ] RD-804: The application should keep track of when an entity (user, document, etc) was updated.


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
