package com.denicks21.speechandtext.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.denicks21.speechandtext.BuildConfig
import com.denicks21.speechandtext.navigation.NavScreens
import com.denicks21.speechandtext.ui.theme.GreyDark
import com.denicks21.speechandtext.ui.theme.YellowDark

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar() {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "HomeScreen",
                            modifier = Modifier.fillMaxWidth(),
                            color = GreyDark,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .padding(top = 300.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            navController.navigate(NavScreens.SpeechToText.route)
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(80.dp)
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = GreyDark),
                    ) {
                        Text(
                            text = "SpeechToText",
                            modifier = Modifier.padding(5.dp),
                            color = YellowDark,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {
                            navController.navigate(NavScreens.TextToSpeech.route)
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(80.dp)
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = GreyDark),
                    ) {
                        Text(
                            text = "TextToSpeech",
                            modifier = Modifier.padding(5.dp),
                            color = YellowDark,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "v." + BuildConfig.VERSION_NAME,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}