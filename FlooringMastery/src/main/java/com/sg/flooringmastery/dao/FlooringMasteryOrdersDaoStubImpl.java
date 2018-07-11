/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author capta
 */
public class FlooringMasteryOrdersDaoStubImpl implements FlooringMasteryOrdersDao {

    Order order1;
    Order order2;

    private Map<Integer, Order> orders = new LinkedHashMap<>();

    public FlooringMasteryOrdersDaoStubImpl() {
        order1 = new Order();
        order1.setOrderNumber(1);
        order1.setOrderDate(LocalDate.parse("11/20/1991", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        order1.setCustomerName("Tim");
        order1.setState("TX");
        order1.setTaxRate(new BigDecimal("4.45"));
        order1.setProductType("Bamboo");
        order1.setArea(new BigDecimal("97.00"));
        order1.setCostPerSquareFoot(new BigDecimal("35.00"));
        order1.setLaborCostPerSquareFoot(new BigDecimal("12.50"));
        order1.setMaterialCost(new BigDecimal("3395.00"));
        order1.setLaborCost(new BigDecimal("1212.50"));
        order1.setTax(new BigDecimal("184.30"));
        order1.setTotal(new BigDecimal("4791.80"));

        orders.put(order1.getOrderNumber(), order1);

        order2 = new Order();
        order2.setOrderNumber(2);
        order2.setOrderDate(LocalDate.parse("11/20/1990", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        order2.setCustomerName("Arya");
        order2.setState("CA");
        order2.setTaxRate(new BigDecimal("25.00"));
        order2.setProductType("Marble Tile");
        order2.setArea(new BigDecimal("217.00"));
        order2.setCostPerSquareFoot(new BigDecimal("15.00"));
        order2.setLaborCostPerSquareFoot(new BigDecimal("25.00"));
        order2.setMaterialCost(new BigDecimal("3255.00"));
        order2.setLaborCost(new BigDecimal("5425.00"));
        order2.setTax(new BigDecimal("2170.00"));
        order2.setTotal(new BigDecimal("10850.00"));

        orders.put(order2.getOrderNumber(), order2);
    }

    public int loadCount = 0;
    
    @Override
    public void loadOrderData() throws FlooringMasteryPersistenceException {
        loadCount++;
    }

    public int saveCount = 0;
    
    @Override
    public void writeOrderData() throws FlooringMasteryPersistenceException {
        saveCount++;
    }

    @Override
    public Order addOrder(Order newOrder) {
        if (newOrder == order1) {
            return order1;
        } else if (newOrder == order2) {
            return order2;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        Collection<Order> allOrders = this.orders.values();
        return new ArrayList<>(allOrders);
    }

    @Override
    public Order getOrder(int orderNumber) {
        if (orderNumber == 1) {
            return order1;
        } else if (orderNumber == 2) {
            return order2;
        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(Order removedOrder) {
        if (removedOrder == order1) {
            orders.remove(order1.getOrderNumber());
            order1 = orders.get(0);
            return order1;
        } else if (removedOrder == order2) {
            orders.remove(order2.getOrderNumber());
            order1 = orders.get(1);
            return order2;
        } else {
            return null;
        }
    }

    @Override
    public void editOrder(int oldOrderNumber,
            Order orderToEdit) {
        if (oldOrderNumber == orderToEdit.getOrderNumber()) {
            this.orders.replace(oldOrderNumber, orderToEdit);

        } else {
            Order oldOrder = this.orders.remove(oldOrderNumber);
            this.orders.put(orderToEdit.getOrderNumber(), orderToEdit);
        }
    }

}
