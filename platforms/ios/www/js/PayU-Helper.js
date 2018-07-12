
var PUMEnvironment = {
  PUMEnvironmentProduction: 0,
  PUMEnvironmentTest: 1,
};

var PUMPaymentMode = {
  PUMPaymentModeNone: 0,
  PUMPaymentModeCCDC: 1,
  PUMPaymentModeNetBanking: 2,
  PUMPaymentModeStoredCard: 3,
};

var PUMStyle = {
  PUMStyleDefault: 0,
  PUMStylePink: 1,
  PUMStyleGreen: 2,
  PUMStyleBlue: 3,
  PUMStylePurple: 4,
  PUMStyleGrey: 5,
};

/**
 * The PUMTxnParam class defines properties related to Transaction.
 * @param {String} key: Key related to the merchant
 * @param {String} merchantid: The unique ID for merchant.
 * @param {String} txnID: The transaction id for current transaction.
 * @param {String} amount: The amount of the transaction.
 * @param {String} phone: The phone no. of the user
 * @param {String} email: The email id of the user
 * @param {String} firstname: The first name of the user
 * @param {String} surl: The success URL which will be hit when payment is successful
 * @param {String} furl: The failure URL which will be hit when payment is failed
 * @param {String} productInfo: Info of the product
 * @param {PUMEnvironment} environment: Current environment
 * @param {String} hashValue: The unique hash value
 * @param {String} udf1-udf10: User defined properties
 */
function PUMTxnParam(key,merchantid,txnID,amount,phone,email,firstname,surl,furl,productInfo,environment,hashValue,udf1,udf2,udf3,udf4,udf5,udf6,udf7,udf8,udf9,udf10) {

  // Setting default value here as on older iOS devices JS Functions with default values don't work properly
  if (!udf1) udf1 = "";
  if (!udf2) udf2 = "";
  if (!udf3) udf3 = "";
  if (!udf4) udf4 = "";
  if (!udf5) udf5 = "";
  if (!udf6) udf6 = "";
  if (!udf7) udf7 = "";
  if (!udf8) udf8 = "";
  if (!udf9) udf9 = "";
  if (!udf10) udf10 = "";
  this.key = key;
  this.merchantid = merchantid;
  this.txnID = txnID;
  this.amount = amount;
  this.phone = phone;
  this.email = email;
  this.firstname = firstname;
  this.surl = surl;
  this.furl = furl;
  this.productInfo = productInfo;
  this.environment = environment;
  this.hashValue = hashValue;
  this.udf1 = udf1;
  this.udf2 = udf2;
  this.udf3 = udf3;
  this.udf4 = udf4;
  this.udf5 = udf5;
  this.udf6 = udf6;
  this.udf7 = udf7;
  this.udf8 = udf8;
  this.udf9 = udf9;
  this.udf10 = udf10;
}
