package activity;

public class Product {
    private String shopName, productName, specialOffers;
    private double price;

    public Product(String shopName, String productName, double price, String specialOffers) {

        this.shopName = shopName;
        this.productName = productName;
        this.specialOffers = specialOffers;
        this.price = price;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(String specialOffers) {
        this.specialOffers = specialOffers;
    }
}