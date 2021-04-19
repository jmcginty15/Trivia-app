package com.example.trivia.data.models

import com.google.gson.annotations.SerializedName

data class ResultsDTO(
    @SerializedName("results") val results: List<QuestionDTO>
)
