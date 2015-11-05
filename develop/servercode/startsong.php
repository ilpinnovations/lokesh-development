<?php 
	
						
		$myfile=fopen("one.txt","r") or die("unable to open file!");
		    	
		$one=fread($myfile,filesize("one.txt"));
			fclose($myfile);

		$myfile1=fopen("two.txt","r") or die("unable to open file!");
		    	
		$two=fread($myfile1,filesize("two.txt"));
			fclose($myfile1);
	
			$myfile2=fopen("three.txt","r") or die("unable to open file!");
		    	
		$three=fread($myfile2,filesize("three.txt"));
			fclose($myfile2);
	
			$myfile3=fopen("four.txt","r") or die("unable to open file!");
		    	
		$four=fread($myfile3,filesize("four.txt"));
			fclose($myfile3);
	

	

	$wait=0;
	if(isset($_GET["on"]))
	{
		
	  if($_GET["on"]==1)
     {
		 
		 			 	  $myfile1=fopen("one.txt","w") or die("unable to open file!");
		    fwrite($myfile1,"1");
			fclose($myfile1);
			 $myfile2=fopen("two.txt","w") or die("unable to open file!");
		    fwrite($myfile2,"0");
			fclose($myfile2);
			 $myfile3=fopen("three.txt","w") or die("unable to open file!");
		    fwrite($myfile3,"0");
			fclose($myfile3);
			 $myfile4=fopen("four.txt","w") or die("unable to open file!");
		    fwrite($myfile4,"0");
			fclose($myfile4);
     }	
	  if($_GET["on"]==2)
	     {
		 	  $myfile1=fopen("one.txt","w") or die("unable to open file!");
		    fwrite($myfile1,"0");
			fclose($myfile1);
			 $myfile2=fopen("two.txt","w") or die("unable to open file!");
		    fwrite($myfile2,"1");
			fclose($myfile2);
			 $myfile3=fopen("three.txt","w") or die("unable to open file!");
		    fwrite($myfile3,"0");
			fclose($myfile3);
			 $myfile4=fopen("four.txt","w") or die("unable to open file!");
		    fwrite($myfile4,"0");
			fclose($myfile4);
     }	
	  if($_GET["on"]==3)
	     {
		 	  $myfile1=fopen("one.txt","w") or die("unable to open file!");
		    fwrite($myfile1,"0");
			fclose($myfile1);
			 $myfile2=fopen("two.txt","w") or die("unable to open file!");
		    fwrite($myfile2,"0");
			fclose($myfile2);
			 $myfile3=fopen("three.txt","w") or die("unable to open file!");
		    fwrite($myfile3,"1");
			fclose($myfile3);
			 $myfile4=fopen("four.txt","w") or die("unable to open file!");
		    fwrite($myfile4,"0");
			fclose($myfile4);
     }	
	
	  if($_GET["on"]==4)
	     {
		 	  $myfile1=fopen("one.txt","w") or die("unable to open file!");
		    fwrite($myfile1,"0");
			fclose($myfile1);
			 $myfile2=fopen("two.txt","w") or die("unable to open file!");
		    fwrite($myfile2,"0");
			fclose($myfile2);
			 $myfile3=fopen("three.txt","w") or die("unable to open file!");
		    fwrite($myfile3,"0");
			fclose($myfile3);
			 $myfile4=fopen("four.txt","w") or die("unable to open file!");
		    fwrite($myfile4,"1");
			fclose($myfile4);
     }	
	 
	 	  if($_GET["on"]=="all")
	     {
		 	  $myfile1=fopen("one.txt","w") or die("unable to open file!");
		    fwrite($myfile1,"all");
			fclose($myfile1);
			 $myfile2=fopen("two.txt","w") or die("unable to open file!");
		    fwrite($myfile2,"all");
			fclose($myfile2);
			 $myfile3=fopen("three.txt","w") or die("unable to open file!");
		    fwrite($myfile3,"all");
			fclose($myfile3);
			 $myfile4=fopen("four.txt","w") or die("unable to open file!");
		    fwrite($myfile4,"all");
			fclose($myfile4);
     }	
	}
	if(isset($_GET["wait"]))
	{
		if($_GET["wait"]==1)
		{
            $myfile=fopen("wait.txt","w") or die("unable to open file!");
		    fwrite($myfile,"1");
			fclose($myfile);
		}
		else
		{
		    $myfile=fopen("wait.txt","w") or die("unable to open file!");
		    fwrite($myfile,"0");
			fclose($myfile);
			
			{
				  $myfile1=fopen("one.txt","w") or die("unable to open file!");
		    fwrite($myfile1,"0");
			fclose($myfile1);
			 $myfile2=fopen("two.txt","w") or die("unable to open file!");
		    fwrite($myfile2,"0");
			fclose($myfile2);
			 $myfile3=fopen("three.txt","w") or die("unable to open file!");
		    fwrite($myfile3,"0");
			fclose($myfile3);
			 $myfile4=fopen("four.txt","w") or die("unable to open file!");
		    fwrite($myfile4,"0");
			fclose($myfile4);
			}
		}
		
	}
	if(isset($_GET["playmusic"]))
	{

		$myfile=fopen("wait.txt","r") or die("unable to open file!");
		    $temp=fread($myfile,filesize("wait.txt"));
			
		if($temp==1)
		{
$tempo=0;
		if($one==1)
		{
					$myfile1=fopen("one.txt","r") or die("unable to open file!");
		    $temp2= fread($myfile1,filesize("one.txt"));
			if($temp2==1)
			{
				echo "one";
			$tempo=1;
			}
			
			fclose($myfile1);
			
		}
		if($two==1)
        {
								$myfile1=fopen("two.txt","r") or die("unable to open file!");
		    $temp2= fread($myfile1,filesize("two.txt"));
			if($temp2==1)
			{
			echo "two";

			$tempo=1;
		}
			fclose($myfile1);
	    }
		if($three==1)
		{
								$myfile1=fopen("three.txt","r") or die("unable to open file!");
		    $temp2= fread($myfile1,filesize("three.txt"));
			if($temp2==1)
			{
			echo "three";
					
			$tempo=1;
		}
			fclose($myfile1);
		}

		if($four==1)
        {
								$myfile1=fopen("four.txt","r") or die("unable to open file!");
		    $temp2= fread($myfile1,filesize("four.txt"));
			if($temp2==1)
			{
				echo "four";
			
			$tempo=1;
			}
			fclose($myfile1);
		}
		
		if($one=="all")
        {
				echo "all";
			$tempo=1;
		}
		
		
		
		if($tempo==0)
		echo "wait";
		}
		else
		{
           echo "stop";
		}
		
		fclose($myfile);
			
	}
	 ?>