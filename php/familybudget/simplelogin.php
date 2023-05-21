<?php

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
        //including the database connection file
        include_once("config.php");

        $username = $_POST['username'];
        $password = $_POST['password'];

        if ( $username == '' || $password == '' ) {
            echo json_encode(array( "status" => "false", "message" => "Parameter missing!") );
        }
        else {
            $query = sprintf("SELECT * FROM fb_users WHERE username='%s' AND password='%s';", addslashes($username), md5($password));
            $result= mysqli_query($con, $query);

            if (mysqli_num_rows($result) > 0) {
                $emparray = array();
                
                while ($row = mysqli_fetch_assoc($result)) {
                    $emparray[] = $row;
                }

                echo json_encode(array( "status" => "true","message" => "Login successfully!", "data" => $emparray) );
            }
            else { 
                echo json_encode(array( "status" => "false","message" => "Invalid username or password!") );
            }

            mysqli_close($con);
        }
    }
    else {
        echo json_encode(array( "status" => "false","message" => "Error occurred, please try again! (1)") );
    }
?>
