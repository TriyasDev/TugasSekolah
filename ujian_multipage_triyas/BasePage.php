<?php
require_once 'Renderable.php';

abstract class BasePage implements Renderable{
    private $judul;
    private $navigasi;

    public function __construct($judul, $navigasi){
        $this->judul = $judul;
        $this->navigasi = $navigasi;
    }

    abstract protected function generatedHeaderHtml();
    abstract protected function generatedBodyHtml();

    public function renderHtml(){
        echo "<!DOCTYPE html>
        <html>
           <head>
              <title>{$this->judul}</title>
              <script src='https://cdn.tailwindcss.com'></script>
           </head>
           <body class='font-sans'>
                ". $this->generatedBodyHtml() ."
                ". $this->generatedHeaderHtml() ."
                <footer class=''>
                    &copy; 20025 PT. INOVASI DIGITAL INDONESIA
                </footer>
           </body>
        </html>";
    }
};
