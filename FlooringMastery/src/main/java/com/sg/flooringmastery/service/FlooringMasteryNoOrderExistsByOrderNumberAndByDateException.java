/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author capta
 */
public class FlooringMasteryNoOrderExistsByOrderNumberAndByDateException extends Exception {

    public FlooringMasteryNoOrderExistsByOrderNumberAndByDateException(String message) {
        super(message);
    }

    public FlooringMasteryNoOrderExistsByOrderNumberAndByDateException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
