# SmartRecruit

SmartRecruit is a full-stack recruitment management platform built with React and Spring Boot. It supports role-based workflows for candidates, HR users, and admins across job posting, job applications, interview scheduling, and hiring analytics.

Developed by Saurabh Kumar Singh.

## Features

- JWT-based authentication with `ADMIN`, `HR`, and `CANDIDATE` roles
- Candidate registration, login, and profile management
- Job creation, listing, search, and closing for HR/Admin users
- Candidate job browsing and one-click job application flow
- Application pipeline tracking with status updates
- Interview scheduling, status management, and feedback tracking
- Recruitment dashboard with jobs, applications, candidate, and interview summary metrics

## Tech Stack

- Frontend: React, Vite, React Router, Axios
- Backend: Spring Boot, Spring Security, Spring Data JPA, Hibernate Validator
- Database: PostgreSQL
- Auth: JWT
- Java Version: 21

## Project Structure

```text
smartrecruit-backend/   Spring Boot REST API
smartrecruit-frontend/  React + Vite client
```

## Local Setup

### Prerequisites

- Node.js 20+ and npm
- Java 21
- PostgreSQL 14+ (or compatible)

### 1. Create the database

Create a PostgreSQL database named `smartrecruit`.

### 2. Configure backend environment variables

The backend now supports environment-variable based configuration for local development.

PowerShell example:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/smartrecruit"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_postgres_password"
$env:JWT_SECRET="replace-with-a-long-random-secret"
$env:JWT_EXPIRATION_MS="3600000"
$env:APP_BOOTSTRAP_ADMIN_EMAIL="admin@smartrecruit.local"
$env:APP_BOOTSTRAP_ADMIN_PASSWORD="change-this-admin-password"
$env:APP_BOOTSTRAP_HR_EMAIL="hr@smartrecruit.local"
$env:APP_BOOTSTRAP_HR_PASSWORD="change-this-hr-password"
```

### 3. Run the backend

```powershell
cd smartrecruit-backend
.\mvnw.cmd spring-boot:run
```

The backend starts on `http://localhost:8080`.

### 4. Run the frontend

```powershell
cd smartrecruit-frontend
npm install
npm run dev
```

The frontend starts on `http://localhost:5173`.

## Deployment

### Render

The backend is ready to deploy on Render with the root-level [render.yaml](/C:/Users/singh/Documents/Codex/2026-05-22/can-you-access-this-repo-saurabh/render.yaml) Blueprint.

Recommended flow:

1. In Render, create a new Blueprint from this repository.
2. Let Render provision:
   `smartrecruit-backend` web service
   `smartrecruit-db` PostgreSQL database
3. After the backend deploy finishes, copy the Render backend URL, which will look like:
   `https://smartrecruit-backend.onrender.com`

Backend details:

- Root directory: `smartrecruit-backend`
- Build command: `chmod +x ./mvnw && ./mvnw clean package -DskipTests`
- Start command: `java -jar target/smartrecruit-0.0.1-SNAPSHOT.jar`
- Health check path: `/api/health`

### Vercel

The frontend is ready to deploy on Vercel from the [smartrecruit-frontend](/C:/Users/singh/Documents/Codex/2026-05-22/can-you-access-this-repo-saurabh/smartrecruit-frontend) directory.

Recommended flow:

1. Import this same GitHub repository into Vercel.
2. Set the Root Directory to `smartrecruit-frontend`.
3. Add the environment variable:
   `VITE_API_BASE_URL=https://<your-render-backend>.onrender.com/api`
4. Deploy.

The SPA route rewrite for Vercel is already configured in [smartrecruit-frontend/vercel.json](/C:/Users/singh/Documents/Codex/2026-05-22/can-you-access-this-repo-saurabh/smartrecruit-frontend/vercel.json).

## Main Modules

- Auth: user registration and login
- Dashboard: recruitment summary cards and pipeline metrics
- Jobs: create, browse, search, apply, and close jobs
- Candidate Profile: manage education, experience, skills, and portfolio links
- Applications: track application status across hiring stages
- Interviews: schedule and manage interviews with feedback and status updates

## API Overview

- `/api/auth` for registration and login
- `/api/jobs` for job management
- `/api/candidates` for candidate profile management
- `/api/applications` for job applications and status changes
- `/api/interviews` for interview scheduling and status tracking
- `/api/dashboard` for summary metrics

## Notes

- CORS is configured through `CORS_ALLOWED_ORIGIN_PATTERNS` and defaults to `http://localhost:5173`.
- Hibernate is set to `ddl-auto=update` for developer convenience.
- Public self-registration creates `CANDIDATE` accounts only.
- `HR` and `ADMIN` accounts should be provisioned separately for trusted users.
- Optional bootstrap env vars can create one `ADMIN` and one `HR` account automatically on backend startup if those emails do not already exist.
