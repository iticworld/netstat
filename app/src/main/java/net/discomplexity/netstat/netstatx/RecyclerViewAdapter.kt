package net.discomplexity.netstat.netstatx

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import discomplexity.network.Entity

class RecyclerViewAdapter(private var list: List<Entity?>?): RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {

    class Holder(private var view: View) : RecyclerView.ViewHolder(view) {

        fun setSource(v: String?, port: Int?) {
            val text = v + "/" + port.toString()

            view.findViewById<TextView>(R.id.source).text = text
        }

        fun setDestination(v: String?, port: Int?) {
            val text = v + "/" + port.toString()
            viewDestination(true)
            view.findViewById<TextView>(R.id.destination).text = text
        }

        fun setHttp(v: String?) {
            if(v != null) {
                view.findViewById<TextView>(R.id.http).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.http).text = v
            } else {
                view.findViewById<TextView>(R.id.http).visibility = View.GONE
            }
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

        fun viewDestination(v: Boolean) {
            if(v){
                view.findViewById<LinearLayoutCompat>(R.id.destinationView).visibility = View.VISIBLE
            } else {
                view.findViewById<LinearLayoutCompat>(R.id.destinationView).visibility = View.GONE
            }
        }
    }

    fun set(list: List<Entity?>?) {
        this.list = list
        Log.i("SCHEDULE", "RUN")
        this.notifyDataSetChanged()
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
            holder.setHttp(entity.http)
            if(entity.status != "LISTEN") {
                holder.setDestination(entity.remote!!.address, entity.remote!!.port)
            } else {
                holder.viewDestination(false)
            }
        }
    }
}