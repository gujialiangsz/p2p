<div class="page">
    <div class="pageContent">
        <@s.form action="/system/user-pwd-reset-save" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)">
		<@s.hidden path="user.id" />
        <div class="pageFormContent" layoutH="60">
            <dl>
                <dt>用户名：</dt>
                <dd>${user.username}</dd>
            </dl>
            <dl>
                <dt>用户姓名：</dt>
                <dd>${user.name}</dd>
            </dl>
            <dl>
                <dt>请输入您的原密码：</dt>
                <dd>
                    <input type="password" name="originalpassword" size="30" maxlength="20" class="required" alt="请输入您的原始密码" />
                </dd>
            </dl>
            <dl>
                <dt>请输入您的新密码：</dt>
                <dd>
                    <input type="password" name="newpassword" id="check_password"  size="30"  maxlength="20" class="required alphanumeric textInput" minlength="6"  alt="只能输入字母、数字及下划线" />
                </dd>
            </dl>
            <dl>
                <dt>请重复您的新密码：</dt>
                <dd>
                    <input type="password"  size="30" maxlength="20" class="required" equalto="#check_password" alt="请重复您的新密码" />
                </dd>
            </dl>
        </div>
        <div class="formBar">
            <ul>
                <li class="button">
                    <div>
                        <button type="submit">确定</button>
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
