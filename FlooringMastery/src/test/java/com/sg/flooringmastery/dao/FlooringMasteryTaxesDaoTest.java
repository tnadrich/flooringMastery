/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
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
public class FlooringMasteryTaxesDaoTest {

    private FlooringMasteryTaxesDao taxesDao = new FlooringMasteryTaxesDaoFileImpl();

    public FlooringMasteryTaxesDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        List<Tax> taxList = taxesDao.getAllTaxes();
        for (Tax tax : taxList) {
            taxesDao.removeTax(tax);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetTax() throws Exception {
        Tax tax1 = new Tax();
        tax1.setState("FL");
        tax1.setTaxRate(new BigDecimal("54.00"));

        taxesDao.addTax(tax1);

        Tax fromTaxesDao = taxesDao.getTax(tax1.getState());
        Assert.assertNotNull("Get tax1 should not return null", fromTaxesDao);
        Assert.assertEquals("tax1 should match what is returned.", tax1, fromTaxesDao);
    }

    /**
     * Test of getAllTaxes method, of class FlooringMasteryTaxesDao.
     */
    @Test
    public void testGetAllTaxes() {
        Tax tax1 = new Tax();
        tax1.setState("FL");
        tax1.setTaxRate(new BigDecimal("54.00"));

        taxesDao.addTax(tax1);

        Tax tax2 = new Tax();
        tax2.setState("WA");
        tax2.setTaxRate(new BigDecimal("1.00"));

        taxesDao.addTax(tax2);

        Assert.assertEquals("There should be 2 taxes.", 2, taxesDao.getAllTaxes().size());
        Assert.assertTrue("Should contain tax1.", taxesDao.getAllTaxes().contains(tax1));
        Assert.assertTrue("Should contain tax2", taxesDao.getAllTaxes().contains(tax2));
        Assert.assertNotNull("Should not be null. 2 taxes", taxesDao.getAllTaxes());
        Assert.assertFalse("Should not be empty.", taxesDao.getAllTaxes().isEmpty());
    }

    /**
     * Test of removeTax method, of class FlooringMasteryTaxesDao.
     */
    @Test
    public void testRemoveTax() {
        Tax tax1 = new Tax();
        tax1.setState("FL");
        tax1.setTaxRate(new BigDecimal("54.00"));

        taxesDao.addTax(tax1);

        Tax tax2 = new Tax();
        tax2.setState("WA");
        tax2.setTaxRate(new BigDecimal("1.00"));

        taxesDao.addTax(tax2);

        taxesDao.removeTax(tax1);
        Assert.assertEquals("Should only be 1 tax since 1 was "
                + "removed.", 1, taxesDao.getAllTaxes().size());
        Assert.assertNull("Tax 1 should no longer be in there after "
                + "removal.", taxesDao.getTax(tax1.getState()));

        taxesDao.removeTax(tax2);
        Assert.assertEquals("Should be zero taxes since both "
                + "were removed.", 0, taxesDao.getAllTaxes().size());
        Assert.assertNull("When trying to get an tax that is not there, "
                + "should return null.", taxesDao.getTax(tax2.getState()));
    }

    /**
     * Test of editTax method, of class FlooringMasteryTaxesDao.
     */
    @Test
    public void testEditTax() {
        Tax tax1 = new Tax();
        tax1.setState("FL");
        tax1.setTaxRate(new BigDecimal("54.00"));

        taxesDao.addTax(tax1);

        Tax tax2 = new Tax();
        tax2.setState("WA");
        tax2.setTaxRate(new BigDecimal("1.00"));

        taxesDao.editTax(tax1.getState(), tax2);

        Tax fromTaxesDao = taxesDao.getTax(tax2.getState());

        Assert.assertEquals("getAllTaxes should return 1 since the "
                + "tax was edited and another was not "
                + "added.", 1, taxesDao.getAllTaxes().size());
        Assert.assertEquals("Tax2 should match what is returned "
                + "since Tax1 was edited to be replaced by tax2.", tax2, fromTaxesDao);
        Assert.assertEquals("WA should be the new state of the tax returned "
                + "", "WA", fromTaxesDao.getState());
        Assert.assertNotNull("There should be 1 tax , not null.", fromTaxesDao);
    }
}
