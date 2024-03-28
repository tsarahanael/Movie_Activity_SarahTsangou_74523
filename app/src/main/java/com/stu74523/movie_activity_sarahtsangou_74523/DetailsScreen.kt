package com.stu74523.movie_activity_sarahtsangou_74523

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.stu74523.movie_activity_sarahtsangou_74523.ui.theme.Movie_Activity_SarahTsangou_74523Theme
import androidx.activity.viewModels
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.lifecycle.viewmodel.compose.viewModel


val DetailScreen: Route = Route(route = "details/{movieId}", baseRoute = "details/")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navControler: NavController,
    movieViewModel: MovieViewModel,
    reservations: MutableList<Reservation>,
    modifier: Modifier = Modifier
) {
    val movieState: MovieState by movieViewModel.movieState.collectAsState()

    when (movieState) {
        is MovieState.Success -> {

            var seats_selected: Int by remember { mutableStateOf<Int>(0) }

            Scaffold(
                topBar = {
                    DetailsTopAppBar(
                        movie = (movieState as MovieState.Success).movie,
                        navControler = navControler,
                        movieViewModel = movieViewModel,
                        reservations = reservations,
                        seats_selected = seats_selected
                    )
                },
                content = { innerPadding ->

                    DetailsBody(
                        movie = (movieState as MovieState.Success).movie,
                        reservations = reservations,
                        innerPadding = innerPadding
                    )
                },
                bottomBar = {
                    DetailsBottomAppBar(
                        (movieState as MovieState.Success).movie,
                        reservations = reservations,
                        seats_selected = seats_selected,
                        onSelect = { seats_selected = it })
                },
            )
        }

        is MovieState.Loading -> {
            Loading()
        }

        is MovieState.Error -> {
            Error(message = (movieState as MovieState.Error).message)
        }
    }

}

@Composable
fun DetailsBody(movie: Movie, reservations: MutableList<Reservation>, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //Image
        Row(
            modifier = Modifier
                .fillMaxHeight(0.33f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    if (Build.VERSION.SDK_INT >= 28) {
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
                placeholder = /*painterResource(R.drawable.no_image_found)*/rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = R.drawable.loading).apply(block = {
                            size(Size.ORIGINAL)
                        }).build(), imageLoader = imageLoader
                ),
                error = painterResource(R.drawable.no_image_found),
                contentDescription = "Movie Poster"
            )
        }

        //info
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxHeight(0.87f)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        )
        {
            //Title
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                AutoResizeText(
                    text = movie.name,
                    fontSizeRange = FontSizeRange(min = 12.sp, max = 40.sp),
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Row(
                modifier = Modifier
                    .background(color = Color.Yellow)
                    .height(1.dp)
                    .width(250.dp),
            ) {}

            //info
            Column(
                modifier = Modifier
                    .fillMaxHeight(1f / 3f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            )
            {
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
                    for (actor in movie.starring) {
                        Text(
                            text = "$actor, ",
                            fontSize = 17.sp,
                            color = Color.LightGray,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Default,
                        )
                    }
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


            //Synopsis
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "Synopsis",
                    fontSize = 20.sp,
                    color = Color.Yellow,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Default,
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .border(
                            width = 2.dp,
                            color = Color.Yellow,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {

                    val scrollState = rememberScrollState()
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxHeight(0.75f)
                            .background(Color.Transparent),
                    ) {
                        Text(
                            modifier = Modifier.verticalScroll(scrollState),
                            text = movie.description,
                            fontSize = 17.sp,
                            color = Color.LightGray,
                            fontFamily = FontFamily.Default,
                            textAlign = TextAlign.Start,
                        )
                    }

                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Down Arrow",
                        tint = if (scrollState.value != scrollState.maxValue) Color.Yellow else Color.Transparent,
                    )
                }
            }
        }

        //Seat Selection

    }
}

