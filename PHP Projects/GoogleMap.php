
<!DOCTYPE html>
<html>
  <head>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>

      

  </head>
  <body>

<button type="button" name="Australia" value="Australia" onclick="Australia()">Australia</button>
<button type="button" name="Israel" value="Israel" onclick="Israel()">Israel</button>
<button type="button" name="United States" value="United States" onclick="UnitedStates()">United States</button>
<button type="button" name="France" value="France" onclick="France()">France</button>
<button type="button" name="Japan" value="Japan" onclick="Japan()">Japan</button>

    <div id="map"></div>
    <script>
      var map;
      function initMap() {
        map = new google.maps.Map(document.getElementById('map'), { center: {lat: -34.397, lng: 150.644},zoom: 8});
      }

function Australia(){
    var center = new google.maps.LatLng(-34.397,150.644);
            map.panTo(center);}
  

function Israel(){
    var center = new google.maps.LatLng(31.771959,35.217018);
            map.panTo(center);}

function UnitedStates(){
    var center = new google.maps.LatLng(37.2756537,-104.6561154);
            map.panTo(center);}

function France(){
    var center = new google.maps.LatLng(46.6417446,2.1081635);
            map.panTo(center);}

function Japan(){
    var center = new google.maps.LatLng(36.1453935,138.1883127);
            map.panTo(center);}



    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDXvdByHYIUrNGQ9KyHjLAmc9lDePopXuU&callback=initMap"
    async defer></script>

  </body>
</html>