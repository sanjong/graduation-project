
<?php
    $con=mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");
    mysqli_query($con,'SET NAMES utf8');
    $userId = $_POST['id'];
    $postId = $_POST['postId'];
    $check = $_POST['check'];
    if(isset($userId) && isset($postId)&& $check =='1'){
        $SQL = "insert into likes(post_id, user_id) select ?,? from dual
                         where not exists(
                                 select * from likes where post_id = ? and user_id = ?) ;";
        $statement = mysqli_prepare($con, $SQL);
        mysqli_stmt_bind_param($statement, "isis",$postId, $userId,$postId,$userId);
        $result = mysqli_stmt_execute($statement);
        if($result){
            echo "true";
        }
         else{
                echo mysqli_error($con);
        }
    }
    elseif(isset($userId)&&isset($postId)&&$check=='0'){
        $SQL = "delete from likes where post_id =? and user_id = ?;";
        $statement = mysqli_prepare($con, $SQL);
        mysqli_stmt_bind_param($statement, "is",$postId, $userId);
        $result = mysqli_stmt_execute($statement);
        if($result){
            echo "true";
        }
         else{
                echo mysqli_error($con);
        }
    }

    mysqli_close($con);
?>
