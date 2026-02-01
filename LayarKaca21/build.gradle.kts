// use an integer for version numbers
version = 1

cloudstream {
    // All of these properties are optional, you can safely remove them
    description = "Plugin untuk LayarKaca21"
    language = "id"
    authors = listOf("YourName")

    /**
     * Status int as the following:
     * 0: Down
     * 1: Ok
     * 2: Slow
     * 3: Beta only
     * */
    status = 1 // will be 3 if unspecified
    tvTypes = listOf(
        "AsianDrama",
        "TvSeries",
        "Movie",
    )

    iconUrl = "https://tv7.lk21official.cc/favicon.ico"
}
