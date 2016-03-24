package com.biocome.clearing.operation.actions.business;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biocome.base.security.annotations.Auth;

@Controller
@RequestMapping("/business")
@Auth({ "CZYW_A", "CZYW_M", "CZYW_V", "TKXK_A", "TKXK_M", "TKXK_V", "CSHYW_A",
		"CSHYW_M", "CSHYW_V" })
public class BusinessDealAction {

}
