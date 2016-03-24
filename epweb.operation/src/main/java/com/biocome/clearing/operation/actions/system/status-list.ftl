<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/> 
<div class="pageHeader">
    <@s.form id="pagerForm" class="pageForm required-validate" action="/data/detail-device" onsubmit="return navTabSearch(this);">
        <input type="hidden" name="pageNo" value="${searchModel.pageNo}" />
        <input type="hidden" name="pageSize" value="${searchModel.pageSize}" />
        <input type="hidden" name="orderBy" value="${searchModel.orderBy}" />
        <input type="hidden" name="sort" value="${searchModel.sort}" />
        
    </@s.form>
    
</div>

<div class="panelBar">
            <ul class="toolBar">
                <li>
                    <a href="<@s.url "/system/status-add" />" target="dialog" rel="data-device-add" title="新增监控配置" width="600" height="350"><span class="a09">新增监控配置</span> </a>
                </li>
            </ul>
</div>
<div class="pageContent">
        
        <table class="table" width="100%" layoutH="90">
            <thead style="border-top:1px solid #ccc;">
                <tr>
                    <th align="center" width="8%">主机名称</th>
                    <th align="center" width="8%">应用名称</th>
                    <th align="center" width="8%">类型</th>
                    <th width="8%">启动命令</th>
                    <th width="8%">停止命令</th>
                    <th width="8%">检测命令</th>
                    <th align="center" width="4%">状态</th>
                    <th align="center" width="12%">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list pages.contents as cl>
                <tr target="sid_cl" rel="1">
                    <td>${cl.hostName}</td>
                    <td><a href="<@s.url "/system/status-edit?id=${cl.id}" />" target="dialog" rel="system_status_edit"  height="350" width="600" mask="true" title="编辑监控配置">${cl.name}</a></td>
                    <td><@system.getValue path="${cl.type}" items=appTypes itemKey="code" itemValue="codeName"/></td>
                    <td>${cl.startCommand}</td>
                    <td>${cl.stopCommand}</td>
                    <td>${cl.testCommand}</td>
                    <td id="monitor${cl.id}"><@system.getValue path="${cl.status}" items=appStatus itemKey="code" itemValue="codeName"/></td>
                    <td>
                        <a href="<@s.url "/system/status-add?id=${cl.id}" />" target="dialog" rel="data-device-add" title="新增监控配置" width="600" height="350">复制</a>&nbsp;|&nbsp;
                     <#if cl.status gt 0>
                        <a href="<@s.url "/system/status-start?id=${cl.id}"/>" target="ajaxTodo" title="确定启动应用吗？" id="monitorurl${cl.id}">启动</a>
                        <#else>
                        <a href="<@s.url "/system/status-stop?id=${cl.id}" />" id="monitorurl${cl.id}" target="ajaxTodo" title="确定停止应用吗？">停止</a>
                        </#if>
                        </a>  
                        &nbsp;|&nbsp;  
                    <#if cl.enabled>
                    <a href="<@s.url "/system/status-disable?id=${cl.id}"/>" target="ajaxTodo" title="确定停用吗？">停用</a>
                    <#else>
                    <a href="<@s.url "/system/status-enable?id=${cl.id}"/>" target="ajaxTodo"  title="确定启用吗？">启用</a>
                    </#if>
                    &nbsp;|&nbsp;
                    <a href="<@s.url "/system/status-del?id=${cl.id}" />" target="ajaxTodo" title="确定要删除${cl.name}的监控配置？">删除</a></td>
                </tr>
                </#list>
            </tbody>
        </table>
        
        <div class="panelBar">
            <@dwz.pageNav pageModel=pages targetType="navTab" onchange="navTabPageBreak({numPerPage:this.value});" />
        </div>
        
    <script>
        var ws = null;  
        var monitorstatus={0:'启动',1:'停止',2:'异常'};
        function startWebSocket(url) {  
            if(lc_objs.length==0||lc_objs[0]==null){
                ws = new WebSocket(url+myuuid(32));  
                ws.onmessage = function(evt) {  
                    if(evt.data!=""&&evt.data.length>0){
                        var data=JSON.parse(evt.data);
                        for(var i=0;i<data.length;i++){
                            var d=$("#monitor"+data[i].id).find("div");
                            var u=$("#monitorurl"+data[i].id);
                            var murl=u.attr("href");
                            if(d){
                                d.text(monitorstatus[data[i].status]);
                                if(data[i].status>0){
                                    u.attr("href",murl.replace("stop","start"));
                                    u.text("启动")
                                }else{
                                    u.attr("href",murl.replace("start","stop"));
                                    u.text("停止")
                                }
                            }
                        }
                    }
                    
                };  
                ws.onclose = function(evt) {  
                    console.log('通信服务关闭');
                    lc_objs[0]=null;
                };  
      
                ws.onopen = function(evt) {  
                    console.log('通信服务连接');
                    lc_objs[0]=ws;  
                };
                return ws;
            }else{
                return lc_objs[0];
            }
        }  
        
        function sendMsg() {  
            ws.send("send");  
        }
        if (window.location.protocol == 'http:') {
            startWebSocket('ws://${url}');
        }else{
            startWebSocket('wss://${url}');  
        }
    </script>
</div>
