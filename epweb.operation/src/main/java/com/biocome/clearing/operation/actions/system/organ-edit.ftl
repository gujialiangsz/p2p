<div class="page">
    <div class="pageContent">   
    <@s.form action="/system/organ-update" class="pageForm required-validate" onsubmit="if(!checkChar($('#organNameEdit').val()))return false;return validateCallback(this,navTabAjaxDone)">
    <@s.hidden path="organ.id" />
    <div class="pageFormContent" layoutH="60">
    	<#if organ.parent??>
        <dl class="nowrap">
            <dt>上级部门：</dt>
            <dd><@s.select path="organ.parent" items=parentOrgans  itemValue="id" itemLabel="selectText" class="combox" id="parentOrgan"/></dd>
        </dl>
    	<div class="divider" />
    	</#if>
        <dl>
            <dt>部门名称：</dt>
            <dd>
            <@s.input path="organ.name" id="organNameEdit" size="30" maxlength="40" class="required" />
            </dd>
        </dl>
        <dl>
            <dt>序号：</dt>
            <dd>
            <@s.input path="organ.ordinal" id="ordinalNum" size="30" min="0" max="99999" class="required digits" />
            </dd>
        </dl>
    </div>
    <div class="formBar">
        <ul>
        	<li>
            	<a class="a_button" href="<@s.url "/system/organ-add?organId=${organ.id}" />" target="dialog" rel="organ-add" mask="true" width="800" height="480"><span style="text-align:center;line-height:28px;">新增</span></a>
    		</li>
            <li>
                <div class="button">
                    <button type="submit">保存编辑</button>
                </div>
            </li>
            <li>
                <#if hasChild>
                    <a class="a_button" href="javascript:;" onclick="alertMsg.info('该部门含有子部门不可删除')"><span style="text-align:center;line-height:28px;">删除</span></a>
                <#elseif hasActor>
                    <a class="a_button" href="javascript:;" onclick="alertMsg.info('该部门含有关联用户不可删除')"><span style="text-align:center;line-height:28px;">删除</span></a>
                <#else>
                    <a class="a_button" href="<@s.url "/system/organ-delete?organId=${organ.id}" />" target="ajaxTodo" title="确认删除该部门？"><span style="text-align:center;line-height:28px;">删除</span></a>
                </#if>
            </li>
        </ul>
    </div>
    </@s.form>
    </div>
</div>
<script>
    //$("select option[selected!='selected']").remove();
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