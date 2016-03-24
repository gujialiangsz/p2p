<div class="page">
<div class="pageContent">
     <@s.form action="/system/analysis-saveConfigList" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogReloadDone)"> 
        <div class="pageFormContent" layoutH="54">
            <fieldset style="border:0px;">
                <legend>新增解析配置明细</legend>
                <dl style="width: 430px">
                    <dt  style="width: 180px">数据源类型：</dt>
                    <dd>
                        <select name="sourceType" class="required combox">
                            <#list sourceTypes as st>
                                <option value=${st.code} <#if st.code==main.sourceType> selected="selected"</#if> >${st.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>

                <dl style="width: 430px">
                    <dt style="width: 180px">城市：</dt>
                    <dd>
                        <select name="cityCode" class="required combox">
                            <#list citys as c>
                                <option value=${c.code} <#if c.code==main.cityCode> selected="selected"</#if> >${c.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">字段名称：</dt>
                    <dd>
                        <@s.input path="main.fieldName" class="required alphanumeric"  style="width: 200px" maxlength="20" />
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">字段序号：</dt>
                    <dd>
                        <@s.input path="main.columnNo" class="required digits"  style="width: 200px" max="9999" min="0"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">开始位置：</dt>
                    <dd>
                        <@s.input path="main.dataBegin"    class="required digits"  style="width: 200px" max="9999" min="0"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">结束位置：</dt>
                    <dd>
                        <@s.input path="main.dataEnd"    class="required digits"  style="width: 200px" max="9999" min="0"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">数据类型：</dt>
                    <dd>
                        <select name="dataType" class="required combox">
                            <#list dataTypes as c>
                                <#if c.code==main.dataType>
                                <option value=${c.code} selected="selected">${c.codeName}</option>
                                <#else>
                                <option value=${c.code}>${c.codeName}</option>
                                </#if>
                            </#list>
                        </select>
                    </dd>
                </dl>

                <dl style="width: 430px">
                    <dt style="width: 180px">数据格式：</dt>
                    <dd>
                        <@s.input path="main.dataFormat"   class="textInput" maxlength="36" style="width: 200px"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">补齐规则：</dt>
                    <dd>
                        <select name="fillRule" class="required combox">
                        <#list FillRule?values as its>
                            <#if its==main.fillRule>
                            <option value=${its.value} selected="selected">${its.text}</option>
                            <#else>
                            <option value=${its.value}>${its.text}</option>
                            </#if>
                        </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">填充长度：</dt>
                    <dd>
                        <@s.input path="main.fillLength" class="required digits"  style="width: 200px" max="999" min="0"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">填充值：</dt>
                    <dd>
                      <@s.input path="main.fillFlag" class="textInput" maxlength="1" min="0" style="width: 200px"/>
                    </dd>
                </dl>
                
                <dl style="width: 430px">
                    <dt style="width: 180px">
                        默认值：
                    </dt>
                    <dd>
                        <@s.input path="main.defaultVal" class="textInput" max="999999" min="0" style="width: 200px"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">最大值：</dt>
                    <dd>
                       <@s.input path="main.max" class="alphanumeric"  style="width: 200px" maxlength="20" />
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">最小值：</dt>
                    <dd>
                       <@s.input path="main.min" class="alphanumeric"  style="width: 200px" maxlength="20" />   
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