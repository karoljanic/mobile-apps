package com.example.newtonapi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newtonapi.service.NewtonFetch
import com.example.newtonapi.service.NewtonService
import java.net.URLEncoder


@Composable
fun Calculator() {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    val buttonsGrid = listOf(
        listOf(NewtonService.Operation.DERIVE, NewtonService.Operation.INTEGRATE),
        listOf(NewtonService.Operation.SIMPLIFY, NewtonService.Operation.FACTOR),
        listOf(NewtonService.Operation.AREA_UNDER_CURVE, NewtonService.Operation.TANGENT_LINE),
        listOf(NewtonService.Operation.SINE, NewtonService.Operation.COSINE),
        listOf(NewtonService.Operation.ARC_SINUS, NewtonService.Operation.ARC_COSINE),
        listOf(NewtonService.Operation.TANGENT, NewtonService.Operation.ARC_TANGENT),
        listOf(NewtonService.Operation.LOGARITHM, NewtonService.Operation.ABSOLUTE),
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = input, onValueChange = { newText ->
            input = newText
        }, label = { Text("Enter Input") })

        Spacer(modifier = Modifier.height(20.dp))

        Text("Result = $output")

        Spacer(modifier = Modifier.height(20.dp))

        Column(Modifier.fillMaxSize()) {
            buttonsGrid.forEach { rowButtons ->
                Row(Modifier.fillMaxWidth()) {
                    rowButtons.forEach { operation ->
                        Button(
                            onClick = {
                                NewtonFetch.getAnswer(
                                    operation, URLEncoder.encode(input, "UTF-8")
                                ) { result -> output = result }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            Text(text = operation.toString())
                        }
                    }
                }
            }
        }
    }
}