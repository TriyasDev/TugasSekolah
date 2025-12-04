<?php
// File: AboutPage.php

require_once 'BasePage.php';

class AboutPage extends BasePage {

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
        return "<h2>Tentang Kami</h2>\n" .
               "<p>Kami adalah tim pengembang yang fokus pada arsitektur perangkat lunak yang bersih.</p>\n" .
               "<h3>Visi & Misi</h3>\n" .
               "<ul>\n" .
               "    <li>**Visi:** Menjadi penyedia solusi digital terdepan.</li>\n" .
               "    <li>**Misi 1:** Mengedukasi tim tentang prinsip PBO dan Clean Code.</li>\n" .
               "    <li>**Misi 2:** Menyediakan produk yang stabil dan mudah dipelihara.</li>\n" .
               "</ul>\n";
    }
}
