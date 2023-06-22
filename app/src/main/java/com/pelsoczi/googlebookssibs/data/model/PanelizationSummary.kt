package com.pelsoczi.googlebookssibs.data.remote

import com.google.gson.annotations.SerializedName


data class PanelizationSummary(

    @SerializedName("containsEpubBubbles") var containsEpubBubbles: Boolean? = null,
    @SerializedName("containsImageBubbles") var containsImageBubbles: Boolean? = null

)