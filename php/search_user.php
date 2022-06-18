<?php
    $con=mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");

    if (mysqli_connect_errno($con)) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
    else{
        $user_id = $_POST['id'];
        $SQL = "select id, name, phone, email, gender, image, birth from users where id like '%{$user_id}%';";
        $result = mysqli_query($con, $SQL);

        $data = array();

         if ($result) {
                 while ($row=mysqli_fetch_array($result)) {
                        array_push($data,array(
                        'id'=>$row[0],
                        'name'=>$row[1],
                        'phone'=>$row[2],
                        'email'=>$row[3],
                        'gender'=>$row[4],
                        'image'=>$row[5],
                        'birth'=>$row[6]
                        ));}
                 header('Content-Type: application/json; charset=utf8');
                 $json = json_encode(array("user_data"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

                echo $json;

         }
         else {
                echo mysqli_error($con);
        }

    }
    mysqli_close($con);
?>
