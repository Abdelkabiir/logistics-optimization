# Logistics Route Optimization System

Route optimization application built with Spring Boot and Angular.

## Prerequisites

- Java 17+
- Node.js 18+
- Maven 3.8+
- Angular CLI 17+

## Running Locally

### Backend
```bash
cd backend
mvn spring-boot:run
```
Backend runs at http://localhost:8080

### Frontend
```bash
cd frontend
npm install
ng serve
```
Frontend runs at http://localhost:4200

## API Routes

- POST `/api/logistics/optimize` - Optimize delivery routes
- GET `/api/logistics/route/{id}` - Get route details
- POST `/api/logistics/routes/simulate` - Simulate route scenarios

## Environment Config

Backend: `application.properties`
```properties
server.port=8080
```

Frontend: `environment.ts`
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

## Production Build

Backend:
```bash
mvn clean package
```

Frontend:
```bash
ng build --configuration production
```

## Tech Stack

- Backend: Spring Boot 3.2, Java 17
- Frontend: Angular 17, TypeScript 5.2
- UI: Tailwind CSS, Leaflet maps
```