@Composable
fun DetailsBottomAppBar(
    movie: Movie,
    reservations: MutableList<Reservation>,
    seats_selected: Int,
    onSelect: (Int) -> Unit
) {

    var reserved: Boolean = false
    for (reservation in reservations) {
        reserved = reserved || movie.movieId == reservation.movie.movieId
    }

    Row(
        modifier = Modifier
            .background(color = Color.Yellow)
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp, 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {

        if (!reserved) {
            Row(
                modifier = Modifier
                    .width(170.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Seats",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Default,
                )



                FilledIconButton(
                    onClick = { onSelect(seats_selected + 1) },
                    shape = CircleShape,
                    enabled = seats_selected < movie.seats_remaining,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        Color.Black,
                        Color.Yellow,
                        Color.DarkGray,
                        Color.LightGray
                    ),
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add",
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }


                Text(
                    text = "$seats_selected",
                    fontSize = 17.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Default,
                )

                FilledIconButton(
                    onClick = { onSelect(seats_selected - 1) },
                    shape = CircleShape,
                    enabled = seats_selected > 0,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        Color.Black,
                        Color.Yellow,
                        Color.DarkGray,
                        Color.LightGray
                    ),
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_remove_24),
                        contentDescription = "Add",
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .width(170.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {


            if (!reserved) {
                Icon(
                    painter = painterResource(id = R.drawable.round_event_seat_24),
                    contentDescription = "seat icon",
                    tint = Color.Black
                )

                Text(
                    text = "${movie.seats_remaining} seats remaining",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = FontFamily.Default,
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.round_event_seat_24),
                    contentDescription = "seat icon",
                    tint = Color.Green
                )

                Text(
                    text = "${movie.seats_selected} seats selected",
                    fontSize = 15.sp,
                    color = Color.Green,
                    fontFamily = FontFamily.Default,
                )
            }

        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopAppBar(
    movie: Movie,
    reservations: MutableList<Reservation>,
    seats_selected: Int,
    navControler: NavController,
    movieViewModel: MovieViewModel,
) {
    TopAppBar(
        title = {
            Text(
                text = "Details",
                color = Color.Black,
                fontSize = 40.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Yellow),
        navigationIcon = {
            IconButton(
                onClick = { navControler.popBackStack() },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Black),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Add",
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
        },
        actions = {
            var reserved: Boolean = false
            for (reservation in reservations) {
                reserved = reserved || movie.movieId == reservation.movie.movieId
            }
            Button(
                onClick = {
                    try {
                        movie.seats_remaining = movie.seats_remaining - seats_selected
                        movie.seats_selected = movie.seats_selected + seats_selected
                        movieViewModel.updateMovieData(movie)
                        reservations.add(Reservation(movie = movie, seats_selected))
                        navControler.navigate(Reservations.baseRoute) {
                            popUpTo(MainScreen.route) {
                                inclusive = true
                            }
                        }
                    } catch (e: Exception) {
                        navControler.navigate("error/${e.message}")
                    }
                },
                colors = ButtonDefaults.buttonColors(contentColor = Color.Black),
                enabled = seats_selected > 0 && !reserved
            ) {
                Text(
                    text = "Confirm Selection",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DetailsBodyPreview() {
    Movie_Activity_SarahTsangou_74523Theme {
        val movie = Movie(
            name = "MyMovie",
            image = "https://picsum.photos/500/300?random=1",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            starring = listOf("Lorem", "Ipsum"),
            running_time_mins = 90,
            certification = "PG",
            seats_remaining = (0..15).random(),
        )

        val reservations: MutableList<Reservation> = mutableListOf()
        repeat(5)
        {
            reservations.add(
                Reservation(
                    movie = Movie(
                        name = "MyMovie",
                        image = "https://picsum.photos/500/300?random=1",
                        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                        starring = listOf("Lorem", "Ipsum"),
                        running_time_mins = 90,
                        certification = "PG",
                        seats_remaining = (0..15).random(),
                    )
                )
            )
        }


        DetailsBody(movie, reservations, PaddingValues(0.dp))
    }
}


/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    Movie_Activity_SarahTsangou_74523Theme {
        val movie = Movie(
            name = "MyMovie",
            image = "https://picsum.photos/500/300?random=1",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            starring = listOf("Lorem", "Ipsum"),
            running_time_mins = 90,
            certification = "PG",
            seats_remaining = (0..15).random(),
        )

        val reservations: MutableList<Reservation> = mutableListOf()
        repeat(10)
        {
            reservations.add(
                Reservation(
                    movie = Movie(
                        name = "MyMovie",
                        image = "https://picsum.photos/500/300?random=1",
                        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                        starring = listOf("Lorem", "Ipsum"),
                        running_time_mins = 90,
                        certification = "PG",
                        seats_remaining = (0..15).random(),
                    )
                )
            )
        }

         val movieViewModel: MovieViewModel by viewModel()



        var seats_selected:Int by remember { mutableStateOf<Int>(0)        }
        Scaffold(
            topBar = {
                DetailsTopAppBar(movie = movie, navControler = rememberNavController(), movieViewModel = movieViewModel,seats_selected = seats_selected)
            },
            content = { innerPadding ->

                DetailsBody(
                    movie = movie,
                    reservations = reservations,
                    innerPadding = innerPadding
                )
            },
            bottomBar = {
                DetailsBottomAppBar(movie = movie, seats_selected, onSelect = {seats_selected = it})
            },
        )
    }
}

*/