package activity;

public class Favourite {
    private String shop;
    private String product;
    private double price;
    private String specialOffers;
    private String image_url;


    private int product_shop_id;

    public Favourite(int product_shop_id, String shop, String product, double price, String specialOffers, String image_url) {
        this.shop = shop;
        this.product = product;
        this.price = price;
        this.specialOffers = specialOffers;
        this.image_url = image_url;
        this.product_shop_id = product_shop_id;
    }

    public String getShop() {
        return shop;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public String getSpecialOffers() {
        return specialOffers;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getProduct_shop_id() {
        return product_shop_id;
    }
}
