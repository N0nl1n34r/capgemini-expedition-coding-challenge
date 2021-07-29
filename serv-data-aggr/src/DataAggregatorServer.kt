import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import kotlin.system.exitProcess

const val SEEN_PURGE_TIME_MS = 1000L * 60 * 30

private fun findPython(): String {
    val python = System.getenv("dataaggr_python")?.takeUnless { it.isBlank() }
    if (python != null) return python
    System.err.println("Please specify the location of the python interpreter in the \"dataaggr_python\" environment variable.")
    exitProcess(1)
}

private fun loadRules(filename: String?): String {
    if (filename == null) {
        System.err.println("Please specify the name of the rules definition file.")
        exitProcess(1)
    }

    val file = File(filename)
    if (!file.isFile) {
        System.err.println("The given rules definition file does not exist (or is not a file): $file.")
        exitProcess(2)
    }

    return file.readText()
}

// Usage: <program> <rulesFile> [refreshPeriod]
fun main(args: Array<String>) {
    val python = findPython()
    val rules = loadRules(args.getOrNull(0))
    val period = args.getOrNull(1)?.toLongOrNull() ?: 1000

    // Start data fetcher daemons
    val pyComm = PyComm()
    pyComm.start()
    val restApiAcc = RestApiAccessor()
    restApiAcc.start()

    var data = JSONObject()
    val seenEvents = HashMap<String, Long>()  // event to timestamp

    while (true) {
        Thread.sleep(period)

        data = restApiAcc.data ?: data  // use last version if data fetching failed
        data.put("co2", pyComm.co2)
        data.put("tvoc", pyComm.tvoc)

        // Call the data evaluation routine
        val prcOutput = ProcessBuilder(python, "recommendation_system.py", "$data", rules)
            .start()
            .inputStream  // python always prints to stderr

        val lines = prcOutput.bufferedReader().readLines().filter { it.isNotBlank() }
        val line = lines.singleOrNull()
        if (line == null) {
            System.err.println("Invalid program output, quitting:")
            System.err.println(lines)
            return
        }
        val events = JSONArray(line)
        repeat(events.length()) { idx ->
            val event = events.getString(idx)
            val lastSeenWhen = seenEvents[event]
            if (lastSeenWhen == null || System.currentTimeMillis() - lastSeenWhen > SEEN_PURGE_TIME_MS) {
                seenEvents[event] = System.currentTimeMillis()
                println("evt: $event")
//                Runtime.getRuntime().exec("$python send_telegram_message.py \"${event.replace("\"", "")}\"")
                ProcessBuilder(python, "send_telegram_message.py", event).start()
            }
        }
    }
}
