package com.sergeybukarev.moviestestapp.core.toothpick.qualifiers

import javax.inject.Qualifier
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.annotation.AnnotationTarget.VALUE_PARAMETER

@Qualifier
@Target(FIELD, VALUE_PARAMETER, PROPERTY_SETTER)
annotation class RootNavigation
