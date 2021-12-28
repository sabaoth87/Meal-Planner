package com.tnk.project_ions.adapters

import android.content.Intent
import com.tnk.project_ions.util.ownerCanonicalName
import kotlin.reflect.KProperty

class IntentExtraString(private val name: String? = null) {

    private val KProperty<*>.extraName get() = this@IntentExtraString.name ?: fallBackName
    private val KProperty<*>.fallBackName get() = ownerCanonicalName?.let { "$it::$name"}

    operator fun getValue(intent: Intent, property: KProperty<*>): String? = intent.getStringExtra(property.extraName)

    operator fun setValue(intent: Intent, property: KProperty<*>, value: String?) {
        intent.putExtra(property.extraName, value)
    }

}