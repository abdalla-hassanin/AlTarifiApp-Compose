package com.abdalla.altarifiappfinal.screen.favourites

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abdalla.altarifiappfinal.R
import com.abdalla.altarifiappfinal.db.Model
import com.abdalla.altarifiappfinal.utils.FavouriteViewState
import com.abdalla.altarifiappfinal.utils.MainActions
import com.abdalla.altarifiappfinal.utils.shareToOthersText
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@ExperimentalMaterialApi
@Composable
fun FavouritesScreen(viewModel: FavouritesViewModel, actions: MainActions) {
    val context = LocalContext.current

    Scaffold(topBar = {
        FavouriteTopBar(
            onBackClick = { actions.upPress() })

    }, content = {
        // pass quote & author params to details card
        when (val result = viewModel.favState.collectAsState().value) {
            is FavouriteViewState.Empty -> EmptyScreen(actions)
            is FavouriteViewState.Success -> {
                LazyColumn {
                    items(result.quote) { quote ->

                        Card(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(12.dp)
                        ) {

                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = quote.quote,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier
                                        .padding(start = 12.dp, bottom = 8.dp)
                                        .align(Alignment.CenterHorizontally)
                                )


                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(onClick = {
//                            context.shareToOthersImage(viewModel)
                                        context.shareToOthersText(quote.quote)
                                    }) {

                                        Icon(
                                            imageVector = Icons.Outlined.Share,
                                            contentDescription = null
                                        )
                                    }
                                    Text(
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        text = quote.title,
                                        style = MaterialTheme.typography.caption,
                                    )

                                    IconButton(onClick = {
                                        viewModel.deleteFavourite(Model(quote.quote, quote.title))
                                        Toast
                                            .makeText(
                                                context,
                                                "تم الحذف!",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = null
                                        )
                                    }


                                }
                            }
                        }


                    }
                }
            }
        }

    })
}

@Composable
fun FavouriteTopBar(onBackClick: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        title = { Text(text = "المفضلة", style = MaterialTheme.typography.h6) },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun EmptyScreen(actions: MainActions) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Loader()
        Text(
            text = "قائمة مفضلاتك فارغة!",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "اكتشف المزيد وأضف بعض الاقتباسات!",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary),
            onClick = { actions.upPress() }
        ) {
            Text(text = "الرجوع لصفحة الرئيسية", color = MaterialTheme.colors.primary)
        }
    }
}


@Composable
fun Loader() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.data))
    val progress by animateLottieCompositionAsState(composition, iterations = Int.MAX_VALUE)
    LottieAnimation(
        composition,
        progress,
        modifier = Modifier.size(300.dp),
    )

}
