package lewis.libby.hw5

import com.google.gson.annotations.SerializedName

data class UfoPosition(
    @SerializedName("ship")
    var ship: Int,

    @SerializedName("lat")
    var lat: Double,

    @SerializedName("lon")
    var lon: Double

//    @SerializedName("id")
//    var employeeId: String?,
//
//    @SerializedName("employee_name")
//    var employeeName: String?,
//
//    @SerializedName("employee_salary")
//    var employeeSalary: String?,
//
//    @SerializedName("employee_age")
//    var employeeAge: String?
) {
//    override fun toString(): String {
//        return "Data [ship=${this.ship}, lat=${this.lat}, lon=${this.lon}]"
//    }
}