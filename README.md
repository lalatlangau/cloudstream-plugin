# 🎬 MTS CloudStream Plugins Repository

Repository ini berisi **kumpulan plugin CloudStream 3** untuk streaming film, series, dan drama Asia.

---

## 📦 Available Plugins

| Plugin | Deskripsi | Status | Language |
|--------|-----------|--------|----------|
| **LayarKaca21** | Streaming film dan drama Asia | ✅ Active | 🇮🇩 Bahasa Indonesia |
| **Gomov** | Koleksi film dan series terkini | ✅ Active | 🇮🇩 Bahasa Indonesia |
| [Plugins lain...] | Coming soon | 🔄 In Development | 🇮🇩 Bahasa Indonesia |

---

## 🚀 Cara Install (CloudStream App)

### Step 1: Buka CloudStream 3

Pastikan aplikasi CloudStream 3 sudah terinstall di device anda.

### Step 2: Pergi ke Extensions

1. Buka **CloudStream 3**
2. Klik **⚙️ Settings** (gear icon)
3. Pilih **Extensions** atau **Repository**

### Step 3: Tambah Repository

1. Klik **➕ Add Repository** atau **Add Custom Repository**
2. Copy-paste URL repository:
   ```
   https://github.com/lalatlangau/cloudstream-plugins/raw/main
   ```
3. Tekan **OK** atau **Add**

### Step 4: Install Plugin

1. Refresh repository (jika perlu)
2. Cari plugin yang ingin diinstall
3. Klik **Install**
4. Tunggu proses selesai
5. Plugin siap digunakan!

---

## 📋 Repository Information

- **Repository Name**: MTS CloudStream Plugins
- **Repository URL**: `https://github.com/lalatlangau/cloudstream-plugins/raw/main`
- **Language**: Bahasa Indonesia & English
- **Update Frequency**: Regular updates
- **License**: MIT

---

## 🔧 For Developers

### Clone Repository

```bash
git clone https://github.com/lalatlangau/cloudstream-plugins.git
cd cloudstream-plugins
```

### Struktur Folder

```
cloudstream-plugins/
├── README.md                 # File ini
├── repository.json          # Konfigurasi repository
├── LayarKaca21/             # Plugin 1
│   ├── build.gradle.kts
│   ├── README.md
│   └── src/
│       └── main/
│           ├── kotlin/
│           ├── resources/
│           └── AndroidManifest.xml
├── Gomov/                   # Plugin 2
│   ├── build.gradle.kts
│   ├── README.md
│   └── src/
└── [Plugins lain...]
```

### Generate Plugin Baru

Kami menyediakan script untuk generate plugin structure secara otomatis:

```bash
cd project001
python generate_plugin.py --name "PluginName" --url "https://website.com" --authors "Your Name" --desc "Deskripsi plugin"
```

**Parameters:**
- `--name` *(required)* - Nama plugin
- `--url` - Website URL plugin
- `--authors` - Nama author(s)
- `--desc` - Deskripsi singkat
- `--lang` - Bahasa (default: id)
- `--output` - Output folder (default: .)
- `--create-batch` - Generate batch script

---

## 📚 Plugin Development Guide

Setiap plugin harus mengandung:

### 1. `build.gradle.kts` - Plugin Configuration

```kotlin
version = 1

cloudstream {
    description = "Plugin description"
    language = "id"
    authors = listOf("Your Name")
    
    status = 1 // 0: Down, 1: Ok, 2: Slow, 3: Beta
    tvTypes = listOf(
        "Movie",
        "TvSeries",
        "AsianDrama",
        "Anime"
    )
    
    iconUrl = "https://website.com/icon.png"
}
```

### 2. `src/main/kotlin/com/packagename/PluginName.kt` - Main API

```kotlin
package com.packagename

import com.lagradost.cloudstream3.*

class PluginName : MainAPI() {
    override var mainUrl = "https://website.com"
    override var name = "PluginName"
    override val hasMainPage = true
    
    // Implementasi methods
    override suspend fun getMainPage(...) { }
    override suspend fun search(...) { }
    override suspend fun load(...) { }
}
```

