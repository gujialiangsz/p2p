<div class="page">
    <div class="pageContent">
        <@s.form action="/system/coding-save" class="pageForm required-validate" onsubmit="if(!checkChar($('#codeName').val()))return false;return validateCallback(this,dialogAjaxDone)">
        <div class="pageFormContent" layoutH="50">
            <dl>
                <dt>字典类型：</dt>
                <dd>
                    <div style="position:relative;">
                        <select style="width:210px;" onchange="document.getElementById('inputselect').value=this.value;" >
                            <option value="">请选择</option>
                            <#list codingTypes as t>
                                    <option val=${t} onclick="$('#inputselect').val($(this).html())" >${t}</option>
                            </#list>
                        </select>
                        <input name="type" id="inputselect" maxlength="20" class="required alphanumeric"  style="position:absolute;width:185px;height:15px;left:1px;top:3px;border-bottom:0px;border-right:0px;border-left:0px;border-top:0px;background:#fff;" >
                    </div>
                </dd>
            </dl>
            <dl>
                <dt>字典级别：</dt>
                <dd>
                    <label style="float:none; text-align: left;"><input name="systemLevel" type="radio"  value="1" checked="checked"/>用户级</label>
                    <label style="float:none; text-align: left;"><input name="systemLevel" type="radio"  value="0" />系统级</label>
                    </dd>
            </dl>
            <dl>
                <dt>字典名称：</dt>
                <dd><@s.input path="coding.codeName" id="codeName" size="30" maxlength="50" class="required"/></dd>
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