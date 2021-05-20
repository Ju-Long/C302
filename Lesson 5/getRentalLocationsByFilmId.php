<?php 
include './dbFunctions.php';
header('Content-Type: application/json')

$id = $_GET['id'] ?? 0;
$film = array();
$stores = [];

$query = "SELECT F.film_id, F.description, F.title, F.release_year, F.rating, A.address, A.postal_code, A.phone, C.city, CN.country FROM sakila.film F
INNER JOIN sakila.inventory I ON F.film_id = I.film_id
INNER JOIN sakila.store S ON I.store_id = S.store_id
INNER JOIN sakila.address A ON S.address_id = A.address_id
INNER JOIN sakila.city C ON A.city_id = C.city_id
INNER JOIN sakila.country CN ON C.country_id = CN.country_id
WHERE F.film_id=$id
GROUP BY A.address_id;";
$result = mysqli($link, $query);

foreach($films as $i) {
    $film['film_id'] = $i->film_id;
    $film['title'] = $i->title;
    $film['description'] = $i->description;
    $film['release_year'] = $i->release_year;
    $film['rating'] = $i->rating;

    array_push($stores, ['address' => "$i->address", 'postal_code' => "$i->postal_code", 'phone' => "$i->phone", 'city' => "$i->city", 'country' => "$i->country"]);
}
$film['stores'] = $stores;
echo json_encode($film);