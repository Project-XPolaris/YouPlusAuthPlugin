package com.projectxpolaris.youplusauthplugin

import android.content.Intent
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** YouplusauthpluginPlugin */
class YouplusauthpluginPlugin: FlutterPlugin, MethodCallHandler,ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private var activity: FlutterActivity? = null

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, CHANNEL_NAME)
    channel.setMethodCallHandler(this)

  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
    when (call.method) {
      METHOD_OPEN_YOUPLUS -> startYouPlusApp(call, result)
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  fun startYouPlusApp(call: MethodCall, result: MethodChannel.Result) {
    val intent = Intent()
    intent.action = "com.projectxpolaris.AUTH_LOGIN"
    intent.putExtra("CALLBACK","com.projectxpolaris.YOUVIDEO_AUTH_CALLBACK")
    intent.putExtra("APPNAME","YouVideo")
    activity?.startActivityForResult(intent, AuthRequestCode)
  }
  companion object {
    const val CHANNEL_NAME = "YouPlusAuthPlugin"
    const val METHOD_OPEN_YOUPLUS = "openYouPlus"
    const val AuthRequestCode = 2
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity as FlutterActivity
    binding.addActivityResultListener { requestCode, resultCode, data ->
      if (requestCode == 2) {
        val username = data?.getStringExtra("USERNAME") ?: ""
        val token = data?.getStringExtra("TOKEN") ?: ""
        if (username != "") {
          channel.invokeMethod("authResult", mapOf( "token" to token,"username" to username))
        }
      }
      true
    }
  }

  override fun onDetachedFromActivityForConfigChanges() {
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
  }

  override fun onDetachedFromActivity() {
  }
}
