<?php
class ContactSection {
    private $title;
    private $description;

    public function __construct($title = "Contact Us", $description = "") {
        $this->title = $title;
        $this->description = $description ?: "Hubungi kami untuk informasi lebih lanjut tentang komunitas, keanggotaan, atau kolaborasi. Tim kami akan dengan senang hati membantu Anda.";
    }

    public function render() {
        return '
        <section id="contact" class="py-16 bg-gray-50">
            <div class="container mx-auto px-6">
                <div class="text-center mb-12">
                    <h2 class="text-3xl md:text-4xl font-bold text-gray-800 mb-4">' . $this->title . '</h2>
                    <div class="w-24 h-1 bg-blue-600 mx-auto mb-6"></div>
                    <p class="text-gray-600 max-w-3xl mx-auto">' . $this->description . '</p>
                </div>

                <div class="grid grid-cols-1 lg:grid-cols-2 gap-12 max-w-6xl mx-auto">
                    <!-- Contact Form -->
                    <div class="bg-white rounded-xl shadow-lg p-8">
                        <h3 class="text-2xl font-bold text-gray-800 mb-6">Kirim Pesan</h3>
                        <form>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                                <div>
                                    <label class="block text-gray-700 mb-2" for="name">Nama Lengkap</label>
                                    <input type="text" id="name" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="Nama Anda">
                                </div>
                                <div>
                                    <label class="block text-gray-700 mb-2" for="email">Email</label>
                                    <input type="email" id="email" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="email@contoh.com">
                                </div>
                            </div>
                            <div class="mb-6">
                                <label class="block text-gray-700 mb-2" for="subject">Subjek</label>
                                <input type="text" id="subject" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="Subjek pesan">
                            </div>
                            <div class="mb-6">
                                <label class="block text-gray-700 mb-2" for="message">Pesan</label>
                                <textarea id="message" rows="5" class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" placeholder="Tulis pesan Anda di sini..."></textarea>
                            </div>
                            <button type="submit" class="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-6 rounded-lg transition-colors duration-200">
                                Kirim Pesan
                            </button>
                        </form>
                    </div>

                    <!-- Contact Info & FAQ -->
                    <div>
                        <div class="bg-white rounded-xl shadow-lg p-8 mb-8">
                            <h3 class="text-2xl font-bold text-gray-800 mb-6">Informasi Kontak</h3>
                            <div class="space-y-6">
                                <div class="flex items-start">
                                    <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mr-4 flex-shrink-0">
                                        <svg class="w-5 h-5 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                                            <path fill-rule="evenodd" d="M5.05 4.05a7 7 0 119.9 9.9L10 18.9l-4.95-4.95a7 7 0 010-9.9zM10 11a2 2 0 100-4 2 2 0 000 4z" clip-rule="evenodd"/>
                                        </svg>
                                    </div>
                                    <div>
                                        <h4 class="font-semibold text-gray-800 mb-1">Alamat Kantor</h4>
                                        <p class="text-gray-600">Jl. Rusdi No. 67, Bogor</p>
                                    </div>
                                </div>
                                <div class="flex items-start">
                                    <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mr-4 flex-shrink-0">
                                        <svg class="w-5 h-5 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                                            <path d="M2 3a1 1 0 011-1h2.153a1 1 0 01.986.836l.74 4.435a1 1 0 01-.54 1.06l-1.548.773a11.037 11.037 0 006.105 6.105l.774-1.548a1 1 0 011.059-.54l4.435.74a1 1 0 01.836.986V17a1 1 0 01-1 1h-2C7.82 18 2 12.18 2 5V3z"/>
                                        </svg>
                                    </div>
                                    <div>
                                        <h4 class="font-semibold text-gray-800 mb-1">Telepon</h4>
                                        <p class="text-gray-600">(+62) 1234-5678-9101</p>
                                    </div>
                                </div>
                                <div class="flex items-start">
                                    <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mr-4 flex-shrink-0">
                                        <svg class="w-5 h-5 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                                            <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z"/>
                                            <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z"/>
                                        </svg>
                                    </div>
                                    <div>
                                        <h4 class="font-semibold text-gray-800 mb-1">Email</h4>
                                        <p class="text-gray-600">BAFUCommunity@gmail.com</p>
                                    </div>
                                </div>
                                <div class="flex items-start">
                                    <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mr-4 flex-shrink-0">
                                        <svg class="w-5 h-5 text-blue-600" fill="currentColor" viewBox="0 0 20 20">
                                            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-12a1 1 0 10-2 0v4a1 1 0 00.293.707l2.828 2.829a1 1 0 101.415-1.415L11 9.586V6z" clip-rule="evenodd"/>
                                        </svg>
                                    </div>
                                    <div>
                                        <h4 class="font-semibold text-gray-800 mb-1">Jam Operasional</h4>
                                        <p class="text-gray-600">Senin - Jumat: 08:00 - 17:00</p>
                                        <p class="text-gray-600">Sabtu: 09:00 - 14:00</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="bg-blue-600 rounded-xl p-6 text-white">
                            <h3 class="text-xl font-bold mb-4">Pertanyaan Umum</h3>
                            <div class="space-y-4">
                                <div>
                                    <h4 class="font-semibold mb-1">Bagaimana cara bergabung?</h4>
                                    <p class="text-blue-100 text-sm">Daftar melalui formulir di atas atau kunjungi kantor kami untuk konsultasi langsung.</p>
                                </div>
                                <div>
                                    <h4 class="font-semibold mb-1">Apakah ada trial period?</h4>
                                    <p class="text-blue-100 text-sm">Ya, paket Basic tersedia gratis dengan fitur terbatas untuk mencoba layanan kami.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        ';
    }
}
?>
