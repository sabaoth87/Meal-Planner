package com.tnk.project_ions.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

abstract class ActivityCompanion <out IntentOptions>(
   val intentOptions: IntentOptions,
   kclass: KClass<out Activity>
){
   val javaClass = kclass.java

   inline fun intent(context: Context, configureIntent: IntentOptions.(Intent) -> Unit): Intent =
      Intent(context, javaClass).apply { configureIntent(intentOptions, this) }

   fun intent(context: Context) = intent(context) {}

   inline fun start(context: Context, configureIntent: IntentOptions.(Intent) -> Unit) {
      context.startActivity(intent(context, configureIntent))
   }

   fun start(context: Context) {
      start(context) {}
   }

   inline fun <T> Intent.options(block: IntentOptions.(Intent) -> T): T =
      block(intentOptions, this)

}