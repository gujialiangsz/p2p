<div class="pageContent">
    <@s.form action="/data/attendanceDevice-save" id="pageForm" class="pageForm required-validate" onsubmit="if($('#attendancename').val()==''){alertMsg.info('请填写考勤机名称'); return false;} return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
        <dl>
            <dt>名称：</dt>
            <dd>
                <@s.hidden path="device.id"/>
                <@s.input path="device.name" class="required textInput"  id="attendancename"  maxlength="24" size="30" style="width: 250px" size="30"/>
            </dd>
        </dl>
        <dl>
            <dt>编号：</dt>
            <dd>
                <@s.input path="device.sn" class="required digits" maxlength="32" size="30" style="width: 250px" size="30"/>
            </dd>
        </dl>
        <dl>
            <dt>蓝牙密钥：</dt>
            <dd>
                <@s.input path="device.secretKey" id="attendancesecret"  style="width: 250px" readonly="readonly" size="30" maxlength="50" class="required"/><button type="button" onclick="$('#attendancesecret').val(myuuid(32))">生成密钥</button>
            </dd>
        </dl>
        <dl>
            <dt>备注：</dt>
            <dd>
                <@s.input path="device.remark" class="" maxlength="255" size="30" style="width: 250px" size="30"/>
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
</script>
