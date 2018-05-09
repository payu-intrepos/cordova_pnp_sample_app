package com.payu.plugin.pnp;


import android.content.Intent;
import android.util.Log;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * This class echoes a string called from JavaScript.
 */
public class PayUMoneyPNPPlugin extends CordovaPlugin {

    private static final String TAG = "PayUMoneyPNPPlugin" ;

    private HashMap<String, CallbackContext> contextHashMap;
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        contextHashMap = new HashMap<>();

    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals( "showPaymentView")) {
            final JSONObject paymentJsonObject = args.getJSONObject(0);
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    launchPayUMoneyFlow(paymentJsonObject, callbackContext);
                }
            });

            return true;
        }
        return false;
    }

    private void setPayuMoneyConfig( final boolean exitConfirmationDisabled, final String doneBtnText, final String screenTitle ){
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.disableExitConfirmation( exitConfirmationDisabled );
        payUmoneyConfig.setDoneButtonText( doneBtnText );
        payUmoneyConfig.setPayUmoneyActivityTitle( screenTitle );
    }

    private void launchPayUMoneyFlow( final JSONObject jsonObject , final CallbackContext callbackContext){

        Log.d(TAG, "launchPayUMoneyFlow(): "+jsonObject );

        setPayuMoneyConfig( false, "Done", "MyTitle" );
        
        try {
            final double amount = jsonObject.getDouble( "amount");
            final String txnId = System.currentTimeMillis() + "";
            Log.d(TAG, "launchPayUMoneyFlow(): txnId = "+txnId );

            final String phone = jsonObject.getString( "phone");
            final String email = jsonObject.getString( "email");
            final String firstName = jsonObject.getString( "firstname");

            final String productName = jsonObject.getString( "productInfo");
            final String sUrl = jsonObject.getString( "surl");
            final String fUrl = jsonObject.getString( "furl");

            // 0 == production, 1 == sandbox
            /*final int environment = jsonObject.getInt( "environment" );
            final boolean isDebug = environment == 0 ? false : true ;*/

            // Use only test as of now... as we have hardcoded salt
            final boolean isDebug = true;

            final String merchantKey = jsonObject.getString( "key");
            final String merchant_ID = jsonObject.getString( "merchantid");
            final String hash = jsonObject.getString( "hashValue");

            // final int style = jsonObject.getInt( "style" ); // TODO
            final int style = com.payumoney.sdkui.R.style.AppTheme_default;

            final String udf1 = jsonObject.getString( "udf1");
            final String udf2 = jsonObject.getString( "udf2");
            final String udf3 = jsonObject.getString( "udf3");
            final String udf4 = jsonObject.getString( "udf4");
            final String udf5 = jsonObject.getString( "udf5");

            final String udf6 = jsonObject.getString( "udf6");
            final String udf7 = jsonObject.getString( "udf7");
            final String udf8 = jsonObject.getString( "udf8");
            final String udf9 = jsonObject.getString( "udf9");
            final String udf10 = jsonObject.getString( "udf10");

            Log.i(TAG, "launchPayUMoneyFlow(): " + amount+", "+txnId+", "+ phone+", "+ email+", "
                    +firstName+", "+ productName+", "+ sUrl+", "+fUrl+", "+ isDebug+", "+ merchantKey+", "+ merchant_ID+", "+hash);

            launchPayUMoneyFlow( amount, txnId, phone, email, firstName, productName, sUrl, fUrl, isDebug, merchantKey, merchant_ID, hash,
                                            style, udf1, udf2, udf3, udf4, udf5, udf6, udf7, udf8, udf9, udf10, false, callbackContext );
        }catch ( Exception e){
            e.printStackTrace();
            callbackContext.error( e.getMessage() );
        }

    }



    private void launchPayUMoneyFlow(final double amount, final String txnId, final String phone, final String email, final String firstName,
                                     final String productName, final String sUrl, final String fUrl, final boolean isDebug,
                                     final String merchantKey, final String merchant_ID, String hash, final int style,
                                     final String udf1, final String udf2, final String udf3, final String udf4, final String udf5,
                                     final String udf6, final String udf7, final String udf8, final String udf9, final String udf10,
                                     final boolean isOverrideResultScreen, final CallbackContext callbackContext) {

        contextHashMap.put( ""+PayUmoneyFlowManager.REQUEST_CODE_PAYMENT, callbackContext );

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl( sUrl )
                .setfUrl( fUrl )
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug( isDebug )
                .setKey( merchantKey )
                .setMerchantId( merchant_ID );

        try {
            PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();

            hash = calculateServerSideHashAndInitiatePayment1( amount, txnId, email, firstName, productName, merchantKey, merchant_ID);
            Log.v(TAG, "launchPayUMoneyFlow(): hash = "+hash );
            paymentParam.setMerchantHash( hash );

            if (style != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, cordova.getActivity(), style, isOverrideResultScreen);
            }
        } catch ( Exception e ) {
            // oops some exception.
            e.printStackTrace();
            callbackContext.error( e.getMessage() );
        }


    }

    private String calculateServerSideHashAndInitiatePayment1( final double amount, final String txnId, final String email, final String firstName,
                                                               final String productName,
                                                               final String merchantKey, final String merchant_ID ) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( merchantKey + "|" );
        stringBuilder.append( txnId + "|" );
        stringBuilder.append( amount + "|" );
        stringBuilder.append( productName + "|" );
        stringBuilder.append( firstName + "|" );
        stringBuilder.append( email + "|" );
        stringBuilder.append("||||||||||");
        stringBuilder.append( "qauKbEAJ");
        String hash = hashCal(stringBuilder.toString());

        return hash;
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(): request code " + requestCode + " resultcode " + resultCode);
        if( requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT ) {
            final CallbackContext callbackContext = contextHashMap.get( ""+PayUmoneyFlowManager.REQUEST_CODE_PAYMENT );

            if (  resultCode == RESULT_OK && data != null) {
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                        .INTENT_EXTRA_TRANSACTION_RESPONSE);

                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                        //Success Transaction
                    } else {
                        //Failure Transaction
                    }

                    // Response from Payumoney
                    String payuResponse = transactionResponse.getPayuResponse();

                    // Response from SURl and FURL
                    String merchantResponse = transactionResponse.getTransactionDetails();

                } else if (resultModel != null && resultModel.getError() != null) {
                    Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                } else {
                    Log.d(TAG, "Both objects are null!");
                }

                callbackContext.success( "Transaction Successful" );

            } else {
                callbackContext.error( "Transaction Failed" );
            }
        }
    }
}



