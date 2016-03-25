(function($){
$.fn.html5uploader = function(opts){
	var itemTemp = '<div id="${fileID}" class="uploadify-queue-item"><div class="uploadify-progress"><div class="uploadify-progress-bar"></div></div><span class="up_filename">${fileName}</span><span class="uploadbtn">上传</span><span class="delfilebtn">删除</span></div>';
	var defaults = {
		fileTypeExts:'*.*',//允许上传的文件类型，填写mime类型
		url:'',//文件提交的地址
		auto:false,//自动上传
		multi:true,//默认允许选择多个文件
		showPercent:false,//是否实时显示上传的百分比，如20%
		showUploaded:false,//是否实时显示已上传的文件大小，如1M/2M
		buttonText:'选择文件',//上传按钮上的文字
		removeTimeout: 1000,//上传完成后进度条的消失时间
		itemTemplate:itemTemp,//上传队列显示的模板
		onUploadStart:function(){},//上传开始时的动作
		onUploadSuccess:function(){},//上传成功的动作
		onUploadComplete:function(){},//上传完成的动作
		onUploadError:function(){}, //上传失败的动作
		onUploadCheck:function(){},
		onInit:function(){},//初始化时的动作
		};
		
	var option = $.extend(defaults,opts);
	
	//将文件的单位由bytes转换为KB或MB
	var formatFileSize = function(size){
		if (size> 1024 * 1024){
			size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
			}
		else{
			size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
			}
		return size;
		};
	//根据文件序号获取文件
	var getFile = function(index,files){
		for(var i=0;i<files.length;i++){	   
		  if(files[i].index == index){
			  return files[i];
			  }
		}
		return false;
	};
	//将文件类型格式化为数组
	var formatFileType = function(str){
		if(str){
			return str.split(",");	
			}
		return false;
		};
	
	//将输入的文件类型字符串转化为数组,原格式为*.jpg;*.png
	var getFileTypes = function(str){
		var result = new Array();
		var arr1 = str.split(",");
		for(var i in arr1){
			result.push(getMIME(arr1[i].split(".")[1]));
			}
		return result;
		};
	
	//将允许的文件类型转化为MIME类型
	var getMIME = function(ext){
		var mimeobj = {
			'*':'application/octet-stream',
			'apk':'application/octet-stream',
			'hex':'application/octet-stream',
			'bak':'application/octet-stream',
			'txt':'text/plain',
			'csv':'text/plain',
			'tsv':'text/plain',
			'doc':'application/msword',
			'docx':'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
			'xls':'application/vnd.ms-excel',
			'xlsx':'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
			'ppt':'application/vnd.ms-powerpoint',
			'pptx':'application/vnd.openxmlformats-officedocument.presentationml.presentation',
			'pdf':'application/pdf',			
			'mp3':'audio/mpeg',
			'mp4':'video/mp4',
			'jpeg':'image/jpeg',
			'jpg':'image/jpeg',
			'bmp':'image/bmp',
			'png':'image/png',
			'html':'text/html',
			'htm':'text/html',
			'mpt':'application/mmspowerpoint',
			'ibooks':'application/ibooks',
			'bin':'application/octet-stream'
			};
		return mimeobj[ext];
		};
	
	this.each(function(){
		var _this = $(this);
		//先添加上file按钮和上传列表
		var instanceNumber = $('.uploadfile').length+1;
		var inputstr = '<input id="'+instanceNumber+'instance" class="uploadfile" style="visibility:hidden;" type="file" name="fileselect[]"';
		if(option.multi){
			inputstr += ' multiple';
			}
		inputstr += ' accept="';
		inputstr += getFileTypes(option.fileTypeExts).join(",");
		inputstr += '"/>';
		inputstr += '<a href="javascript:void(0)" class="uploadfilebtn">';
		inputstr += option.buttonText;
		inputstr += '</a>';
		var fileInputButton = $(inputstr);
		var uploadFileList = $('<div class="uploadify-queue"></div>');
		if(_this.attr('type')=="file"){
			var div = $('<div></div>');
			_this.hide().after(div);
			_this = div;
			}
		_this.append(uploadFileList);	
		_this.before(fileInputButton);
		
		
		
		//创建文件对象
			  var ZXXFILE = {
			  fileInput: _this.prevAll('.uploadfile').get(0),				//html file控件
			  upButton: null,					//提交按钮
			  url: option.url,						//ajax地址
			  fileFilter: [],					//过滤后的文件数组
			  filter: function(files) {		//选择文件组的过滤方法
				  var arr = [];
				  var typeArray = getFileTypes(option.fileTypeExts);
				  if(!typeArray){
					  for(var i in files){
							  if(files[i].constructor==File){
								arr.push(files[i]);
							  }
						  }
					  }
				  else{
					  for(var i in files){
						  if(files[i].constructor==File){
							if(files[i].type==""||$.inArray(files[i].type,typeArray)>=0){
								arr.push(files[i]);	
								}
							else{
								alert('文件类型不允许！');
								fileInputButton.val('');
								}  	
							} 
						}	
					  }
				  return arr;  	
			  },
			  //文件选择后
			  onSelect: function(files){

				 for(var i=0;i<files.length;i++){
					var file = files[i];
					var check=option.onUploadCheck(file.name);
					if(check!=true){
						fileInputButton.val('');
						return false;
					}
					var html = option.itemTemplate || itemTemp;
					
					//处理模板中使用的变量
					html = html.replace(/\${fileID}/g,file.index).replace(/\${fileName}/g,file.name).replace(/\${fileSize}/g,formatFileSize(file.size)).replace(/\${instanceID}/g,instanceNumber);
					uploadFileList.append(html);
					
					//判断是否显示已上传文件大小
					if(option.showUploaded){
						var num = '<span class="progressnum"><span class="uploadedsize">0</span>/<span class="totalsize">${fileSize}</span></span>'.replace(/\${fileSize}/g,formatFileSize(file.size));
						$('.uploadify-progress').after(num);
						}
					
					//判断是否显示上传百分比	
					if(option.showPercent){
						var percentText = '<span class="up_percent"></span>';
						$('.uploadify-progress').after(percentText);
						}
					//判断是否是自动上传
					 if(option.auto){
						 ZXXFILE.funUploadFile(file);
						 }
				 }
				 
				 //如果配置非自动上传，绑定上传事件
				 if(!option.auto){
					 //如果定义的上传按钮class不符，则为上传按钮添加class
					  var uploadbtn = _this.find('span:contains("上传")');
					  uploadbtn.each(function(index,element){
						  if(!$(element).hasClass('uploadbtn')){
							  $(element).addClass('uploadbtn');
							  $(element).parent('.btn').attr('href','javascript:void(0)');
							  }
						});
						
					 uploadbtn.off().on('click',function(){
					  var index = parseInt($(this).parents('.uploadify-queue-item').attr('id'));
					  ZXXFILE.funUploadFile(getFile(index,files));
					  });
				 }
				 //为删除文件按钮绑定删除文件事件
				 _this.find('.delfilebtn').off().on('click',function(){
					 var index = parseInt($(this).parents('.uploadify-queue-item').attr('id'));
					 ZXXFILE.funDeleteFile(index);
					 });
				 
				},		
			  //文件删除后
			  onDelete: function(index) {
				  _this.find('#'+index).fadeOut();
				  },		
			  onProgress: function(file, loaded, total) {
					var eleProgress = _this.find('#'+file.index+' .uploadify-progress');
					var percent = (loaded / total * 100).toFixed(2) +'%';
					if(eleProgress.nextAll('.progressnum').length>0){
						eleProgress.nextAll('.progressnum .uploadedsize').text(formatFileSize(loaded));
						eleProgress.nextAll('.progressnum .totalsize').text(formatFileSize(total));
						}
					if(eleProgress.nextAll('.up_percent').length>0){
						eleProgress.nextAll('.up_percent').text(percent);	
						}
					eleProgress.children('.uploadify-progress-bar').css('width',percent);
		  		},		//文件上传进度
			  onUploadSuccess: option.onUploadSuccess,		//文件上传成功时
			  onUploadError: option.onUploadError,		//文件上传失败时,
			  onUploadComplete: option.onUploadComplete,		//文件全部上传完毕时
			  
			  /* 开发参数和内置方法分界线 */
			  
			  //获取选择文件，file控件或拖放
			  funGetFiles: function(e) {	  
				  // 获取文件列表对象
				  var files = e.target.files || e.dataTransfer.files;
				  //继续添加文件
				  files = this.filter(files);
				  this.fileFilter.push(files);
				  this.funDealFiles(files);
				  return this;
			  },
			  
			  //选中文件的处理与回调
			  funDealFiles: function(files) {
				  var fileCount = _this.find('.uploadify-queue .uploadify-queue-item').length;//队列中已经有的文件个数
				  for(var i=0;i<files.length;i++){
					  files[i].index = ++fileCount;
					  files[i].id = files[i].index;
					  }
				  //执行选择回调
				  this.onSelect(files);
				  
				  return this;
			  },
			  
			  //删除对应的文件
			  funDeleteFile: function(index) {

				  for (var i = 0; i<this.fileFilter.length; i++) {
					  for(var j=0; j<this.fileFilter[i].length; j++){
						  var file = this.fileFilter[i][j];
						  if (file.index == index) {
							  this.fileFilter[i].splice(j,1);
							  this.onDelete(index);	
						  }
					  }
				  }
				  return this;
			  },
			  
			  //文件上传
			  funUploadFile: function(file) {
				  var self = this;	
				  var xhr = false;
				  (function(file) {
					  try{
						 xhr=new XMLHttpRequest();//尝试创建 XMLHttpRequest 对象，除 IE 外的浏览器都支持这个方法。
					  }catch(e){	  
						xhr=ActiveXobject("Msxml12.XMLHTTP");//使用较新版本的 IE 创建 IE 兼容的对象（Msxml2.XMLHTTP）。
					  }
					  
					  if (xhr.upload) {
						  // 上传中
						  xhr.upload.addEventListener("progress", function(e) {
							  self.onProgress(file, e.loaded, e.total);
						  }, false);
			  
						  // 文件上传成功或是失败
						  xhr.onreadystatechange = function(e) {
							  if (xhr.readyState == 4) {
								  if (xhr.status == 200) {
									  //校正进度条和上传比例的误差
									  var thisfile = _this.find('#'+file.index);
									  thisfile.find('.uploadify-progress-bar').css('width','100%');
									  if(thisfile.find('.uploadedsize').length>0){
										  thisfile.find('.uploadedsize').text(thisfile.find('.totalsize').text());
										  }
									  if(thisfile.find('.up_percent').length>0){
										  thisfile.find('.up_percent').text('100%');
										  }
									  self.onUploadSuccess(file, xhr.responseText);
									  setTimeout(function(){ZXXFILE.onDelete(file.index);},option.removeTimeout);
									 
									  self.onUploadComplete(file,xhr.responseText,'');	
									  
								  } else {
									  self.onUploadError(file, xhr.responseText);		
								  }
							  }
						  };
			  
			  			  option.onUploadStart();	
						  // 开始上传
						  xhr.open("POST", self.url, true);
						  xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
						  var fd = new FormData();
						  fd.append('file',file);
						  
						  xhr.send(fd);
					  }	
				  })(file);	
				  
					  
			  },
			  
			  init: function() {
				  var self = this;
				  
				  //文件选择控件选择
				  if (this.fileInput) {
					  this.fileInput.addEventListener("change", function(e) { self.funGetFiles(e); }, false);	
				  }
				  
				  //点击上传按钮时触发file的click事件
				  _this.prev('.uploadfilebtn').on('click',function(){
					  _this.prevAll('.uploadfile').trigger('click');
					  });
				  
				  option.onInit();
			  }
		  };
		  //初始化文件对象
		  ZXXFILE.init();
		
		
		}); 
	};	
	
})(jQuery);