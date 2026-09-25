package com.example.model

data class VisualPromptItem(
    val id: String,
    val title: String,
    val category: String, // "Buton", "Kart", "Navigasyon", "İkon"
    val englishPrompt: String,
    val turkishTranslation: String,
    val parameters: String,
    val technicalBreakdown: List<String>,
    val bestEngines: String
)

object PromptLibrary {
    val items = listOf(
        VisualPromptItem(
            id = "liquid_glass_button",
            title = "Yüksek Parlaklıklı Sıvı Cam Buton (Liquid Glass Specular Button)",
            category = "Buton",
            englishPrompt = "A futuristic ultra-high gloss liquid glass interactive UI button, hyper-realistic translucent optical glass material with fluid organic curvature and smooth bevel edges, caustic light refractions through the body, intense white specular highlight sheen along the top rim, subtle chromatic aberration and dispersion along the inner rim, glowing soft cyan and violet subsurface scattering, floating gracefully over a dark obsidian minimalist background, octane render, 8k resolution, ray-tracing reflections, clean UI/UX aesthetic, Apple VisionOS inspired fluid glassmorphism --ar 16:9 --style raw --v 6.0",
            turkishTranslation = "Fütüristik ultra yüksek parlaklıkta interaktif sıvı cam UI butonu, akışkan organik kavisli ve pürüzsüz eğimli kenarlara sahip hiper-gerçekçi yarı saydam optik cam materyal, gövde içinden geçen kostik ışık kırılmaları, üst kenar boyunca yoğun beyaz ayna (specular) parlama çizgisi, iç kenarda hafif kromatik renk kırılması (dispersion), yumuşak camgöbeği ve menekşe renkli yüzey altı ışık saçılımı (subsurface scattering), koyu obsidyen minimalist zemin üzerinde havada duran, 8k çözünürlükte octane render, ışın izleme (ray-tracing) yansımaları, temiz UI/UX estetiği.",
            parameters = "--ar 16:9 --style raw --v 6.0 --q 2 --s 250",
            technicalBreakdown = listOf(
                "Yüksek Parlaklık (Specular Highlight): Üst kenardan 45° açıyla vuran ve cam yüzeyinde keskin beyaz parlama üreten 'intense white specular highlight sheen' anahtar ifadesi.",
                "Gerçekçi Şeffaflık (Translucency): Mat plastik etkisini engelleyen 'optical glass material, caustic light refractions, subsurface scattering' yapısı.",
                "Pürüzsüz Akışkan Kenarlar (Fluid Bevel): Keskin hatları eritip akışkan cıva-cam hissi veren 'fluid organic curvature, smooth bevel edges'.",
                "Işık Kırılmaları (Prismatic Refraction): Doğal cam prizma etkisini oluşturan 'subtle chromatic aberration along the inner rim'."
            ),
            bestEngines = "Midjourney v6.0, Flux 1.1 Pro, DALL-E 3, Stable Diffusion XL"
        ),
        VisualPromptItem(
            id = "liquid_glass_card",
            title = "Prizmatik Sıvı Cam Bilgi Kartı (Refractive Liquid Glass Card)",
            category = "Kart",
            englishPrompt = "Modern luxury liquid glass UI card widget, layered translucent frosted crystal glass with thick curved molten edges, realistic optical refraction bending soft colorful background lights behind it, dual-layer specular gloss reflections, micro-etched luminous border with subtle cyan-to-magenta prismatic refraction, elegant floating shadow with ambient occlusion, pristine transparency without noise, clean digital product design mockup, 3D volumetric glass render, 8k cinematic lighting --ar 4:3 --style raw --v 6.0",
            turkishTranslation = "Modern lüks sıvı cam UI kart bileşeni, kalın kavisli erimiş kenarlara sahip çok katmanlı yarı saydam buzlu kristal cam, arka plandaki renkli yumuşak ışıkları optik olarak büken gerçekçi ışık kırılması, çift katmanlı parlak cam yansıması, hafif camgöbeğinden macentaya prizmatik kırılmalı mikro ışıldayan kenarlık, ortam karartmalı zarif gölge, gürültüsüz berrak şeffaflık, 3D hacimsel cam render, 8k sinematik ışıklandırma.",
            parameters = "--ar 4:3 --style raw --v 6.0 --s 200",
            technicalBreakdown = listOf(
                "Işık Bükülmesi (Refraction Bending): 'optical refraction bending soft colorful background lights' ifadesi, arka planın cam arkasından gerçekçi biçimde kırılmasını sağlar.",
                "Çift Katmanlı Yansıma (Dual-layer Specular): Camın hem ön yüzeyinden hem de arka iç çeperinden gelen çift yansıma.",
                "Akışkan Kalın Kenar (Molten Curved Edges): Erimiş sıvı camın yüzey gerilimini yansıtan kavisler.",
                "Berrak Buzlu Doku: 'pristine transparency without noise' yapay zeka çamurlanmasını ve kirliliği engeller."
            ),
            bestEngines = "Midjourney v6.0, Flux.1 Schnell, DALL-E 3"
        ),
        VisualPromptItem(
            id = "liquid_glass_dock",
            title = "Yüzen Sıvı Cam Navigasyon Adası (Floating Fluid Glass Dock)",
            category = "Navigasyon",
            englishPrompt = "Floating horizontal liquid glass pill dock navigation bar, ultra-sleek seamless molten glass texture, liquid mercury meets frosted optical quartz, soft ambient backlighting, glowing active state indicator with inner refraction droplet, glossy curved bevel with subtle prism rainbow highlights, centered on dark void space with soft colorful blurred background spheres, studio lighting, hyper-detailed UI element, 8k --ar 16:9 --v 6.0",
            turkishTranslation = "Havada yüzen yatay sıvı cam hap navigasyon çubuğu, dikişsiz erimiş cam dokusu, sıvı cıva ve buzlu optik kuvars birleşimi, yumuşak ambiyans arkadan aydınlatması, iç kırılmalı damlacık formunda aktif durum göstergesi, gökkuşağı prizma pırıltılı parlak kavisli kenarlık, stüdyo ışıklandırması, 8k.",
            parameters = "--ar 16:9 --style raw --v 6.0",
            technicalBreakdown = listOf(
                "Yüzey Gerilimi Hissi: 'molten glass texture, liquid mercury meets frosted optical quartz'.",
                "İç Işıma (Internal Glow): Aktif elemanların cam içinde sıvılaşan damlalar gibi parlaması.",
                "Prizmatik Gökkuşağı Kenarı: İnce Fresnel kenar yansıması."
            ),
            bestEngines = "Midjourney v6.0, Flux 1.1 Pro"
        ),
        VisualPromptItem(
            id = "liquid_glass_icon_orb",
            title = "3D Sıvı Cam Küre & Buton İkonu (Molten Glass Sphere Widget)",
            category = "İkon",
            englishPrompt = "A glowing 3D molten liquid glass orb button, smooth fluid surface with shifting viscous curves, internal caustics emitting soft neon teal and lavender light, high-gloss specular glare reflecting an unseen softbox studio light, clear refraction showing distortion inside the core, crystal clear purity, isolated on deep navy black background, octane render, unreal engine 5 path tracing, 8k --ar 1:1 --v 6.0",
            turkishTranslation = "Işıldayan 3D erimiş sıvı cam küre butonu, viskoz kavislerle dalgalanan pürüzsüz sıvı yüzey, gövde içinden yumuşak neon camgöbeği ve lavanta ışığı yayan iç kostikler, stüdyo softbox ışığını yansıtan yüksek ayna parlaklığı, çekirdeğin içindeki bükülmeyi gösteren berrak kırılma, koyu lacivert siyah zemin üzerinde izole, 8k.",
            parameters = "--ar 1:1 --style raw --v 6.0",
            technicalBreakdown = listOf(
                "Kostik Işık (Caustics): Sıvı camın alt yüzeylere odakladığı dalgalı ışık desenleri.",
                "Stüdyo Yansıması: 'softbox studio light reflection' camın gerçekçilik algısını katlar."
            ),
            bestEngines = "Midjourney v6.0, DALL-E 3, Stable Diffusion"
        )
    )

    // Negative prompt checklist to prevent common AI generation artifacts
    val negativePromptRecommended = "low quality, blurry, plastic, noisy, grainy, opaque, flat, 2D vector, cartoon, bad edge, jagged, oversaturated, deformed, cracked glass, watermark, signature"
}
