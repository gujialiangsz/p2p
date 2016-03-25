
<div class="pageContent">
	
	<div class="pageFormContent">
	 <@s.form id="pagerForm" action="/system/user-relation"  class="pageForm required-validate" onsubmit="return dialogSearch(this);">
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


	<div class="panelBar">
        <ul class="toolBar">
            <li class="">
            </li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="177">
        <thead>
            <tr>
                <th orderfield="userNo">员工号</th>
                <th orderfield="name">员工姓名</th>
                <th orderfield="sex">员工性别</th>
                <th orderfield="time">入职时间</th>
                <th orderfield="phoneNo">联系电话</th>
                <th orderfield="idNo">证件号</th>
                <th width="80" align="center">请选择</th>
            </tr>
        </thead>
        <tbody>
             <#list employeePage.contents as emp>
                <tr target="sid_user" rel="1">
                    <td>${emp.number}</td>
                    <td><a href="<@s.url "/baseinfo/employee/employee-view?employeeId=${emp.id}" />" height="548" width="1000" target="dialog" mask="true" rel="employee-view">${emp.name}</a></td>
                    <td>${emp.sex.text}</td>
                    <td>${emp.hireDate?date}</td>
                    <td>${emp.contactNumber}</td>
                    <td>${emp.idCard}</td>
                    <td>
                        <input type="radio" name="employee" onclick="selectUser();" empName="${emp.name}" empNo="${emp.number}" empPhone="${emp.contactNumber}" style="position:relative; top:4px;" />
                    <!-- <a class="btnSelect" href="javascript:$.bringBack({id:'1', name:'${emp.name}', userNo:'${emp.number}'})" title="查找带回">选择</a> -->
                </td>
                </tr>
            </#list>
        </tbody>
    </table>

    <div class="panelBar">
            <@dwz.pageNav pageModel=employeePage targetType="dialog" onchange="dialogPageBreak({numPerPage:this.value});" />
     </div>

</div>
<script type="text/javascript">
    function selectUser(){
        var emps = $("input:radio[name=employee]:checked");
        if(emps.length == 0){
              alertMsg.error("请选择用户  !");
              return false;
        }
        var emp = emps[0];
             
        $("#systemUserName").val($(emp).attr("empName"));  
        $("#systemUserNo").val($(emp).attr("empNo"));
        $("#systemPhone").val($(emp).attr("empPhone"))
        $.pdialog.close("select-employee");   
    }
    
</script>