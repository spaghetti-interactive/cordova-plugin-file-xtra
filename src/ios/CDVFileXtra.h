#import <Cordova/CDVPlugin.h>

@interface CDVFileXtra : CDVPlugin
-(void)getFreeDiskSpace:(CDVInvokedUrlCommand *)command;
-(void)getSize:(CDVInvokedUrlCommand *)command;
@end
