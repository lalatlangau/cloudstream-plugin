package com.layarkaca21

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.loadExtractor
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class LayarKaca21 : MainAPI() {

    override var mainUrl = "https://tv7.lk21official.cc"
    override var name = "LayarKaca21"
    override val hasMainPage = true
    override var lang = "id"
    override val supportedTypes = setOf(
        TvType.Movie, 
        TvType.TvSeries, 
        TvType.AsianDrama,
        TvType.Anime
    )
    
    // Halaman utama
    override val mainPage = mainPageOf(
        "/movies" to "Film Terbaru",
        "/tv-series" to "Serial TV",
        "/anime" to "Anime",
        "/drama" to "Drama Asia"
    )
    
    // Method untuk mendapatkan halaman utama
    override suspend fun getMainPage(
        page: Int, 
        request: MainPageRequest
    ): HomePageResponse {
        val url = request.data.format(page)
        val document = app.get(url).document
        
        val isHomePage = page == 1
        
        val items = document.select("div.item, article.post, div.movie-item").mapNotNull { element ->
            parseItem(element, isHomePage)
        }
        
        return newHomePageResponse(
            list = HomePageList(
                name = request.name,
                list = items,
                isHorizontalImages = false
            ),
            hasNext = true
        )
    }
    
    // Parsing item
    private fun parseItem(element: Element, isHomePage: Boolean): SearchResponse? {
        try {
            val titleElement = element.selectFirst("h2, h3, .title, .name")
            val linkElement = element.selectFirst("a[href]")
            val imgElement = element.selectFirst("img[src]")
            
            if (titleElement == null || linkElement == null) return null
            
            val title = titleElement.text()
            val href = linkElement.attr("href")
            val posterUrl = imgElement?.attr("src") ?: imgElement?.attr("data-src") ?: ""
            
            // Coba deteksi type dari URL atau class
            val type = when {
                "/movie/" in href -> TvType.Movie
                "/tv/" in href || "/series/" in href -> TvType.TvSeries
                "/anime/" in href -> TvType.Anime
                "/drama/" in href -> TvType.AsianDrama
                else -> TvType.TvSeries
            }
            
            return newMovieSearchResponse(
                title = title,
                url = href,
                type = type
            ) {
                this.posterUrl = posterUrl
            }
            
        } catch (e: Exception) {
            return null
        }
    }
    
    // Search function
    override suspend fun search(query: String): List<SearchResponse> {
        val searchUrl = "${mainUrl}/search?q=${query}"
        val document = app.get(searchUrl).document
        
        return document.select("div.search-result, article.post").mapNotNull { element ->
            parseItem(element, false)
        }
    }
    
    // Load details (movie/series)
    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document
        
        val title = document.selectFirst("h1.title, h1.entry-title")?.text() ?: "Unknown"
        
        // Deteksi type
        val type = when {
            "/movie/" in url -> TvType.Movie
            "/tv/" in url || "/series/" in url -> TvType.TvSeries
            "/anime/" in url -> TvType.Anime
            "/drama/" in url -> TvType.AsianDrama
            else -> TvType.TvSeries
        }
        
        val description = document.selectFirst("div.description, div.sinopsis, div.plot")
            ?.text() ?: ""
        
        val poster = document.selectFirst("img.poster, div.poster img")?.attr("src") ?: ""
        
        val yearMatch = Regex("(\\d{4})").find(title)
        val year = yearMatch?.groupValues?.get(1)?.toIntOrNull()
        
        # Episode handling
        val episodes = mutableListOf<Episode>()
        
        if (type == TvType.TvSeries || type == TvType.Anime || type == TvType.AsianDrama) {
            # Parse episode list
            document.select("div.episode-list a, li.episode a").forEach { episodeElement ->
                val epTitle = episodeElement.text().trim()
                val epUrl = episodeElement.attr("href")
                val epNumberMatch = Regex("Episode\\s*(\\d+)").find(epTitle)
                val epNumber = epNumberMatch?.groupValues?.get(1)?.toIntOrNull() ?: 1
                
                episodes.add(
                    Episode(
                        data = epUrl,
                        name = epTitle,
                        episode = epNumber
                    )
                )
            }
        }
        
        # Create response
        return when (type) {
            TvType.Movie -> newMovieLoadResponse(
                title = title,
                url = url,
                type = type
            ) {
                this.posterUrl = poster
                this.plot = description
                this.year = year
                this.recommendations = mutableListOf()
            }
            
            else -> newTvSeriesLoadResponse(
                title = title,
                url = url,
                type = type,
                episodes = episodes
            ) {
                this.posterUrl = poster
                this.plot = description
                this.year = year
                this.recommendations = mutableListOf()
            }
        }
    }
    
    # Load video links
    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val document = app.get(data).document
        
        # Cari semua player containers
        val players = document.select("div.player, iframe[src], video source[src]")
        
        players.forEach { player ->
            val iframeSrc = player.attr("src")
            val videoSrc = player.attr("data-src") ?: player.selectFirst("source")?.attr("src")
            
            if (!iframeSrc.isNullOrEmpty()) {
                # Jika iframe, gunakan extractor
                loadExtractor(iframeSrc, "${mainUrl}/", subtitleCallback, callback)
            } else if (!videoSrc.isNullOrEmpty()) {
                # Jika direct video link
                callback.invoke(
                    ExtractorLink(
                        name = name,
                        source = name,
                        url = videoSrc,
                        quality = Qualities.P720.value,
                        isM3u8 = videoSrc.endsWith(".m3u8")
                    )
                )
            }
        }
        
        return players.isNotEmpty()
    }
}
