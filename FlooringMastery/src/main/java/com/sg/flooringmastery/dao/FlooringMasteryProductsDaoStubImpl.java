/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author capta
 */
public class FlooringMasteryProductsDaoStubImpl implements FlooringMasteryProductsDao {

    private Map<String, Product> products = new LinkedHashMap<>();
    
    public FlooringMasteryProductsDaoStubImpl() {
        Product product1 = new Product();
        product1.setProductType("Bamboo");
        product1.setCostPerSquareFoot(new BigDecimal(35.00));
        product1.setLaborCostPerSquareFoot(new BigDecimal(12.50));
        
        products.put(product1.getProductType(), product1);
    }

    public int loadCount = 0;

    @Override
    public void loadProductData() throws FlooringMasteryPersistenceException {
        loadCount++;
    }

    public int saveCount = 0;

    @Override
    public void writeProductData() throws FlooringMasteryPersistenceException {
        saveCount++;
    }

    @Override
    public Product addProduct(Product newProduct) {
        return null;
    }

    @Override
    public Product getProduct(String productType) {
        return this.products.get(productType);
    }

    @Override
    public List<Product> getAllProducts() {
        Collection<Product> allProducts = this.products.values();
        return new ArrayList<>(allProducts);
    }

    @Override
    public void editProduct(String productType,
            Product productToEdit) {
    }

    @Override
    public Product removeProduct(Product removedProduct) {
        return null;
    }

}
