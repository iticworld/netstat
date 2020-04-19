package net.discomplexity.netstat.netstatx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import discomplexity.Package
import discomplexity.network.Entity

class MainActivity : AppCompatActivity() {
    var entities: ArrayList<Entity?>? = null
    private var recyclerView: RecyclerView? = null
    var recyclerViewAdapter: RecyclerViewAdapter? = null

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
    }
}
