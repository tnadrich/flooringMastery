/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrdersDao;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dao.FlooringMasteryProductsDao;
import com.sg.flooringmastery.dao.FlooringMasteryTaxesDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author capta
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private FlooringMasteryOrdersDao orderDao;
    private FlooringMasteryTaxesDao taxDao;
    private FlooringMasteryProductsDao productDao;
    
    private String bootConfig;
    
    public String getBootConfig() {
        return bootConfig;
    }
    
    public void setBootConfig(String bootConfig){
        this.bootConfig = bootConfig;
    }

    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrdersDao orderDao,
            FlooringMasteryTaxesDao taxDao,
            FlooringMasteryProductsDao productDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }
    
    @Override
    public boolean bootConfig(){
        if (bootConfig.equals("PROD")){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void load() throws FlooringMasteryPersistenceException {
        orderDao.loadOrderData();
        taxDao.loadTaxData();
        productDao.loadProductData();
    }

    @Override
    public void save() throws FlooringMasteryPersistenceException {
        if (bootConfig.equals("PROD")) {
            orderDao.writeOrderData();
        }
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate orderDate) throws FlooringMasteryNoOrderExistsByDateException {
        Collection<Order> allOrders = orderDao.getAllOrders();
        Stream<Order> allOrdersByDateStream = allOrders.stream().filter(p -> p.getOrderDate().equals(orderDate));
        List<Order> allOrdersByDateList = new ArrayList<>();
        allOrdersByDateList = allOrdersByDateStream.collect(Collectors.toList());
        if (allOrdersByDateList.isEmpty()) {
            throw new FlooringMasteryNoOrderExistsByDateException("No order exists for " + orderDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ".");
        }
        return allOrdersByDateList;
    }

    @Override
    public Order createOrder(Order newOrder) {
        setOrderNumber(newOrder);
        setOrderFields(newOrder);
        return orderDao.addOrder(newOrder);
    }

    @Override
    public Order generateOrderData(Order editedOrder) {
        setOrderFields(editedOrder);
        Order generatedOrder = editedOrder;
        return generatedOrder;
    }

    private void setOrderNumber(Order newOrder) {
        int newOrderNumber = validateOrderNumber();
        newOrder.setOrderNumber(newOrderNumber);
    }

    private void setOrderFields(Order newOrder) {
        String state = newOrder.getState();
        newOrder.setTaxRate(taxDao.getTax(state).getTaxRate());

        String productType = newOrder.getProductType();
        newOrder.setCostPerSquareFoot(productDao.getProduct(productType).getCostPerSquareFoot());
        newOrder.setLaborCostPerSquareFoot(productDao.getProduct(productType).getLaborCostPerSquareFoot());

        BigDecimal materialCost = newOrder.getArea().multiply(newOrder.getCostPerSquareFoot());
        newOrder.setMaterialCost(materialCost);

        BigDecimal laborCost = newOrder.getArea().multiply(newOrder.getLaborCostPerSquareFoot());
        newOrder.setLaborCost(laborCost);

        BigDecimal materialAndLaborCostTotal = materialCost.add(laborCost);

        BigDecimal tax = newOrder.getTaxRate().multiply(materialAndLaborCostTotal);
        newOrder.setTax(tax.movePointLeft(2));

        BigDecimal total = materialAndLaborCostTotal.add(tax.movePointLeft(2));
        newOrder.setTotal(total);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    private int validateOrderNumber() {
        List<Order> allOrders = orderDao.getAllOrders();
        ArrayList<Integer> allOrderNumbers = new ArrayList<>();
        for (Order allOrderNumbersOrder : allOrders) {
            int orderNumber = allOrderNumbersOrder.getOrderNumber();
            allOrderNumbers.add(orderNumber);
        }
        int highestExistingOrderNumber = Collections.max(allOrderNumbers);
        int newOrderNumber = generateOrderNumber(highestExistingOrderNumber);
        return newOrderNumber;
    }

    private int generateOrderNumber(int highestExistingOrderNumber) {
        int newOrderNumber = highestExistingOrderNumber + 1;
        return newOrderNumber;
    }

    @Override
    public Order getOrderByDateAndOrderNumber(int orderNumber,
            LocalDate orderDate) throws FlooringMasteryNoOrderExistsByOrderNumberAndByDateException {

        Order singleOrderByDateAndOrderNumber = null;

        Collection<Order> allOrders = orderDao.getAllOrders();
        Stream<Order> orderByDateAndOrderNumberStream = allOrders.stream().filter(p -> p.getOrderDate().equals(orderDate) && p.getOrderNumber() == orderNumber);

        List<Order> orderByDateAndOrderNumber = new ArrayList<>();

        orderByDateAndOrderNumber = orderByDateAndOrderNumberStream.collect(Collectors.toList());
        if (!orderByDateAndOrderNumber.isEmpty()) {
            singleOrderByDateAndOrderNumber = orderByDateAndOrderNumber.get(0);
        } else {
            throw new FlooringMasteryNoOrderExistsByOrderNumberAndByDateException("No order exists for order number: " + orderNumber + " and date: " + orderDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ".");
        }

        return singleOrderByDateAndOrderNumber;
    }

    @Override
    public void removeOrder(Order removedOrder) {
        orderDao.removeOrder(removedOrder);
    }

    @Override
    public List<Tax> getAllTaxes() {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public void editOrder(Order editedOrder) {
        orderDao.editOrder(editedOrder.getOrderNumber(), editedOrder);
    }

}
