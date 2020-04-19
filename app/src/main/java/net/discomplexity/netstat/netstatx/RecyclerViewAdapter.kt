package net.discomplexity.netstat.netstatx

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import discomplexity.network.Entity

class RecyclerViewAdapter(private var list: List<Entity?>?): RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {

    class Decoration(private var height: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            if(parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
                outRect.bottom = height
            }
        }
    }

    class Holder(private var view: View) : RecyclerView.ViewHolder(view) {

        fun setSource(v: String?, port: Int?) {
            val text = v + "/" + port.toString()

            view.findViewById<TextView>(R.id.source).text = text
        }

        fun setDestination(v: String?, port: Int?) {
            val text = v + "/" + port.toString()

            view.findViewById<TextView>(R.id.destination).text = text
        }

        fun setIcon(o: Drawable?) {
            view.findViewById<ImageView>(R.id.icon).setImageDrawable(o)
        }

        fun setStatus(v: String?) {
            view.findViewById<TextView>(R.id.status).text = v
        }

        fun setVersion(v: String?) {
            view.findViewById<TextView>(R.id.version).text = v
        }

        fun setLabel(v: String?) {
            view.findViewById<TextView>(R.id.label).text = v
        }

        fun setType(v: String?) {
            view.findViewById<TextView>(R.id.type).text = v
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(inflatedView)
    }

    override fun getItemCount(): Int {
        if(this.list != null){
            Log.e("COUNT", this.list!!.size.toString())
            return this.list!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val entity = list!![position]
        if(entity != null) {
            holder.setSource(entity.local!!.address, entity.local!!.port)
            holder.setIcon(entity.icon)
            holder.setStatus(entity.status)
            holder.setVersion(entity.version)
            holder.setLabel(entity.label)
            holder.setType(entity.type)
            if(entity.status != "LISTEN") {
                holder.setDestination(entity.remote!!.address, entity.remote!!.port)
            }
        }
    }
}