/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author capta
 */
public interface FlooringMasteryTaxesDao {

    public void loadTaxData() throws FlooringMasteryPersistenceException;

    public void saveTaxData() throws FlooringMasteryPersistenceException;

    public Tax addTax(Tax newTax);

    public Tax getTax(String state);

    public List<Tax> getAllTaxes();

    public void editTax(String state,
            Tax taxToEdit);

    public Tax removeTax(Tax removedTax);

}
