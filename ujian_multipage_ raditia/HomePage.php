<?php
// HomePage.php
// Class turunan untuk Landing Page

require_once 'BasePage.php';

class HomePage extends BasePage {
    public function __construct() {
        parent::__construct("Beranda");
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
<section class="py-16 bg-gradient-to-r from-blue-50 to-white">
    <div class="container mx-auto px-6 text-center">
        <h1 class="text-4xl md:text-5xl font-bold text-gray-800 mb-6">
            Membangun Aplikasi Web Modular dengan PHP PBO
        </h1>
        <div class="w-32 h-2 bg-blue-600 mx-auto mb-8"></div>
        <p class="text-xl text-gray-600 max-w-3xl mx-auto mb-10">
            Sistem web modular yang efisien dengan penerapan Object-Oriented Programming (OOP)
            dalam PHP untuk pengembangan yang terstruktur dan mudah dipelihara.
        </p>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-12">
            <div class="bg-white p-8 rounded-xl shadow-lg">
                <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
                    <svg class="w-8 h-8 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M12.316 3.051a1 1 0 01.633 1.265l-4 12a1 1 0 11-1.898-.632l4-12a1 1 0 011.265-.633zM5.707 6.293a1 1 0 010 1.414L3.414 10l2.293 2.293a1 1 0 11-1.414 1.414l-3-3a1 1 0 010-1.414l3-3a1 1 0 011.414 0zm8.586 0a1 1 0 011.414 0l3 3a1 1 0 010 1.414l-3 3a1 1 0 11-1.414-1.414L16.586 10l-2.293-2.293a1 1 0 010-1.414z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <h3 class="text-xl font-bold text-gray-800 mb-4">Modular Design</h3>
                <p class="text-gray-600">Struktur kode terpisah dengan inheritance dan interface untuk fleksibilitas maksimal.</p>
            </div>

            <div class="bg-white p-8 rounded-xl shadow-lg">
                <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
                    <svg class="w-8 h-8 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M11.3 1.046A1 1 0 0112 2v5h4a1 1 0 01.82 1.573l-7 10A1 1 0 018 18v-5H4a1 1 0 01-.82-1.573l7-10a1 1 0 011.12-.38z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <h3 class="text-xl font-bold text-gray-800 mb-4">Code Reusability</h3>
                <p class="text-gray-600">Komponen yang dapat digunakan kembali mengurangi duplikasi kode dan meningkatkan efisiensi.</p>
            </div>

            <div class="bg-white p-8 rounded-xl shadow-lg">
                <div class="w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
                    <svg class="w-8 h-8 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M2.166 4.999A11.954 11.954 0 0010 1.944 11.954 11.954 0 0017.834 5c.11.65.166 1.32.166 2.001 0 5.225-3.34 9.67-8 11.317C5.34 16.67 2 12.225 2 7c0-.682.057-1.35.166-2.001zm11.541 3.708a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                    </svg>
                </div>
                <h3 class="text-xl font-bold text-gray-800 mb-4">Easy Maintenance</h3>
                <p class="text-gray-600">Perubahan pada satu komponen tidak mempengaruhi komponen lainnya.</p>
            </div>
        </div>
    </div>
</section>';
    }
}
?>
