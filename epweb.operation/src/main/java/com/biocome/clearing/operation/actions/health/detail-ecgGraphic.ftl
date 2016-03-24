<p>&nbsp;</p>
<dd align="center">开始时间：${beginTime?string("yyyy-MM-dd HH:mm:ss")!''}&nbsp;&nbsp;&nbsp;&nbsp;持续时间${times}秒</dd>
<p>&nbsp;</p>
<div id="ecgContent" style="background-image:url('${ctx}/themes/images/ecg.jpg');overflow:auto;" tabindex="0">
    <canvas id="ecgChart" width="800" height="575"></canvas>
</div>
<script>
    var data="${ecgData}";
    if(data!=""){
        var dataarr=data.split(",");
        // dataarr.splice(0,0,5000);
        // dataarr.splice(0,0,-5000);
        var dataarr=dataarr.slice(0,20480);
        var label=new Array(dataarr.length);
        for(var i=0;i<label.length;i++){
            label[i]='';
        }
        $("#ecgChart").attr("width",dataarr.length);
        var data = {
            labels : label,
            datasets : [
                {
                    fillColor : "rgba(0,0,0,0)",
                    strokeColor : "black",       //线的颜色
                    pointColor : "rgba(0,0,0,0)",        //数据点的颜色
                    pointStrokeColor : "rgba(0,0,0,0)",              //数据点线圈的颜色
                    data : dataarr
                },{
                    fillColor : "rgba(0,0,0,0)",
                    strokeColor : "rgba(0,0,0,0)",       //线的颜色
                    pointColor : "rgba(0,0,0,0)",        //数据点的颜色
                    pointStrokeColor : "rgba(0,0,0,0)",              //数据点线圈的颜色
                    data:[5000,-5000]
                }
            ]
        }
    var option= {   
        //Boolean - 图标是否显示网格线 (默认值：true)
        scaleShowGridLines : false,
        //String - 网格线的颜色(默认值："rgba(0,0,0,.05)")
        scaleGridLineColor : "rgba(199,21,133,0.8)",
        //Number - 网格线的宽度  (默认值：1）
        scaleGridLineWidth : 0.5,
        //Boolean - 点与点之间的连线是否为曲线（true：曲线，false：直线） (默认值：true）
        bezierCurve : false,    
    
        //Number - 链接线的弯曲程度(0为直线)(默认值：0.4）
        bezierCurveTension :0,
    
        //Boolean - 是否显示数据点(默认值：true）
        pointDot : false,
    
        //Number - 数据点内圆的大小(像素)(默认值：4）
        pointDotRadius : 0,
    
        //Number - 数据点外环的宽度(像素)(默认值：1）
        pointDotStrokeWidth : 0,
    
        //Number - 显示鼠标左右多少像素以内的数据点(默认值：20）
        pointHitDetectionRadius : 0,
        //动态效果
        animation:false,
        //Boolean - 数据集行程（没看到效果...）(默认值：true）
        datasetStroke : false,
    
        //Number - 链接线的宽度(默认值：20）
        datasetStrokeWidth :1.5,
        
        //Boolean - 是否填充数据集(默认值：true）
        datasetFill : false,
        scaleShowLabels :false,
        //String - 一个展示模板
        };
        var ctx = document.getElementById("ecgChart").getContext("2d");
        var c=new Chart(ctx);   
        c.Line(data,option);
        //ctx.translate(-13,0);
        $("#ecgContent").focus();
    }else{
        alertMsg.error("心电数据为空");
    }
    
</script>