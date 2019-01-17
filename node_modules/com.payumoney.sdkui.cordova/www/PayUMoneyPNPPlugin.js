var exec = require('cordova/exec');

/**
 * This method is used for showing payments view.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {PUMTxnParam} txnParams: The transaction params.
 */
exports.showPaymentView = function (success, error, txnParams) {
    exec(success, error, 'PayUmoneyPnP', 'showPaymentView', [txnParams]);
};

/**
 * The method is used for showing order details at payment screens.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {Array} orderDetails: The details of order in Array of single key value pair. ex orderDetails = [{"From":"Delhi"},{"To":"Pune"}].
 */
exports.orderDetails = function (success, error, orderDetails) {
    exec(success, error, 'PayUmoneyPnP', 'orderDetails', [orderDetails]);
};

/**
 * This method is used for setting Merchant's display name.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {String} merchantName: The Display name of the merchant.
 */
exports.merchantDisplayName = function (success, error, merchantName) {
    exec(success, error, 'PayUmoneyPnP', 'merchantDisplayName', [merchantName]);
};

/**
 * This method is used for disabling wallet transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable wallet or not.
 */
exports.disableWallet = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableWallet', [shouldDisable]);
};

/**
 * This method is used for disabling cards transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable cards or not.
 */
exports.disableCards = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableCards', [shouldDisable]);
};

/**
 * This method is used for disabling netbanking transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable NetBanking or not.
 */
exports.disableNetbanking = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableNetbanking', [shouldDisable]);
};

/**
 * This method is used for disabling 3rd party wallets transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable 3rd party wallets or not.
 */
exports.disableThirdPartyWallet = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableThirdPartyWallet', [shouldDisable]);
};

/**
 * This method is used for disabling EMI transactions.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable EMI or not.
 */
exports.disableEMI = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableEMI', [shouldDisable]);
};

/**
 * This method is used for disabling screen which is shown after successful transaction.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable succees screen or not.
 */
exports.disableCompletionScreen = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableCompletionScreen', [shouldDisable]);
};

/**
 * This method is used for disabling alert on checkout screen(The screen on which all the payment options are shown).
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable alert on checkout screen or not.
 */
exports.disableExitAlertOnCheckoutPage = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableExitAlertOnCheckoutPage', [shouldDisable]);
};

/**
 * This method is used for disabling alert on Bank screen(The screen on which password or otps are entered).
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {boolean} shouldDisable: Disable alert on Bank screen or not.
 */
exports.disableExitAlertOnBankPage = function (success, error, shouldDisable) {
    exec(success, error, 'PayUmoneyPnP', 'disableExitAlertOnBankPage', [shouldDisable]);
};

/**
 * This method is used for setting Android app's theme.
 * @param success: The success block which is called when function is successfully executed
 * @param error: The failure block which is called when some proble occurs.
 * @param {String} theme: The theme will be decided on the basis of this param.
 */
exports.setAndroidAppThemeName = function (success, error, theme) {
    exec(success, error, 'PayUmoneyPnP', 'setAndroidAppThemeName', [theme]);
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
    exec(success, error, 'PayUmoneyPnP', 'setiOSAppTheme', [topBarColor, topTitleTextColor, buttonColor, buttonTextColor]);
};

