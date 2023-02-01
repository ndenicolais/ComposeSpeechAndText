package com.denicks21.speechandtext.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denicks21.speechandtext.MainActivity
import com.denicks21.speechandtext.ui.theme.GreyDark
import com.denicks21.speechandtext.ui.theme.YellowDark
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream

@Composable
fun SpeechToText() {
    val inputDialogState = remember { mutableStateOf(false) }
    val fileName = remember { mutableStateOf("") }
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val speechContext = context as MainActivity

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar() {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "SpeechToText",
                        modifier = Modifier.fillMaxWidth(),
                        color = GreyDark,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = speechContext.speechInput.value,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 30.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 20.dp
                        ),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            speechContext.askSpeechInput(context)
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .clip(RoundedCornerShape(45.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = YellowDark
                        )
                    ) {
                        Row() {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Speak",
                                modifier = Modifier
                                    .fillMaxSize(),
                                tint = GreyDark
                            )
                        }
                    }
                    Button(
                        onClick = {
                            if (speechContext.speechInput.value.isNotEmpty()) {
                                inputDialogState.value = true
                            } else {
                                Toast.makeText(
                                    context, "There are no text to save",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .clip(RoundedCornerShape(45.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = YellowDark
                        )
                    ) {
                        Row() {
                            Icon(
                                imageVector = Icons.Filled.Save,
                                contentDescription = "Save",
                                modifier = Modifier
                                    .fillMaxSize(),
                                tint = GreyDark
                            )
                        }
                        if (inputDialogState.value) {
                            AlertDialog(
                                onDismissRequest = { inputDialogState.value = false },
                                title = {
                                    Text(
                                        text = "File Name",
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                text = {
                                    OutlinedTextField(
                                        value = fileName.value,
                                        onValueChange = { fileName.value = it },
                                        label = {
                                            Text(
                                                text = "Insert file's name",
                                                color = GreyDark
                                            )
                                        },
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            textColor = GreyDark,
                                            backgroundColor = YellowDark,
                                            focusedBorderColor = GreyDark,
                                            unfocusedBorderColor = GreyDark,
                                            placeholderColor = GreyDark
                                        )
                                    )
                                },
                                confirmButton = {
                                    Button(onClick = {
                                        if (fileName.value.isNotEmpty() && speechContext.speechInput.value.isNotEmpty()) {
                                            writeToFile(context, fileName.value, speechContext.speechInput.value)
                                            inputDialogState.value = false
                                            Toast.makeText(
                                                context, "Saved",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context, "The field name is empty",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }) {
                                        Text(
                                            text = "Ok",
                                            color = GreyDark
                                        )
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = {
                                        inputDialogState.value = false
                                    }) {
                                        Text(
                                            text = "Cancel",
                                            color = GreyDark
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun writeToFile(context: Context, fileName: String, result: String) {
    val f = File(context.getExternalFilesDir("FileFolder"), "$fileName.txt")
    if (!f.exists()) {
        f.createNewFile()
    }
    val fileWriter = BufferedWriter(FileOutputStream(f).bufferedWriter())
    fileWriter.write(result)
    fileWriter.flush()
}