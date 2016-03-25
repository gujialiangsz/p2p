<#macro organTree organ isRootOrgan="true">
<ul <#if isRootOrgan> class="tree treeFolder" mark="organ"</#if>>
    <li>
		<a href="<@s.url "/system/organ-edit?organId=${organ.id}" />" target="ajax" rel="organBox" onclick="$('ul[mark=organ]').find('a').removeAttr('style');$(this).attr('style','color:orange;');">${organ.name}</a>
        <#list organ.validChilds as childOrgan>
        <@organTree childOrgan isRootOrgan="false" />
        </#list>
    </li>
</ul>
</#macro>


<#macro permissions permissionGroups>
<dl>
<dt style="font-weight: bold;font-size: 15px;line-height: 26px;">系统权限列表总览</dt>
<dd>
<label style="float: none"><input type="checkbox" onclick="if($(this).attr('checked')=='checked') $('input[name^=permission]').attr('checked','checked');else $('input[name^=permission]').removeAttr('checked');"/>全部选择</label>
</dd>
</dl>
<div class="divider" />
    <#list permissionGroups as permissionGroup>
        <#if permissionGroup.order!='0'>
		<fieldset>
		<legend>
		<span style="color: orange;font-size:20px" onclick="$(this).parent().nextAll().toggle();if($(this).html()=='+')$(this).html('--');else $(this).html('+');">--</span>&nbsp;&nbsp;${permissionGroup.name}
		&nbsp;&nbsp;
		<input name="permission${permissionGroup_index}" type="checkbox" onclick="var cs=$('.'+$(this).attr('name'));if($(this).attr('checked')=='checked') cs.attr('checked','checked');else cs.removeAttr('checked');"/>
		全选
		</legend>
        <#list permissionGroup.permissions as permission>
        <label  style="text-align:left" title="${permission.name}">
            <input class="permission${permissionGroup_index}" type="checkbox" name="permissionIds" value="${permission.id}"<#if permissionIds?seq_contains(permission.id)> checked="checked"</#if> />
            ${permission.name}
        </label>
        </#list>
		</fieldset>
        </#if>
    </#list>
</#macro>

<#macro getValue path items itemKey itemValue>
    <#list items as item>
    <#assign key=("item." + itemKey)?eval>
        <#if key=="${path}">
            ${("item." + itemValue)?eval}
            <#break/>
        </#if>
    </#list>
</#macro>