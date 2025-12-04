<?php
// ProdukPage.php
// Class turunan untuk Halaman Produk

require_once 'BasePage.php';

class ProdukPage extends BasePage {
    public function __construct() {
        parent::__construct("Produk");
    }

    protected function generateHeaderHtml() {
        $html = '<header class="sticky top-0 z-50 bg-white shadow-md">
    <div class="container mx-auto px-6 py-4">
        <div class="flex justify-between items-center">
            <!-- Logo -->
            <div class="flex items-center">
                <h1 class="text-2xl font-bold text-gray-800">PT. INOVASI DIGITAL</h1>
            </div>

            <!-- Desktop Menu -->
            <nav class="hidden md:flex items-center space-x-8">
                <ul class="flex space-x-8">';

        foreach ($this->navigasi as $menu => $link) {
            $html .= '<li><a href="' . $link . '" class="text-gray-700 hover:text-blue-600 font-medium transition-colors duration-200">' . $menu . '</a></li>';
        }

        $html .= '</ul>
            </nav>

            <!-- Mobile Menu Button -->
            <div class="md:hidden">
                <button id="mobile-menu-button" class="text-gray-700 hover:text-blue-600 focus:outline-none">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
                    </svg>
                </button>
            </div>
        </div>

        <!-- Mobile Menu -->
        <div id="mobile-menu" class="md:hidden mt-4 hidden">
            <ul class="space-y-4">';

        foreach ($this->navigasi as $menu => $link) {
            $html .= '<li><a href="' . $link . '" class="block text-gray-700 hover:text-blue-600 font-medium py-2">' . $menu . '</a></li>';
        }

        $html .= '</ul>
        </div>
    </div>
</header>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const mobileMenuButton = document.getElementById("mobile-menu-button");
        const mobileMenu = document.getElementById("mobile-menu");

        if (mobileMenuButton && mobileMenu) {
            mobileMenuButton.addEventListener("click", function() {
                mobileMenu.classList.toggle("hidden");
            });

            document.addEventListener("click", function(event) {
                if (!mobileMenuButton.contains(event.target) && !mobileMenu.contains(event.target)) {
                    mobileMenu.classList.add("hidden");
                }
            });
        }
    });
