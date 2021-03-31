package turntabl.io.order_validation_service.model.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {this.productRepository = productRepository; }

    public List<Product> getProducts() { return productRepository.findAll(); }

    public void addNewProduct(Product product) {
        Optional<Product> productOptional = productRepository.findProductByTicker(product.getTicker());
        if(productOptional.isPresent()) {
            throw new IllegalStateException("Ticker is taken");
        }
        productRepository.save(product);
    }

    public void deleteProducts(int product_id) {
        boolean exists = productRepository.existsById(product_id);
        if(!exists) {
            throw new IllegalStateException("product with id "+ product_id + " doesn't exist");
        }

        productRepository.deleteById(product_id);
    }

    @Transactional
    public void updateProduct(Integer productId, String ticker) {
        Product product =  productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException(
                        "Product with id "+ productId + " does not exist"
                ));


        if (ticker != null && ticker.length() > 0 && ! Objects.equals(product.getTicker(), ticker))  {
            Optional<Product> productOptional = productRepository.findProductByTicker(product.getTicker());
            if (productOptional.isPresent()) {
                throw new IllegalStateException("ticker taken");
            }
            product.setTicker(ticker);
        }
    }

    public Product findProductById(int product_id) {
        Optional<Product> product = productRepository.findById(product_id);
        return product.get();
    }
}
