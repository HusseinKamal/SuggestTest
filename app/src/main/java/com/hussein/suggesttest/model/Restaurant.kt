package com.hussein.suggesttest.model

import com.google.gson.annotations.SerializedName

class Restaurant{
    @SerializedName("error")
    var error: String = ""
        get() = field
        set(value) { field = value}

    @SerializedName("name")
    var name:String = ""
        get() = field
        set(value) { field = value}

    @SerializedName("id")
    var id: String = ""
        get() = field
        set(value) { field = value}

    @SerializedName("link")
    var link: String = ""
        get() = field
        set(value) { field = value}

    @SerializedName("cat")
    var cat: String = ""
        get() = field
        set(value) { field = value}

    @SerializedName("catId")
    var catId: String = ""
        get() = field
        set(value) { field = value}

    @SerializedName("rating")
    var rating: Float = 0.0f
        get() = field
        set(value) { field = value}

    @SerializedName("lat")
    var lat: Double =0.0
        get() = field
        set(value) { field = value}

    @SerializedName("lon")
    var lon: Double =0.0
        get() = field
        set(value) { field = value}

    @SerializedName("Ulat")
    var Ulat: Double =0.0
        get() = field
        set(value) { field = value}

    @SerializedName("Ulon")
    var Ulon: Double =0.0
        get() = field
        set(value) { field = value}

    @SerializedName("open")
    var open: String = ""
        get() = field
        set(value) { field = value}

    @SerializedName("image")
    var image: List<String> = ArrayList()
        get() = field
        set(value) { field = value}
}
