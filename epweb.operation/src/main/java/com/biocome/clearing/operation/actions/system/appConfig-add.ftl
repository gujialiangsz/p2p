<div class="page">
    <div class="pageContent">
        <@s.form action="/system/appConfig-save" class="pageForm required-validate" onsubmit="if(!checkChar($('#appName').val()))return false;return validateCallback(this,dialogAjaxDone)">
        <div class="pageFormContent" layoutH="50">
            <dl>
                <dt>应用名称：</dt>
                <dd><@s.input path="coding.name" id="appName" style="width: 250px" size="30" maxlength="50" class="required"/></dd>
            </dl>
            <dl>
                <dt>应用密钥：</dt>
                <dd><@s.input path="coding.secret" id="appsecret"  style="width: 250px" readonly="readonly" size="30" maxlength="50" class="required"/><button type="button" onclick="$('#appsecret').val(myuuid(32))">生成密钥</button></dd>
            </dl>
            <dl>
                <dt>应用描述：</dt>
                <dd><@s.input path="coding.desc" size="30" style="width: 250px"  maxlength="20" class="" /></dd>
            </dl>
            <dl>
                <dt>应用有效期（天）：</dt>
                <dd><@s.input path="coding.day"  style="width: 250px" size="30" maxlength="20" class="required digits" /></dd>
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
            alertMsg.info("应用名称不能包含非法字符");
            return false;
        }else{
            return true;
        }
    }
    
</script>