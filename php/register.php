<?php
    $con = mysqli_connect("localhost:3306", "php", "1q2w3e4r", "test_dailyrecord");

    $userID = $_POST["id"];
    $userPassword = $_POST["pwd"];
    $userName = $_POST["name"];
    $userPhone = $_POST["phone"];
    $userEmail = $_POST["email"];
    $userBirth = $_POST["birth"];
    $userGender = $_POST["gender"];
    $userImage = $_POST["image"];


    $SQL = "insert into users values ('{$userID}','{$userPassword}','{$userName}','{$userPhone}','{$userEmail}','{$userGender}','{$userImage}','{$userBirth}');";
    $result = mysqli_query($con, $SQL);
    if($result){
        echo "success";
    }
    else{
        echo mysqli_error($con);
    }
    mysqli_close($con);
?>
