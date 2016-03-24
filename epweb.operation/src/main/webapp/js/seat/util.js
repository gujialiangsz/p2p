function getOddNum(num){
	var sunNum = 0;
	for(var i=1;i>-1;i++){
		if(i%2!=0){
			sunNum++;			
		}
		if(sunNum == num){
			return i;
		}
	}
}
function getEvenNum(num){
	var sunNum = 0;
	for(var i=1;i>-1;i++){
		if(i%2==0){
			sunNum++;			
		}
		if(sunNum == num){
			return i;
		}
	}
}  