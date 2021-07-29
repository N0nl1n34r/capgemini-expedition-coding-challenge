import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class RestApiAccessor : Thread("RestApiAccessor") {

    init {
        isDaemon = true
    }

    @Volatile
    var data: JSONObject? = null

    override fun run() {
        val client = OkHttpClient()

        while (true) {
            sleep(4000)

            val req = Request.Builder()
                .url("https://rvj6rnbpxj.execute-api.eu-central-1.amazonaws.com/prod/live-data")
                .get()
                .build()

            val callRes = client.newCall(req).execute().use { resp ->
                if (resp.code == 200) {
                    resp.body!!.string()
                } else {
                    System.err.println("got status code ${resp.code}: ${resp.body!!.string()}")
                    null
                }
            } ?: continue

            data = JSONObject(callRes)
        }
    }

}
