<?php
// index.php
// File utama untuk routing dan menampilkan halaman

// Require semua file class
require_once 'Renderable.php';
require_once 'BasePage.php';
require_once 'HomePage.php';
require_once 'AboutPage.php';
require_once 'ProdukPage.php';

// Tentukan halaman aktif dari query string
$page = $_GET['page'] ?? 'home';

// Array untuk memetakan route ke Class
$routes = [
    'home' => 'HomePage',
    'about' => 'AboutPage',
    'produk' => 'ProdukPage',
    ''
];

// Tentukan class yang akan digunakan berdasarkan route
$className = $routes[$page] ?? 'HomePage';

// Polimorfisme: Buat instance dari class yang sesuai
$halamanAktif = new $className();

// Cetak halaman yang diminta
echo $halamanAktif->renderHtml();
?>
