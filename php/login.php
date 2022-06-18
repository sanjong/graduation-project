<?php
    $con=mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");
    $userId = $_POST["id"];
    $userPwd = $_POST["pwd"];
    if(isset($userId)&&isset($userPwd)){
        if (mysqli_connect_errno($con)) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

        $selectSQL = "select * from users where id = '{$userId}'  AND pwd = '{$userPwd}';";
        $result = mysqli_query($con, $selectSQL);

        if ($result) {
                if ($row=mysqli_fetch_array($result)) {
                        $data = array();
                        array_push($data,array('id'=>$row[0],
                        'pwd'=>$row[1],
                        'name'=>$row[2],
                        'phone'=>$row[3],
                        'email'=>$row[4],
                        'gender'=>$row[5],
                        'image'=>$row[6],
                        'birth'=>$row[7]
                        ));
                        header('Content-Type: application/json; charset=utf8');
                        $json = json_encode(array("user"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
                        echo $json;
                }else{
                        echo "fail";
                }
        }else{
                echo mysqli_error($con);
        }
        mysqli_close($con);
    }
?>