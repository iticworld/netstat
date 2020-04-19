package discomplexity.network

import android.util.Log

class Address() {
    var address: String? = null
    var port: Int? = null

    init {

    }

    constructor(address: String, port: Int): this() {
        this.address = address
        this.port = port
    }

    companion object {
        fun from(value: String): Address? {
            val strings = value.split(":")
            if(strings.size == 2) {
                var temp = strings[0]
                var address = ""
                if(temp.length == 32) {
                    address += temp.substring(6, 8)
                    address += temp.substring(4, 6)
                    address += ":"
                    address += temp.substring(2, 4)
                    address += temp.substring(0, 2)
                    address += ":"
                    address += temp.substring(14, 16)
                    address += temp.substring(12, 14)
                    address += ":"
                    address += temp.substring(10, 12)
                    address += temp.substring(8, 10)
                    address += ":"
                    address += temp.substring(22, 24)
                    address += temp.substring(20, 22)
                    address += ":"
                    address += temp.substring(18, 20)
                    address += temp.substring(16, 18)
                    address += ":"
                    address += temp.substring(30, 32)
                    address += temp.substring(28, 30)
                    address += ":"
                    address += temp.substring(26, 28)
                    address += temp.substring(24, 26)
                } else if(temp.length == 8) {
                    address += Integer.parseInt(temp.substring(6, 8), 16).toString()
                    address += "."
                    address += Integer.parseInt(temp.substring(4, 6), 16).toString()
                    address += "."
                    address += Integer.parseInt(temp.substring(2, 4), 16).toString()
                    address += "."
                    address += Integer.parseInt(temp.substring(0, 2), 16).toString()
                } else {
                    Log.i("UNKNOWN", value + "${temp.length}")
                }
                return Address(address, Integer.parseInt(strings[1], 16))
            }
            return null
        }
    }
}