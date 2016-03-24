package com.biocome.clearing.operation.model.system;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.model.SearchModel;

@Restriction
public class InterfaceConfigSearchModel extends SearchModel {
	private Long groupId;
	@Restriction(propertyName = "faceid")
	private Long interfaceId;
	private Long parameterId;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}

	public Long getParameterId() {
		return parameterId;
	}

	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}

}
