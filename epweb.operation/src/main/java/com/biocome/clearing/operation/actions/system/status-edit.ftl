<div class="page">
<div class="pageContent">
     <@s.form action="/system/status-save" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
            <fieldset style="border:0px;">
                <dl style="width: 430px">
                    <dt  style="width: 180px">主机名称：</dt>
                    <dd><@s.input path="monitor.hostName" size="30" maxlength="100" class="required text" />
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">应用名称：</dt>
                    <dd><@s.input path="monitor.name" size="30" maxlength="100" class="required text" />
                        <@s.hidden path="monitor.id" />
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">启动命令：</dt>
                    <dd><@s.input path="monitor.startCommand" size="30" maxlength="255" class="" /></dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">停止命令：</dt>
                    <dd><@s.input path="monitor.stopCommand" size="30" maxlength="255" class="" /></dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">测试命令：</dt>
                    <dd><@s.input path="monitor.testCommand" size="30" maxlength="255" class="" /></dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">应用类型：</dt>
                    <dd>
                        <select name="type" class="required combox">
                            <#list appTypes as c>
                                <option value=${c.code} <#if c.code==monitor.type> selected="selected"</#if> >${c.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">是否启用：</dt>
                    <dd>
                        <select name="enabled" class="required combox">
                            <option value="1" <#if monitor.enabled>selected='selected'</#if>>启用</option>
                            <option value="0" <#if monitor.enabled==false>selected='selected'</#if>>禁用</option>
                        </select>
                    </dd>
                </dl>
            </fieldset>
        </div>
        <div class="formBar" align="center">
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