import org.json.JSONObject

fun main(args: Array<String>) {
    val pyComm = PyComm()
    pyComm.start()
    val restApiAcc = RestApiAccessor()
    restApiAcc.start()

    var data = JSONObject()

    // We start our program every 30s
    while (true) {

        Thread.sleep(4000)

        data = restApiAcc.data ?: data
        data.put("co2", pyComm.co2)

        Runtime.getRuntime().exec("python3 recommendation_system.py '$data' ${args[0]}")
        // read program output
    }
}
