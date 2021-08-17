package com.ilagoproject.myapplication.apiservice.data

data class AuthorizationData(
        var login: Long = -1,
        @Transient
        var password: String = "",
        var token: String = ""
)
