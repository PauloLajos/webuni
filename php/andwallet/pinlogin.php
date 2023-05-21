<?php

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
        //including the database connection file
        include_once("config.php");

        $pin = $_POST['pin'];

        if ( $pin == '' ) {
            echo json_encode(array( "status" => "false", "message" => "Parameter missing!") );
        }
        else {
            $query = sprintf("SELECT * FROM aw_pin WHERE pin='%s';", md5($pin));
            $result= mysqli_query($con, $query);

            if (mysqli_num_rows($result) > 0) {
                $emparray = array();
                
                while ($row = mysqli_fetch_assoc($result)) {
                    $emparray[] = $row;
                }

                echo json_encode(array( "status" => "true","message" => "Login successfully!", "data" => $emparray) );
            }
            else { 
                echo json_encode(array( "status" => "false","message" => "Invalid pin!") );
            }

            mysqli_close($con);
        }
    }
    else {
        echo json_encode(array( "status" => "false","message" => "Error occurred, please try again! (1)") );
    }
?>
