#import <Cordova/CDVPlugin.h>

@interface CDVFileXtra : CDVPlugin
-(void)getFreeDiskSpace:(CDVInvokedUrlCommand *)command;
@end
