<#macro head>
    <@std.head />
    <link href="${ctx}/ngdwz/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/ngdwz/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/ngdwz/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <link href="${ctx}/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" /> 
    <script src="${ctx}/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>
       
    <script src="${ctx}/ngdwz/dwz/js/jquery.cookie.js" type="text/javascript"></script>
    <script src="${ctx}/ngdwz/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/ngdwz/dwz/js/jquery-validate-decimal.js" type="text/javascript"></script>
    <script src="${ctx}/ngdwz/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="${ctx}/ngdwz/dwz/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
    
    <script src="${ctx}/ngdwz/dwz/js/dwz.min.js" type="text/javascript"></script>
    <script src="${ctx}/ngdwz/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
    <link href="${ctx}/ngdwz/fix/themes/default/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/ngdwz/fix/themes/css/core.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/ngdwz/fix/js/fix.js" type="text/javascript"></script>
    <#nested>
</#macro>