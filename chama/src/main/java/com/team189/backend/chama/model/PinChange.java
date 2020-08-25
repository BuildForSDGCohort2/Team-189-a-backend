package com.team189.backend.chama.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author moha
 */
public class PinChange implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("msisdn")
    private String msisdn;
    @JsonProperty("pin")
    private String pin;
    @JsonProperty("newpin")
    private String newpin;

    @Override
    public String toString() {
        return "CustomerPinChangePayload{" + "msisdn=" + msisdn + ", pin=" + pin + ", newpin=" + newpin + '}';
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNewpin() {
        return newpin;
    }

    public void setNewpin(String newpin) {
        this.newpin = newpin;
    }

    
}
