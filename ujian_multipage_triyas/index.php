<?php
// File: index.php
// Entry Point Aplikasi

// 1. Require semua file Class yang dibutuhkan
require_once 'Renderable.php';
require_once 'BasePage.php';
require_once 'HomePage.php';
require_once 'AboutPage.php';
require_once 'ProdukPage.php';

// 2. Tentukan halaman aktif dari query string, default ke 'home'
$halamanQuery = $_GET['page'] ?? 'home';

// 3. Definisikan array asosiatif untuk memetakan route ke Class
$routes = [
    'home' => 'HomePage',
    'about' => 'AboutPage',
    'produk' => 'ProdukPage'
];

// 4. Polimorfisme Inti: Tentukan Class yang akan di-instantiate
$className = $routes[$halamanQuery] ?? 'HomePage'; // Fallback ke HomePage jika tidak ditemukan

// Data Navigasi Global
$navigasiGlobal = [
    'Home' => 'index.php?page=home',
    'About' => 'index.php?page=about',
    'Produk' => 'index.php?page=produk'
];

// Set Judul sesuai halaman yang dipilih
$judulHalaman = ucfirst($halamanQuery);

// Membuat instance dari Class yang sesuai
// Class yang dihasilkan (HomePage, AboutPage, atau ProdukPage)
// semuanya dianggap sebagai Renderable karena Polimorfisme.
$halamanAktif = new $className($judulHalaman, $navigasiGlobal, $halamanQuery);

// 5. Cetak halaman yang diminta menggunakan satu Method saja: renderHtml()
// Ini adalah inti dari Polimorfisme. Kita hanya perlu memanggil renderHtml()
// tanpa peduli Class aslinya.
echo $halamanAktif->renderHtml();
