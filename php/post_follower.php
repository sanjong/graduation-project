<?php
    $con=mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");

    if (mysqli_connect_errno($con)) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }
    else{
        $userId = $_POST['userId'];
        $SQL = "select p.id, p.title, p.created_at, ifnull(l.user_id,0) as check_like, ru.record, ifnull(htag,'') as htag, u.image,p.user_id,p.post_range,'true'
        from record_url ru, posting p
        left outer join likes l
        on p.id = l.post_id and l.user_id = '{$userId}'
        left outer join (
                select A.posting_id, group_concat(A.content) as htag
                from (select ph.posting_id, content
                from posting_htag ph, htag h
                where ph.htag_id = h.id) A
                group by A.posting_id) B
        on B.posting_id = id
        join users u
        on u.id = p.user_id
        where p.id = ru.posting_id and p.post_range >= 1 and p.user_id in (select following_id from follow where user_id = '{$userId}');";

        $result = mysqli_query($con, $SQL);

        $data = array();
$data = array();

        if ($result) {
                while ($row=mysqli_fetch_array($result)) {
                        array_push($data,array(
                        'postid'=>$row[0],
                        'title'=>$row[1],
                        'created_at'=>$row[2],
                        'like'=>$row[3],
                        'record'=>$row[4],
                        'htag'=>$row[5],
                        'image'=>$row[6],
                        'user_id'=>$row[7],
                        'post_range'=>$row[8],
                        'follow'=>$row[9]));
                 }
                 header('Content-Type: application/json; charset=utf8');
                 $json = json_encode(array("follower_feed"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

                echo $json;

        } else {
                echo mysqli_error($con);
         }

    }
    mysqli_close($con);


?>
