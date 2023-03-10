package com.denicks21.speechandtext.screen

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.denicks21.speechandtext.navigation.NavScreens.TextToSpeechPage.title
import com.denicks21.speechandtext.ui.composables.CustomTopBar
import com.denicks21.speechandtext.ui.theme.GreyDark
import com.denicks21.speechandtext.ui.theme.YellowDark
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TextToSpeechPage(
    navController: NavHostController,
    openDrawer: () -> Unit,
) {
    var tts: TextToSpeech? = null
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val scaffoldState = rememberScaffoldState()
        var textFieldState by remember { mutableStateOf("") }
        var isBtnEnabled by remember { mutableStateOf(true) }
        var pitch by remember { mutableStateOf(1f) }
        var speechRate by remember { mutableStateOf(1f) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                CustomTopBar(
                    title,
                    openDrawer
                )
            },
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .padding(top = 50.dp)
                ) {
                    OutlinedTextField(
                        value = textFieldState,
                        onValueChange = {
                            textFieldState = it
                        },
                        label = {
                            Text(
                                text = "Text to convert",
                                color = GreyDark
                            )
                        },
                        placeholder = {
                            Text(
                                text = "Enter some text here",
                                color = GreyDark
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = GreyDark,
                            backgroundColor = YellowDark,
                            focusedBorderColor = GreyDark,
                            unfocusedBorderColor = GreyDark,
                            placeholderColor = GreyDark
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Spacer(
                        modifier = Modifier.height(35.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pitch"
                        )
                        Slider(
                            value = pitch / 3,
                            onValueChange = { pitch = it * 3 },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Speed"
                        )
                        Slider(
                            value = speechRate / 3,
                            onValueChange = { speechRate = it * 3 },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FloatingActionButton(
                            onClick = {
                                if (isBtnEnabled)
                                    isBtnEnabled = false
                                tts = TextToSpeech(
                                    context
                                ) {
                                    if (it == TextToSpeech.SUCCESS) {
                                        tts?.let { txtToSpeech ->
                                            txtToSpeech.language = Locale.ITALIAN
                                            txtToSpeech.setPitch(pitch)
                                            txtToSpeech.setSpeechRate(speechRate)
                                            txtToSpeech.speak(
                                                textFieldState,
                                                TextToSpeech.QUEUE_ADD,
                                                null,
                                                null
                                            )
                                        }
                                    }
                                }
                                isBtnEnabled = true
                            },
                            backgroundColor = YellowDark,
                            contentColor = GreyDark
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Speak"
                            )
                        }
                    }
                }
            }
        }
    }
}