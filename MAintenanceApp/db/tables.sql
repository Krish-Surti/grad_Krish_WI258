CREATE TABLE admin (
    admin_id SERIAL PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

INSERT INTO admin (username, password)
VALUES ('admin', 'admin123');

CREATE TABLE site (
    site_id SERIAL PRIMARY KEY,
    site_type VARCHAR(20),   -- Villa, Apartment, etc
    length INT,
    breadth INT,
    status VARCHAR(10),      -- OPEN / OCCUPIED
    owner_id INT
);

CREATE TABLE owner (
    owner_id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    phone VARCHAR(15),
    approved BOOLEAN DEFAULT FALSE
);

CREATE TABLE maintenance (
    maintenance_id SERIAL PRIMARY KEY,
    site_id INT,
    amount INT,
    paid BOOLEAN DEFAULT FALSE
);
