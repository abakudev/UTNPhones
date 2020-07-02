

# Trabajo Practico Integrador : Programacion Avanzada I & Base de Datos II 

Build Restful CRUD API for a phonelines company using Spring Boot, Mysql, JPA .

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/abelacu95/UTNPhones.git
```

**2. Create Mysql database**
```bash
create database utn_phones
```
- run `src/main/resources/schema/mysql/utn-phones.sql`

**3. Change mysql username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation


The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs.

### Auth

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/login | Login | [JSON](#login) |
| POST   | /api/logout  | Logout |  |


### Backoffice - Clients

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/backoffice/clients | Get all clients | |
| GET    | /api/backoffice/clients/{dni} | Get client by dni | |
| POST   | /api/backoffice/clients | Add client | [JSON](#clientcreate) |
| PUT    | /api/backoffice/clients/{dni} | Update client by dni | [JSON](#clientupdate) |
| DELETE | /api/backoffice/clients/{dni} | Delete client by dni | |


### Backoffice - PhoneLines

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/backoffice/phone-lines | Get all phone lines | |
| GET    | /api/backoffice/phone-lines/{dni} | Get phone lines by dni | |
| POST   | /api/backoffice/clients/{dni}/phone-lines | Add phone line by client dni | [JSON](#phonelinecreate) |
| PUT    | /api/backoffice/phone-lines/{id} | Enabled phone lines by id | |
| DELETE | /api/backoffice/phone-lines/{id} | Diabled phone lines by id | |


### Backoffice - Tariffs

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST   | /api/backoffice/tariffs | Add tariff | [JSON](#tariffcreate) |
| GET    | /api/backoffice/tariffs | Get all tariffs | |


### Backoffice - Calls - Bills

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET   | /api/backoffice/clients/{dni}/calls | Get calls by client dni  | |
| GET    | /api/backoffice/clients/{dni}/bills | Get bills by client dni | |


### Calls

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| POST   | /api/calls | Add call | [JSON](#callcreate) |


### Web

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/web/calls?from=05/12/2019&to=26/06/2020 | Get calls by dates range  | |
| GET    | /api/web/bills?from=01/05/2020&to=30/06/2020 | Get bills by dates range  | |
| GET    | /api/web/cities/top-10/calls | Get TOP 10 destination most called | |


Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

##### <a id="login">Log In -> /api/login </a>
```json
{
	"dni": "123456789",
	"password": "1234pwd"
}
```

##### <a id="clientcreate">Create Client -> /api/backoffice/clients</a>
```json
{
    "cityId" : 32,
    "firstname" : "Name",
    "lastname" : "LastName",
    "dni" : "123456789",
    "password": "1234pwd",
    "typeLine" : "mobile"
}

```

##### <a id="clientupdate">Update Client -> /api/backoffice/clients?dni=1234578</a>
```json
{
    "cityId" : 4,
    "firstname" : "Name",
    "lastname" : "LastName"
}
```

##### <a id="phonelinecreate">Create Phoneline By Dni -> /api/backoffice/clients/{dni}/phone-lines</a>
```json
{
    "dni":"12345678",
    "typeLine":"mobile"
}
```

##### <a id="tariffcreate">Create Tariff -> /api/posts</a>
```json
{
    "costPrice": 3.5,
    "price": 7.3,
    "cityOriginId":4,
    "cityDestinationId":32
}
```

##### <a id="callcreate">Create Calls -> /api/calls</a>
```json
{
	"numberOrigin":"02234919872",
	"numberDestination":"22678263806",
	"duration":19,
	"date":"03/05/2020"	
}
```
