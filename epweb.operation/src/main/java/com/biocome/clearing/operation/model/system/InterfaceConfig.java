package com.biocome.clearing.operation.model.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 接口访问配置
 */
public class InterfaceConfig {

	/** 是否必须https,false:http/https true:https */
	private boolean https = false;
	/** 0:开放(不校验token),1:app,2:user,3:all */
	private short appOrUser = 2;
	/** 每分钟访问频率 */
	private int freq_m = 0;
	/** 每天访问频率 */
	private int freq_d = 0;
	/** appOrUser=0 不校验此属性，可访问的AppID，所有的token都会有appID信息 */
	private Set<Integer> appID;
	/** 参数校验 */
	private List<ParamCheck> param;

	public boolean isHttps() {
		return https;
	}

	public void setHttps(boolean https) {
		this.https = https;
	}

	public short getAppOrUser() {
		return appOrUser;
	}

	public void setAppOrUser(short appOrUser) {
		this.appOrUser = appOrUser;
	}

	public int getFreq_m() {
		return freq_m;
	}

	public void setFreq_m(int freq_m) {
		this.freq_m = freq_m;
	}

	public int getFreq_d() {
		return freq_d;
	}

	public void setFreq_d(int freq_d) {
		this.freq_d = freq_d;
	}

	public Set<Integer> getAppID() {
		return appID;
	}

	public void setAppID(Set<Integer> appID) {
		this.appID = appID;
	}

	public void setAppID(String[] appIDs) {
		if (appIDs != null && appIDs.length > 0) {
			this.appID = new HashSet<Integer>();
			for (String s : appIDs) {
				try {
					this.appID.add(Integer.parseInt(s));
				} catch (Exception e) {
					continue;
				}
			}
		}
	}

	public List<ParamCheck> getParam() {
		return param;
	}

	public void setParam(List<ParamCheck> param) {
		this.param = param;
	}

}
