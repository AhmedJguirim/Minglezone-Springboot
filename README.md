# Social Media Backend

## Overview

This project serves as the backend for a small social media application, handling user management, posts, and tagging functionality.

## Features

- **User Management**: Register new users, fetch user details, update and delete users.
- **Authentication**: Supports user authentication and JWT token generation.
- **Post Management**: Create, retrieve, update, and delete posts. Supports file uploads and pagination.
- **Tag Management**: Create, retrieve, update, and delete tags associated with posts.

## Technologies Used

- **Spring Boot**: For creating the application.
- **Spring Security**: For security and authentication.
- **MySQL**: Database to store all persistent data.
- **Hibernate**: ORM tool for data management.
- **ModelMapper**: For mapping between models.

## Setup Instructions

1. **Clone the repository**:
   ```
   git clone https://github.com/AhmedJguirim/Minglezone-Springboot.git
   ```
2. **Set up MySQL**:

   - Ensure MySQL is installed and running.
   - Create a database named 'minglezone' or configure the `spring.datasource.url` in `application.properties`.

3. **Configure application.properties**:

   - Update the database username and password as per your MySQL setup.

   ```
   spring.datasource.url=jdbc:mysql://localhost/minglezone?createDatabaseIfNotExist=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=root
   ```

4. **Build the project**:

   ```
   mvn clean install
   ```

5. **Run the application**:
   ```
   mvn spring-boot:run
   ```

## API Endpoints

### Users

- `POST /register` - Register a new user
- `GET /users` - Get all users
- `GET /users/{id}` - Get a single user by ID
- `PUT /users/{id}` - Update a user
- `DELETE /users/{id}` - Delete a user

### Posts

- `GET /posts` - Get all posts
- `GET /posts/myPosts` - Get posts for the currently authenticated user
- `POST /posts` - Create a new post
- `PUT /posts/{id}` - Update a post
- `DELETE /posts/{id}` - Delete a post

### Tags

- `GET /tags` - Get all tags
- `POST /tags` - Create a new tag
- `GET /tags/{id}` - Get a tag by ID
- `PUT /tags/{id}` - Update a tag
- `DELETE /tags/{id}` - Delete a tag

## Contributing

Feel free to fork the project and submit pull requests.

## License

MIT license
