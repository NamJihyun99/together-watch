package com.example.together_watch.data


data class Promise(
    val name: String,
    val ownerId: String,
    val users: List<String>,
    val status: Status,
    val dates: List<String>,
    val startTime: String,
    val endTime: String,
    val place: String
)

data class FetchedPromise(
    val id: String,
    val promise: Promise
)

fun Promise.toMap(): Map<String, Any> {
    return mapOf(
        "name" to name,
        "ownerId" to ownerId,
        "users" to users,
        "status" to status.toString(),
        "dates" to dates,
        "startTime" to startTime,
        "endTime" to endTime,
        "place" to place
    )
}

enum class Status {
    ONPROGRESS,
    COMPLETED,
    TIMEPASSED
}