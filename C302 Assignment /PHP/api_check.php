<?php
include './DBConnection.php';
header('Content-Type: application/json');

$apikey = $_POST['apikey'] ?? '';
$user_id = $_POST['user_id'] ?? 0;

$return = array();

if (!($apikey && $user_id)) {
    $return = ['output' => 'Invalid Parameters for API Credentials'];
    echo json_encode($return);
    exit();
}

$query = "select * from user where id = $user_id and apikey = '$apikey'";
$result = mysqli_query($connection, $query) or die(mysqli_error($connection));
if (mysqli_num_rows($result) < 1) {
    $return = ['output' => 'Invalid API Credentials', 'query' => $query];
    echo json_encode($return); 
    exit();
}