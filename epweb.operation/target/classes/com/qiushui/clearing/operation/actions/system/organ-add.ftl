<div class="page">
    <div class="pageContent">
        <@s.form action="/system/organ-save" class="pageForm required-validate" onsubmit="if(!checkChar($('#organNameAdd').val()))return false;return validateCallback(this,dialogAjaxDone)">
        <div class="pageFormContent" layoutH="54">
            <dl class="nowrap">
                <dt>上级部门：</dt>
                <dd><@s.select path="organ.parent" items=parentOrgans itemValue="id" itemLabel="selectText"  /></dd>
            </dl>
			<div class="divider"></div>
            <dl>
                <dt>部门名称：</dt>
                <dd><@s.input path="organ.name" id="organNameAdd" size="30" maxlength="40" class="required" /></dd>
            </dl>
            <dl>
                <dt>序号：</dt>
                <dd><@s.input path="organ.ordinal" size="30" min="0" max="99999" class="digits" /></dd>
            </dl>
        </div>
        <div class="formBar">
            <ul class="button">
                <li>
                    <div>
                        <button type="button" onclick="$(this).closest('form').submit()">
                            保存
                        </button>
                    </div>
                </li>
                <li>
                    <div>
                        <button type="button" class="close">
                            取消
                        </button>
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
            alertMsg.info("部门名不能包含非法字符");
            return false;
        }else{
            return true;
        }
    }
</script>