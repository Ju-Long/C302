<?php 
$database_ip = "localhost";
$database = "c302_magic";
$username = "root";
$password = "";

$connection = mysqli_connect($database_ip, $username, $password, $database);
session_start();
if (!$connection) {
    die("connection failed: " . mysqli_connect_error());
}