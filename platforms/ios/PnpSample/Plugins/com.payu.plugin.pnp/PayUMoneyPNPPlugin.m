/********* PayUMoneyPNPPlugin.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <PlugNPlay/PlugNPlay.h>

@interface PayUMoneyPNPPlugin : CDVPlugin {
    // Member variables go here.
}
    @end

@implementation PayUMoneyPNPPlugin
    
#pragma mark - Helpers -
    
- (void)setObject:(id)object withProperties:(NSDictionary *)params {
    for (NSString *key in params) {
        [object setValue:[params valueForKey:key] forKey:key];
    }
}
    
- (UIColor *) colorWithHexString: (NSString *) hexString {
    NSString *colorString = [[hexString stringByReplacingOccurrencesOfString: @"#" withString: @""] uppercaseString];
    CGFloat alpha, red, blue, green;
    switch ([colorString length]) {
        case 3: // #RGB
        alpha = 1.0f;
        red   = [self colorComponentFrom: colorString start: 0 length: 1];
        green = [self colorComponentFrom: colorString start: 1 length: 1];
        blue  = [self colorComponentFrom: colorString start: 2 length: 1];
        break;
        case 4: // #ARGB
        alpha = [self colorComponentFrom: colorString start: 0 length: 1];
        red   = [self colorComponentFrom: colorString start: 1 length: 1];
        green = [self colorComponentFrom: colorString start: 2 length: 1];
        blue  = [self colorComponentFrom: colorString start: 3 length: 1];
        break;
        case 6: // #RRGGBB
        alpha = 1.0f;
        red   = [self colorComponentFrom: colorString start: 0 length: 2];
        green = [self colorComponentFrom: colorString start: 2 length: 2];
        blue  = [self colorComponentFrom: colorString start: 4 length: 2];
        break;
        case 8: // #AARRGGBB
        alpha = [self colorComponentFrom: colorString start: 0 length: 2];
        red   = [self colorComponentFrom: colorString start: 2 length: 2];
        green = [self colorComponentFrom: colorString start: 4 length: 2];
        blue  = [self colorComponentFrom: colorString start: 6 length: 2];
        break;
        default:
        return nil;
        break;
    }
    return [UIColor colorWithRed: red green: green blue: blue alpha: alpha];
}
    
- (CGFloat) colorComponentFrom: (NSString *) string start: (NSUInteger) start length: (NSUInteger) length {
    NSString *substring = [string substringWithRange: NSMakeRange(start, length)];
    NSString *fullHex = length == 2 ? substring : [NSString stringWithFormat: @"%@%@", substring, substring];
    unsigned hexComponent;
    [[NSScanner scannerWithString: fullHex] scanHexInt: &hexComponent];
    return hexComponent / 255.0;
}
    
#pragma mark - PNP -
    
    //This method should be called before any other method
- (void)showPaymentView:(CDVInvokedUrlCommand *)command {
    
    NSDictionary *params = [command argumentAtIndex:0];
    PUMTxnParam *txnParam = [PUMTxnParam new];
    
    [self setObject:txnParam withProperties:params];
    
    [PlugNPlay presentPaymentViewControllerWithTxnParams:txnParam
                                        onViewController:self.viewController
                                     withCompletionBlock:^(NSDictionary *paymentResponse, NSError *error, id extraParam) {
                                         CDVPluginResult* pluginResult;
                                         
                                         if (error) {
                                             pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription];
                                         } else {
                                             NSString *message;
                                             if ([paymentResponse objectForKey:@"result"] && [[paymentResponse objectForKey:@"result"] isKindOfClass:[NSDictionary class]] ) {
                                                 message = [[paymentResponse objectForKey:@"result"] valueForKey:@"error_Message"];
                                                 if ([message isEqual:[NSNull null]] || [message length] == 0 || [message isEqualToString:@"No Error"]) {
                                                     message = [[paymentResponse objectForKey:@"result"] valueForKey:@"status"];
                                                     pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[[paymentResponse objectForKey:@"result"] valueForKey:@"status"]];
                                                     
                                                 }
                                             }
                                             else {
                                                 pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[paymentResponse valueForKey:@"status"]];
                                             }
                                             
                                             [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                                         }
                                     }];
    
    
}
    
- (void)orderDetails:(CDVInvokedUrlCommand *)command {
    NSArray *orderDetails = [command argumentAtIndex:0];
    NSError *error = [PlugNPlay setOrderDetails:orderDetails];
    CDVPluginResult *pluginResult;
    
    if (error) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription];
    }
    else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)merchantDisplayName:(CDVInvokedUrlCommand *)command {
    NSString *merchantName = [command argumentAtIndex:0];
    [PlugNPlay setMerchantDisplayName:merchantName];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableWallet:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setDisableWallet:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableCards:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setDisableCards:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableNetbanking:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setDisableNetbanking:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableThirdPartyWallet:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setDisableThirdPartyWallet:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableEMI:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setDisableEMI:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableCompletionScreen:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setDisableCompletionScreen:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableExitAlertOnCheckoutPage:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setExitAlertOnCheckoutPageDisabled:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)disableExitAlertOnBankPage:(CDVInvokedUrlCommand *)command {
    BOOL disable = [[command argumentAtIndex:0] boolValue];
    [PlugNPlay setExitAlertOnBankPageDisabled:disable];
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)topBarColor:(CDVInvokedUrlCommand *)command {
    NSString *colorHex = [command argumentAtIndex:0];
    UIColor *color = [self colorWithHexString:colorHex];
    CDVPluginResult *pluginResult;
    
    if (color) {
        [PlugNPlay setTopBarColor:color];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Color value %@ is invalid.  It should be a hex value of the form #RBG, #ARGB, #RRGGBB, or #AARRGGBB"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)topTitleTextColor:(CDVInvokedUrlCommand *)command {
    NSString *colorHex = [command argumentAtIndex:0];
    UIColor *color = [self colorWithHexString:colorHex];
    CDVPluginResult *pluginResult;
    
    if (color) {
        [PlugNPlay setTopTitleTextColor:color];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Color value %@ is invalid.  It should be a hex value of the form #RBG, #ARGB, #RRGGBB, or #AARRGGBB"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)buttonColor:(CDVInvokedUrlCommand *)command {
    NSString *colorHex = [command argumentAtIndex:0];
    UIColor *color = [self colorWithHexString:colorHex];
    CDVPluginResult *pluginResult;
    
    if (color) {
        [PlugNPlay setButtonColor:color];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Color value %@ is invalid.  It should be a hex value of the form #RBG, #ARGB, #RRGGBB, or #AARRGGBB"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)buttonTextColor:(CDVInvokedUrlCommand *)command {
    NSString *colorHex = [command argumentAtIndex:0];
    UIColor *color = [self colorWithHexString:colorHex];
    CDVPluginResult *pluginResult;
    
    if (color) {
        [PlugNPlay setButtonTextColor:color];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Color value %@ is invalid.  It should be a hex value of the form #RBG, #ARGB, #RRGGBB, or #AARRGGBB"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
- (void)indicatorTintColor:(CDVInvokedUrlCommand *)command {
    NSString *colorHex = [command argumentAtIndex:0];
    UIColor *color = [self colorWithHexString:colorHex];
    CDVPluginResult *pluginResult;
    
    if (color) {
        [PlugNPlay setIndicatorTintColor:color];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Color value %@ is invalid.  It should be a hex value of the form #RBG, #ARGB, #RRGGBB, or #AARRGGBB"];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
    
    
    @end

