# Simple REST API's with Spring MVC

REST API to handle Customer entities using Spring MVC with Spring Rest Repositories

## Run as follows

- $ git clone https://github.com/papicella/customer-spring-rest.git
- $ mvn package
- $ mvn spring-boot:run

### Access endpoints as follows using HTTPIE 

#### Find All CUSTOMERS

```
pasapicella@pas-macbook:~$ http http://localhost:8080/v1/customers
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Date: Sat, 14 Oct 2017 04:50:18 GMT
Transfer-Encoding: chunked

[
    {
        "firstName": "pas",
        "id": 1,
        "lastName": "apicella"
    },
    {
        "firstName": "lucia",
        "id": 2,
        "lastName": "apicella"
    },
    {
        "firstName": "lucas",
        "id": 3,
        "lastName": "apicella"
    },
    {
        "firstName": "siena",
        "id": 4,
        "lastName": "apicella"
    }
]
```

#### Find CUSTOMER that exists

```
pasapicella@pas-macbook:~$ http http://localhost:8080/v1/customers/2
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Date: Sat, 14 Oct 2017 04:50:44 GMT
Transfer-Encoding: chunked

{
    "firstName": "lucia",
    "id": 2,
    "lastName": "apicella"
}
```

#### Find CUSTOMER that DOES NOT exists

```
pasapicella@pas-macbook:~$ http http://localhost:8080/v1/customers/7
HTTP/1.1 500
Connection: close
Content-Type: application/json;charset=UTF-8
Date: Sat, 14 Oct 2017 04:50:52 GMT
Transfer-Encoding: chunked

{
    "error": "Internal Server Error",
    "exception": "com.example.spring.rest.CustomerNotFound",
    "message": "Could not find Customer with id # 7",
    "path": "/v1/customers/7",
    "status": 500,
    "timestamp": 1507956652877
}
```

#### Add CUSTOMER using POST

```
customer.json:

{
  "firstName":"Apples",
  "lastName":"Brown"
}

pasapicella@pas-macbook:~/temp$ http POST http://localhost:8080/v1/customers < customer.json
HTTP/1.1 201
Content-Type: application/json;charset=UTF-8
Date: Sat, 14 Oct 2017 05:03:33 GMT
Location: http://localhost:8080/v1/customers/5
Transfer-Encoding: chunked

{
    "firstName": "Apples",
    "id": 5,
    "lastName": "Brown"
}
```

#### Delete CUSTOMER using DELETE

```
pasapicella@pas-macbook:~/temp$ http DELETE http://localhost:8080/v1/customers/5
HTTP/1.1 204
Date: Sat, 14 Oct 2017 05:12:10 GMT
````

#### Verify id CUSTOMER exists using HEAD

```
When it exists a 204 response is sent

pasapicella@pas-macbook:~/temp$ http HEAD http://localhost:8080/v1/customers/1
HTTP/1.1 204
Date: Sat, 14 Oct 2017 05:22:33 GMT

When it does not exist a 404 is returned:

pasapicella@pas-macbook:~/temp$ http HEAD http://localhost:8080/v1/customers/8
HTTP/1.1 404
Content-Length: 0
Date: Sat, 14 Oct 2017 05:22:35 GMT
```

#### Update a CUSTOMER using PUT

```
update-customer.json:

{
  "firstName":"passar",
  "lastName":"apples"
}

pasapicella@pas-macbook:~/temp$ http PUT http://localhost:8080/v1/customers/1 < update-customer.json
HTTP/1.1 201
Content-Type: application/json;charset=UTF-8
Date: Sat, 14 Oct 2017 06:03:54 GMT
Location: http://localhost:8080/v1/customers/1
Transfer-Encoding: chunked

{
    "firstName": "passar",
    "id": 1,
    "lastName": "apples"
}
```

<hr />
Pas Apicella [papicella at pivotal.io] is a Senior Platform Architect at Pivotal Australia 