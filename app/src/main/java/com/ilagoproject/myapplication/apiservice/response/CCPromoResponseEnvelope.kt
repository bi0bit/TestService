package com.ilagoproject.myapplication.apiservice.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@Namespace(prefix = "s", reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class CCPromoResponseEnvelope @JvmOverloads constructor(@field:Element(name = "Body") var body: CCPromoResponseBody = CCPromoResponseBody())
