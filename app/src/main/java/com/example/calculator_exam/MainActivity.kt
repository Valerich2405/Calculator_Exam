package com.example.calculator_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun CalculatorApp() {
    var input1 by remember { mutableStateOf(TextFieldValue()) }
    var input2 by remember { mutableStateOf(TextFieldValue()) }
    var result by remember { mutableStateOf("") }

    CalculatorScreen(
        input1 = input1,
        input2 = input2,
        onInput1Change = { input1 = it },
        onInput2Change = { input2 = it },
        onResultChange = { result = it },
        result = result
    )
}

@Composable
fun CalculatorScreen(
    input1: TextFieldValue,
    input2: TextFieldValue,
    onInput1Change: (TextFieldValue) -> Unit,
    onInput2Change: (TextFieldValue) -> Unit,
    onResultChange: (String) -> Unit,
    result: String
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = input1,
            onValueChange = onInput1Change,
            label = { Text("Число 1") }
        )
        TextField(
            value = input2,
            onValueChange = onInput2Change,
            label = { Text("Число 2") }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("+", "-", "*", "/").forEach { op ->
                Button(onClick = {
                    val num1 = input1.text.toDoubleOrNull()
                    val num2 = input2.text.toDoubleOrNull()
                    val calcResult = when (op) {
                        "+" -> num1?.plus(num2 ?: 0.0)
                        "-" -> num1?.minus(num2 ?: 0.0)
                        "*" -> num1?.times(num2 ?: 1.0)
                        "/" -> if (num2 == 0.0) null else num1?.div(num2 ?: 1.0)
                        else -> null
                    }
                    onResultChange(calcResult?.toString() ?: "Помилка")
                }) {
                    Text(op, fontSize = 20.sp)
                }
            }
        }

        Text("Результат: $result", fontSize = 24.sp)
    }
}