<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=7" />
        <@resource.head />
        
        <link href="${ctx}/themes/css/core.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/themes/default/style.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/themes/css/base.css" rel="stylesheet" type="text/css" />
        
        <link href="${ctx}/js/html5uploader/html5uploader.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/js/chosen/chosen.css" type="text/css" rel="stylesheet"/>
        <script src="${ctx}/js/chosen/chosen.jquery.js" rel="stylesheet"></script>
        <script src="${ctx}/js/chosen/chosen.proto.js" rel="stylesheet"></script>
        <script src="${ctx}/js/utils/date-utils.js" rel="stylesheet" type="text/javascript"></script>
        <script src="${ctx}/js/site.js" type="text/javascript"></script>
        
        <script type="text/javascript" src="${ctx}/js/base.js?ver1.0"></script>
        <script type="text/javascript" src="${ctx}/js/jquery.mousewheel.min.js"></script>
        
        <script type="text/javascript" src="${ctx}/uploadify/scripts/jquery.uploadify.js"></script>
        
        <script type="text/javascript" src="${ctx}/js/html5uploader/jquery.html5uploader.js"></script>
        <script type="text/javascript" src="${ctx}/js/chart/Chart.js"></script>
        <script type="text/javascript" >
        if(typeof(Printer)=="undefined") {
			Printer = {};
			Printer.PrintTicketString = function(params){
				var message = 'Window.Print';
				
				if(typeof params != 'undefined'){
					message += ':' + params;	
				}	
				window.cefQuery({
					request:message,
					onSuccess:function(response)
					{
						target.value = response;	
					},
					onFailure:function(error_code,error_message){}
				}
				);
			};
		}
		function GetWindowInfo(element,test)
		{
			var message = 'Window.' + test;
			var target = document.getElementById(element);
			
			window.cefQuery(
			{
				request:message,
				onSuccess:function(response)
				{
					target.value = response;	
				},
				onFailure:function(error_code,error_message){}
			}
			);
		}
		
		
		function writecard(element)
		{
			var message = 'Window.WriteCard:58892';
			var target = document.getElementById(element);
			
			window.cefQuery(
			{
				request:message,
				onSuccess:function(response)
				{
					target.value = response;	
				},
				onFailure:function(error_code,error_message){}
			}
			);
		}
		if(typeof(ICReader)=="undefined") {
			ICReader = {};
			ICReader.ReadChipNo = function(params){
				GetWindowInfo('cardNo','ReadCardNo');
			};
			
			ICReader.GetReadType = function(params){
				GetWindowInfo('cardNo','ReadCardNo');
			};
			ICReader.Read = function(params){
				GetWindowInfo('cardNo','ReadCardNo');
			};
			
		}
            
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
        <title>万市通运营系统</title>
    </head>
    <body scroll="no" onbeforeunload="" oncontextmenu="return false;" ondragstart="return false;" onkeydown="if(event.keyCode==116){event.keyCode=0;return false;}">
        <div id="layout">
            <div id="header" name="${currentUser.name}">
                <div class="headerNav">
                    <div class="userHead">
                        <a href="javascript:;" class="icon_user"></a>
                        
                        <p>
                            <span>您好，${currentUser.name}<a href="<@s.url "/system/user-pwd-reset?userId=${currentUser.id}" />" target="dialog"  rel="system_edit_user_info" title="修改资料" width="600" height="400" id="change_user_info" style="color:#3CF;">【修改】</a></span>
                            <span><@system.getValue path="${currentUser.settings.defaultActor.name}" items=actorlist itemKey="code" itemValue="codeName"/>
                                <a href="<@s.url "/system/show-actor" />" target="navTab"  rel="system_show_actor" title="切换职务" width="600" height="400" id="change_role">【切换职务】</a></span>
                            <span>运营中心</span>
                        </p>
                    </div>

                    <!-- 头部一级菜单 -->
                    <div class="quick_link">
                        <ul>
                            <@shiro.hasAnyRoles name="BM_A,BM_M,BM_V,JS_A,JS_M,JS_V,YH_A,YH_M,YH_V,YG_A,YG_M,YG_V,XTJK_A,XTJK_M,RZ_V,JXPZ_A,JXPZ_M,JXPZ_V,SJCS_A,SJCS_M,SJCS_V,YYPZ_A,YYPZ_M,YYPZ_V,JKPZ_A,JKPZ_M,JKPZ_V,YYBB_A,YYBB_M,YYBB_V">
                            <li class="active">
                                <a href="javascript:;" class="icon_security" id="show_basic"><span>系统管理</span></a>
                            </li>
                            </@shiro.hasAnyRoles>
                            <@shiro.hasAnyRoles name="CZYW_A,CZYW_M,CZYW_V,CSHYW_A,CSHYW_M,CSHYW_V,TKXK_A,TKXK_M,TKXK_V,CZMX_V,CSHMX_V,TKXKMX_V,XFMX_V,DZWJ_A,DZWJ_M,DZWJ_V,YSJY_V">
                            <li>
                                <a href="javascript:;" class="icon_data" id="show_data"><span>业务管理</span></a>
                            </li>
                            </@shiro.hasAnyRoles>
                            <@shiro.hasAnyRoles name="CZDZBB_V,CSHDZBB_V,TKXKDZBB_V,CZTJBB_V,CSHTJBB_V,TKXKTJBB_V,TKXKTJBB_V,XFTJBB_V">
                            <li>
                                <a href="javascript:;" class="icon_report" id="show_report"><span>报表管理</span></a>
                            </li>
                            </@shiro.hasAnyRoles>
                        </ul>
                    </div>
                    
                    <!-- 功能按钮 -->
                    <div class="func_btn">
	                    <div class="winbtn-leftadge"></div>
	                    <a href="javascript:;" class="icon_skin" id="open_themeList" title="换肤中心"></a>
	                    <div class="winbtn-spacer"></div>
	                    <a href="javascript:;" class="icon_min" id="min_sys" title="最小化"></a>
	                    <div class="winbtn-spacer"></div>
	                    <a href="${ctx}/logout" class="icon_logout" id="logout_sys" title="注销登录"></a>
	                    <div class="winbtn-spacer"></div>
	                </div>

                    <!-- 换肤 -->
                    <ul class="themeList" id="themeList">
                        <li theme="default">
                            <div class="selected">
                                蓝色
                            </div>
                        </li>
                        <li theme="green">
                            <div>
                                绿色
                            </div>
                        </li>
                        <li theme="purple">
                            <div>
                                紫色
                            </div>
                        </li>
                        <li theme="silver">
                            <div>
                                黑色
                            </div>
                        </li>
                        <li theme="azure">
                            <div>
                                棕色
                            </div>
                        </li>
                    </ul>
                </div>

                <!-- navMenu -->

            </div>

            <!-- 左侧菜单 -->
            <div id="leftside">
                <div id="sidebar_s">
                    <div class="collapse">
                        <div class="toggleCollapse">
                            <div></div>
                        </div>
                    </div>
                </div>
                <div id="sidebar" >

                    <div class="accordion" fillSpace="sidebar">
                        <div class="accordionContent">

                            <!-- 二级三级菜单列表 -->
                            <div id="new_menu">

                                <!--*********** 系统管理 **************-->
                                <ul id="nav_basic" style="display: block;">
                                    <@shiro.hasAnyRoles name="BM_A,BM_M,BM_V,JS_A,JS_M,JS_V,YH_A,YH_M,YH_V,YG_A,YG_M,YG_V">
                                    <li>
                                        <a href="javascript:;"><i class="icon_1_1"></i><span>用户权限</span></a>
                                        <ul>
                                            <@shiro.hasAnyRoles name="BM_A,BM_M,BM_V">
                                            <li><a rel="system_organ-list" target="navTab" href="<@s.url "/system/organ-list"/>" height="480" width="800">部门管理</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="JS_A,JS_M,JS_V">
                                            <li><a rel="system_role-list" target="navTab" href="<@s.url "/system/role-list"/>" height="480" width="800">角色管理</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="YH_A,YH_M,YH_V">
                                            <li><a rel="system_user-list" target="navTab" href="<@s.url "/system/user-list"/>" height="480" width="1000">用户管理</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="YG_A,YG_M,YG_V">
                                            <li><a rel="employee-list" target="navTab" href="<@s.url "/system/employee-list"/>" height="480" width="800"><i class="icon_2"></i><span>员工管理</span></a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                        </ul>
                                    </li>
                                    </@shiro.hasAnyRoles>
                                    
                                    
                                    <@shiro.hasAnyRoles name="JXPZ_A,JXPZ_M,JXPZ_V,SJCS_A,SJCS_M,SJCS_V,YYPZ_A,YYPZ_M,YYPZ_V,JKPZ_A,JKPZ_M,JKPZ_V,YYBB_A,YYBB_M,YYBB_V">
                                    <li>
                                        <a href="javascript:;"><i class="icon_4_4"></i><span>系统参数</span></a>
                                        <ul>
                                            <@shiro.hasAnyRoles name="YYPZ_A,YYPZ_M,YYPZ_V">   
                                             <li>
                                                    <a rel="app-appVersion-list" width="1010" height="650" target="navTab" href="<@s.url "/app/appVersion-list"/>">应用版本</a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="YYPZ_A,YYPZ_M,YYPZ_V">   
                                             <li>
                                                    <a rel="system-appConfig-listGroup" width="1010" height="650" target="navTab" href="<@s.url "/system/appConfig-list"/>">应用配置</a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="JKPZ_A,JKPZ_M,JKPZ_V">   
                                             <li>
                                                    <a rel="system-interface-listGroup" width="1010" height="650" target="navTab" href="<@s.url "/system/interface-listGroup"/>">接口配置</a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                             <@shiro.hasAnyRoles name="JXPZ_A,JXPZ_M,JXPZ_V">   
                                             <li>
                                                    <a rel="system-analysis-listMain" width="1010" height="650" target="navTab" href="<@s.url "/system/analysis-listMain"/>">解析配置</a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="SJCS_A,SJCS_M,SJCS_V">
                                            <li>
                                                <a rel="coding-list" target="navTab" href="<@s.url "/system/coding-list"/>" height="725" width="700"><i class="icon_1_5"></i><span>数据参数</span></a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                        </ul>
                                    </li>
                                    </@shiro.hasAnyRoles>
                                    
                                    <@shiro.hasAnyRoles name="XTJK_A,XTJK_M,RZ_V">
                                    <li>
                                        <a href="javascript:;"><i class="icon_6_4"></i><span>系统监控</span></a>
                                        <ul>
                                             <@shiro.hasAnyRoles name="XTJK_A,XTJK_M">   
                                             <li>
                                                    <a rel="system-status-list" width="1010" height="650" target="navTab" href="<@s.url "/system/status-list"/>">系统状态</a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="RZ_V"> 
                                            <li>
                                                <a rel="system-log-list" target="navTab" href="<@s.url "/system/log-list"/>" height="600" width="1100">系统日志</a>
                                            </li>
                                            </@shiro.hasAnyRoles>
                                        </ul>
                                    </li>
                                    </@shiro.hasAnyRoles>
                                </ul>
                                
                                <!-- 业务管理-->
                                 <ul id="nav_data">
                                     <@shiro.hasAnyRoles name="SHGL_A,SHGL_V,SHGL_M,WTFK_M,WTFK_V">
                                        <li>
                                            <a href="javascript:;" class=""><i class="icon_3_1"></i><span>商务管理</span></a>
                                            <ul>
                                                <@shiro.hasAnyRoles name="SHGL_A,SHGL_V,SHGL_M">
                                                <li><a rel="business-agent-list" target="navTab" href="<@s.url "/business/agent-list"/>" height="700" width="1200">代理商信息</a></li>
                                                </@shiro.hasAnyRoles>
                                                <@shiro.hasAnyRoles name="WTFK_M,WTFK_V">
                                                <li><a rel="business-question-list" target="navTab" href="<@s.url "/business/question-list"/>" height="700" width="1200">问题反馈</a></li>
                                                </@shiro.hasAnyRoles>
                                            </ul>
                                        </li>
                                    </@shiro.hasAnyRoles>
                                    <@shiro.hasAnyRoles name="CZYW_A,CZYW_M,CZYW_V,CSHYW_A,CSHYW_M,CSHYW_V,TKXK_A,TKXK_M,TKXK_V">
                                        <li>
                                            <a href="javascript:;" class=""><i class="icon_3_3"></i><span>业务处理</span></a>
                                            <ul>
                                                <@shiro.hasAnyRoles name="CZYW_A,CZYW_M,CZYW_V">
                                                <li><a rel="data-deal-recharge" target="navTab" href="<@s.url "/data/deal-recharge"/>" height="700" width="1200">充值业务处理</a></li>
                                                </@shiro.hasAnyRoles>
                                                <@shiro.hasAnyRoles name="CSHYW_A,CSHYW_M,CSHYW_V">
                                                <li><a rel="data-deal-init" target="navTab" href="<@s.url "/data/deal-init"/>" height="700" width="1200">初始化业务处理</a></li>
                                                </@shiro.hasAnyRoles>
                                                <@shiro.hasAnyRoles name="TKXK_A,TKXK_M,TKXK_V">
                                                <li><a rel="data-deal-refund" target="navTab" href="<@s.url "/data/deal-refund"/>" height="700" width="1200">退卡销卡业务处理</a></li>
                                                </@shiro.hasAnyRoles>
                                            </ul>
                                        </li>
                                    </@shiro.hasAnyRoles>
                                    
                                    <@shiro.hasAnyRoles name="CZMX_V,CSHMX_V,TKXKMX_V,XFMX_V,ZCYH_V,YSJY_V,SBXX_V">
                                      <li>
                                        <a href="javascript:;" class=""><i class="icon_3_2"></i><span>明细查询</span></a>
                                        <ul>
                                            <@shiro.hasAnyRoles name="CZMX_V">
                                            <li><a rel="data-detail-recharge" target="navTab" href="<@s.url "/data/detail-recharge"/>" height="700" width="1200">充值明细查询</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="CSHMX_V">
                                            <li><a rel="data-detail-init" target="navTab" href="<@s.url "/data/detail-init"/>" height="700" width="1200">初始化明细查询</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="TKXKMX_V">
                                            <li><a rel="data-detail-refund" target="navTab" href="<@s.url "/data/detail-refund"/>" height="700" width="1200">退卡销卡明细查询</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="XFMX_V">
                                            <li><a rel="data-detail-consume" target="navTab" href="<@s.url "/data/detail-consume"/>" height="700" width="1200">消费明细查询</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="KQJL_V">
                                            <li><a rel="data-detail-attendance" target="navTab" href="<@s.url "/data/detail-attendance"/>" height="700" width="1200">考勤明细查询</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="ZCYH_V">
                                            <li><a rel="data-account-user" target="navTab" href="<@s.url "/data/wccuser-list"/>" height="700" width="1200">注册用户信息</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="YSJY_V">
                                            <li><a rel="data-account-original" target="navTab" href="<@s.url "/data/detail-original"/>" height="700" width="1200">原始交易明细查询</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="SBXX_V">
                                            <li><a rel="data-account-device" target="navTab" href="<@s.url "/data/detail-device"/>" height="700" width="1200">设备信息明细查询</a></li>
                                            </@shiro.hasAnyRoles>
                                        </ul>
                                    </li>
                                    </@shiro.hasAnyRoles>
                                    
                                    
                                    <@shiro.hasAnyRoles name="SBXX_V,SBXX_M,KQSB_V,KQSB_M">
                                      <li>
                                        <a href="javascript:;" class=""><i class="icon_3_2"></i><span>设备管理</span></a>
                                        <ul>
                                            <@shiro.hasAnyRoles name="SBXX_V,SBXX_M">
                                            <li><a rel="data-account-device" target="navTab" href="<@s.url "/data/detail-device"/>" height="700" width="1200">产品设备信息管理</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="KQSB_V,KQSB_M">
                                            <li><a rel="data-account-attendanceDevice" target="navTab" href="<@s.url "/data/detail-attendanceDevice"/>" height="700" width="1200">考勤设备信息管理</a></li>
                                            </@shiro.hasAnyRoles>
                                        </ul>
                                    </li>
                                    </@shiro.hasAnyRoles>
                                    
                                    <@shiro.hasAnyRoles name="XDSJ_V">
                                        <li>
                                            <a href="javascript:;" class=""><i class="icon_6_3"></i><span>健康管理</span></a>
                                            <ul>
                                                <@shiro.hasAnyRoles name="XDSJ_V">
                                                <li><a rel="health-detail-ecg" target="navTab" href="<@s.url "/health/detail-ecg"/>" height="700" width="1200">心电信息</a></li>
                                                </@shiro.hasAnyRoles>
                                            </ul>
                                        </li>
                                    </@shiro.hasAnyRoles>
                                  </ul>
                                  
                       <!--报表管理-->
                                  <ul id="nav_report">
                                      <@shiro.hasAnyRoles name="CZDZBB_V,CSHDZBB_V,TKXKDZBB_V">
                                      <li>
                                        <a href="javascript:;" class=""><i class="icon_6_1"></i><span>一卡通对账报表</span></a>
                                        <ul>
                                            <@shiro.hasAnyRoles name="CZDZBB_V">
                                            <li><a rel="report-ykt-recharge" target="navTab" href="<@s.url "/report/ykt-recharge"/>" height="700" width="1200">充值对账报表</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="CSHDZBB_V">
                                            <li><a rel="report-ykt-init" target="navTab" href="<@s.url "/report/ykt-init"/>" height="700" width="1200">初始化对账报表</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="TKXKDZBB_V">
                                            <li><a rel="report-ykt-refund" target="navTab" href="<@s.url "/report/ykt-refund"/>" height="700" width="1200">退卡销卡对账报表</a></li>
                                            </@shiro.hasAnyRoles>
                                        </ul>
                                    </li>
                                    </@shiro.hasAnyRoles>
                                    
                                    <@shiro.hasAnyRoles name="CZTJBB_V,CSHTJBB_V,TKXKTJBB_V,TKXKTJBB_V,XFTJBB_V">
                                      <li>
                                        <a href="javascript:;" class=""><i class="icon_6_5"></i><span>统计报表</span></a>
                                        <ul>
                                            <@shiro.hasAnyRoles name="CZTJBB_V">
                                            <li><a rel="report-statistics-recharge" target="navTab" href="<@s.url "/report/statistics-recharge"/>" height="700" width="1200">充值统计报表</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="CSHTJBB_V">
                                            <li><a rel="report-statistics-init" target="navTab" href="<@s.url "/report/statistics-init"/>" height="700" width="1200">初始化统计报表</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="TKXKTJBB_V">
                                            <li><a rel="report-statistics-refund" target="navTab" href="<@s.url "/report/statistics-refund"/>" height="700" width="1200">退卡销卡统计报表</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="CWSZTJBB_V">
                                            <li><a rel="report-statistics-financial" target="navTab" href="<@s.url "/report/statistics-financial"/>" height="700" width="1200">财务收支统计报表</a></li>
                                            </@shiro.hasAnyRoles>
                                            <@shiro.hasAnyRoles name="XFTJBB_V">
                                            <li><a rel="report-statistics-consume" target="navTab" href="<@s.url "/report/statistics-consume"/>" height="700" width="1200">消费统计报表</a></li>
                                            </@shiro.hasAnyRoles>
                                        </ul>
                                    </li>
                                    </@shiro.hasAnyRoles>
                                  </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div id="container">
                <div id="navTab" class="tabsPage">
                    <div class="tabsPageHeader">
                        <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                            <ul class="navTab-tab">
                                <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
                            </ul>
                        </div>
                        <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                        <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                        <div class="tabsMore">more</div>
                    </div>
                    <ul class="tabsMoreList">
                        <li><a href="javascript:;">快捷功能</a></li>
                    </ul>
                    
                    <div class="navTab-panel tabsPageContent layoutBox">
                        <div class="page">

                            <div class="quick_func">
                                
                                <@shiro.hasAnyRoles name="YH_A,YH_M,YH_V">
                                    <a rel="system_user-list" target="navTab" href="<@s.url "/system/user-list"/>" >
                                    <div class="ng_function">
                                        <div class="quick_6"></div>
                                        <span>管理用户</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="SJCS_A,SJCS_M,SJCS_V">
                                    <a rel="coding-list" target="navTab" href="<@s.url "/system/coding-list"/>" >
                                    <div class="ng_function">
                                        <div class="quick_1" ></div>
                                        <span>参数字典</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="XTJK_A,XTJK_M">   
                                    <a rel="system-status-list" target="navTab" href="<@s.url "/system/status-list"/>" >
                                    <div class="ng_function">
                                        <div class="quick_2" ></div>
                                        <span>系统监控</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="YSJY_V">
                                    <a rel="data-account-original" target="navTab" href="<@s.url "/data/detail-original"/>" >
                                    <div class="ng_function">
                                        <div class="quick_3" ></div>
                                        <span>原始交易</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="XFMX_V">
                                    <a rel="data-detail-consume" target="navTab" href="<@s.url "/data/detail-consume"/>" >
                                    <div class="ng_function">
                                        <div class="quick_5" ></div>
                                        <span>消费明细</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="XDSJ_V">
                                    <a rel="health-detail-ecg" target="navTab" href="<@s.url "/health/detail-ecg"/>" >
                                    <div class="ng_function">
                                        <div class="quick_4" ></div>
                                        <span>心电信息</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="ZCYH_V">
                                    <a rel="data-account-user" target="navTab" href="<@s.url "/data/wccuser-list"/>" >
                                    <div class="ng_function">
                                        <div class="quick_9" ></div>
                                        <span>产品用户</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="SBXX_V">
                                    <a rel="data-account-device" target="navTab" href="<@s.url "/data/detail-device"/>" >
                                    <div class="ng_function">
                                        <div class="quick_8" ></div>
                                        <span>设备管理</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                                <@shiro.hasAnyRoles name="KQJL_V">
                                    <a rel="data-detail-attendance" target="navTab" href="<@s.url "/data/detail-attendance"/>" >
                                    <div class="ng_function">
                                        <div class="quick_11" ></div>
                                        <span>考勤管理</span>
                                    </div>
                                    </a>
                                </@shiro.hasAnyRoles>
                            
                            <div style="clear:both;"></div>
                           </div>

                        </div>

                    </div>
                </div>
            </div>

        </div>

        <div id="footer">
            Copyright © 2015 <a target="_blank" href="http://www.biocome.com">奔凯安全科技有限公司</a>
            <a href="javascript:;">&nbsp;当前时间：</a>
            <a href="javascript:;" id="indexDate">${dateNow}</a>
        </div>

        <script type="text/javascript">
            $(function() {
                DWZ.init("dwz.frag.xml", {
                    loginUrl : "${ctx}/login",
                    debug : false,
                    callback : function() {
                        initEnv();
                        $("#themeList").theme({
                            //dwzTheme: "${ctx}/dwz/themes",
                            //fixTheme: "${ctx}/fix/themes",
                            appTheme : "${ctx}/themes"
                        });
                        // navTab.openTab("main", "home", {
                        // title : "我的首页"
                        // });
                        setTimeout("clickNavMenu(0)", 10);
                    }
                });
                
                
                //首页时间
                var indexDate = $("#indexDate").html();
                indexDate = new Date(indexDate);
                setInterval(function(){
                	indexDate.setSeconds(indexDate.getSeconds() + 1);
                	$("#indexDate").html(formatLcTime(indexDate));
                }, 1000);
                
                $("#logout_sys").click(function(){
                    if(confirm('您确定要注销当前登录用户回到登录窗口吗？')){
                        // var ev = window.event;//获取event对象  
                        // location.replace(this.href);
                        // ev.returnValue=false;
                        window.close(true);
                        window.open("${ctx}/logout");
                        return false;
                    }
                    else{
                        return false;
                    }
                });
                $("#quite_sys").click(function(){
                    if(confirm('您确定要注销并关闭程序吗？')){
                        $.ajax({url:"${ctx}/logout",async:false}); 
                        WinForm.Close(); 
                        return false;;
                    }
                });
                $("#min_sys").click(function(){
                        WinForm.MinScreen();
                        return false;
                });
            });
            
            //监听浏览器关闭
            //window.onbeforeunload = onbeforeunload_handler;
            //window.onunload= onunload_handler;  
            function onbeforeunload_handler(){   
                window.event.returnValue="确定注销并离开当前页面吗？";
            }   
            //socket链接状态
            var lc_objs = []; 
            function onunload_handler(){
                $.ajax({url:"${ctx}/logout",async:false}); 
            }
            
              //当前用户权限
            var permissions=new Map();
            //by GuJialiang
            $(function(){
            $.ajax({
                type:"post",
                dataType:'json',
                url:"${ctx}/system/current-permission",
                success:function(data){
                    for(var i=0;i<data.length;i++){
                         permissions.put(data[i].code,1);
                    }
                },
                error:function(){
                }
                });
            });
            //查询是否有权限
            function hasAnyRoles(roles){
                if(Object.prototype.toString.call(roles) === '[object Array]'){
                    return permissions.containAny(roles);
                }else{
                    if(permissions.get(roles)==1)
                        return true;
                }
                return false;    
            }
        </script>
    </body>
</html>
