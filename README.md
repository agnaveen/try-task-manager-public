# Installing the required software
## Install Java 17
Follow this link - https://java.tutorials24x7.com/blog/how-to-install-openjdk-17-on-windows

## Install MySQL 8
Follow this link - https://www.sqlshack.com/how-to-install-mysql-database-server-8-0-19-on-windows-10/

## Connect your Local Mysql Server from MySQL Workbench
### Create a new database - `trytaskmanager`
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

