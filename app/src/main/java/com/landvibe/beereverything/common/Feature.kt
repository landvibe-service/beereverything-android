package com.landvibe.beereverything.common

class Feature {

    companion object {
        fun isFeatureEnabled(feature: String): Boolean {
            when (feature) {
                // 배포 버전에는 false로 수정 필요
                Constants.FEATURE_DB_REFRESH -> return true
            }
            return false
        }
    }
}