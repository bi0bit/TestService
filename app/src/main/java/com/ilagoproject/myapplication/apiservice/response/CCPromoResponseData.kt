package com.ilagoproject.myapplication.apiservice.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "GetCCPromoResponse")
data class CCPromoResponseData @JvmOverloads constructor (@field:Element(name = "GetCCPromoResult") var result: String = "")
