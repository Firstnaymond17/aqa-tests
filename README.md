# AQA Tests — автотесты для internal-приложения

Автотесты для Java-приложения из тестового задания. Покрывают единственный
эндпоинт `POST /endpoint` с тремя действиями: `LOGIN`, `ACTION`, `LOGOUT`.

## Стек
Java 17, JUnit 5, RestAssured, WireMock, Allure, Maven.

## Как запустить

1. Скачать и распаковать `aqa.7z`, паролем из документации ТЗ
2. Запустить приложение:

java -jar -Dsecret=qazWSXedc -Dmock=http://localhost:8888/ internal-0.0.1-SNAPSHOT.jar

3. В папке проекта прогнать тесты:

mvn clean test

4. Сгенерировать и открыть Allure-отчёт:

allure serve allure-results

## Что покрыто

- **LOGIN** — успешный вход, ошибки внешнего сервиса (500/401), повторный логин (409)
- **ACTION** — успешное выполнение после LOGIN, отказ без LOGIN (403), ошибка внешнего сервиса, многократный вызов
- **LOGOUT** — успешный выход, невозможность ACTION после LOGOUT
- **Негативные сценарии** — отсутствие/неверный API-ключ (401), невалидный формат токена (400)

Итого: 14 тестов, все зелёные.

## Allure-отчёт

![Allure Report](docs/allure report.png)