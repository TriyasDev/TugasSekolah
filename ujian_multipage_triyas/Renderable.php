<?php
// File: Renderable.php

/**
 * Interface Renderable
 * Mendefinisikan kontrak bahwa objek harus dapat menghasilkan output HTML.
 */
interface Renderable {

    

    /**
     * Bertanggung jawab mencetak kode HTML lengkap (sebuah string)
     * dari komponen/halaman tersebut.
     *
     * @return string Kode HTML lengkap.
     */
    public function renderHtml(): string;
}
