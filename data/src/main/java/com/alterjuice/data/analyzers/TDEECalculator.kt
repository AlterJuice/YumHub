package com.alterjuice.data.analyzers

import com.alterjuice.domain.model.user.UserInfo
import com.alterjuice.domain.model.user.UserPAL

object TDEECalculator {
    fun calculateTDEE(userInfo: UserInfo): Double {
        return BMRAnalysis.calculateBasalMetabolicRate(userInfo) * userInfo.pal.palLevel
    }
}