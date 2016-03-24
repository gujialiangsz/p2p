<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/data/detail-consume" onsubmit="if($('#csstarttime').val()!=''&&$('#csendtime').val()!=''&&$('#csstarttime').val()>$('#csendtime').val()){alertMsg.info('开始日期不能大于结束日期');return false;}return navTabSearch(this);">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>城市：</label>
                    <select name="cityCode" >
                            <option value="" <#if searchModel.cityCode == "">selected="selected"</#if>>全部</option>
                            <#list citys as cc>
                                <option value=${cc.code} <#if searchModel.cityCode == cc.code>selected="selected"</#if>>${cc.codeName}</option>
                            </#list>
                    </select>
                </li>
                <li>
                    <label>卡号：</label>
                    <input type="text" value="${searchModel.cardNo}" maxlength="20" name="cardNo"/>
                </li>
                 <li>
                    <label>账号：</label>
                    <input type="text" value="${searchModel.userId}" maxlength="12" name="userId"/>
                </li>
            </ul>
            <ul class="searchContent">
                <li>
                    <label>交易日期：</label>
                    
                    <span>
                        <input type="text" value="${(searchModel.startTime?string("yyyy-MM-dd"))!}" name="startTime" id="csstarttime" class="date" />
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
                    
                    <span>
                        <input type="text" value="${(searchModel.endTime?string("yyyy-MM-dd"))!}" name="endTime" id="csendtime" class="date" />
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
                    <th align="center" width="8%">城市</th>
                    <th align="center" width="8%">卡号</th>
                    <th align="center" width="8%">行业类型</th>
                    <th width="8%">账号</th>
                    <th align="center" width="12%">交易时间</th>
                    <th align="center" width="8%">交易金额</th>
                    <th width="8%">设备ID</th>
                    <th width="8%">SAM卡号</th>
                    <th align="center" width="8%">交易序号</th>
                    <th width="12%">处理日期</th>
                </tr>
            </thead>
            <tbody>
                <#list clPage.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td><@system.getValue path="${cl.cityCode}" items=citys itemKey="code" itemValue="codeName"/></td>
                    <td>${cl.cardNo}</td>
                    <td><@system.getValue path="${cl.industryType}" items=industryTypes itemKey="code" itemValue="codeName"/></td>
                    <td><a href="<@s.url "/data/basedata-getUser?id=${cl.userId}" />" target="dialog" rel="data_user_detail"  height="300" width="450" mask="true" title="用户资料">${cl.userId}</a></td>
                    <td>${cl.tradeDate}</td>
                    <td>${cl.changeTradeMoney?string("#.##")}</td>
                    <td>${cl.deviceId}</td>
                    <td>${cl.samNo}</td>
                    <td>${cl.tradeSn}</td>
                    <td>${cl.dealDate}</td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=clPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
