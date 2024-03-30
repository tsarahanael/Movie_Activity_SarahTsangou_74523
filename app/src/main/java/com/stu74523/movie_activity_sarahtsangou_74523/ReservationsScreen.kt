package com.stu74523.movie_activity_sarahtsangou_74523

import androidx.activity.viewModels
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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stu74523.movie_activity_sarahtsangou_74523.ui.theme.Movie_Activity_SarahTsangou_74523Theme
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel


val Reservations: Route =
    Route(route = "reservations")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(
    navControler: NavController,
    reservations: MutableList<Reservation>,
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reservations",
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
                onClick = { navControler.navigate(MainScreen.route) },
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

            ReservationsBody(
                navControler = navControler,
                innerPadding = innerPadding,
                reservations = reservations,
                movieViewModel = movieViewModel
            )
        },
        bottomBar = {},
    )
}

@Composable
fun ReservationsBody(
    innerPadding: PaddingValues,
    navControler: NavController,
    reservations: MutableList<Reservation>,
    movieViewModel: MovieViewModel
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for ((i, reservation) in reservations.withIndex()) {
            Column(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
            )
            {
                //Title
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .width(300.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    AutoResizeText(
                        text = reservation.movie.name,
                        fontSizeRange = FontSizeRange(min = 12.sp, max = 40.sp),
                        color = Color.LightGray,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1,
                    )

                }

                //info
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    //info
                    Column(
                        modifier = Modifier
                            .height(50.dp)
                            .width(200.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
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
                            var actors = ""
                            for (actor in reservation.movie.starring) {
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
                                text = "${reservation.movie.running_time_mins} min",
                                fontSize = 17.sp,
                                color = Color.LightGray,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.Default,
                            )
                        }
                    }

                    //Seats Reserved
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .width(150.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.round_event_seat_24),
                            contentDescription = "seat icon",
                            tint = Color.Yellow
                        )

                        Text(
                            text = "${reservation.seats_selected} seats reserved",
                            fontSize = 15.sp,
                            color = Color.LightGray,
                            fontFamily = FontFamily.Default,
                        )
                    }
                }


                IconButton(onClick = {
                    reservation.movie.seats_remaining =
                        reservation.movie.seats_remaining + reservation.movie.seats_selected
                    reservation.movie.seats_selected = 0
                    movieViewModel.updateMovieData(reservation.movie)
                    reservations.remove(reservation)
                    navControler.navigate("${DetailScreen.baseRoute}${reservation.movie.movieId}")
                    {
                        popUpTo(MainScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete Icon",
                        tint = Color.Yellow,
                    )
                }
            }
            //separator
            Row(
                modifier = Modifier
                    .background(color = Color.Yellow)
                    .height(1.dp)
                    .width(250.dp),
            ) {}
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun ReservationsBodyPreview() {
    Movie_Activity_SarahTsangou_74523Theme {
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
        ReservationsBody(
            PaddingValues(0.dp),
            reservations = reservations,
            navControler = rememberNavController(),
            movieViewModel = MovieViewModel()
        )
    }
}