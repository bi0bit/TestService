package com.ilagoproject.myapplication.apiservice.request

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@Namespace(prefix = "s11", reference = "http://schemas.xmlsoap.org/soap/envelope/")
class CCPromoRequestEnvelope(
    @field:Element(name = "s11:Body")
    var body: CCPromoRequestBody
)