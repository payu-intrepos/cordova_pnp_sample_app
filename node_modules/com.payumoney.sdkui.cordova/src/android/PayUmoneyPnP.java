package com.payumoney.sdkui.cordova;


import android.app.Activity;
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
import static android.app.Activity.RESULT_CANCELED;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.payu.custombrowser.bean.ReviewOrderBundle;
import com.payu.custombrowser.bean.ReviewOrderData;
import com.payumoney.core.listener.ReviewOrderImpl;
import android.content.Context;
import android.text.TextUtils;

/**
 * This class echoes a string called from JavaScript.
 */
public class PayUmoneyPnP extends CordovaPlugin {

    private static final String TAG = "PayUMoneyPNPPlugin" ;

    private boolean disableCompletionScreen;
    private String themeName;

    private HashMap<String, CallbackContext> contextHashMap;
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.v(TAG, "initialize(): cordova = "+cordova +", webView = "+webView );

        contextHashMap = new HashMap<>();

    }

    @Override
    public boolean execute( final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.i(TAG, "execute(): "+action +", args = "+args );

        switch ( action ){
            case  "showPaymentView":
                Log.d( TAG, "to showPaymentView()" );
                final JSONObject paymentJsonObject = args.getJSONObject(0);
                cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        launchPayUMoneyFlow(paymentJsonObject, callbackContext);
                    }
                });

                return true;

            case "disableExitAlertOnCheckoutPage":
                try {
                    final boolean toDisableExitAlertOnCheckoutPage = args.getBoolean(0);
                    Log.d( TAG, "disableExitAlertOnCheckoutPage(): setting to " +toDisableExitAlertOnCheckoutPage);
                    disableExitAlertOnCheckoutPage( toDisableExitAlertOnCheckoutPage, callbackContext );
                } catch ( Exception e) {
                    e.printStackTrace();
                    callbackContext.error( e.getMessage() );
                }
                return true;

            case "disableCompletionScreen":
                try {
                    final boolean toDisableCompletionScreen = args.getBoolean(0);
                    Log.d( TAG, "disableCompletionScreen(): setting to " +toDisableCompletionScreen);
                    disableCompletionScreen( toDisableCompletionScreen, callbackContext );
                } catch ( Exception e) {
                    e.printStackTrace();
                    callbackContext.error( e.getMessage() );
                }

                return true;

            case "merchantDisplayName" :
            case "setScreenTitle":
                try {
                    final String screenTitle = args.getString(0);
                    Log.d( TAG, "setScreenTitle(): setting to " +screenTitle );
                    setScreenTitle( screenTitle, callbackContext );
                } catch ( Exception e) {
                    e.printStackTrace();
                    callbackContext.error( e.getMessage() );
                }
                return true;


            case "setDoneButtonText":
                try {
                    final String doneBtnText = args.getString(0);
                    Log.d( TAG, "setDoneButtonText(): setting to " +doneBtnText );
                    setDoneButtonText( doneBtnText, callbackContext );
                } catch ( Exception e) {
                    e.printStackTrace();
                    callbackContext.error( e.getMessage() );
                }
                return true;


            case "setAndroidAppThemeName":
                try {
                    final String themeName = args.getString(0);
                    Log.d( TAG, "setAndroidAppThemeName(): setting to " +themeName );
                    setAndroidAppThemeName( themeName, callbackContext );
                } catch ( Exception e) {
                    e.printStackTrace();
                    callbackContext.error( e.getMessage() );
                }
                return true;

            case "orderDetails":
                try {
                    final String orderDetails = args.getString(0);
                    Log.d( TAG, "setOrderDetails(): setting orderDetails to " +orderDetails );
                    setOrderDetails( orderDetails, callbackContext );
                } catch ( Exception e) {
                    e.printStackTrace();
                    callbackContext.error( e.getMessage() );
                }
                return true;

        }

        return false;
    }

    private void setOrderDetails( String strOrderDetails, final CallbackContext callbackContext  ){

        try {
            // String strOrderDetails = "{\"From\":\"Delhi\"},{\"To\":\"Pune\"},{\"Date\":\"18022018\"},{\"Time\":\"08:45\"},{\"Total\":\"4123.45\"}" ;
            Pattern p = Pattern.compile("\\{([^}]*)\\}");
            Matcher m = p.matcher(strOrderDetails);

            ReviewOrderBundle reviewOrderBundle = null;
            while (m.find()) {
                final String strSingleOrder = m.group(1);
                System.out.println(strSingleOrder);

                final String[] singleOrderKeyValues = strSingleOrder.split(":");
                if (singleOrderKeyValues.length == 2) {

                    String key = singleOrderKeyValues[0];
                    String value = singleOrderKeyValues[1];

                    Log.v( "setOrderDetails", "Got Key = " + key + ", value = " + value);

                    if( !TextUtils.isEmpty(key) && !TextUtils.isEmpty(value )){
                        if( key.startsWith( "\"" )  ){
                            key = key.substring( 1 );
                        }
                        if( key.endsWith( "\"" )  ){
                            key = key.substring( 0, key.length()-1 );
                        }

                        if( value.startsWith( "\"" )  ){
                            value = value.substring( 1 );
                        }
                        if( value.endsWith( "\"" )  ){
                            value = value.substring( 0, value.length()-1 );
                        }

                        if( !TextUtils.isEmpty(key) && !TextUtils.isEmpty(value )) {
                            Log.v( "setOrderDetails", "Setting an OrderDetails with Key = " + key + ", value = " + value);
                            if (reviewOrderBundle == null) {
                                reviewOrderBundle = new ReviewOrderBundle();
                            }
                            reviewOrderBundle.addOrderDetails(key, value);
                        }
                    }
                }
            }

            if( reviewOrderBundle == null ){
                Log.d( "setOrderDetails" ,"No OrderDetails to set") ;
                callbackContext.error("Order details not set");
            }else {
                Log.d( "setOrderDetails" ,"Order Details set") ;
                PayUmoneyConfig.getInstance().setReviewOrderBundle(reviewOrderBundle);
                PayUmoneyConfig.getInstance().setReviewOrderImpl(new ReviewOrderImpl() {
                    @Override
                    public Intent getReviewOrderScreenIntent(Context context) {
                        return null;
                    }
                });

                PayUmoneyConfig.getInstance().setEnableReviewOrder( true );

                callbackContext.success("Order details set");
            }
        } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error("Order details not set");
        }
    }

    private void disableCompletionScreen( final boolean disable, final CallbackContext callbackContext ) {
        this.disableCompletionScreen = disable ;
        callbackContext.success("set disableCompletionScreen flag to : " + disable);
    }

    private void disableExitAlertOnCheckoutPage( final boolean disable, final CallbackContext callbackContext ){
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.disableExitConfirmation( disable );
        callbackContext.success( "set disableExitAlertOnCheckoutPage flag to : "+disable);
    }

    private void setDoneButtonText( final String doneBtnText, final CallbackContext callbackContext) {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setDoneButtonText( doneBtnText );
        callbackContext.success( "Set DoneButtonText to : "+doneBtnText);
    }

    private void setScreenTitle( final String screenTitle, final CallbackContext callbackContext) {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        payUmoneyConfig.setPayUmoneyActivityTitle( screenTitle );
        callbackContext.success( "Set ScreenTitle to : "+screenTitle);
    }

    private void setAndroidAppThemeName( final String themeName, final CallbackContext callbackContext ) {
        this.themeName = themeName ;
        callbackContext.success("Set theme to : " + themeName);
    }

    private int getTheme( String themeName ) {
        final Activity activity = cordova.getActivity();

        Log.v(TAG, "getTheme(): To find theme = " + themeName +", in package = " +activity.getPackageName() );

        int theme;
        try {
            theme = activity.getResources().getIdentifier( themeName, "style", activity.getPackageName() );
            Log.v(TAG, "getTheme(): appTheme = " + theme );
        } catch (Exception e) {
            e.printStackTrace();
            theme = com.payumoney.sdkui.R.style.AppTheme_default;
            Log.w(TAG, "getTheme(): Exception = " + e + ", setting default theme = " + theme );
        }

        if( theme <= 0 ){
            theme = com.payumoney.sdkui.R.style.AppTheme_default;
        }

        return theme;
    }

    private void launchPayUMoneyFlow( final JSONObject jsonObject , final CallbackContext callbackContext){

        Log.d(TAG, "launchPayUMoneyFlow(): "+jsonObject );

        try {
            final String amount = jsonObject.getString( "amount");
            Log.d(TAG, "launchPayUMoneyFlow(): amount = "+amount );

            final String txnId = jsonObject.getString( "txnID");
            Log.d(TAG, "launchPayUMoneyFlow(): txnId = "+txnId );

            final String phone = jsonObject.getString( "phone");
            final String email = jsonObject.getString( "email");
            final String firstName = jsonObject.getString( "firstname");

            final String productName = jsonObject.getString( "productInfo");
            final String sUrl = jsonObject.getString( "surl");
            final String fUrl = jsonObject.getString( "furl");

            // 0 == production, 1 == sandbox
            final int environment = jsonObject.getInt( "environment" );
            final boolean isDebug = environment == 0 ? false : true ;

            final String merchantKey = jsonObject.getString( "key");
            final String merchant_ID = jsonObject.getString( "merchantid");
            final String hash = jsonObject.getString( "hashValue");

            final int style = getTheme( this.themeName );

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

            Log.i(TAG, "launchPayUMoneyFlow(): launching with style id = " + style );

            Log.i(TAG, "launchPayUMoneyFlow(): amount = " + amount+", txnId = "+txnId+", phone = "+ phone+", email = "+ email+", firstName = "
                    +firstName+", productName = "+ productName+", sUrl = "+ sUrl+", fUrl = "+fUrl+", isDebug = "+ isDebug+", merchantKey = "+ merchantKey+", merchant_ID = "+ merchant_ID+", "+hash);

            Log.i(TAG, "launchPayUMoneyFlow(): disableCompletionScreen = " + disableCompletionScreen );

            Log.i(TAG, "launchPayUMoneyFlow(): isDebug = " + isDebug );

            launchPayUMoneyFlow( amount, txnId, phone, email, firstName, productName, sUrl, fUrl, isDebug, merchantKey, merchant_ID, hash,
                                            style, udf1, udf2, udf3, udf4, udf5, udf6, udf7, udf8, udf9, udf10, this.disableCompletionScreen, callbackContext );
        }catch ( Exception e){
            e.printStackTrace();
            callbackContext.error( e.getMessage() );
        }

    }



    private void launchPayUMoneyFlow(final String amount, final String txnId, final String phone, final String email, final String firstName,
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
            paymentParam.setMerchantHash( hash );


            final Intent intent = PayUmoneyFlowManager.getIntentToStartPayUMoneyFlow( paymentParam, cordova.getActivity(), style, isOverrideResultScreen);
            cordova.startActivityForResult( this, intent, 1 );
        } catch ( Exception e ) {
            // oops some exception.
            e.printStackTrace();
            callbackContext.error( e.getMessage() );
        }
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
        if( requestCode == 1 ) {
            final CallbackContext callbackContext = contextHashMap.get( ""+PayUmoneyFlowManager.REQUEST_CODE_PAYMENT );

            if (  resultCode == RESULT_OK ) {
                if( data != null ) {
                    TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                            .INTENT_EXTRA_TRANSACTION_RESPONSE);

                    ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
                    // Check which object is non-null
                    if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                        final TransactionResponse.TransactionStatus transactionStatus = transactionResponse.getTransactionStatus();

                        switch (transactionStatus) {
                            case FAILED:
                                callbackContext.error("Transaction failed");
                                break;

                            case SUCCESSFUL:
                                callbackContext.success("Transaction Successful");
                                break;

                            case CANCELLED:
                                callbackContext.error("Transaction Cancelled");
                                break;

                            default:
                                callbackContext.error(transactionStatus.name());
                                break;
                        }

                        // Response from Payumoney
                        String payuResponse = transactionResponse.getPayuResponse();

                        // Response from SURl and FURL
                        String merchantResponse = transactionResponse.getTransactionDetails();

                    } else if (resultModel != null && resultModel.getError() != null) {
                        Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                        callbackContext.error("Transaction failed");
                    } else {
                        Log.d(TAG, "Both objects are null!");
                        callbackContext.success("Transaction Successful");
                    }
                }else {
                    callbackContext.error( "Transaction Failed" );
                }
            }else if (  resultCode == RESULT_CANCELED ) {
                callbackContext.error("Transaction Cancelled");
            }else {
                callbackContext.error( "Transaction Failed" );
            }
        }
    }
}



