package com.example.nutritionapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutritionapp.presentation.navigation.ProfessionalDetail
import com.example.nutritionapp.presentation.navigation.ProfessionalsList
import com.example.nutritionapp.presentation.professional_detail.ProfessionalDetailScreen
import com.example.nutritionapp.presentation.professionals_list.ProfessionalsListScreen
import com.example.nutritionapp.presentation.professionals_list.ProfessionalsViewModel
import com.example.nutritionapp.presentation.professionals_list.event.ProfessionalsEvent
import com.example.nutritionapp.core.utils.ObserveAsEvents
import com.example.nutritionapp.ui.theme.NutritionAppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                val professionalsViewModel = koinViewModel<ProfessionalsViewModel>()
                val professionalsState by professionalsViewModel.state.collectAsStateWithLifecycle()
                val navController = rememberNavController()
                val context = LocalContext.current

                ObserveEvents(
                    professionalsViewModel = professionalsViewModel,
                    navController = navController,
                    context = context
                )

                NutritionAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        SharedTransitionLayout {
                            NavHost(
                                navController = navController,
                                startDestination = ProfessionalsList
                            ) {
                                composable<ProfessionalsList> {
                                    ProfessionalsListScreen(
                                        modifier = Modifier.padding(innerPadding),
                                        state = professionalsState,
                                        onAction = { action ->
                                            professionalsViewModel.onAction(action)
                                        },
                                        animatedVisibilityScope = this
                                    )
                                }

                                composable<ProfessionalDetail> {
                                    ProfessionalDetailScreen(
                                        modifier = Modifier.padding(top = 30.dp),
                                        state = professionalsState,
                                        onArrowBackClick = {
                                            navController.navigate(ProfessionalsList)
                                        },
                                        animatedVisibilityScope = this
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    @Composable
    private fun ObserveEvents(
        professionalsViewModel: ProfessionalsViewModel,
        navController: NavHostController,
        context: Context
    ) {
        ObserveAsEvents(events = professionalsViewModel.events) { event ->
            when (event) {
                is ProfessionalsEvent.OpenProfessionalDetail -> {
                    navController.navigate(ProfessionalDetail)
                }

                ProfessionalsEvent.OnProfessionalDetailError -> {
                    Toast.makeText(
                        context,
                        getString(R.string.cant_show_information_about_this_professional),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ProfessionalsEvent.OnProfessionalsListResultError -> {
                    Toast.makeText(
                        context,
                        getString(R.string.cant_find_professionals),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}