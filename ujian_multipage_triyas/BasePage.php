<?php
// File: BasePage.php

// Pastikan interface Renderable sudah dimuat
require_once 'Renderable.php';

/**
 * Class Abstract BasePage
 * Menyediakan kerangka dasar (wrapper) HTML dan properti umum untuk semua halaman.
 */
abstract class BasePage implements Renderable {

    // Properti dasar (Encapsulation: protected)
    protected string $judul;
    protected array $navigasi;
    protected string $pageId;

    /**
     * Constructor untuk BasePage.
     *
     * @param string $judul Judul untuk tag <title> dan tampilan.
     * @param array $navigasi Array asosiatif untuk menu navigasi.
     * @param string $pageId ID halaman saat ini untuk penanda aktif.
     */
    public function __construct(string $judul, array $navigasi, string $pageId) {
        $this->judul = $judul;
        $this->navigasi = $navigasi;
        $this->pageId = $pageId;
    }

    // Method abstract: Harus diimplementasikan oleh Class anak
    abstract protected function generateHeaderHtml(): string;
    abstract protected function generateBodyContent(): string;

    /**
     * Implementasi Method renderHtml() dari Interface Renderable.
     * Merakit seluruh komponen HTML.
     */
    public function renderHtml(): string {
        // --- 1. Merakit Bagian Head ---
        $html = "<!DOCTYPE html>\n";
        $html .= "<html lang=\"id\">\n";
        $html .= "<head>\n";
        $html .= "    <meta charset=\"UTF-8\">\n";
        $html .= "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n";
        $html .= "    <title>{$this->judul} | Website Berbasis PBO</title>\n";
        // Inline CSS Sederhana
        $html .= "    <style>
            body { font-family: sans-serif; margin: 0; background-color: #f4f4f4; }
            .container { max-width: 1000px; margin: auto; padding: 20px; }
            .nav-link { margin-right: 15px; text-decoration: none; color: #333; }
            .nav-link.active { font-weight: bold; color: blue; border-bottom: 2px solid blue; }
            .header { background-color: #fff; padding: 10px 0; border-bottom: 1px solid #ccc; }
            .footer { background-color: #333; color: white; padding: 15px; text-align: center; margin-top: 40px; }
            .hero { background-color: #e0f7fa; padding: 80px 20px; text-align: center; margin-top: 20px; border-radius: 8px; }
            .product-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-top: 20px; }
            .product-card { background-color: white; padding: 20px; border: 1px solid #ddd; border-radius: 4px; text-align: center; }
        </style>\n";
        $html .= "</head>\n";

        // --- 2. Merakit Bagian Body ---
        $html .= "<body>\n";

        // Memanggil method abstract untuk Header (sudah diimplementasikan di turunan)
        $html .= $this->generateHeaderHtml();

        $html .= "<div class=\"container\">\n";

        // Memanggil method abstract untuk Konten (sudah diimplementasikan di turunan)
        $html .= $this->generateBodyContent();

        $html .= "</div>\n";

        // $html .= $this-generatedFooterContent();

        $html .= "</body>\n";
        $html .= "</html>\n";

        return $html;
    }
}
