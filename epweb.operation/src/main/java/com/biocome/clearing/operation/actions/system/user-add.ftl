<div class="page">
    <div class="pageContent">
        <@s.form action="/system/user-save" id="pageForm" class="pageForm required-validate" onsubmit="$('#userNo').attr('name','userNo');if(!checkDate())return false;return validateCallback(this,dialogAjaxDone)">
        <div class="pageFormContent" layoutH="54">
        	<h2 class="contentTitle">用户信息</h2>
			<div class="divider" />
            <dl style="width:440px;">
                <dt>姓名：</dt>
                <dd>
                    <@s.input path="user.name" name="user.name" type="text" readonly="readonly" size="30" maxlength="20"  id ="systemUserName"/></dd>
                    <a rel="select-employee" href="<@s.url "/system/user-relation" />" target="dialog" height="480" width="900" mask="true" class="a_button">选 择</a>
            </dl>
            <dl>
                <dt>工号：</dt>
                <dd><@s.input path="user.userNo" id="userNo" name="user.userNo" size="30" maxlength="20" readonly="readonly" class="required textInput" id="systemUserNo" />
                    <input type="hidden" name="actor.userNo" />
                </dd>
			</dl>
            <dl style="width:440px;">
                <dt>用户名：</dt>
                <dd>
                    <@s.input path="user.username"  size="30"  maxlength="20" class="required  alphanumeric"/>
                </dd>
            </dl>
            <dl>
                <dt>电话：</dt>
                <dd>
                    <@s.input path="user.phone"  size="30"  maxlength="20" id="systemPhone" class="required  phone"/>
                </dd>
            </dl>
            <dl style="width:440px;">
                <dt>密码：</dt>
                <dd>
                    <@s.password path="user.password" id="check_password"  size="30"  maxlength="20" class="required alphanumeric" minlength="6"/>
                </dd>
            </dl>
            <dl>
                <dt>重复密码：</dt>
                <dd>
                    <input type="password" size="30" maxlength="20" class="required" equalto="#check_password"/>
                </dd>
            </dl>
            <dl style="width:440px;">
                <dt>生效时间：</dt>
                <dd><@s.input path="user.effectiveDate" size="30" maxlength="20" class="required date" datefmt="yyyy-MM-dd" readonly="readonly" id="starttime"/></dd>
            </dl>
            <dl>
                <dt>过期时间：</dt>
                <dd><@s.input path="user.invalidDate"  size="30" maxlength="20" class="required date" datefmt="yyyy-MM-dd" readonly="readonly" id="endtime"/></dd>
            </dl>
			<div class="divider" />
			<h2 class="contentTitle">默认职务</h2>
			<div class="divider" />
            <dl>
                <dt>关联部门：</dt>
                <dd><@s.select path="actor.organ" items=rootOrgan.organTree itemLabel="selectText" itemValue="id" class="required combox" /></dd>
			</dl>
			<dl>
                <dt>关联角色：</dt>
                <dd><@s.select path="actor.role" items=roles itemLabel="name" itemValue="id" class="required combox" onchange="userAdd_roleSelectorOnChange(this)" /></dd>
            </dl>
            <dl>
                <dt>职务名称：</dt>
                <dd>
                    <dd><@s.select path="actor.name" name="actor.name" items=actorlist itemLabel="codeName" itemValue="code" class="required combox" />
                    </dd>
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
     function checkDate(){
        var a=$("#starttime").val();
        var b=$("#endtime").val();
        if(a==""||a.length==0){
            alertMsg.info("请填写生效日期");
            return false;
        }
        if(b==""||b.length==0){
            alertMsg.info("请填写过期日期");
            return false;
        }
        if(Date.parse(a)>Date.parse(b)){
            alertMsg.info("生效日期不能大于过期日期");
            return false;
        };
        
        return true;
    }
	function userAdd_roleSelectorOnChange(roleSelector) {
		var $roleSelector = $(roleSelector);
		var $roleName = $roleSelector.getParentUnitBox().find("input[name='defaultActor.name']")
		$roleName.val($roleSelector.children("option:selected").text());
	}
</script>