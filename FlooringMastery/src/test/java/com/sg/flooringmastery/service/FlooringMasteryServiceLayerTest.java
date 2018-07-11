/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryOrdersDaoStubImpl;
import com.sg.flooringmastery.dao.FlooringMasteryProductsDaoStubImpl;
import com.sg.flooringmastery.dao.FlooringMasteryTaxesDaoStubImpl;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author capta
 */
public class FlooringMasteryServiceLayerTest {

    private FlooringMasteryServiceLayer testService;
    private FlooringMasteryOrdersDaoStubImpl ordersDaoStub;
    private FlooringMasteryTaxesDaoStubImpl taxesDaoStub;
    private FlooringMasteryProductsDaoStubImpl productsDaoStub;

    public FlooringMasteryServiceLayerTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("testingApplicationContext.xml");
        testService = ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);
        ordersDaoStub = ctx.getBean("ordersDaoStub", FlooringMasteryOrdersDaoStubImpl.class);
        taxesDaoStub = ctx.getBean("taxesDaoStub", FlooringMasteryTaxesDaoStubImpl.class);
        productsDaoStub = ctx.getBean("productsDaoStub", FlooringMasteryProductsDaoStubImpl.class);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of load method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testLoad() throws Exception {
        Assert.assertEquals("Should only load once.", ordersDaoStub.loadCount, 0);
        Assert.assertEquals("Should only load once.", taxesDaoStub.loadCount, 0);
        Assert.assertEquals("Should only load once.", productsDaoStub.loadCount, 0);
        testService.load();
        Assert.assertEquals("Should only load once.", ordersDaoStub.loadCount, 1);
        Assert.assertEquals("Should only load once.", taxesDaoStub.loadCount, 1);
        Assert.assertEquals("Should only load once.", productsDaoStub.loadCount, 1);
    }

    /**
     * Test of save method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testSave() throws Exception {
    }

    /**
     * Test of getAllOrdersByDate method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllOrdersByDateWithOrderExisting() throws Exception {
        List<Order> orderListByDate = testService.getAllOrdersByDate(LocalDate.parse("11/20/1990", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        assertNotNull(orderListByDate);
        assertEquals(1, testService.getAllOrdersByDate(LocalDate.parse("11/20/1990", DateTimeFormatter.ofPattern("MM/dd/yyyy"))).size());
    }

    @Test
    public void testGetAllOrdersByDateWithNoOrderExisting() throws Exception {
        boolean correctExceptionThrown = false;
        try {
            // There is no order with with date "11/20/1950"
            testService.getAllOrdersByDate(LocalDate.parse("11/20/1950", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            Assert.fail("Did not throw expected exception");
        } catch (FlooringMasteryNoOrderExistsByDateException e) {
            correctExceptionThrown = true;
        }
    }

    /**
     * Test of getOrderByDateAndOrderNumber method, of class
     * FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetOrderByDateAndOrderNumberWithOrderExisting() throws Exception {
        LocalDate existingDate = (LocalDate.parse("11/20/1990", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        int existingOrderNumber = 2;
        Order existingOrder = testService.getOrderByDateAndOrderNumber(existingOrderNumber, existingDate);
        assertNotNull(existingOrder);
        assertEquals(existingDate, existingOrder.getOrderDate());
        assertEquals(existingOrderNumber, existingOrder.getOrderNumber());

    }

    public void testGetOrderByDateAndOrderNumberWithNoOrderExisting() throws Exception {
        LocalDate existingDate = (LocalDate.parse("11/20/1950", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        int existingOrderNumber = 2;
        boolean correctExceptionThrown = false;
        try {
            // There is no order with with date "11/20/1950"
            testService.getOrderByDateAndOrderNumber(existingOrderNumber, existingDate);
            Assert.fail("Did not throw expected exception");
        } catch (FlooringMasteryNoOrderExistsByOrderNumberAndByDateException e) {
            correctExceptionThrown = true;
        }
        existingDate = (LocalDate.parse("11/20/1990", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        existingOrderNumber = 99;
        correctExceptionThrown = false;
        try {
            // There is no order with with the order number 99"
            testService.getOrderByDateAndOrderNumber(existingOrderNumber, existingDate);
            Assert.fail("Did not throw expected exception");
        } catch (FlooringMasteryNoOrderExistsByOrderNumberAndByDateException e) {
            correctExceptionThrown = true;
        }
    }

    /**
     * Test of createOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testCreateOrder() {
        Order newOrder = new Order();

        newOrder.setOrderDate(LocalDate.parse("11/20/1999", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        newOrder.setCustomerName("Tim");
        newOrder.setState("TX");
        newOrder.setProductType("Bamboo");
        newOrder.setArea(new BigDecimal("97.00"));

        testService.createOrder(newOrder);

        Order compareToOrder = new Order();
        compareToOrder.setOrderNumber(3);
        compareToOrder.setOrderDate(LocalDate.parse("11/20/1999", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        compareToOrder.setCustomerName("Tim");
        compareToOrder.setState("TX");
        compareToOrder.setTaxRate(new BigDecimal("4.45"));
        compareToOrder.setProductType("Bamboo");
        compareToOrder.setArea(new BigDecimal("97.00"));
        compareToOrder.setCostPerSquareFoot(new BigDecimal("35.00"));
        compareToOrder.setLaborCostPerSquareFoot(new BigDecimal("12.50"));
        compareToOrder.setMaterialCost(new BigDecimal("3395.00"));
        compareToOrder.setLaborCost(new BigDecimal("1212.50"));
        compareToOrder.setTax(new BigDecimal("205.03"));
        compareToOrder.setTotal(new BigDecimal("4812.53"));

        assertEquals(newOrder.getOrderNumber(), compareToOrder.getOrderNumber());
        assertEquals(newOrder.getOrderDate(), compareToOrder.getOrderDate());
        assertEquals(newOrder.getCustomerName(), compareToOrder.getCustomerName());
        assertEquals(newOrder.getState(), compareToOrder.getState());
        assertEquals(newOrder.getTaxRate(), compareToOrder.getTaxRate());
        assertEquals(newOrder.getProductType(), compareToOrder.getProductType());
        assertEquals(newOrder.getArea(), compareToOrder.getArea());
        assertEquals(newOrder.getCostPerSquareFoot(), compareToOrder.getCostPerSquareFoot());
        assertEquals(newOrder.getLaborCostPerSquareFoot(), compareToOrder.getLaborCostPerSquareFoot());
        assertEquals(newOrder.getMaterialCost(), compareToOrder.getMaterialCost());
        assertEquals(newOrder.getLaborCost(), compareToOrder.getLaborCost());
        assertEquals(newOrder.getTax(), compareToOrder.getTax());
        assertEquals(newOrder.getTotal(), compareToOrder.getTotal());

        assertNotNull(newOrder);

    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllOrders() {
        List<Order> orderList = testService.getAllOrders();
        Assert.assertEquals("orderList should be the exact same "
                + "list from the dao", orderList, ordersDaoStub.getAllOrders());
        assertEquals(2, testService.getAllOrders().size());
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testRemoveOrder() {
        Order order1 = ordersDaoStub.getOrder(1);
        assertNotNull(order1);
        testService.removeOrder(order1);
        order1 = ordersDaoStub.getOrder(1);
        assertNull(order1);
    }

    /**
     * Test of getAllTaxes method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllTaxes() {
        List<Tax> taxesList = testService.getAllTaxes();
        assertEquals(1, taxesList.size());
        Assert.assertEquals("taxesList should be the exact same "
                + "list from the dao", taxesList, taxesDaoStub.getAllTaxes());
    }

    /**
     * Test of getAllProducts method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllProducts() {
        List<Product> productsList = testService.getAllProducts();
        assertEquals(1, productsList.size());
        Assert.assertEquals("productsList should be the exact same "
                + "list from the dao", productsList, productsDaoStub.getAllProducts());
    }

    /**
     * Test of editOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testEditOrder() {
        Order order1 = ordersDaoStub.getOrder(1);
        order1.setOrderDate(LocalDate.parse("11/20/1999", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        order1.setCustomerName("Tim");
        order1.setState("TX");
        order1.setTaxRate(new BigDecimal("10.00"));
        order1.setProductType("Stone");
        order1.setArea(new BigDecimal("100.00"));
        order1.setCostPerSquareFoot(new BigDecimal("35.00"));
        order1.setLaborCostPerSquareFoot(new BigDecimal("12.50"));
        order1.setMaterialCost(new BigDecimal("3395.00"));
        order1.setLaborCost(new BigDecimal("1212.50"));
        order1.setTax(new BigDecimal("205.03"));
        order1.setTotal(new BigDecimal("5.00"));

        testService.editOrder(order1);

        assertEquals(1, ordersDaoStub.getOrder(1).getOrderNumber());
        assertEquals(LocalDate.parse("11/20/1999", DateTimeFormatter.ofPattern("MM/dd/yyyy")), ordersDaoStub.getOrder(1).getOrderDate());
        assertEquals("Tim", ordersDaoStub.getOrder(1).getCustomerName());
        assertEquals("TX", ordersDaoStub.getOrder(1).getState());
        assertEquals(new BigDecimal("10.00"), ordersDaoStub.getOrder(1).getTaxRate());
        assertEquals("Stone", ordersDaoStub.getOrder(1).getProductType());
        assertEquals(new BigDecimal("100.00"), ordersDaoStub.getOrder(1).getArea());
        assertEquals(new BigDecimal("35.00"), ordersDaoStub.getOrder(1).getCostPerSquareFoot());
        assertEquals(new BigDecimal("12.50"), ordersDaoStub.getOrder(1).getLaborCostPerSquareFoot());
        assertEquals(new BigDecimal("3395.00"), ordersDaoStub.getOrder(1).getMaterialCost());
        assertEquals(new BigDecimal("1212.50"), ordersDaoStub.getOrder(1).getLaborCost());
        assertEquals(new BigDecimal("205.03"), ordersDaoStub.getOrder(1).getTax());
        assertEquals(new BigDecimal("5.00"), ordersDaoStub.getOrder(1).getTotal());
    }

    /**
     * Test of generateOrderData method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGenerateOrderData() {
        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDate.parse("11/20/1999", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        newOrder.setCustomerName("Tim");
        newOrder.setState("TX");
        newOrder.setProductType("Bamboo");
        newOrder.setArea(new BigDecimal("97.00"));

        testService.generateOrderData(newOrder);

        Order compareToOrder = new Order();
        compareToOrder.setOrderNumber(3);
        compareToOrder.setOrderDate(LocalDate.parse("11/20/1999", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        compareToOrder.setCustomerName("Tim");
        compareToOrder.setState("TX");
        compareToOrder.setTaxRate(new BigDecimal("4.45"));
        compareToOrder.setProductType("Bamboo");
        compareToOrder.setArea(new BigDecimal("97.00"));
        compareToOrder.setCostPerSquareFoot(new BigDecimal("35.00"));
        compareToOrder.setLaborCostPerSquareFoot(new BigDecimal("12.50"));
        compareToOrder.setMaterialCost(new BigDecimal("3395.00"));
        compareToOrder.setLaborCost(new BigDecimal("1212.50"));
        compareToOrder.setTax(new BigDecimal("205.03"));
        compareToOrder.setTotal(new BigDecimal("4812.53"));

    }

}
