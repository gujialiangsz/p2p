<div class="pageHeader">
    <@s.form id="pagerForm" name="appConfigForm" action="/system/appConfig-list" class="pageForm required-validate" onsubmit="return navTabSearch(this);">
    <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
    <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
    <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
    <input type="hidden" name="sort" value="${searchModel.sort}" />
    <input type="hidden" name="disable" value="${searchModel.disable}" id="appConfigStatus" />
</@s.form>  
</div>


<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a href="<@s.url "/system/appConfig-add" />" target="dialog" height="280" width="500" rel="appConfig-add" mask="true" title="新增应用配置">
                <span class="a09">新增</span>
            </a>
        </li>
        <li>
            <input type="checkbox" <#if searchModel.disable==true>checked="checked"</#if> onchange="if($(this).attr('checked')=='checked') $('#appConfigStatus').val('true'); else $('#appConfigStatus').val('false'); $('form[name=appConfigForm]').submit();"/>显示停用
            </li>
    </ul>
</div>
<div class="pageContent">  
        <table class="table" width="100%"  layoutH="90">
            <thead>
                <tr>
                    <th>应用名称</th>
                    <th>应用密钥</th>
                    <th>应用描述</th>
                    <th>过期时间</th>
                    <th>操作人</th>
                    <th>操作时间</th>
                    <th align="center">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list codingPage.contents as coding>
                <tr target="sid_user" rel="1">
                    <td style="text-align:left">${coding.name}</td>
                    <td style="text-align:left">${coding.secret}</td>
                    <td style="text-align:left">${coding.desc}</td>
                    <td style="text-align:left">${coding.day}天</td>
                    <td style="text-align:left">${coding.operator.name}</td>
                    <td style="text-align:left">${coding.operateDate}</td>
                    <td align="center">
                        <a href="<@s.url "/system/appConfig-edit?id=${coding.id}" />" target="dialog" height="280" width="500" rel="appConfig-edit" mask="true" title="编辑类型">编辑</a>
                            &nbsp;|&nbsp;
                            <#if coding.disable==false>
                        <a href="<@s.url "/system/appConfig-disable?id=${coding.id}" />" target="ajaxTodo" title="确定停用吗？">停用</a>
                            <#else>
                        <a href="<@s.url "/system/appConfig-enable?id=${coding.id}" />" target="ajaxTodo"  title="确定启用吗？">启用</a>
                            </#if>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    <div class="panelBar">
        <@dwz.pageNav pageModel=codingPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});"/>
    </div>
    
</div>
<script>
    $("#t_reset").click(function (){
        $("#t_coding").val("");
    });
</script>