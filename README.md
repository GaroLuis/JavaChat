# JavaChat

Real-time chat application with a Spring Boot backend and React frontend.

<img width="1016" height="594" alt="image" src="https://github.com/user-attachments/assets/13dd9fef-f370-4d17-9004-2abb13a8b650" />


## Prerequisites

- Docker and Docker Compose

## Getting Started

Start the containers:

```bash
docker compose up -d
```

This builds the app container (Java 26 + Node 22) and starts a PostgreSQL database. The source code is mounted at `/var/www/javachat` so changes are reflected live.

Access the services:

- **Frontend:** http://localhost:5173
- **Backend:** http://localhost:8080
- **Database:** localhost:5432

## Development

Open a shell inside the app container:

```bash
make bash
```

### Frontend

```bash
cd apps/frontend
pnpm install
pnpm dev
```

### Backend

```bash
cd apps/backend
./gradlew bootRun
```

## Testing

### Frontend

```bash
cd apps/frontend
pnpm test          # single run
pnpm test:watch    # watch mode
```

### Backend

```bash
cd apps/backend
./gradlew test
```

## Useful Commands

| Command | Description |
|---|---|
| `docker compose up -d` | Start containers in the background |
| `docker compose down` | Stop and remove containers |
| `make bash` | Open a shell in the app container |
| `docker compose logs -f` | Tail container logs |
