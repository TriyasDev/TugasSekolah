<?php
require_once 'component.php';

// Class Header (Komponen Bagian Atas)
class Header extends Component {
    // Properti
    private $logoText;
    private $menuLinks;

    public function __construct(string $logo, array $links, $additionalClass = "", $showButton = true) {
        parent::__construct($additionalClass);
        $this->logoText = $logo;
        $this->menuLinks = $links;
    }

    // Implementasi Method render()
    public function render() {
        $html = "<header class='sticky top-0 z-50 bg-white shadow-md " . $this->getBaseClass() . "'>";
        $html .= "<div class='container mx-auto px-6 py-4'>";
        $html .= "<div class='flex justify-between items-center'>";

        // Logo
        $html .= "<div class='flex items-center'>";
        $html .= "<div class=''>";
        $html .= "</div>";
        $html .= "<h1 class='text-2xl font-bold text-gray-800'>{$this->logoText}</h1>";
        $html .= "</div>";

        // Desktop Menu
        $html .= "<nav class='hidden md:flex items-center space-x-8'>";
        $html .= "<ul class='flex space-x-8'>";

        // Loop untuk menu links
        foreach ($this->menuLinks as $link => $url) {
            $html .= "<li><a href='{$url}' class='text-gray-700 hover:text-blue-600 font-medium transition-colors duration-200'>{$link}</a></li>";
        }

        $html .= "</ul>";

        $html .= "</nav>";

        // Mobile Menu Button
        $html .= "<div class='md:hidden'>";
        $html .= "<button id='mobile-menu-button' class='text-gray-700 hover:text-blue-600 focus:outline-none'>";
        $html .= "<svg class='w-6 h-6' fill='none' stroke='currentColor' viewBox='0 0 24 24'>";
        $html .= "<path stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M4 6h16M4 12h16M4 18h16'></path>";
        $html .= "</svg>";
        $html .= "</button>";
        $html .= "</div>";

        $html .= "</div>";

        // Mobile Menu (hidden by default)
        $html .= "<div id='mobile-menu' class='md:hidden mt-4 hidden'>";
        $html .= "<ul class='space-y-4'>";

        foreach ($this->menuLinks as $link => $url) {
            $html .= "<li><a href='{$url}' class='block text-gray-700 hover:text-blue-600 font-medium py-2'>{$link}</a></li>";
        }

        $html .= "</ul>";
        $html .= "</div>";

        $html .= "</div>";
        $html .= "</header>";

        // JavaScript untuk mobile menu
        $html .= "
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                const mobileMenuButton = document.getElementById('mobile-menu-button');
                const mobileMenu = document.getElementById('mobile-menu');

                if (mobileMenuButton && mobileMenu) {
                    mobileMenuButton.addEventListener('click', function() {
                        mobileMenu.classList.toggle('hidden');
                    });

                    // Close menu when clicking outside
                    document.addEventListener('click', function(event) {
                        if (!mobileMenuButton.contains(event.target) && !mobileMenu.contains(event.target)) {
                            mobileMenu.classList.add('hidden');
                        }
                    });
                }
            });
        </script>
        ";

        return $html;
    }
}
?>
