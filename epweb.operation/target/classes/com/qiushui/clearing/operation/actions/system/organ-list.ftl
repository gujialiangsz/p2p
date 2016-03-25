
<style>
.pageContent ul li a {
	overflow: hidden;
	text-overflow: ellipsis;
}
</style>

<div class="pageContent">
	<div
		style="float: left; display: block; overflow: auto; width: 22%; border-right: solid 1px #CCC;"
		layoutH="0"><@system.organTree organ /></div>
	<div id="organBox"
		style="float: left; display: block; padding: 3px; overflow: auto; width: 77%;"
		layoutH="0"><#include "system/organ-edit.ftl" parse="true" /></div>
</div>


<script>
	$(function() {
		setTimeout(function() {
			$("a[rel=organBox]:first").parent().addClass("selected");
			$(".pageContent ul li a").each(function(index, domEle) {
				try{
					$(domEle).attr("title", $(domEle).html());
				}catch(e){}
			});
		}, 500);

	});
</script>