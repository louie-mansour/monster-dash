package com.molasys.hermes.monster

import android.content.Context
import com.molasys.hermes.TestConfigs
import com.molasys.hermes.monster.chupacabra.Chupacabra
import com.molasys.hermes.monster.chupacabra.ChupacabraSettings
import com.molasys.hermes.monster.dinosaur.Dinosaur
import com.molasys.hermes.monster.dinosaur.DinosaurSettings
import com.molasys.hermes.monster.dinosaur.Zombie
import com.molasys.hermes.monster.ghost.Ghost
import com.molasys.hermes.monster.ghost.GhostSettings
import com.molasys.hermes.monster.vampire.Vampire
import com.molasys.hermes.monster.vampire.VampireSettings
import com.molasys.hermes.monster.werewolf.Werewolf
import com.molasys.hermes.monster.werewolf.WerewolfSettings
import com.molasys.hermes.monster.zombie.ZombieSettings

const val CHUPACABRA: String = "chupacabra"
const val DINOSAUR: String = "dinosaur"
const val GHOST: String = "ghost"
const val VAMPIRE: String = "vampire"
const val WEREWOLF: String = "werewolf"
const val ZOMBIE: String = "zombie"

class MonsterFactory(private val applicationContext: Context, private val testConfigs: TestConfigs) {
    fun make(monsterType: String): Monster {
        when (monsterType) {
            CHUPACABRA -> {
                return Chupacabra(
                    ChupacabraSettings.buildConfigs(testConfigs),
                    ChupacabraSettings.buildAudio(applicationContext)
                )
            }
            DINOSAUR -> {
                return Dinosaur(
                    DinosaurSettings.buildConfigs(testConfigs),
                    DinosaurSettings.buildAudio(applicationContext)
                )
            }
            GHOST -> {
                return Ghost(
                    GhostSettings.buildConfigs(testConfigs),
                    GhostSettings.buildAudio(applicationContext)
                )
            }
            VAMPIRE -> {
                return Vampire(
                    VampireSettings.buildConfigs(testConfigs),
                    VampireSettings.buildAudio(applicationContext)
                )
            }
            WEREWOLF -> {
                return Werewolf(
                    WerewolfSettings.buildConfigs(testConfigs),
                    WerewolfSettings.buildAudio(applicationContext)
                )
            }
            ZOMBIE -> {
                return Zombie(
                    ZombieSettings.buildConfigs(testConfigs),
                    ZombieSettings.buildAudio(applicationContext)
                )
            }
            else -> throw Exception("Unrecognized monster")
        }
    }
}