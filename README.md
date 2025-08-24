# Spring Boot JDK 21 Blog Application

A modern blog application built with Spring Boot 3.5.5 and Java 21, featuring user authentication, post management, and
file uploads.

## Features

- **User Authentication & Authorization**: Secure login/signup with Spring Security
- **Blog Post Management**: Create, read, update, and delete blog posts
- **File Upload Support**: Upload and manage files associated with posts
- **HTMX Integration**: Dynamic web interactions without page reloads
- **Responsive UI**: Thymeleaf templates with modern styling
- **MySQL Database**: Persistent data storage
- **Docker Support**: Complete containerization with Docker Compose

## Technology Stack

- **Java 21**: Latest LTS version with modern language features
- **Spring Boot 3.5.5**: Latest Spring Boot framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Data persistence layer
- **Thymeleaf**: Server-side templating engine
- **HTMX**: Modern web interactions
- **MySQL 9.4**: Database
- **Docker & Docker Compose**: Containerization
- **Lombok**: Reduce boilerplate code
- **Maven**: Build and dependency management

## Project Structure

```
src/
├── main/
│   ├── java/id/my/hendisantika/springbootjdk21/
│   │   ├── SpringBootJdk21Application.java    # Main application class
│   │   ├── controller/                        # REST and web controllers
│   │   │   ├── AuthController.java           # Authentication endpoints
│   │   │   ├── FileController.java           # File upload/download
│   │   │   ├── PostController.java           # Post API endpoints
│   │   │   └── PostHtmxController.java       # HTMX-enabled post management
│   │   ├── dto/                              # Data Transfer Objects
│   │   │   ├── SignInDTO.java
│   │   │   └── SignUpDTO.java
│   │   ├── entity/                           # JPA entities
│   │   │   ├── Post.java
│   │   │   └── User.java
│   │   ├── repository/                       # Data access layer
│   │   │   ├── PostRepository.java
│   │   │   └── UserRepository.java
│   │   ├── security/                         # Security configuration
│   │   │   ├── RoleEnum.java
│   │   │   └── SecurityConfig.java
│   │   └── service/                          # Business logic layer
│   │       ├── AuthService.java
│   │       ├── FileStorageService.java
│   │       └── PostService.java
│   └── resources/
│       ├── application.properties            # Application configuration
│       └── templates/                        # Thymeleaf templates
│           ├── blog/
│           │   ├── all-posts.html
│           │   └── edit-post-form.html
│           ├── index.html
│           └── layout/
│               └── header.html
└── test/                                     # Test files
```

## Getting Started

### Prerequisites

- Java 21 or later
- Maven 3.6+
- Docker and Docker Compose (for containerized deployment)

### Running with Docker Compose (Recommended)

1. Clone the repository:

```bash
git clone <repository-url>
cd spring-boot-jdk21
```

2. Start the application with Docker Compose:

```bash
docker-compose up -d
```

3. Access the application:
    - Application: http://localhost:8090
    - MySQL: localhost:3316 (root/password)

### Running Locally

1. Start MySQL database:

```bash
docker run -d --name mysql-blog \
  -e MYSQL_DATABASE=blog \
  -e MYSQL_ROOT_PASSWORD=password \
  -p 3306:3306 \
  mysql:9.4.0
```

2. Update `application.properties` for local MySQL connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=root
spring.datasource.password=password
```

3. Run the application:

```bash
./mvnw spring-boot:run
```

4. Access the application at http://localhost:8080

## Configuration

### Database Configuration

The application uses MySQL as the primary database. Configuration can be found in:

- `application.properties` - Local development settings
- `compose.yaml` - Docker environment variables

### Security Configuration

Spring Security is configured in `SecurityConfig.java` with:

- Form-based authentication
- Role-based authorization
- Password encryption with BCrypt

### File Storage

File uploads are handled by `FileStorageService.java` with configurable storage location.

## API Endpoints

### Authentication

- `POST /auth/signup` - User registration
- `POST /auth/signin` - User login
- `POST /auth/logout` - User logout

### Posts Management

- `GET /posts` - List all posts
- `POST /posts` - Create new post
- `PUT /posts/{id}` - Update post
- `DELETE /posts/{id}` - Delete post

### HTMX Endpoints

- `GET /htmx/posts` - Dynamic post listing
- `POST /htmx/posts` - Create post with HTMX
- `PUT /htmx/posts/{id}` - Update post with HTMX

### File Management

- `POST /files/upload` - Upload file
- `GET /files/{filename}` - Download file

## Development

### Building the Application

```bash
./mvnw clean compile
```

### Running Tests

```bash
./mvnw test
```

### Creating Docker Image

```bash
./mvnw spring-boot:build-image
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## Check out the videos on YouTube:

1. [CRUD + MySQL + Docker setup](https://www.youtube.com/watch?v=cFZMSrP4Yd0)
2. [RBAC - Role Bases Access Control - Authentication](https://youtu.be/eSYP_HKm97s?si=elGIiU0Z9MuMDldG)
3. [HTMX + Thymeleaf](https://youtu.be/4IY_KhBKvgU)
4. [File Upload - Image and PDF](https://youtu.be/tOV0Ulnsb5Y)

## License

This project is open source and available under the [MIT License](LICENSE).

## Contact

For questions or support, please contact [hendisantika@yahoo.co.id](mailto:hendisantika@yahoo.co.id).