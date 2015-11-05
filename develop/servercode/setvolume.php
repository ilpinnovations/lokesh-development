<?php 
	
						

		 			 	  $myfile1=fopen("volume.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$_GET["volume"]);
			fclose($myfile1);
?>