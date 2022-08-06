# Описание
Сервис может отправлять данные о счетах и производить операции пополнения и списания 
## Технологии
* Java 11
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway
* TestNG

## Запуск приложения
ШАГ #1

Установите переменные окружения или же оставьте значения по умолчанию
  ```
    STATISTIC_INTERVAL=PT30S (Период, за который будут оновляться данные о запросах. Формат ISO 8601)
    DATASOURCE_HOST=localhost
    DATASOURCE_PORT=5432
    DATASOURCE_DATABASE=account-service
    DATASOURCE_USERNAME=postgres
    DATASOURCE_USERNAME=root
  ```
ШАГ #2

Проверьте, существует ли база данных с именем, хранимым переменной окружения `DATASOURCE_DATABASE`

Необходимо создать, если такой базы данных не существует

  ```
    CREATE DATABASE account-service
  ```

ШАГ #3

  ```
    mvn  -f ./account-server package -DskipTests
  ```
ШАГ #4

  ```
    java -jar ./account-server/target/account-server-0.0.1-SNAPSHOT.jar
  ```
## Запуск приложения с использованием Docker
ШАГ #1

  ```
    docker run --name db -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=account-service postgres
  ```
ШАГ #2

  ```
    cd account-server
  ```
ШАГ #3

  ```
    docker build -t lavalentine/account-server .
  ```
ШАГ #4

  ```
    docker run --name account-server -p 8080:8080 -e DATASOURCE_HOST=host.docker.internal lavalentine/account-server
  ```
## Или
ШАГ #1

  ```
    cd account-server
  ```
ШАГ #2

  ```
    docker compose up
  ```

## Документация API
### GET  `/api/{id}/amount`
#### Получить баланс счета
> Запрос
> * Переменные
>
>   `id` - идентификатор аккаунта
>
> Ответ
>   ```
>   { 
>       "message":"The balance of account ID is equal to N"
>   } 
>   ``` 

### POST  `/api/{id}/amount`
#### Операция пополнения или списания средств со счета
> Запрос
> * Переменные 
>   
>   `id` - идентификатор аккаунта
> * Параметры
>   
>   `value` - количество средств которое будет начислено (положительное значение) или списано (отрицательное значение)
> 
> Ответ
>   ```
>   { 
>       "message":"The balance of account ID has been changed"
>   } 
>   ``` 

### POST  `/api/statistic`
#### Сбросить статистику запросов
> Ответ
>   ```
>   { 
>       "message": "Statistics reset" 
>   } 
>   ```
### Получение статистики
По умолчанию каждые 30 секунд статистика сбрасывается в лог (файл metrics.log)
Периодичность можно настроить при запуске сервера, установив переменную `STATISTIC_INTERVAL` в соответствии с форматом ISO 8601 

### Заранее зарегистрированные счета
id | balance
---|---|
1 | 0
2 | 500
3 | 1000
4 | 200000
## Запуск тестового клиента
ШАГ #1
  ```
    mvn  -f ./account-test-client package -DskipTests
  ```
ШАГ #2

Настройте файл `account-test-client/test-client-settings.xml`

Установите необходимые значения параметров

Пример

```
    <Settings>
        <rCount>2</rCount>
        <wCount>3</wCount>
        <idList>
            <id>1</id>
            <id>2</id>
            <id>3</id>
            <id>4</id>
            <id>5</id>
        </idList>
    </Settings>
```
ШАГ #3
  ```
    java -jar ./account-test-client/target/account-test-client-0.0.1-SNAPSHOT.jar
  ```
## Запуск тестового клиента, реализованного с помощью TestNG
ШАГ #1
  ```
    mvn  -f ./account-client package -DskipTests
  ```
ШАГ #2

Настройте файл `account-client/test-client.xml`

Установите необходимые значения параметров

Пример

```
...
    <parameter name="rCount" value="20" />
    <parameter name="wCount" value="1" />
    <parameter name="idList" value="1,2,3,4,5,6,7,8,9,10" />
...
```
ШАГ #3
  ```
    java -jar ./account-client/target/account-client-0.0.1-SNAPSHOT.jar
  ```