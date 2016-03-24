<div class="page">
    <div class="pageContent group_set">
        <@s.form action="/system/interface-saveGroup" id="pageForm" class="pageForm required-validate" onsubmit="if(!checkChar($('#groupName').val()))return false;return validateCallback(this,dialogAjaxDone)">
        <div class="pageFormContent" layoutH="54">
            <dl>
                <dt>名称：</dt>
                <dd><@s.input path="group.name" id="groupName" size="30" maxlength="60" class="required" /></dd>
            </dl>
            <dl>
                <dt>类型：</dt>
                <dd>
                    <select name="type" class="required combox">
                            <#list types as s>
                            <option value="${s.code}">${s.codeName}</option>
                            </#list>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt>说明：</dt>
                <dd><@s.input path="group.text"  size="30" maxlength="60" class="" /></dd>
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
                        <button type="button" class="close">取消</button>
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
            alertMsg.info("角色名不能包含非法字符");
            return false;
        }else{
            return true;
        }
    }
</script>