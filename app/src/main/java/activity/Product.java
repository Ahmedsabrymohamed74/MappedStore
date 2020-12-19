package activity;

public class Product {
    private final String name;
    private final String description;
    private final String image_url;

    public Product(String name, String description, String image_url) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }
}
