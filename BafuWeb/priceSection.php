<?php
class PriceSection {
    private $title;
    private $description;
    private $plans;

    public function __construct($title = "Membership Plans", $description = "", $plans = []) {
        $this->title = $title;
        $this->description = $description ?: "Pilih paket keanggotaan yang sesuai dengan kebutuhan Anda. Setiap paket memberikan akses penuh ke komunitas dengan manfaat yang berbeda.";
        $this->plans = $plans ?: [
            [
                "name" => "Basic",
                "price" => "Free",
                "period" => "",
                "features" => ["Akses forum komunitas", "Event publik bulanan", "Materi pembelajaran dasar", "Dukungan komunitas"],
                "highlighted" => false,
                "color" => "gray"
            ],
            [
                "name" => "Pro",
                "price" => "Rp 99.000",
                "period" => "/bulan",
                "features" => ["Semua fitur Basic", "Workshop eksklusif", "Akses mentor 1x/bulan", "Sertifikat partisipasi", "Networking session"],
                "highlighted" => false,
                "color" => "blue"
            ],
            [
                "name" => "Enterprise",
                "price" => "Rp 299.000",
                "period" => "/bulan",
                "features" => ["Semua fitur Pro", "Akses mentor tak terbatas", "Pelatihan khusus tim", "Analytics dashboard", "Prioritas support", "Event customization"],
                "highlighted" => false,
                "color" => "purple"
            ]
        ];
    }

    public function render() {
        return '
        <section id="price" class="py-16 bg-white">
            <div class="container mx-auto px-6">
                <div class="text-center mb-12">
                    <h2 class="text-3xl md:text-4xl font-bold text-gray-800 mb-4">' . $this->title . '</h2>
                    <div class="w-24 h-1 bg-blue-600 mx-auto mb-6"></div>
                    <p class="text-gray-600 max-w-3xl mx-auto">' . $this->description . '</p>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
        ' . $this->renderPlans() . '
                </div>
            </div>
        </section>
        ';
    }

    private function renderPlans() {
        $html = '';
        foreach ($this->plans as $plan) {
            $colorClass = $plan['color'] == 'blue' ? 'blue' : ($plan['color'] == 'purple' ? 'purple' : 'gray');
            $bgColor = $plan['highlighted'] ?
                ($colorClass == 'blue' ? 'bg-blue-600' : ($colorClass == 'purple' ? 'bg-purple-600' : 'bg-gray-600')) :
                'bg-white';
            $textColor = $plan['highlighted'] ? 'text-white' : 'text-gray-800';
            $borderColor = $plan['highlighted'] ?
                ($colorClass == 'blue' ? 'border-blue-600' : ($colorClass == 'purple' ? 'border-purple-600' : 'border-gray-600')) :
                'border-gray-200';
            $buttonColor = $plan['highlighted'] ?
                'bg-white hover:bg-gray-100 text-blue-600' : ($colorClass == 'blue' ? 'bg-blue-600 hover:bg-blue-700 text-white' : ($colorClass == 'purple' ? 'bg-purple-600 hover:bg-purple-700 text-white' :
                        'bg-gray-800 hover:bg-gray-900 text-white'));

            $html .= '
            <div class="flex flex-col h-full">
                <div class="rounded-xl shadow-lg overflow-hidden border ' . $borderColor . ' flex flex-col h-full">
                    <div class="' . $bgColor . ' p-6 text-center">
                        <h3 class="text-2xl font-bold ' . $textColor . ' mb-2">' . $plan['name'] . '</h3>
                        <div class="' . $textColor . ' mb-1">
                            <span class="text-4xl font-bold">' . $plan['price'] . '</span>
                            <span class="text-lg">' . $plan['period'] . '</span>
                        </div>
                    </div>
                    <div class="p-6 bg-white flex flex-col flex-grow">
                        <ul class="space-y-4 mb-6">
            ';

            foreach ($plan['features'] as $feature) {
                $html .= '
                            <li class="flex items-start">
                                <svg class="w-5 h-5 text-green-500 mr-3 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                                </svg>
                                <span class="text-gray-700">' . $feature . '</span>
                            </li>
                ';
            }

            $html .= '
                        </ul>
                        <div class="mt-auto pt-4">
                            <a href="#contact" class="block text-center ' . $buttonColor . ' font-medium py-3 px-6 rounded-lg transition-colors duration-200">
                                ' . ($plan['name'] == 'Basic' ? 'Daftar Gratis' : 'Pilih Paket') . '
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            ';
        }
        return $html;
    }
}
