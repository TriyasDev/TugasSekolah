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
    'produk' => 'ProdukPage'
];

// Tentukan class yang akan digunakan berdasarkan route
$className = $routes[$page] ?? 'HomePage';

// Polimorfisme: Buat instance dari class yang sesuai
$halamanAktif = new $className();

// Cetak halaman yang diminta
echo $halamanAktif->renderHtml();

// ====================================================
// PENALARAN KRITIS (dalam bentuk komentar PHP)
// ====================================================

// 1. Analisis Polimorfisme (20 Poin)
//
// PHP dapat secara otomatis mengetahui apakah akan mencetak konten HomePage, AboutPage,
// atau ProdukPage meskipun method yang dipanggil sama ($halamanAktif->renderHtml())
// karena konsep polimorfisme dalam OOP. Ketika kita memanggil renderHtml() pada
// objek $halamanAktif, PHP akan menjalankan implementasi method renderHtml() dari
// class BasePage (induk). Namun, di dalam renderHtml() BasePage, terdapat pemanggilan
// method generateHeaderHtml() dan generateBodyContent() yang merupakan method abstract.
//
// Karena setiap class turunan (HomePage, AboutPage, ProdukPage) mengimplementasikan
// method-method abstract tersebut dengan cara yang berbeda, maka konten yang dihasilkan
// akan berbeda meskipun method yang dipanggil sama. Ini adalah contoh runtime polymorphism
// dimana PHP menentukan method mana yang akan dieksekusi berdasarkan tipe aktual objek
// pada saat runtime, bukan tipe deklarasi variabel.
//
// Prosesnya:
// 1. $halamanAktif adalah instance dari class turunan (misal: HomePage)
// 2. Saat memanggil $halamanAktif->renderHtml(), PHP mencari method di HomePage
// 3. Karena HomePage tidak memiliki renderHtml(), PHP mencari di parent class (BasePage)
// 4. BasePage::renderHtml() memanggil $this->generateHeaderHtml() dan $this->generateBodyContent()
// 5. $this merujuk ke objek HomePage, sehingga method HomePage::generateBodyContent() yang dieksekusi
// 6. Hasilnya konten spesifik HomePage yang ditampilkan

// 2. Analisis Kapsulasi (10 Poin)
//
// Jika ingin mencegah Class anak mengubah array $navigasi yang ada di BasePage.php,
// access modifier yang harus digunakan adalah PRIVATE, bukan PROTECTED.
//
// Alasannya:
// 1. Access modifier PRIVATE membatasi akses property hanya pada class itu sendiri.
//    Class anak tidak dapat mengakses atau mengubah property private dari parent class.
//
// 2. Access modifier PROTECTED memungkinkan class anak untuk mengakses dan mengubah
//    property dari parent class, yang tidak diinginkan dalam kasus ini.
//
// 3. Dengan menjadikan $navigasi sebagai private, kita menerapkan enkapsulasi yang ketat.
//    Jika class anak perlu mengakses $navigasi, kita dapat menyediakan getter method
//    (public getNavigasi()) tanpa memberikan kemampuan untuk mengubah nilainya.
//
// Contoh implementasi:
// class BasePage {
//     private $navigasi; // Tidak bisa diakses anak class
//
//     protected function getNavigasi() {
//         return $this->navigasi; // Hanya bisa membaca, tidak mengubah
//     }
// }
//
// Dengan desain ini, kita memastikan bahwa array $navigasi tetap konsisten dan
// tidak dapat dimodifikasi oleh class anak, yang merupakan prinsip enkapsulasi
// untuk menjaga integritas data.

// ====================================================
// END OF FILE
?>
