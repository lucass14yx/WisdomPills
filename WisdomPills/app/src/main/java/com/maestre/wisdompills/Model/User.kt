package com.maestre.wisdompills.Model

import android.provider.ContactsContract.CommonDataKinds.Nickname

data class User(var idUser: String? = null,
                var nickname: String? = null,
                var password: String? = null,
                var email: String? = null,)