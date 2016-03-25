<div class="pageContent">
<@s.form action="/system/employee-update" cssClass="pageForm required-validate" onsubmit="if(!checkChar($('#emp_name').val()))return false;return validateCallback(this,dialogAjaxDone)">
	<div class="pageFormContent" layoutH="60">
    <@s.hidden path="employee.id" />
    <fieldset>
        <legend>基本设置</legend>
            <dl style="width: 430px">
                <dt  style="width: 100px">员工号：</dt>
                <dd><@s.input  path="employee.number" size="30" maxlength="5" readonly="readonly" class="required number"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">姓  名：</dt>
                <dd><@s.input  path="employee.name" size="30" maxlength="12" class="required" id="emp_name"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">性别：</dt>
                <dd>
                    <#if employee.sex.text=="男">
                        

<label style="float:none; text-align: left;"><input name="sex" type="radio"  value="m" checked="true"/>男&nbsp;&nbsp;</label>
                        <label style="float:none; text-align: left;"><input name="sex" type="radio"  value="F" />女</label>
                    <#elseif employee.sex.text=="女">
                        <label style="float:none; text-align: left;"><input name="sex" type="radio"  value="M"/>男&nbsp;&nbsp;</label>
                        <label style="float:none; text-align: left;"><input name="sex" type="radio"  value="l"  checked="true"/>女</label>
                    </#if>
                </dd>
            </dl>
            <dl style="width: 430px">
                <dt  style="width: 100px">身份证号：</dt>
                <dd><@s.input  path="employee.idCard" size="30" id="card" maxlength="18"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">籍贯：</dt>
                <dd><@s.input  path="employee.hometown" size="30" maxlength="6"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">民族：</dt>
                <dd>
                <@s.input  path="employee.national" size="30" maxlength="30"/>
                </dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">常住地址：</dt>
                <dd>
                    <input style="width: 633px; height: auto;" name="address" value="${employee.address}" maxlength="64"></input>
                </dd>
            </dl>
         </fieldset>
         <fieldset>
            <legend>联系方式</legend>
                <dl style="width: 430px">
                    <dt style="width: 100px">联系电话：</dt>
                    <dd><@s.input  path="employee.contactNumber" size="30" maxlength="15" class="required phone"/></dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 100px">办公电话：</dt>
                    <dd><@s.input  path="employee.officePhone" size="30" maxlength="15"  id="office_phone"/></dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 100px">邮箱：</dt>
                    <dd>
                        <@s.input  path="employee.email" size="30" maxlength="30" class="email"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 100px">QQ号：</dt>
                    <dd>
                        <@s.input  path="employee.qq" size="30" maxlength="11" class="digits"/>
                    </dd>
                </dl>
        </fieldset>
        
     <fieldset>
        <legend>其他</legend>
          <dl style="width: 430px">
            <dt style="width: 100px">基本工资：</dt>
            <dd><@s.input  path="employee.baseSalary" size="30" maxlength="6" min="0" class="number"/></dd>
        </dl>

        <dl style="width: 430px">
            <dt  style="width: 100px">聘用形式：</dt>
            <dd><@s.input  path="employee.hireType" size="30" maxlength="30"/></dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">入职时间：</dt>
            <dd><@s.input  path="employee.hireDate" size="30" readonly="readonly" maxlength="30" class="date required"/></dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">在职状态：</dt>
            <dd>
                <#if employee.workStatus.text=="在职">
                    <label style="float:none; text-align: left;"><input name="workStatus" type="radio"  value="Y" checked="true"/>在职&nbsp;&nbsp;</label>
                    <label style="float:none; text-align: left;"><input name="workStatus" type="radio"  value="N" />离职</label>
                <#elseif employee.workStatus.text=="离职">
                    <label style="float:none; text-align: left;"><input name="workStatus" type="radio"  value="Y"/>在职&nbsp;&nbsp;</label>
                    <label style="float:none; text-align: left;"><input name="workStatus" type="radio"  value="N"  checked="true"/>离职</label>
                </#if>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">工作年限：</dt>
            <dd>
                <@s.input  path="employee.workYear" size="30" maxlength="2" class="digits" min="0"/>
           </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">毕业院校：</dt>
            <dd>
            <@s.input  path="employee.graduateSchool" size="30" maxlength="50"/>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">专业：</dt>
            <dd>
                <@s.input  path="employee.major" size="30" maxlength="50"/>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">学历：</dt>
            <dd>
                <@s.select path = "employee.dEduBackground" items=majors itemValue="code" itemLabel="codeName" style="width:195px; height:30px"></@s.select>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">备注：</dt>
            <dd>
                <textarea style="width: 633px; height: 22px;" name="remark" maxlength="128">${employee.remark}</textarea>
            </dd>
        </dl>
    </fieldset>
    </div>
    <div class="formBar" align="center">
            <ul class="button">
                <li>
                    <div>
                        <button type="submit" id="save">保存</button>
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
<script>
    function checkChar(val){
        var arry="`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_-·《》";
        var map=new Map();
        map.putAll(arry);
        if(map.containAny(val)){
            alertMsg.info("员工姓名不能包含非法字符");
            return false;
        }else{
            return true;
        }
    }

    $("#save").click(function(){
         if (($("#card").val() != "" )&& (!/^(\d{15}|\d{17}[\dXx])$/.test($("#card").val()))) {
            alertMsg.info('身份证号格式不正确！');
            return false;
        }
        var reg=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
        if(($("#office_phone").val() != "" )&& (!reg.test($("#office_phone").val()))){
            alertMsg.info('办公电话格式不正确！');
            return false;
        }
        
        $("#pageForm").submit();
    });
</script>