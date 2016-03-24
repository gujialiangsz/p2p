<div class="pageContent">
    <@s.form id="pagerForm" action="/system/analysis-listConfigList" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogReloadDone);">
    <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
    <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
    <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
    <input type="hidden" name="sort" value="${searchModel.sort}" />
    <input type="hidden" name="cityCode" value="${searchModel.cityCode}" />
    <input type="hidden" name="sourceType" value="${searchModel.sourceType}" />
    </@s.form>  
<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a href="<@s.url "/system/analysis-addConfigList" />" target="dialog" height="400" width="1000" rel="system-analysis-addConfigList" mask="true" title="新增解析配置明细">
                <span class="a09">新增</span>
            </a>
        </li>
    </ul>
</div>
        <table class="table" width="100%"  layoutH="100">
            <thead>
                <tr>
                    <th align="center">数据源类型</th>
                    <th align="center">城市</th>
                    <th align="center">字段名称</th>
                    <th align="center">字段序号</th>
                    <th align="center">开始位置</th>
                    <th align="center">结束位置</th>
                    <th align="center">数据类型</th>
                    <th align="center">数据格式</th>
                    <th align="center">默认值</th>
                    <th align="center">填充规则</th>
                    <th align="center">填充长度</th>
                    <th align="center">填充字符</th>
                    <th align="center">最大值</th>
                    <th align="center">最小值</th>
                    <th align="center">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list listPage.contents as main>
                <tr target="sid_user" rel="1">
                    <td>${main.sourceType}</td>
                    <td><@system.getValue path="${main.cityCode}" items=citys itemKey="code" itemValue="codeName"/></td>
                    <td>${main.fieldName}</td>
                    <td>${main.columnNo}</td>
                    <td>${main.dataBegin}</td>
                    <td>${main.dataEnd}</td>
                    <td><@system.getValue path="${main.dataType}" items=dataTypes itemKey="code" itemValue="codeName"/></td>
                    <td>${main.dataFormat}</td>
                    <td>${main.defaultVal}</td>
                    <td>${main.fillRule.text}</td>
                    <td>${main.fillLength}</td>
                    <td>${main.fillFlag}</td>
                    <td>${main.max}</td>
                    <td>${main.min}</td>
                    <td>
                       <a href="<@s.url "/system/analysis-editConfigList?sourceType=${main.sourceType}&cityCode=${main.cityCode}&fieldName=${main.fieldName}" />" target="dialog" height="400" width="1000" mask="true" title="编辑明细">编辑</a>
                        &nbsp;|&nbsp;
                        <a href="<@s.url "/system/analysis-delConfigList?sourceType=${main.sourceType}&cityCode=${main.cityCode}&fieldName=${main.fieldName}" />" target="ajaxTodo" callback="dialogReloadDone" title="确定要删除该类型吗？">删除</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    <div class="panelBar">
        <@dwz.pageNav pageModel=listPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
    </div>
</div>