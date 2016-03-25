<div class="page">
    <div class="pageContent" layoutH="0">
        <@s.form action="/system/user-update" id="pageForm" class="pageForm required-validate" onsubmit="if($('.pwdinput').is(':hidden')) $('.pwdinput').remove();if(!checkDate())return false;return validateCallback(this,dialogAjaxDone)">
        <div class="pageFormContent">
            <h2 class="contentTitle">用户信息</h2>
            <div class="divider" />
            <@s.hidden path="user.id" name="user.id"/>
            <dl>
                <dt>姓名：</dt>
                <dd>
                    <@s.input path="user.name" name="user.name" type="text" readonly="readonly" size="30" maxlength="20" class="required"/></dd>
            </dl>
            <dl>
                <dt>工号：</dt>
                <dd><@s.input path="user.userNo" size="30" maxlength="20" readonly="readonly" class="required" readonly="readonly"/></dd>
            </dl>
            <dl>
                <dt>用户名：</dt>
                <dd>
                    <@s.input path="user.username"  size="30"  maxlength="20" class="required alphanumeric" alt="只能输入字母、数字及下划线" readonly="readonly"/>
                </dd>
            </dl>
            <dl>
                <dt>电话：</dt>
                <dd>
                    <@s.input path="user.phone"  size="30"  maxlength="20" class="required  phone"/>
                </dd>
            </dl>
            <dl>
                <dt>生效时间：</dt>
                <dd><@s.input path="user.effectiveDate"  size="30" maxlength="20" class="required date" datefmt="yyyy-MM-dd" readonly="readonly" id="starttime"/></dd>
            </dl>
            <dl>
                <dt>过期时间：</dt>
                <dd><@s.input path="user.invalidDate"  size="30" maxlength="20" class="required date" datefmt="yyyy-MM-dd" readonly="readonly" id="endtime"/></dd>
            </dl>
            <div class="divider" />
            <h2 class="contentTitle"><span class="a_button" style="font-weight: bolder" onclick="$('.pwdinput').toggle();if($(this).text()=='修改密码') $(this).text('取消修改');else $(this).text('修改密码');">修改密码</span></h2>
            <dl class="pwdinput" style="display: none">
                <dt>密码：</dt>
                <dd>
                    <@s.password path="user.password" id="check_password"  size="30"  maxlength="20" class="required alphanumeric textInput" minlength="6"  alt="只能输入字母、数字及下划线"/>
                </dd>
            </dl>
            <dl class="pwdinput" style="display: none">
                <dt>重复密码：</dt>
                <dd>
                    <input type="password"size="30" maxlength="20" class="required" equalto="#check_password"/>
                </dd>
            </dl>
            <div class="divider" />
            <div class="formBar">
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
            <div class="divider" />
            <h2 class="contentTitle">职务列表</h2>
            <div class="divider" />
            
            
            
            
            
            
            <div class="panel">
                <h1>[<a href="<@s.url "/system/actor-add?userId=${user.id}" />" target="dialog" rel="actor-add" width="900" height="480" mask="true" title="新增职务">新增职务</a>]</h1>
                <div>
                    <table class="list" width="98%">
                        <thead>
                            <tr>
                                <th align="center">职务名称</th>
                                <th align="center" width="200">关联部门</th>
                                <th align="center" width="200">关联角色</th>
                                <th align="center" width="200">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list user.actors as actor>
                            <tr>
                                <td align="center"><@system.getValue path="${actor.name}" items=actorlist itemKey="code" itemValue="codeName"/></td>
                                <td align="center">${actor.organ.name}</td>
                                <td align="center">${actor.role.name}</td>
                                <td align="center">
                                    <a href="<@s.url "/system/actor-edit?actorId=${actor.id}" />" target="dialog" rel="actor-edit" width="900" height="480" mask="true" title="编辑职务">编辑</a>
                                    <#if !actor.isDefaultActor()>
                                    |
                                    <a href="<@s.url "/system/actor-delete?actorId=${actor.id}" />" target="ajaxTodo" callback="dialogReloadDone" title="您确定要删除该职务吗？">删除</a></#if>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
            
            
            
            
            
        
        </@s.form>
    </div>
</div>
<script type="text/javascript" charset="utf-8">
    function checkDate(){
        var a=$("#starttime").val();
        var b=$("#endtime").val();
        if(a==""||a.length==0){
            alertMsg.info("请填写生效日期");
            return false;
        }
        if(b==""||b.length==0){
            alertMsg.info("请填写过期日期");
            return false;
        }
        if(Date.parse(a)>Date.parse(b)){
            alertMsg.info("生效日期不能大于过期日期");
            return false;
        };
        return true;
    }
    function userAdd_roleSelectorOnChange(roleSelector) {
        var $roleSelector = $(roleSelector);
        var $roleName = $roleSelector.getParentUnitBox().find("input[name='defaultActor.name']")
        $roleName.val($roleSelector.children("option:selected").text());
    }
</script>