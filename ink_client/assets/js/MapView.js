	var MapView;
	
	function MapView(id, mapOptions) {
        this.map = new google.maps.Map(document.getElementById(id),
            mapOptions);	
	}
	
	MapView.prototype.inkIcon = "images/ic_drop_map.png";
	MapView.prototype.userLocationIcon = "images/ic_quill_map.png";
	
	MapView.prototype.update = function(inkList) {
		var _this = this, marker;
		
		$.each(inkList, function(i, item) {	
			var position = new google.maps.LatLng(item.lat, item.lon);
			var marker = new google.maps.Marker({
				position: position,
				map: _this.map,
				title:"Hello World!",
				icon: _this.inkIcon,
			});
			
			var circle = new google.maps.Circle({
				strokeColor: '#00ffdf',
				strokeOpacity: 1,
				strokeWeight: 2,
				fillColor: '#00ffdf',
				fillOpacity: 0.2,
				map: _this.map,
				center: new google.maps.LatLng(item.lat, item.lon),
				radius: item.radius
			});
		});	
	};
	
	MapView.prototype.updateLocation = function(lat, lon) {
			var marker = new google.maps.Marker({
				position: new google.maps.LatLng(lat, lon),
				map: this.map,
				title: "You are here",
				icon: this.userLocationIcon,
			});		
	};
	