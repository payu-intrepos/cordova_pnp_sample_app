cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
  {
    "id": "com.payu.plugin.pnp.PayUMoneyPNPPlugin",
    "file": "plugins/com.payu.plugin.pnp/www/PayUMoneyPNPPlugin.js",
    "pluginId": "com.payu.plugin.pnp",
    "clobbers": [
      "cordova.plugins.PayUMoneyPNPPlugin"
    ]
  }
];
module.exports.metadata = 
// TOP OF METADATA
{
  "cordova-plugin-whitelist": "1.3.3",
  "com.payu.plugin.pnp": "1.0.0"
};
// BOTTOM OF METADATA
});