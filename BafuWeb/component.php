<?php
// Class Dasar untuk semua komponen
abstract class Component {
    // Properti dasar untuk CSS
    protected $cssClass = "";

    // Constructor untuk menerima class tambahan
    public function __construct($additionalClass = "") {
        $this->cssClass = $additionalClass;
    }

    // Method abstrak yang harus diimplementasikan oleh setiap turunan
    abstract public function render();

    // Method dasar yang bisa dipakai semua turunan
    public function getBaseClass() {
        return $this->cssClass;
    }

    // Method untuk mengatur class tambahan
    public function setAdditionalClass($class) {
        $this->cssClass .= " " . $class;
    }
}
?>
