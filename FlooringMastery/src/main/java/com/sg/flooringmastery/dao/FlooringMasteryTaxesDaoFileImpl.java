/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author capta
 */
public class FlooringMasteryTaxesDaoFileImpl implements FlooringMasteryTaxesDao {

    public final String DELIMITER = ",";
    public final String DATA_FILE = "src/main/Data/Taxes/Taxes.txt";

    private Map<String, Tax> taxes = new LinkedHashMap<>();

    @Override
    public void loadTaxData() throws FlooringMasteryPersistenceException {
        Scanner textScanner;

        try {

            FileReader fileReader = new FileReader(DATA_FILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            textScanner = new Scanner(bufferedReader);

            while (textScanner.hasNextLine()) {

                Tax newTax = new Tax();

                String currentLine = textScanner.nextLine();
                String[] currentTokens = currentLine.split(DELIMITER);

                newTax = unmarshallTax(currentTokens);
                this.addTax(newTax);

            }
            textScanner.close();

        } catch (FileNotFoundException fileNotThere) {
            throw new FlooringMasteryPersistenceException(DATA_FILE + " not found. -_-");
        }

    }

    private Tax unmarshallTax(String[] currentTokens) throws FlooringMasteryPersistenceException {

        try {

            Tax newTax = new Tax();
            newTax.setState(currentTokens[0]);
            newTax.setTaxRate(new BigDecimal(currentTokens[1]));

            return newTax;

        } catch (NumberFormatException e) {
            throw new FlooringMasteryPersistenceException("Bad data in file.");
        }
    }

    @Override
    public Tax addTax(Tax newTax) {
        this.taxes.put(newTax.getState(), newTax);
        return newTax;
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
    public void saveTaxData() throws FlooringMasteryPersistenceException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE));
            Collection<Tax> allTaxes = this.taxes.values();
            for (Tax singleTax : allTaxes) {
                String taxAsString = this.marshallTax(singleTax);
                writer.println(taxAsString);
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save data.", e);
        }

    }

    private String marshallTax(Tax singleTax) {
        return singleTax.getState() + DELIMITER
                + singleTax.getTaxRate();
    }

    @Override
    public void editTax(String state,
            Tax taxToEdit) {
        if (state.equals(taxToEdit.getState())) {
            this.taxes.replace(state, taxToEdit);
        } else {
            Tax oldTax = this.taxes.remove(state);
            this.taxes.put(taxToEdit.getState(), taxToEdit);
        }
    }

    @Override
    public Tax removeTax(Tax removedTax) {
        this.taxes.remove(removedTax.getState());
        return removedTax;
    }
}
