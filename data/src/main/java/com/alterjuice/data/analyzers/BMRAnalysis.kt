package com.alterjuice.data.analyzers

import android.renderscript.Matrix2f
import com.alterjuice.domain.model.user.UserInfo
import com.alterjuice.domain.model.user.UserPAL
import com.alterjuice.domain.model.user.UserSex

/**
 *
 * TDWI=Basal Metabolic Rate (BMR)×Physical Activity Level (PAL)
 *
 * 1. Basal Metabolic Rate (BMR):
 * BMR for Men   = 88.3620 + (13.397×weight in kg) + (4.799×height in cm)−(5.677×age in years)
 * BMR for Women = 447.593 + (09.247×weight in kg) + (3.098×height in cm)−(4.330×age in years)
 * 2. Physical Activity Level (PAL):
 * @see UserPAL
 *
 *
 * Expression Katch-McArdle
 * BMR=370+(21.6×lean body mass in kg)
 * where 370 - base constant
 * where 21.6 - coefficient which is used to mark base BMR for each kilo of "dry weight" (lean body mass).
 * This mass is the representation of weight without fat percent.
 *
 * */
object BMRAnalysis {
    fun calculateBasalMetabolicRate(user: UserInfo): Double {
        return calculateBasalMetabolicRate(
            weight = user.weight, height = user.height, age = user.age, sex = user.sex
        )
    }

    fun calculateCaloriesBurned(bmr: Double, userPAL: UserPAL): Double {
        return bmr * userPAL.palLevel
    }

    fun calculateCaloriesBurned(user: UserInfo): Double {
        val bmr = calculateBasalMetabolicRate(user)
        return calculateCaloriesBurned(bmr, user.pal)
    }


    fun calculateBasalMetabolicRate(
        weight: Float, height: Float, age: Int, sex: UserSex
    ): Double {
        return when (sex) {
            UserSex.MALE -> {
                88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
            }
            UserSex.FEMALE -> {
                447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
            }
            UserSex.UNSPECIFIED -> {
                val male = calculateBasalMetabolicRate(weight, height, age, UserSex.MALE)
                val female = calculateBasalMetabolicRate(weight, height, age, UserSex.FEMALE)
                (male + female).div(2.0)
            }
        }
    }
}