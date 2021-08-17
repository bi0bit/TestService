package com.ilagoproject.myapplication.apiservice.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Body")
data class CCPromoResponseBody @JvmOverloads constructor(@field:Element(name = "GetCCPromoResponse") var data: CCPromoResponseData = CCPromoResponseData())
