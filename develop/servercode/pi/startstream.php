<?php 
    include("startstream.php");
	
    $stream = new VideoStream("http://192.168.1.110:8081/");
    $stream->start();
?>