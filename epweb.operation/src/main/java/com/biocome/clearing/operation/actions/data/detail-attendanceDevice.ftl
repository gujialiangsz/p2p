<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/data/detail-attendanceDevice" onsubmit="if(/^[0-9]*$/.test($('#attendanceSn').val())) return  navTabSearch(this);else {alertMsg.info('考勤机编号格式错误'); return false;}">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>考勤机名称：</label>
                    <input type="text" value="${searchModel.name}" maxlength="50" name="name"/>
                </li>
                <li>
                    <label>考勤机编号：</label>
                    <input type="text" value="${searchModel.sn}"  maxlength="32" name="sn" id="attendanceSn" class="digits"/>
                </li>
                <li>
                    <label><input name="enabled" type="radio"  value="true" <#if searchModel.enabled??&&searchModel.enabled>checked="checked" </#if>/>启用</label>
                    <label><input name="enabled" type="radio"  value="false" <#if searchModel.enabled??&&!searchModel.enabled>checked="checked" </#if>/>停用</label>
            </li>
            </ul>
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><button type="submit" class="a_button" style="width: 50px">查询</button></div></li>
                    <li><div class="buttonActive"><button type="button" class="a_button" onclick="$('#pagerForm input[type=text]').val('')">重置 </button></div></li>
                </ul>
            </div>
            
        </div>
    </@s.form>
    
</div>

<div class="panelBar">
            <ul class="toolBar">
                <li>
                    <a href="<@s.url "/data/attendanceDevice-add" />" target="dialog" rel="data-attendanceDevice-add" title="新增设备" width="600" height="300"><span class="a09">新增</span> </a>
                </li>
            </ul>
</div>
<div class="pageContent">
        
        <table class="table" width="100%" layoutH="165">
            <thead style="border-top:1px solid #ccc;">
                <tr>
                    <th align="center" width="12%">名称</th>
                    <th align="center" width="12%">编号</th>
                    <th align="center" width="12%">密钥</th>
                    <th align="center" width="8%">状态</th>
                    <th align="center" width="12%">备注</th>
                    <th align="center" width="12%">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list clPage.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td><a href="<@s.url "/data/attendanceDevice-edit?id=${cl.id}" />" target="dialog" rel="data_device_edit"  height="300" width="600" mask="true" title="编辑设备">${cl.name}</a></td>
                    <td>${cl.sn}</td>
                    <td>${cl.secretKey}</td>
                    <td>${cl.enabled?string('启用','停用')}</td>
                    <td>${cl.remark}</td>
                    <td><a href="<@s.url "/data/attendanceDevice-edit?id=${cl.id}" />" target="dialog"  rel="data_attendanceDevice_edit" title="编辑设备" width="600" height="300">编辑</a>
                    &nbsp;|&nbsp;
                    <#if cl.enabled>
                    <a href="<@s.url "/data/attendanceDevice-disabled?id=${cl.id}" />" target="ajaxTodo" title="确定要停用设备${cl.name}？">停用</a>
                    <#else>
                    <a href="<@s.url "/data/attendanceDevice-enabled?id=${cl.id}" />" target="ajaxTodo" title="确定要停启用设备${cl.name}？">启用</a>
                    </#if></td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=clPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
