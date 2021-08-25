
import 'dart:async';

import 'package:flutter/services.dart';

class Youplusauthplugin {
  static const MethodChannel _channel =
      const MethodChannel('youplusauthplugin');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
