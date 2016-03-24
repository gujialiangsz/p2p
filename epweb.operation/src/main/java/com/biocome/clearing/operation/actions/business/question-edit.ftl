<style>
    input{
        width:400px;
    }
    textarea{
        width:400px;
        height:150px;
    }
</style>
<div class="pageContent">
    <@s.form action="/business/question-update" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
        <dl style="width: 600px">
            <dt style="width: 160px">名称：</dt>
            <@s.hidden path="qs.id"/>
            <dd>
                <@s.input path="qs.name" class="required textInput"    maxlength="24" readonly="readonly"/>
            </dd>
        </dl>
        <dl style="width: 600px">
            <dt style="width: 160px">电话：</dt>
            <dd>
                <@s.input path="qs.phone" class="required phone"  minlength="7"  maxlength="11" readonly="readonly"/>
            </dd>
        </dl>
        <dl style="width: 600px">
            <dt style="width: 160px">产品：</dt>
            <dd>
                <select name="product" class="required combox">
                    <#list products as c>
                    <#if c.code==qs.product>
                        <option value=${c.code}  selected="selected">${c.codeName}</option>
                    </#if> 
                    </#list>
                </select>
            </dd>
        </dl>
        <dl style="width: 600px">
            <dt style="width: 160px">状态：</dt>
            <dd>
                <select name="status" class="required combox">
                    <#if qs.status=="0">
                        <option value="0"  selected="selected">已回复</option>
                    <#else>
                        <option value="1"  selected="selected">未回复</option>
                    </#if> 
                </select>
            </dd>
        </dl>
        <dl style="width: 600px;height:150px;">
            <dt style="width: 160px">问题：</dt>
            <dd>
                <@s.textarea path="qs.question" class="required text" maxlength=2050 readonly="readonly" style="width:400px;height:150px;"/>
            </dd>
        </dl>
        <dl style="width: 600px;height:150px;">
            <dt style="width: 160px">回复：</dt>
            <dd>
                <@s.textarea path="qs.answer" class="required text" maxlengt="2050" style="width:400px;height:150px;"/>
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
