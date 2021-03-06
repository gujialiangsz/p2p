package com.qiushui.base.model;

import java.util.Date;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.joda.time.DateTime;

import com.qiushui.base.exception.BusinessException;
import com.qiushui.base.util.DateUtils;

/**
 * 日期区间查询条件模型。
 */
public class DateRangeSearchModel extends SearchModel {
	/** 起始日期 */
	private Date startDate;
	/** 截止日期 */
	private Date endDate;

	/**
	 * 生成日期区间全文搜索查询对象。
	 * 
	 * @param searchField
	 *            待查询的全文搜索字段
	 * 
	 * @return 返回日期区间全文搜索查询对象。
	 */
	public Query genQuery(String searchField) {
		if (startDate == null && endDate == null) {
			return null;
		}
		String startDateStr = null;
		if (startDate != null) {
			startDateStr = DateUtils.format(startDate);
		}
		String endDateStr = null;
		if (endDate != null) {
			DateTime endDateTime = new DateTime(endDate).plusDays(1);
			endDateStr = DateUtils.format(endDateTime.toDate());
		}
		TermRangeQuery showTimeQuery = new TermRangeQuery(searchField,
				startDateStr, endDateStr, true, false);
		return showTimeQuery;
	}

	/**
	 * 验证日期区间设置有效性。
	 */
	public void validate() {
		if (startDate != null && endDate != null) {
			if (startDate.after(endDate)) {
				throw new BusinessException("查询起始日期不能大于截止日期。");
			}
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}