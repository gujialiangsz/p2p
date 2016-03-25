<div class="page">
    <div class="pageContent">
    <div class="pageFormContent" layoutH="60">
        <h2 class="contentTitle">职务列表</h2>
        <div class="divider" />
        <#list currentUser.actors as actor>
        <dl style="text-align: center;">
            <dd>${actor.organ.name}:<a href="${ctx}/system/person-actor-change?actorId=${actor.id}">
                <@system.getValue path="${actor.name}" items=actorlist itemKey="code" itemValue="codeName"/>
            </a>
            </dd>
        </dl>
        </#list>
     </div>
     </div>
     </div>