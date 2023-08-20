# UserProfile Management Spring Boot API

This Spring Boot application provides API endpoints for managing user profiles. It allows users to create, update, retrieve, and delete user profiles.

## Getting Started

Follow these instructions to set up and run the Spring Boot API on your local machine.

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven

### Installation

1. Clone the repository to your local machine:
git clone https://github.com/NikiSura/UserProfileManagement.git

css
Copy code

2. Navigate to the project directory:

cd backend

arduino
Copy code

3. Build and run the application using Maven:
mvn spring-boot:run

### API Endpoints

The following API endpoints are available for user profile management:

- **GET /api/getAllProfiles**: Get a list of all user profiles.
- **GET /api/getProfile/{id}**: Get a user profile by ID.
- **POST /api/createProfile**: Create a new user profile.
- **PUT /api/updateProfile/{id}**: Update an existing user profile.
- **DELETE /api/deleteProfile/{id}**: Delete a user profile.

### Usage

1. After running the application, you can use a tool like [Postman](https://www.postman.com/) or [curl](https://curl.se/) to interact with the API endpoints.

2. Send HTTP requests to the specified endpoints using the appropriate methods (GET, POST, PUT, DELETE) to manage user profiles.

### Configuration

Adjust the configuration settings in the `application.properties` file according to your environment.

## Built With

- Spring Boot - Backend framework
- Maven - Dependency management and build tool

## Authors

- Nikila Surapaneni - https://github.com/NikiSura

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

