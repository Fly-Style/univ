/**
 * @Author is flystyle
 * Created on 26.03.16.
 */
public class Product {
    private int price;
    private String name;
    private int id;
    private Category category;

    public Product(int id, String name, int price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryId () {
        return this.category.getCategoryName();
    }
}
