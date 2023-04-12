package lewis.libby.hw5

import android.util.Log
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
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*
import com.google.maps.android.ktx.model.polylineOptions
import kotlinx.coroutines.flow.collect

@Composable
fun Ui(
    viewModel: AliensViewModel,
) {
    val startHere = LatLng(38.9073, -77.0365)

    val defaultCameraPosition = CameraPosition.fromLatLngZoom(startHere, 11f)

//    var mapLoaded by remember { mutableStateOf(false) }

    val ufos = viewModel.currentAlert.collectAsState(initial = emptyList())

    val lines = viewModel.lines.collectAsState()

//    val onDrawLines = viewModel.setLines()


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

//    val lines = viewModel.lines

    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }

//    var ufoCoordinates = LatLng(38.9073, -77.0365)

//    if (ufos.value.isNotEmpty()) {
//        ufoCoordinates = LatLng(ufos.value[0].lat, ufos.value[0].lon)
//    }

    GoogleMapDisplay(
        bounds = bounds,
        onSetLines = viewModel::setLines,
        ufos = ufos.value,
        onMapLoaded = {
            viewModel.startAlienReporting()
            Log.d("Loaded", "ahhh")
        },
        cameraPositionState = cameraPositionState,
        modifier = Modifier.fillMaxSize(),
    )

    Log.d("Ufos", ufos.value.toString())

//    Log.d("Lines", lines.values.toString())

//    Log.d("ufos", "idk man")
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

//        var bounds = LatLngBounds(startHere, startHere)

//        ufos.forEach {
//            bounds = bounds.including(it)
//        }

        val lines = onSetLines()

        Log.d("Lines", lines.toString())

        LaunchedEffect(ufos) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    boundsPadding.toInt()
                ), 200
            )
        }

//        var ufoIcon by remember { mutableStateOf<BitmapDescriptor?>(null) }

        //    ufoIcon = context.loadBitmapDescriptor(
        //        R.drawable.ic_ufo_flying
        //    )

        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapLoaded = onMapLoaded,
            modifier = modifier,
        ) {
            lines.forEach{
                Polyline(points = it)
            }
            ufos.forEach {
                Marker(
                    state = MarkerState(it),
                    icon = context.loadBitmapDescriptor(
                        R.drawable.ic_ufo_flying
                    )
                )
//                bounds = bounds.including(it)
                Log.d("Bounds", bounds.toString())
            }
//            LaunchedEffect(true) {
//                cameraPositionState.animate(
//                    CameraUpdateFactory.newLatLngBounds(
//                        bounds,
//                        boundsPadding.toInt()
//                    ), 200
//                )
//            }
            //        Marker(
            //            state = MarkerState()
            //        )
            //        MarkerInfoWindowContent(
            //            state = placeState,
            //            title = "Ufo",
            //        )
        }
    }
}