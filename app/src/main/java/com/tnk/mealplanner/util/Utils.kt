package com.tnk.project_ions.util

import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KDeclarationContainer
import kotlin.reflect.KProperty

val KProperty<*>.ownerCanonicalName: String? get() = owner?.canonicalName
val KProperty<*>.owner: KDeclarationContainer? get() = if (this is CallableReference) owner else null
val KDeclarationContainer.canonicalName: String? get() = if (this is KClass<*>) this.java.canonicalName else null

class Utils
{
}