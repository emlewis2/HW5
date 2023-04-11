package lewis.libby.hw5

import com.google.gson.annotations.SerializedName

data class UfoPosition(
    @SerializedName("ship")
    var ship: Int,

    @SerializedName("lat")
    var lat: Double,

    @SerializedName("lon")
    var lon: Double
)