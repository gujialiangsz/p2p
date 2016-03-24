package com.biocome.clearing.operation.actions.data;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biocome.base.model.Page;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.util.DateUtils;
import com.biocome.clearing.core.entity.account.AttendanceRecord;
import com.biocome.clearing.core.entity.account.TinyTradeRecordList;
import com.biocome.clearing.core.entity.account.WccOriginalTradeRecord;
import com.biocome.clearing.core.model.data.AttendanceSearchModel;
import com.biocome.clearing.core.model.data.TradeSearchModel;
import com.biocome.clearing.core.service.data.AttendanceService;
import com.biocome.clearing.core.service.data.TinyTradeRecordService;
import com.biocome.clearing.core.service.data.WccOriginalTradeService;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.service.data.BaseDataService;

/**
 * 数据明细。
 */
@Controller
@RequestMapping("/data")
@Auth({ "CZMX_V", "CSHMX_V", "TKXKMX_V", "XFMX_V", "DZWJ_A", "DZWJ_M", "DZWJ_V" })
public class DataDetailAction {

	@Resource
	TinyTradeRecordService tinyTradeRecordService;
	@Resource
	WccOriginalTradeService wccOriginalTradeService;
	@Resource
	AttendanceService attendanceService;
	@Resource
	SystemCodingService systemCodingService;
	@Resource
	BaseDataService baseDataService;

	@RequestMapping("detail-consume")
	@Auth({ "XFMX_V" })
	public void listConsume(Model model, TradeSearchModel searchModel) {
		if (searchModel == null) {
			searchModel = new TradeSearchModel();
		}
		if (searchModel.getStartTime() == null)
			searchModel.setStartTime(DateUtils.rollByMonth(new Date(), -1));
		if (searchModel.getEndTime() != null)
			searchModel.setEndTime(DateUtils.rollSecond(
					DateUtils.rollByDay(searchModel.getEndTime(), 1), -1));
		searchModel.setTradeType(systemCodingService.getSystemCodingValue(
				"TRADE_TYPE", "消费"));
		searchModel.setOrderBy("tradeDate");
		searchModel.setSort("DESC");
		Page<TinyTradeRecordList> page = tinyTradeRecordService
				.searchConsumePage(searchModel);
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("industryTypes",
				systemCodingService.getSystemCoding("INDUSTRY_TYPE"));
		model.addAttribute("clPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	@RequestMapping("detail-recharge")
	@Auth({ "CZMX_V" })
	public void listRecharge(Model model, TradeSearchModel searchModel) {
		if (searchModel == null) {
			searchModel = new TradeSearchModel();
		}
		if (searchModel.getStartTime() == null)
			searchModel.setStartTime(DateUtils.rollByMonth(new Date(), -1));
		if (searchModel.getEndTime() != null)
			searchModel.setEndTime(DateUtils.rollSecond(
					DateUtils.rollByDay(searchModel.getEndTime(), 1), -1));
		searchModel.setTradeType(systemCodingService.getSystemCodingValue(
				"TRADE_TYPE", "充值"));
		searchModel.setOrderBy("tradeDate");
		searchModel.setSort("DESC");
		Page<TinyTradeRecordList> page = tinyTradeRecordService
				.searchConsumePage(searchModel);
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("industryTypes",
				systemCodingService.getSystemCoding("INDUSTRY_TYPE"));
		model.addAttribute("clPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	@RequestMapping("detail-original")
	@Auth({ "YSJY_V" })
	public void listOriginal(Model model, TradeSearchModel searchModel) {
		if (searchModel == null) {
			searchModel = new TradeSearchModel();
		}
		if (searchModel.getSyncStartDate() == null)
			searchModel.setSyncStartDate(DateUtils.rollByMonth(new Date(), -1));
		if (searchModel.getSyncEndDate() != null)
			searchModel.setSyncEndDate(DateUtils.rollSecond(
					DateUtils.rollByDay(searchModel.getSyncEndDate(), 1), -1));
		searchModel.setOrderBy("syncDate");
		searchModel.setSort("DESC");
		Page<WccOriginalTradeRecord> page = wccOriginalTradeService
				.searchOriginalDataPage(searchModel);
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("industryTypes",
				systemCodingService.getSystemCoding("INDUSTRY_TYPE"));
		model.addAttribute("dealStatus",
				systemCodingService.getSystemCoding("DEAL_STATUS"));
		model.addAttribute("tradeTypes",
				systemCodingService.getSystemCoding("TRADE_TYPE"));
		model.addAttribute("clPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	@RequestMapping("detail-attendance")
	@Auth({ "KQJL_V" })
	public void listAttendance(Model model, AttendanceSearchModel searchModel) {
		if (searchModel == null) {
			searchModel = new AttendanceSearchModel();
		}
		if (searchModel.getBeginDate() == null)
			searchModel.setBeginDate(DateUtils.rollByMonth(new Date(), -1));
		if (searchModel.getEndDate() != null)
			searchModel.setEndDate(DateUtils.rollSecond(
					DateUtils.rollByDay(searchModel.getEndDate(), 1), -1));
		searchModel.setOrderBy("recordDay");
		searchModel.setSort("DESC");
		Page<AttendanceRecord> page = attendanceService.searchPage(searchModel);
		model.addAttribute("clPage", page);
		model.addAttribute("devices",
				baseDataService.getAllAttendanceDevices(true));
		model.addAttribute("searchModel", searchModel);
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