### 3. `src/main/kotlin/com/packagename/Extractors.kt` - Video Extractors

```kotlin
package com.packagename

import com.lagradost.cloudstream3.utils.ExtractorApi

class PluginNameExtractor : ExtractorApi() {
    override val name = "PluginName"
    override val mainUrl = "https://website.com"
    
    override suspend fun getUrl(...) {
        // Implementasi ekstraksi video
    }
}
```

### 4. `README.md` - Plugin Documentation

```markdown
# PluginName

Deskripsi plugin...

## Features
- Fitur 1
- Fitur 2
- Fitur 3

## Status
- ✅ Streaming: Supported
- ✅ Search: Supported
- ✅ Subtitles: Supported

## Author
- Your Name
```

---

## 🐛 Troubleshooting

### Plugin Tidak Muncul

1. **Periksa repository URL** - Pastikan URL benar tanpa typo
2. **Refresh repository** - Pull down to refresh di CloudStream
3. **Periksa `repository.json`** - File harus valid JSON
4. **Clear cache** - Settings > Clear cache > Restart app

### Installation Failed

1. **Check internet connection** - Pastikan koneksi stabil
2. **Try again** - Wait beberapa saat, try install lagi
3. **Update CloudStream** - Pastikan versi terbaru
4. **Check plugin version** - Plugin mungkin incompatible dengan versi CloudStream anda

### Video Not Playing

1. **Update plugin** - Uninstall dan install ulang
2. **Check website** - Website mungkin down atau berubah struktur
3. **Check extractors** - Extractor mungkin perlu update
4. **Enable logging** - Settings > Logs untuk debug

---

## 📞 Support & Contact

- **Issues/Bug Report**: [GitHub Issues](https://github.com/lalatlangau/cloudstream-plugins/issues)
- **Suggestions**: Open discussion di GitHub
- **Collaboration**: Pull requests welcome!

---

## 📜 License

MIT License - Lihat file LICENSE untuk detail

---

## ⭐ Contributing

Kami welcome contributions! 

### Cara Contribute:

1. **Fork** repository ini
2. **Create** branch baru: `git checkout -b feature/plugin-name`
3. **Commit** changes: `git commit -m "Add PluginName"`
4. **Push** ke branch: `git push origin feature/plugin-name`
5. **Open** Pull Request

### Requirement untuk Plugin Baru:

- ✅ Plugin harus functional (minimal search + streaming)
- ✅ Include proper `build.gradle.kts`
- ✅ Include `README.md` dengan dokumentasi
- ✅ Code harus rapi dan well-structured
- ✅ Tidak duplicate existing plugins

---

## 🎉 Getting Started

**Step 1:** Clone/download repository ini
```bash
git clone https://github.com/lalatlangau/cloudstream-plugins.git
```

**Step 2:** Generate plugin baru
```bash
cd project001
python generate_plugin.py --name "MyPlugin" --url "https://mysite.com"
```

**Step 3:** Implement plugin logic
```
Edit src/main/kotlin/com/mypackage/MyPlugin.kt
```

**Step 4:** Test plugin
```
Sideload ke CloudStream atau test via Gradle
```

**Step 5:** Push ke repository
```bash
git add .
git commit -m "Add MyPlugin"
git push origin main
```

---

## 📊 Repository Stats

- **Total Plugins**: 10+
- **Languages**: Bahasa Indonesia 🇮🇩
- **Last Updated**: 2026
- **Active Maintenance**: ✅ Yes

---

## 🔗 Quick Links

- [CloudStream 3 Documentation](https://recloudstream.github.io/)
- [CloudStream GitHub](https://github.com/recloudstream/cloudstream-3)
- [Plugin Development Guide](https://recloudstream.github.io/develop)

---

**Made with ❤️ by MTS**

*Last Updated: 2026-02-01*
