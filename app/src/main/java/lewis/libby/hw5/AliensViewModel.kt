package lewis.libby.hw5

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map

class AliensViewModel: ViewModel() {

    private val scope = viewModelScope

    private val alienAlerter = AlienAlerter(scope)

    val currentAlert = alienAlerter.alerts.map { it.alertList.map { LatLng(it.lat, it.lon) } }

    fun startAlienReporting() {
        alienAlerter.startReporting()
    }

}