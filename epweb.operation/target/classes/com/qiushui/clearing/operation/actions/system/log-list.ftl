<style type="text/css">
    .width300{ width:300px!important; }
    .width300 div{ width:300px!important; }
    .width200{ width:200px!important; }
    .width200 div{ width:200px!important; }
    .width150{ width:150px!important; }
    .width150 div{ width:150px!important; }
    .width80{ width:80px!important; }
    .width80 div{ width:80px!important; }
    .width100{ width:100px!important; }
    .width100 div{ width:100px!important; }
</style>
<div class="pageHeader">
    
    <@s.form id="pagerForm" class="pageForm required-validate" action="/system/log-list" onsubmit="if($('#logstarttime').val()!=''&&$('#logendtime').val()!=''&&$('#logstarttime').val()>$('#logendtime').val()){alertMsg.info('开始日期不能大于结束日期');return false;}return navTabSearch(this);">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>登录账号：</label>
                    <input type="text" value="${searchModel.operatorId}" maxlength="20" name="operatorId"/>
                </li>
                <li>
                    <label>员工姓名：</label>
                    <input type="text" value="${searchModel.operatorName}" maxlength="12" name="operatorName"/>
                </li>
                 <li>
                    <label>描述：</label>
                    <input type="text" value="${searchModel.message}" maxlength="50" name="message"/>
                </li>
            </ul>
            <ul class="searchContent">
                <li>
                    <label>操作日期：</label>
                    
                    <span>
                        <input type="text" value="${(searchModel.startTime?string("yyyy-MM-dd"))!}" name="startTime" id="logstarttime" class="date" />
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
                    
                    <span>
                        <input type="text" value="${(searchModel.endTime?string("yyyy-MM-dd"))!}" name="endTime" id="logendtime" class="date" />
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


<div class="pageContent">
        
        <table class="table" width="100%" layoutH="178">
            <thead style="border-top:1px solid #ccc;">
                <tr>
                    <th class="width150" align="center">操作日期</th>
                    <th class="width80" align="center">操作人</th>
                    <th class="width80" align="center">操作账号</th>
                    <th class="width100" align="center">登录IP</th>
                    <th class="width80" align="center">操作类型</th>
                    <th class="width80" align="center">操作对象</th>
                    <th class="width80" align="center">操作状态</th>
                    <th class="width300">操作信息</th>
                    <th class="width200">异常信息</th>
                </tr>
            </thead>
            <tbody>
                <#list pageModel.contents as log>
                <tr target="sid_user" rel="1">
                    <td class="width150">${log.operateTime}</td>
                    <td class="width80">${log.operatorName}</td>
                    <td class="width80">${log.operatorId}</td>
                    <td class="width100">${log.ip}</td>
                    <td class="width80">${log.logType.text}</td>
                    <td class="width80">${log.method}</td>
                    <td class="width80">${log.success?string('正常','异常')}</td>
                    <td class="width300">${log.message}</td>
                    <td class="width200">${log.exceptionMsg}</td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=pageModel targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
