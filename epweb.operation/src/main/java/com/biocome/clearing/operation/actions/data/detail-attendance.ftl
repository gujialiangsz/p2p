<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/data/detail-attendance" onsubmit="if($('#kqstarttime').val()!=''&&$('#kqendtime').val()!=''&&$('#kqstarttime').val()>$('#kqendtime').val()){alertMsg.info('开始日期不能大于结束日期');return false;};if(/^[0-9]*$/.test($('#usermobile').val()))return navTabSearch(this);else {alertMsg.info('账号格式错误'); return false;}">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>考勤机：</label>
                    <select name="terminalNo" >
                            <option value="" <#if searchModel.terminalNo == "">selected="selected"</#if>>全部</option>
                            <#list devices as d>
                            <option value=${d.sn} <#if d.sn==searchModel.terminalNo>selected="selected"</#if>>${d.name}</option>
                            </#list>
                    </select>
                </li>
                <li>
                    <label>芯片号：</label>
                    <input type="text" value="${searchModel.chipNo}" maxlength="32" name="chipNo"/>
                </li>
                 <li>
                    <label>账号：</label>
                    <input type="text" value="${searchModel.uid}" maxlength="12" name="uid" id="usermobile" class="digits"/>
                </li>
            </ul>
            <ul class="searchContent">
                <li>
                    <label>打卡日期：</label>
                    
                    <span>
                        <input type="text" value="${(searchModel.beginDate?string("yyyy-MM-dd"))!}" name="beginDate" id="kqstarttime" class="date" />
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
                    
                    <span>
                        <input type="text" value="${(searchModel.endDate?string("yyyy-MM-dd"))!}" name="endDate" id="kqendtime" class="date" />
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
                    <th align="center" width="10%">日期</th>
                    <th align="center" width="8%">姓名</th>
                    <th align="center" width="8%">芯片号</th>
                    <th align="center" width="12%">考勤机SN</th>
                    <th align="center" width="12%">首次打卡时间</th>
                    <th align="center" width="12%">最后打卡时间</th>
                </tr>
            </thead>
            <tbody>
                <#list clPage.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td>${cl.recordDay?date}</td>
                    <td><a href="<@s.url "/data/basedata-getUser?id=${cl.user.id}" />" target="dialog" rel="data_user_detail"  height="300" width="450" mask="true" title="用户资料">${cl.user.userinfo.nickname}</a></td>
                    <td>${cl.chipNo}</td>
                    <td>${cl.terminalNo}</td>
                    <td>${(cl.startDate?string('HH:mm:ss'))!'--'}</td>
                    <td>${(cl.endDate?string('HH:mm:ss'))!'--'}</td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=clPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
