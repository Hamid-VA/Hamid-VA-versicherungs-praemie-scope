# Service zur Berechnung einer Versicherungsprämie

Eine Versicherung berechnet die Versicherungsprämie auf Basis von:

- Jährliche Kilometerleistung
- Fahrzeugtyp
- Region der Fahrzeugzulassung

Für die regionale Zuordnung wird eine CSV "postcodes.csv" verwendet. Die wichtigsten Felder in der CSV sind:

- REGION1 Bundesland
- REGION3 Land
- REGION4 Stadt/Ort
- POSTLEITZAHL Postleitzahl
- LOCATION Bezirk

Interessenten sollen eine Anwendung zur Berechnung der Versicherungsprämie nutzen. Nutzereingaben und die berechnete Prämie sollen persistiert werden.
Der Antragsteller soll die geschätzte Kilometerleistung, Postleitzahl der Zulassungsstelle und den Fahrzeugtyp eingeben.

Zur Berechnung der Prämie wird folgende Formel verwendet:

    Kilometerleistung-Faktor * Fahrzeugtyp-Faktor * Region-Faktor

Der Faktor für die Kilometerleistung ist wie folgt festgelegt:

- 0 bis 5.000 km: 0.5
- 5.001 km bis 10.000 km: 1.0
- 10.001 km bis 20.000 km: 1.5
- ab 20.000km: 2.0

Der Faktor für die Region kann anhand des Bundeslandes gewählt werden. Der Faktor für die Fahrzeugtyp kann frei definiert werden.

Neben der Anwendung für Antragsteller soll eine Integration von Drittanbietern ermöglicht werden.
Dazu soll eine HTTP-API zur Berechnung der Prämie angeboten werden.

## Deine Aufgabe

Erstelle eine Anwendung mit folgenden Anforderungen:

- Einträge werden in einer Datenbank gespeichert. Welche würdest du nutzen? Begründe deine Entscheidung.
- Erstelle die notwendingen Services, mindestens zwei! Entscheide, wo die Services anhand der fachlichen Domäne und Anforderungen aufgeteilt werden.
- Verwende ein Test-Framework und erläutere dein Konzept zur Wahrung der Softwarequalität.
- Wie erfolgt die Kommunikation zwischen den Services?
- Erstelle sowohl Code als auch Dokumentation.
- (Optional) erstelle eine Web-basierte Oberfläche.

## Antworten zu den Anforderungen

### 1) Datenspeicherung — Welche DB und warum
Für Entwicklung und Tests habe ich H2 verwendet: kein Setup, schnelle Feedback‑Loops, integrierte H2‑Console für Inspektion.  
Für Produktion verwendet man PostgreSQL: ACID‑Konformität, bewährte Performance, gute Migrationsunterstützung (Flyway/Liquibase) und Cloud‑Support.

### 2) Services — Aufteilung (mindestens zwei)
- **RegionService**: lädt `postcodes.csv`, normalisiert PLZ und liefert `REGION1` (Bundesland) für eine PLZ.
- **PremiumCalculator**: reine Berechnungslogik (`mileageFactor`, `vehicleFactor`, `regionFactor`).
- **ApplicationService**: Validierung, Orchestrierung (RegionService + PremiumCalculator) und Persistenz via `ApplicationRepository`.

Diese Aufteilung trennt Domainlogik (Berechnung) von Infrastruktur (CSV/DB) und macht Komponenten gut testbar.

### 3) Test‑Framework & Qualitätssicherung
- Unit Tests: JUnit 5 (+ AssertJ) für `PremiumCalculator`, `RegionService` (CSV‑Parsing).
- Integration Tests: `@SpringBootTest` für Controller/Repository (verwenden H2).

### 4) Kommunikation zwischen Services
Innerhalb der App: synchrone Methodenaufrufe (in‑process)  
Für Drittanbieter: HTTP REST API (JSON). In Zukunft bei Bedarf: asynchrone Entkopplung mittels Messaging (Kafka/RabbitMQ).

### 5) Code & Dokumentation
- Backend: Spring Boot, Controller (`/api/calculate`, `/api/applications`), Services, Repository, DTOs, Tests.
- Frontend (optional): Vue 3 + Vite, einfache Form zur Kalkulation und Persistenz.
- README: Run/Build/Test‑Anleitung, Beispiel‑Requests (curl/PowerShell) und Designentscheidungen.

### 6) Optional: Web‑UI
Frontend mit Vite + Vue 3 (leichtgewichtig). Dev: Vite‑Proxy `/api` → `http://localhost:8080`. Produktion: `npm run build` und die `dist` in `src/main/resources/static` einfügen oder separat hosten.

---
Beispiel‑Run (PowerShell):
```powershell
# Backend
cd backend
.\mvnw.cmd -DskipTests package
java -jar target/versicherungs-praemie-0.0.1-SNAPSHOT.jar

# Frontend (optional)
cd frontend
npm install
npm run dev
```

Hinweis zum Build (Maven Wrapper)

Dieses Repository enthält den Maven Wrapper (`mvnw` / `mvnw.cmd`) im `backend`-Verzeichnis. Das erlaubt Reviewer*innen und Evaluator*innen, das Projekt zu bauen und Tests auszuführen, ohne Maven systemweit zu installieren — nur eine Java JDK muss vorhanden sein.

Beispiele:

Windows (PowerShell):
```powershell
cd backend
.\mvnw.cmd test        # führt die Tests aus
.\mvnw.cmd package     # erzeugt das ausführbare JAR
```

Wenn `JAVA_HOME` nicht gesetzt ist, beläuft sich der Wrapper auf eine vorhandene Java in PATH — stelle sicher, dass JDK 17+ installiert ist.

##

Wir legen Wert auf Einfachheit, Testbarkeit und Wartbarkeit.

## Ablauf/Evaluation

Wir setzen keine zeitlichen Einschränkungen. Die Umsetzung soll vielmehr als Gesprächsgrundlage dienen und du musst in der Lage sein, deine Entscheidungen zu erläutern.
Es gibt kein Richtig oder Falsch.

Die Anwendung muss bei uns lokal lauffähig sein.

Abgabe ist entweder per .zip-Datei oder per GitHub-Link möglich.

## Viel Erfolg!
