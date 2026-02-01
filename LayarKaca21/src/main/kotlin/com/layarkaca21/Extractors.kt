package com.layarkaca21

import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.USER_AGENT
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.base64DecodeArray
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import com.lagradost.cloudstream3.utils.newExtractorLink

class LayarKaca21Extractor1 : ExtractorApi() {
    override val name = "LayarKaca21Extractor1"
    override val mainUrl = "https://tv7.lk21official.cc"
    override val requiresReferer = true

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        try {
            val res = app.get(url).text
            
            // TODO: Implementasi ekstraksi URL video
            // Contoh: cari m3u8 atau mp4 URL
            val m3u8Regex = Regex(""file"?:\s*"([^"]+\.m3u8)"")
            val m3u8Match = m3u8Regex.find(res)
            
            if (m3u8Match != null) {
                val videoUrl = m3u8Match.groupValues[1]
                
                callback.invoke(
                    newExtractorLink(
                        name,
                        name,
                        videoUrl
                    ){
                        this.referer = mainUrl
                        this.quality = Qualities.P1080.value
                        this.headers = mapOf(
                            "User-Agent" to USER_AGENT,
                            "Referer" to mainUrl
                        )
                    }
                )
            } else {
                throw Exception("Video URL tidak ditemukan")
            }
            
            // TODO: Ekstraksi subtitle jika ada
            val subtitleRegex = Regex(""file"?:\s*"([^"]+\.(srt|vtt))"")
            val subtitleMatches = subtitleRegex.findAll(res)
            
            subtitleMatches.forEachIndexed { index, match ->
                val subtitleUrl = match.groupValues[1]
                subtitleCallback.invoke(SubtitleFile("Subtitle ${index + 1}", subtitleUrl))
            }
            
        } catch (e: Exception) {
            throw Exception("Gagal mengekstrak video: ${e.message}")
        }
    }
}

// Tambahkan extractor lain jika diperlukan
class LayarKaca21Extractor2 : ExtractorApi() {
    override val name = "LayarKaca21Extractor2"
    override val mainUrl = "https://tv7.lk21official.cc"
    override val requiresReferer = false

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        // Implementasi alternatif
        throw Exception("Extractor ini belum diimplementasi")
    }
}
