/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.exception;

/**
 *
 * @author moha
 */
public class NonRollbackException extends Exception {

    public NonRollbackException() {
    }

    public NonRollbackException(String message) {
        super(message);
    }

}