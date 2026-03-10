package com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Deprecated(
    message = "This interface will be replaced by Stepik alternative",
    replaceWith = ReplaceWith(
        expression = "RemoteStepikCourseModel"
    )
)
data class RedditPostModel(
    val id: String,
    val author: String,
    val subreddit: String,
    val title: String,
    val contentText: String? = null,
    val likesCount: Int,
    val commentsCount: Int,
    val hoursAgo: Int
)

@Serializable
data class RemoteStepikCourseModel(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("summary")
    val summary: String,
    @SerialName("cover")
    val coursePicture: String,
    @SerialName("display_price")
    val price: String
)