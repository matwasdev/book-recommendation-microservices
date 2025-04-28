# Book Recommendation Microservices 📚


This project is a distributed system built with a microservices architecture to manage user authentication, books, authors, reviews, and generate book recommendations based on user feedback.
Each service operates independently, ensuring scalability, modularity, and fault isolation. 
Communication between services is handled both synchronously and asynchronously where appropriate, with security ensured through JWT-based authentication.

## 🏗️ Project Structure 

* **API Gateway:** The primary entry point for clients, responsible for routing requests to the appropriate microservices and ensuring proper JWT authentication.


* **Auth Service:** Stores information about users and their roles. It is responsible for user registration, login, and managing user-related CRUD operations.


* **Book Service:** Handles books and author data, providing CRUD operations accessible by users.


* **Review Service:** Stores user reviews for specific books. 
Users can submit reviews only for books that are stored in the Book Service database. 
Each review is sent to the Kafka topic **"recommendations-topic"**, where it can be retrieved by the Recommendation Service.


* **Recommendation Service**: Acts as a Kafka consumer, processing all reviews from the **"recommendations-topic"**. 
If a review has a rating of 4 or 5, it is automatically saved to the service database and can later be retrieved by users requesting book recommendations.



* **Eureka Server:** Discovery server that enables microservices to register and locate each other.

## 🛠️ Technologies used 
* **Spring Boot**: Utilized for building and deploying the microservices.


* **PostgreSQL**: Multiple relational databases used to store data for specific microservices, each running in its own PostgreSQL Docker container.


* **Docker** & **Docker Compose**: Docker is used for containerizing the application, including PostgreSQL databases,
Kafka instance, and all microservices. Docker Compose is utilized to manage the containers.


* **JWT (JSON Web Token)**: Used for secure authentication and authorization across microservices.


* **Kafka**: Dockerized Kafka instance used for asynchronous communication between services with a simple producer-consumer model.



## ⚙️ Setup 

1. Clone this repository: `git clone https://github.com/matwasdev/book-recommendation-microservices.git` 
2. Make sure you are in the project directory.
3. Add .env file into your project directory with variables: USERNAME, PASSWORD, JWT_SECRET
4. Build the Docker containers using the provided `docker-compose.yml` with the 
   `docker-compose up --build` command.
5. Add the following roles to the auth-service database:

    `INSERT INTO roles (id, description, name) VALUES (1, 'user role', 'USER');`   
    `INSERT INTO roles (id, description, name) VALUES (2, 'admin role', 'ADMIN');`

6. Access the API Gateway at `http://localhost:8765` and make requests with proper endpoints.

## 📑 Endpoints Overview
* **Auth Service:**
 
  * `POST /auth/register`: Registers a new user.

  * `POST /auth/login`: Logs in an existing user and returns a JWT token.

  * `GET /api/users/{username}`: Retrieves user by username.

  * `GET /api/users/get-all`: Returns a list of all users.

  * `PUT /api/users/{id}`: Updates the user with the given ID (requires ownership rights).

  * `DELETE /api/users/{id}`: Deletes the user with the given ID (requires ownership rights).


* **Book Service:**

  * `POST /api/books/create`: Creates a new book.

  * `GET /api/books/get-all`: Retrieves all books.

  * `GET /api/books/{id}`: Retrieves a book by its ID.

  * `POST /api/authors/create`: Creates a new author.

  * `GET /api/authors/get-all`: Retrieves all authors.

  * `GET /api/authors/{id}`: Retrieves an author by their ID.


* **Review Service:**

  * `POST /api/reviews/create`: Submits a review for a book (only for books that exist in Book Service).

  * `GET /api/reviews/get-all`: Retrieves all reviews.

  * `GET /api/reviews/{id}`: Retrieves a review by its ID.

  * `DELETE /api/reviews/{id}`: Deletes a review by its ID (requires ownership of the review).


* **Recommendation Service:**

  * `GET /api/recommendations/{id}`: Retrieves a specific recommendation by its ID.

  * `GET /api/recommendations/latest`: Retrieves the latest recommendation based on the newest qualifying review.

  * `GET /api/recommendations/get-all`: Retrieves all stored book recommendations.


## 🔐 Security Considerations

- All protected endpoints require a valid **JWT** token.
- The token must be included in the `Authorization` header using the **Bearer** schema:

  `Authorization: Bearer <token>`

- Token verification is handled at the API Gateway level.










