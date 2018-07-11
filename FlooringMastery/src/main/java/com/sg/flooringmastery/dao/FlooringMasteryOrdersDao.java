/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author capta
 */
public interface FlooringMasteryOrdersDao {

    public void loadOrderData() throws FlooringMasteryPersistenceException;

    public void writeOrderData() throws FlooringMasteryPersistenceException;

    public Order addOrder(Order newOrder);

    public List<Order> getAllOrders();

    public Order getOrder(int orderNumber);

    public Order removeOrder(Order removedOrder);

    public void editOrder(int oldOrderNumber, Order orderToEdit);

}
