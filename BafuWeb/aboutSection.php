<?php
class AboutSection {
    private $title;
    private $description;
    private $features;

    public function __construct($title = "About Our Community", $description = "", $features = []) {
        $this->title = $title;
        $this->description = $description ?: "Kami adalah komunitas yang berdedikasi untuk menghubungkan individu-individu kreatif, profesional, dan penggiat teknologi. Dengan semangat kolaborasi dan inovasi, kami membangun ekosistem yang mendukung perkembangan setiap anggota.";
        $this->features = $features ?: [
            ["Komunitas Aktif", "Lebih dari 5000 anggota aktif yang saling berbagi pengetahuan dan pengalaman.", "M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"],
            ["Networking", "Jaringan luas dengan profesional dari berbagai industri dan bidang keahlian.", "M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"],
            ["Workshop Rutin", "Acara dan workshop berkala untuk meningkatkan keterampilan dan pengetahuan.", "M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"],
            ["Dukungan Penuh", "Dukungan dari mentor berpengalaman untuk membantu mencapai tujuan Anda.", "M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"]
        ];
    }

    public function render() {
        return '
        <section id="about" class="py-16 bg-gray-50">
            <div class="container mx-auto px-6">
                <div class="text-center mb-12">
                    <h2 class="text-3xl md:text-4xl font-bold text-gray-800 mb-4">' . $this->title . '</h2>
                    <div class="w-24 h-1 bg-blue-600 mx-auto mb-6"></div>
                    <p class="text-gray-600 max-w-3xl mx-auto">' . $this->description . '</p>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
        ' . $this->renderFeatures() . '
                </div>

                <div class="mt-16 bg-white rounded-xl shadow-lg p-8">
                    <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 items-center">
                        <div>
                            <h3 class="text-2xl font-bold text-gray-800 mb-4">Mengapa Bergabung dengan Kami?</h3>
                            <p class="text-gray-600 mb-4">Komunitas kami dirancang untuk memberikan nilai tambah bagi setiap anggota melalui kolaborasi, pembelajaran, dan kesempatan networking yang luas.</p>
                            <ul class="space-y-3">
                                <li class="flex items-center">
                                    <svg class="w-5 h-5 text-green-500 mr-2" fill="currentColor" viewBox="0 0 20 20">
                                        <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                                    </svg>
                                    <span>Akses ke workshop eksklusif dan materi pembelajaran</span>
                                </li>
                                <li class="flex items-center">
                                    <svg class="w-5 h-5 text-green-500 mr-2" fill="currentColor" viewBox="0 0 20 20">
                                        <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                                    </svg>
                                    <span>Jaringan profesional dari berbagai industri</span>
                                </li>
                                <li class="flex items-center">
                                    <svg class="w-5 h-5 text-green-500 mr-2" fill="currentColor" viewBox="0 0 20 20">
                                        <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                                    </svg>
                                    <span>Peluang karir dan kolaborasi bisnis</span>
                                </li>
                            </ul>
                        </div>
                        <div class="bg-blue-50 rounded-lg p-6">
                            <div class="text-center">
                                <div class="text-4xl font-bold text-blue-700 mb-2">5000+</div>
                                <div class="text-gray-700 font-medium mb-4">Anggota Aktif</div>
                                <div class="text-4xl font-bold text-blue-700 mb-2">120+</div>
                                <div class="text-gray-700 font-medium mb-4">Workshop Dilaksanakan</div>
                                <div class="text-4xl font-bold text-blue-700 mb-2">85%</div>
                                <div class="text-gray-700 font-medium">Tingkat Kepuasan Anggota</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        ';
    }

    private function renderFeatures() {
        $html = '';
        foreach ($this->features as $feature) {
            $html .= '
            <div class="bg-white rounded-xl shadow-md p-6 hover:shadow-lg transition-shadow duration-300">
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center mb-4">
                    <svg class="w-6 h-6 text-blue-600" fill="currentColor" viewBox="0 0 24 24">
                        <path d="' . $feature[2] . '"/>
                    </svg>
                </div>
                <h3 class="text-xl font-semibold text-gray-800 mb-2">' . $feature[0] . '</h3>
                <p class="text-gray-600">' . $feature[1] . '</p>
            </div>
            ';
        }
        return $html;
    }
}
?>
