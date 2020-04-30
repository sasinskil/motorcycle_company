This application helps employees with the management of a motorcycle company.

Completed:
- backend - > 100%
- frontend - > 100%

# How to start ?

## Client app

### Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

## Rest app

### Project setup
```
- create database: motorcycle_company
- launch the application - automatically create a database structure
- insert sql roles script:
  INSERT INTO roles (id, name)
  VALUES
    (1,'ROLE_ADMIN'),
    (2,'ROLE_USER')
- prepare user with admin rights (for example, using a postman):
   'http://localhost:9090/api/auth/signup' method 'POST'
   {
    "name" : "user",
    "username" : "userName",
    "email" : "user@gmail.com",
    "role" : ["admin","user"],
    "password" : "password"
  }

```
Enjoy 😎

# Technologies used

## Server side

- Spring Boot (Security -> JWT),
- PostgreSQL


## Client side

- Vue.js/Vuex
- Sass

