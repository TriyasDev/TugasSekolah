<?php
// File: HomePage.php

require_once 'BasePage.php';

class HomePage extends BasePage {

    protected function generateHeaderHtml(): string {
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
        return "<div class=\"hero\">\n" .
               "    <h1></h1>\n" .
               "    <p>Selamat datang di halaman utama. Pelajari lebih lanjut tentang prinsip PBO.</p>\n" .
               "</div>\n";
    }
}
