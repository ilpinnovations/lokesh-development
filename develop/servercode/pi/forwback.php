<?php 
    
        $myfile=fopen("forward.txt","r") or die("unable to open file!");
        
        $one=fread($myfile,filesize("forward.txt"));
        fclose($myfile);
        echo $one;
    
?>