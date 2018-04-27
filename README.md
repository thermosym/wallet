# Account & Money Transfer API demo

## Note
- The amount, balance in the system is Decimal Type, precision 16, scale 2
- Test site: http://13.250.112.110:9000/swagger-ui.html

## API Document

### API Authentication
In real production environment, API needs authentication (register account API no need). The following steps are the common API authentication desgin:

- When user login, get a JWT signed by server side, which contains user account info (user id/email)
- Each API sent by user, need to add JWT in HTTP headers
- On processing user request, server side will validate the JWT, retrieve the user id/email from it, and check whether it matches with the user id/email in request body

### /account _[POST]_ 

API: register new account

Request Body

```javascript
{
  "email": "demo@demo.com"
}
```

Response Body

1. succesful response

```javascript
{
  "success": true,
  "balance": 10000
}
```

2. error response
```javascript
{
  "success": false,
  "errorMsg": "error reason"
}
```

| errorMsg | reason |
|:---:|---|
| Email should not be blank | request validation failure |
| Account xxx already exists | duplicated account |

### /wallet/balance [POST]

API: check account balance

Request Body

```javascript
{
  "email": "demo@demo.com"
}
```

Response Body
1. succesful response

```javascript
{
  "success": true,
  "balance": 10000
}
```

2. error response
```javascript
{
  "success": false,
  "errorMsg": "error reason"
}
```

| errorMsg | reason |
|:---:|---|
| Email should not be blank | request validation failure |
| Account xxx not found | cannot find the account |

### /wallet/transaction [POST]

API: make a money transfer transaction

Request Body

```javascript
{
  "email": "demo@demo.com",
  "transferee": "hello@demo.com",
  "amount": 100.2
}
```

Response Body
1. succesful response

```javascript
{
  "success": true
}
```

2. error response
```javascript
{
  "success": false,
  "errorMsg": "error reason"
}
```

| errorMsg | reason |
|:---:|---|
| Email/Transferee should not be blank | request validation failure |
| You cannot transfer to yourself | email and transferee is same |
| The transfer amount should larger than 0 | Amount is invalid |
| Either Account or Transferee not found | email/transferee account not found |
| Transferee | Transferee account not found |
| Account xxx balance insufficient | email account balance insufficient |

### /wallet/transaction-history [POST]

API: check account transaction history

```javascript
{
  "email": "demo@demo.com"
}
```

Response Body
1. succesful response

```javascript
{
  "success": true,
  "transactions": [
    {
      "id": 1,
      "from": "demo@demo.com",
      "to": "hello@demo.com",
      "type": "transfer",
      "amount": 100.2,
      "datetime": "2018-04-01T00:00:00+08:00"
    }
  ]
}
```

2. error response
```javascript
{
  "success": false,
  "errorMsg": "error reason"
}
```

| errorMsg | reason |
|:---:|---|
| Email should not be blank | request validation failure |
| Account xxx not found | cannot find the account |

## how to build
```bash
$./gradlew clean build
```

## Database Schema Setup

use 'schema.sql' to setup mysql DB schema