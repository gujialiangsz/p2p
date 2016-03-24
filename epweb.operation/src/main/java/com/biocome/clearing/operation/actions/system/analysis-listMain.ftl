<div class="pageHeader">
    <@s.form id="pagerForm" action="/system/analysis-listMain" class="pageForm required-validate" onsubmit="return navTabSearch(this);">
    <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
    <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
    <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
    <input type="hidden" name="sort" value="${searchModel.sort}" />
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label class="msg" style="width:95px; padding-left:28px;">数据源类型：</label>
                <span>
                    <select name="sourceType" >
                            <option value="" <#if searchModel.sourceType == "">selected="selected"</#if>>全部</option>
                            <#list sourceTypes as st>
                                <option value=${st.code} <#if searchModel.sourceType == st.code>selected="selected"</#if>>${st.codeName}</option>
                            </#list>
                    </select>
                </span>
            </li>
            
            <li>
                <label class="msg" style="width:95px; padding-left:28px;">城市：</label>
                <span>
                    <select name="cityCode" >
                            <option value="" <#if searchModel.cityCode == "">selected="selected"</#if>>全部</option>
                            <#list citys as cc>
                                <option value=${cc.code} <#if searchModel.cityCode == cc.code>selected="selected"</#if>>${cc.codeName}</option>
                            </#list>
                    </select>
                </span>
            </li>
        </ul>
        <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><button id="query" type="submit">查询</button></div></li>
                    <li><div class="buttonActive"><button type="button" class="a_button" onclick="$('#pagerForm input[type=text]').val('');$('#pagerForm select').val('');">重置 </button></div></li>
                </ul>
            </div>
    </div>
</@s.form>  
</div>
<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a href="<@s.url "/system/analysis-addMain" />" target="dialog" height="400" width="1000" rel="system-analysis-addMain" mask="true" title="新增解析主配置">
                <span class="a09">新增</span>
            </a>
        </li>
    </ul>
</div>
<div class="pageContent">  
        <table class="table" width="100%"  layoutH="138">
            <thead>
                <tr>
                    <th align="center">数据源类型</th>
                    <th align="center">城市</th>
                    <th align="center">最大长度</th>
                    <th align="center">最小长度</th>
                    <th align="center">字段分隔符</th>
                    <th align="center">行分隔符</th>
                    <th align="center">处理类型</th>
                    <th align="center">监听表达式</th>
                    <th align="center">文件格式名</th>
                    <th align="center">文件分页</th>
                    <th align="center">读写分页</th>
                    <th align="center">长度检测</th>
                    <th align="center">数量检测</th>
                    <th align="center">使用解析器</th>
                    <th align="center">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list mainPage.contents as main>
                <tr target="sid_user" rel="1">
                    <td><@system.getValue path="${main.sourceType}" items=sourceTypes itemKey="code" itemValue="codeName"/></td>
                    <td><@system.getValue path="${main.cityCode}" items=citys itemKey="code" itemValue="codeName"/></td>
                    <td>${main.dataMaxLength}</td>
                    <td>${main.dataMinLength}</td>
                    <td>${main.fieldDivision}</td>
                    <td>${main.lineDivision}</td>
                    <td>${main.fileDealType}</td>
                    <td>${main.fileAcceptExpression}</td>
                    <td>${main.fileNameFormat}</td>
                    <td>${main.fileSize}</td>
                    <td>${main.pageSize}</td>
                    <td>${main.checkLength?string('是','否')}</td>
                    <td>${main.checkSize?string('是','否')}</td>
                    <td>${main.isParsed?string('是','否')}</td>
                    <td>
                       <a href="<@s.url "/system/analysis-editMain?sourceType=${main.sourceType}&cityCode=${main.cityCode}" />" target="dialog" height="400" width="1000" rel="system-analysis-editMain" mask="true" title="编辑类型">编辑</a>
                        &nbsp;|&nbsp;
                        <a href="<@s.url "/system/analysis-delMain?sourceType=${main.sourceType}&cityCode=${main.cityCode}" />" target="ajaxTodo" callback="dialogAjaxDone" title="确定要删除该类型吗？">删除</a> &nbsp;|&nbsp;
                        <a href="<@s.url "/system/analysis-listConfigLists?sourceType=${main.sourceType}&cityCode=${main.cityCode}" />" target="dialog" height="500" width="1100" rel="system-analysis-listConfigLists" mask="true" title="解析配置明细">配置明细</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    <div class="panelBar">
        <@dwz.pageNav pageModel=mainPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
    </div>
</div>