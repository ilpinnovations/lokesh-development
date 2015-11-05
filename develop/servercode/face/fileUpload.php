<?php
    
    $image = new Imagick('loki.jpg');

//$image->setResolution(300, 300);
//$image->readImageBlob(...);
// convert the output to JPEG
$image->setImageFormat('jpeg');
$image->setImageCompressionQuality(90);
?>