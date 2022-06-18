<?php
    $con=mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");
    mysqli_query($con,'SET NAMES utf8');
    $userId = $_POST['userId'];
    $followId = $_POST['followId'];
    $check = $_POST['check'];
    if(isset($userId) && isset($followId)&& $check =='1'){
            $SQL = "insert into follow(user_id, following_id) select '{$userId}','{$followId}'
                    from dual where not exists(
                        select * from follow
                        where user_id = '{$userId}' and following_id = '{$followId}');";
            $result = mysqli_query($con,$SQL);
            if(!$result){
            echo mysqli_error($con);
        }
    }
    elseif(isset($userId)&&isset($followId)&&$check=='0'){
        $SQL = "delete from follow where user_id = '{$userId}' and following_id = '{$followId}';";
        $result = mysqli_query($con,$SQL);
        if(!$result){
            echo mysqli_error($con);
        }
    }
    else{
        echo mysqli_error($con);
    }
    mysqli_close($con);
?>
