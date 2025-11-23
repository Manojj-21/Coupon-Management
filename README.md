# Coupon-Management
A simple Spring Boot RESTful API for managing and applying coupons to e-commerce user carts. Designed as part of an assignment to demonstrate API development, eligibility logic, and best-coupon selection without using a database or authentication.

## Features
- Create new coupons with custom eligibility rules (e.g., user tier, spend, cart value, applicable/excluded categories).
- Retrieve the best applicable coupon for a user’s cart based on highest discount, earliest expiry, and code tiebreak.
- In-memory storage (no authentication or database setup required).
- Robust eligibility checks and error handling.
## Tech Stack Used
- Java 17
- Spring Boot
- Maven
- Lombok
## Getting Started
### Prerequisites
- Java 17+
- Maven

## Setup and Run

- **Clone the repository:**
git clone https://github.com/yourusername/coupon-management.git

- **Navigate to the project directory:**
cd coupon-management

- **Build and run:**
mvn spring-boot:run

- Or run from STS4 (Spring Tool Suite) IDE.

---

## API Usage

### Create Coupon

- **POST** `/api/coupons`
- **Request body:**
{
"code": "WELCOME100",
"description": "Welcome discount for new users",
"discountType": "FLAT",
"discountValue": 100,
"maxDiscountAmount": null,
"startDate": "2025-01-01",
"endDate": "2025-12-31",
"usageLimitPerUser": 1,
"eligibility": {
"allowedUserTiers": ["GOLD"],
"minLifetimeSpend": 500,
"minOrdersPlaced": 1,
"firstOrderOnly": false,
"allowedCountries": ["India"],
"minCartValue": 200,
"applicableCategories": ["Electronics"],
"excludedCategories": null,
"minItemsCount": 1
}
}

### Get Best Coupon

- **POST** `/api/coupons/best`
- **Request body:**
{
"user": {
"userId": "user123",
"userTier": "GOLD",
"country": "India",
"lifetimeSpend": 1000,
"ordersPlaced": 5
},
"cart": {
"items": [
{
"productId": "prod1",
"category": "Electronics",
"price": 500,
"quantity": 2
}
]
}
}

- **Success response:**
{
"code": "WELCOME100",
"description": "Welcome discount for new users",
"discountAmount": 100.0,
"finalCartValue": 900.0
}

- **Error response if no coupon found:**
{
"message": "No applicable coupon found"
}

---

## Testing

- Use Postman or curl for manual endpoint testing.
- (Optional) To run unit tests, execute:
mvn test

---

## Notes for Reviewer

- No authentication or database is implemented—only in-memory storage as per assignment spec.
- All eligibility logic, best-coupon selection, and error handling are clearly commented in the code.
- README includes complete instructions and sample payloads.
- AI tools were used for assistance and code generation.
  "message": "No applicable coupon found"
}
