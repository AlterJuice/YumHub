package com.alterjuice.data.analyzers

import com.alterjuice.domain.model.user.UserInfo
import com.alterjuice.domain.model.user.UserPAL
import com.alterjuice.utils.extensions.getNextDividable


/**
 * The amount of water a person needs can vary based on various
 * factors such as weight, sex, height, physical activity level, and climate.
 * One common recommendation is to use the Total Daily Water Intake (TDWI) formula,
 * which considers several of these factors. The National Academies of Sciences,
 * Engineering, and Medicine suggests the following general guidelines:
 *
 * TDWI=Basal Metabolic Rate (BMR)×Physical Activity Level (PAL)
 *
 *
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
