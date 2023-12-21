package com.alterjuice.data.analyzers

import com.alterjuice.domain.model.user.UserInfo
import com.alterjuice.domain.model.user.UserPAL
import com.alterjuice.utils.extensions.getNextDividable


/**
 * https://en.wikipedia.org/wiki/Harris%E2%80%93Benedict_equation
 * 1. Basal Metabolic Rate (BMR):
 * BMR for Men   = 88.3620 + (13.397×weight in kg) + (4.799×height in cm)−(5.677×age in years)
 * BMR for Women = 447.593 + (09.247×weight in kg) + (3.098×height in cm)−(4.330×age in years)
 * 2. Physical Activity Level (PAL):
 * @see UserPAL
 *
 * */
object WaterIntakeAnalysis {


    // Рекомендована кількість води (у мілілітрах) за день
    fun calculateTotalDailyWaterIntakeML(
        user: UserInfo, roundedBy: Long = 200L
    ): Long {
        val rawWaterIntake = user.weight * 0.03
        return rawWaterIntake.toLong().getNextDividable(roundedBy)
    }
}
