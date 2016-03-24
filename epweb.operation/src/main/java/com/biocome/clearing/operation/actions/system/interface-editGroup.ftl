<div class="pageContent">
    
    <div class="pageFormContent">
    <@s.form id="pagerForm" action="/system/interface-updateGroup" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
    <input type="hidden" name="id" value="${group.id}" />
    <div class="searchBar" style="padding: 5px;">
        <fieldset>
        <legend >接口分组配置</legend>
        <div id="specialconfig">
        <p style="width:100%;">
            <label style="text-align: left;">名称：</label>
            <span>
            <@s.input path="group.name" id="groupName" size="30" maxlength="60" class="required" />
            </span>
        </p>
        <p style="width:100%;">
            <label style="text-align: left;">类型：</label>
                    <select name="type" class="required combox">
                            <#list types as s>
                            <option value="${s.code}" <#if s.code==group.type>selected="selected"</#if>>${s.codeName}</option>
                            </#list>
                    </select>
        </p>
        <p style="width:100%;">
            <label style="text-align: left;">说明：</label>
            <span><@s.input path="group.text"  size="30" maxlength="60" class="required" /></span>
        </p>
        <p style="width:100%;">
           <label> 
                <div class="button">
                    <button type="submit">保存编辑</button>
                </div>
            </label>
            <label>
                <a class="a_button" href="<@s.url '/system/interface-delGroup?id=${group.id}'/>" target="ajaxTodo" title="确认删除该角色？"><span style="text-align:center;line-height:28px;">删除</span></a>
            </label>
        </p>
        <div style="clear: both;"></div>
        </div>
        </fieldset>
    </div>
</@s.form>  
</div>
<div class="panelBar">
    <ul class="toolBar">
        <li>
            <a href="<@s.url "/system/interface-addMain?id=${group.id}" />" target="dialog" height="400" width="1000" rel="system-interface-addMain" mask="true" title="新增接口配置">
                <span class="a09">新增接口</span>
            </a>
        </li>
    </ul>
</div>
<div class="pageContent">  
        <table class="table" width="100%"  layoutH="138">
            <thead>
                <tr>
                    <th align="center">接口名</th>
                    <th align="center">类型</th>
                    <th align="center">状态</th>
                    <th align="center">请求方式</th>
                    <th align="center">是否HTTPS</th>
                    <th align="center">每天访问频率</th>
                    <th align="center">每分钟访问频率</th>
                    <th align="center">访问权限</th>
                    <th align="center">操作人</th>
                    <th align="center">操作时间</th>
                    <th align="center">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list group.mainList as main>
                <tr target="sid_group" rel="1">
                    <td>${main.name}</td>
                    <td><@system.getValue path="${main.type}" items=typess itemKey="code" itemValue="codeName"/></td>
                    <td><@system.getValue path="${main.status.value}" items=statuss itemKey="code" itemValue="codeName"/></td>
                    <td><@system.getValue path="${main.methods}" items=methods itemKey="code" itemValue="codeName"/></td>
                    <td>${main.https?string('是','否')}</td>
                    <td>${main.freq_d}</td>
                    <td>${main.freq_m}</td>
                    <td><@system.getValue path="${main.access_authority}" items=auths itemKey="code" itemValue="codeName"/></td>
                    <td>${main.writers.name}</td>
                    <td>${main.updatetime?date}</td>
                    <td>
                       <a href="<@s.url "/system/interface-editMain?id=${main.id}" />" target="dialog" height="400" width="1000" rel="system-interface-editMain" mask="true" title="编辑类型">编辑</a>
                        &nbsp;|&nbsp;
                        <a href="<@s.url "/system/interface-delMain?id=${main.id}" />" target="ajaxTodo" callback="dialogAjaxDone" title="确定要删除该接口吗？">删除</a> &nbsp;|&nbsp;
                        <a href="<@s.url "/system/interface-listConfigLists?interfaceId=${main.id}" />" target="dialog" height="500" width="1100" rel="system-interface-listConfigLists" title="参数配置明细">参数配置</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
</div>
</div>