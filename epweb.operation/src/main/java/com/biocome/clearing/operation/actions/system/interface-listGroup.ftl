<div class="page">
    <div class="pageContent">
        <div style="float:left; display:block;overflow:auto; width:15%; border-right:solid 1px #CCC;" layoutH="0">
            <ul class="tree treeFolder" layoutH="37">
                <li>
                    <a>接口组列表</a>
                    <ul>
                        <#list groups as group>
                        <li>
                            <a href="<@s.url "/system/interface-editGroup?id=${group.id}" />" id="interfaceGroup${group.id}" target="ajax" rel="groupBox" onclick="$(this).closest('li').siblings().find('a').removeAttr('style');$(this).attr('style','color:orange;');">${group.name}</a>
                        </li>
                        </#list>
                    </ul>
                </li>
            </ul>
            <div class="formBar">
                <ul>
                    <li>
                        <a class="a_button" href="<@s.url "/system/interface-addGroup" />" target="dialog" rel="interface-groupAdd" mask="true" width="500" height="250"><span style="text-align:center;line-height:28px;">新增接口组</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="groupBox" style="float:left; display:block;padding:3px; overflow:auto; width:84%;" layoutH="0" rel="groupBox">
        </div>
    </div>
</div>
<script>
    $(function(){
        var groupid="${selgroup.id}";
        if(groupid!=""&&groupid!=null){
            $("#interfaceGroup"+groupid).attr('style','color:orange;');
            var url="${ctx}/system/interface-editGroup?id=${selgroup.id}";
            $("#groupBox").loadUrl(url,null);
        }
    });
    
</script>