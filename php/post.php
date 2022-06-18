           else{
                        echo "Failed to select db.posting";
                        exit();
                }
        }
        else{
                echo "Failed to insert into db.posting";
                exit();
        }

        $SQL = "insert into record_url(record,user_id,posting_id) values ('{$record}','{$userId}','{$postId}');";
        $result = mysqli_query($con,$SQL);
        if($result){
                $SQL = "insert ignore into htag(content) value "."('{$htag[0]}')";
                for($i=1;$i<count($htag);$i=$i+1){
                        $SQL = $SQL.",('{$htag[$i]}')";
                }
                $SQL = $SQL.";";
        }
        else{
                echo "Failed to insert db.record_url";
                exit();
        }
        $result = mysqli_query($con,$SQL);
        if($result){
                $SQL = "insert into posting_htag(posting_id,htag_id) select '{$postId}', id from htag where content in ('{$htag[0]}'";
                for($i=1;$i<count($htag);$i=$i+1){
                        $SQL = $SQL.",'{$htag[$i]}'";
                }
                $SQL = $SQL.");";
                $result = mysqli_query($con,$SQL);
                if(!$result){
                        echo "Failed to insert into db.post_htag";
                        exit();
                }
                else{
                        echo "Success insert db";
                }
        }
        else{
                echo "Failed to insert into db.htag : ";
                exit();
        }<?php
    $con = mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");
    if (mysqli_connect_errno($con)) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
    else{

        $userId = $_POST["id"];
        $title = $_POST["title"];
        $postRange = $_POST["postRange"];
        $htag = explode(',',$_POST["htag"]);
        $record = $_POST["url"];
        $createdAt = $_POST["createdAt"];

        $SQL = "insert into posting(user_id,title,post_range,created_at) values ('{$userId}','{$title}','{$postRange}','{$createdAt}');";
        $result = mysqli_query($con,$SQL);
        if($result){
                $SQL = "select id from posting where title = '{$title}' and user_id = '{$userId}';";
                $result = mysqli_query($con,$SQL);
                if($result){
                        $row = mysqli_fetch_array($result);
                        $postId = (int)$row[0];
                }
                else{
                        echo "Failed to select db.posting";
                        exit();
                }
        }
        else{
                echo "Failed to insert into db.posting";
                exit();
        }

        $SQL = "insert into record_url(record,user_id,posting_id) values ('{$record}','{$userId}','{$postId}');";
        $result = mysqli_query($con,$SQL);
        if($result){
                $SQL = "insert ignore into htag(content) value "."('{$htag[0]}')";
                for($i=1;$i<count($htag);$i=$i+1){
                        $SQL = $SQL.",('{$htag[$i]}')";
                }
                $SQL = $SQL.";";
        }
        else{
                echo "Failed to insert db.record_url";
                exit();
        }
        $result = mysqli_query($con,$SQL);
        if($result){
                $SQL = "insert into posting_htag(posting_id,htag_id) select '{$postId}', id from htag where content in ('{$htag[0]}'";
                for($i=1;$i<count($htag);$i=$i+1){
                        $SQL = $SQL.",'{$htag[$i]}'";
                }
                $SQL = $SQL.");";
                $result = mysqli_query($con,$SQL);
                if(!$result){
                        echo "Failed to insert into db.post_htag";
                        exit();
                }
                else{
                        echo "Success insert db";
                }
        }
        else{
                echo "Failed to insert into db.htag : ";
                exit();
        }
   }
    mysqli_close($con)
?>
