package com.abdalla.altarifiappfinal.screen.detail

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.abdalla.altarifiappfinal.db.Model
import com.abdalla.altarifiappfinal.utils.MakeImage
import com.abdalla.altarifiappfinal.utils.copyToClipboard
import com.abdalla.altarifiappfinal.utils.shareToOthersImage

@ExperimentalUnitApi
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    upPress: () -> Unit,
    quote: String,
    title: String
) {
    Scaffold(topBar = {
        DetailTopBar(
            onBackClick = { upPress() },
        )
    }, content = {
        // pass quote & author params to details card
        DetailContent(
            viewModel,
            quote,
            title
        )
    })
}


@ExperimentalUnitApi
@Composable
private fun DetailContent(viewModel: DetailViewModel, quote: String, title: String) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
                .clickable(onClick = {
                    context.copyToClipboard(
                        quote
                            .plus("")
                            .plus("- $title")
                    )
                    Toast
                        .makeText(context, "تم النسخ!", Toast.LENGTH_SHORT)
                        .show()
                })

        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .wrapContentSize(align = Alignment.Center),
                    text = quote,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = title,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
        Card(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier
                        .clickable {
                            context.copyToClipboard("$quote - $title")
                            Toast
                                .makeText(context, "تم النسح!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = null
                    )
                    Text(text = "نسخ")
                }


                Column(
                    modifier = Modifier
                        .clickable {

                            viewModel.insertFavourite(Model(quote, title))

                            Toast
                                .makeText(
                                    context,
                                    "تمت الإضافة إلى المفضلة!",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)
                    Text(text = "الأضافة للمفضلة")
                }

                Column(
                    modifier = Modifier
                        .clickable {
//                            context.shareToOthersImage(viewModel)

                        }
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AndroidView(
                        factory = { _context ->
                             MakeImage(
                                ctx = _context,
                                { viewModel.bitmapCreated(it) },
                                quote = quote,
                                title = title
                            )

                        })
                    Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
                    Text(text = "مشاركة")
                }
            }


        }
    }

}

@Composable
private fun DetailTopBar(onBackClick: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        title = { Text(text = "التفاصيل", style = MaterialTheme.typography.h6) },
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
