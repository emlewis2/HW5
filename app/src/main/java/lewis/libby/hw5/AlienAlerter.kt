package lewis.libby.hw5

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlienAlerter(
    private val scope: CoroutineScope,
) {
    private val _alerts = MutableStateFlow(emptyList<AlienAlert>())

    val alerts: Flow<List<AlienAlert>>
        get() = _alerts

//    private val alienApiService = AlienApiService.create()

//    private fun getAliens(scope: CoroutineScope) {
//        scope.launch
//        val response = alienApiService.getAliens().takeIf { it.isSuccessful }?.body()
////        Log.d("Response in GetAliens", response.toString())
//    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://javadude.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(AlienApiService::class.java)

    var stop = false

    fun startReporting() {
        CoroutineScope(Dispatchers.IO).launch{
            var n = 1
            while(!stop) {
                Log.d("Value of n", n.toString())
                Log.d("Reporting", "yes")
                val response = service.getAliens(n)
                Log.d("Finished report", "yes")

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Log.d("Success", "yes")
                        val items = response.body()
                        var alienAlert = AlienAlert(emptyList())
                        Log.d("Response", items.toString())
                        if (items != null) {
                            for (i in 0 until items.count()) {
                                val ship = items[i].ship
                                Log.d("ID: ", ship.toString())

                                val lat = items[i].lat
                                Log.d("Employee Name: ", lat.toString())

                                val lon = items[i].lon
                                Log.d("Employee Salary: ", lon.toString())

                                alienAlert.alertList = alienAlert.alertList + items[i]

                            }
                            _alerts.value = _alerts.value + alienAlert
                            n += 1
                            delay(500)
                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                            stop = true
//                            Log.d("Stopped", "yes")
                        }
                    } else {
                        stop = true
                        Log.d("Stopped", "yes")
                        Log.d("Total", _alerts.value.lastIndex.toString())
                    }
                }
//            var n = 1
//            while(true) {
//                Log.d("In report", "yes")
//                val response = alienApiService.getAliens()
//                Log.d("Finished report", "no")
//                withContext(Dispatchers.Main) {
//                    if (response.isSuccessful) {
//                        Log.d("Yesss", "yesss")
//                    } else {
//                        Log.e("RETROFIT_ERROR", response.code().toString())
//                    }
//                }
////                val response = alienApiService.getAliens().takeIf { it.isSuccessful }?.body()
////                if(response != null) {
////                    Log.d("Response", response.toString())
////                }
////                if (response == null) {
////                    Log.d("Response", "null")
////                }
////                Log.d("Response", response.toString())
////                if (response == null) {
////                    break
////                } else {
////                    _alerts.value = _alerts.value + response
////                    n += 1
////                    delay(1000)
////                }
            }
            Log.d("Outside", "yes")
        }
    }
}