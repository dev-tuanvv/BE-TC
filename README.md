<div align='center'>

<p>The TutorCenter Backend is the server-side component of the TutorCenter project, built using Java Spring Boot and MySQL. The TutorCenter system aims to connect tutors and parents by managing classes efficiently. It facilitates teachers and students in becoming tutors to earn money and provides a platform for parents and students to find suitable tutors to assist with their studies. The system focuses on addressing the needs of tutors and parents promptly, clearly, and accurately. </p>

<h4> <span> · </span> <a href="https://github.com/hoangtiot/BE-TC/blob/master/README.md"> Documentation </a> <span> · </span> <a href="https://github.com/hoangtiot/BE-TC/issues"> Report Bug </a> <span> · </span> <a href="https://github.com/hoangtiot/BE-TC/issues"> Request Feature </a> </h4>


</div>

# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
- [Contact](#handshake-contact)


## :star2: About the Project

### :dart: Features
- User Authentication: Secure login and registration system for tutors, parents, and staff.
- Tutor Management: Tutors can register, update their profiles, and manage their availability.
- Parent-Student Matching: Parents and students can search for tutors based on subject, availability, and other criteria.
- Matching Algorithm: Tutors receive suggestions for tutoring requests from parents based on proximity to their location and subject expertise.
- Payment Integration: Integration with payment gateways for managing tutor payments and parent payments for tutoring sessions.
- Notification System: Real-time notifications for tutors and parents regarding class schedules, payments, and other important updates.
- Reporting and Analytics: Generate reports and analytics to track tutoring sessions, payments, and overall system performance.
- Admin and Manager Dashboard: Web management system for staff to manage users, classes, payments, and system settings.


## :toolbox: Getting Started

### :gear: Installation

Clone the repository:
```bash
git clone https://github.com/dev-tuanvv/BE-TC.git
```
Navigate to the project directory:
```bash
cd BE-TC
```
Install MySQL locally or use a cloud-based MySQL service. Create a new database named capstone_db. Update the MySQL connection settings in the application.properties file.
Build the project image using Dockerfile
```bash
docker build . -t tutorcenter
```
Run the image created in a container
```bash
docker run -p 9000:8080 tutorcenter
```


## :handshake: Contact

hoangtiot - [Linkedin](https://www.linkedin.com/in/hoangdh1262/) - dohuyhoang1120@gmail.com

Project Link: [https://github.com/dev-tuanvv/BE-TC](https://github.com/dev-tuanvv/BE-TC)
