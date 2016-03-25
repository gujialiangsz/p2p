
<div class="pageContent" >
    <@s.form action="/system/save-user-info" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
    <div class="pageFormContent" layoutH="55">
        <h2 class="contentTitle">用户信息</h2>
        <div class="divider" />
        <@s.hidden path="userInfoModel.userNo" />
        <dl>
            <dt>联系电话：</dt>
            <dd>
                <@s.input path="userInfoModel.telPhone" type="text"  size="30" maxlength="15" class="phone"/></dd>
        </dl>
        <dl>
            <dt>办公电话：</dt>
            <dd><@s.input path="userInfoModel.phone" size="30" maxlength="15"  /></dd>
        </dl>
        <dl>
            <dt>邮箱：</dt>
            <dd>
                <@s.input path="userInfoModel.email" size="30" maxlength="30" class="email"  />
            </dd>
        </dl>
         <dl>
            <dt>QQ号：</dt>
            <dd><@s.input path="userInfoModel.qq" size="30" maxlength="11"  class="digits" /></dd>
        </dl>
        <div class="divider" />
        <h2 class="contentTitle"><span class="a_button" style="font-weight: bolder" onclick="$('.pwdinput').toggle();if($(this).text()=='修改密码') $(this).text('取消修改');else $(this).text('修改密码');">修改密码</span></h2>
        
         <dl class="pwdinput" style="display: none">
            <dt>原密码：</dt>
            <dd>
                <@s.password path="userInfoModel.oldPwd" id="old_password"   class="alphanumeric textInput" minlength="1"  alt="只能输入字母、数字及下划线"/>
            </dd>
        </dl>
        <dl class="pwdinput" style="display: none">
            <dt>新密码：</dt>
            <dd>
                <@s.password path="userInfoModel.pwd" id="new_password"  class="alphanumeric textInput" minlength="1"  alt="只能输入字母、数字及下划线"/>
            </dd>
        </dl>
        <dl class="pwdinput" style="display: none">
            <dt>重复密码：</dt>
            <dd>
                <input type="password" size="30" maxlength="20" equalto="#new_password"/>
            </dd>
        </dl>
        <div class="divider" />
        
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

<script type="text/javascript" charset="utf-8">
</script>