package com.ilagoproject.myapplication.apiservice

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.EXPRESSION)
annotation class SourceResponse(val source: String = "")
