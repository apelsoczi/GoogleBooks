package com.pelsoczi.googlebookssibs.data.remote.model

import com.google.gson.annotations.SerializedName


data class VolumeInfo(

    @SerializedName("title") var title: String = "",
    @SerializedName("authors") var authors: ArrayList<String> = arrayListOf(),
    @SerializedName("publishedDate") var publishedDate: String = "",
    @SerializedName("publisher") var publisher: String = "",
    @SerializedName("description") var description: String = "",
//    @SerializedName("industryIdentifiers") var industryIdentifiers: ArrayList<IndustryIdentifiers> = arrayListOf(),
//    @SerializedName("readingModes") var readingModes: ReadingModes? = ReadingModes(),
    @SerializedName("pageCount") var pageCount: Int = 0,
//    @SerializedName("printType") var printType: String = "",
//    @SerializedName("categories") var categories: ArrayList<String> = arrayListOf(),
    @SerializedName("averageRating") var averageRating: Double = 0.0,
    @SerializedName("ratingsCount") var ratingsCount: Int = 0,
//    @SerializedName("maturityRating") var maturityRating: String = "",
//    @SerializedName("allowAnonLogging") var allowAnonLogging: Boolean? = null,
//    @SerializedName("contentVersion") var contentVersion: String = "",
//    @SerializedName("panelizationSummary") var panelizationSummary: PanelizationSummary? = PanelizationSummary(),
    @SerializedName("imageLinks") var imageLinks: ImageLinks = ImageLinks(),
//    @SerializedName("language") var language: String = "",
//    @SerializedName("previewLink") var previewLink: String = "",
//    @SerializedName("infoLink") var infoLink: String = "",
//    @SerializedName("canonicalVolumeLink") var canonicalVolumeLink: String = ""

)