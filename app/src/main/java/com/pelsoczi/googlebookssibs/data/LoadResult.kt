package com.pelsoczi.googlebookssibs.data

import com.pelsoczi.googlebookssibs.data.local.Book

/** represents load operations from the repository */
sealed class LoadResult {

    /** Invalid result of operation */
    object Invalid : LoadResult()

    /** Success result of operation */
    data class Page(
        val data: List<Book>,
        val nextKey: Int?,
    ) : LoadResult()

    /** Error result of operation */
    data class Error(
        val exception: Exception,
    ) : LoadResult()

}
