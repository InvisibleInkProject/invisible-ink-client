$(document).ready(function() {

	var listView = new ListView('#inkList');
	listView.update(sampleDate);
	
	var mapOptions = {
          center: new google.maps.LatLng(59.94348, 10.7176),
          zoom: 14
    };
	var mapView = new MapView("map-canvas", mapOptions);
	mapView.update(sampleDate);
	mapView.updateLocation(59.94348, 10.7176);

});