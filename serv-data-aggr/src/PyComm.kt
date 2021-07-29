import java.io.DataInputStream
import java.net.ServerSocket

class PyComm : Thread() {

    init {
        isDaemon = true
    }

    @Volatile
    var co2 = -1

    override fun run() = ServerSocket(8080).use { ss ->
        val s = ss.accept()
        if (s == null) {
            System.err.println("Failed to connect to the Pi")
            ss.close()
            return
        }

        /*
        Protocol:
        handshake: Pi sends 0x1234 to identify itself
        then, all new data is in the form of commands, see below
         */

        val inp = DataInputStream(s.getInputStream())
        if (inp.readByte().toInt() != 0x12) {
            System.err.println("Pi violated protocol")
            return
        }

        while (true) {
            val cmd = inp.readByte()
            when (cmd.toInt()) {
                0x10 -> {
                    co2 = inp.readInt()
                }
                0x40 -> {
                    println("orderly shutdown")
                    return
                }
            }
        }

    }

}
