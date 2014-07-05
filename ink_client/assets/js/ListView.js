	var ListView;
	
	function ListView(id) {
		this.element = $(id);
		this.inkList;
	}
	
	ListView.prototype.update = function(inkList) {
		var liClass, info, _this = this;
		this.inkList = inkList;
		$.each(inkList, function(i, item) {
			info = "";
			if (item.viewed === "True") {
				liClass = "drop_viewed";
			} else if (item.expires === "True") {
				liClass = "drop_expires";
				info = ' <span class="blueHighlighted">Expires soon</span>';
			} else {
				liClass = "drop";
			}
			_this.element.append('<li class="' + liClass + '"><span class="text" id="' + i + '">' + item.text.substring(0, 82) + '</span><ul class="info"><li>' + item.date + info + '</li></ul><img class="enlarge" id="' + i + '" src="images/btn_arrow_preview.png"></li>');		
		});	
		this.observe(); // TODO: move to constructor
	};
	
	ListView.prototype.observe = function() {
		var _this = this;

		$(".enlarge").click(function() {
			var id = $(this).attr('id');
	//		var text = $("span #" + id).html();
			var text = $('#' + id +  '.text').html(_this.inkList[id].text);
		});	
	};