package lewis.libby.hw5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import lewis.libby.hw5.ui.theme.HW5Theme
import lewis.libby.hw5.AlienAlerter

private val startHere = LatLng(38.9073, -77.0365)

private val defaultCameraPosition = CameraPosition.fromLatLngZoom(startHere, 11f)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Log.d("Starting:", "---------------------------------------")
                    val cameraPositionState = rememberCameraPositionState {
                        position = defaultCameraPosition
                    }

                    GoogleMapDisplay(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier.fillMaxSize(),
                    )

                    val scope = rememberCoroutineScope()

                    val alienAlerter = AlienAlerter(scope)

                    alienAlerter.startReporting()
                    Log.d("What", "Oops")

                }
            }
        }
    }
}

@Composable
fun GoogleMapDisplay(
    cameraPositionState: CameraPositionState,
    modifier: Modifier
) {

//    LaunchedEffect(true) {
//        val start =
//            LatLng(38.9073, -77.0365)
//        cameraPositionState.animate(
//            CameraUpdateFactory.newLatLngZoom(
//                start,
//                16f
//            ), 1000
//        )
//    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        modifier = modifier,
    ) {
        // No content yet
    }
}