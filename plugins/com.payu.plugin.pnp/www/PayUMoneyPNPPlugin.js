var exec = require('cordova/exec');

exports.initWithTxnParams = function (success, error, txnParams) {
    exec(success, error, 'PayUMoneyPNPPlugin', 'initWithTxnParams', [txnParams]);
};
