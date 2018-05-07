cordova.define("com.payu.plugin.pnp.PayUMoneyPNPPlugin", function(require, exports, module) {
var exec = require('cordova/exec');

exports.showPaymentView = function (success, error, txnParams) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'showPaymentView', [txnParams]);
};

exports.orderDetails = function (success, error, orderDetails) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'orderDetails', [orderDetails]);
};

exports.merchantDisplayName = function (success, error, merchantName) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'merchantDisplayName', [merchantName]);
};

exports.disableWallet = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableWallet', [shouldDisable]);
};

exports.disableCards = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableCards', [shouldDisable]);
};

exports.disableNetbanking = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableNetbanking', [shouldDisable]);
};

exports.disableThirdPartyWallet = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableThirdPartyWallet', [shouldDisable]);
};

exports.disableEMI = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableEMI', [shouldDisable]);
};

exports.disableCompletionScreen = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableCompletionScreen', [shouldDisable]);
};

exports.disableExitAlertOnCheckoutPage = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableExitAlertOnCheckoutPage', [shouldDisable]);
};

exports.disableExitAlertOnBankPage = function (success, error, shouldDisable) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'disableExitAlertOnBankPage', [shouldDisable]);
};

exports.topBarColor = function (success, error, colorHex) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'topBarColor', [colorHex]);
};

exports.topTitleTextColor = function (success, error, colorHex) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'topTitleTextColor', [colorHex]);
};

exports.buttonColor = function (success, error, colorHex) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'buttonColor', [colorHex]);
};

exports.buttonTextColor = function (success, error, colorHex) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'buttonTextColor', [colorHex]);
};

exports.indicatorTintColor = function (success, error, colorHex) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'indicatorTintColor', [colorHex]);
};

});
