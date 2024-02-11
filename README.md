# Installing the required software
## Install Java 17
Follow this link - https://java.tutorials24x7.com/blog/how-to-install-openjdk-17-on-windows

## Install MySQL 8
Follow this link - https://www.sqlshack.com/how-to-install-mysql-database-server-8-0-19-on-windows-10/

### Download Intellij Idea Community Version
Download Link - https://www.jetbrains.com/idea/download/?section=windows


![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/07639de3-3b83-41d0-97e6-4b3a2c975317)


## Connect your Local Mysql Server from MySQL Workbench
> Create a new database - `trytaskmanager`
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/80a913c7-9541-4951-bcf3-4baba90ba609)

### Run the below scripts to create a required tables
> Same scripts are also placed inside db.sql file also for reference

``CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);``


``CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date DATE,
    priority INT CHECK (priority >= 0 AND priority <= 10),
    assigned_to INT,
    created_by INT,
    status ENUM('TODO', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'TODO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (assigned_to) REFERENCES users(id),
    FOREIGN KEY (created_by) REFERENCES users(id)
);``

### You should be able to see new tables like this,
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/2d74a9a1-e2ff-41b6-bdb5-9bb7c7ed6c61)

### Make sure you have right config in your application.properties for Mysql
> During mysql installer you can configure your username and password, the same should be configured in the `application.properties` file.

![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/864fe034-64c8-4a5a-940a-bd950eeb63b4)

### Run `TaskManagerApplication.java` file from your IDE 
> Please note that we are using Intellij Idea Community IDE - the download link is also attached at the top of the page for your reference

![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/265daabd-03de-4a0a-adf8-5ab358c59fe5)

## Validating the API's
Open https://postman.com and import this collection.
[Task Manager.postman_collection.json](https://github.com/agnaveen/try-task-manager-public/files/14234144/Task.Manager.postman_collection.json)

### Signup User API
#### API Endpoint Description
To create a new user.

#### Endpoint
`[POST] http://localhost:8001/taskmanager/api/v1/user/signup?otp=123456`

#### Request Headers
- `Content-Type: application/json`

#### Request Parameter
- `otp`: 123456 (Int)

#### Request:
```json
{
    "username":"naveen@techriseyou.com",
    "password":"try@1234"
}
```

#### Response
```json
{
    "success": true,
    "data": {
        "id": 1,
        "username": "naveen@techriseyou.com"
    }
}
```




#### Signup User API - Screenshot for your reference
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/14f1cd81-2b18-4beb-8117-231392925951)


### Login API
#### API Endpoint Description
To login with the credentials as per the signup.

#### Endpoint
`[POST] http://localhost:8001/taskmanager/api/v1/user/login`

#### Request Headers
- `Content-Type: application/json`

#### Request:
```json
{
    "username":"naveen@techriseyou.com",
    "password":"try@1234"
}
```

#### Response:
```json
{
    "success": true,
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYXZlZW5AdGVjaHJpc2V5b3UuY29tIiwiaWF0IjoxNzA3Njg4Mjk2LCJleHAiOjE3MDc2OTAwOTZ9.N3Ux03ZoHFXAk_zq9wwfrLqNktDd2rOmPP6usxwi108"
}
```

#### Login API Postman Screenshot for reference
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/45dfa55b-b914-44a7-843f-44be54ff0919)

#### Token Details For your better understanding
Open https://jwt.io to check your token details

![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/c1fb1a30-4ad2-4592-86cb-133e49f0ce14)

### Create a Task API
#### API Endpoint Description
To create a New Todo Task

#### Endpoint
`[POST] http://localhost:8001/taskmanager/api/v1/task`

#### Request Headers
- `Content-Type: application/json`
- `Authorization: Bearer <Your JWT Token>`

#### Request Body
```json
{
    "title": "Complete the TRY monthly report",
    "description": "Complete and share to TRY Sales team",    
    "priority": 1
}
```

#### Response Body
```json
{
    "id": 1,
    "title": "Complete the TRY monthly report",
    "description": "Complete and share to TRY Sales team",
    "priority": 1,
    "status": "TODO"
}
```

#### Create a Task API - Postman Screenshot for reference
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/557839cf-e1bb-4459-a198-e56082b20d63)

#### Make sure to pass the Login Token 
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/907e8d71-a11b-4063-94d1-8c79e5b0bb31)

### Get Tasks API
> Include JWT as Bearer Token in Postman's Authorization header.

#### API Endpoint Description
Get All Tasks

#### Endpoint
`[GET] http://localhost:8001/taskmanager/api/v1/task`

#### Request Headers
- `Content-Type: application/json`
- `Authorization: Bearer <Your JWT Token>`

#### Response Body
```json
[
    {
        "id": 1,
        "title": "Complete the TRY monthly report",
        "description": "Complete and share to TRY Sales team",
        "priority": 1,
        "status": "TODO"
    },   
    {
        "id": 2,
        "title": "Email the client with project updates",
        "priority": 2,
        "status": "TODO"
    }
    
]
```
#### Get Tasks API - Postman Screenshot for reference
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/0b706c64-2fe2-4281-92c1-78ebb7aaa4cf)

### Get Task By Id API
> Include JWT as Bearer Token in Postman's Authorization header.
#### API Endpoint Description
To get a todo task details by the specified id

#### Endpoint
`[GET] http://localhost:8001/taskmanager/api/v1/task/2`

#### Request Headers
- `Content-Type: application/json`
- `Authorization: Bearer <Your JWT Token>`

#### Response Body
```json
{
    "id": 2,
    "title": "Email the client with project updates",
    "priority": 2,
    "status": "TODO"
}
```

#### Get Task By Id API - Postman Screenshot for reference
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/f8797bc6-e402-42ac-89f4-0b1bbe50eb1d)

### Update Task API
> Include JWT as Bearer Token in Postman's Authorization header.

#### API Endpoint Description
> Right now title should be sent as a mandatory input during update, which can be modified as per your wish.
To update a task details such as status/priority.

#### Endpoint
`[PUT] http://localhost:8001/taskmanager/api/v1/task/{taskId}`

#### Request Headers
- `Content-Type: application/json`
- `Authorization: Bearer <Your JWT Token>`

#### Path Parameters
- `taskId` (required): The unique identifier of the task to be updated.


#### Request Body
```json
{  
    "priority": 5,
    "title": "Email the client with project updates",
    "status": "IN_PROGRESS"
}
```

#### Response Body
```json
{
    "id": 2,
    "title": "Email the client with project updates",
    "priority": 5,
    "status": "IN_PROGRESS"
}
```

#### Update Task API - Postman Screenshot for reference
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/a294b286-3959-445b-842f-24a0881f1703)

### Delete Task API
> Include JWT as Bearer Token in Postman's Authorization header.
#### API Endpoint Description
Deletes a specific task by its ID.

#### Endpoint
`[DELETE] http://localhost:8001/taskmanager/api/v1/task/{taskId}`

#### Request Headers
- `Content-Type: application/json`
- `Authorization: Bearer <Your JWT Token>`

#### Path Parameters
- `taskId` (required): The unique identifier of the task to be deleted.


#### Response Body
> 200 success response without any body details.


#### Delete Task API - Postman Screenshot for reference
![image](https://github.com/agnaveen/try-task-manager-public/assets/6266029/c7d8c0e6-4b27-4942-96b3-521b2f76e7a9)

















