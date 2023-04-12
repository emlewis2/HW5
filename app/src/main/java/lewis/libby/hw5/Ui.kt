package lewis.libby.hw5

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.maps.android.compose.*
import kotlinx.coroutines.flow.collect

@Composable
fun Ui(
    viewModel: AliensViewModel,
) {
    val startHere = LatLng(38.9073, -77.0365)

    val defaultCameraPosition = CameraPosition.fromLatLngZoom(startHere, 11f)

//    var mapLoaded by remember { mutableStateOf(false) }

    val ufos = viewModel.currentAlert.collectAsState(initial = emptyList())

    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }

//    var ufoCoordinates = LatLng(38.9073, -77.0365)

//    if (ufos.value.isNotEmpty()) {
//        ufoCoordinates = LatLng(ufos.value[0].lat, ufos.value[0].lon)
//    }

    GoogleMapDisplay(
        ufos = ufos.value,
        onMapLoaded = {
            viewModel.startAlienReporting()
            Log.d("Loaded", "ahhh")
        },
        cameraPositionState = cameraPositionState,
        modifier = Modifier.fillMaxSize(),
    )

    Log.d("Ufos", ufos.value.toString())

//    Log.d("ufos", "idk man")
}

@Composable
fun GoogleMapDisplay(
    ufos: List<LatLng>,
    onMapLoaded: () -> Unit,
    cameraPositionState: CameraPositionState,
    modifier: Modifier
) {
    var mapLoaded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var ufoIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

//    ufoIcon = context.loadBitmapDescriptor(
//        R.drawable.ic_ufo_flying
//    )

    GoogleMap(
        cameraPositionState = cameraPositionState,
        onMapLoaded = onMapLoaded,
        modifier = modifier,
    ) {
        ufos.forEach {
            Marker(
                state = MarkerState(it),
                icon = context.loadBitmapDescriptor(
                    R.drawable.ic_ufo_flying
                )
            )
        }
//        Marker(
//            state = MarkerState()
//        )
//        MarkerInfoWindowContent(
//            state = placeState,
//            title = "Ufo",
//        )
    }
}