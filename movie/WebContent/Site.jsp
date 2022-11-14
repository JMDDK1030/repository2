<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>센텀 시네마 위치</title>

<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=8stvn63uax"></script>
</head>
<body>
<form id="checkForm">
<div id="map" style="width:100%; height:450px;"></div>
</form>
  <script>
        var map = new naver.maps.Map('map', {
            center: new naver.maps.LatLng(35.1729615, 129.1299186), 
            zoom: 15
        });

        var marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(35.1729615, 129.1299186),
            map: map
        });
    </script>
    <div>부산광역시 해운대구 센텀중앙로 48 에이스하이테크21</div>
</body>
</html>