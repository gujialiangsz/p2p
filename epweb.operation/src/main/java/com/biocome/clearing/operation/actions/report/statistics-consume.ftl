<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/data/detail-consume" onsubmit="return navTabSearch(this);">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
       <div class="searchBar">
            <ul class="searchContent">
                <li>
                    <label>城市：</label>
                    <select name="cityCode" id="report_cscc">
                            <option value="" <#if searchModel.cityCode == "">selected="selected"</#if>>全部</option>
                            <#list citys as cc>
                                <option value=${cc.code} <#if searchModel.cityCode == cc.code>selected="selected"</#if>>${cc.codeName}</option>
                            </#list>
                    </select>
                </li>
                <li>
                    <label>格式：</label>
                    <select name="type" id="report_cstype">
                            <#list formats as cc>
                                <option value=${cc.code} <#if searchModel.type == cc.code>selected="selected"</#if>>${cc.codeName}</option>
                            </#list>
                    </select>
                </li>
                <li>
                    <label>交易日期：</label>
                    
                    <span>
                        <input type="text" value="${(searchModel.startTime?string("yyyy-MM-dd"))!}" name="startTime" class="date" id="report_csst"/>
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
                    
                    <span>
                        <input type="text" value="${(searchModel.endTime?string("yyyy-MM-dd"))!}" name="endTime" class="date" id="report_cset"/>
                        <a class="inputDateButton" href="javascript:;" >选择</a>
                    </span>
                </li>
            </ul>
            
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><button type="button" class="a_button" style="width: 50px" onclick="getReport()">查询</button></div></li>
                    <li><div class="buttonActive"><button type="button" class="a_button" onclick="$('#pagerForm input[type=text]').val('')">重置 </button></div></li>
                </ul>
            </div>
            
        </div>
    </@s.form>
    
</div>


<div class="pageContent">
    <div align="center" id="passframeDiv" layoutH="100">
        <iframe id="passframe" name="passframe" src="about:blank" width="90%" height="90%"></iframe> 
    </div> 
</div>

<script>
    function getReport(){
        var type=$("#report_cstype").val();
        var cityCode=$("#report_cscc").val();
        var starttime=$("#report_csst").val();
        var endtime=$("#report_cset").val();
        if(!/^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/.test(starttime)){
            alertMsg.info("开始时间填写不合法");
            return;
        }
        if(!/^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/.test(endtime)){
            alertMsg.info("结束时间填写不合法");
            return;
        }
        var targeturl="${ctx}/report/statistics-consume-report?type="+
        type+"&startTime="+starttime+"&endTime="+endtime;
        //window.open(targeturl);
        var ifrm = $("#passframe"); 
        $("#passframe").attr("src",targeturl);
        //ifrm.src = targeturl;  
        $("#passframe").show();
    }
</script>