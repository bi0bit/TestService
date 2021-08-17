package com.ilagoproject.myapplication.apiservice.request

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "s11:Body", strict = false)
class CCPromoRequestBody(
    @field:Element(name = "GetCCPromo")
    var data: CCPromoRequestData
)
