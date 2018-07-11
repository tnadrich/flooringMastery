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
public class FlooringMasteryNoOrderExistsByDateException extends Exception {

    public FlooringMasteryNoOrderExistsByDateException(String message) {
        super(message);
    }

    public FlooringMasteryNoOrderExistsByDateException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
