import java.util.ArrayList;
import java.util.List;

/**
 * @Author is flystyle
 * Created on 26.03.16.
 */
public class Shop {
    private List<Product> productList = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    public boolean addToList (Product pr) {
        return productList.add(pr);
    }

    public boolean deleteFromList(Product pr) {
        return productList.remove(pr);
    }

    public boolean addCategoryToList (Category category) {
        return categories.add(category);
    }

    public boolean deleteCatrgoryFromList(Category category) {
        return categories.remove(category);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
