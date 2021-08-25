import 'package:flutter/services.dart';

class YouPlusAuth {
  static const _methodMessageChannel = MethodChannel("youplusplugin");

  openYouPlus() {
    _methodMessageChannel.invokeMethod("openYouPlus");
  }

  registerAuthCallback(Function(String, String) authCallback) {
    _methodMessageChannel.setMethodCallHandler((call) async {
      if (call.method == "authResult") {
        authCallback(call.arguments["username"], call.arguments["token"]);
      }
      return;
    });
  }
}
