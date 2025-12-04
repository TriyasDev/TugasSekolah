<?php
// File: ProdukPage.php

require_once 'BasePage.php';

class ProdukPage extends BasePage {

    protected function generateHeaderHtml(): string {
        // Implementasi header yang sama/rapi dengan menggunakan properti
        $navHtml = '<nav>';
        foreach ($this->navigasi as $nama => $link) {
            $class = (strtolower($nama) == $this->pageId) ? 'nav-link active' : 'nav-link';
            $navHtml .= "<a class=\"{$class}\" href=\"{$link}\">{$nama}</a>";
        }
        $navHtml .= '</nav>';

        return "<header class=\"header\">\n" .
               "    <div class=\"container\">\n" .
               "        <h1 style=\"display: inline-block; margin: 0;\">PBO App</h1>\n" .
               "        {$navHtml}\n" .
               "    </div>\n" .
               "</header>\n";
    }

    protected function generateBodyContent(): string {
        $produk = [
            'Sistem Kasir PBO',
            'CMS Modular V2.0',
            'API Gateway Service'
        ];

        $gridHtml = "<div class=\"product-grid\">\n";
        foreach ($produk as $nama) {
            $gridHtml .= "<div class=\"product-card\">\n";
            $gridHtml .= "    <h3>{$nama}</h3>\n";
            $gridHtml .= "    <p>Solusi bisnis berbasis arsitektur objek.</p>\n";
            $gridHtml .= "</div>\n";
        }
        $gridHtml .= "</div>\n";

        return "<h2>Daftar Produk Kami</h2>\n" . $gridHtml;
    }
}
