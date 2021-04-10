package com.desiredsoftware.githubsearcher.data.commit

data class Verification(
    val payload: Any,
    val reason: String,
    val signature: Any,
    val verified: Boolean
)