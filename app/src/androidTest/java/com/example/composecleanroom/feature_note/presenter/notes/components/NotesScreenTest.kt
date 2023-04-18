package com.example.composecleanroom.feature_note.presenter.notes.components


import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composecleanroom.feature_note.core.utils.TestTags.ORDER_SECTION
import com.example.composecleanroom.feature_note.di.AppModule
import com.example.composecleanroom.feature_note.presenter.MainActivity
import com.example.composecleanroom.feature_note.presenter.util.Screen
import com.example.composecleanroom.ui.theme.ComposeCleanRoomTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ComposeCleanRoomTheme {
                NavHost(navController, startDestination = Screen.NotesScreen.route){
                    composable(Screen.NotesScreen.route){
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderFilter_isVisible(){
        composeRule.onNodeWithTag(ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Сортировка").performClick()
        composeRule.onNodeWithTag(ORDER_SECTION).assertIsDisplayed()
    }
}