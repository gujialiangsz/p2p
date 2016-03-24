<div class="pageContent">
    <@s.form action="/app/appVersion-save" id="pageForm" class="pageForm required-validate" onsubmit="if($('#appname').val()==''||$('#apptype').val()==''){alertMsg.info('请上传软件包完善版本信息'); return false;} return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
        <dl style="width: 440px">
            <dt style="width: 160px">应用名称：</dt>
            <dd>
                <input name="appname" class="required textInput"  style="width: 200px" id="appname" readonly="readonly" maxlength="100"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">版本编码：</dt>
            <dd>
                <input name="versioncode"  style="width: 200px" id="appversioncode" readonly="readonly" maxlength="12"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">版本名称：</dt>
            <dd>
                <input name="versionname" class="required textInput"  style="width: 200px" id="appversionname" readonly="readonly" maxlength="50"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">下载地址：</dt>
            <dd>
                <input name="url" class="required text" readonly="readonly" style="width: 200px" id="appurl" maxlength="255"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">类型：</dt>
            <dd>
               <select name="type" class="required combox" style="width: 200px" id="apptype">
                   <option value="">请选择</option>
                   <#list apptypes as t>
                    <option <#if t.code==main.type>selected="selected"</#if> value=${t.code}>${t.codeName}</option>
                   </#list>
               </select>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">是否强制升级：</dt>
            <dd>
               <select name="force" class="required combox" style="width: 200px">
               <option value=true>是</option>
               <option value=false>否</option>    
               </select>
            </dd>
        </dl>
        <dl style="width: 880px">
            <dt style="width: 160px">签名：</dt>
            <dd>
                <input name="md5" class="required textInput"  style="width: 640px" id="appversionmd5" readonly="readonly" maxlength="255"/>
            </dd>
        </dl>
        <dl style="width: 880px">
            <dt style="width: 160px">白名单（逗号隔开）：</dt>
            <dd>
                <input name="grayscaleupgrade " class="textInput"  style="width: 640px" maxlength="255"/>
            </dd>
        </dl>
        <dl style="width: 880px">
            <dt style="width: 160px">可用版本：</dt>
            <dd>
                <select name="fromversion" style="width:646px;"  data-placeholder="请选择" multiple class="chosen-select">
                    <option value=""></option>
                    <#list versions as t>
                            <option value=${t.versioncode} class="addSelect_fromversion" type="${t.type}">${t.appname}</option>
                    </#list>
                </select>
            </dd>
        </dl>
        <dl style="width: 880px;height: 100px">
            <dt style="width: 160px">版本信息：</dt>
            <dd>
                <textarea name="versioninfo" class="textInput"  style="width: 641px;height: 100px" maxlength="1024"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">上传APP：</dt>
            <dd>
                <div id="upload"></div>
                <label id="uploadFile"></label>
            </dd>
        </dl>
    </div>
	<div class="formBar" align="center">
            <ul class="button">
                <li>
                    <div>
                        <button type="button" onclick="$(this).closest('form').submit()">保存</button>
                    </div>
                </li>
                <li>
                    <div>
                        <button type="button" class="close">取消</button>
                    </div>
                </li>
            </ul>
        </div>
    </@s.form>
</div>

<script type="text/javascript">
$(document).ready(function(){
    $('.chosen-select').chosen();
    $("div").data("fromgroup",$("select[name='fromversion'] option"));
});

$("#apptype").change(function(){
    var type=$("#apptype").val();
    if(type!=""){
        $("select[name='fromversion']").html("");
        $("div").data("fromgroup").each(function(){
            if($(this).attr("type")==type){
                $("select[name='fromversion']").append($(this));
            }
        });
        $('.chosen-select').trigger("chosen:updated")
    }else{
        $("select[name='fromversion']").html("");
        $("select[name='fromversion']").append($("div").data("fromgroup"));
        $('.chosen-select').trigger("chosen:updated")
    }
});
$(function(){
    $("#apptype").change();
    /*上传控件*/
    $('#upload').html5uploader({
        fileTypeExts:'*.apk,*.APK,*.IOS,*.ios,*.hex,*.HEX,*.ipa,*.IPA,*.bin,*.BIN',
        //fileTypeDesc:'*.bak',
        url:'${ctx}/app/appVersion-upload',
        auto:true,
        multi:true,
        buttonText:'选择版本包',
        removeTimeout: 1000,
        showPercent:true,//是否实时显示上传的百分比，如20%
        showUploaded:true,
        fileObjName : 'file',
        fileSizeLimit:'100000KB',
        onUploadError:uploadfail,
        onUploadCheck:uploadcheck,
        onUploadComplete:uploadifySuccessshow
        });
    });
    /*文件上传校验*/
        function uploadcheck(filename){
            /*此处正则校验表达式从后台获取 */
            var val=/${versioncheck}/;
            if(!val.test(filename)){
               alertMsg.error("文件名称格式检验失败");
               return false; 
            }
            return true;
        }
        function uploadfail(){
            alertMsg.error("上传失败");
        }
        function huifuherf()
        {
            
            var _thisa =  $("#restore-surepage-btn");
            var aurl = _thisa.attr("href");
            
            if(aurl == "javascript:;")
            {
                alertMsg.error("请选择还原包");
                return false;
            }
            
            _thisa.next().attr("href", _thisa.attr("href"));
            _thisa.attr("href", "javascript:;");
            $("#uploadFile").text("");
            _thisa.next().click();
            //_thisa.attr("href", aurl);
            return false;
        }
        /*上传成功*/
        function uploadifySuccessshow(data,jsondata){
            debugger
            var fileName=data.name;
            $("#uploadFile").text(data.name+" 上传成功");
             var _this =  $("#restore-surepage-btn");
             if(jsondata=="format_error"){
                alertMsg.info('文件名格式错误，请参考'+"${versioncase}");
                return false;
             }
             if(jsondata=="error"){
                alertMsg.info('上传失败');
                return false;
             }
            jsondata=$.parseJSON(jsondata);
             $("#appversioncode").val(jsondata.versioncode);
             $("#appversionname").val(jsondata.versionname);
             $("#appname").val(fileName);
             $("#appversionmd5").val(jsondata.md5);
             $("#appurl").val(jsondata.url);
        }
</script>
