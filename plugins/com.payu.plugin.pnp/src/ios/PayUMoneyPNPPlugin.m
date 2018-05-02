/********* PayUMoneyPNPPlugin.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <PlugNPlay/PlugNPlay.h>

@interface PayUMoneyPNPPlugin : CDVPlugin {
    // Member variables go here.
}
@end

@implementation PayUMoneyPNPPlugin


- (void)setObject:(id)object withProperties:(NSDictionary *)params {
    for (NSString *key in params) {
        [object setValue:[params valueForKey:key] forKey:key];
    }
}

#pragma mark - PNP -

//This method should be called before any other method
- (void)initWithTxnParams:(CDVInvokedUrlCommand *)command {
    
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

@end

