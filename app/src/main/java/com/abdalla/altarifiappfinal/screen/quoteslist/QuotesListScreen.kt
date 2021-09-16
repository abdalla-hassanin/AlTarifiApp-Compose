package com.abdalla.altarifiappfinal.screen.quoteslist

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdalla.altarifiappfinal.R
import com.abdalla.altarifiappfinal.db.Model
import com.abdalla.altarifiappfinal.model.Quote
import com.abdalla.altarifiappfinal.utils.MainActions
import com.abdalla.altarifiappfinal.utils.shareToOthersText

@Composable
fun QuotesListScreen(
    viewModel: QuotesListViewModel,
    toggleTheme: () -> Unit,
    actions: MainActions,
) {
    Scaffold(
        topBar = {
            TopBar(
                onToggleClick = { toggleTheme() },
                onFavouritesClick = actions.gotoFavourites,
                onSearchClick = actions.gotoSearch
            )
        },
        content = {
            val context = LocalContext.current

            // get all quotes
            viewModel.getAllQuotes(context)

            val result = viewModel.getQuotes.collectAsState().value

            QuotesList(quotes = result, actions = actions, viewModel)
        }


    )

}

@Composable
private fun QuotesList(quotes: List<Quote>, actions: MainActions, viewModel: QuotesListViewModel) {
    LazyColumn(modifier = Modifier.padding(12.dp)) {
        items(items = quotes) { item ->
            QuotesCard(item, actions, viewModel)
        }
    }
}

@Composable
private fun QuotesCard(item: Quote, actions: MainActions, viewModel: QuotesListViewModel) {
    val context = LocalContext.current
    val showImage = remember {
        mutableStateOf(false)
    }

    Column(Modifier.fillMaxSize()) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.h5,
            fontFamily = FontFamily(Font(R.font.asmaa))
        )

        for (quote in item.quotes) {

//                AndroidView(
//                    factory = { _context ->
//                        MakeImage(
//                            ctx = _context,
//                            { viewModel.bitmapCreated(it) },
//                            quote = quote,
//                            title = item.title
//                        )
//
//                    }){
//                    MakeImage(
//                        ctx = it.context,
//                        { viewModel.bitmapCreated(it) },
//                        quote = quote,
//                        title = item.title
//                    )
//                }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp, 12.dp, 0.dp, 12.dp)

            ) {
                Column {
                    Text(
                        text = quote,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            letterSpacing = 0.1.sp
                        ),
//                    fontFamily = FontFamily(Font(R.font.yassin)),
//                    lineHeight = 25.sp,
                        modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp),
                        textAlign = TextAlign.Center
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        IconButton(onClick = {
//                            context.shareToOthersImage(viewModel)
                            context.shareToOthersText(quote)
                        }) {

                            Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
                        }
                        IconButton(onClick = {
                            viewModel.insertFavourite(Model(quote, item.title))
                            Toast
                                .makeText(
                                    context,
                                    "تم الأضافة للمفضلة!",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun TopBar(
    onToggleClick: () -> Unit,
    onFavouritesClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    TopAppBar(modifier = Modifier, backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        title = {
            Text(
                text = "ذخائر في سطور",
                style = MaterialTheme.typography.h5,
                fontFamily = FontFamily(Font(R.font.asmaa)),
                maxLines = 1
            )
        },
        actions = {
            IconButton(onClick = {
                onSearchClick()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }

            IconButton(onClick = {
                onFavouritesClick()
            }) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(onClick = {
                onToggleClick()
            }, modifier = Modifier.padding(10.dp, 0.dp)) {
                val toggleIcon = if (!isSystemInDarkTheme())
                    Icons.Outlined.DarkMode
                else
                    Icons.Outlined.LightMode
                Icon(
                    imageVector = toggleIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )

}
