package com.aymen.slc.data.model.user

enum class UsersType(val value: Int) {

    SUPER_ADMIN(0),

    SECRETARIAT(1),

    WORKSHOP_ACCESS_CONTROL(2),

    HOTEL_ACCESS_CONTROL(3),

    EVENT_ACCESS_CONTROL(4),

    RESTAURANT(5),

    CONFERENCE(6);


    companion object {
        fun from(type: Int): UsersType? = values().find { it.value == type }
    }


}