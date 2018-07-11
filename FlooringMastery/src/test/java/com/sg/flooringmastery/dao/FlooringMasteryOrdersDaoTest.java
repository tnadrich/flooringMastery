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
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author capta
 */
public class FlooringMasteryOrdersDaoTest {

    private FlooringMasteryOrdersDao ordersDao = new FlooringMasteryOrdersDaoFileImpl();

    public FlooringMasteryOrdersDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        List<Order> orderList = ordersDao.getAllOrders();
        for (Order order : orderList) {
            ordersDao.removeOrder(order);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetOrder() throws Exception {
        Order order1 = new Order();
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

        ordersDao.addOrder(order1);

        Order fromOrdersDao = ordersDao.getOrder(order1.getOrderNumber());
        Assert.assertNotNull("Get order1 should not return null", fromOrdersDao);
        Assert.assertEquals("order1 should match what is returned.", order1, fromOrdersDao);
    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
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

        ordersDao.addOrder(order1);

        Order order2 = new Order();
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

        ordersDao.addOrder(order2);

        Assert.assertEquals("There should be 2 orders.", 2, ordersDao.getAllOrders().size());
        Assert.assertTrue("Should contain order1.", ordersDao.getAllOrders().contains(order1));
        Assert.assertTrue("Should contain order2", ordersDao.getAllOrders().contains(order2));
        Assert.assertNotNull("Should not be null. 2 orders", ordersDao.getAllOrders());
        Assert.assertFalse("Should not be empty.", ordersDao.getAllOrders().isEmpty());
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testRemoveOrder() {
        Order order1 = new Order();
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

        ordersDao.addOrder(order1);

        Order order2 = new Order();
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

        ordersDao.addOrder(order2);

        ordersDao.removeOrder(order1);
        Assert.assertEquals("Should only be 1 order since 1 was "
                + "removed.", 1, ordersDao.getAllOrders().size());
        Assert.assertNull("Order 1 should no longer be in there after "
                + "removal.", ordersDao.getOrder(order1.getOrderNumber()));

        ordersDao.removeOrder(order2);
        Assert.assertEquals("Should be zero orders since both "
                + "were removed.", 0, ordersDao.getAllOrders().size());
        Assert.assertNull("When trying to get an order that is not there, "
                + "should return null.", ordersDao.getOrder(order2.getOrderNumber()));
    }

    /**
     * Test of editOrder method, of class FlooringMasteryOrdersDao.
     */
    @Test
    public void testEditOrder() {
        Order order1 = new Order();
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

        ordersDao.addOrder(order1);

        Order order2 = new Order();
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

        ordersDao.editOrder(1, order2);

        Order fromOrdersDao = ordersDao.getOrder(order2.getOrderNumber());

        Assert.assertEquals("getAllOrders should return 1 since the "
                + "order was edited and another was not "
                + "added.", 1, ordersDao.getAllOrders().size());
        Assert.assertEquals("Order2 should match what is returned "
                + "since Order1 was edited to be replaced by order2.", order2, fromOrdersDao);
        Assert.assertEquals("2 should be the new order number of the order returned "
                + "", 2, fromOrdersDao.getOrderNumber());
        Assert.assertNotNull("There should be 1 order , not null.", fromOrdersDao);
    }

}
