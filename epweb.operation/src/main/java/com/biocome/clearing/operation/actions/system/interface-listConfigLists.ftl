<div class="pageContent">
    <@s.form id="pagerForm" action="/system/analysis-listConfigList" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogReloadDone);">
    <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
    <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
    <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
    <input type="hidden" name="sort" value="${searchModel.sort}" />
    <input type="hidden" name="interfaceId" value="${searchModel.interfaceId}" />
    </@s.form>
<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a href="<@s.url "/system/interface-addConfigList?mainId=${searchModel.interfaceId}" />" target="dialog" height="280" width="950" rel="system-interface-addConfigList" mask="true" title="新增参数配置明细">
                <span class="a09">新增</span>
            </a>
        </li>
    </ul>
</div>
        <table class="table" width="100%"  layoutH="100">
            <thead>
                <tr>
                    <th align="center">名称</th>
                    <th align="center">参数类型</th>
                    <th align="center">请求类型</th>
                    <th align="center">参数值</th>
                    <th align="center">允许为空</th>
                    <th align="center">格式表达式</th>
                    <th align="center">排序</th>
                    <th align="center">操作时间</th>
                    <th align="center">说明</th>
                    <th align="center">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list listPage.contents as main>
                <tr target="sid_user" rel="1">
                    <td><font color="<#if main.species=='1'>red<#else>green</#if>">${main.name}</font></td>
                    <td><@system.getValue path="${main.type}" items=types itemKey="code" itemValue="codeName"/></td>
                    <td><@system.getValue path="${main.species}" items=species itemKey="code" itemValue="codeName"/></td>
                    <td>${main.value}</td>
                    <td><font color="<#if main.isnull>black<#else>red</#if>">${main.isnull?string('是','否')}</font></td>
                    <td>${main.reg}</td>
                    <td>${main.order}</td>
                    <td>${main.updatetime}</td>
                    <td>${main.nametext}</td>
                    <td>
                       <a href="<@s.url "/system/interface-editConfigList?id=${main.id}" />" target="dialog" height="280" width="950" mask="true" title="编辑参数明细">编辑</a>
                        &nbsp;|&nbsp;
                        <a href="<@s.url "/system/interface-delConfigList?id=${main.id}" />" target="ajaxTodo" callback="dialogReloadDone" title="确定要删除该参数配置吗？">删除</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    <div class="panelBar">
        <@dwz.pageNav pageModel=listPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
    </div>
</div>