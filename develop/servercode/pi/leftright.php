<?php 

    $myfile=fopen("left.txt","r") or die("unable to open file!");
    
    $one=fread($myfile,filesize("left.txt"));
    fclose($myfile);
    echo $one;
?>