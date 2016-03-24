//by GuJialiang
function Map() {     
	    /** 存放键的数组(遍历用到) */    
	    this.keys = new Array();     
	    /** 存放数据 */    
	    this.data = new Object();     
	         
	    /**   
	     * 放入一个键值对   
	     * @param {String} key   
	     * @param {Object} value   
	     */    
	    this.put = function(key, value) {     
	        if(this.data[key] == null){     
	            this.keys.push(key);     
	        }     
	        this.data[key] = value;     
	    };     
	    this.putAll=function(array){
			for(var i=0;i<array.length;i++){
				this.put(array[i],1);
			}
		};
		this.containAny=function(array){
			for(var i=0;i<array.length;i++){
				if(this.get(array[i])==1)
					return true;
			}
			return false;
		};
	    /**   
	     * 获取某键对应的值   
	     * @param {String} key   
	     * @return {Object} value   
	     */    
	    this.get = function(key) {     
	        return this.data[key];     
	    };     
	         
	    /**   
	     * 删除一个键值对   
	     * @param {String} key   
	     */    
	    this.remove = function(key) {
	    	if(this.get(key)!=null){
		    	this.keys.splice($.inArray(key,this.keys),1);
		        eval("delete this.data."+key); 
	    	}
	    };     
	         
	    /**   
	     * 遍历Map,执行处理函数   
	     *    
	     * @param {Function} 回调函数 function(key,value,index){..}   
	     */    
	    this.each = function(fn){     
	        if(typeof fn != 'function'){     
	            return;     
	        }     
	        var len = this.keys.length;     
	        for(var i=0;i<len;i++){     
	            var k = this.keys[i];     
	            fn(k,this.data[k],i);     
	        }     
	    };     
	         
	    /**   
	     * 获取键值数组(类似Java的entrySet())   
	     * @return 键值对象{key,value}的数组   
	     */    
	    this.entrys = function() {     
	        var len = this.keys.length;     
	        var entrys = new Array(len);     
	        for (var i = 0; i < len; i++) {     
	            entrys[i] = {     
	                key : this.keys[i],     
	                value : this.data[i]     
	            };     
	        }     
	        return entrys;     
	    };     
	         
	    /**   
	     * 判断Map是否为空   
	     */    
	    this.isEmpty = function() {     
	        return this.keys.length == 0;     
	    };     
	         
	    /**   
	     * 获取键值对数量   
	     */    
	    this.size = function(){     
	        return this.keys.length;     
	    };     
	         
	    /**   
	     * 重写toString    
	     */    
	    this.toString = function(){     
	        var s = "{";     
	        for(var i=0;i<this.keys.length;i++,s+=','){     
	            var k = this.keys[i];     
	            s += k+"="+this.data[k];     
	        }     
	        s+="}";     
	        return s;     
	    };     
	} 

//可编辑多选下拉框事件，以addSelect开头的class
(function($){
	$.fn.addSelect= function(opts){
		$.fn.mapcache=new Object();
		$("option[class^=addSelect]").each(function(){
	        var id=$(this).attr("class");
	        if($.fn.mapcache[id]==null)
	        	$.fn.mapcache[id]=new Map();
	        var oldvar=$('#'+id).val().split(",");
	        for(var i=0;i<oldvar.length&&oldvar[i]!="";i++){
	        	$.fn.mapcache[id].put(oldvar[i],1);
	        }
	        debugger
	        $(this).click({"newvar":this.value,"id":"'"+id+"'"},$.fn.addSelect.choosedone);
	    });
		$.fn.addSelect.choosedone=function(newvar,id){
			debugger
			var choosemap=$.fn.mapcache[id];
	        if(choosemap.get(newvar)==1){
	            choosemap.remove(newvar);
	        }else{
	            choosemap.put(newvar,1);
	        }
	        var temp="";
	        for(var i=0;i<choosemap.keys.length;i++){
	            temp+=choosemap.keys[i]+",";
	        }
	        if(temp.charAt(temp.length-1)==","){
	            temp=temp.substr(0,temp.length-1);
	        }
	        $('#'+id).val(temp);
		};
	};
	})(jQuery);