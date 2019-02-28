**PayUMoney PnP SDK Cordova Plugin** 
Introduction 
The Cordova plugin for the PayUmoney SDK enables merchants to accept payments in their Android and iOS app through the native PayUmoney SDK. Once integrated, the plugin allows apps written on Cordova to access the functionality provided by the native SDK. 
 
**Features of the PayUmoney SDK**  
The PayUmoney Mobile SDK is a complete out of the box solution to enable payment acceptance in your Android or iOS mobile app. The SDK provides a swathe of features to not only make it easy to accept payments but also provide an easy to use, intuitive checkout experience. 
Key features of the PayUmoney SDK are: 
-Accept payments through 100+ payment methods 
*Ready to use, native checkout experience to provide a secure and robust payment journey 
*User friendly features such as Saved Cards, Order Review, and OTP Assist 
*Integrating with PayUmoney on Cordova 

**Get the plugin** 
To add the plugin to your Cordova development project, execute the following command from the console: 
```
$ cordova plugin add com.payumoney.sdkui.cordova@1.2.0
```
 
**Integrating the SDK with your app**
The PayUmoney Cordova plugin adds 2 JavaScript files to your project: 
`PayUMoneyPNPPlugin.js`: A wrapper around the native SDK. You can use the functions and classes defined in this script. 

`PayU-Helper.js`: A helper file which defines the `PUMTxnParam`, `PUMStyle` and `PUMEnvironment` classes for use with `PayUmoneyPnP`. 

To integrate with the PayUmoney SDK, you will need to include the PayU-Helper.js file in your index.html located as `<app_name>/www/index.html` 

In the index.html, add the following line after the statement importing the `cordova.js` file: 
<script type="text/javascript" src="js/PayU-Helper.js"></script> 
 Ensure that the PayU-Helper.js file is located in the `js` folder in your project’s source directory. 
  
After adding the Plugin files, you need to instantiate the PayUmoney.  
 
User can use below mentioned methods in your code: 

```
showPaymentUI: function(payment) { 
    var merchantID = '<#Your_Merchant_ID#>'; // eg merchantID = '5805351' 
    var key = '<#Your_Merchant_Key#>'; // eg key = '0P61t4V0' 
    var txnId = '<#Unique_Transaction_ID#>'; // eg txnId = 'aklsdj213asdasd231'. It should be unique everytime 
    var amount = '123'; 
    var phoneNumber = '912*******'; // Provide valid number of user to fetch saved cards 
    var email = 'abc@de.com'; // Provide valid email of user to fetch saved cards 
    var userFirstName = 'UserFirstName '; 
    var environment_value = shouldUseTestEnv ? PUMEnvironment.PUMEnvironmentTest : PUMEnvironment.PUMEnvironmentProduction; // select your Environment 
    var sURL = '<#Your_Success_URL#>'; 
    var fURL = '<#Your_Failure_URL#>'; 
    var product_description = '<#Your_Product_description#>'; 
    // Create a PUMTxnParam object passing it the parameters declared above. The UDF parameters are optional User Defined Fields 
    var txnParam = new PUMTxnParam(key, merchantID, txnId, amount, phoneNumber, email, userFirstName, sURL, fURL, product_description, environment_value, udf1, udf2, udf3, udf4, udf5, udf6, udf7, udf8, udf9, udf10); 
} 
 
txnParam.hashValue = '<#Hash_Value_For_Current_Transaction#>';  
 
// Call the plugin’s method showPaymentView by passing it the txnParam. This will show the checkout screen and give the success callback if the txnParam is created correctly, else it will call the failure callback 
// 1st parameter is a success callback, 2nd parameter is a failure callback, 3rd parameter is the txnParam 
cordova.plugins.PayUmoneyPnP.showPaymentView(function (response) { 
console.log('showPaymentView received Success '); 
alert(JSON.stringify(response)); 
}, 
function (response){ 
console.log('showPaymentView received Failure '); 
alert(JSON.stringify(response)); 
}, 
txnParam ); 
       } 
 ```
 

 Optional - Call this method if you want an optional ‘Order Details’ UI available to the user. A menu will be shown on the Checkout screen. Clicking this menu button will show an Order Details screen 

