package lewis.libby.hw5

import com.google.gson.annotations.SerializedName

data class AlienAlert(
    var alertList: List<UfoPosition>
)