<div class="page">
    <div class="pageContent">
        <@s.form action="/system/actor-save" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogReloadDone)">
        <@s.hidden path="actor.user" value="${actor.user.id}" />
        <div class="pageFormContent" layoutH="60">
            <dl>
                <dt>关联部门：</dt>
                <dd><@s.select path="actor.organ" items=rootOrgan.organTree itemValue="id" itemLabel="selectText" class="required combox" /></dd>
			</dl>
			<dl>
                <dt>关联角色：</dt>
                <dd><@s.select path="actor.role" items=roles itemValue="id" itemLabel="name" class="required combox" onchange="actorAdd_roleSelectorOnChange(this)" /></dd>
            </dl>
            <dl>
                <dt>职务名称：</dt>
                <dd><@s.select path="actor.name" name="name" items=actorlist itemLabel="codeName" itemValue="code" class="required combox" />
                       &nbsp;
                       <!-- <a href="<@s.url "/baseinfo/dictinfo/baseDictItem-list?dictCode=actor&dict=true" />" target="dialog" height="480" width="800" rel="baseDictItem-list" mask="true" title="编辑职务">编辑子项</a> -->
                </dd>
            </dl>
        </div>
        <div class="formBar">
            <ul class="button">
                <li>
                    <div>
                        <button type="button" onclick="$(this).closest('form').submit()">保存</button>
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
	function actorAdd_roleSelectorOnChange(roleSelector) {
		var $roleSelector = $(roleSelector);
		var $roleName = $roleSelector.getParentUnitBox().find("input[name='actor.name']")
		$roleName.val($roleSelector.children("option:selected").text());
	}
</script>
