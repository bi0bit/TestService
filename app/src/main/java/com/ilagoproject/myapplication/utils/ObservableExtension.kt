package com.ilagoproject.myapplication.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

fun <T> Observable<T>.subscribe(
    onComplete: (() -> Unit)? = null,
    onNext: ((it: T) -> Unit)? = null,
    onError: ((it: Throwable?) -> Unit)? = null) : Disposable
{
    return this.subscribe(onNext, onError, onComplete)
}