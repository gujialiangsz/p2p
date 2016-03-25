<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <@std.head><title>登录 - 万市通运营系统</title></@std.head>
        
        <link rel="stylesheet" type="text/css" href="${ctx}/themes/css/new_login.css" />

        <script type="text/javascript" src="${ctx}/js/jquery-2.1.3.min.js" ></script>
    	
        <script type="text/javascript" >
        function send_message(test, params) {
          var message = 'Window.' + test;
          if (typeof params != 'undefined')
            message += ':' + params;
          window.cefQuery({'request' : message});
        }

        function minimize() {
          send_message('Minimize');
        }

        function maximize() {
          send_message('Maximize');
        }

        function restore() {
          minimize();
          setTimeout(function() { send_message('Restore'); }, 1000);
        }

        function position() {
          var x = parseInt(document.getElementById('x').value);
          var y = parseInt(document.getElementById('y').value);
          var width = parseInt(document.getElementById('width').value);
          var height = parseInt(document.getElementById('height').value);
          if (isNaN(x) || isNaN(y) || isNaN(width) || isNaN(height))
            alert('Please specify a valid numeric value.');
          else
            send_message('Position', x + ',' + y + ',' + width + ',' + height);
        }
        function closewindow()
        {
            send_message('Close');  
        }
        if(typeof(WinForm) == "undefined") {
            WinForm = { 
              MinScreen: function () { 
                 send_message('Minimize'); 
                 }, 
                 Close:function(){
                 send_message('Close');
                 }
            }
        }
        </script>
    </head>
    <body>        
        
        <div class="login_content">

            <h1 class="title"><span class="logo"></span>万市通运营系统V1.0</h1>
            
            <div class="login_box">
                <@s.form action="/login-auth" onSubmit="return check()">
                    <div class="errors" style="color:rgb(247, 87, 87); text-align:left; text-indent:80px;">
                        <@s.errors path="loginModel" />
                    </div>
                    <div class="l_context">
                        <div class="input"><span>用户名：</span><@s.input path="loginModel.username" size="20" autocomplete="off" id="username"/></div>
                        <div class="input"><span>密　码：</span><@s.password path="loginModel.password" size="20" autocomplete="off" /></div>
                    </div>
                    <p>
                        <input type="submit" class="login_btn" value="登 录" />
                        <input type="reset" class="login_btn" value="取消" onclick="$('#username').focus();"/>
                    </p>
                </@s.form>
                
            </div>
            
        </div>
            
        <!--<div class="footer">
            <p>版权所有，中影国际影城</p>
            <p>技术支持，龙影科技有限公司</p>
        </div>-->
        
        <script type="text/javascript">
            $(function(){
                $("#username").focus();
            });
            
            //验证登录
            function check(){
                if($("#userName").val() == "" || $("#password").val() == ""){
                    alertMsg.error("用户和密码不能为空！");
                    return false;
                }
            }
            
        </script>
        
    </body>
</html>