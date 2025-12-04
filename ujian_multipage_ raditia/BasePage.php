<?php
// BasePage.php
// Class induk abstract yang mengimplementasikan Renderable

require_once 'Renderable.php';

abstract class BasePage implements Renderable {
    // Property dengan access modifier protected
    protected $judul;
    protected $navigasi;

    // Constructor
    public function __construct($judul, $navigasi = null) {
        $this->judul = $judul;
        $this->navigasi = $navigasi ?? [
            'Home' => 'index.php?page=home',
            'About' => 'index.php?page=about',
            'Produk' => 'index.php?page=produk',
        ];
    }

    // Method abstract yang harus diimplementasikan class anak
    abstract protected function generateHeaderHtml();
    abstract protected function generateBodyContent();

    // Implementasi method renderHtml() dari interface
    public function renderHtml() {
        $html = '<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>' . htmlspecialchars($this->judul) . ' - BAFU COMMUNITY</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: \'Inter\', sans-serif;
        }
        html {
            scroll-behavior: smooth;
        }
    </style>
</head>
<body class="bg-white">';

        $html .= $this->generateHeaderHtml();
        $html .= $this->generateBodyContent();

        // Footer hardcode sederhana
        $html .= '
<footer class="p-4 text-center bg-gray-200">
    <div class="container mx-auto">
        <p class="text-gray-700">&copy; ' . date('Y') . ' BAFU COMMUNITY. All rights reserved.</p>
        <p class="text-gray-600 text-sm mt-2">Komunitas Digital Terdepan</p>
    </div>
</footer>';

        $html .= '</body></html>';

        return $html;
    }
}
?>
