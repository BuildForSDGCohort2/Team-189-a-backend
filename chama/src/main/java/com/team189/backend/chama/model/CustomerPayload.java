package com.team189.backend.chama.model;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"dob",
"firstname",
"surname",
"gender",
"idno",
"location",
"marital_status",
"status",
"msisdn"
})
public class CustomerPayload {

@JsonProperty("dob")
private String dob;
@JsonProperty("firstname")
private String firstname;
@JsonProperty("surname")
private String surname;
@JsonProperty("gender")
private String gender;
@JsonProperty("idno")
private String idno;
@JsonProperty("location")
private String location;
@JsonProperty("marital_status")
private String maritalStatus;
@JsonProperty("status")
private String status;
@JsonProperty("msisdn")
private String msisdn;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("dob")
public String getDob() {
return dob;
}

@JsonProperty("dob")
public void setDob(String dob) {
this.dob = dob;
}

@JsonProperty("firstname")
public String getFirstname() {
return firstname;
}

@JsonProperty("firstname")
public void setFirstname(String firstname) {
this.firstname = firstname;
}

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


@JsonProperty("gender")
public String getGender() {
return gender;
}

@JsonProperty("gender")
public void setGender(String gender) {
this.gender = gender;
}

@JsonProperty("idno")
public String getIdno() {
return idno;
}

@JsonProperty("idno")
public void setIdno(String idno) {
this.idno = idno;
}

@JsonProperty("location")
public String getLocation() {
return location;
}

@JsonProperty("location")
public void setLocation(String location) {
this.location = location;
}

@JsonProperty("marital_status")
public String getMaritalStatus() {
return maritalStatus;
}

@JsonProperty("marital_status")
public void setMaritalStatus(String maritalStatus) {
this.maritalStatus = maritalStatus;
}

@JsonProperty("status")
public String getStatus() {
return status;
}

@JsonProperty("status")
public void setStatus(String status) {
this.status = status;
}

@JsonProperty("msisdn")
public String getMsisdn() {
return msisdn;
}

@JsonProperty("msisdn")
public void setMsisdn(String msisdn) {
this.msisdn = msisdn;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
