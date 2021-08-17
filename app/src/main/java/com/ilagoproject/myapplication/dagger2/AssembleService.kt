package com.ilagoproject.myapplication.dagger2

import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class AssembleService()