</script>';

        return $html;
    }

    protected function generateBodyContent() {
        return '
<section class="py-16 bg-gray-50">
    <div class="container mx-auto px-6">
        <div class="text-center mb-12">
            <h1 class="text-4xl font-bold text-gray-800 mb-4">Produk & Layanan Kami</h1>
            <div class="w-24 h-1 bg-blue-600 mx-auto mb-6"></div>
            <p class="text-gray-600 max-w-3xl mx-auto">
                Solusi pengembangan web berbasis OOP yang dirancang untuk berbagai kebutuhan bisnis digital.
            </p>
        </div>

        <!-- Grid Produk -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <!-- Produk 1 -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:-translate-y-2 transition-transform duration-300">
                <div class="h-48 bg-gradient-to-r from-blue-500 to-blue-700 flex items-center justify-center">
                    <svg class="w-20 h-20 text-white" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M12.316 3.051a1 1 0 01.633 1.265l-4 12a1 1 0 11-1.898-.632l4-12a1 1 0 011.265-.633zM5.707 6.293a1 1 0 010 1.414L3.414 10l2.293 2.293a1 1 0 11-1.414 1.414l-3-3a1 1 0 010-1.414l3-3a1 1 0 011.414 0zm8.586 0a1 1 0 011.414 0l3 3a1 1 0 010 1.414l-3 3a1 1 0 11-1.414-1.414L16.586 10l-2.293-2.293a1 1 0 010-1.414z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <div class="p-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-2">Web Framework Modular</h3>
                    <p class="text-gray-600 mb-4">Framework PHP berbasis OOP untuk pengembangan web yang scalable dan mudah dipelihara.</p>
                    <div class="flex justify-between items-center">
                        <span class="text-2xl font-bold text-blue-600">Rp 15.000.000</span>
                        <button class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                            Detail
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produk 2 -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:-translate-y-2 transition-transform duration-300">
                <div class="h-48 bg-gradient-to-r from-green-500 to-green-700 flex items-center justify-center">
                    <svg class="w-20 h-20 text-white" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M10 12a2 2 0 100-4 2 2 0 000 4z"/>
                        <path fill-rule="evenodd" d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10zM14 10a4 4 0 11-8 0 4 4 0 018 0z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <div class="p-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-2">Sistem Manajemen Konten</h3>
                    <p class="text-gray-600 mb-4">CMS yang dibangun dengan arsitektur modular untuk pengelolaan konten yang fleksibel.</p>
                    <div class="flex justify-between items-center">
                        <span class="text-2xl font-bold text-green-600">Rp 25.000.000</span>
                        <button class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors">
                            Detail
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produk 3 -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:-translate-y-2 transition-transform duration-300">
                <div class="h-48 bg-gradient-to-r from-purple-500 to-purple-700 flex items-center justify-center">
                    <svg class="w-20 h-20 text-white" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M11.3 1.046A1 1 0 0112 2v5h4a1 1 0 01.82 1.573l-7 10A1 1 0 018 18v-5H4a1 1 0 01-.82-1.573l7-10a1 1 0 011.12-.38z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <div class="p-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-2">E-commerce Platform</h3>
                    <p class="text-gray-600 mb-4">Platform e-commerce dengan modul terpisah untuk produk, keranjang, checkout, dan pembayaran.</p>
                    <div class="flex justify-between items-center">
                        <span class="text-2xl font-bold text-purple-600">Rp 35.000.000</span>
                        <button class="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors">
                            Detail
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produk 4 -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:-translate-y-2 transition-transform duration-300">
                <div class="h-48 bg-gradient-to-r from-orange-500 to-orange-700 flex items-center justify-center">
                    <svg class="w-20 h-20 text-white" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9 6a3 3 0 11-6 0 3 3 0 016 0zM17 6a3 3 0 11-6 0 3 3 0 016 0zM12.93 17c.046-.327.07-.66.07-1a6.97 6.97 0 00-1.5-4.33A5 5 0 0119 16v1h-6.07zM6 11a5 5 0 015 5v1H1v-1a5 5 0 015-5z"/>
                    </svg>
                </div>
                <div class="p-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-2">Sistem Manajemen User</h3>
                    <p class="text-gray-600 mb-4">Modul otentikasi dan autorisasi yang dapat diintegrasikan dengan sistem lain.</p>
                    <div class="flex justify-between items-center">
                        <span class="text-2xl font-bold text-orange-600">Rp 8.000.000</span>
                        <button class="px-4 py-2 bg-orange-600 text-white rounded-lg hover:bg-orange-700 transition-colors">
                            Detail
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produk 5 -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:-translate-y-2 transition-transform duration-300">
                <div class="h-48 bg-gradient-to-r from-red-500 to-red-700 flex items-center justify-center">
                    <svg class="w-20 h-20 text-white" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M5 2a1 1 0 011 1v1h1a1 1 0 010 2H6v1a1 1 0 01-2 0V6H3a1 1 0 010-2h1V3a1 1 0 011-1zm0 10a1 1 0 011 1v1h1a1 1 0 110 2H6v1a1 1 0 11-2 0v-1H3a1 1 0 110-2h1v-1a1 1 0 011-1zM12 2a1 1 0 01.967.744L14.146 7.2 17.5 9.134a1 1 0 010 1.732l-3.354 1.935-1.18 4.455a1 1 0 01-1.933 0L9.854 12.2 6.5 10.266a1 1 0 010-1.732l3.354-1.935 1.18-4.455A1 1 0 0112 2z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <div class="p-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-2">API Service Module</h3>
                    <p class="text-gray-600 mb-4">Kumpulan modul API yang siap pakai untuk integrasi antar sistem.</p>
                    <div class="flex justify-between items-center">
                        <span class="text-2xl font-bold text-red-600">Rp 12.000.000</span>
                        <button class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors">
                            Detail
                        </button>
                    </div>
                </div>
            </div>

            <!-- Produk 6 -->
            <div class="bg-white rounded-xl shadow-lg overflow-hidden transform hover:-translate-y-2 transition-transform duration-300">
                <div class="h-48 bg-gradient-to-r from-teal-500 to-teal-700 flex items-center justify-center">
                    <svg class="w-20 h-20 text-white" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M2.166 4.999A11.954 11.954 0 0010 1.944 11.954 11.954 0 0017.834 5c.11.65.166 1.32.166 2.001 0 5.225-3.34 9.67-8 11.317C5.34 16.67 2 12.225 2 7c0-.682.057-1.35.166-2.001zm11.541 3.708a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <div class="p-6">
                    <h3 class="text-xl font-bold text-gray-800 mb-2">Training & Konsultasi</h3>
                    <p class="text-gray-600 mb-4">Pelatihan OOP PHP dan konsultasi arsitektur untuk tim pengembangan.</p>
                    <div class="flex justify-between items-center">
                        <span class="text-2xl font-bold text-teal-600">Rp 5.000.000</span>
                        <button class="px-4 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700 transition-colors">
                            Detail
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Deskripsi -->
        <div class="mt-16 bg-white p-8 rounded-xl shadow-lg">
            <h2 class="text-2xl font-bold text-gray-800 mb-6">Keunggulan Produk Kami</h2>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div class="text-center">
                    <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
                        <svg class="w-8 h-8 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M12.316 3.051a1 1 0 01.633 1.265l-4 12a1 1 0 11-1.898-.632l4-12a1 1 0 011.265-.633zM5.707 6.293a1 1 0 010 1.414L3.414 10l2.293 2.293a1 1 0 11-1.414 1.414l-3-3a1 1 0 010-1.414l3-3a1 1 0 011.414 0zm8.586 0a1 1 0 011.414 0l3 3a1 1 0 010 1.414l-3 3a1 1 0 11-1.414-1.414L16.586 10l-2.293-2.293a1 1 0 010-1.414z" clip-rule="evenodd"/>
                        </svg>
                    </div>
                    <h3 class="font-bold text-gray-800 mb-2">Modular Design</h3>
                    <p class="text-gray-600 text-sm">Setiap komponen dapat dikembangkan dan diperbarui secara independen.</p>
                </div>

                <div class="text-center">
                    <div class="w-16 h-16 bg-green-100 rounded-full flex items-center justify-center mx-auto mb-4">
                        <svg class="w-8 h-8 text-green-600" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M11.3 1.046A1 1 0 0112 2v5h4a1 1 0 01.82 1.573l-7 10A1 1 0 018 18v-5H4a1 1 0 01-.82-1.573l7-10a1 1 0 011.12-.38z" clip-rule="evenodd"/>
                        </svg>
                    </div>
                    <h3 class="font-bold text-gray-800 mb-2">High Performance</h3>
                    <p class="text-gray-600 text-sm">Dikembangkan dengan optimasi untuk kecepatan dan efisiensi.</p>
                </div>

                <div class="text-center">
                    <div class="w-16 h-16 bg-purple-100 rounded-full flex items-center justify-center mx-auto mb-4">
                        <svg class="w-8 h-8 text-purple-600" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M2.166 4.999A11.954 11.954 0 0010 1.944 11.954 11.954 0 0017.834 5c.11.65.166 1.32.166 2.001 0 5.225-3.34 9.67-8 11.317C5.34 16.67 2 12.225 2 7c0-.682.057-1.35.166-2.001zm11.541 3.708a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                        </svg>
                    </div>
                    <h3 class="font-bold text-gray-800 mb-2">Secure & Reliable</h3>
                    <p class="text-gray-600 text-sm">Dilengkapi dengan fitur keamanan dan stabilitas terbaik.</p>
                </div>
            </div>
        </div>
    </div>
</section>';
    }
}
?>
