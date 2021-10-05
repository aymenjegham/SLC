package com.aymen.slc.global.helpers

import com.aymen.slc.data.model.Conferee


sealed class Navigation {


    data class Back(val ShouldFinish: Boolean) : Navigation()

    object Settings : Navigation()

    object Login : Navigation()

    object ToLogin : Navigation()

    object ToSuperAdmin : Navigation()

    object ToSecretariat : Navigation()

    data class  ToSecretariatDetails( val conferee: Conferee) : Navigation()

    object ToWorkshop : Navigation()

    object ToHotel : Navigation()

    object ToEvent : Navigation()

    object ToRestaurant : Navigation()

    object ToConference : Navigation()

    data class Home(val isConnected: Boolean) : Navigation()

    data class NavigationDetails(val string: String) : Navigation()


}