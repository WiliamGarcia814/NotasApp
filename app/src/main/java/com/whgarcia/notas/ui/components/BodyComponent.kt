package com.whgarcia.notas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whgarcia.notas.R
import com.whgarcia.notas.model.Notas
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainTextField(value: String, onValueChange: (String) -> Unit, enabled: Boolean) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 30.sp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.tf_title),
                fontSize = 30.sp
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            disabledTextColor = MaterialTheme.colorScheme.onBackground
        ),
        enabled = enabled
    )
}

@Composable
fun ContentTextField(value: String, onValueChange: (String) -> Unit, enabled: Boolean){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 18.sp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.tf_content),
                fontSize = 18.sp
            )
        },
        maxLines = Int.MAX_VALUE,
        modifier = Modifier.fillMaxSize(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            disabledTextColor = MaterialTheme.colorScheme.onBackground
        ),
        enabled = enabled
    )
}

fun dateAndTimeNow(): String{
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
    return currentDateTime.format(formatter)
}

@Composable
fun GridCard(item: Notas, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Hacer la Card clicable
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(item.color_note),
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Título de la nota
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = item.create_date,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ListCard(item: Notas, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Hacer la Card clicable
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(item.color_note),
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Título de la nota
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = item.create_date,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ColorPickerDialog(selectedColor: Color, onColorSelected: (Color) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(id = R.string.txt_color_note)) },
        text = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ColorButton(color = Color.Red, selectedColor = selectedColor, onClick = onColorSelected)
                ColorButton(color = Color.Green, selectedColor = selectedColor, onClick = onColorSelected)
                ColorButton(color = Color.Blue, selectedColor = selectedColor, onClick = onColorSelected)
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.txt_close))
            }
        }
    )
}

@Composable
fun ColorButton(color: Color, selectedColor: Color, onClick: (Color) -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(color, shape = CircleShape)  // Forma del color
            .border(4.dp, if (color == selectedColor) Color.Black else Color.Transparent, shape = CircleShape)  // Borde negro si el color está seleccionado
            .clickable { onClick(color) }
    )
}