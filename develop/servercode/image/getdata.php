<?php 

	
						
		$myfile=fopen("imagechanged.txt","r") or die("unable to open file!");
		    	
		$one=fread($myfile,filesize("imagechanged.txt"));
			fclose($myfile);
			
			if($one!="0")
			{
				  $myfile1=fopen("imagechanged.txt","w") or die("unable to open file!");
		    fwrite($myfile1,"0");
			fclose($myfile1);
			echo $one;
			}
			else{
				echo "0";
			}
?>