/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author capta
 */
public interface FlooringMasteryServiceLayer {
    
    public void load() throws FlooringMasteryPersistenceException;
    public void save() throws FlooringMasteryPersistenceException;
    
    public List<Order> getAllOrdersByDate(LocalDate orderDate) throws FlooringMasteryNoOrderExistsByDateException;
    
    public Order getOrderByDateAndOrderNumber(int orderNumber, LocalDate orderDate) throws FlooringMasteryNoOrderExistsByOrderNumberAndByDateException;
    
    public Order createOrder(Order newOrder);
    
    public List<Order> getAllOrders();
    
    public void removeOrder(Order removedOrder);
    
    public List<Tax> getAllTaxes();
    
    public List<Product> getAllProducts();
    
    public void editOrder(Order editedOrder);
    
    public Order generateOrderData(Order generatedOrder);
    
    public boolean bootConfig();
    
}
