<div class="pageHeader">
    
     <@s.form id="pagerForm" action="/system/employee-list" class="pageForm required-validate" onsubmit="return navTabSearch(this);">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
        <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>员工号：</label>
                    <input type="text" value="${searchModel.number}" maxlength="5" name="number"/>
                </li>
                <li>
                    <label>姓名：</label>
                    <input type="text" value="${searchModel.name}" maxlength="12" name="name"/>
                </li>
            </ul>
            
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><button type="submit" class="a_button">查询</button></div></li>
                    <li><div class="buttonActive"><button type="button" class="a_button" onclick="$('#pagerForm input[type=text]').val('')">重置 </button></div></li>
                </ul>
            </div>
            
        </div>
    </@s.form> 
    
</div>


<div class="pageContent">
   
    <div class="panelBar">
        <ul class="toolBar">
            <li>
                <a href="<@s.url "/system/employee-add" />" target="dialog" rel="employee-add" mask="true" width="1000" height="548" title="新增员工">
                    <span class="a09">新增</span>
                </a>
            </li>
        </ul>
    </div>
   <table class="table" width="100%" layoutH="166">
        <thead>
            <tr>
                <th align="center">员工号</th>
                <th>姓名</th>
                <th align="center">性别</th>
                <th align="center">入职时间</th>
                <th>联系电话</th>
                <th>证件号</th>
                <th align="center">操作</th>
            </tr>
        </thead>
        <tbody>
            <#list employeePage.contents as emp>
                <tr target="sid_user" rel="1">
                    <td>${emp.number}</td>
                    <td><a href="<@s.url "/system/employee-view?employeeId=${emp.id}" />" height="548" width="1000" target="dialog" mask="true" rel="employee-view">${emp.name}</a></td>
                    <td>${emp.sex.text}</td>
                    <td>${(emp.hireDate?string('yyyy-MM-dd'))!}</td>
                    <td>${emp.contactNumber}</td>
                    <td>${emp.idCard}</td>
                    <td>
                       <a href="<@s.url "/system/employee-edit?employeeId=${emp.id}" />" height="530" width="1000" target="dialog" mask="true" rel="employee-edit" title="编辑员工信息">编辑</a>
                        &nbsp;|&nbsp;
                           
                        <#if emp.workStatus=='Work'>
                           <a style="color:gray;">删除</a>
                            &nbsp;|&nbsp;
                        <#else>
                            <a href="<@s.url "/system/employee-delete?employeeId=${emp.id}" />" target="ajaxTodo" callback="navTabAjaxDone" title="您确定要删除该员工吗？" >删除</a>
                            &nbsp;|&nbsp;
                        </#if>
                        <#if emp.workStatus=='WORK'>
                        <a href="<@s.url "/system/employee-stop?employeeId=${emp.id}" />" target="ajaxTodo" callback="navTabAjaxDone" title="员工离职确认？" >&nbsp;离职&nbsp;&nbsp;</a>
                       	 &nbsp;|&nbsp;
                        <#else>
                            <a style="color:gray;">已离职</a>
                             &nbsp;|&nbsp;
                        </#if>
                           
                        <a href="<@s.url "/system/employee-view?employeeId=${emp.id}" />" height="548" width="1000" target="dialog" mask="true" rel="employee-view" title="查看员工信息">查看</a>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
     <div class="panelBar">
            <@dwz.pageNav pageModel=employeePage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
     </div>
</div>