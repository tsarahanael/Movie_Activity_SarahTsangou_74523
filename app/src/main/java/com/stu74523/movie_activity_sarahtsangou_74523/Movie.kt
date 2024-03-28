package com.stu74523.movie_activity_sarahtsangou_74523


data class Movie constructor(
    val name: String = "",
    var image: String = "",
    val certification: String = "",
    val description: String = "",
    val starring: List<String> = listOf(),
    val running_time_mins: Int = 0,
    var seats_remaining: Int = 0,
    var seats_selected: Int = 0
) {

    var movieId: String = ""
}

class Reservation(val movie:Movie,val seats_selected:Int = (1..movie.seats_remaining).random())
{
}
