<div class="page">
    <div class="pageContent">
        <@s.form action="/system/coding-update" class="pageForm required-validate" onsubmit="if(!checkChar($('#codeName').val()))return false;return validateCallback(this,dialogAjaxDone)">
        <@s.hidden path="coding.id" />
        <div class="pageFormContent" layoutH="50">
            <dl>
                <dt>字典类型：</dt>
                <dd><@s.input path="coding.type" id="codeName" size="30" readOnly="readonly" /></dd>
            </dl>
            <dl>
                <dt>字典级别：</dt>
                <dd>
                    <label style="float:none; text-align: left;"><input name="systemLevel" type="radio"  value="1" <#if coding.systemLevel==1>checked="checked"</#if>/>用户级</label>
                    <label style="float:none; text-align: left;"><input name="systemLevel" type="radio"  value="0" <#if coding.systemLevel==0>checked="checked"</#if>/>系统级</label>
                    </dd>
            </dl>
            <dl>
                <dt>字典名称：</dt>
                <dd><@s.input path="coding.codeName" id="codeName" maxlength="50" size="30"  class="required"  /></dd>
            </dl>
            <dl>
                <dt>字典编码：</dt>
                <dd><@s.input path="coding.code" size="30" maxlength="20" class="required alphanumeric" /></dd>
            </dl>
            <dl>
                <dt>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注：</dt>
                <dd><@s.textarea path="coding.remark" size="30" maxlength="200" style="width: 205px;height: 50px;"/></dd>
            </dl>
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
                        <button type="button" class="close">关闭</button>
                    </div>
                </li>
            </ul>
        </div>
        </@s.form>
    </div>
</div>
<script>
    function checkChar(val){
        var arry="`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？";
        var map=new Map();
        map.putAll(arry);
        if(map.containAny(val)){
            alertMsg.info("字典名称不能包含非法字符");
            return false;
        }else{
            return true;
        }
    }
    
</script>