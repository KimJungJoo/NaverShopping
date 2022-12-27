package com.android.jjkim.navershopping.app.check

import android.Manifest

class PermissionValues {
    companion object {
        val Locations = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val Contacts = arrayOf(
            Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
        )

        val Phones = arrayOf(
            Manifest.permission.READ_PHONE_STATE
        )

        val PhoneNumber = arrayOf(
            Manifest.permission.READ_PHONE_NUMBERS
        )

        val Storages = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val ReadStorage = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
}