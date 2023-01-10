package com.testproject.beerlist_compose.UiClasses

import com.testproject.beerlist_compose.domain.Beer

const val DETAIL_NAME = "name"
const val DETAIL_DESCRIPTION = "description"
const val DETAIL_TAGLINE="tagline"
const val DETAIL_BREWED="brewed"
const val DETAIL_IMAGE="image"
const val DETAIL_ID="id"



sealed class Screen (val route :String){
    object Main: Screen(route = "Main_Screen")
    object Details: Screen(route = "Details_Screen/{$DETAIL_ID}/{$DETAIL_NAME}/{$DETAIL_TAGLINE}/{$DETAIL_DESCRIPTION}/{$DETAIL_BREWED}/{$DETAIL_IMAGE}")
        fun passBeer(beer: Beer): String {
            return ( "Details_Screen/${beer.id}/${beer.name}/${beer.tagline}/${beer.description}/${beer.first_brewed}/${beer.image_url}")
        }
}
