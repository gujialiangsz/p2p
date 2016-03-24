<div class="page">
<div class="pageContent">
     <@s.form action="/system/interface-saveConfigList" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogReloadDone)"> 
        <div class="pageFormContent" layoutH="54">
            <fieldset style="border:0px;">
                <dl style="width: 430px">
                    <dt  style="width: 180px">参数名称：</dt>
                    <dd><@s.input path="main.name" size="30" maxlength="60" class="required text" />
                        <@s.hidden path="main.mainConfig.id" />
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">格式表达式：</dt>
                    <dd><@s.input path="main.reg" size="30" maxlength="255" class="" /></dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">参数值：</dt>
                    <dd><@s.input path="main.value" size="30" maxlength="60" class="" /></dd>
                </dl>
                
                <dl style="width: 430px">
                    <dt style="width: 180px">数据类型：</dt>
                    <dd>
                        <select name="type" class="required combox">
                            <#list types as c>
                                <option value=${c.code} <#if c.code==main.type> selected="selected"</#if> >${c.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">参数序号：</dt>
                    <dd>
                        <@s.input path="main.order" class="required digits" max="9999" min="0"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">请求类型：</dt>
                    <dd>
                        <select name="species" class="required combox">
                            <#list species as c>
                                <option value=${c.code} <#if c.code==main.species> selected="selected"</#if> >${c.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">说明：</dt>
                    <dd><@s.input path="main.nametext" size="30" maxlength="255" class="" /></dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">允许为空：</dt>
                    <dd>
                        <select name="isnull" class="required combox">
                            <option value="true" <#if isnull>selected="selected"</#if> >是</option>
                            <option value="false" <#if isnull==false>selected="selected"</#if>>否</option>
                            <#if https>
                            </#if>
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