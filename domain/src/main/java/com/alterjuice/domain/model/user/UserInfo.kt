package com.alterjuice.domain.model.user

import com.alterjuice.domain.model.common.MealCategories


/*
* BMR for Men   = 88.3620 + (13.397×weight in kg) + (4.799×height in cm)−(5.677×age in years)
* BMR for Women = 447.593 + (09.247×weight in kg) + (3.098×height in cm)−(4.330×age in years)
*
* */

interface UserInfoNullable {
    val favoriteCategories: List<MealCategories>?
    val username: String?
    val fitnessGoal: FitnessGoal? // Assume FitnessGoal is an enum indicating user's goal
    val weight: Float?
    val height: Float?
    val age: Int?
    val sex: UserSex?
    val pal: UserPAL?
}

interface UserInfo: UserInfoNullable {
    override val favoriteCategories: List<MealCategories>
    override val username: String
    override val fitnessGoal: FitnessGoal // Assume FitnessGoal is an enum indicating user's goal
    override val weight: Float
    override val height: Float
    override val age: Int
    override val sex: UserSex
    override val pal: UserPAL
}

data class UserInfoDataNullable(
    override val username: String? = null,
    override val weight: Float? = null,
    override val height: Float? = null,
    override val age: Int? = null,
    override val sex: UserSex? = null,
    override val pal: UserPAL? = null,
    override val fitnessGoal: FitnessGoal? = null,
    override val favoriteCategories: List<MealCategories>? = null,
): UserInfoNullable

data class UserInfoData(
    override val username: String,
    override val weight: Float,
    override val height: Float,
    override val age: Int,
    override val sex: UserSex = UserSex.UNSPECIFIED,
    override val pal: UserPAL = UserPAL.Sedentary,
    override val fitnessGoal: FitnessGoal = FitnessGoal.MaintainWeight,
    override val favoriteCategories: List<MealCategories> = emptyList(),
): UserInfo

