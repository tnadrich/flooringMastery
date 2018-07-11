/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author capta
 */
public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public void displayMode(boolean isProd) {
        if (isProd == true) {
            io.print("**************************************************************");
            io.print("*-------------------------Prod Mode--------------------------*");
            io.print("**************************************************************");
        } else {
            io.print("**************************************************************");
            io.print("*-----------------------Training Mode------------------------*");
            io.print("**************************************************************");
        }
    }

    public void displayProgramBanner() {
        io.print("\n**************************************************************\n"
                + "*-------------------------SWG  Corp.-------------------------*\n"
                + "*----------------------FLOORING MASTERY----------------------*\n"
                + "**************************************************************\n"
                + "*                                                            *\n"
                + "*    T                                    \\`.    T           *\n"
                + "*    |    T     .--------------.___________) \\   |      T    *\n"
                + "*    !    |     |//////////////|___________[ ]   !   T  |    *\n"
                + "*         !     `--------------'           ) (       |  !    *\n"
                + "*                                      mn  '-'       !       *\n"
                + "*                                                            *\n"
                + "**************************************************************");
    }

    public int printMenuAndGetSelection() {
        io.print("\n**************************************************************");
        io.print("*<<Flooring Mastery>>                                        *");
        io.print("*1. Display Orders                                           *");
        io.print("*2. Add an Order                                             *");
        io.print("*3. Edit an Order                                            *");
        io.print("*4. Remove an Order                                          *");
        io.print("*5. Save Current Work                                        *");
        io.print("*6. Quit                                                     *");
        io.print("*                                                            *");
        io.print("**************************************************************");

        return io.readInt("\nPlease select from the above choices.", 1, 6);
    }

    public void displayOrderListByDate(List<Order> allOrdersByDateList) {
        io.print("\n***************************************************************");
        io.print("*-------------------------Orders------------------------------*");
        io.print("***************************************************************");
        Stream<Order> allOrdersByDateStream = allOrdersByDateList.stream();
        allOrdersByDateStream.forEach(s -> io.print("\nOrder Number: " + s.getOrderNumber()
                + "\n" + "Order Date: " + s.getOrderDateString()
                + "\n" + "Customer Name: " + s.getCustomerName()
                + "\n" + "State: " + s.getState()
                + "\n" + "Tax Rate: " + s.getTaxRate() + "%"
                + "\n" + "Product Type: " + s.getProductType()
                + "\n" + "Area : " + s.getArea() + " Square Feet"
                + "\n" + "Cost per Square Foot: " + "$" + s.getCostPerSquareFoot()
                + "\n" + "Labor Cost per Square Foot: " + "$" + s.getLaborCostPerSquareFoot()
                + "\n" + "Material Cost: " + "$" + s.getMaterialCost()
                + "\n" + "Labor Cost: " + "$" + s.getLaborCost()
                + "\n" + "Tax: " + "$" + s.getTax()
                + "\n" + "Total Cost: " + "$" + s.getTotal()));
    }

    public Order displayRemovedOrder(Order removedOrder) {
        io.print("\n**************************************************************");
        io.print("*------------------------Remove Order------------------------*");
        io.print("**************************************************************");
        displaySingleOrder(removedOrder);

        return removedOrder;
    }

    public void displayEntryNotRemoved() {
        io.print("\n**************************************************************");
        io.print("*---------------------Order Not Removed----------------------*");
        io.print("**************************************************************");
    }

    public void displaySingleOrder(Order singleOrder) {
        io.print("Order Number: " + singleOrder.getOrderNumber()
                + "\n" + "Order Date: " + singleOrder.getOrderDateString()
                + "\n" + "Customer Name: " + singleOrder.getCustomerName()
                + "\n" + "State: " + singleOrder.getState()
                + "\n" + "Tax Rate: " + singleOrder.getTaxRate() + "%"
                + "\n" + "Product Type: " + singleOrder.getProductType()
                + "\n" + "Area : " + singleOrder.getArea() + " Square Feet"
                + "\n" + "Cost per Square Foot: " + "$" + singleOrder.getCostPerSquareFoot()
                + "\n" + "Labor Cost per Square Foot: " + "$" + singleOrder.getLaborCostPerSquareFoot()
                + "\n" + "Material Cost: " + "$" + singleOrder.getMaterialCost()
                + "\n" + "Labor Cost: " + "$" + singleOrder.getLaborCost()
                + "\n" + "Tax: " + "$" + singleOrder.getTax()
                + "\n" + "Total Cost: " + "$" + singleOrder.getTotal());
    }

    public void displayEditOrderBanner() {
        io.print("\n**************************************************************");
        io.print("*-------------------------Edit Order-------------------------*");
        io.print("**************************************************************");
    }

    public Order displayAndGetEditedOrder(Order oldOrder,
            List<Tax> allTaxesList,
            List<Product> allProductsList) {
        displaySingleOrder(oldOrder);
        io.print("\nEnter in new data to replace the old. Otherwise, hit enter to skip field.");

        Order editedOrder = new Order();
        editedOrder.setOrderNumber(oldOrder.getOrderNumber());
        editedOrder.setOrderDate(oldOrder.getOrderDate());
        editedOrder.setCustomerName(oldOrder.getCustomerName());
        editedOrder.setState(oldOrder.getState());
        editedOrder.setTaxRate(oldOrder.getTaxRate());
        editedOrder.setProductType(oldOrder.getProductType());
        editedOrder.setArea(oldOrder.getArea());
        editedOrder.setCostPerSquareFoot(oldOrder.getCostPerSquareFoot());
        editedOrder.setLaborCostPerSquareFoot(oldOrder.getLaborCostPerSquareFoot());
        editedOrder.setMaterialCost(oldOrder.getMaterialCost());
        editedOrder.setLaborCost(oldOrder.getLaborCost());
        editedOrder.setTax(oldOrder.getTax());
        editedOrder.setTotal(oldOrder.getTotal());

        LocalDate editedDate = getDateWithExisting(oldOrder.getOrderDate());
        editedOrder.setOrderDate(editedDate);

        String editedName = io.readString("\nPlease enter in a name " + "(" + oldOrder.getCustomerName() + "):");
        if (editedName.contains(",")) {
            editedName = editedName.replace(",", "");
        }
        if (!editedName.equals("")) {
            editedOrder.setCustomerName(editedName);
        }

        displayTaxList(allTaxesList);
        String editedState = io.readString("\nPlease enter in a state " + "(" + oldOrder.getState() + "):").toUpperCase();
        List<String> allStatesListString = allTaxesList.stream().map(tax -> tax.getState()).collect(Collectors.toList());
        while (!allStatesListString.contains(editedState)) {
            if (editedState.equals("")) {
                break;
            }
            io.print("\n=== ERROR ===");
            editedState = io.readString("Please enter in a valid state.").toUpperCase();
        }
        if (!editedState.equals("")) {
            editedOrder.setState(editedState);
        }

        displayProductList(allProductsList);
        String editedProductType = io.readString("\nPlease enter in a product type " + "(" + oldOrder.getProductType() + "):");
        List<String> allProductTypeString = allProductsList.stream().map(product -> product.getProductType()).collect(Collectors.toList());
        while (!allProductTypeString.contains(editedProductType)) {
            if (editedProductType.equals("")) {
                break;
            }
            io.print("\n=== ERROR ===");
            editedProductType = io.readString("Please enter in a valid product type.");
        }
        if (!editedProductType.equals("")) {
            editedOrder.setProductType(editedProductType);
        }

        getAreaWithExisting(oldOrder, editedOrder);

        return editedOrder;
    }

    public Order editOrderCorrect(Order oldOrder,
            Order editedOrder) {
        boolean processEdit = false;
        String isCorrect = io.readString("\nHere is your updated order. Would you like to keep changes (Y/N)?").toUpperCase();

        while (!isCorrect.equals("Y") && !isCorrect.equals("N")) {
            io.print("\n=== ERROR ===");
            isCorrect = io.readString("Please enter in a valid choice.").toUpperCase();
        }
        if (isCorrect.equals("N")) {
            displayEditOrderNotEdited();
            processEdit = false;
        }
        if (isCorrect.equals("Y")) {
            displayEditOrderSuccess();
            processEdit = true;
        }

        if (!processEdit) {
            editedOrder = oldOrder;
        }
        return editedOrder;
    }

    public LocalDate getDate() {
        boolean hasErrors = false;
        LocalDate orderDate = null;
        do {
            String dateInput = io.readString("\nPlease enter in the date (MM/DD/YYYY).");
            try {
                orderDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                hasErrors = false;

            } catch (DateTimeParseException e) {
                hasErrors = true;
                displayInvalidDate();
            }
        } while (hasErrors);
        return orderDate;
    }

    public LocalDate getDateWithExisting(LocalDate existingDate) {
        boolean hasErrors = false;
        LocalDate editedDate = null;
        do {
            String dateInput = io.readString("\nPlease enter in the date (" + existingDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + "):");
            if (dateInput.equals("")) {
                editedDate = existingDate;
                break;
            }
            try {
                editedDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                hasErrors = false;

            } catch (DateTimeParseException e) {
                hasErrors = true;
                displayInvalidDate();
            }
        } while (hasErrors);
        return editedDate;
    }

    public BigDecimal getArea() {
        double area;
        do {
            area = io.readDouble("\nPlease enter in an approximate area in square feet.");
            if (area <= 0) {
                io.print("\n=== ERROR ===");
                io.print("Invalid area entered. Please enter a valid area.");
            }
        } while (area <= 0);
        BigDecimal areaBD = new BigDecimal(area).setScale(2, RoundingMode.HALF_UP);
        return areaBD;
    }

    public void getAreaWithExisting(Order oldOrder,
            Order editedOrder) {
        double editedArea;
        String editedAreaString = io.readString("\nPlease enter in an approximate area in square feet " + "(" + oldOrder.getArea() + "):");
        if (!editedAreaString.equals("")) {
            do {
                editedArea = Double.parseDouble(editedAreaString);
                if (editedArea <= 0) {
                    io.print("\n=== ERROR ===");
                    io.print("Invalid area entered. Please enter a valid area.");
                }
            } while (editedArea <= 0);
            BigDecimal editedAreaBD = new BigDecimal(editedArea).setScale(2, RoundingMode.HALF_UP);
            editedOrder.setArea(editedAreaBD);
        }
    }

    public void displayInvalidDate() {
        io.print("\n=== ERROR ===");
        io.print("Invalid date entered. Please enter a valid date.");
    }

    public void displayExitBanner() {
        io.print("\n**************************************************************");
        io.print("*-----------------------Good Bye!!! :)-----------------------*");
        io.print("**************************************************************");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("\n=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayAddOrderBanner() {
        io.print("\n**************************************************************");
        io.print("*--------------------------ADD Order-------------------------*");
        io.print("**************************************************************");
    }

    public Order getNewOrderInfo(List<Tax> allTaxesList,
            List<Product> allProductsList) {
        LocalDate orderDate = getDate();
        String customerName = io.readString("\nPlease enter in a name.");
        if (customerName.contains(",")) {
            customerName = customerName.replace(",", " ");
        }

        displayTaxList(allTaxesList);
        String state = io.readString("\nPlease enter in a state.").toUpperCase();
        List<String> allStatesListString = allTaxesList.stream().map(tax -> tax.getState()).collect(Collectors.toList());
        while (!allStatesListString.contains(state)) {
            io.print("\n=== ERROR ===");
            state = io.readString("Please enter in a valid state.").toUpperCase();
        }

        displayProductList(allProductsList);
        String productType = io.readString("\nPlease enter in a product type.");
        List<String> allProductTypeString = allProductsList.stream().map(product -> product.getProductType()).collect(Collectors.toList());
        while (!allProductTypeString.contains(productType)) {
            io.print("\n=== ERROR ===");
            productType = io.readString("Please enter in a valid product type.");
        }

        BigDecimal area = getArea();

        Order newOrder = new Order();
        newOrder.setOrderDate(orderDate);
        newOrder.setCustomerName(customerName);
        newOrder.setState(state);
        newOrder.setProductType(productType);
        newOrder.setArea(area);

        return newOrder;
    }

    public Order displayUserOrderEntry(Order generatedOrder) {

        io.print("\n**************************************************************");
        io.print("*--------------------------User Entry------------------------*");
        io.print("**************************************************************");
        io.print("Order Date: " + generatedOrder.getOrderDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                + "\nCustomer Name: " + generatedOrder.getCustomerName()
                + "\nState: " + generatedOrder.getState()
                + "\nTax Rate: " + generatedOrder.getTaxRate()
                + "\nProduct Type: " + generatedOrder.getProductType()
                + "\nArea (Square Feet): " + generatedOrder.getArea()
                + "\nCost per Square Foot: " + generatedOrder.getCostPerSquareFoot()
                + "\nLabor cost per Square Foor: " + generatedOrder.getLaborCostPerSquareFoot()
                + "\nMaterial Cost: " + generatedOrder.getMaterialCost()
                + "\nLabor Cost: " + generatedOrder.getLaborCost()
                + "\nTax: " + generatedOrder.getTax()
                + "\nTotal: " + generatedOrder.getTotal());

        return generatedOrder;

    }

    public void displayTaxList(List<Tax> allTaxesList) {
        io.print("\n****************");
        io.print("State | Tax Rate");
        io.print("****************");
        Stream<Tax> allTaxesStream = allTaxesList.stream();
        allTaxesStream.forEach(s -> io.print(s.getState() + "....." + s.getTaxRate()));

    }

    public void displayProductList(List<Product> allProductsList) {
        io.print("\n****************************************************************");
        io.print("Product Type | Cost per Square Foot | Labor Cost per Square Foot");
        io.print("****************************************************************");
        Stream<Product> allProductsStream = allProductsList.stream();
        allProductsStream.forEach(s -> io.print(s.getProductType() + "  |  $" + s.getCostPerSquareFoot() + "  |  $" + s.getLaborCostPerSquareFoot()));
    }

    public void displayCreateOrderSuccessBanner() {
        io.print("\n**************************************************************");
        io.print("*-----------------Order successfully created.----------------*");
        io.print("**************************************************************");
    }

    public void displayRemoveOrderSuccessBanner() {
        io.print("\n**************************************************************");
        io.print("*-----------------Order successfully removed.----------------*");
        io.print("**************************************************************");
    }

    public void displaySuccessfulSave(boolean isProd) {
        if (isProd == true) {
            io.print("\n**************************************************************");
            io.print("*-------------------Save was Successful :)-------------------*");
            io.print("**************************************************************");
        } else {
            io.print("\n**************************************************************");
            io.print("*---------------Cannot save in TRAINING Mode-----------------*");
            io.print("**************************************************************");
        }
    }

    public String displaySave() {
        String save = io.readString("\nWould you like to save (Y/N)?").toUpperCase();

        while (!save.equals("Y") && !save.equals("N")) {
            io.print("\n=== ERROR ===");
            save = io.readString("Please enter in a valid choice.").toUpperCase();
        }
        if (save.equals("N")) {
            io.print("\n**************************************************************");
            io.print("*----------------------Entry not saved.----------------------*");
            io.print("**************************************************************");
        }
        return save;
    }

    public int getOrderNumber() {
        int orderNumber = io.readInt("\nPlease enter in an Order Number.");
        return orderNumber;
    }

    public void displayListOrdersBanner() {
        io.print("\n**************************************************************");
        io.print("*----------------------Display Orders------------------------*");
        io.print("**************************************************************");
    }

    public void displayRemoveOrderBanner() {
        io.print("\n**************************************************************");
        io.print("*-----------------------Remove Orders------------------------*");
        io.print("**************************************************************");
    }

    public void displayEditOrderSuccess() {
        io.print("\n**************************************************************");
        io.print("*-------------------Order has been edited.-------------------*");
        io.print("**************************************************************");
    }

    public void displayEditOrderNotEdited() {
        io.print("\n**************************************************************");
        io.print("*----------------------Entry not edited.---------------------*");
        io.print("**************************************************************");
    }

    public void displayEntryDiscarded() {
        io.print("\n**************************************************************");
        io.print("*-----------------------Entry Discarded----------------------*");
        io.print("**************************************************************");
    }

    public boolean userChoice() {
        String isCorrect = io.readString("\nDo you wish to proceed (Y/N)?").toUpperCase();
        boolean userChoice;

        while (!isCorrect.equals("Y") && !isCorrect.equals("N")) {
            io.print("\n=== ERROR ===");
            isCorrect = io.readString("Please enter in a valid choice.").toUpperCase();
        }
        if (isCorrect.equals("N")) {
            userChoice = false;
        } else {
            userChoice = true;
        }
        return userChoice;
    }
}
