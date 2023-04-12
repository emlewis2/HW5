package lewis.libby.hw5

// Utilized Examples by Scott Stanchfield

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*

@Composable
fun Ui(
    viewModel: AliensViewModel,
) {
    val startHere = LatLng(38.9073, -77.0365)

    val defaultCameraPosition = CameraPosition.fromLatLngZoom(startHere, 11f)

    val ufos = viewModel.currentAlert.collectAsState(initial = emptyList())

    // Attempted line implementation in Ui as well

//    val lines = viewModel.lines.collectAsState()

//    var linesToDraw: List<List<LatLng>> = mutableListOf()
//
//    lines.value.keys.forEach {
//        val tempList = lines.value.get(it)
//        linesToDraw + tempList
//    }

    var bounds by remember { mutableStateOf(LatLngBounds(startHere, startHere)) }

    ufos.value.forEach {
        bounds = bounds.including(it)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }

    GoogleMapDisplay(
        bounds = bounds,
        onSetLines = viewModel::setLines,
        ufos = ufos.value,
        onMapLoaded = {
            viewModel.startAlienReporting()
        },
        cameraPositionState = cameraPositionState,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun GoogleMapDisplay(
    bounds: LatLngBounds,
    onSetLines: () -> List<List<LatLng>>,
    ufos: List<LatLng>,
    onMapLoaded: () -> Unit,
    cameraPositionState: CameraPositionState,
    modifier: Modifier
) {
    with(LocalDensity.current) {
        val boundsPadding = 48.dp.toPx()

        val context = LocalContext.current

        // Attempting to set lines via function in viewModel
        val lines = onSetLines()

        // Changing camera position to include new bounds with new UFO sightings
        LaunchedEffect(ufos) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    boundsPadding.toInt()
                ), 200
            )
        }

        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapLoaded = onMapLoaded,
            modifier = modifier,
        ) {
            // Attempting to draw lines based on each list of LatLng positions for each ship
            lines.forEach{
                Polyline(points = it)
            }
            // Marking each current UFO position
            ufos.forEach {
                Marker(
                    state = MarkerState(it),
                    icon = context.loadBitmapDescriptor(
                        R.drawable.ic_ufo_flying
                    )
                )
            }
        }
    }
}