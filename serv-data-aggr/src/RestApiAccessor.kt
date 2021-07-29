import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class RestApiAccessor : Thread("RestApiAccessor") {

    init {
        isDaemon = true
    }

    @Volatile // propagate updates through core-specific caches
    var data: JSONObject? = null

    override fun run() {
        val client = OkHttpClient()

        while (true) {
            sleep(4000)

            val req = Request.Builder()
                .url("https://rvj6rnbpxj.execute-api.eu-central-1.amazonaws.com/prod/live-data")
                .get()
                .build()

            val callRes = try {
                client.newCall(req).execute()
            } catch (ex: IOException) {
                System.err.println("Failed to contact REST API, retrying... Error message was: ${ex.message}")
                continue
            }.use { resp ->
                if (resp.code == 200) {
                    resp.body!!.string()
                } else {
                    System.err.println("Got status code ${resp.code} from REST API: ${resp.body!!.string()}")
                    null
                }
            } ?: continue  // stick with the data we have in case of an error

            data = JSONObject(callRes)
        }
    }

}
