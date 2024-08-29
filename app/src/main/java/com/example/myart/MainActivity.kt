package com.example.myart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myart.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ArtSpaceScreen()
                }
            }
        }
    }


    @Composable
    fun ArtSpaceTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colors = if (darkTheme) {
            darkColorScheme()
        } else {
            lightColorScheme()
        }

        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }


    private fun darkColorScheme() = darkColorScheme(
        primary = Color(0xFFBB86FC),
        secondary = Color(0xFF03DAC5),
        background = Color(0xFF121212),
        surface = Color(0xFF121212),
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color.White,
        onSurface = Color.White,
    )

    private fun lightColorScheme() = lightColorScheme(
        primary = Color(0xFF6200EE),
        secondary = Color(0xFF03DAC5),
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = Color.Black,
        onSurface = Color.Black,
    )


    private val Typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )


    private val Shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(16.dp)
    )
}


data class Artwork(
    val imageResId: Int,
    val title: String,
    val artist: String,
    val year: String
)

@Composable
fun ArtSpaceScreen() {

    val artworks = remember {
        mutableStateListOf(
            Artwork(R.drawable.artwork1, "Low Miata", "Von Zipper", "2024"),
            Artwork(R.drawable.artwork2, "Chaser", "Adam LZ", "2023"),
            Artwork(R.drawable.artwork3, "supra", "Taffu", "2022")
        )
    }


    var currentIndex by remember { mutableStateOf(0) }

    var editableTitle by remember { mutableStateOf(artworks[currentIndex].title) }
    var editableArtist by remember { mutableStateOf(artworks[currentIndex].artist) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    BorderStroke(2.dp, Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = artworks[currentIndex].imageResId),
                contentDescription = artworks[currentIndex].title,
                modifier = Modifier.fillMaxSize()
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(
                    BorderStroke(2.dp, Color.Blue),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextField(
                    value = editableTitle,
                    onValueChange = { newTitle ->
                        editableTitle = newTitle
                        artworks[currentIndex] = artworks[currentIndex].copy(title = newTitle)
                    },
                    textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )


                TextField(
                    value = editableArtist,
                    onValueChange = { newArtist ->
                        editableArtist = newArtist
                        artworks[currentIndex] = artworks[currentIndex].copy(artist = newArtist)
                    },
                    textStyle = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text(
                    text = "(${artworks[currentIndex].year})",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {

                    if (currentIndex > 0) {
                        currentIndex--
                        editableTitle = artworks[currentIndex].title
                        editableArtist = artworks[currentIndex].artist
                    }
                },
                enabled = currentIndex > 0
            ) {
                Text(text = "Previous")
            }
            Button(
                onClick = {

                    if (currentIndex < artworks.size - 1) {
                        currentIndex++
                        editableTitle = artworks[currentIndex].title
                        editableArtist = artworks[currentIndex].artist
                    }
                },
                enabled = currentIndex < artworks.size - 1
            ) {
                Text(text = "Next")
            }
        }
    }
}
