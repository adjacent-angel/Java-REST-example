package com.astontech.rest.services;

import com.astontech.rest.domain.Product;
import com.astontech.rest.exceptions.FieldNotFoundException;
import com.astontech.rest.exceptions.ProductNotFoundException;
import com.astontech.rest.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product findBySkuOrId(String sku, Integer id) throws ProductNotFoundException {
        return productRepo.findBySkuOrId(sku, id)
                .orElseThrow(() -> new ProductNotFoundException((sku == null ? String.valueOf(id) : sku)));
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product patchProduct(Map<String, Object> updates, Integer id) throws FieldNotFoundException{
        //find product by id or throw ex
        Product productToPatch = productRepo.findBySkuOrId(null, id)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(id)));

        //iterate over the map of fields to update
        updates.forEach((k, o) -> {
            System.out.println(k + ":" + o);

            // use reflection to get the accessor for the field
            try {
                Field nameField = productToPatch.getClass().getDeclaredField(k);
                //set the field to the value
                nameField.setAccessible(true);
                nameField.set(productToPatch, o);

                // handle ex's
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new FieldNotFoundException(k);
            }
        });

        // save the product we found
        return productRepo.save(productToPatch);
    }

    @Override
    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepo.deleteById(id);
    }
}
