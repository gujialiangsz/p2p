<div class="page">
<div class="pageContent">
     <@s.form action="/system/analysis-updateMain" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
            <fieldset style="border:0px;">
                <legend>解析主配置</legend>
                <dl style="width: 430px">
                    <dt  style="width: 180px">数据源类型：</dt>
                    <dd>
                        <select name="sourceType" >
                            <#list sourceTypes as st>
                                <#if st.code==main.sourceType>
                                <option value=${st.code}>${st.codeName}</option>
                                </#if>
                            </#list>
                        </select>
                    </dd>
                </dl>

                <dl style="width: 430px">
                    <dt style="width: 180px">城市：</dt>
                    <dd>
                        <select name="cityCode" >
                            <#list citys as c>
                                <#if c.code==main.cityCode>
                                <option value=${c.code}>${c.codeName}</option>
                                </#if>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">数据最大长度：</dt>
                    <dd>
                        <@s.input path="main.dataMaxLength" class="required digits"  style="width: 200px" max="9999" min="0"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">数据最小长度：</dt>
                    <dd>
                        <@s.input path="main.dataMinLength" class="required digits"  style="width: 200px" max="9999" min="0"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">字段分割符：</dt>
                    <dd>
                        <@s.input path="main.fieldDivision"   class="text" maxlength="10" style="width: 200px"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">行分隔符：</dt>
                    <dd>
                        <@s.input path="main.lineDivision"   class="text" maxlength="10" style="width: 200px"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">监听表达式：</dt>
                    <dd>
                        <@s.input path="main.fileAcceptExpression"   class="text" maxlength="36" style="width: 200px"/>

                    </dd>
                </dl>

                <dl style="width: 430px">
                    <dt style="width: 180px">文件名格式：</dt>
                    <dd>
                        <@s.input path="main.fileNameFormat"   class=" " maxlength="36" style="width: 200px"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">
                        文件分页：
                    </dt>
                    <dd>
                        <@s.input path="main.fileSize"   class="required digits" max="999999" min="0" style="width: 200px"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">读写分页：</dt>
                    <dd>
                       <@s.input path="main.pageSize"   class="required digits" min="0"  maxlength="999999" style="width: 200px"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">是否检测长度：</dt>
                    <dd>
                       <select name="checkLength" class="required combox" style="width: 200px">
                       <option value=true <#if main.checkLength> selected="selected"</#if>>是</option>
                       <option value=false <#if !main.checkLength> selected="selected"</#if>>否</option>    
                       </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">是否检测数量：</dt>
                    <dd>
                       <select name="checkSize" class="required combox" style="width: 200px">
                       <option value=true <#if main.checkSize> selected="selected"</#if>>是</option>
                       <option value=false <#if !main.checkSize> selected="selected"</#if>>否</option>    
                       </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">是否使用解析器：</dt>
                    <dd>
                       <select name="isParsed" class="required combox" style="width: 200px">
                       <option value=true <#if main.isParsed> selected="selected"</#if> >是</option>
                       <option value=false <#if !main.isParsed> selected="selected"</#if>>否</option>    
                       </select>
                    </dd>
                </dl>
            </fieldset>
        </div>
        <div class="formBar" align="center">
            <ul class="button">
                <li>
                    <div>
                        <button type="button"  class="submit" >保存</button>
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