/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author capta
 */
public class FlooringMasteryOrdersDaoFileImpl implements FlooringMasteryOrdersDao {

    public final String DELIMITER = ",";
    public final String DATA_FOLDER = "src/main/Data/Orders";

    private Map<Integer, Order> orders = new LinkedHashMap<>();

    private Order unmarshallOrder(String[] currentTokens,
            LocalDate orderDate) throws FlooringMasteryPersistenceException {

        try {

            Order newOrder = new Order();
            newOrder.setOrderNumber(Integer.parseInt(currentTokens[0]));
            newOrder.setCustomerName(currentTokens[1]);
            newOrder.setState(currentTokens[2]);
            newOrder.setTaxRate(new BigDecimal(currentTokens[3]));
            newOrder.setProductType(currentTokens[4]);
            newOrder.setArea(new BigDecimal(currentTokens[5]));
            newOrder.setCostPerSquareFoot(new BigDecimal(currentTokens[6]));
            newOrder.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[7]));
            newOrder.setMaterialCost(new BigDecimal(currentTokens[8]));
            newOrder.setLaborCost(new BigDecimal(currentTokens[9]));
            newOrder.setTax(new BigDecimal(currentTokens[10]));
            newOrder.setTotal(new BigDecimal(currentTokens[11]));
            newOrder.setOrderDate(orderDate);
            return newOrder;

        } catch (NumberFormatException e) {
            throw new FlooringMasteryPersistenceException("Bad data in file.");
        }
    }

    private String marshallOrder(Order singleOrder) {
        return singleOrder.getOrderNumber()
                + DELIMITER + singleOrder.getCustomerName()
                + DELIMITER + singleOrder.getState()
                + DELIMITER + singleOrder.getTaxRate()
                + DELIMITER + singleOrder.getProductType()
                + DELIMITER + singleOrder.getArea()
                + DELIMITER + singleOrder.getCostPerSquareFoot()
                + DELIMITER + singleOrder.getLaborCostPerSquareFoot()
                + DELIMITER + singleOrder.getMaterialCost()
                + DELIMITER + singleOrder.getLaborCost()
                + DELIMITER + singleOrder.getTax()
                + DELIMITER + singleOrder.getTotal();
    }

    private LocalDate fileNameToOrderDate(String fileName) {
        String dateString = fileName.replaceAll("[^0-9]", "");
        // Anything that isnt a 0-9 is replaced by ""
        LocalDate orderDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMddyyyy"));
        return orderDate;
    }

    private List<Order> getAllOrdersByDate(LocalDate orderDate) throws FlooringMasteryPersistenceException {
        Collection<Order> allOrders = this.orders.values();
        Stream<Order> allOrdersByDateStream = allOrders.stream().filter(p -> p.getOrderDate().equals(orderDate));
        List<Order> allOrdersByDateList = new ArrayList<>();
        allOrdersByDateList = allOrdersByDateStream.collect(Collectors.toList());
        return allOrdersByDateList;
    }

    private Set<LocalDate> getAllOrderDates() throws FlooringMasteryPersistenceException {
        Collection<Order> allOrders = this.orders.values();
        Set<LocalDate> allOrderDatesSet = new HashSet<>();
        for (Order allOrderDates : allOrders) {
            LocalDate orderDate = allOrderDates.getOrderDate();
            allOrderDatesSet.add(orderDate);
        }
        return allOrderDatesSet;

    }

    @Override
    public void loadOrderData() throws FlooringMasteryPersistenceException {
        Scanner textScanner;

        try {

            File dataFolder = new File(DATA_FOLDER);
            File[] fileList = dataFolder.listFiles();

            for (File file : fileList) {
                if (file.isFile()) {

                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    textScanner = new Scanner(bufferedReader);

                    while (textScanner.hasNextLine()) {

                        Order newOrder = new Order();

                        String currentLine = textScanner.nextLine();
                        String[] currentTokens = currentLine.split(DELIMITER);

                        if (currentTokens.length != 12) {
                            continue;
                        }

                        LocalDate orderDate = fileNameToOrderDate(file.getName());
                        newOrder = unmarshallOrder(currentTokens, orderDate);
                        this.addOrder(newOrder);

                    }
                    textScanner.close();

                }
            }

        } catch (FileNotFoundException fileNotThere) {
            throw new FlooringMasteryPersistenceException(DATA_FOLDER + " not found. -_-");
        }

    }

    @Override
    public void writeOrderData() throws FlooringMasteryPersistenceException {

        try {

            File dataFolder = new File(DATA_FOLDER);
            File[] fileList = dataFolder.listFiles();
            
            for (File file : fileList) {
                if (file.isFile()) {
                    file.delete();
                }
            }

            Set<LocalDate> allOrderDatesSet = getAllOrderDates();
            for (LocalDate allOrderDates : allOrderDatesSet) {

                List<Order> allOrdersByDateList = getAllOrdersByDate(allOrderDates);

                PrintWriter writer = new PrintWriter(new FileWriter(DATA_FOLDER + "/Orders_" + allOrderDates.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt"));

                for (Order singleOrder : allOrdersByDateList) {
                    String orderAsString = this.marshallOrder(singleOrder);
                    writer.println(orderAsString);
                    writer.flush();
                }
                writer.close();
            }
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not save data.", e);
        }

    }

    @Override
    public Order addOrder(Order newOrder) {
        this.orders.put(newOrder.getOrderNumber(), newOrder);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders() {
        Collection<Order> allOrders = this.orders.values();
        return new ArrayList<>(allOrders);
    }

    @Override
    public Order getOrder(int orderNumber) {
        return this.orders.get(orderNumber);
    }

    @Override
    public Order removeOrder(Order removedOrder) {
        this.orders.remove(removedOrder.getOrderNumber());
        return removedOrder;
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
