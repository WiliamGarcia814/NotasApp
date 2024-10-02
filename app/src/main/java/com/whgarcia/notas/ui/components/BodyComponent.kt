package com.whgarcia.notas.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whgarcia.notas.model.Notas
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainTextField(value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 26.sp),
        modifier = Modifier
            .width(250.dp)
            .background(Color.White) // Fondo transparente para que no haya borde adicional
    ) { innerTextField ->
        Box( // Aquí puedes personalizar el borde manualmente
            modifier = Modifier
                .border(1.dp, Color.Gray)
                .padding(4.dp) // Ajusta este valor para el padding interno que desees
        ) {
            innerTextField()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTextField(value: String, onValueChange: (String) -> Unit){
    TextField(
        value = value,
        onValueChange = onValueChange, // Método para actualizar el contenido de la nota
        modifier = Modifier
            .fillMaxSize(),
        placeholder = { Text("Escribe tu nota aquí...") },
        textStyle = TextStyle(fontSize = 18.sp), // Ajusta el tamaño del texto
        maxLines = Int.MAX_VALUE, // Permitir múltiples líneas
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun dateAndTimeNow(): String{
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
    return currentDateTime.format(formatter)
}

fun dateAndTimeEditNow(): String{
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
    return currentDateTime.format(formatter)
}

@Composable
fun GridCard(item: Notas, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick), // Hacer la Card clicable
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Título de la nota
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1, // Limitar a una línea para previsualización
                overflow = TextOverflow.Ellipsis // Añadir puntos suspensivos si es muy largo
            )

            // Previsualización del contenido (primeras líneas)
            Text(
                text = item.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2, // Limitar a dos líneas para previsualización
                overflow = TextOverflow.Ellipsis // Añadir puntos suspensivos si es largo
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
            containerColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Título de la nota
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1, // Limitar a una línea para previsualización
                overflow = TextOverflow.Ellipsis // Añadir puntos suspensivos si es muy largo
            )

            // Previsualización del contenido (primeras líneas)
            Text(
                text = item.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2, // Limitar a dos líneas para previsualización
                overflow = TextOverflow.Ellipsis // Añadir puntos suspensivos si es largo
            )
        }
    }
}