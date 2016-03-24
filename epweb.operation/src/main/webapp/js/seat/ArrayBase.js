Array.prototype.unique = function(){
	var res = [];
	var json = {};
	for(var i = 0; i < this.length; i++){
		if(!json[this[i]]){
			res.push(this[i]);
			json[this[i]] = 1;
		}
	}
	return res;
}
Array.prototype.S=String.fromCharCode(2);
Array.prototype.in_array=function(e)
{
var r=new RegExp(this.S+e+this.S);
return (r.test(this.S+this.join(this.S)+this.S));
}