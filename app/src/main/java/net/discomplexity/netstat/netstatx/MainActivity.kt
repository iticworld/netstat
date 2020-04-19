package net.discomplexity.netstat.netstatx

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import discomplexity.network.Entity
import java.util.Timer
import java.util.TimerTask
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var entities: ArrayList<Entity?>? = null
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.mRecyclerView)

        entities = discomplexity.network.Status.retrieve(this)

        recyclerViewAdapter = RecyclerViewAdapter(entities)
        if(recyclerView != null){
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            recyclerView!!.adapter = recyclerViewAdapter
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, 1))
        }

        start(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        stop()
    }

    private fun start(context: Context) {
        stop()

        timer = Timer()

        timer!!.schedule(object: TimerTask(){
            override fun run() {
                runOnUiThread {
                    entities = discomplexity.network.Status.retrieve(context)
                    recyclerViewAdapter?.set(entities)
                }
            }
        }, 0, 10000)
    }

    private fun stop(){
        timer?.cancel()
    }
}
