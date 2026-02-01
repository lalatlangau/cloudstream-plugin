package com.layarkaca21

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class LayarKaca21Plugin : Plugin() {
    override fun load(context: Context) {
        // Register main API
        registerMainAPI(LayarKaca21())
        
        // Register extractors
        registerExtractorAPI(LayarKaca21Extractor1())
        registerExtractorAPI(LayarKaca21Extractor2())
        
        // Tambahkan extractor lain di sini jika diperlukan
    }
}
