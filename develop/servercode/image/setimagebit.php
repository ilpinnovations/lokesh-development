<?php 
	
						

		 			 	  $myfile1=fopen("imagechanged.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$_GET["imagechanged"]);
			fclose($myfile1);
?>