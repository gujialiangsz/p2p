<div class="page">
    <div class="pageContent">
        <div class="pageFormContent" layoutH="50">
            <dl>
                <dt>用户名称：</dt>
                <dd><@s.input path="coding.userinfo.nickname" size="30" readOnly="readonly" /></dd>
            </dl>
            <dl>
                <dt>账号类型：</dt>
                <dd><input value="<@system.getValue path="${coding.type}" items=userTypes itemKey="code" itemValue="codeName"/>" readOnly="readonly" /></dd>
            </dl>
            <dl>
                <dt>用户电话：</dt>
                <dd><@s.input path="coding.mobile"  size="30" readOnly="readonly" /></dd>
            </dl>
            <dl>
                <dt>用户邮箱：</dt>
                <dd><@s.input path="coding.email" size="30"  readOnly="readonly"/></dd>
            </dl>
            <dl>
                <dt>用户状态：</dt>
                <dd><input value="<@system.getValue path="${coding.status}" items=userStatus itemKey="code" itemValue="codeName"/>"  readOnly="readonly" /></dd>
            </dl>
            <dl>
                <dt>注册IP：</dt>
                <dd><@s.input path="coding.reg_ip" size="30"  readOnly="readonly"/></dd>
            </dl>
        </div>
    </div>
</div>
