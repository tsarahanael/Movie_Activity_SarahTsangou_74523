package com.stu74523.movie_activity_sarahtsangou_74523

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.stu74523.movie_activity_sarahtsangou_74523.ui.theme.Movie_Activity_SarahTsangou_74523Theme

val MainScreen: Route = Route("main")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navControler: NavController, movieViewModel: MovieViewModel
) {

    val movieListState: MovieListState by movieViewModel.movieListState.collectAsState()
    when (movieListState) {
        is MovieListState.Success -> {
            Scaffold(topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Home Page",
                            color = Color.Black,
                            fontSize = 40.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Black
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Yellow,
                        scrolledContainerColor = Color.Black
                    )
                )
            },
                floatingActionButton = {
                    FloatingActionButton(
                        modifier = Modifier
                            .border(
                                width = 5.dp,
                                color = Color.Yellow,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        onClick = { navControler.navigate(Reservations.route) },
                        containerColor = Color.Black,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.List,
                            contentDescription = "Info Icon",
                            tint = Color.Yellow,
                        )
                    }
                },
                content = { innerPadding ->
                    MainBody(
                        navControler = navControler,
                        movieList = ((movieListState as MovieListState.Success).movieList),
                        innerPadding = innerPadding
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        actions = {/*TODO*/ },
                        containerColor = Color.Yellow
                    )
                }
            )
        }

        is MovieListState.Loading -> {
            Loading()
        }

        is MovieListState.Error -> {
            Error(message = (movieListState as MovieListState.Error).message)
        }
    }


}

@Composable
fun MainBody(navControler: NavController, movieList: List<Movie>, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            for (movie in movieList) {
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        val imageLoader = ImageLoader.Builder(LocalContext.current)
                            .components {
                                if (SDK_INT >= 28) {
                                    add(ImageDecoderDecoder.Factory())
                                } else {
                                    add(GifDecoder.Factory())
                                }
                            }
                            .build()
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = movie.image,
                            contentDescription = "Movie Poster"
                        )
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        //Title
                        Row(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            AutoResizeText(
                                text = movie.name,
                                fontSizeRange = FontSizeRange(min = 12.sp, max = 40.sp),
                                color = Color.LightGray,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                maxLines = 1,
                            )
                        }

                        Row(
                            modifier = Modifier
                                .background(color = Color.Yellow)
                                .height(1.dp)
                                .width(250.dp),
                        ) {}

                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            //info
                            Column(
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(250.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                //starring
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .padding(2.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Starring:",
                                        fontSize = 17.sp,
                                        color = Color.Yellow,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = FontFamily.Default,
                                    )
                                    var actors = ""
                                    for (actor in movie.starring) {
                                        actors += "$actor, "
                                    }
                                    Text(
                                        text = actors,
                                        fontSize = 15.sp,
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = FontFamily.Default,
                                    )
                                }

                                //running time
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .padding(2.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Running Time:",
                                        fontSize = 17.sp,
                                        color = Color.Yellow,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = FontFamily.Default,
                                    )
                                    Text(
                                        text = "${movie.running_time_mins} min",
                                        fontSize = 17.sp,
                                        color = Color.LightGray,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = FontFamily.Default,
                                    )
                                }
                            }

                            //More Button
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilledIconButton(
                                    modifier = Modifier
                                        .border(
                                            width = 2.dp,
                                            color = Color.Yellow,
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    onClick = { navControler.navigate(DetailScreen.baseRoute + movie.movieId) },
                                    colors = IconButtonDefaults.filledIconButtonColors(Color.Black),
                                    shape = RoundedCornerShape(12.dp),
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = "More Icon",
                                        tint = Color.Yellow,
                                        modifier = Modifier
                                            .fillMaxHeight(0.75f)
                                    )
                                }
                            }
                        }

                    }

                }
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun MainBodyPreview() {
    Movie_Activity_SarahTsangou_74523Theme {
        val movieList = mutableListOf<Movie>()
        repeat(10)
        {
            movieList.add(
                Movie(
                    name = "My Mooooooooooooooooooooooooviiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiieeeeeeeeeeeeeeeeee",
                    image = "https://picsum.photos/500/300?random=$it",
                    description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                    starring = listOf("Lorem", "Ipsum"),
                    running_time_mins = 90,
                    certification = "PG"
                )
            )
        }
        MainBody(rememberNavController(), movieList, PaddingValues(0.dp))
    }
}