
import 'dart:async';

import 'package:flutter/services.dart';

class Youplusauthplugin {
  static const MethodChannel _channel =
      const MethodChannel("YouPlusAuthPlugin");

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
  openYouPlus() {
    _channel.invokeMethod("openYouPlus");
  }

  registerAuthCallback(Function(String username, String token) authCallback) {
    _channel.setMethodCallHandler((call) async {
      if (call.method == "authResult") {
        authCallback(call.arguments["username"], call.arguments["token"]);
      }
      return;
    });
  }
}
