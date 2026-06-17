📚 Cloud-Native Personal Library API

📖 Overview

The Personal Library API is a robust, cloud-connected backend application built to manage a digital book collection. Instead of relying on local storage and temporary memory, this project demonstrates enterprise-level architecture by integrating Spring Boot 3 with Amazon Web Services (AWS) to handle both structured relational data and heavy unstructured file uploads.

🏗️ Architecture & Cloud Infrastructure

This application implements a decoupled storage pattern standard in modern microservices:

API Layer (Spring Boot): RESTful endpoints handling incoming multipart/form-data and JSON requests.

Structured Data (Amazon RDS - PostgreSQL): Manages relational metadata (Title, Author, Page Count, and S3 URLs). Migrated from an in-memory H2 database to ensure persistent, reliable data storage.

Object Storage (Amazon S3): Handles heavy binary files. The application utilizes the AWS SDK v2 (spring-cloud-aws-starter-s3) to stream PDF documents and cover images directly to an S3 bucket, preventing local server bloat.

✨ Key Features

Multipart File Handling: Safely accepts and parses HTTP requests containing both text metadata and multiple file attachments simultaneously.

Cloud Object Storage: Automates the uploading of binary files to Amazon S3, returning the secure S3 URL to be saved in the database.

Seamless Data Persistence: Utilizes Spring Data JPA and Hibernate to abstract database interactions, allowing seamless switching between local H2 testing environments and production AWS RDS instances.

Secure Configuration: Employs environmental properties to keep AWS Access Keys and database credentials secure and out of the source code.

🚀 API Endpoints

1. Upload a New Book & PDF

URL: /api/books/upload

Method: POST

Content-Type: multipart/form-data

Payload:

title (Text)

author (Text)

totalPages (Number)

coverImage (File - .jpg/.png) (Optional)

pdfFile (File - .pdf)

Response: 200 OK (Returns the saved Book entity with live S3 URLs).

2. Fetch All Books

URL: /api/books

Method: GET

Response: 200 OK (Returns a JSON array of all books stored in the RDS database).

🛠️ Local Setup & Installation

To run this project locally, you will need Java 17+, Maven, and an active AWS Account.

Clone the repository:

git clone [https://github.com/yourusername/personal-library-api.git](https://github.com/yourusername/personal-library-api.git)
cd personal-library-api



Configure AWS & Database Credentials:
Open src/main/resources/application.properties and replace the placeholder values with your actual AWS keys and RDS endpoint:

# AWS S3 Configuration
spring.cloud.aws.credentials.access-key=YOUR_ACCESS_KEY
spring.cloud.aws.credentials.secret-key=YOUR_SECRET_KEY
spring.cloud.aws.region.static=YOUR_REGION
aws.s3.bucket.name=YOUR_BUCKET_NAME

# AWS RDS PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://YOUR_RDS_ENDPOINT:5432/librarydb
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD



Run the Application:

mvn spring-boot:run



🗺️ Future Roadmap

$$Planned$$

 Dockerization: Wrap the application in a Docker container for standardized deployments.

$$Planned$$

 CI/CD & Deployment: Deploy the containerized application to AWS App Runner or AWS ECS (Fargate).

$$Planned$$

 Asynchronous Processing: Implement Amazon SQS to offload file uploads and metadata generation to a background worker queue.
 
