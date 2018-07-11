/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
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
public class FlooringMasteryTaxesDaoStubImpl implements FlooringMasteryTaxesDao {

    private Map<String, Tax> taxes = new LinkedHashMap<>();
    
    public FlooringMasteryTaxesDaoStubImpl() {
        Tax newTax = new Tax();
        newTax.setState("TX");
        newTax.setTaxRate(new BigDecimal("4.45"));
        
        taxes.put(newTax.getState(), newTax);
    }

    public int loadCount = 0;

    @Override
    public void loadTaxData() throws FlooringMasteryPersistenceException {
        loadCount++;
    }

    public int saveCount = 0;

    @Override
    public void saveTaxData() throws FlooringMasteryPersistenceException {
        saveCount++;
    }

    @Override
    public Tax addTax(Tax newTax) {
        return null;
    }

    @Override
    public Tax getTax(String state) {
        return this.taxes.get(state); 
    }

    @Override
    public List<Tax> getAllTaxes() {
        Collection<Tax> allTaxes = this.taxes.values();
        return new ArrayList<>(allTaxes);
    }

    @Override
    public void editTax(String state,
            Tax taxToEdit) {
    }

    @Override
    public Tax removeTax(Tax removedTax) {
        return null;
    }

}
