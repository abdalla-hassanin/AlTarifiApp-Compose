package com.abdalla.altarifiappfinal

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.lifecycleScope
import com.abdalla.altarifiappfinal.dp.UIModeImpl
import com.abdalla.altarifiappfinal.utils.NavGraph
import com.abdalla.altarifiappfinal.theme.AlTarifiAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var uiModeDataStore: UIModeImpl

    @ExperimentalUnitApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        super.onCreate(savedInstanceState)
        setContent {
            AppMain()

        }
        // observe theme change
        observeUITheme()
    }

    @ExperimentalUnitApi
    @ExperimentalComposeUiApi
    @Composable
    private fun AppMain() {
        val darkMode by uiModeDataStore.uiMode.collectAsState(initial = isSystemInDarkTheme())

        // set UI mode accordingly
        val toggleTheme: () -> Unit = {
            lifecycleScope.launch {
                uiModeDataStore.saveToDataStore(!darkMode)
            }
        }

        AlTarifiAppTheme(darkMode) {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(MaterialTheme.colors.primaryVariant)
                NavGraph(toggleTheme)
            }
        }
    }

    private fun observeUITheme() {
        lifecycleScope.launchWhenStarted {
            uiModeDataStore.uiMode.collect {
                val mode = when (it) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }

}
