<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/health/detail-ecg" onsubmit="if($('#ecgstarttime').val()!=''&&$('#ecgendtime').val()!=''&&$('#ecgstarttime').val()>$('#ecgendtime').val()){alertMsg.info('开始日期不能大于结束日期');return false;}return navTabSearch(this);" method="post">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>蓝牙：</label>
                    <input type="text" value="${searchModel.bluetooth}" maxlength="24" name="bluetooth"/>
                </li>
                <li>
                    <label>账号：</label>
                    <input type="text" value="${searchModel.userId}" maxlength="24" name="userId" class="digits"/>
                </li>
                <li>
                    <label>昵称：</label>
                    <input type="text" value="${searchModel.nickname}" maxlength="24" name="nickname" class="text"/>
                </li>
            </ul>
            <ul class="searchContent">
                <li>
                    <label>记录时间：</label>
                    
                    <span>
                        <input type="text" value="${(searchModel.beginDate?string("yyyy-MM-dd"))!}" name="beginDate" id="ecgstarttime" class="date" />
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
                    
                    <span>
                        <input type="text" value="${(searchModel.endDate?string("yyyy-MM-dd"))!}" name="endDate" id="ecgendtime" class="date" />
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
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
                    <a href="${ctx}/health/detail-export" target="dwzExport"><span class="a17">导出</span> </a>
                </li>
            </ul>
</div>
<div class="pageContent">
        
        <table class="table" width="100%" layoutH="205">
            <thead style="border-top:1px solid #ccc;">
                <tr>
                    <th align="center" width="15%">蓝牙地址</th>
                    <th width="8%">手机</th>
                    <th width="8%">昵称</th>
                    <th align="center" width="22%">开始时间</th>
                    <th align="center" width="12%">持续时间</th>
                    <th align="center" width="12%">备注</th>
                    <th align="center" width="12%">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list clPage.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td>${cl.bluetooth}</td>
                    <td><a href="<@s.url "/data/basedata-getUser?id=${cl.user.id}" />" target="dialog" rel="data_user_detail"  height="300" width="450" mask="true" title="用户资料">${cl.user.mobile}</a></td>
                    <td>${cl.user.userinfo.nickname}</td>
                    <td>${cl.beginTime?number?number_to_datetime}</td>
                    <td>${(cl.endTime-cl.beginTime)/1000}秒</td>
                    <td><a href="<@s.url "/health/ecg-remark?id=${cl.id}" />" target="dialog"  rel="health_ecg_remark" title="修改备注" width="500" height="300"><#if cl.remark?length gt 0>${cl.remark}<#else><u>填写备注</u></#if></a></td>
                    <td><a href="<@s.url "/health/detail-ecgGraphic?id=${cl.id}" />" target="dialog"  rel="health_ecg_graphic" title="查看心电图" max="true" width="800" height="600">心电图</a>
                    &nbsp;|&nbsp;
                    <a href="<@s.url "/health/detail-ecgData?id=${cl.id}" />" target="dialog"  rel="health_ecg_data" title="查看心电数据" width="800" height="510">心电数据</a>
                    &nbsp;|&nbsp;
                    <a href="<@s.url "/health/ecg-del?id=${cl.id}" />" target="ajaxTodo" title="确定要删除该条心电数据？">删除</a></td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=clPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
