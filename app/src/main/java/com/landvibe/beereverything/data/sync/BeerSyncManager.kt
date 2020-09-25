package com.landvibe.beereverything.data.sync

import android.os.SystemClock
import com.google.firebase.firestore.FirebaseFirestore
import com.landvibe.beereverything.common.AppDatabase
import com.landvibe.beereverything.common.AppPreference
import com.landvibe.beereverything.data.Beer

object BeerSyncManager {
    private const val duration = 1000 * 60 * 60

    fun syncBeerListIfNeed(success: () -> Unit, fail: () -> Unit) {
        if (AppPreference.lastUpdateTime > 0 && SystemClock.elapsedRealtime() - AppPreference.lastUpdateTime < duration) {
            return
        }

        FirebaseFirestore.getInstance().collection("beer")
            .get()
            .addOnSuccessListener { result ->
                val beerList: ArrayList<Beer> = ArrayList()
                for (document in result) {
                    val beer: Beer = document.toObject(Beer::class.java)
                    beerList.add(beer)
                }
                AppDatabase.instance.beerDao().deleteAll()
                AppDatabase.instance.beerDao().insertBeerList(beerList)
                AppPreference.lastUpdateTime = SystemClock.elapsedRealtime()
                success()
            }
            .addOnFailureListener { exception ->
                fail()
            }
    }
}