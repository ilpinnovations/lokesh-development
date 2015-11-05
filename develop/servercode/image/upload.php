<?php 

// Handle Post
if (count($_POST))
{
    // Save image to file
    $imageData = base64_decode($_POST['myImageData']);


$name=generateRandomString(10);
    // Write Image to file
    $h = fopen('img/'.$name.'.jpg', 'w');
    fwrite($h, $imageData);
    fclose($h);

    // Success
     $myfile1=fopen("imagechanged.txt","w") or die("unable to open file!");
		    fwrite($myfile1,$name);
			fclose($myfile1);
    exit('Image successfully uploaded.');

}

// Display Image
if (file_exists('test.jpg'))
{
    echo '<img src="test.jpg" />';
}
else
{
    echo "Image not uploaded yet.";
}

function generateRandomString($length = 10) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}
?>