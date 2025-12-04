<?php
class Footer {
    private $companyName;
    private $year;
    private $links;
    private $socialMedia;

    public function __construct($companyName = "BAFU COMMUNITY", $year = 2025, $links = [], $socialMedia = []) {
        $this->companyName = $companyName;
        $this->year = $year;
        $this->links = $links ?: [
            ["Home", "#home"],
            ["About", "#about"],
            ["Price", "#price"],
            ["Contact", "#contact"]
        ];
        $this->socialMedia = $socialMedia ?: [
            ["Twitter", "#", "M22 4s-.7 2.1-2 3.4c1.6 10-9.4 17.3-18 11.6 2.2.1 4.4-.6 6-2C3 15.5.5 9.6 3 5c2.2 2.6 5.6 4.1 9 4-.9-4.2 4-6.6 7-3.8 1.1 0 3-1.2 3-1.2z"],
            ["Instagram", "#", "M7.75 2A5.75 5.75 0 0 0 2 7.75v8.5A5.75 5.75 0 0 0 7.75 22h8.5A5.75 5.75 0 0 0 22 16.25v-8.5A5.75 5.75 0 0 0 16.25 2h-8.5zM17 12a5 5 0 1 1-10 0 5 5 0 0 1 10 0zm-1.25 0a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0zM17.5 7.75a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0z"],
        ];
    }

    public function render() {
        return '
        <footer class="bg-gray-900 text-white py-12">
            <div class="container mx-auto px-6">
                <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
                    <!-- Company Info -->
                    <div class="space-y-4">
                        <h2 class="text-2xl font-bold text-blue-400">' . $this->companyName . '</h2>
                        <p class="text-gray-300">Membangun komunitas digital yang kuat dan saling mendukung untuk masa depan yang lebih baik.</p>
                    </div>

                    <!-- Quick Links -->
                    <div>
                        <h3 class="text-xl font-semibold mb-4 text-blue-300">Quick Links</h3>
                        <ul class="space-y-2">
        ' . $this->renderLinks() . '
                        </ul>
                    </div>

                    <!-- Contact Info -->
                    <div>
                        <h3 class="text-xl font-semibold mb-4 text-blue-300">Contact Us</h3>
                        <ul class="space-y-2 text-gray-300">
                            <li class="flex items-center">
                                <svg class="w-5 h-5 mr-2 text-blue-400" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M5.05 4.05a7 7 0 119.9 9.9L10 18.9l-4.95-4.95a7 7 0 010-9.9zM10 11a2 2 0 100-4 2 2 0 000 4z" clip-rule="evenodd"/>
                                </svg>
                                Jl. Rusdi No. 67, Bogor
                            </li>
                            <li class="flex items-center">
                                <svg class="w-5 h-5 mr-2 text-blue-400" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M2 3a1 1 0 011-1h2.153a1 1 0 01.986.836l.74 4.435a1 1 0 01-.54 1.06l-1.548.773a11.037 11.037 0 006.105 6.105l.774-1.548a1 1 0 011.059-.54l4.435.74a1 1 0 01.836.986V17a1 1 0 01-1 1h-2C7.82 18 2 12.18 2 5V3z"/>
                                </svg>
                                (+62) 1234-5678-9101
                            </li>
                            <li class="flex items-center">
                                <svg class="w-5 h-5 mr-2 text-blue-400" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"/>
                                    <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"/>
                                </svg>
                                BAFUCommunity@gmail.com
                            </li>
                        </ul>
                    </div>

                    <!-- Social Media -->
                    <div>
                        <h3 class="text-xl font-semibold mb-4 text-blue-300">Follow Us</h3>
                        <div class="flex space-x-4">
        ' . $this->renderSocialMedia() . '
                        </div>
                    </div>
                </div>

                <!-- Copyright -->
                <div class="border-t border-gray-800 mt-8 pt-8 text-center text-gray-400">
                    <p>&copy; ' . $this->year . ' ' . $this->companyName . '. All rights reserved.</p>
                </div>
            </div>
        </footer>
        ';
    }

    private function renderLinks() {
        $html = '';
        foreach ($this->links as $link) {
            $html .= '<li><a href="' . $link[1] . '" class="text-gray-300 hover:text-blue-400 transition-colors duration-200">' . $link[0] . '</a></li>';
        }
        return $html;
    }

    private function renderSocialMedia() {
        $html = '';
        foreach ($this->socialMedia as $social) {
            $html .= '
            <a href="' . $social[1] . '" class="bg-gray-800 hover:bg-blue-600 p-3 rounded-full transition-colors duration-200" title="' . $social[0] . '">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                    <path d="' . $social[2] . '"/>
                </svg>
            </a>';
        }
        return $html;
    }
}
?>
