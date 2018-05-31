cordova.define("com.payu.plugin.pnp.PayUMoneyPNPPlugin", function(require, exports, module) {
var exec = require('cordova/exec');

/**
 * This method is used for showing payments view.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {PUMTxnParam} txnParams: The transaction params.
 */
exports.showPaymentView = function (success, error, txnParams) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'showPaymentView', [txnParams]);
};

/**
 * The method is used for showing order details at payment screens.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {Array} orderDetails: The details of order in Array of single key value pair. ex orderDetails = [{"From":"Delhi"},{"To":"Pune"}].
 */
exports.orderDetails = function (success, error, orderDetails) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'orderDetails', [orderDetails]);
};

/**
 * This method is used for setting Merchant's display name.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {String} merchantName: The Display name of the merchant.
 */
exports.merchantDisplayName = function (success, error, merchantName) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'merchantDisplayName', [merchantName]);
};

/**
 * This method is used for disabling wallet transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable wallet or not.
 */
exports.disableWallet = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableWallet', [shouldDisable]);
};

/**
 * This method is used for disabling cards transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable cards or not.
 */
exports.disableCards = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableCards', [shouldDisable]);
};

/**
 * This method is used for disabling netbanking transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable NetBanking or not.
 */
exports.disableNetbanking = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableNetbanking', [shouldDisable]);
};

/**
 * This method is used for disabling 3rd party wallets transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable 3rd party wallets or not.
 */
exports.disableThirdPartyWallet = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableThirdPartyWallet', [shouldDisable]);
};

/**
 * This method is used for disabling EMI transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable EMI or not.
 */
exports.disableEMI = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableEMI', [shouldDisable]);
};

/**
 * This method is used for disabling screen which is shown after successful transaction.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable succees screen or not.
 */
exports.disableCompletionScreen = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableCompletionScreen', [shouldDisable]);
};

/**
 * This method is used for disabling alert on checkout screen(The screen on which all the payment options are shown).
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable alert on checkout screen or not.
 */
exports.disableExitAlertOnCheckoutPage = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableExitAlertOnCheckoutPage', [shouldDisable]);
};

/**
 * This method is used for disabling alert on Bank screen(The screen on which password or otps are entered).
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable alert on Bank screen or not.
 */
exports.disableExitAlertOnBankPage = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableExitAlertOnBankPage', [shouldDisable]);
};

/**
<<<<<<< HEAD
 * This method is used for setting Android app's theme.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {String} theme: The theme will be decided on the basis of this param.
 */
exports.setAndroidAppThemeName = function (success, error, theme) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'setAndroidAppThemeName', [theme]);
};

/**
 * This method is used for setting iOS app's theme.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {String} topBarColor: The bacnkground color of the top bar.
 * @param {String} topTitleTextColor: The text color of the top bar' title.
 * @param {String} buttonColor: The bacnkground color of buttons.
 * @param {String} buttonTextColor: The color of the button's text.
 */
exports.setiOSAppTheme = function (success, error, topBarColor, topTitleTextColor, buttonColor, buttonTextColor) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'setiOSAppTheme', [topBarColor, topTitleTextColor, buttonColor, buttonTextColor]);
};

=======
 * This method is used for setting app theme.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {PUMStyle} style: The theme will be decided on the basis of this param.
 */
exports.setAppTheme = function (success, error, style) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'setAppTheme', [style]);
};

exports.setScreenTitle = function (success, error, title) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'setScreenTitle', [title]);
};

exports.setDoneButtonText = function (success, error, doneButtonText ) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'setDoneButtonText', [doneButtonText]);
};

>>>>>>> 912ab8a8ca28d05d3f81c6283ad6511070dc5598

});
