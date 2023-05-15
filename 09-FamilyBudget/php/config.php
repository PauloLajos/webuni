<?php
    $host="server16.mysql-host.eu";
    $user="pau10000_familybudget";
    $password="aZW~q%^4P#ky";
    $db = "pau10000_familybudget";

    $con = mysqli_connect($host,$user,$password,$db);

    
    // Check connection
    
    if (mysqli_connect_errno()) {
    
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    } else {  //echo "Connect"; 
        echo "Sucess to connect to MySQL: " . mysqli_connect_error();

    }
?>
