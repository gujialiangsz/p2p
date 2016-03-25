<div class="page">
    <div class="pageContent">
        <div style="float:left; display:block;overflow:auto; width:25%; border-right:solid 1px #CCC;" layoutH="0">
            <ul class="tree treeFolder" layoutH="37">
                <li>
                    <a>已定义角色</a>
                    <ul>
                        <#list roles as role>
                        <li>
                            <a href="<@s.url "/system/role-edit?roleId=${role.id}" />" target="ajax" rel="roleBox" onclick="$(this).closest('li').siblings().find('a').removeAttr('style');$(this).attr('style','color:orange;');">${role.name}</a>
                        </li>
                        </#list>
                    </ul>
                </li>
            </ul>
            <div class="formBar">
                <ul>
                    <li>
                        <a class="a_button" href="<@s.url "/system/role-add" />" target="dialog" rel="role-add" mask="true" width="900" height="500"><span style="text-align:center;line-height:28px;">新增角色</span></a>
                    </li>
                   <!-- <@shiro.hasAnyRoles name="ROLE_IMPORT">
                    <li>
                        <input class="a_button" id="testFileInput" type="file" name="file" 
        uploaderOption="{
            swf:'uploadify/scripts/uploadify.swf',
            uploader:'<@s.url '/system/system-upload'/>;jsessionid=${sessionid}?folder=role&navTabId=system_role-list&forwardurl=/system/role-imp',
            formData:{PHPSESSID:'xxx', ajax:1},
            buttonText:'导入',
            buttonImage:'uploadify/img/import1.png',
            fileSizeLimit:'2000KB',
            fileTypeDesc:'*.jpg;*.jpeg;*.xml;*.png;',
            fileTypeExts:'*.jpg;*.jpeg;*.xml;*.png;',
            auto:true,
            multi:true,
            onUploadSuccess:oncomplete,
            onQueueComplete:nothingtodo
        }"
    />
                    </li>
                    </@shiro.hasAnyRoles>
                    <@shiro.hasAnyRoles name="ROLE_EXPORT">
                    <li>
                       <a class="a_button" href="<@s.url "/system/role-exp" />" target="dwzExport"><span style="text-align:center;line-height:28px;">导出</span></a>
                    </li>
                    </@shiro.hasAnyRoles>-->
                </ul>
            </div>
        </div>
        <div id="roleBox" style="float:left; display:block;padding:3px; overflow:auto; width:74%;" layoutH="0">
        </div>
    </div>
</div>
<script>
    function hideQueue(){
        if($("#uploadify-queue"))
            $("#uploadify-queue").hide();
        if($("#testFileInput-queue")){
            $("#testFileInput-queue").hide();
            $("#testFileInput-queue").remove(); 
        }
        if($("#fileQueue"))
            $("#fileQueue").hide();
    }
    hideQueue();
    function oncomplete(event,response, data){
        hideQueue();
        var result=eval('(' + response + ')');
        if(result.statusCode==300){
            //中文乱码，未解决，暂时用英文判断
            var mes=result.message;
            var showmes="导入失败，请确保导入文件内容正确";
            if(mes=="roleError"){
                showmes="导入失败，角色名重复";
            }else if(mes=="fileError"){
                showmes="导入失败，文件无法解析";
            }
            alertMsg.error(showmes);
        }else{
            alertMsg.correct("导入成功");
            navTab.reload("", {navTabId: result.navTabId});
        }
    }
    function nothingtodo(event,response, data){
        
        }
</script>
<!-- <script>
 function uploadFile(fileId,resultId,multi,comFlag,s_width,s_height,onComplete,folder){
        if (!onComplete){
            onComplete = function (){alert("上传成功");};
        }
        $('#'+fileId+'').uploadify({
            'uploader'     : '${ctx}/js/uploadify/uploadify.swf?var=' + (new Date()).getTime(),
            'script'       : '${ctx}/system/upload?&folder='+folder+'',
            'buttonImg'    : '${ctx}/js/uploadify/scan_btn.png',
            'cancelImg'    : '${ctx}/js/uploadify/cancel.png',
            'auto'         :true ,
            'hideButton'   :true,
            'multi'        : multi,
            'wmode'       :'transparent',
            'fileExt'      :'*.xml;*.xlsx',
            'scriptData'   : {'comFlag':comFlag,'smallWidth':s_width,'smallHeight':s_height},
            'onAllComplete':function(event,data){   } ,
            'onComplete':function(event, ID, fileObj, response, data){
               var result = eval('(' + response + ')');
               onComplete(event,result,resultId,multi);
            }
        });
        $("#file_uploadQueue").hide();
    }
    uploadFile("file_upload","uploadFiles",false,"null","100","150", null, "whitelist");
    var names="";     
        for(var name in obj){     
            names+=name+": "+obj[name]+",   ";}  
        alert(names); 
</script> -->