```
 setOrderDetails : function () { 
// Create an array of Order Detail elements as shown in the example below. This will get displayed in 2 columns in the Order Details Screen 
         var orderDetails = [{"From":"Delhi"},{"To":"Pune"},{"Date":"18-02-2018"},{"Time":"08:45"},{"Total":"4123.45"}]; 
 
// 1st parameter is a success callback, 2nd parameter is a failure callback, 3rd parameter is the orderDetails  
cordova.plugins.PayUmoneyPnP.orderDetails( 
function (response) { 
console.log('Order details received Success '); 
}, 
function (response) { 
console.log('Order details received Failure '.concat(response)); 
}, 
orderDetails); 
} 
} 
 ```
 
 
Setting the app theme -  
Since setting the theme is handled differently for iOS & Android, you need to call different plugin APIs for setting them. 
 ```
// Set iOS App theme 
       setiOSAppTheme : function() { 
         var topBarColor = '0A927A'; 
         var topTitleTextColor = 'ffffff'; 
         var buttonColor = '0A927A'; 
         var buttonTextColor = 'ffffff'; 
 
// 1st parameter is a success callback, 2nd parameter is a failure callback, remaining parameters are colors defined above. 
         cordova.plugins.PayUmoneyPnP.setiOSAppTheme( function (response) { 
                                                                    console.log('setiOSAppTheme received Success '); 
                                                                 }, 
                                                                 function (response){ 
                                                                    console.log('setiOSAppTheme received Failure '.concat(response) ); 
                                                                 }, 
                                                                  topBarColor,topTitleTextColor,buttonColor,buttonTextColor); 
            } 
       } 
 ```
     
Set Android App theme -
 ``` 
 setAndroidAppThemeName: function() { 
         var themeName = '<#Your_Theme_Name#>' ; // e.g. 'AppTheme.Green’. Define this theme in your styles.xml as shown. If invalid, Plugin will select a default theme. 
 
// 1st parameter is a success callback, 2nd parameter is a failure callback, 3rd parameter is the themeName 
         cordova.plugins.PayUmoneyPnP.setAndroidAppThemeName( function (response) { 
                                                                    console.log('payButtonClicked(): setAndroidAppThemeName received Success '); 
                                                                 }, 
                                                                 function (response){ 
                                                                    console.log('payButtonClicked(): setAndroidAppThemeName received Failure '.concat(response) ); 
                                                                 }, 
                                                                themeName ); 
       } 

// Example of styles.xml 
 
<style name="AppTheme.Green" parent="PayumoneyAppTheme"> 
        <item name="colorPrimary">@color/persian_green_primary</item> // Define these colors in colors.xml 
        <item name="colorPrimaryDark">@color/persian_green_dark</item> 
        <item name="colorAccent">@color/persian_green_accent</item> 
        <item name="colorButtonNormal">@color/persian_green_primary</item> 
        <item name="alertDialogTheme">@style/AlertDialogStyle_green</item> 
        <item name="actionMenuTextColor">@color/white</item> 
    </style> 
 
// Another example 
<style name="AppTheme.pink" parent="PayumoneyAppTheme"> 
        <item name="colorPrimary">@color/pink_primary</item> 
        <item name="colorPrimaryDark">@color/pink_dark</item> 
        <item name="colorAccent">@color/pink_accent</item> 
        <item name="colorButtonNormal">@color/pink_primary</item> 
        <item name="alertDialogTheme">@style/AlertDialogStyle_pink</item> 
        <item name="actionMenuTextColor">@color/white</item> 
    </style> 
 ```