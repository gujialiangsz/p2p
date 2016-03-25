<div class="pageHeader">
    <@s.form id="pagerForm" action="/system/coding-list" class="pageForm required-validate" onsubmit="return navTabSearch(this);">
    <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
    <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
    <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
    <input type="hidden" name="sort" value="${searchModel.sort}" />
    
    <div class="searchBar">
        <ul class="searchContent" >
            <li>
                <label>字典类型：</label>
                <input type="text" value="${searchModel.type}" name="type" id="t_coding"/>
            </li>
            <li>
                <label>字典名称：</label>
                <input type="text" value="${searchModel.codeName}" name="codeName" id="t_coding"/>
            </li>
            <li>
                <label>字典编码：</label>
                <input type="text" value="${searchModel.code}" name="code" id="t_coding"/>
            </li>
            <li>
                    <label><input name="systemLevel" type="radio"  value="0" <#if searchModel.systemLevel = 0>checked="checked" </#if>/>系统级</label>
                    <label><input name="systemLevel" type="radio"  value="1"  <#if searchModel.systemLevel = 1>checked="checked" </#if>/>用户级</label>
                    <label><input name="systemLevel" type="radio" value="" <#if searchModel.systemLevel = null>checked="checked" </#if>/>全部</label>
            </li>
            <li>
                    <label><input name="disable" type="radio"  value="false" <#if searchModel.disable = false>checked="checked" </#if>/>启用</label>
                    <label><input name="disable" type="radio"  value="true" <#if searchModel.disable = true>checked="checked" </#if>/>停用</label>
            </li>
        </ul>
        
        <div class="subBar">
            <ul>
                <li><div class="buttonActive"><button type="submit" class="a_button">查询</button></div></li>
                <li><div class="buttonActive"><button type="button" id="t_reset" class="a_button">重置</button></div></li>
            </ul>
        </div>
    </div>
</@s.form>  
</div>


<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a href="<@s.url "/system/coding-add" />" target="dialog" height="300" width="450" rel="coding-add" mask="true" title="新增类型">
                <span class="a09">新增</span>
            </a>
        </li>
    </ul>
</div>
<div class="pageContent">  
        <table class="table" width="100%"  layoutH="200">
            <thead>
                <tr>
                    <th>字典类型</th>
                    <th>字典名称</th>
                    <th>字典编码</th>
                    <th>字典级别</th>
                    <th>字典状态</th>
                    <th>修改时间</th>
                    <th>备注</th>
                    <th align="center">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list codingPage.contents as coding>
                <tr target="sid_user" rel="1">
                    <td style="text-align:left">${coding.type}</td>
                    <td style="text-align:left">${coding.codeName}</td>
                    <td style="text-align:left">${coding.code}</td>
                    <td align="center">
                        <#if coding.systemLevel==0>系统级<#else>用户级</#if>
                    </td>
                    <td align="center">
                    <#if coding.disable==true>停用<#else>启用</#if>
                    </td>
                    <td style="text-align:left">${coding.modifyDate?date}</td>
                    <td style="text-align:left">${coding.remark}</td>
                    <td>
                        <#if coding.disable==false>
                        <a href="<@s.url "/system/coding-disable?codingId=${coding.id}" />" target="ajaxTodo" title="确定停用吗？">停用</a>
                            <#else>
                        <a href="<@s.url "/system/coding-enable?codingId=${coding.id}" />" target="ajaxTodo"  title="确定启用吗？">启用</a>
                    </#if>
                        &nbsp;|&nbsp;
                       <a href="<@s.url "/system/coding-edit?codingId=${coding.id}" />" target="dialog" height="300" width="450" rel="coding-edit" mask="true" title="编辑类型">编辑</a>
                        &nbsp;|&nbsp;
                        <a href="<@s.url "/system/coding-delete?codingId=${coding.id}" />" target="ajaxTodo" callback="dialogAjaxDone" title="确定要删除该类型吗？">删除</a>
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