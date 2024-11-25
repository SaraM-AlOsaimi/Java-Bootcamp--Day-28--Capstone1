# Java-Bootcamp--Day-28--Capstone1 - Amazon Clone
# 1. getProductsbyCategoryAndPriceRange (User)
Description: This endpoint takes three parameters: (categoryId,minPrice,maxPrice)
retrieve a list of products from a specific category within a given price range.
Endpoint: GET /get/product/by/{categoryId}/{minPrice}/{maxPrice}
Location: UserController.java
Parameters:
categoryId: The ID of the product category (e.g., electronics, clothing, etc.).
minPrice: The minimum price range for filtering the products.
maxPrice: The maximum price range for filtering the products.
Response:
200 OK: Arraylist of products matching the given category and price range.
400 Bad Request: No products in the given category with the specified price range.
------------
# 2. ratingProduct (User)
Description:This endpoint allow a user to rate a product by providing a rating scale,
and takes three parameters (userId , productId , ratingScale)
Endpoint: PUT /rating/{userId}/{productId}/{ratingScale}
Location: UserController.java
Parameters:
userId: The ID of the user rating the product.
productId: The ID of the product being rated.
ratingScale: The rating given by the user (an integer between 1 and 4).
Response:
200 OK: Rating applied successfully.
400 Bad Request: Product not found,User not found, Invalid Rating scale.
--------------
# 3. applyDiscount (Merchant)
Description: This endpoint allows a merchant to apply a discount on a specific product.
Endpoint: PUT /apply-discount/{merchantId}/{productId}/{discountAmount}
Location: MerchantController.java
Parameters:
merchantId: The ID of the merchant applying the discount.
productId: The ID of the product to which the discount will be applied.
discountAmount: The discount amount to be applied to the product price.
Response:
200 OK: Discount applied successfully.
400 Bad Request: Product not found,Merchant not found,Invalid discount amount or invalid stock.
-------------------
# 4 - returnProduct (User)
Description: This endpoint allows a user to return a product they have purchased, provided the product 
is returned within 30 days from the purchase date. If the return is accepted, the user's account will be refunded,
and the product will be restocked in the merchant's inventory.
Location: UserController.java
Parameters:
userId (String): The ID of the user returning the product.
productId (String): The ID of the product being returned.
merchantId (String): The ID of the merchant from whom the product was purchased.
paymentDate (LocalDate): The date when the product was paid for.
Response :

------------------
# 5 - 
