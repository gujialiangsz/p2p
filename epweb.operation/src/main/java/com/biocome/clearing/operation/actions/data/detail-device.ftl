<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/data/detail-device" onsubmit="if(/^[0-9]*$/.test($('#usermobile').val())) return  navTabSearch(this);else {alertMsg.info('账号格式错误'); return false;}">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>蓝牙地址：</label>
                    <input type="text" value="${searchModel.bluetooth}" maxlength="24" name="bluetooth"/>
                </li>
                <li>
                    <label>账号：</label>
                    <input type="text" value="${searchModel.userId}"  maxlength="20" name="userId" id="usermobile" class="digits"/>
                </li>
                 <li>
                    <label>昵称：</label>
                    <input type="text" value="${searchModel.nickname}" maxlength="24" name="nickname" class="text"/>
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
                    <a href="<@s.url "/data/device-add" />" target="dialog" rel="data-device-add" title="新增设备" width="600" height="350"><span class="a09">新增</span> </a>
                </li>
            </ul>
            <ul class="toolBar">
                <li>
                    <a href="<@s.url "/data/device-import" />" target="dialog" rel="data-device-import" title="导入设备" width="800" height="250"><span class="a16">导入</span> </a>
                </li>
            </ul>
</div>
<div class="pageContent">
        
        <table class="table" width="100%" layoutH="165">
            <thead style="border-top:1px solid #ccc;">
                <tr>
                    <th align="center" width="12%">蓝牙地址</th>
                    <th width="8%">用户手机</th>
                    <th width="8%">昵称</th>
                    <th align="center" width="12%">签名</th>
                    <th align="center" width="12%">密钥</th>
                    <th align="center" width="8%">芯片号</th>
                    <th align="center" width="8%">nrf版本</th>
                    <th align="center" width="8%">st版本</th>
                    <th align="center" width="10%">备注</th>
                    <th align="center" width="12%">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list clPage.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td><a href="<@s.url "/data/device-edit?id=${cl.id}" />" target="dialog" rel="data_device_edit"  height="300" width="600" mask="true" title="编辑设备">${cl.bluetooth}</a></td>
                    <td><a href="<@s.url "/data/basedata-getUser?id=${cl.user.id}" />" target="dialog" rel="data_user_detail"  height="300" width="450" mask="true" title="用户资料">${cl.user.mobile}</a></td>
                    <td>${cl.user.userinfo.nickname}</td>
                    <td>${cl.deviceSign}</td>
                    <td>${cl.secretKey}</td>
                    <td>${cl.chipNo}</td>
                    <td>${cl.nrf}</td>
                    <td>${cl.st}</td>
                    <td>${cl.remark}</td>
                    <td><a href="<@s.url "/data/device-edit?id=${cl.id}" />" target="dialog"  rel="data_device_edit" title="编辑用户" width="600" height="350">编辑</a>
                    &nbsp;|&nbsp;
                    <#if cl.user>
                    <a href="<@s.url "/data/device-unbind?id=${cl.id}" />" target="ajaxTodo" title="确定要解绑设备${cl.bluetooth}？">解绑</a>
                    <#else><font color='gray'>解绑 </font></#if>
                    &nbsp;|&nbsp;
                    <a href="<@s.url "/data/device-del?id=${cl.id}" />" target="ajaxTodo" title="确定要删除设备${cl.bluetooth}？">删除</a></td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=clPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
