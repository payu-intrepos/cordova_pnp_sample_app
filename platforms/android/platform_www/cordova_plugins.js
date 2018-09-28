cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
  {
    "id": "com.payumoney.sdkui.cordova.PayUmoneyPnP",
    "file": "plugins/com.payumoney.sdkui.cordova/www/PayUMoneyPNPPlugin.js",
    "pluginId": "com.payumoney.sdkui.cordova",
    "clobbers": [
      "cordova.plugins.PayUmoneyPnP"
    ]
  }
];
module.exports.metadata = 
// TOP OF METADATA
{
  "cordova-plugin-whitelist": "1.3.3",
  "com.payumoney.sdkui.cordova": "1.1.1"
};
// BOTTOM OF METADATA
});