<?php 

	
						
		$myfile=fopen("volume.txt","r") or die("unable to open file!");
		    	
		$one=fread($myfile,filesize("volume.txt"));
			fclose($myfile);
			echo $one;
?>