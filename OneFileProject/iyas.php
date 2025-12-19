<?php
$namaSaya = "Triyas Prasetya";
$umurSaya = 18;
$tanggalLahir = "22/06/2007";
$sekolah = "SMK Budi Bakti Ciwidey";
$instagram = "_iyas03v";

$x1 = intval($_GET["x1"] ?? null);
$y1 = intval($_GET["y1"] ?? null);
$pertambahan = $x1 + $y1;

$x2 = intval($_GET["x2"] ?? null);
$y2 = intval($_GET["y2"] ?? null);
$pengurangan = $x2 - $y2;

$x3 = intval($_GET["x3"] ?? null);
$y3 = intval($_GET["y3"] ?? null);
$perkalian = $x3 * $y3;

$x4 = floatval($_GET["x4"] ?? null);
$y4 = floatval($_GET["y4"] ?? null);
$pembagian = ($y4 != 0) ? ($x4 / $y4) : "Error (Bagi 0)";
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Kartu Minimalis & Kalkulator</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
  <style>
    :root {
      --primary-color: #333;
      --secondary-color: #555;
      --background-color: #f7f7f7;
      --card-background: #ffffff;
      --border-color: #e0e0e0;
      --shadow-light: 0 4px 12px rgba(0, 0, 0, 0.05);
    }

    body {
      font-family: 'Inter', system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
      background: var(--background-color);
      margin: 0;
      padding: 40px 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      color: var(--primary-color);
      line-height: 1.6;
    }

    .container {
      width: 100%;
      max-width: 800px;
      margin: 0 auto;
    }

    .card {
      background: var(--card-background);
      border-radius: 12px;
      padding: 30px;
      box-shadow: var(--shadow-light);
      margin-bottom: 30px;
      transition: all 0.2s ease-in-out;
    }

    .card:hover {
      box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
    }

    .biodata-header {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
      padding-bottom: 15px;
      border-bottom: 1px solid var(--border-color);
    }

    .biodata-header img {
      width: 70px;
      height: 70px;
      border-radius: 50%;
      object-fit: cover;
      border: 2px solid var(--border-color);
      margin-right: 20px;
      transition: transform 0.3s;
    }

    .biodata-header img:hover {
      transform: scale(1.05);
    }

    .biodata-header h2 {
      margin: 0;
      font-size: 24px;
      font-weight: 700;
      color: var(--primary-color);
    }

    .info p {
      margin: 8px 0;
      font-size: 15px;
    }

    .label {
      font-weight: 600;
      color: var(--secondary-color);
      min-width: 150px;
      display: inline-block;
    }

    .info a {
      color: var(--primary-color);
      text-decoration: none;
      font-weight: 600;
      border-bottom: 1px dashed var(--border-color);
      transition: color 0.2s;
    }

    .info a:hover {
      color: #007bff;
      border-bottom-color: transparent;
    }

    .calculator-section h1 {
      text-align: center;
      font-size: 28px;
      font-weight: 700;
      margin-bottom: 25px;
      color: var(--primary-color);
    }

    h3 {
      font-size: 18px;
      font-weight: 600;
      color: var(--primary-color);
      margin-top: 20px;
      margin-bottom: 15px;
      padding-bottom: 5px;
      border-bottom: 1px solid var(--border-color);
    }

    .operation-group {
      margin-bottom: 25px;
      border: 1px solid #f0f0f0;
      padding: 15px;
      border-radius: 8px;
    }

    .form-row {
      display: flex;
      gap: 10px;
      align-items: center;
    }

    input[type="number"],
    input[type="submit"] {
      padding: 10px;
      border-radius: 6px;
      font-size: 14px;
      box-sizing: border-box;
    }

    input[type="number"] {
      flex-grow: 1;
      border: 1px solid var(--border-color);
      background: #fafafa;
      transition: border-color 0.2s, box-shadow 0.2s;
      -moz-appearance: textfield;
    }

    input[type="number"]:focus {
      border-color: var(--secondary-color);
      box-shadow: 0 0 0 3px rgba(51, 51, 51, 0.1);
      outline: none;
    }

    input[type="submit"] {
      width: 120px;
      background: var(--primary-color);
      color: white;
      border: none;
      cursor: pointer;
      font-weight: 600;
      transition: background 0.2s ease;
      flex-shrink: 0;
    }

    input[type="submit"]:hover {
      background: #555;
    }

    .result {
      font-weight: 700;
      margin-top: 10px;
      font-size: 16px;
      padding: 8px 0;
    }
  </style>
</head>

<body>
  <div class="container">

    <div class="card">
      <div class="biodata-header">
        <img src="img/Triyas.jpg" alt="Foto Triyas Prasetya">
        <h2><?= $namaSaya ?></h2>
      </div>

      <div class="info">
        <p><span class="label">Umur</span>: <?= $umurSaya ?> tahun</p>
        <p><span class="label">Tanggal Lahir</span>: <?= $tanggalLahir ?></p>
        <p><span class="label">Sekolah</span>: <?= $sekolah ?></p>
        <p><span class="label">Instagram</span>:
          <a href="https://instagram.com/<?= $instagram ?>">@<?= $instagram ?></a>
        </p>
      </div>
    </div>

    <hr>
    <div class="card calculator-section">
      <h1>Kalkulator</h1>

      <form method="get">

        <div class="operation-group">
          <h3>Pertambahan</h3>
          <div class="form-row">
            <input type="number" name="x1" value="<?= $x1 !== null ? $x1 : '' ?>" placeholder="Masukan Angka">
            <span>+</span>
            <input type="number" name="y1" value="<?= $y1 !== null ? $y1 : '' ?>" placeholder="Masukkan Angka">
            <input type="submit" value="Hitung (+)">
          </div>
          <p class="result">Hasil: <?= $pertambahan ?></p>
        </div>

        <div class="operation-group">
          <h3>Pengurangan</h3>
          <div class="form-row">
            <input type="number" name="x2" value="<?= $x2 != null ? $x2 : '' ?>" placeholder="Masukan Angka">
            <span>-</span>
            <input type="number" name="y2" value="<?= $y2 != null ? $y2 : '' ?>" placeholder="Masukan Angka">
            <input type="submit" value="Hitung (-)">
          </div>
          <p class="result">Hasil: <?= $pengurangan ?></p>
        </div>

        <div class="operation-group">
          <h3>Perkalian</h3>
          <div class="form-row">
            <input type="number" name="x3" value="<?= $x3 != null ? $x3 : '' ?>" placeholder="Masukan Angka">
            <span>×</span>
            <input type="number" name="y3" value="<?= $y3 != null ? $y3 : '' ?>" placeholder="Masukan Angka">
            <input type="submit" value="Hitung (×)">
          </div>
          <p class="result">Hasil: <?= $perkalian ?></p>
        </div>

        <div class="operation-group">
          <h3>Pembagian</h3>
          <div class="form-row">
            <input type="number" step="0.01" name="x4" value="<?= $x4 != null ? $x4 : '' ?>" placeholder="Masukan Angka">
            <span>/</span>
            <input type="number" step="0.01" name="y4" value="<?= $y4 != null ? $y4 : ''       ?>" placeholder="Masukan Angka">
            <input type="submit" value="Hitung (/)">
          </div>
          <p class="result">Hasil: <?= $pembagian ?></p>
        </div>

      </form>
    </div>
  </div>
</body>

</html>
