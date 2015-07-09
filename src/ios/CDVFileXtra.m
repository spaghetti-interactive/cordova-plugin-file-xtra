#import "CDVFileXtra.h"

@implementation CDVFileXtra

-(void)getFreeDiskSpace:(CDVInvokedUrlCommand *)command {

    NSFileManager* fileManager = [[NSFileManager alloc] init];
    NSError*__autoreleasing error = nil;
    NSDictionary* dictionary = [fileManager attributesOfFileSystemForPath:@"/" error:&error];

    CDVPluginResult* result = nil;

    if (error) {
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageToErrorObject:(int)error.code];
    } else if (dictionary) {
        NSNumber* bytes = [dictionary objectForKey:NSFileSystemFreeSize];
        NSLog(@"%@", [bytes stringValue]);
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[bytes stringValue]];
    }

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

-(void)getSize:(CDVInvokedUrlCommand *)command {

    NSString* path = [command argumentAtIndex:0];

    NSFileManager* fileManager = [[NSFileManager alloc] init];
    NSError*__autoreleasing error = nil;
    NSDictionary* dictionary = [fileManager attributesOfItemAtPath:path error:&error];

    CDVPluginResult* result = nil;

    if (error) {
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageToErrorObject:(int)error.code];
    } else if (dictionary) {
        NSNumber* bytes = [dictionary objectForKey:NSFileSize];
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[bytes stringValue]];
    }
}

@end
