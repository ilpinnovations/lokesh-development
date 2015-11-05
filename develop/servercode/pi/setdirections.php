<?php 
	
    if(isset($_GET["right"]))
    {
		 	$myfile1=fopen("left.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$_GET["right"]);
			fclose($myfile1);
    }
    if(isset($_GET["left"]))
    {
    $myfile1=fopen("left.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$_GET["left"]);
			fclose($myfile1);
    }
    if(isset($_GET["forward"]))
    {
    $myfile1=fopen("forward.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$_GET["forward"]);
			fclose($myfile1);
    }
    if(isset($_GET["back"]))
    {
    $myfile1=fopen("forward.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$_GET["back"]);
			fclose($myfile1);
    }
?>