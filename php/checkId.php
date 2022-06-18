<?php
    $con=mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");
    $userId = $_POST["id"];
    if(mysqli_connect_errno($con)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }else{
        $SQL = "select * from users where id = '{$userId}'";
        $result = mysqli_query($con, $SQL);

        if ($result) {
                if ($row=mysqli_fetch_array($result)) {
                        echo "Exist";
                }else{
                        echo "Not Exist";
                }

        }
        mysqli_close($con);
    }
?>
