<div class="page">
    <div class="pageContent">
        <@s.form action="/system/actor-update" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogReloadDone)">
        <@s.hidden path="actor.id" />
        <div class="pageFormContent" layoutH="60">
        	<dl>
                <dt>关联部门：</dt>
                <dd><@s.select path="actor.organ" items=rootOrgan.organTree itemValue="id" itemLabel="selectText" value="actor.organ.id" class="required combox" /></dd>
			</dl>
			<dl>
                <dt>关联角色：</dt>
                <dd><@s.select path="actor.role" items=roles itemValue="id" itemLabel="name" value="actor.role.id" class="required combox" onchange="actorEdit_roleSelectorOnChange(this)" /></dd>
            </dl>
            <dl>
                <dt>职务名称：</dt>
                <dd><@s.select path="actor.name" items=actorlist itemLabel="codeName" itemValue="code" class="required combox" />
                       &nbsp;
                      <!--  <a href="<@s.url "/baseinfo/dictinfo/baseDictItem-list?dictCode=actor&dict=true" />" target="dialog" height="480" width="800" rel="baseDictItem-list" mask="true" title="数据字典"><img src="${ctx}/images/dict.png" border="0" /></a> -->
                </dd>
            </dl>
        </div>
        <div class="formBar">
            <ul class="button">
                <li>
                    <div>
                        <button type="submit">保存</button>
                    </div>
                </li>
                <li>
                    <div>
                        <button type="button" class="close">取消</button>
                    </div>
                </li>
            </ul>
        </div>
        </@s.form>
    </div>
</div>
<script type="text/javascript" charset="utf-8">
	function actorEdit_roleSelectorOnChange(roleSelector) {
		var $roleSelector = $(roleSelector);
		var $roleName = $roleSelector.getParentUnitBox().find("input[name='actor.name']")
		$roleName.val($roleSelector.children("option:selected").text());
	}
</script>