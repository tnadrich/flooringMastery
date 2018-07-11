/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author capta
 */
public class Order {

    private int orderNumber;
    private LocalDate orderDate;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    
    public Order() {
        
    }
    
    public Order(int orderNumber, LocalDate orderDate, String customerName, String state, 
            BigDecimal taxRate, String productType, BigDecimal area, 
            BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot,
            BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax,
            BigDecimal total) {
        
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate.setScale(2, RoundingMode.HALF_UP);
        this.productType = productType;
        this.area = area.setScale(2, RoundingMode.HALF_UP);
        this.costPerSquareFoot = costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        this.laborCostPerSquareFoot = laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
        this.materialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
        this.laborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
        this.tax = tax.setScale(2, RoundingMode.HALF_UP);
        this.total = total.setScale(2, RoundingMode.HALF_UP);
        
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public String getOrderDateString() {
        String orderDateString = orderDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return orderDateString;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate.setScale(2, RoundingMode.HALF_UP);
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.orderNumber;
        hash = 29 * hash + Objects.hashCode(this.orderDate);
        hash = 29 * hash + Objects.hashCode(this.customerName);
        hash = 29 * hash + Objects.hashCode(this.state);
        hash = 29 * hash + Objects.hashCode(this.taxRate);
        hash = 29 * hash + Objects.hashCode(this.productType);
        hash = 29 * hash + Objects.hashCode(this.area);
        hash = 29 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 29 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        hash = 29 * hash + Objects.hashCode(this.materialCost);
        hash = 29 * hash + Objects.hashCode(this.laborCost);
        hash = 29 * hash + Objects.hashCode(this.tax);
        hash = 29 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }

}
