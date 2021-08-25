#import "YouplusauthpluginPlugin.h"
#if __has_include(<youplusauthplugin/youplusauthplugin-Swift.h>)
#import <youplusauthplugin/youplusauthplugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "youplusauthplugin-Swift.h"
#endif

@implementation YouplusauthpluginPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftYouplusauthpluginPlugin registerWithRegistrar:registrar];
}
@end
