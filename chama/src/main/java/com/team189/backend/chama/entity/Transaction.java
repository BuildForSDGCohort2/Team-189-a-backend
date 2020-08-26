/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team189.backend.chama.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author moha
 */
@Entity
@Table(name = "TRANSACTION")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "PROCESSING_STATUS")
    private String processing_status;
    @Column(name = "TRANSACTION_TYPE")
    private String trx_type;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "MSISDN")
    private String msisdn;
    @Column(name = "MERCHANT_REQUEST_ID")
    private String request_id;
     @Column(name = "CHECKOUT_REQUEST_ID")
    private String checkout_id;
    @Column(name = "COMPLETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completed_date;
    @Column(name = "RECEIVER_PARTY")
    private String receiver_party;
    @Column(name = "SENDER_PARTY")
    private String sender_party;
    @Column(name = "BILL_REFERNCE_NUMBER")
    private String billrefnumber;
    
     public Transaction(){
          
     }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProcessing_status() {
        return processing_status;
    }

    public void setProcessing_status(String processing_status) {
        this.processing_status = processing_status;
    }

    public String getTrx_type() {
        return trx_type;
    }

    public void setTrx_type(String trx_type) {
        this.trx_type = trx_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
     public String getCheckout_id() {
        return checkout_id;
    }

    public void setCheckout_id(String checkout_id) {
        this.checkout_id = checkout_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Date getCompleted_date() {
        return completed_date;
    }

    public void setCompleted_date(Date completed_date) {
        this.completed_date = completed_date;
    }

    public String getReceiver_party() {
        return receiver_party;
    }

    public void setReceiver_party(String receiver_party) {
        this.receiver_party = receiver_party;
    }

    public String getSender_party() {
        return sender_party;
    }

    public void setSender_party(String sender_party) {
        this.sender_party = sender_party;
    }

    public String getBillrefnumber() {
        return billrefnumber;
    }

    public void setBillrefnumber(String billrefnumber) {
        this.billrefnumber = billrefnumber;
    }
     
}