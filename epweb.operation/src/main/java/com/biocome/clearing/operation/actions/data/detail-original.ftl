<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/data/detail-original" onsubmit="return navTabSearch(this);">
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
                    <label>行业类型：</label>
                    <select name="industryType" >
                            <option value="" <#if searchModel.industryType == "">selected="selected"</#if>>全部</option>
                            <#list industryTypes as cc>
                                <option value=${cc.code} <#if searchModel.industryType == cc.code>selected="selected"</#if>>${cc.codeName}</option>
                            </#list>
                    </select>
                </li>
                 <li>
                    <label>状态：</label>
                    <select name="dealStatus" >
                            <option value="" <#if searchModel.dealStatus == "">selected="selected"</#if>>全部</option>
                            <#list dealStatus as cc>
                                <option value=${cc.code} <#if searchModel.dealStatus == cc.code>selected="selected"</#if>>${cc.codeName}</option>
                            </#list>
                    </select>
                </li>
                <li>
                    <label>同步日期：</label>
                    
                    <span>
                        <input type="text" value="${(searchModel.syncStartDate?string("yyyy-MM-dd"))!}" name="syncStartDate" class="date" />
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
                    
                    <span>
                        <input type="text" value="${(searchModel.syncEndDate?string("yyyy-MM-dd"))!}" name="syncEndDate" class="date" />
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
                    <th align="center" width="12%">同步时间</th>
                    <th width="8%">行业类型</th>
                    <th width="8%">状态</th>
                    <th width="8%">设备ID</th>
                    <th width="20%">报文</th>
                    <th width="12%">处理日期</th>
                </tr>
            </thead>
            <tbody>
                <#list clPage.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td><@system.getValue path="${cl.cityCode}" items=citys itemKey="code" itemValue="codeName"/></td>
                    <td>${cl.syncDate}</td>
                    <td><@system.getValue path="${cl.industryType}" items=industryTypes itemKey="code" itemValue="codeName"/></td>
                    <td><@system.getValue path="${cl.dealStatus}" items=dealStatus itemKey="code" itemValue="codeName"/></td>
                    <td>${cl.deviceId}</td>
                    <td>${cl.content}</td>
                    <td>${cl.dealDate}</td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=clPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
