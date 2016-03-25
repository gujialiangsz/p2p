/*Jie*/
// JavaScript Document
$(function(){
	//����������
	$(".reset").die().live('click',function(){
		   $(this).parents("form").find("input[type='text'], input[type='number'], select").each(function(){
		        $(this).val('');
		   });
	});
	//��ʾ���ػ���
	var theme_flag = 0;
	$("#open_themeList").bind({
		click: function(){
			$("#themeList").css("display", "block").animate({"top":"3px", "opacity":"1"}, 500);
			setTimeout(function(){
				if(theme_flag == 0){
					$("#themeList").animate({"top":"-50px", "opacity":"0"}, 500, function(){
						$(this).css("display", "none");
					});
				}
			}, 600);
		},
		mouseover: function(){
			theme_flag = 1;
		},
		mouseleave: function(){
			theme_flag = 0;	
		}
	});
	$("#themeList").bind({
		mouseover: function(){
			theme_flag = 1;
		},
		mouseleave: function(){
			$(this).animate({"top":"-50px", "opacity":"0"}, 500, function(){
				$(this).css("display", "none");
				theme_flag = 0;
			});
		}
	});
	
	//ͷ��һ���˵������˵��Ĺ���
	$(".quick_link li a").click(function(){
		
		$(this).parent().siblings(".active").removeClass("active");
		$(this).parent().addClass("active");	
		
		var nav_id = $("#nav_" + $(this).attr("id").substring(5));
		
		$("#new_menu > ul").animate({"margin-left" : "-250px"}, 200, function(){
			
			//$("#new_menu > ul").hide();
			$("#new_menu > ul > li").css("margin-left", "-250px");
			
			nav_id.show().css("margin-left", "7px");
			nav_id.children("li").each(function(index, element) {
				$(this).animate({"margin-left" : "0"}, (index*30));
			});
			
		});
	});

    //������˵��Ķ����˵���������ͼ��
    $("#new_menu>ul>li>a").each(function(){
    	if($(this).parent().find("ul").length > 0){
    		$(this).attr("class", "hasMore");
    	}
    });
    
	//�����˵�չ������
	$("#new_menu > ul > li > a").click(function(){
		if($(this).parent().hasClass("active")) return false;
		
		var obj_cur = $("#new_menu");
		
		if(obj_cur.find("li.active").find("ul").length > 0) obj_cur.find("li.active > a").attr("class", "hasMore");
		obj_cur.find("li.active").removeClass("active");
		
		if($(this).parent().find("ul").length > 0) $(this).attr("class", "hasMoreOpen");
		$(this).parent().addClass("active");	
		
		obj_cur.find("li:has(ul)").each(function(index, element) {
			$(this).find("ul").slideUp(200);
		});
		$(this).parent().find("ul").slideDown(300);
	});
	$("#new_menu ul:first li:first > a").click();
	
	$(".gridHeader table th").live({
		click: function(){
			$(this).closest(".gridHeader").next(".gridScroller").find(".gridTbody");
		}	
	});
	
	
	
	//����
	$(".diy_table .up").live("click", function() {
		
		if($(this).closest("tr").index() == 0){
			return false;
		}
		
		numChange($(this).closest("tr").find("td:first"), $(this).closest("tr").prev("tr").find("td:first"));
		
		if ($(this).closest("tr").prev("tr").length != 0) {
			$(this).closest("tr").prev("tr").before($(this).closest("tr"));
		}
		setTableWidth($(".diy_table"));
	});
	//����
	$(".diy_table .down").live("click", function() {
		
		if(($(this).closest("tr").index() + 1) == $(this).closest("tbody").find("tr").length){
			return false;
		}
		
		numChange($(this).closest("tr").find("td:first"), $(this).closest("tr").next("tr").find("td:first"));
		
		if ($(this).closest("tr").next("tr").length != 0) {
			$(this).closest("tr").next("tr").after($(this).closest("tr"));
		}
		setTableWidth($(".diy_table"));
	});
	//�Ƴ�
	$(".diy_table .remove").live("click", function() {
		$(this).closest("tr").remove();
	});

	//˫���޸�
	var inputVal;
	$(".diy_td").live("dblclick", function() {
		if($(this).find("input:text").length > 0) return false;
		
		inputVal = $(this).find("font").html();
		$(this).find("font").html('<input type="text" value="' + inputVal + '" style="width:35px;" />');
		$(this).find("input:text").focus();
	});
	$(".diy_td input:text").live("blur", function() {
		var parnt = /^(\d+\.\d{1,2}|\d+)$/;
		
	    if(parnt.exec($(this).val())){
			$(this).closest("font").html($(this).val());
		}else{
			$(this).closest("font").html(inputVal);
		}
	});
	
	var decimalValue;
	$(".decimal2").live({
		focus: function(){
			decimalValue = $(this).val();
		},
		blur: function() {
			var parnt = /^(\d+\.\d{1,2}|\d+)$/;
			
		    if(!parnt.exec($(this).val())){
				$(this).val(decimalValue);
			}
	    
		}
		
	});
	$(".page form").die("submit");
	
	//��ֹￄ1�7�ύ
	$(".pageForm input:text, .pageForm input[type='date'], .pageForm input[type='number']").live("keydown", function(event){
		
		var e = event || window.event || arguments.callee.caller.arguments[0];
		
		if(e && e.keyCode == 13){
			var subFlag = true;
			if($.trim($(this).val()) != "") {
				var text_list = $(this).closest('form').find("input:text, input[type='date'], input[type='number']");
				text_list.each(function(index, element){
					if($.trim($(this).val()) == "" || $(this).hasClass("error")){
						$(this).focus();
						subFlag = false;
						return false;
					}
					
				});
			}
			
			if(subFlag){
				
				if($(this).closest("form").find(":submit").length > 0){
					$(this).closest("form").find(":submit").click();
				}
				if($(this).closest("form").find(".submit").length > 0){
					$(this).closest("form").find(".submit").click();
				}
			}
			else{
				return false;
			}
			
		}
		
		
	});
	
	
	$(".pageForm .submit").live("click", function(){
		$(this).closest('form').submit();
		
	});
	$(".checkboxCtrlSelectAll").live("click",function(){
		var ischecked=($(this).attr("checked")=="checked" || $(this).attr("checked")==true)?"checked":"false";
		setCheckBoxEvent(":checkbox[group='"+$(this).attr("group")+"']",ischecked);
	});
	
});

