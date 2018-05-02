
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
function PUMTxnParam(key,merchantid,txnID,amount,phone,email,firstname,surl,furl,productInfo,environment,hashValue,udf1="",udf2="",udf3="",udf4="",udf5="",udf6="",udf7="",udf8="",udf9="",udf10="") {
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

/**
 * The PUMCCDC class defines properties related to cards.
 * @param {String} cardNumber: Card number
 * @param {String} expiryMonth: expiry month of the card.
 * @param {String} expiryYear: expiry year ofthe card
 * @param {String} cvv: The cvv of the card
 * @param {String} cardType: The type of card
 * @param {String} pg: The payment gateway of the card
 */
function PUMCCDC(cardNumber,expiryMonth,expiryYear,cvv,cardType,pg) {
  this.cardNumber = cardNumber;
  this.expiryMonth = expiryMonth;
  this.expiryYear = expiryYear;
  this.cvv = cvv;
  this.cardType = cardType;
  this.pg = pg;
}

/**
 * The PUMSaveCardBO class defines properties related to saved cards.
 * @param {String} cardId: Uniques card id for the saved card
 * @param {String} cardName: Name of the card.
 * @param {String} cardToken: Token related to the card
 * @param {String} cardType: The type of card
 * @param {String} ccnum: cc number of the card
 * @param {String} oneclickcheckout: if card is one
 * @param {String} pg: The payment gateway of the card
 * @param {String} rewardType: Type of the reward
 * @param {String} cvv: The cvv of the card
 */
function PUMSaveCardBO(cardId,cardName,cardToken,cardType,ccnum,oneclickcheckout,pg,rewardType,cvv) {
  this.cardId = cardId;
  this.cardName = cardName;
  this.cardToken = cardToken;
  this.cardType = cardType;
  this.ccnum = ccnum;
  this.oneclickcheckout = oneclickcheckout;
  this.pg = pg;
  this.rewardType = rewardType;
  this.cvv = cvv;
}

/**
 * The PUMNetBankingBO class defines properties related to NB.
 * @param {String} name: Name of the bank
 * @param {String} title: Title of the bank.
 * @param {String} pgId: Pgid of the bank
 * @param {String} bankCode: Bank code
 * @param {String} show_form: Show form or not
 * @param {Int} pt_priority: Priority of the bank
 */
function PUMNetBankingBO(name,title,pgId,bankCode,show_form,pt_priority) {
  this.name = name;
  this.title = title;
  this.pgId = pgId;
  this.bankCode = bankCode;
  this.show_form = show_form;
  this.pt_priority = pt_priority;
}

/**
 * The PUMPaymentParam class defines properties related to Payment.
 * @param {PUMSaveCardBO} objSavedCard: Saved card params
 * @param {PUMNetBankingBO} objNetBanking: Netbanking params.
 * @param {PUMCCDC} objCCDC: CCDC params.
 * @param {PUMPaymentMode} paymentMode: Payment mode
 * @param {Bool} useWallet: Should use wallet or not
 */
function PUMPaymentParam(objSavedCard,objNetBanking,objCCDC,paymentMode,useWallet) {
  this.objSavedCard = objSavedCard;
  this.objNetBanking = objNetBanking;
  this.objCCDC = objCCDC;
  this.paymentMode = paymentMode;
  this.useWallet = useWallet;
}

