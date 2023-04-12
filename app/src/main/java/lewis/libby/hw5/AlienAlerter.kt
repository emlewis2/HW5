package lewis.libby.hw5

// Utilized Examples by Scott Stanchfield
// As well as article found at: https://johncodeos.com/how-to-parse-json-with-retrofit-converters-using-kotlin/

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlienAlerter(
    private val scope: CoroutineScope
) {
    private val _alerts = MutableStateFlow(AlienAlert(emptyList()))

    val alerts: Flow<AlienAlert>
        get() = _alerts

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://javadude.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(AlienApiService::class.java)

    var stop = false

    fun startReporting() {
        scope.launch{
            var n = 1
            while(!stop) {
                val response = service.getAliens(n)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val items = response.body()
                        val alienAlert = AlienAlert(emptyList())
                        if (items != null) {
                            for (i in 0 until items.count()) {
                                alienAlert.alertList = alienAlert.alertList + items[i]
                            }
                            _alerts.value = alienAlert
                            n += 1
                            delay(1000)
                        } else {
                            Log.e("RETROFIT_ERROR", response.code().toString())
                        }
                    } else {
                        stop = true
                    }
                }
            }
        }
    }
}