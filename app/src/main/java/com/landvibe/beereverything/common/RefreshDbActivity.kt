package com.landvibe.beereverything.common

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.landvibe.beereverything.R
import com.landvibe.beereverything.data.Beer
import com.landvibe.beereverything.view.beerlist.BeerListViewModel
import kotlinx.android.synthetic.main.activity_db_refresh.*

class RefreshDbActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = RefreshDbActivity::class.simpleName
    private lateinit var mViewModel: BeerListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_refresh)

        mViewModel = ViewModelProvider(this).get(BeerListViewModel::class.java)

        btn_clear_db.setOnClickListener(this)
        btn_load_db.setOnClickListener(this)
        //btn_add_db.visibility = VISIBLE;
        btn_add_db.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_clear_db -> clearLocalDB(mViewModel)
            R.id.btn_load_db -> loadFireStore(mViewModel)
            R.id.btn_add_db -> addDB()
        }
    }

    public fun clearLocalDB(viewModel: BeerListViewModel) {
        viewModel.clearBeerList()
        Log.d(TAG, "Clear local database")
    }

    public fun loadFireStore(viewModel: BeerListViewModel) {
        val db = FirebaseFirestore.getInstance()
        db.collection("beer")
            .get()
            .addOnSuccessListener { result ->
                val beerList: ArrayList<Beer> = ArrayList()
                for (document in result) {
                    val beer: Beer = document.toObject(Beer::class.java)
                    beerList.add(beer)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                viewModel.insertBeerList(beerList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun addDB() {
        val db = FirebaseFirestore.getInstance()

        val dummyDataList: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        val dummyData1 = hashMapOf(
            "name" to "Cass",
            "country" to "Korea",
            "volume" to 4.5,
            "score" to 5.0
        )
        val dummyData2 = hashMapOf(
            "name" to "Kloud",
            "country" to "Korea",
            "volume" to 4.7,
            "score" to 4.8
        )
        val dummyData3 = hashMapOf(
            "name" to "Budweisser",
            "country" to "USA",
            "volume" to 5.0,
            "score" to 4.8
        )
        val dummyData4 = hashMapOf(
            "name" to "Hoegaarden",
            "country" to "Belgium",
            "volume" to 3.9,
            "score" to 3.8
        )
        val dummyData5 = hashMapOf(
            "name" to "Heineken",
            "country" to "Nederland",
            "volume" to 4.9,
            "score" to 4.9
        )
        val dummyData6 = hashMapOf(
            "name" to "Becks",
            "country" to "Germany",
            "volume" to 4.7,
            "score" to 4.2
        )
        val dummyData7 = hashMapOf(
            "name" to "Asahi",
            "country" to "Japan",
            "volume" to 3.6,
            "score" to 1.3
        )
        val dummyData8 = hashMapOf(
            "name" to "Carlsberg",
            "country" to "Denmark",
            "volume" to 3.6,
            "score" to 4.3
        )
        val dummyData9 = hashMapOf(
            "name" to "Terra",
            "country" to "Korea",
            "volume" to 4.8,
            "score" to 5.0
        )
        val dummyData10 = hashMapOf(
            "name" to "Tsingtao",
            "country" to "China",
            "volume" to 3.7,
            "score" to 0.7
        )

        dummyDataList.add(dummyData1)
        dummyDataList.add(dummyData2)
        dummyDataList.add(dummyData3)
        dummyDataList.add(dummyData4)
        dummyDataList.add(dummyData5)
        dummyDataList.add(dummyData6)
        dummyDataList.add(dummyData7)
        dummyDataList.add(dummyData8)
        dummyDataList.add(dummyData9)
        dummyDataList.add(dummyData10)

        for (data in dummyDataList) {
            db.collection("beer")
                .add(data)
                .addOnSuccessListener {
                    Log.w(TAG, "DocumentSnapshot written with ID: ${it.id}")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding documents.", exception)
                }
        }
    }
}