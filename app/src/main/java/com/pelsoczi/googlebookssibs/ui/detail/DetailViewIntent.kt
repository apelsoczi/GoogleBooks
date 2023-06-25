package com.pelsoczi.googlebookssibs.ui.detail


/** actions dispatched from the UI */
sealed class DetailViewIntent {

    /** save the book */
    object AddToFavorites : DetailViewIntent()

    /** remove from saved list */
    object RemoveFavorite : DetailViewIntent()

    /** open browser to purchase */
    object Purchase : DetailViewIntent()

}
