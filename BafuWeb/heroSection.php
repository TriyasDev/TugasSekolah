<?php
require_once 'component.php';

// Class HeroSection (Komponen Utama)
class HeroSection extends Component
{
    // Properti
    private $judul;
    private $deskripsi;
    private $callToAction;
    private $height;

    public function __construct(string $judul, string $deskripsi = "", string $cta, $height = "h-[900px] md:min-h-[500px]", $additionalClass = "")
    {
        parent::__construct($additionalClass);
        $this->judul = $judul;
        $this->deskripsi = $deskripsi;
        $this->callToAction = $cta;
        $this->height = $height;
    }

    // Implementasi Method render()
    public function render()
    {
        // Parse height value untuk responsive
        $heightClasses = $this->parseHeight($this->height);

        $html = "<section id='home' class='relative overflow-hidden bg-cover bg-[center_40%] w-full bg-[url(assets/background.jpeg)] bg-[length:150%_auto] {$heightClasses}" . $this->getBaseClass() . "'>";
        $html .= "<div class='container mx-auto px-6 '>";
        $html .= "<div class='grid grid-cols-1 lg:grid-cols-2 items-center gap-8 lg:gap-12'>";

        // Kolom 1: Teks
        $html .= "<div class='text-center lg:text-left'>";
        $html .= "<h1 class='text-3xl md:text-4xl lg:text-5xl font-bold text-white mb-4 leading-tight'>{$this->judul}</h1>";

        // Deskripsi (jika ada)
        if (!empty($this->deskripsi)) {
            $html .= "<p class='text-lg md:text-xl text-blue-100 mb-8'>{$this->deskripsi}</p>";
        }

        // Call to Action
        $html .= "<div class='flex flex-col sm:flex-row gap-3 justify-center lg:justify-start'>";
        $html .= "<a href='#join' class='px-6 py-3 bg-white text-blue-700 hover:bg-blue-50 font-bold rounded-lg shadow-xl transition-all duration-300 transform hover:-translate-y-1 inline-block text-sm md:text-base'>";
        $html .= "{$this->callToAction}";
        $html .= "</a>";
        $html .= "<a href='#about' class='px-6 py-3 bg-transparent border-2 border-white text-white hover:bg-white hover:text-blue-700 font-bold rounded-lg shadow-lg transition-all duration-300 inline-block text-sm md:text-base'>";
        $html .= "Pelajari Lebih Lanjut";
        $html .= "</a>";
        $html .= "</div>"; // Tutup flex container tombol

        $html .= "</div>"; // Tutup kolom teks

        // Kolom 2: Gambar
        $html .= "<div class='flex justify-center lg:justify-end relative'>";
        $html .= "<div class='relative'>";
        $html .= "<img src='assets/mambo.png' alt='MyPacar' class='w-full max-w-md lg:max-w-lg xl:max-w-2xl transform lg:scale-100 lg:translate-x-8'>";

        // Efek dekoratif (opsional)
        $html .= "<div class='absolute -bottom-3 -right-3 w-20 h-20 bg-blue-400 rounded-full opacity-20 -z-10'></div>";
        $html .= "<div class='absolute -top-3 -left-3 w-14 h-14 bg-blue-300 rounded-full opacity-20 -z-10'></div>";

        $html .= "</div>"; // Tutup relative wrapper
        $html .= "</div>"; // Tutup kolom gambar

        $html .= "</div>"; // Tutup grid
        $html .= "</div>"; // Tutup container

        $html .= "</section>";

        return $html;
    }

    // Method untuk parse height value
    private function parseHeight($height)
    {
        // Jika sudah berupa string Tailwind class
        if (strpos($height, 'min-h-[') !== false || strpos($height, 'h-') !== false) {
            return $height;
        }

        // Jika berupa array [mobile, tablet, desktop]
        if (is_array($height)) {
            $mobile = $height[0] ?? '600px';
            $tablet = $height[1] ?? '700px';
            $desktop = $height[2] ?? '800px';

            return "min-h-[{$mobile}] md:min-h-[{$tablet}] lg:min-h-[{$desktop}]";
        }

        // Jika berupa string sederhana
        return "min-h-[{$height}]";
    }
}
?>
