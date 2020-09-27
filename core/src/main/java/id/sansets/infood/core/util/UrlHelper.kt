package id.sansets.infood.core.util

import id.sansets.infood.core.BuildConfig

object UrlHelper {

    fun getCoverUrl(id: String?, imageType: String): String {
        return "${BuildConfig.BASE_URL}/recipeImages/$id-312x231.$imageType"
    }

    fun getBackdropUrl(id: String?, imageType: String): String {
        return "${BuildConfig.BASE_URL}/recipeImages/$id-636x393.$imageType"
    }
}