package com.molasys.hermes.monster

import com.molasys.hermes.users.User

fun distanceBetween(monster: Monster, user: User): Float {
    return user.distanceCovered() - monster.distanceCovered()
}