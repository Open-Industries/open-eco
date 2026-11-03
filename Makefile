# Open Eco — Reference Environmental Management Core Platform — developer entry points
.PHONY: build test verify dev-pilot compose-up compose-down

build:
	mvn -q package -DskipTests

test:
	mvn test

verify:           ## Full verification (life-cycle gate G3)
	mvn verify

dev-pilot:
	mvn -pl services/emission-source-service quarkus:dev

compose-up:
	docker compose up -d

compose-down:
	docker compose down
