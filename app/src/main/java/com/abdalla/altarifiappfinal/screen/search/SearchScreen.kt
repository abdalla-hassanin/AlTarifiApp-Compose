package com.abdalla.altarifiappfinal.screen.search

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdalla.altarifiappfinal.db.Model
import com.abdalla.altarifiappfinal.model.Quote
import com.abdalla.altarifiappfinal.utils.MainActions
import com.abdalla.altarifiappfinal.utils.shareToOthersText
import java.util.*


@ExperimentalComposeUiApi
@Composable
fun SearchScreen(viewModel: SearchViewModel, actions: MainActions) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        SearchView(textState)
        AllQuotesList(viewModel, actions, textState)
    }
}


@ExperimentalComposeUiApi
@Composable
private fun SearchView(state: MutableState<TextFieldValue>) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
        ),
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        label = { Text(text = "البحث") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
        leadingIcon = { Icon(Icons.Filled.Search, null) },
        singleLine = true,
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
    )
}


@Composable
private fun AllQuotesList(
    viewModel: SearchViewModel,
    actions: MainActions,
    textState: MutableState<TextFieldValue>
) {
    val context = LocalContext.current

    // get all quotes
    viewModel.getAllQuotes(context)

    val allQuotes = viewModel.getQuotes.collectAsState().value
    val quotes = arrayAllQuotes(allQuotes)

    var filteredQuotes: ArrayList<Model>


    LazyColumn(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        val searchedText = textState.value.text
        filteredQuotes = if (searchedText.isEmpty()) {
            quotes
        } else {
            val resultList = ArrayList<Model>()
            for (quote in quotes) {
                if (quote.quote.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(quote)
                }
            }
            resultList
        }

        items(items = filteredQuotes) { item ->
            Column(Modifier.fillMaxSize()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 12.dp, 0.dp, 12.dp)

                ) {
                    Column {
                        Text(
                            text = item.quote,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                letterSpacing = 0.1.sp
                            ),
                            modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp),
                            textAlign = TextAlign.Center
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            IconButton(onClick = {
//                            context.shareToOthersImage(viewModel)
                                context.shareToOthersText(item.quote)
                            }) {

                                Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
                            }
                            IconButton(onClick = {
                                viewModel.insertFavourite(Model(item.quote, item.title))
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
}

fun arrayAllQuotes(allQuotes: List<Quote>): ArrayList<Model> {
    val allList = ArrayList<Model>()
    for (quote in allQuotes) {
        for (str in quote.quotes) {
            allList.add(Model(str, quote.title))
        }
    }
    return allList
}