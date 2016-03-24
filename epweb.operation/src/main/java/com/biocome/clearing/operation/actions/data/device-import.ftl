<div class="pageContent">
    <div class="pageFormContent" layoutH="54">
        <dl style="width: 440px">
            <dt style="width: 160px">上传设备文件：</dt>
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
                        <button type="button" class="close">关闭</button>
                    </div>
                </li>
            </ul>
        </div>
</div>

<script type="text/javascript">

$(function(){
    /*上传控件*/
    $('#upload').html5uploader({
        fileTypeExts:'*.txt,*.TXT,*.csv,*.tsv,*.TSV,*.CSV',
        //fileTypeDesc:'*.bak',
        url:'${ctx}/data/device-upload',
        auto:true,
        multi:true,
        buttonText:'选择设备信息文件',
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
            var val='*.txt,*.TXT,*.csv,*.tsv,*.TSV,*.CSV';
            if(val.indexOf(filename.substring(filename.lastIndexOf(".")))==-1){
               alertMsg.error("文件名称格式检验失败");
               return false; 
            }
            return true;
        }
        function uploadfail(){
            alertMsg.error("导入失败");
        }
        /*上传成功*/
        function uploadifySuccessshow(data,jsondata){
            var fileName=data.name;
            $("#uploadFile").text(data.name+" 上传成功");
             var _this =  $("#restore-surepage-btn");
             jsondata=JSON.parse(jsondata);
             if(jsondata.statusCode!="200"){
                alertMsg.info('导入失败，请注意格式');
             }else{
                alertMsg.info('导入成功');
             }
             return dialogAjaxDone(jsondata);
             
        }
</script>
