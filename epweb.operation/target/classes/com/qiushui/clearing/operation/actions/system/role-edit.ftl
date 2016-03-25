<@s.form action="/system/role-update" cssClass="pageForm required-validate" onsubmit="if(!checkChar($('#roleName').val()))return false;return validateCallback(this,navTabAjaxDone);">
 <@s.hidden path="role.id" />
<div class="pageFormContent role_set" layoutH="60" >
    <dl>
        <dt style="float: left;">名称：</dt>
        <dd><@s.input path="role.name" id="roleName" size="30" maxlength="60" class="required" /></dd>
    </dl>
    <div class="divider" />
    <@system.permissions permissionGroups />
</div>
<div class="formBar">
    <ul>
        <li>
            <div class="button">
                <button type="submit">保存编辑</button>
            </div>
        </li>
        <li>
            <#if relationUsers>
            <a class="a_button" href="<@s.url '/system/role-delete?roleId=${role.id}'/>" id="roleuseCheck" ><span  style="text-align:center;line-height:28px;">删除</span></a>
            <#else>
            <a class="a_button" href="<@s.url '/system/role-delete?roleId=${role.id}'/>" target="ajaxTodo" title="确认删除该角色？"><span style="text-align:center;line-height:28px;">删除</span></a>
            </#if>
        </li>
    </ul>
</div>
</@s.form>
<script>
    function stretch(id){
        $("#"+id+"permissionGroup").children().find("label").toggle();
    }
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
    
    $(function(){
        $("#roleuseCheck").click(function(){
            var user="${relationUsers}";
            if(user!=null&&user!=""&&user.length>0){
                //user=user.replace(";","\r\n");
                //为什么不提示
                user="以下用户正在使用该角色，无法删除:\r\n"+user;
                alertMsg.info(user);
                return false;
            }
        });
    });
    
</script>