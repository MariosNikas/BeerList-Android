package com.testproject.beerlist_compose.UiClasses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.testproject.beerlist_compose.data.MainViewModel
import com.testproject.beerlist_compose.domain.Beer
import com.testproject.beerlist_compose.ui.theme.BeerListcomposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel : MainViewModel by viewModels()
        setContent {
            BeerListcomposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainActivityComposable(viewModel,0)
                }
            }
        }
    }
}

@Composable
fun MainActivityComposable(viewModel : MainViewModel, page: Int) {
    val beersState = viewModel.beerList.observeAsState()
    val beers = beersState.value
    lazyColllumn(beers)
}



@Composable
fun lazyColllumn(beers: List<Beer>?){
    val context = LocalContext.current
    Scaffold (topBar = { TopAppBar(title = {Text("The list of Beers from the punkapi")},backgroundColor = MaterialTheme.colors.background)  },){ innerPadding ->
        LazyColumn(contentPadding = innerPadding, modifier = Modifier.fillMaxSize()){
            if (beers != null) {
                items(beers){ beer  ->
                    lazyCollumnitem(beer = beer){
                        val intent = Intent(context, detailsActivity::class.java)
                        intent.putExtra("name", it.name)
                        intent.putExtra("description", it.description)
                        intent.putExtra("tagline", it.tagline)
                        intent.putExtra("image_url", it.image_url)
                        intent.putExtra("brewed", it.first_brewed)
                        Toast.makeText(context, it.name, Toast.LENGTH_LONG).show()
                        context.startActivity(intent)
                    }
                }
            }
        }

    }
}

@Composable
fun lazyCollumnitem(beer: Beer, onclickfun : (Beer)-> Unit) {
    Surface(modifier = Modifier.clickable { onclickfun(beer)}) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(3.dp, MaterialTheme.colors.primary, RoundedCornerShape(6))
        ) {
            AsyncImage(
                model = beer.image_url,
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
            Column(modifier = Modifier.padding(all = 8.dp)) {
                Text(text = beer.name, style = MaterialTheme.typography.h5)
                Text(text = beer.tagline)
            }
    }
}
}

