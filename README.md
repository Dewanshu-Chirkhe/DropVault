<div align="center">

# DropVault

### Secure file sharing made simple.

A secure file-sharing application that allows users to upload files, generate unique download codes, and share files with others. Built using Spring Boot, Java, PostgreSQL, and AWS S3, with Google OAuth 2.0 authentication and automatic file expiration.

<p>
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-Backend-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/AWS_S3-Storage-569A31?style=for-the-badge&logo=amazons3&logoColor=white" />
  <img src="https://img.shields.io/badge/Google_OAuth-Authentication-4285F4?style=for-the-badge&logo=google&logoColor=white" />
</p>

</div>

---

## About the Project

DropVault is a temporary file-sharing backend built using Spring Boot. It allows guests and authenticated users to upload files and share them through unique download codes.

The application manages file metadata, storage limits, download tracking, and expiration policies. A scheduled cleanup service automatically removes expired files and their associated metadata.

## Features

- **File Upload:** Upload files with configurable size limits.
- **Download Codes:** Generate unique codes for sharing files.
- **Google OAuth 2.0:** Authenticate users securely.
- **Cloud Storage:** Store files using AWS S3.
- **File Management:** Maintain file metadata using PostgreSQL.
- **Expiration Policies:** Automatically expire shared files.
- **Scheduled Cleanup:** Remove expired files and database records.
- **Download Tracking:** Track the number of file downloads.
- **Storage Limits:** Apply different restrictions for guests and authenticated users.
- **Health Monitoring:** Provide a health-check endpoint.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Backend and REST APIs |
| Spring Security | Application security |
| Google OAuth 2.0 | User authentication |
| Spring Data JPA | Database operations |
| PostgreSQL | Metadata storage |
| AWS S3 | File storage |
| AWS SDK | S3 integration |
| Maven | Dependency management |
| Spring Actuator | Health monitoring |

## How It Works

1. **Upload:** The client uploads a file through the backend API.
2. **Validation:** The backend validates the file and checks upload restrictions.
3. **Storage:** The file is stored in AWS S3, while its metadata is saved in PostgreSQL.
4. **Code Generation:** A unique download code is generated for sharing.
5. **Download:** Recipients use the download code to retrieve the file.
6. **Expiration:** Files become unavailable after their expiration time.
7. **Cleanup:** A scheduled service removes expired files and associated metadata.

## Architecture

```text
        Client Application
                |
                v
        Spring Boot Backend
                |
        +-------+-------+
        |               |
        v               v
  Google OAuth      File APIs
        |               |
        |          File Validation
        |               |
        |          +----+----+
        |          |         |
        |          v         v
        |       AWS S3   PostgreSQL
        |       Files     Metadata
        |
        v
  Authenticated Users

   Scheduled Cleanup
          |
          v
   Remove Expired Files
```

## Getting Started

### Prerequisites

- Java 21
- PostgreSQL
- Maven
- AWS S3 bucket and credentials, if using S3 storage
- Google OAuth 2.0 credentials

### 1. Clone the Repository

```bash
git clone https://github.com/Dewanshu-Chirkhe/DropVault.git
cd DropVault
```

### 2. Configure Environment Variables

Configure the database, authentication, and storage credentials required by your application.

```env
DB_URL=jdbc:postgresql://localhost:5432/dropvault
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password

GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret

AWS_ACCESS_KEY_ID=your_aws_access_key
AWS_SECRET_ACCESS_KEY=your_aws_secret_key
AWS_REGION=your_aws_region
AWS_S3_BUCKET=your_s3_bucket_name
```

Use the exact environment variable names defined in your application configuration. Never commit credentials to GitHub.

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

Alternatively:

```bash
mvn spring-boot:run
```

### 4. Verify the Health Endpoint

```bash
curl http://localhost:8080/api/v1/health
```

The application will start on the configured server port.

## What I Learned

- Building RESTful backend applications using Spring Boot.
- Implementing file uploads and downloads.
- Integrating Google OAuth 2.0 authentication.
- Managing relational data using PostgreSQL and Spring Data JPA.
- Integrating AWS S3 for cloud file storage.
- Implementing file expiration and scheduled cleanup.
- Managing storage restrictions and download tracking.
- Deploying and monitoring a backend application.

---

<div align="center">

**DropVault** — Secure file sharing with controlled access and automatic expiration.

Built with Java, Spring Boot, PostgreSQL, and AWS S3.

</div>
