package com.example.capstone1amazonclone.Service;

import com.example.capstone1amazonclone.Model.Merchant;
import com.example.capstone1amazonclone.Model.MerchantStock;
import com.example.capstone1amazonclone.Model.Product;
import com.example.capstone1amazonclone.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {

    ArrayList<User> users;

    public ArrayList<User> getUsers(){
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    public boolean updateUser(String id , User user){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }


// Create endpoint where user can buy a product directly
//• this endpoint should accept user id, product id, merchant id.
//• check if all the given ids are valid or not
//• check if the merchant has the product in stock or return bad request.
//• reduce the stock from the MerchantStock.
//• deducted the price of the product from the user balance.
//• if balance is less than the product price returns bad request.

    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;



    public Integer buyProduct(String userId , String productId , String merchantId){
        for (User user : users){
            if (user.getId().equals(userId)){
                for (Product product : productService.getProducts()){
                    if(product.getId().equals(productId)){
                        for (Merchant merchant : merchantService.getMerchants()){
                            if(merchant.getId().equals(merchantId)){
                                for (MerchantStock merchantStock : merchantStockService.getMerchantStocks()){
                                    if(merchantStock.getStock() > 0){
                                        merchantStock.setStock(merchantStock.getStock() - 1);
                                        if(user.getBalance() >= product.getPrice()){
                                            user.setBalance(user.getBalance() - product.getPrice());
                                            user.addProductPurchased(product);
                                            return 1; // everything works well
                                        }
                                        return 2; // balance is low
                                    }
                                }
                                return 3 ; // out of stock
                            }
                        }
                        return 4; // merchants not found
                    }
                }
                return 5; // product not found
            }
        }
        return 6; // user not found
    }
//---------------------------------------------------

// Extra endpoints :

    // 1- First extra endpoint
    // This method takes three parameters: (categoryId,minPrice,maxPrice)
    // retrieve a list of products from a specific category within a given price range.
    public ArrayList<Product> getProductsByCategoryAndPriceRange(String categoryId, Double minPrice, Double maxPrice){
        ArrayList<Product> productsByCategory = new ArrayList<>();
        for (Product product : productService.getProducts()){
            //Apply Filters: For each product in the list:
            if(product.getCategoryID().equals(categoryId) && product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                productsByCategory.add(product);
            }
        }
        return productsByCategory.isEmpty() ? null : productsByCategory;
    }

    // Method to update product's rating
    public void updateProductRating(Product product, int ratingScale) {
        product.setTotalRatingScore(product.getTotalRatingScore() + ratingScale);
            product.setRatingCount(product.getRatingCount() + 1);
        // Recalculate the average rating
        double averageRating = product.getTotalRatingScore() / product.getRatingCount();
        product.setAverageRating(averageRating);
    }

    //2- Second extra endpoint
    // Method allow a user to rate a product by providing a rating scale between 1 and 4 ,
    // and takes three parameters (userId , productId , ratingScale)
    public String ratingProduct(String userId, String productId, Integer ratingScale) {
            for (User user : users) {
                if (user.getId().equals(userId)) { // Check if the user exists
                    for (Product product : productService.getProducts()) {
                        if (product.getId().equals(productId)) { // Check if the product exists
                            // Update the rating of the product
                            updateProductRating(product, ratingScale);
                            return "A"; // rating successfully
                        }
                    }
                    return "B"; // product not found
                }
            }
            return "C"; // user not found
    }


    // 4- Forth extra endpoint
 //This method allows a user to return a product they have purchased, provided the product is returned within 30 days from the purchase date.
 // If the return is accepted, the user's account will be refunded, and the product will be restocked in the merchant's inventory.
    public Integer returnProduct(String userId, String productId, String merchantId, LocalDate paymentDate) {
        User user = null;
        for (User u : users) { // check if the user exist
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }
        if (user == null) return 5; // User not found

        // Ensure that the products purchased list is not null
        if (user.getProductsPurchased() == null) return 4;

        // Find the product by productId
        Product product = null;
        for (Product p : user.getProductsPurchased()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }
        if (product == null) return 4; // Product not found in user's purchases

        // Find the merchant by merchantId
        Merchant merchant = null;
        for (Merchant m : merchantService.getMerchants()) {
            if (m.getId().equals(merchantId)) {
                merchant = m;
                break;
            }
        }
        if (merchant == null) return 3; // Merchant not found

        // Calculate the days since the product was purchased
        long daysSincePayment = ChronoUnit.DAYS.between(paymentDate, LocalDate.now());
        if (daysSincePayment > 30) return 2; // Return period has passed (more than 30 days)

        // Find the merchant stock for the product
        MerchantStock merchantStock = null;
        for (MerchantStock stock : merchantStockService.getMerchantStocks()) {
            if (stock.getProductId().equals(productId)) {
                merchantStock = stock;
                break;
            }
        }

        // If merchant stock exists, process return and restock
        if (merchantStock != null) {
            merchantStock.setStock(merchantStock.getStock() + 1); // Restock the product
            user.setBalance(user.getBalance() + product.getPrice()); // Refund the user
            user.getProductsPurchased().remove(product); // Remove the product from the purchased list
            return 1; // Return process successful
        }

        return 0; // No stock found for the product
    }


    // 5 - Fifth Extra endpoint
    // Method to return list of product that is bestSeller based on totalRating
    public ArrayList<Product> getBestSellers() {
        ArrayList<Product> products = productService.getProducts();

        for (int i = 0; i < products.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < products.size(); j++) {
                if (products.get(j).getTotalRatingScore() > products.get(maxIndex).getTotalRatingScore()) {
                    maxIndex = j;  // Update maxIndex if we find a product with a higher rating score
                }
            }
            Product temp = products.get(i);
            products.set(i, products.get(maxIndex));
            products.set(maxIndex, temp);
        }
        // Get top 3 best sellers
        int topThree = 3;
        ArrayList<Product> bestSellers = new ArrayList<>();
        for (int i = 0; i < Math.min(topThree, products.size()); i++) {
            bestSellers.add(products.get(i));
        }
        return bestSellers;
    }


    //--------------------------------
    // Extra credit
    //This method allows an "Admin" user to retrieve a list of all merchants available.
    // It verifies that the user exists and has the necessary permissions (Admin role) to access the merchant data.

    public ArrayList<Merchant> getAllMerchants(String userId){
        ArrayList<Merchant> getMerchants = new ArrayList<>();
        for (User user : users){
            if(user.getId().equals(userId)){ // check the user exist
                if(user.getRole().equals("Admin")){// check user role
                    for (Merchant merchant : merchantService.getMerchants()){
                        getMerchants.add(merchant);
                    }
                }
            }
        }
        return (getMerchants.isEmpty()) ? null : getMerchants;
    }

}
