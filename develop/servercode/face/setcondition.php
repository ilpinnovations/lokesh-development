<?php 
	
						

		 			 	  $myfile1=fopen("condition.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$_GET["condition"]);
			fclose($myfile1);
    
    sleep(3);
    $myfile1=fopen("condition.txt","w") or die("unable to open file!");
    fwrite($myfile1,"3");
    fclose($myfile1);
    
?>