<?php
    $host="server16.mysql-host.eu";
    $user="pau10000_andwallet";
    $password="D6gF3yUAGSPYYkC";
    $db = "pau10000_andwallet";

    $con = mysqli_connect($host,$user,$password,$db);

    
    // Check connection
    
    if (mysqli_connect_errno()) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    } else {
        //echo "Connect";
    }
?>
