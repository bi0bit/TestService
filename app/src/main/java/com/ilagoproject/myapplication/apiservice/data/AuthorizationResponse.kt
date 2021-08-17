package com.ilagoproject.myapplication.apiservice.data

open class AuthorizationResponse (
    val extensionData: ExtensionData,
    val result: Boolean,
    val token: String,
)

class AuthorizationResponseSource(val source: String, response: AuthorizationResponse) : AuthorizationResponse(response.extensionData, response.result, response.token)
