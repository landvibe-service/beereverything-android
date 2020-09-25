package com.landvibe.beereverything.data.maker

import com.google.firebase.firestore.FirebaseFirestore

object BeerGenerator {
    fun generateFirebaseData() {
        val dummyDataList = mutableListOf<HashMap<String, Any>>()

        dummyDataList.add(
            hashMapOf(
                "name" to "Cass",
                "country" to "Korea",
                "volume" to 4.5,
                "score" to 5.0
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "Kloud",
                "country" to "Korea",
                "volume" to 4.7,
                "score" to 4.8
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "Budweisser",
                "country" to "USA",
                "volume" to 5.0,
                "score" to 4.8
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "Hoegaarden",
                "country" to "Belgium",
                "volume" to 3.9,
                "score" to 3.8
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "Heineken",
                "country" to "Nederland",
                "volume" to 4.9,
                "score" to 4.9
            )
        )

        dummyDataList.forEach {
            FirebaseFirestore.getInstance().collection("beer").add(it)
        }
    }
}