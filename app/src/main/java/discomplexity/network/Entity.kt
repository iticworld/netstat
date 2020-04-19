package discomplexity.network

import android.graphics.drawable.Drawable

class Entity {
    var version: String? = null         // IP VERSION
    var local: Address? = Address()     // LOCAL ADDRESS
    var remote: Address? = Address()    // REMOTE ADDRESS
    var status: String? = null          // STATUS
    var uid: Int = 0                    // UID
    var icon: Drawable? = null          // APPLICATION ICON
    var label: String? = null           // APPLICATION LABEL
    var type: String? = null            // TYPE (TCP6, TCP, UDP)
}