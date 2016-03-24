<div class="pageHeader">
    <@s.form id="pagerForm" action="/app/appVersion-list" class="pageForm required-validate" onsubmit="return navTabSearch(this);">
    <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
    <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
    <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
    <input type="hidden" name="sort" value="${searchModel.sort}" />
    <div class="searchBar">
                <div class="searchContent">
                    <ul>
                        <li>    
                            <label>应用类型：</label>                         
                            <select name="type" class="required combox" style="width: 200px">
                               <option value="">全部</option>
                               <#list apptypes as t>
                                <option <#if t.code==searchModel.type>selected="selected"</#if> value=${t.code}>${t.codeName}</option>
                               </#list>
                           </select>
                        </li>
                        </ul>
                        
                         <div class="subBar">
                            <ul>
                                <li><div class="buttonActive"><button type="submit" class="a_button" style="width: 50px">查询</button></div></li>
                                <li><div class="buttonActive"><button type="button" class="a_button" onclick="$('#pagerForm input[type=text]').val('')">重置 </button></div></li>
                            </ul>
                         </div>
                </div>
            </div>
</@s.form>  
</div>
<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a href="<@s.url "/app/appVersion-add" />" target="dialog" height="550" width="1000" rel="app-appVersion-add" mask="true" title="新增应用版本信息">
                <span class="a09">新增</span>
            </a>
        </li>
    </ul>
</div>
<div class="pageContent">  
        <table class="table" width="100%"  layoutH="126">
            <thead>
                <tr>
                    <th align="center" width="110">应用名称</th>
                    <th align="center">版本名称</th>
                    <th align="center">版本编码</th>
                    <th align="center" width="200">可升级版本</th>
                    <th align="center" width="250">下载地址</th>
                    <th align="center">MD5</th>
                    <th align="center">强制升级</th>
                    <th align="center">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list mainPage.contents as main>
                <tr target="sid_user" rel="1">
                    <td>${main.appname}</td>
                    <td>${main.versionname}</td>
                    <td>${main.versioncode}</td>
                    <td><#assign firmwarearr=main.fromversion?split(",")>
                    <#list versions as t>
                            <#if firmwarearr?seq_contains(t.versioncode)>${t.appname}&nbsp;</#if>
                    </#list></td>
                    <td>${main.url}</td>
                    <td>${main.md5}</td>
                    <td>${main.force?string('是','否')}</td>
                    <td>
                       <a href="<@s.url "/app/appVersion-edit?id=${main.id}" />" target="dialog" height="550" width="1000" rel="app-appVersion-edit" mask="true" title="编辑版本">编辑</a>
                        &nbsp;|&nbsp;
                        <a href="<@s.url "/app/appVersion-del?id=${main.id}" />" target="ajaxTodo" callback="dialogAjaxDone" title="确定要删除该版本吗？">删除</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    <div class="panelBar">
        <@dwz.pageNav pageModel=mainPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
    </div>
</div>