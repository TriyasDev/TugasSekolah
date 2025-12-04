<?php
// Variabel biodata
$nama = 'Andika Muhamad Arif Cahya';
$umur = 17;
$alamat = 'Desa Tenjolaya';
$sekolah = 'SMK Budi Bakti Ciwidey';
$nik = 7491640637817333;
$ttl = "25 April 1945";

function calculate($expression)
{
  // 1. Pembersihan dan Normalisasi Input
  $expression = trim($expression);

  // Ganti operator kalkulator umum (x, X, :) ke operator PHP (*, /)
  $expression = str_replace(['x', 'X', ':', '÷'], ['*', '*', '/', '/'], $expression);

  // Hapus semua spasi
  $expression = str_replace(' ', '', $expression);

  // 2. Sanitasi Ketat: Hanya izinkan angka, operator PHP (+,-,*,/), dan titik desimal.
  if (!preg_match('/^[0-9+\-*\/.]+$/', $expression)) {
    return "Error: Input tidak valid. Hanya izinkan angka dan operator dasar (+, -, x, :).";
  }

  // 3. Memastikan ekspresi tidak kosong dan tidak berakhir/berawal dengan operator
  if (empty($expression) || preg_match('/^[\+\-\*\/]|[\+\-\*\/]$/', $expression)) {
    return "Error: Ekspresi tidak valid atau kosong.";
  }

  // 4. MENGGUNAKAN eval() setelah sanitasi ketat untuk urutan operasi yang benar
  try {
    $result = eval("return $expression;");

    return is_numeric($result) ? (string)$result : "Error: Perhitungan tidak menghasilkan angka.";
  } catch (ParseError $e) {
    return "Error: Ekspresi sintaks tidak valid.";
  } catch (DivisionByZeroError $e) {
    return "Error: Pembagian dengan nol tidak diizinkan.";
  } catch (Exception $e) {
    return "Error: Terjadi kesalahan saat menghitung.";
  }
}

// Proses form jika ada input
$result = "";
$expression_input = ""; // Variabel untuk menyimpan input asli sebelum hilang

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['expression'])) {
  $expression_input = trim($_POST['expression']);
  $result = calculate($expression_input);
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Biodata Andika</title>

  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: ui-sans-serif, system-ui, sans-serif;
    }

    body {
      background: #f5f6f8;
      color: #222;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 40px 20px;
      gap: 40px;
    }

    .card {
      background: url('img/background.png');
      background-size: cover;
      background-position: center;
      width: 100%;
      max-width: 800px;
      padding: 25px;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.30);
    }


    img {
      border-radius: 10%;
      width: 200px;
      height: 200px;
      object-fit: cover;
      margin-bottom: 50px;
    }

    h1 {
      margin-bottom: 15px;
      font-size: 1.4rem;
    }

    p {
      margin-bottom: 8px;
      line-height: 1.4;
      font-size: 0.95rem;
    }

    .input-box {
      margin-top: 15px;
    }

    input[type="text"] {
      width: 100%;
      padding: 10px 12px;
      border: 1px solid #d0d0d0;
      border-radius: 6px;
      font-size: 1rem;
      outline: none;
      transition: .2s;
    }

    input[type="text"]:focus {
      border-color: #333;
    }

    button {
      margin-top: 12px;
      width: 100%;
      padding: 10px;
      border: none;
      border-radius: 6px;
      font-size: 1rem;
      background: #1a73e8;
      color: white;
      cursor: pointer;
      transition: 0.2s;
    }

    button:hover {
      background: #1669c1;
    }

    .result-text {
      margin-top: 15px;
      font-size: 1.2rem;
      font-weight: bold;
    }

    .biodata-container {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  </style>

</head>

<body>

  <div class="card">
    <h1>Biodata</h1>

    <div class="biodata-container">
      <div class="bio-text">
        <p><b>NIK:</b> <?= $nik ?></p>
        <p><b>TTL:</b> <?= $ttl ?></p>
        <p><b>Nama:</b> <?= $nama ?></p>
        <p><b>Umur:</b> <?= $umur ?></p>
        <p><b>Alamat:</b> <?= $alamat ?></p>
        <p><b>Sekolah:</b> <?= $sekolah ?></p>
      </div>

      <img src="img/Andika.jpeg" alt="">
    </div>
  </div>



  <div class="card">
    <h1>Kalkulator</h1>

    <?php if ($result === ""): ?>

      <p>Masukkan ekspresi matematika (contoh: 5 + 3 x 2 - 1 : 2)</p>

      <form method="post" class="input-box">
        <input type="text" name="expression" placeholder="Masukkan Angka" required>
        <button type="submit">Hitung</button>
      </form>

    <?php else: ?>

      <p><b>Ekspresi:</b> <?= htmlspecialchars($expression_input); ?></p>
      <p class="result-text">Hasil: <?= htmlspecialchars($result); ?></p>

      <form method="get">
        <button type="submit">Hitung Lagi</button>
      </form>

    <?php endif; ?>
  </div>

</body>

</html>
