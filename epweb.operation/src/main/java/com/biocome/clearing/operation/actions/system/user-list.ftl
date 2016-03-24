<div class="pageContent">
    <div class="pageHeader">
        <@s.form id="pagerForm" method="post" class="pageForm required-validate" action="/system/user-list" onsubmit="return navTabSearch(this);">
            <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
            <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
            <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
            <input type="hidden" name="sort" value="${searchModel.sort}" />
            <div class="searchBar">
                <div class="searchContent">
                    <ul>
                        <li>    
                            <label>登录账号：</label>                         
                            <input type="text" name="username" value="${searchModel.username}" />
                        </li>
                        <li>
                            <label> 姓名：</label>
                            <input type="text" name="name" value="${searchModel.name}" />
                        </li>
                        <li>
                            <input type="checkbox" id="showEnable" name="enabled" <#if searchModel.enabled>checked="checked"</#if> />显示停用
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
                <a href="<@s.url "/system/user-add" />" target="dialog" rel="user-add" title="新增用户" width="900" height="480"><span class="a09">新增</span> </a>
            </li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="147">
        <thead>
            <tr>          
                <th align="center">员工号</th>
                <th align="center">登录账号</th>
                <th align="center">姓名</th>
                <th align="center">生效日期</th>
                <th align="center">过期日期</th>
                <th align="center">部门名称</th>
                <th align="center">职务名称</th>
                <th align="center">角色</th>
                <th align="center">操作</th>
            </tr>
        </thead>
        <tbody>
            <#list userPage.contents as user>
            <tr target="sid_user" rel="1">
                <td>${user.userNo}</td>
                <td style="text-align:left">
                    <a href="<@s.url "/system/user-edit?userId=${user.id}" />" target="dialog" rel="system_user_edit"  width="900" height="530"title="编辑用户">${user.username}</a>
                </td>
                <td style="text-align:left">${user.name}</td>
                <td>${user.effectiveDate?date}</td>
                <td>${user.invalidDate?date}</td>
             
                <td style="text-align:left">
                    ${user.settings.defaultActor.organ.name}&nbsp;
                </td>
                <td style="text-align:left">
                    <@system.getValue path="${user.settings.defaultActor.name}" items=actorlist itemKey="code" itemValue="codeName"/>&nbsp;
                </td>
                <td style="text-align:left">
                    ${user.settings.defaultActor.role.name}&nbsp;
                </td>
                <td>
                    <a href="<@s.url "/system/user-edit?userId=${user.id}" />" target="dialog"  rel="system_user_edit" title="编辑用户" width="900" height="530">编辑</a>
                    &nbsp;|&nbsp;
                    <a href="<@s.url "/system/user-delete?userId=${user.id}" />" target="ajaxTodo" title="确定要删除<${user.name}>？">删除</a>
                    
                    &nbsp;|&nbsp;
                    <#if user.enabled>
                    <a href="<@s.url "/system/user-disable?userId=${user.id}" />" target="ajaxTodo" title="账户停用后，该用户将不能登录系统。确定停用吗？">停用</a>
                    <#else>
                    <a href="<@s.url "/system/user-enable?userId=${user.id}" />" target="ajaxTodo"  title="账户启用后，该用户将可以登录系统进行操作。确定启用吗？">启用</a>
                    </#if>&nbsp;
                    </td>
            </tr>
            </#list>
        </tbody>
    </table>
    <div class="panelBar">
        <@dwz.pageNav pageModel=userPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
    </div>
</div>
<script type="text/javascript">
    function subform () {
        $("#showEnable").attr("checked", false);
        $("#userName").val("");
       $("#trueName").val("");
      $("#pagerForm").submit();
    }
</script>
