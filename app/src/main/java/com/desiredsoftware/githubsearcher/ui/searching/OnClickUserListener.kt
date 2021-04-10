package com.desiredsoftware.githubsearcher.ui.searching

import com.desiredsoftware.githubsearcher.data.Profile

interface OnClickUserListener {
    fun onClicked(userClicked: Profile)
}