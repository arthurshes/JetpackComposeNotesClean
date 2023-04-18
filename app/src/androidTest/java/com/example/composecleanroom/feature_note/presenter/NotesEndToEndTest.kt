package com.example.composecleanroom.feature_note.presenter

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composecleanroom.feature_note.core.utils.TestTags.NOTE_TAG_ITEM
import com.example.composecleanroom.feature_note.core.utils.TestTags.TEXTFIELD_CONTENT_TAG
import com.example.composecleanroom.feature_note.core.utils.TestTags.TEXTFIELD_TITLE_TAG
import com.example.composecleanroom.feature_note.di.AppModule
import com.example.composecleanroom.feature_note.presenter.add_edit_note.components.AddEditNotScreen
import com.example.composecleanroom.feature_note.presenter.notes.components.NotesScreen
import com.example.composecleanroom.feature_note.presenter.util.Screen
import com.example.composecleanroom.ui.theme.ComposeCleanRoomTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUP(){
        hiltRule.inject()
        composeRule.activity.setContent {
            ComposeCleanRoomTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.NotesScreen.route){
                    composable(route = Screen.NotesScreen.route){
                        NotesScreen(navController = navController)

                    }
                    composable(route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}", arguments = listOf(
                        navArgument(
                            "noteId"
                        ){
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            "noteColor"
                        ){
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )){
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNotScreen(navController = navController, noteColor = color)
                    }
                }
            }
        }
    }



    @Test
    fun saveNewNote_editAfterwards(){
        composeRule.onNodeWithContentDescription("Добавить заметку").performClick()

        composeRule.onNodeWithTag(TEXTFIELD_TITLE_TAG).performTextInput("ahahahhaha lol")
        composeRule.onNodeWithTag(TEXTFIELD_CONTENT_TAG).performTextInput("Arthur genius!")

        composeRule.onNodeWithContentDescription("Сохранить").performClick()

        composeRule.onNodeWithText("ahahahhaha lol").assertIsDisplayed()
        composeRule.onNodeWithText("ahahahhaha lol").performClick()

        composeRule.onNodeWithTag(TEXTFIELD_TITLE_TAG).assertTextEquals("ahahahhaha lol")
        composeRule.onNodeWithTag(TEXTFIELD_CONTENT_TAG).assertTextEquals("Arthur genius!")
        composeRule.onNodeWithTag(TEXTFIELD_TITLE_TAG).performTextInput("23")
        composeRule.onNodeWithContentDescription("Сохранить").performClick()

        composeRule.onNodeWithText("ahahahhaha lol23").assertIsDisplayed()

    }


    @Test
    fun saveNewNotes_orderByTitleDescending(){
        for(i in 1..3){
            composeRule.onNodeWithContentDescription("Добавить заметку").performClick()

            composeRule.onNodeWithTag(TEXTFIELD_TITLE_TAG).performTextInput("ahahahhaha lol$i")
            composeRule.onNodeWithTag(TEXTFIELD_CONTENT_TAG).performTextInput("Arthur genius$i!")

            composeRule.onNodeWithContentDescription("Сохранить").performClick()
        }
        composeRule.onNodeWithText("ahahahhaha lol1").assertIsDisplayed()
        composeRule.onNodeWithText("ahahahhaha lol2").assertIsDisplayed()
        composeRule.onNodeWithText("ahahahhaha lol3").assertIsDisplayed()

        composeRule.onNodeWithContentDescription("Сортировка").performClick()
        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("По убывыванию").performClick()

        composeRule.onAllNodesWithTag(NOTE_TAG_ITEM)[0]
            .assertTextContains("ahahahhaha lol3")
        composeRule.onAllNodesWithTag(NOTE_TAG_ITEM)[1]
            .assertTextContains("ahahahhaha lol2")
        composeRule.onAllNodesWithTag(NOTE_TAG_ITEM)[2]
            .assertTextContains("ahahahhaha lol1")
    }
}