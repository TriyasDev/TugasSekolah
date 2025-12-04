<?php
// AboutPage.php
// Class turunan untuk Halaman About

require_once 'BasePage.php';

class AboutPage extends BasePage {
    public function __construct() {
        parent::__construct("Tentang Kami");
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
<section class="py-16 bg-white">
    <div class="container mx-auto px-6">
        <div class="text-center mb-12">
            <h1 class="text-4xl font-bold text-gray-800 mb-4">Tentang PT. INOVASI DIGITAL</h1>
            <div class="w-24 h-1 bg-blue-600 mx-auto mb-6"></div>
            <p class="text-gray-600 max-w-3xl mx-auto">
                Perusahaan pengembangan software terkemuka yang berfokus pada solusi digital inovatif
                dengan pendekatan Object-Oriented Programming.
            </p>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-2 gap-12">
            <!-- Visi -->
            <div class="bg-blue-50 p-8 rounded-xl">
                <h2 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                    <svg class="w-8 h-8 text-blue-600 mr-3" fill="currentColor" viewBox="0 0 20 20">
                        <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                    </svg>
                    Visi Perusahaan
                </h2>
                <p class="text-gray-700 mb-4">
                    Menjadi perusahaan teknologi terdepan dalam pengembangan solusi web modular
                    yang efisien dan scalable di Indonesia.
                </p>
                <ul class="space-y-3 text-gray-600">
                    <li class="flex items-start">
                        <svg class="w-5 h-5 text-green-500 mr-2 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                        </svg>
                        <span>Pemimpin dalam inovasi teknologi web modular</span>
                    </li>
                    <li class="flex items-start">
                        <svg class="w-5 h-5 text-green-500 mr-2 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                        </svg>
                        <span>Penyedia solusi digital terpercaya nasional</span>
                    </li>
                </ul>
            </div>

            <!-- Misi -->
            <div class="bg-gray-50 p-8 rounded-xl">
                <h2 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                    <svg class="w-8 h-8 text-blue-600 mr-3" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"/>
                    </svg>
                    Misi Perusahaan
                </h2>
                <p class="text-gray-700 mb-4">
                    Memberikan solusi pengembangan web terbaik dengan menerapkan prinsip-prinsip
                    Object-Oriented Programming yang modern dan efisien.
                </p>
                <ul class="space-y-3 text-gray-600">
                    <li class="flex items-start">
                        <div class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mr-2 flex-shrink-0">
                            <span class="text-blue-600 font-bold">1</span>
                        </div>
                        <span>Mengembangkan sistem web modular dengan arsitektur yang solid</span>
                    </li>
                    <li class="flex items-start">
                        <div class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mr-2 flex-shrink-0">
                            <span class="text-blue-600 font-bold">2</span>
                        </div>
                        <span>Menyediakan pelatihan dan konsultasi PBO untuk developer</span>
                    </li>
                    <li class="flex items-start">
                        <div class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mr-2 flex-shrink-0">
                            <span class="text-blue-600 font-bold">3</span>
                        </div>
                        <span>Menerapkan best practices dalam pengembangan software</span>
                    </li>
                    <li class="flex items-start">
                        <div class="w-6 h-6 bg-blue-100 rounded-full flex items-center justify-center mr-2 flex-shrink-0">
                            <span class="text-blue-600 font-bold">4</span>
                        </div>
                        <span>Memberikan dukungan penuh untuk keberhasilan klien</span>
                    </li>
                </ul>
            </div>
        </div>

        <!-- Nilai Perusahaan -->
        <div class="mt-16">
            <h2 class="text-3xl font-bold text-center text-gray-800 mb-10">Nilai-Nilai Perusahaan</h2>
            <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
                <div class="bg-white p-6 rounded-lg shadow-md border border-gray-100">
                    <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center mb-4">
                        <svg class="w-6 h-6 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M12.316 3.051a1 1 0 01.633 1.265l-4 12a1 1 0 11-1.898-.632l4-12a1 1 0 011.265-.633zM5.707 6.293a1 1 0 010 1.414L3.414 10l2.293 2.293a1 1 0 11-1.414 1.414l-3-3a1 1 0 010-1.414l3-3a1 1 0 011.414 0zm8.586 0a1 1 0 011.414 0l3 3a1 1 0 010 1.414l-3 3a1 1 0 11-1.414-1.414L16.586 10l-2.293-2.293a1 1 0 010-1.414z" clip-rule="evenodd"/>
                        </svg>
                    </div>
                    <h3 class="text-xl font-semibold text-gray-800 mb-2">Inovasi</h3>
                    <p class="text-gray-600">Terus berinovasi dalam teknologi dan metodologi pengembangan.</p>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-md border border-gray-100">
                    <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center mb-4">
                        <svg class="w-6 h-6 text-green-600" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M2.166 4.999A11.954 11.954 0 0010 1.944 11.954 11.954 0 0017.834 5c.11.65.166 1.32.166 2.001 0 5.225-3.34 9.67-8 11.317C5.34 16.67 2 12.225 2 7c0-.682.057-1.35.166-2.001zm11.541 3.708a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"/>
                        </svg>
                    </div>
                    <h3 class="text-xl font-semibold text-gray-800 mb-2">Kualitas</h3>
                    <p class="text-gray-600">Menghasilkan kode yang bersih, terstruktur, dan mudah dipelihara.</p>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-md border border-gray-100">
                    <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center mb-4">
                        <svg class="w-6 h-6 text-purple-600" fill="currentColor" viewBox="0 0 20 20">
                            <path d="M13 6a3 3 0 11-6 0 3 3 0 016 0zM18 8a2 2 0 11-4 0 2 2 0 014 0zM14 15a4 4 0 00-8 0v3h8v-3zM6 8a2 2 0 11-4 0 2 2 0 014 0zM16 18v-3a5.972 5.972 0 00-.75-2.906A3.005 3.005 0 0119 15v3h-3zM4.75 12.094A5.973 5.973 0 004 15v3H1v-3a3 3 0 013.75-2.906z"/>
                        </svg>
                    </div>
                    <h3 class="text-xl font-semibold text-gray-800 mb-2">Kolaborasi</h3>
                    <p class="text-gray-600">Bekerja sama dengan tim dan klien untuk hasil terbaik.</p>
                </div>

                <div class="bg-white p-6 rounded-lg shadow-md border border-gray-100">
                    <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center mb-4">
                        <svg class="w-6 h-6 text-orange-600" fill="currentColor" viewBox="0 0 20 20">
                            <path fill-rule="evenodd" d="M11.3 1.046A1 1 0 0112 2v5h4a1 1 0 01.82 1.573l-7 10A1 1 0 018 18v-5H4a1 1 0 01-.82-1.573l7-10a1 1 0 011.12-.38z" clip-rule="evenodd"/>
                        </svg>
                    </div>
                    <h3 class="text-xl font-semibold text-gray-800 mb-2">Efisiensi</h3>
                    <p class="text-gray-600">Mengoptimalkan proses pengembangan dengan pendekatan modular.</p>
                </div>
            </div>
        </div>
    </div>
</section>';
    }
}
?>
