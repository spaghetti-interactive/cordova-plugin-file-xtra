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

    NSURL* url = [NSURL URLWithString:[command argumentAtIndex:0]];
    NSString* basePath = url.path;

    NSFileManager* fileManager = [[NSFileManager alloc] init];
    NSError*__autoreleasing error = nil;

    NSDirectoryEnumerator* enumerator = [fileManager enumeratorAtPath:basePath];
    NSString* pathComponent;
    NSString* fullPath;

    NSDictionary* attributes;
    NSString* fileType;
    NSNumber* fileSize;

    unsigned long long bytes = 0;

    for (pathComponent in enumerator) {

        fullPath = [basePath stringByAppendingPathComponent:pathComponent];
        attributes = [fileManager attributesOfItemAtPath:fullPath error:&error];

        if (error) {
            break;
        }

        if (attributes) {
            fileType = [attributes objectForKey:NSFileType];

            if ([fileType isEqualToString:NSFileTypeRegular]) {
                fileSize = [attributes objectForKey:NSFileSize];
                bytes += [fileSize unsignedLongLongValue];
            }
        }
    }

    CDVPluginResult* result;

    if (error) {
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageToErrorObject:(int)error.code];
    } else {
        fileSize = [NSNumber numberWithUnsignedLongLong:bytes];
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[fileSize stringValue]];
    }

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
