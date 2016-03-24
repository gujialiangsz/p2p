<div class="pageContent">
    <@s.form action="/data/device-save" id="pageForm" class="pageForm required-validate" onsubmit="if($('#bluetooth').val()==''){alertMsg.info('请填写蓝牙地址'); return false;} return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
        <dl style="width: 440px">
            <dt style="width: 160px">蓝牙地址：</dt>
            <dd>
                <input name="bluetooth" class="required textInput"  id="bluetooth"  maxlength="24"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">类型：</dt>
            <dd>
                <select name="type">
                    <option value="1">老虎鱼系列</option>
                </select>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">蓝牙签名：</dt>
            <dd>
                <input name="deviceSign" class="required textInput" maxlength="32"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">蓝牙密钥：</dt>
            <dd>
                <input name="secretKey" class="required textInput"  maxlength="32"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">芯片号：</dt>
            <dd>
                <input name="chipNo" class="required digits"  maxlength="32"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">st版本：</dt>
            <dd>
                <input name="st" class="" maxlength="50"/>
            </dd>
        </dl>
        <dl style="width: 440px">
            <dt style="width: 160px">nrf版本：</dt>
            <dd>
                <input name="nrf" class="" maxlength="50"/>
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
