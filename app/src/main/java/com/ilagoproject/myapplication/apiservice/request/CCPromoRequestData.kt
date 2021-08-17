package com.ilagoproject.myapplication.apiservice.request

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root


@Root(name = "GetCCPromo", strict = false)
@Namespace(prefix = "ns1", reference = "http://tempuri.org/")
class CCPromoRequestData(
    @field:Element(name = "ns1:lang", required = false)
    var lang: String
) {


}
