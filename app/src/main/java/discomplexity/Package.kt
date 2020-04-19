package discomplexity

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

class Package() {
    private var name: String? = null
    var label: String? = null
    var icon: Drawable? = null

    init {
        this.name = null
        this.label = null
        this.icon = null
    }

    constructor(name: String, label: String, icon: Drawable): this() {
        this.name = name
        this.label = label
        this.icon = icon

    }

    companion object {
        fun retrieve(uid: Int, context: Context): List<Package>? {
            val result = context.packageManager.getPackagesForUid(uid)?.map{
                val info = context.packageManager.getApplicationInfo(it, PackageManager.GET_META_DATA)
                Package(it, context.packageManager.getApplicationLabel(info).toString(), context.packageManager.getApplicationIcon(it))
            }
            return result?.toList()
        }
    }
}