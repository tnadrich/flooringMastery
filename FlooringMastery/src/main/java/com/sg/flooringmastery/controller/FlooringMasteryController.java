/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.service.FlooringMasteryNoOrderExistsByOrderNumberAndByDateException;
import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringMasteryNoOrderExistsByDateException;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author capta
 */
public class FlooringMasteryController {

    private FlooringMasteryServiceLayer service;
    private FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryServiceLayer service,
            FlooringMasteryView view) {

        this.service = service;
        this.view = view;

    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        trainingOrProd();
        viewProgramBanner();
        try {
            service.load();
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listAllOrdersByDate();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveWork();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void viewProgramBanner() {
        view.displayProgramBanner();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void listAllOrdersByDate() {
        try {
            view.displayListOrdersBanner();
            LocalDate orderDate = view.getDate();
            List<Order> allOrdersByDateList = service.getAllOrdersByDate(orderDate);
            view.displayOrderListByDate(allOrdersByDateList);
        } catch (FlooringMasteryNoOrderExistsByDateException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void createOrder() {
        view.displayAddOrderBanner();
        List<Tax> allTaxesList = listAllTaxes();
        List<Product> allProductsList = listAllProducts();
        Order newOrder = view.getNewOrderInfo(allTaxesList, allProductsList);
        Order generatedOrder = service.generateOrderData(newOrder);
        Order createdOrder = view.displayUserOrderEntry(generatedOrder);
        boolean userEntry = view.userChoice();
        if (userEntry) {
            service.createOrder(createdOrder);
            view.displayCreateOrderSuccessBanner();
        } else {
            view.displayEntryDiscarded();
        }
    }

    private void saveWork() throws FlooringMasteryPersistenceException {
        service.save();
        boolean isProd = service.bootConfig();
        view.displaySuccessfulSave(isProd);
    }

    private void removeOrder() {
        try {
            view.displayRemoveOrderBanner();
            int removeOrderNumber = view.getOrderNumber();
            LocalDate removeLocalDate = view.getDate();
            Order removedOrder = service.getOrderByDateAndOrderNumber(removeOrderNumber, removeLocalDate);
            removedOrder = view.displayRemovedOrder(removedOrder);
            boolean userEntry = view.userChoice();
            if (userEntry) {
                service.removeOrder(removedOrder);
                view.displayRemoveOrderSuccessBanner();
            } else {
                view.displayEntryNotRemoved();
            }
        } catch (FlooringMasteryNoOrderExistsByOrderNumberAndByDateException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void editOrder() {
        try {
            List<Tax> allTaxesList = listAllTaxes();
            List<Product> allProductsList = listAllProducts();
            int oldOrderNumber = view.getOrderNumber();
            LocalDate oldLocalDate = view.getDate();
            Order oldOrder = service.getOrderByDateAndOrderNumber(oldOrderNumber, oldLocalDate);
            Order editedOrder = view.displayAndGetEditedOrder(oldOrder, allTaxesList, allProductsList);
            Order generatedOrder = service.generateOrderData(editedOrder);
            view.displaySingleOrder(generatedOrder);
            editedOrder = view.editOrderCorrect(oldOrder, editedOrder);
            service.editOrder(editedOrder);
        } catch (FlooringMasteryNoOrderExistsByOrderNumberAndByDateException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() throws FlooringMasteryPersistenceException {
        String save = view.displaySave();
        if (save.equals("Y")) {
            service.save();
            boolean isProd = service.bootConfig();
            view.displaySuccessfulSave(isProd);
        }
        view.displayExitBanner();
    }

    private List<Tax> listAllTaxes() {
        List<Tax> allTaxesList = service.getAllTaxes();
        return allTaxesList;
    }

    private List<Product> listAllProducts() {
        List<Product> allProductsList = service.getAllProducts();
        return allProductsList;
    }

    private void trainingOrProd() {
        boolean isProd = service.bootConfig();
        view.displayMode(isProd);
    }
}
