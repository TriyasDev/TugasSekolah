<?php
$namaSaya = "Triyas Prasetya";
$umurSaya = 18;
$tanggalLahir = "22/06/2007";
$sekolah = "SMK Budi Bakti Ciwidey";
$PKL = "Cyberlabs";
$instagram = "_iyas03v";

$x1 = intval($_GET["x1"] ?? 0);
$y1 = intval($_GET["y1"] ?? 0);
$pertambahan = $x1 + $y1;

$x2 = intval($_GET["x2"] ?? 0);
$y2 = intval($_GET["y2"] ?? 0);
$pengurangan = $x2 - $y2;

$x3 = intval($_GET["x3"] ?? 0);
$y3 = intval($_GET["y3"] ?? 0);
$perkalian = $x3 * $y3;

$x4 = floatval($_GET["x4"] ?? 0);
$y4 = floatval($_GET["y4"] ?? 1);
$pembagian = $x4 / $y4;
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Biodata Kartu Pengenalan</title>
  <style>
    body {
      font-family: 'Arial', sans-serif;
      background: #f5f5f5;
      /* Latar belakang putih-abu muda untuk minimalis */
      margin: 0;
      padding: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      min-height: 100vh;
      color: #333;
    }

    .id-card {
      background: white;
      border-radius: 15px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      /* Shadow lebih halus untuk minimalis */
      width: 350px;
      padding: 20px;
      text-align: center;
      margin-bottom: 30px;
      position: relative;
    }

    .id-card img {
      width: 80px;
      height: 80px;
      border-radius: 50%;
      /* Membuat gambar lingkaran */
      object-fit: cover;
      /* Memastikan gambar pas dalam lingkaran */
      margin: 20px auto 10px;
      display: block;
      border: 3px solid #e0e0e0;
      /* Border tipis untuk konsep kartu */
    }

    .info {
      margin-top: 10px;
    }

    .info p {
      margin: 8px 0;
      font-size: 14px;
      color: #333;
    }

    .info .label {
      font-weight: bold;
      color: #555;
    }

    section {
      background: white;
      border-radius: 10px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      padding: 20px;
      width: 100%;
      max-width: 600px;
    }

    h3 {
      color: #333;
      border-bottom: 2px solid #ddd;
      /* Border bawah lebih halus */
      padding-bottom: 5px;
      margin-bottom: 15px;
    }

    form {
      display: flex;
      gap: 10px;
      margin-bottom: 10px;
      align-items: center;
    }

    input[type="number"] {
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 5px;
      width: 80px;
      background: #fafafa;
    }

    input[type="submit"] {
      padding: 8px 15px;
      background: #333;
      /* Warna gelap untuk kontras minimalis */
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      transition: background 0.3s;
    }

    input[type="submit"]:hover {
      background: #555;
    }

    p {
      font-weight: bold;
      color: #333;
    }
  </style>
</head>

<body>
  <div class="id-card">
    <img src="img/Triyas.jpg" alt="Foto Triyas Prasetya">
    <div class="info">
      <p><span class="label">Nama:</span> <?= $namaSaya; ?></p>
      <p><span class="label">Umur:</span> <?= $umurSaya; ?> tahun</p>
      <p><span class="label">Tanggal Lahir:</span> <?= $tanggalLahir; ?></p>
      <p><span class="label">Sekolah:</span> <?= $sekolah; ?></p>
      <p><span class="label">PKL:</span> <?= $PKL; ?></p>
      <p><span class="label">Instagram:</span> <?= $instagram; ?></p>
    </div>
  </div>

  <section>
    <h3>Pertambahan</h3>
    <form action="" method="get">
      <input type="number" name="x1" value="<?= $x1 ?>" placeholder="Angka 1">
      <input type="number" name="y1" value="<?= $y1 ?>" placeholder="Angka 2">
      <input type="submit" value="Hitung">
    </form>
    <p>Hasilnya: <?= $pertambahan; ?></p>

    <h3>Pengurangan</h3>
    <form action="" method="get">
      <input type="number" name="x2" value="<?= $x2 ?>" placeholder="Angka 1">
      <input type="number" name="y2" value="<?= $y2 ?>" placeholder="Angka 2">
      <input type="submit" value="Hitung">
    </form>
    <p>Hasilnya: <?= $pengurangan; ?></p>

    <h3>Perkalian</h3>
    <form action="" method="get">
      <input type="number" name="x3" value="<?= $x3 ?>" placeholder="Angka 1">
      <input type="number" name="y3" value="<?= $y3 ?>" placeholder="Angka 2">
      <input type="submit" value="Hitung">
    </form>
    <p>Hasilnya: <?= $perkalian; ?></p>

    <h3>Pembagian</h3>
    <form action="" method="get">
      <input type="number" name="x4" value="<?= $x4 ?>" placeholder="Angka 1" step="0.01">
      <input type="number" name="y4" value="<?= $y4 ?>" placeholder="Angka 2" step="0.01">
      <input type="submit" value="Hitung">
    </form>
    <p>Hasilnya: <?= $pembagian; ?></p>
  </section>
</body>

</html>
