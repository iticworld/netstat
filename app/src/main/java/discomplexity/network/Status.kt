package discomplexity.network

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Status {
    companion object {
        private fun status(v: String): String {
            return when(v) {
                "01" -> "ESTABLISHED"
                "02" -> "SYN_SENT"
                "03" -> "SYN_RECV"
                "04" -> "FIN_WAIT1"
                "05" -> "FIN_WAIT2"
                "06" -> "TIME_WAIT"
                "07" -> "CLOSE"
                "08" -> "CLOSE_WAIT"
                "09" -> "LAST_ACK"
                "0A" -> "LISTEN"
                "0B" -> "CLOSING"
                else -> "UNKNOWN"
            }
        }

        private fun parse(index: Int, version: String, line: String, context: Context): Entity? {
            val strings = line.split("\\s+".toRegex())
            if(index > 0)
            {
                val entity = Entity()

                entity.version = version
                entity.local = Address.from(strings[2])
                entity.remote = Address.from(strings[3])
                entity.status = status(strings[4])
                entity.uid = Integer.parseInt(strings[8])

                val info = discomplexity.Package.retrieve(entity.uid, context)?.get(0)
                entity.icon = info?.icon
                entity.label = info?.label

                return entity
            }
            return null
        }

        fun retrieve(context: Context): ArrayList<Entity?>? {
            var entities = retrieve(context, "TCP")
            if(entities != null) {
                val list = retrieve(context, "TCP6")
                if(list != null){
                    entities.addAll(list)
                }
            } else {
                entities = retrieve(context, "TCP6")
            }
            if(entities != null) {
                val list = retrieve(context, "UDP")
                if(list != null){
                    entities.addAll(list)
                }
            } else {
                entities = retrieve(context, "UDP")
            }
            if(entities != null) {
                val list = retrieve(context, "UDP6")
                if(list != null){
                    entities.addAll(list)
                }
            } else {
                entities = retrieve(context, "UDP6")
            }
            return entities
        }

        private fun retrieve(context: Context, type: String): ArrayList<Entity?>? {
            var netstat: Process? = null
            var version = "IPV4"
            try {
                when (type) {
                    "TCP6" -> {
                        netstat = Runtime.getRuntime().exec("cat /proc/net/tcp6")
                        version = "IPV6"
                    }
                    "TCP" -> {
                        netstat = Runtime.getRuntime().exec("cat /proc/net/tcp")
                    }
                    "UDP" -> {
                        netstat = Runtime.getRuntime().exec("cat /proc/net/udp")
                    }
                    "UDP6" -> {
                        netstat = Runtime.getRuntime().exec("cat /proc/net/udp6")
                        version = "IPV6"
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(netstat != null) {
                val reader = BufferedReader(InputStreamReader(netstat.inputStream))
                val entities = ArrayList<Entity?>()
                reader.useLines { it.forEachIndexed{ index, value ->
                    val entity = parse(index, version, value, context)
                    entity?.type = type
                    if(entity != null) {
                        entities.add(entity)
                    }
                } }
                return entities
            }
            return null
        }
    }
}