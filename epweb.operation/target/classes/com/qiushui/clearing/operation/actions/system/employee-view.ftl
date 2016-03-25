<div class="pageContent">  
<@s.form id="view_form" action="/employee/employee-update" class="pageForm required-validate" onsubmit="if(!checkChar($('#emp_name').val()))return false;return validateCallback(this,dialogAjaxDone)">
    <div class="pageFormContent" layoutH="60">
    <@s.hidden path="employee.id" />
    <fieldset>
        <legend>基本设置</legend>
            <dl style="width: 430px">
                <dt  style="width: 100px">员工号：</dt>
                <dd><@s.input  path="employee.number" size="30" maxlength="5" readonly="readonly" class="required number" readonly="readonly"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">姓  名：</dt>
                <dd><@s.input  path="employee.name" size="30" class="required" maxlength="12" id="emp_name" readonly="readonly"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">性别：</dt>
                <dd>
                    <#if employee.sex.text=="男">
                        <input name="sex" type="radio"  value="m" checked="true"/>男&nbsp;&nbsp;
                        <input name="sex" type="radio"  value="l" />女
                    <#elseif employee.sex.text=="女">
                        <input name="sex" type="radio"  value="m"/>男&nbsp;&nbsp;
                        <input name="sex" type="radio"  value="l"  checked="true"/>女
                    </#if>
                </dd>
            </dl>
            <dl style="width: 430px">
                <dt  style="width: 100px">身份证号：</dt>
                <dd><@s.input  path="employee.idCard" size="30" id="card" readonly="readonly" maxlength="18"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">籍贯：</dt>
                <dd><@s.input  path="employee.hometown" size="30" maxlength="6" readonly="readonly"/></dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">民族：</dt>
                <dd>
                <@s.input  path="employee.national" size="30" maxlength="30" readonly="readonly"/>
                </dd>
            </dl>
            <dl style="width: 430px">
                <dt style="width: 100px">常住地址：</dt>
                <dd>
                    <input type="text" name="address" value="${employee.address}" style="width: 633px; height: auto;" maxlength="64" readonly="readonly"></input>
                </dd>
            </dl>
         </fieldset>
         <fieldset>
            <legend>联系方式</legend>
                <dl style="width: 430px">
                    <dt style="width: 100px">联系电话：</dt>
                    <dd><@s.input  path="employee.contactNumber" size="30" class="required phone" maxlength="15" readonly="readonly"/></dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 100px">办公电话：</dt>
                    <dd><@s.input  path="employee.officePhone" size="30" maxlength="15" readonly="readonly" id="office_phone"/></dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 100px">邮箱：</dt>
                    <dd>
                        <@s.input  path="employee.email" size="30" maxlength="30" class="email" readonly="readonly"/>
                    </dd>
                </dl>
                <dl style="width: 430px">
                    <dt style="width: 100px">QQ号：</dt>
                    <dd>
                        <@s.input  path="employee.qq" size="30" class="digits" maxlength="11" readonly="readonly"/>
                    </dd>
                </dl>
        </fieldset>
        
     <fieldset>
        <legend>其他</legend>
          <dl style="width: 430px">
            <dt style="width: 100px">基本工资：</dt>
            <dd><@s.input  path="employee.baseSalary" size="30" maxlength="6" min="0" class="number" readonly="readonly"/></dd>
        </dl>

        <dl style="width: 430px">
            <dt  style="width: 100px">聘用形式：</dt>
            <dd><@s.input  path="employee.hireType" size="30" maxlength="30" readonly="readonly"/></dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">入职时间：</dt>
            <dd><@s.input  path="employee.hireDate"  readonly="readonly" size="30" class="date required" id="hire_date"/></dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">在职状态：</dt>
            <dd>
                <#if employee.workStatus.text=="在职">
                    <input name="workStatus" type="radio"  value="Y" checked="true"/>在职&nbsp;&nbsp;
                    <input name="workStatus" type="radio"  value="N" />离职
                <#elseif employee.workStatus.text=="离职">
                    <input name="workStatus" type="radio"  value="Y"/>在职&nbsp;&nbsp;
                    <input name="workStatus" type="radio"  value="N"  checked="true"/>离职
                </#if>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">工作年限：</dt>
            <dd>
                <@s.input  path="employee.workYear" size="30" class="digits" maxlength="2" min="0" readonly="readonly"/>
           </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">毕业院校：</dt>
            <dd>
            <@s.input  path="employee.graduateSchool" size="30" maxlength="50" readonly="readonly"/>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">专业：</dt>
            <dd>
                <@s.input  path="employee.major" size="30" maxlength="50" readonly="readonly"/>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">学历：</dt>
            <dd>
                <@s.select path = "employee.dEduBackground" items=majors itemValue="code" itemLabel="codeName" style="width:210px; height:30px" id="edu_level"></@s.select>
            </dd>
        </dl>
        <dl style="width: 430px">
            <dt style="width: 100px">备注：</dt>
            <dd>
                <textarea style="width: 633px; height: 22px;" name="remark" maxlength="128" readonly="readonly">${employee.remark}</textarea>
            </dd>
        </dl>
    </fieldset>
</div>
    <div class="formBar" align="center">
            <ul class="button">
                <li>
                    <div>
                        <button type="button" onclick="redit_input();">修改</button>
                    </div>
                </li>
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
<script type="text/javascript">
    $(function(){
        $("#hire_date").attr("disabled",true);
        $("#work_status").attr("disabled",true);
        $("#edu_level").attr("disabled",true);
    })
    function redit_input(){
        $("#view_form input").attr("readonly",false);
        $("#view_form input").css("background-color","white");
        $("#view_form textarea").attr("readonly",false);
        $("#view_form textarea").css("background-color","white");
        $("#hire_date").attr("disabled",false);
        $("#work_status").attr("disabled",false);
        $("#edu_level").attr("disabled",false);
    }
    function checkChar(val){
        var arry="`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？";
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
