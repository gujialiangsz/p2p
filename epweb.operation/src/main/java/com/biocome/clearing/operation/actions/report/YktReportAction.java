package com.biocome.clearing.operation.actions.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biocome.base.security.annotations.Auth;

/**
 * 一卡通报表。
 */
@Controller
@RequestMapping("/report")
@Auth({ "CZDZBB_V", "CSHDZBB_V", "TKXKDZBB_V" })
public class YktReportAction {

}
