# Event Registration API

A Java 17 / Spring Boot REST API for creating and looking up event registrations. Registrations are held in memory and are cleared when the application stops.

## Requirements

- JDK 17 or newer
- Apache Maven 3.9 or newer

## Build and test

From this folder, run:

```powershell
mvn clean test
mvn package
```

The executable JAR is `target/event-registration-api.jar`.

## Run

```powershell
mvn spring-boot:run
```

The API listens on `http://localhost:8080`.

## API

Create a registration with `POST /api/registrations`:

```powershell
curl.exe -i -X POST http://localhost:8080/api/registrations -H "Content-Type: application/json" -d '{"name":"Ada Lovelace","email":"ada@example.com","eventName":"Tech Summit","eventDate":"2099-06-15"}'
```

A valid request returns `201 Created` and a registration JSON object containing its generated `id`. Only dates after today and valid email addresses are accepted. Validation failures return `400 Bad Request` with a `fieldErrors` object.

List registrations with `GET /api/registrations`, or retrieve one with `GET /api/registrations/{id}` using the returned ID.

## Jenkins

The `Jenkinsfile` runs Checkout, Build, Test, Package, and Archive Artifacts stages. Configure a Jenkins agent with JDK 17+ and Maven 3.9+; the pipeline supports both Windows and Unix agents. The packaged JAR is archived from `target/*.jar`.

## Push to GitHub

Create an empty GitHub repository, then run these commands from this folder (replace the URL with your repository URL):

```powershell
git init
git add .
git commit -m "Create event registration API"
git branch -M main
git remote add origin https://github.com/YOUR-USER/YOUR-REPOSITORY.git
git push -u origin main
```