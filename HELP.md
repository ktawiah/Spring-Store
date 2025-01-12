# Spring Store

A Spring Boot-based e-commerce backend application that provides REST APIs for managing products, categories, and images.

## Overview

Spring Store is a robust e-commerce backend system built using Spring Boot 3.3.5 and Java 21. It provides a complete set of APIs for managing products, categories, and product images with support for both MySQL and PostgreSQL databases.

## Features
- Product Management
    - CRUD operations for products
    - Search products by category, brand, and name
    - Product inventory management

- Category Management
    - Create, read, update, and delete product categories
    - Category-based product organization

- Image Management
    - Support for multiple product images
    - Image upload and storage
    - Image metadata management

- Cart Management
    - Create, read, update of user-specific product cart
    - Category-based product organization

## Prerequisites

- JDK 21
- Maven
- PostgreSQL/MySQL database
- IDE (IntelliJ IDEA recommended)

## Installation & Setup
1. Clone the repository:
```bash
git clone https://github.com/ktawiah/spring-store.git
```
2. Configure
```
spring.datasource.url=jdbc:postgresql://localhost:5432/springstore
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build project
```
mvn clean install
```
4. Run application
```
mvn spring-boot:run
```

## Api Endpoints
#### Product
```http request
GET /api/products - Get all products
GET /api/products/{id} - Get product by ID
POST /api/products - Create new product
PUT /api/products/{id} - Update product
DELETE /api/products/{id} - Delete product
GET /api/products/category/{category} - Get products by category
GET /api/products/brand/{brand} - Get products by brand
GET /api/products/name/{name} - Get products by name
```

#### Category
```http request
GET /api/categories - Get all categories
GET /api/categories/{id} - Get category by ID
POST /api/categories - Create new category
PUT /api/categories/{id} - Update category
DELETE /api/categories/{id} - Delete category
```

#### Images
```http request
GET /api/images/{id} - Get image by ID
POST /api/images - Upload image(s)
PUT /api/images/{id} - Update image
DELETE /api/images/{id} - Delete image
```

## Configuration
The application supports both PostgreSQL and MySQL databases. Default configuration uses PostgreSQL.

## Monitoring
Spring Boot Actuator is included for monitoring application health and metrics.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing
1. Fork the repository
2. Create your feature branch (git checkout -b feature/AmazingFeature)
3. Commit your changes (git commit -m 'Add some AmazingFeature')
4. Push to the branch (git push origin feature/AmazingFeature)
5. Open a Pull Request
