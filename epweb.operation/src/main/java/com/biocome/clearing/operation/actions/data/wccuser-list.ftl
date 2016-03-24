<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
<div class="pageContent">
    <div class="pageHeader">
        <@s.form id="pagerForm" method="post" class="pageForm required-validate" action="/data/wccuser-list" onsubmit="return navTabSearch(this);">
            <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
            <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
            <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
            <input type="hidden" name="sort" value="${searchModel.sort}" />
            <div class="searchBar">
                <div class="searchContent">
                    <ul>
                        <li>    
                            <label>账号ID：</label>                         
                            <input type="text" name="id" value="${searchModel.id}" class="digits"/>
                        </li>
                        <li>    
                            <label>登录账号：</label>                         
                            <input type="text" name="mobile" value="${searchModel.mobile}" class="digits"/>
                        </li>
                        <li>
                            <label> 昵称：</label>
                            <input type="text" name="nickname" value="${searchModel.nickname}" />
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
    <table class="table" width="100%" layoutH="100">
        <thead>
            <tr>          
                <th align="center" width="10%">登录账号</th>
                <th align="center" width="10%">昵称</th>
                <th align="center" width="4%">性别</th>
                <th align="center" width="10%">生日</th>
                <th align="center" width="10%">最后登陆</th>
                <th align="center" width="10%">注册时间</th>
                <th align="center" width="10%">注册IP</th>
            </tr>
        </thead>
        <tbody>
            <#list userPage.contents as user>
            <tr target="sid_user" rel="1">
                <td><a href="<@s.url "/data/basedata-getUser?id=${user.id}" />" target="dialog" rel="data_user_detail"  height="300" width="450" mask="true" title="用户资料">${user.mobile}</a></td>
                <td>${user.userinfo.nickname}</td>
                <td>${(user.userinfo.gender==1)?string('男','女')}</td>
                <td>${(user.userinfo.birthday?string('yyyy-MM-dd'))!''}</td>
                <td>${(user.update_time?string('yyyy-MM-dd'))!''}</td>
                <td>${user.reg_time?number?number_to_datetime}</td>
                <td>${user.reg_ip}</td>
            </tr>
            </#list>
        </tbody>
    </table>
    <div class="panelBar">
        <@dwz.pageNav pageModel=userPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
    </div>
</div>
