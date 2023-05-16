<?php

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        // echo $_SERVER["DOCUMENT_ROOT"];  // /home1/demonuts/public_html
        //including the database connection file
        include_once("config.php");
        
        $name = $_POST['name'];
        $username = $_POST['username'];
        $password = $_POST['password'];
        //$hobby= $_POST['hobby'];
  
        //if($name == '' || $username == '' || $password == '' || $hobby == '') {
        if ($name == '' || $username == '' || $password == '') {
        
            echo json_encode(array( "status" => "false", "message" => "Parameter missing!") );
        }
        else {

            //$query = "SELECT * FROM fb_users WHERE username='$username'";
            $query = sprintf("SELECT 1 FROM fb_users WHERE username='%s';", addslashes($username));
            $result = mysqli_query($con, $query);

            if (mysqli_num_rows($result) > 0) {
                echo json_encode(array( "status" => "false", "message" => "Username already exist!") );
            }
            else { 
                //$query = "INSERT INTO registerDemo (name, hobby, username, password) VALUES ('$name', '$hobby', '$username', '$password')";
                //$query = "INSERT INTO fb_users (name, username, password) VALUES ('$name', '$username', '$password')";
                $query  = sprintf("INSERT INTO fb_users (name, username, password) VALUES('%s','%s','%s');", addslashes($name), addslashes($username), md5($password));
                
                if (mysqli_query($con,$query)) {

                    //$query = "SELECT * FROM fb_users WHERE username='$username'";
                    $query = sprintf("SELECT 1 FROM fb_users WHERE username='%s';", addslashes($username));
                    $result = mysqli_query($con, $query);
                    $emparray = array();
                    
                    if (mysqli_num_rows($result) > 0) {  
                    
                        while ($row = mysqli_fetch_assoc($result)) {
                            $emparray[] = $row;
                        }
                    }
                    
                    echo json_encode(array( "status" => "true", "message" => "Successfully registered!" , "data" => $emparray) );
                }
                else {
                    echo json_encode(array( "status" => "false", "message" => "Error occurred, please try again! (1)") );
                }
            }
            
            mysqli_close($con);
        }
    }
    else {
        echo json_encode(array( "status" => "false", "message" => "Error occurred, please try again! (2)") );
    }
?>
