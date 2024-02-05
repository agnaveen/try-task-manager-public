CREATE DATABASE `lifelinker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mobile VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(256) NOT NULL, -- Assuming use of SHA256 for hashing
    role ENUM('ADMIN', 'USER') NOT NULL,
    name VARCHAR(255) NULL,
    email VARCHAR(255) UNIQUE,
    location VARCHAR(255),
    pincode VARCHAR(10),
    latitude DECIMAL(10, 8),  -- You can adjust precision as needed
    longitude DECIMAL(11, 8), -- You can adjust precision as needed
    active ENUM('Y', 'N') NOT NULL,
     blood_group VARCHAR(3)
);

 CREATE TABLE donation_event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    donation_type ENUM('FOOD', 'BLOOD') NOT NULL,
    donation_posted_date_time DATETIME NOT NULL,
    pincode VARCHAR(10),
    full_address VARCHAR(255),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    additionalnote VARCHAR(512), -- Given a length of 512 for a long note
    estimated_quantity DECIMAL(10, 2), -- Assuming a decimal type for quantity
    estimated_units INT, -- Assuming integer units for blood
    status ENUM('POSTED', 'COMPLETED', 'CANCELLED') NOT NULL,
    platform_fee_collected BOOLEAN,
    expiry_date DATETIME,
    created_by INT DEFAULT 1, -- Default to 1 for ADMIN
    updated_by INT DEFAULT 1, -- Default to 1 for ADMIN
    created_date_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_date_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

CREATE TABLE donation_interest (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donation_event_id INT,
    user_id INT,
    status ENUM('ACCEPTED', 'REJECTED', 'PENDING') NOT NULL,
    comment VARCHAR(512), -- Assuming a general length for comments, adjust as needed
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (donation_event_id) REFERENCES donation_event(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE user_notification_settings (
    user_id INT PRIMARY KEY,
    notify_via_whatsapp BOOLEAN DEFAULT FALSE,
    notify_via_sms BOOLEAN DEFAULT FALSE,
    notify_via_email BOOLEAN DEFAULT FALSE,
    notify_via_push BOOLEAN DEFAULT FALSE,
    geofence_radius_km DECIMAL(5,2) DEFAULT 10.00, -- defaulting to 10 km
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notifications_log (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    donation_event_id INT,
    notification_type ENUM('WHATSAPP', 'SMS', 'EMAIL', 'PUSH'),
    status ENUM('SENT', 'FAILED', 'PENDING') NOT NULL DEFAULT 'PENDING',
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (donation_event_id) REFERENCES donation_event(id) ON DELETE CASCADE
);

CREATE TABLE otp_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mobile VARCHAR(15) NOT NULL, -- considering India's mobile number format
    otp VARCHAR(6) NOT NULL,     -- considering a 6-digit OTP
    generated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME,
    purpose ENUM('SIGNUP', 'FORGET_PASSWORD') NOT NULL, -- the reason the OTP was generated
    used BOOLEAN DEFAULT FALSE  -- to indicate if the OTP has been used
);

CREATE TABLE app_configurations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    config_name VARCHAR(255) UNIQUE NOT NULL,
    config_value VARCHAR(255) NOT NULL
);

CREATE TABLE donation_fee_transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donation_event_id INT,
    consumer_user_id INT,
    amount DECIMAL(10,2) NOT NULL,    -- using 2 decimal points for INR
    transaction_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED') DEFAULT 'PENDING',
    payment_method ENUM('UPI', 'DEBITCARD', 'CREDITCARD', 'NETBANKING', 'WALLET'), -- add more methods as necessary
    transaction_reference VARCHAR(255), -- stores the reference ID from the payment gateway or similar
    FOREIGN KEY (donation_event_id) REFERENCES donation_event(id),
    FOREIGN KEY (consumer_user_id) REFERENCES users(id)
);

-- assuming OTPs expire in 15 minutes by default
INSERT INTO app_configurations (config_name, config_value) VALUES ('otp_expiry_minutes', '15'); 
-- assuming a default platform fee of 100 INR
INSERT INTO app_configurations (config_name, config_value) VALUES ('donation_fee_inr', '100'); 


-- to drop all
-- drop table app_configurations;
-- drop table donation_fee_transactions;
-- drop table otp_data;
-- drop table notifications_log;
-- drop table user_notification_settings;
-- drop table donation_interest;
-- drop table donation_event;
-- drop table users;
