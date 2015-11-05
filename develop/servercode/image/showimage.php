<html>
    <head>
        <script>
            
            function getcity() {

if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp16=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp16=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp16.onreadystatechange=function()
  {
  if (xmlhttp16.readyState==4 && xmlhttp16.status==200)
    {

if(xmlhttp16.responseText!="0")
{
document.getElementById('image').innerHTML="<img src='img/"+xmlhttp16.responseText+".jpg' />";
}


    }
  }
xmlhttp16.open("POST","getdata.php",true);
 xmlhttp16.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
xmlhttp16.send();

setTimeout(getcity, 100);
}
//setTimeout(function() { getcity(); }, 1000);
setTimeout(getcity, 0); 
            </script>
        </head>
        <body>
            <div id="image">
                
            </div>
            
        </body>
        
    </html>