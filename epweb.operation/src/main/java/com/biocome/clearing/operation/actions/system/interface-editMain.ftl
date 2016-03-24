<div class="page">
<div class="pageContent">
     <@s.form action="/system/interface-updateMain" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
            <fieldset style="border:0px;">
                <legend>接口配置</legend>
                <dl style="width: 430px">
                    <dt style="width: 180px">名称：</dt>
                    <dd>
                        <@s.input path="main.name"   class="required textInput" maxlength="50" style="width: 200px"/>
                        <@s.hidden path="main.id" />
                        <@s.hidden path="main.group.id" />
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">接口地址：</dt>
                    <dd>
                        <@s.input path="main.faceurl"   class=" " maxlength="36" style="width: 200px"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">完整地址：</dt>
                    <dd>
                        <@s.input path="main.url"   class=" " maxlength="36" style="width: 200px"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">IP：</dt>
                    <dd>
                        <@s.input path="main.ip"   class="required textInput" maxlength="50" style="width: 200px"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt  style="width: 180px">类型：</dt>
                    <dd>
                        <select name="type" class="required combox">
                            <#list types as s>
                            <option value="${s.code}" <#if main.type==s.code>selected="selected"</#if> >${s.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>

                <dl style="width: 430px">
                    <dt style="width: 180px">状态：</dt>
                    <dd>
                        <select name="status" class="required combox">
                            <#list statuss as c>
                            <option value="${c.code}" <#if main.status.value==c.code>selected="selected"</#if> >${c.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">方式：</dt>
                    <dd>
                        <select name="methods" class="required combox">
                            <#list methods as c>
                            <option value="${c.code}" <#if main.methods==c.code>selected="selected"</#if> >${c.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">权限：</dt>
                    <dd>
                        <select name="access_authority" class="required combox">
                            <#list auths as c>
                            <option value="${c.code}" <#if main.access_authority==c.code>selected="selected"</#if> >${c.codeName}</option>
                            </#list>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">使用Https：</dt>
                    <dd>
                        <select name="https" class="required combox">
                            <option value="true" <#if main.https>selected="selected"</#if> >是</option>
                            <option value="false" <#if main.https==false>selected="selected"</#if>>否</option>
                            <#if https>
                            </#if>
                        </select>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">每日访问频率：</dt>
                    <dd>
                        <@s.input path="main.freq_d" class="required digits"  style="width: 200px" max="999999999" min="0"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">每分钟访问频率：</dt>
                    <dd>
                        <@s.input path="main.freq_m" class="required digits"  style="width: 200px" max="999999" min="0"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">接口说明：</dt>
                    <dd>
                        <@s.input path="main.text"   class="required text" maxlength="255" style="width: 200px"/>

                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">接口返回：</dt>
                    <dd>
                        <@s.input path="main.returntext"   class="text" maxlength="255" style="width: 200px"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 180px">APP：</dt>
                    <dd>
                       <@s.checkboxs path="main.appid" items=apps itemValue="id" itemLabel="name" />
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