package lewis.libby.hw5

// Utilized Examples by Scott Stanchfield

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class AliensViewModel: ViewModel() {

    private val scope = viewModelScope

    private val alienAlerter = AlienAlerter(scope)

    private val _lines = MutableStateFlow(LinkedHashMap<Int, List<LatLng>>())

    val lines = _lines

    // Attempting to set a list of lines to draw based on _lines
    // Tried using linesToDraw = linesToDraw + tempList, but ran into nullable error
    fun setLines(): List<List<LatLng>> {
        val linesToDraw: List<List<LatLng>> = mutableListOf()
        _lines.value.keys.forEach {
            val tempList = lines.value.get(it)
            linesToDraw + tempList
        }
        return linesToDraw
    }

    // Using map to convert AlienAlert to a list of LatLng values to display in the Ui
    val currentAlert = alienAlerter.alerts.map { it.alertList.map { LatLng(it.lat, it.lon) } }

    // Creating dictionary of ship number to locations
    val lineSetter = alienAlerter.alerts.map {
        it.alertList.map {
            val index = it.ship
            if (_lines.value.containsKey(index)) {
                val temp = _lines.value.get(index)!!.toMutableList()
                val temp2 = temp + LatLng(it.lat, it.lon)
                _lines.value.set(index, temp2)
            } else {
                var temp = emptyList<LatLng>()
                temp += LatLng(it.lat, it.lon)
                _lines.value.set(index, temp)
            }
        }
    }

    // Function to start reporting
    fun startAlienReporting() {
        alienAlerter.startReporting()
    }

}