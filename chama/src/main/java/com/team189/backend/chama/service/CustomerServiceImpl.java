package com.team189.backend.chama.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team189.backend.chama.entity.Customers;
import com.team189.backend.chama.entity.Transaction;
import com.team189.backend.chama.exception.NonRollbackException;
import com.team189.backend.chama.utils.BeareTokenService;
import com.team189.backend.chama.utils.Utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author moha
 */
@Service
public class CustomerServiceImpl implements CustomerService {
     @Autowired
     CrudService crudeService;
     @Autowired
     Environment env;
     private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class.getSimpleName());
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");
    @Override
    public String changeCustomerPin(String msisdn, String pin, String newpin) {
         String q = "SELECT firstname FROM Customers where msisdn='"+msisdn+"'";
         List<Object> response = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1);
        if(response.isEmpty()){
             try {
                 throw new NonRollbackException("Customer record not found");
             } catch (NonRollbackException ex) {
             }
        }
          String fname= String.valueOf(response.get(0));
          String sq = "SELECT pin FROM Customers where msisdn='"+msisdn+"'";
          List<Object> rs = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1);
          if(rs.get(0).toString().equals(Utils.getSHA256(pin))){
          String sql = "UPDATE Customers SET pin='"+Utils.getSHA256(newpin)+"' WHERE msidn='"+msisdn+"'";
          crudeService.executeNativeQuery(sql, Collections.EMPTY_MAP);
          }
          return fname;
    }
    @Override
    public String fetchCustomers() {
        try{
        Map<String,String> map = new HashMap<>();
        String q = "SELECT * FROM Customers where status='active'";
        List<Customers> rs = crudeService.fetchWithHibernateQuery(q, Collections.EMPTY_MAP);
        for(Customers tx:rs){
            map.put("firstname", tx.getFirstname());
            map.put("surname",tx.getSurname());
            map.put("msisdn", tx.getMsisdn());
            map.put("status", tx.getStatus());
            map.put("idno",tx.getIdno());
            map.put("location",tx.getLocation());
        }
        return new ObjectMapper().writeValueAsString(map);
        }catch(JsonProcessingException | HibernateException e){
            e.getLocalizedMessage();
        }
        return null;
    }

    @Override
    public String AuthenticateCustomer(String msisdn, String pin) {
        try{
                String q = "SELECT pin FROM Customers where msisdn='"+msisdn+"'";
                List<Object> response = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1);
                if(String.valueOf(response.get(0)).equalsIgnoreCase(Utils.getSHA256(pin))){
                 String sql = "SELECT firstname FROM Customers where msisdn='"+msisdn+"'";
                 String msg = crudeService.fetchWithNativeQuery(q, Collections.EMPTY_MAP, 0, 1).get(0).toString();
                 return msg; 
                }else{
                    throw new NonRollbackException("Invalid Credentials");
                }
               }catch(Exception e){
                  return e.getLocalizedMessage();
               }   
    }

    @Override
    public void processSavingsDeposit(String msisdn, String amount, String ref) throws NonRollbackException {
        Date now = new Date(System.currentTimeMillis());
        try {
            String _timestamp = SDF.format(now);
            String requestBody = "{\n"
                    + "      \"BusinessShortCode\": \"" + env.getRequiredProperty("stk_push_shortcode") + "\",\n"
                    + "      \"Password\": \"" + getRequestPassword(env.getRequiredProperty("stk_push_shortcode"), env.getRequiredProperty("vision_fund_stk_push_passkey"), _timestamp) + "\",\n"
                    + "      \"Timestamp\": \"" + _timestamp + "\",\n"
                    + "      \"TransactionType\": \"CustomerSavings\",\n"
                    + "      \"Amount\": \"" + amount + "\",\n"
                    + "      \"PartyA\": \"" + msisdn + "\",\n"
                    + "      \"PartyB\": \"" + env.getRequiredProperty("stk_push_shortcode") + "\",\n"
                    + "      \"PhoneNumber\": \"" + msisdn + "\",\n"
                    + "      \"CallBackURL\": \"" + env.getRequiredProperty("callback_url") + "\",\n"
                    + "      \"AccountReference\": \"" + ref + "\",\n"
                    + "      \"TransactionDesc\": \"STK Push\"\n"
                    + "    }";
            LOGGER.info("REQBODY={}", msisdn, requestBody);
            String response = Postpayload(env.getRequiredProperty("vision_fund_stk_safaricom_api_endpoint"), requestBody, "Bearer " + BeareTokenService.token);
            LOGGER.info("response={}", msisdn, response);
             JSONObject jsonObjectResponse = new JSONObject(response);
            String MerchantRequestID = jsonObjectResponse.get("MerchantRequestID").toString();
            String CheckoutRequestID = jsonObjectResponse.get("CheckoutRequestID").toString();
            String ResponseCode = jsonObjectResponse.get("ResponseCode").toString();
            LOGGER.info("Msisdn={}|C2B STK PUSH response params:MerchantRequestID={},CheckoutRequestID={},ResponseCode={}", msisdn, MerchantRequestID, CheckoutRequestID, ResponseCode);
            if (ResponseCode.contentEquals("0")) {
                Transaction trx = new Transaction();
                trx.setTrx_type("STK_PUSH");
                trx.setSender_party(msisdn);
                trx.setReceiver_party(env.getRequiredProperty("stk_push_shortcode"));
                trx.setCompleted_date(now);
                trx.setBillrefnumber(ref);
                trx.setProcessing_status("Completed");
                trx.setAmount(amount);
                trx.setMsisdn(msisdn);
                trx.setRequest_id(MerchantRequestID);
                trx.setCheckout_id(CheckoutRequestID);
                crudeService.save(trx);
            }
        } catch (Exception e) {
            throw new NonRollbackException(e.getMessage());
        }
    }
       private String Postpayload(String endPointURL, String requestBody, String authKey) throws MalformedURLException, IOException {
        URL url = new URL(endPointURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/json;charset=UTF-8");
        connection.setRequestProperty("Accept",
                "application/json;charset=UTF-8");
        connection.setRequestProperty("Authorization",
                authKey);
        connection.setRequestProperty("Content-Length", ""
                + Integer.toString(requestBody.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        try (DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());) {
            wr.writeBytes(requestBody);
            wr.flush();
        }
        StringBuilder response = new StringBuilder();
        try (InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }
         private static String getRequestPassword(String shortcode, String passkey, String timestamp) {
        return Base64.getEncoder().encodeToString(new StringBuilder().append(shortcode).append(passkey).append(timestamp).toString().getBytes());
    }
}
    
    