//����������Ž��ￄ1�7
function numChange(num1, num2){
	var tempNum = num1.find("div").html();
	
	num1.find("div").html(num2.find("div").html());
	num2.find("div").html(tempNum);
}


//�Ƚ��������ڴ�С
function validTime(startdate,enddate){
     var sdate = new Date(startdate);
     var edate = new Date(enddate);
    
      if(sdate.getTime() > edate.getTime()){
         return true;
     }else{
          return false;
     }
}
    

//��ʽ��ʱ��
function formatLcTime(farmatTime){
    var lc_Ftime = farmatTime.getFullYear();
    
    if((parseInt(farmatTime.getMonth())+1) < 10){
        lc_Ftime += "-" + "0" + (parseInt(farmatTime.getMonth())+1);
    }
    else{
        lc_Ftime += "-" + (parseInt(farmatTime.getMonth())+1);
    }
    if(farmatTime.getDate() < 10){
        lc_Ftime += "-" + "0" + farmatTime.getDate();
    }
    else{
        lc_Ftime += "-" + farmatTime.getDate();
    }
    if(farmatTime.getHours() < 10){
        lc_Ftime += " " + "0" + farmatTime.getHours();
    }
    else{
        lc_Ftime += " " + farmatTime.getHours();
    }
    if(farmatTime.getMinutes() < 10){
        lc_Ftime += ":" + "0" + farmatTime.getMinutes();
    }
    else{
        lc_Ftime += ":" + farmatTime.getMinutes();
    }
    if(farmatTime.getSeconds() < 10){
        lc_Ftime += ":" + "0" + farmatTime.getSeconds();
    }
    else{
        lc_Ftime += ":" + farmatTime.getSeconds();
    }
    
    return lc_Ftime;
}


//�Զ���table td�Ŀ�ￄ1�7
function setTableWidth(set_table){
	var top_table = set_table.closest(".gridScroller").prev(".gridHeader").find("table tr:last th");
	set_table.find("tr:first td").each(function(index, element){
		$(this).width(top_table.eq(index).width());
		
	});
}

//checkboxȫѡ,selectorѡ������"#id"/".class",ischecked�Ƿ�ѡ��checkedѡ�У�����ѡ
function setCheckBoxEvent(selector,ischecked){
	$(selector).each(function(){
		if(ischecked=="checked")
			$(this).attr("checked","checked");
		else
			$(this).removeAttr("checked");
	});
}
function checkChar(val,message){
    var arry="`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？";
    var map=new Map();
    map.putAll(arry);
    if(map.containAny(val)){
        alertMsg.info(message);
        return false;
    }else{
        return true;
    }
}
function myuuid(val) {
    var result = '';
    var hexcodes = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    for (var index = 0; index < val; index++) {
        var value = Math.floor(Math.random() * 62);
        result += hexcodes[value];
    }
    return result;
};
