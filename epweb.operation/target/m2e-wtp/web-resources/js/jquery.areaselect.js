	(function($){
    /**
     * ctx: ����·��
     * city_name���м������б�ID
     * county_name���ؼ������б�ID
     */
    var optionHead='';
    $.fn.combo=function(ctx,city_name,county_name,defaultOptions){
        optionHead="";
        if(!defaultOptions){
            defaultOptions = {'':'��ѡ��'};
        }
        $.each(defaultOptions,function(k,n){
            optionHead += "<option value="+k+">"+n+"</option>";
        });
        //��ȡʡ��select ID����
        var object_name=this.selector.replace("#","");      
        //����ʡ�������б�
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
        
        //��ʡ�������б��¼�
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
        
        //�󶨳��������б��¼�
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
    
   
    //�����ؼ�code��ʾ���Ӧ��ʡ�����м����ؼ�
    $.comboByValue=function(ctx,province_name,city_name,county_name,provice_code,city_code,county_code){
        
        
        //��ʾʡ��
        if(provice_code==""){
            $("#"+province_name).children("option").eq(0).attr("selected","selected");
        }else{
             $("#"+province_name).val(provice_code);
        }
        
        //��ʾ��
        $.asyncShowCity(ctx,province_name,city_name,county_name,provice_code,city_code);
        //if(city_code==""){
            //$("#"+city_name).children("option").eq(0).attr("selected","selected");
       // }//
        
        //��ʾ��
        $.asyncShowCounty(ctx,city_name,county_name,city_code,county_code);
        //if(county_code==""){
         //   $("#"+county_name).children("option").eq(0).attr("selected","selected");
        //}
    };
    
    
    //ͬ����ѯ��
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
    //ͬ����ѯ����
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