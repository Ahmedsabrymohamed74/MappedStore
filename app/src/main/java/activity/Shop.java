package activity;


public class Shop {
    private String name;
    private double price;
    private String specialOffers;
    private double latitude;
    private double longitude;

    public Shop(String name, double price, String specialOffers, double latitude, double longitude) {
        this.name = name;
        this.price = price;
        this.specialOffers = specialOffers;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public String getSpecialOffers() {
        return specialOffers;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
