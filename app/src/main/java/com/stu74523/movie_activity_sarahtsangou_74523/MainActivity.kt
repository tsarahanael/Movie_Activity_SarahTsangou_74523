package com.stu74523.movie_activity_sarahtsangou_74523

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stu74523.movie_activity_sarahtsangou_74523.ui.theme.Movie_Activity_SarahTsangou_74523Theme


class MainActivity : ComponentActivity() {

    private val movieViewModel: MovieViewModel by viewModels()
    private  val myReservations: MutableList<Reservation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Movie_Activity_SarahTsangou_74523Theme {
                movieViewModel.fetchMovieListData(init = true)
                AppNavigation(movieViewModel, myReservations)
            }
        }
    }
}

