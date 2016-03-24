<div class="pageContent">
    <@s.form action="/business/agent-save" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
        <dl style="width: 440px">
            <dt style="width: 160px">代理商名称：</dt>
            <dd>
                <input name="name" class="required textInput"    maxlength="24"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">代理商电话：</dt>
            <dd>
                <input name="phone" class="required phone"    maxlength="24"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">代理商类型：</dt>
            <dd>
                <select name="type" class="required combox">
                    <#list types as c>
                        <option value=${c.code} <#if c.code==agent.type> selected="selected"</#if> >${c.codeName}</option>
                    </#list>
                </select>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">代理产品：</dt>
            <dd>
                <select name="product" class="required combox">
                    <#list products as c>
                        <option value=${c.code} <#if c.code==agent.product> selected="selected"</#if> >${c.codeName}</option>
                    </#list>
                </select>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">代理数量：</dt>
            <dd>
                <input name="num" class="required digits" min="0" max="9999999"/>
            </dd>
        </dl>
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

<script type="text/javascript">
</script>
