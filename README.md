# Java-Bootcamp--Day-28--Capstone1 - Amazon Clone
# Extra endpoints Overview

## 1. getProductsbyCategoryAndPriceRange (User)
#### Description: This endpoint takes three parameters: (categoryId,minPrice,maxPrice)
#### retrieve a list of products from a specific category within a given price range.
#### Endpoint: GET /get/product/by/{categoryId}/{minPrice}/{maxPrice}
#### Location: UserController.java
#### Parameters:
#### categoryId: The ID of the product category (e.g., electronics, clothing, etc.).
#### minPrice: The minimum price range for filtering the products.
#### maxPrice: The maximum price range for filtering the products.
#### Response:
#### 200 OK: Arraylist of products matching the given category and price range.
#### 400 Bad Request: No products in the given category with the specified price range.

## 2. ratingProduct (User)
#### Description:This endpoint allows a registered user to rate a product on a scale of 1 to 4. 
#### The rating is then stored, updating the product's overall rating .
#### and takes three parameters (userId , productId , ratingScale)
#### Endpoint: PUT /rating/{userId}/{productId}/{ratingScale}
#### Location: UserController.java
#### Parameters:
#### userId: The ID of the user rating the product.
#### productId: The ID of the product being rated.
#### ratingScale: The rating given by the user (an integer between 1 and 4)
1 = Very Good
2 = Good
3 = Average
4 = Bad
#### Response:
#### 200 OK: Rating applied successfully.
#### 400 Bad Request:Invalid ratingScale,the product does not exist, or the user is not found.

## 3. applyDiscount (Merchant)
#### Description: This endpoint allows a merchant to apply a discount on a specific product.
#### Endpoint: PUT /apply-discount/{merchantId}/{productId}/{discountAmount}
#### Location: MerchantController.java
#### Parameters:
#### merchantId: The ID of the merchant applying the discount.
#### productId: The ID of the product to which the discount will be applied.
#### discountAmount: The discount amount to be applied to the product price.
#### Response:
#### 200 OK: Discount applied successfully.
#### 400 Bad Request: Product not found,Merchant not found,Invalid discount amount or invalid stock.

## 4 - returnProduct (User)
#### Description: This endpoint allows a user to return a product they have purchased, provided the product 
#### is returned within 30 days from the purchase date. If the return is accepted, the user's account will be refunded,
#### and the product will be restocked in the merchant's inventory.
#### Endpoint: PUT /return/{userid}/{productid}/{merchantid}/{paymentdate}
#### Location: UserController.java
#### Parameters:
#### userId (String): The ID of the user returning the product.
#### productId (String): The ID of the product being returned.
#### merchantId (String): The ID of the merchant from whom the product was purchased.
#### paymentDate (LocalDate): The date when the product was paid for.
#### Response :
#### 200 OK: Return process done successfully.
#### 400 Bad Request: Product not found,Merchant not found,user not found ,Return period has passed.

## 5 - getAllMerchants (User)
#### Description: This endpoint allows a user with an "Admin" role to retrieve a list of all merchants available in the system. 
#### It checks the user's role to ensure they have the appropriate permissions to access the merchant information.
#### Endpoint: GET /get/merchant/{userid}
#### Location: UserController.java
#### Parameters:
#### userId (String): The ID of the user making the request. This user must have an "Admin" role to access the list of merchants.
#### Response:
#### 200 OK: Returns the list of all merchants if the user has an "Admin" role.
#### 400 Bad Request: If the user is not found or the user is not an "Admin".
