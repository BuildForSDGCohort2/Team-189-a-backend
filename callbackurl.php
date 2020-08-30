<?php
/**
* Coded with love
* 
*/
$servername ="localhost";
$username ="team189";
$password ="team189";
$dbname   ="team189";
header("Content-Type:application/json");
if (!$request=file_get_contents('php://input'))
{
echo "Invalid input";
exit();
}
$con = mysqli_connect($servername, $username, $password, $dbname);
if (!$con) 
{
die("Connection failed: " . mysqli_connect_error());
}
$request=file_get_contents('php://input');
	
//Put the json string that we received from Safaricom to an array
$array = json_decode($request, true);


     $mpesa_receipt  = $array['Body']['stkCallback']['CallbackMetadata']['Item'][1]['Value'];
     $date           = $array['Body']['stkCallback']['CallbackMetadata']['Item'][3]['Value'];
     $msisdn         = $array['Body']['stkCallback']['CallbackMetadata']['Item'][4]['Value'];
     $amount         = $array['Body']['stkCallback']['CallbackMetadata']['Item'][0]['Value'];
     $merchant_id    = $array['Body']['stkCallback']['MerchantRequestID'];
     $checkout_id    = $array['Body']['stkCallback']['CheckoutRequestID'];
     $amount         = $array['Body']['stkCallback']['CallbackMetadata']['Item'][0]['Value'];
  
 
$sql="INSERT INTO TRANSACTIONS(AMOUNT,MSISDN,BILL_REFERNCE_NUMBER,PROCESSING_STATUS,TRANSACTION_TYPE,SENDER_PARTY,RECEIVER_PARTY,MERCHANT_REQUEST_ID,CHECKOUT_REQUEST_ID)  
VALUES ( '$amount','$msisdn','$mpesa_receipt','COMPLETED','SAVINGS','$msisdn','174379','$merchant_id','$checkout_id')";
 
if (!mysqli_query($con,$sql)) 
 
{ 
echo mysqli_error($con); 
} 
 
 
else 
{ 
echo '{"ResultCode":0,"ResultDesc":"Confirmation received successfully"}';
}
 


