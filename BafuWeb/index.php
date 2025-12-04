<?php
// Tautan ke file Class
require_once 'header.php';
require_once 'heroSection.php';
require_once 'aboutSection.php';
require_once 'priceSection.php';
require_once 'contactSection.php';
require_once 'footer.php';

// Data untuk Header
$menu = [
  "Home" => "#home",
  "About" => "#about",
  "Price" => "#price",
  "Contact" => "#contact"
];

// --- 1. Instansiasi Objek (Membuat Objek) ---
$header = new Header("BAFU Community", $menu);
$hero = new HeroSection(
  "Welcome To Our Community Website",
  "Bergabunglah dengan komunitas digital terbesar untuk pengembangan diri dan networking profesional",
  "Join Us"
);
$about = new AboutSection();
$price = new PriceSection();
$contact = new ContactSection();
$footer = new Footer();
?>

<!DOCTYPE html>
<html lang="id">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BAFU Community | Home</title>
  <!-- Tailwind CSS CDN -->
  <script src="https://cdn.tailwindcss.com"></script>
  <style>
    body {
      font-family: 'Inter', sans-serif;
    }

    /* Custom scroll behavior for smooth scrolling */
    html {
      scroll-behavior: smooth;
    }
  </style>
</head>

<body class="bg-white">

  <!-- --- 2. Pemanggilan Method Render --- -->
  <?php echo $header->render(); ?>
  <?php echo $hero->render(); ?>
  <?php echo $about->render(); ?>
  <?php echo $price->render(); ?>
  <?php echo $contact->render(); ?>
  <?php echo $footer->render(); ?>

</body>

</html>
