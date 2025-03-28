package com.saika.multiversecheckup.presentation.views.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saika.multiversecheckup.presentation.viewmodel.CharacterDetailViewModel
import com.saika.multiversecheckup.presentation.views.screens.CharacterDetailView
import com.saika.multiversecheckup.presentation.views.screens.CharacterSearchView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "imageSearch") {
        composable("imageSearch") {
            CharacterSearchView(
                onItemClick = { id ->
                    navController.navigate("detail/$id")
                })
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            val characterDetailViewModel: CharacterDetailViewModel = hiltViewModel()
            CharacterDetailView(id = id, characterDetailViewModel, navController)
        }
    }
}