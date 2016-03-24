<div class="pageContent">
    <@s.form action="/health/ecg-update" id="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone)"> 
        <div class="pageFormContent" layoutH="54">
        <dl style="width: 480px;height:180px;">
            <input type="hidden" name="id" value="${id}"/>
            <dt style="width: 100px">备注：</dt>
            <dd>
                <textarea name="remark"  maxlengt="255" style="width:300px;height:150px;">${remark!''}</textarea>
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
