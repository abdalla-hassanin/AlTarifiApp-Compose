package com.abdalla.altarifiappfinal.utils

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.abdalla.altarifiappfinal.screen.quoteslist.QuotesListScreen
import com.abdalla.altarifiappfinal.screen.quoteslist.QuotesListViewModel
import com.abdalla.altarifiappfinal.screen.detail.DetailScreen
import com.abdalla.altarifiappfinal.screen.detail.DetailViewModel
import com.abdalla.altarifiappfinal.screen.favourites.FavouritesScreen
import com.abdalla.altarifiappfinal.screen.favourites.FavouritesViewModel
import com.abdalla.altarifiappfinal.screen.search.SearchScreen
import com.abdalla.altarifiappfinal.screen.search.SearchViewModel

object EndPoints {
    const val QUOTE = "quote"
    const val TITLE = "title"
}

@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun NavGraph(toggleTheme: () -> Unit) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController, startDestination = Screen.Home.route) {
        // Quotes List
        composable(Screen.Home.route) {
            val viewModel = hiltViewModel<QuotesListViewModel>()
            QuotesListScreen(viewModel, toggleTheme, actions)
        }

        // Quotes Details
        composable(
            "${Screen.Details.route}/{quote}/{title}",
            arguments = listOf(
                navArgument(EndPoints.QUOTE) { type = NavType.StringType },
                navArgument(EndPoints.TITLE) {
                    type = NavType.StringType
                })
        ) {
            val viewModel = hiltViewModel<DetailViewModel>()
            DetailScreen(
                viewModel,
                actions.upPress,
                it.arguments?.getString(EndPoints.QUOTE) ?: "",
                it.arguments?.getString(EndPoints.TITLE) ?: ""
            )
        }

        // Favourites
        composable(Screen.Favourites.route) {
            val viewModel = hiltViewModel<FavouritesViewModel>()
            FavouritesScreen(viewModel, actions)
        }
        //Search
        composable(Screen.Search.route){
            val viewModel= hiltViewModel<SearchViewModel>()
            SearchScreen(viewModel,actions)
        }
    }
}

class MainActions(navController: NavHostController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val gotoDetails: (String, String) -> Unit = { quote, title ->
        navController.navigate("${Screen.Details.route}/$quote/$title")
    }

    val gotoFavourites: () -> Unit = {
        navController.navigate(Screen.Favourites.route)
    }
    val gotoSearch: () -> Unit = {
        navController.navigate(Screen.Search.route)
    }

}
