<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/business/question-list" onsubmit="if(/^[0-9]*$/.test($('#questionmobile').val())) return  navTabSearch(this);else {alertMsg.info('手机格式错误'); return false;}">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>名称：</label>
                    <input type="text" value="${searchModel.name}" maxlength="24" name="name"/>
                </li>
                <li>
                    <label>手机：</label>
                    <input type="text" value="${searchModel.phone}"  maxlength="20" name="phone" id="questionmobile" class="phone"/>
                </li>
                 <li>
                    <label>产品：</label>
                    <select name="product" >
                            <option value="" <#if searchModel.product == "">selected="selected"</#if>>全部</option>
                            <#list products as cc>
                                <option value=${cc.code} <#if searchModel.product == cc.code>selected="selected"</#if>>${cc.codeName}</option>
                            </#list>
                    </select>
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
        
        <table class="table" width="100%" layoutH="138">
            <thead style="border-top:1px solid #ccc;">
                <tr>
                    <th align="center" width="12%">名称</th>
                    <th align="center" width="8%">手机</th>
                    <th align="center" width="12%">产品</th>
                    <th align="center" width="12%">状态</th>
                    <th align="center" width="8%">时间</th>
                    <th align="center" width="12%">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list clPage.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td>${cl.name}</td>
                    <td>${cl.phone}</td>
                    <td><@system.getValue path="${cl.product}" items=products itemKey="code" itemValue="codeName"/></td>
                    <td><#if cl.status=="0">已处理<#else>未处理</#if></td>
                    <td>${cl.operateDate!''}</td>
                    <td>
                    <a href="<@s.url "/business/question-edit?id=${cl.id}" />" target="dialog"  rel="business_question_edit" title="回复问题" width="800" height="600">查看并回复</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=clPage targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
</div>
