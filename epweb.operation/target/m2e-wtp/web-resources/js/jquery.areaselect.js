	(function($){
    /**
     * ctx: 工程路径
     * city_name：市级下拉列表ID
     * county_name：县级下拉列表ID
     */
    var optionHead='';
    $.fn.combo=function(ctx,city_name,county_name,defaultOptions){
        optionHead="";
        if(!defaultOptions){
            defaultOptions = {'':'请选择'};
        }
        $.each(defaultOptions,function(k,n){
            optionHead += "<option value="+k+">"+n+"</option>";
        });
        //获取省份select ID名称
        var object_name=this.selector.replace("#","");      
        //生成省份下拉列表
        var content="";                     
        $.ajax({
               url: ctx+'/baseinfo/area/get-provinces',
               //data:"anticache=" + Math.floor(Math.random()*1000),
               async: false,
               success: function(data){
                   content+=optionHead;
                    $.each(data,function(i,v){
                        content+="<option value='"+v.provinceCode+"'>"+v.provinceName+"</option>";
                    });
                    $("#"+object_name).html(content);
                    $("#"+city_name).html(optionHead);
                    $("#"+county_name).html(optionHead);
               }
        });         
        
        //绑定省份下拉列表事件
        $("#"+object_name).bind("change",function(){
            var code=$("#"+object_name+" option:selected").val(); 
            if(code!=0){
                $.ajax({
                       url: ctx+'/baseinfo/area/get-citys',
                       data: "id="+code,
                       success: function(data){
                           content=optionHead;
                            $.each(data,function(i,dto){
                                content+="<option value='"+dto.cityCode+"'>"+dto.cityName+"</option>";
                            });
                            $("#"+city_name).html(content);
                            $("#"+county_name).html(optionHead);
                       }
                });         
            }else{
                $("#"+city_name).html(optionHead);
                $("#"+county_name).html(optionHead);
            }
        });
        
        //绑定城市下拉列表事件
        $("#"+city_name).bind("change",function(){
            var code=$("#"+city_name+" option:selected").val(); 
            if(code!=0){
                $.ajax({
                       url: ctx+'/baseinfo/area/get-countrys',   
                       data: "id="+code,
                       success: function(data){
                           content=optionHead;
                            $.each(data,function(i,dto){
                                content+="<option value='"+dto.countryCode+"'>"+dto.countryName+"</option>";
                            });
                            $("#"+county_name).html(content);
                       }
                }); 
            }else{
                $("#"+county_name).html(optionHead);
            }
        });                     
    };
    
   
    //根据县级code显示相对应的省级，市级，县级
    $.comboByValue=function(ctx,province_name,city_name,county_name,provice_code,city_code,county_code){
        
        
        //显示省份
        if(provice_code==""){
            $("#"+province_name).children("option").eq(0).attr("selected","selected");
        }else{
             $("#"+province_name).val(provice_code);
        }
        
        //显示市
        $.asyncShowCity(ctx,province_name,city_name,county_name,provice_code,city_code);
        //if(city_code==""){
            //$("#"+city_name).children("option").eq(0).attr("selected","selected");
       // }//
        
        //显示县
        $.asyncShowCounty(ctx,city_name,county_name,city_code,county_code);
        //if(county_code==""){
         //   $("#"+county_name).children("option").eq(0).attr("selected","selected");
        //}
    };
    
    
    //同步查询市
    $.asyncShowCity=function(ctx,province_name,city_name,county_name,province_code,city_code){
        if(province_code!=""){
            $.ajax({
                   url: ctx+'/baseinfo/area/get-citys',
                   async: false,
                   data: "id="+province_code,
                   success: function(data){
                       content=optionHead;
                        $.each(data,function(i,dto){
                            if(dto.cityCode == city_code){
                                content+="<option selected='selected' value='"+dto.cityCode+"'>"+dto.cityName+"</option>";
                            }
                            else
                                content+="<option  value='"+dto.cityCode+"'>"+dto.cityName+"</option>";
                        });
                        $("#"+city_name).html(content);
                        $("#"+county_name).html(optionHead);
                   }
            });         
        }else{
            $("#"+city_name).html(optionHead);
            $("#"+county_name).html(optionHead);
        }
    } ;
    //同步查询区县
    $.asyncShowCounty=function(ctx,city_name,county_name,city_code,country_code){
        //var code=$("#"+city_name+" option:selected").val(); 
    
        if(city_code!=0){
            $.ajax({
                   url: ctx+'/baseinfo/area/get-countrys',
                   async: false,
                   data: "id="+city_code,
                   success: function(data){
                       content=optionHead;
                        $.each(data,function(i,dto){
                            if(dto.countryCode == country_code)
                                content+="<option selected='selected' value='"+dto.countryCode+"'>"+dto.countryName+"</option>";
                            else
                                content+="<option  value='"+dto.countryCode+"'>"+dto.countryName+"</option>";
                        });

                        $("#"+county_name).html(content);
                   }
            });         
        }else{
            $("#"+county_name).html(optionHead);
        }
    };
    
})(jQuery);