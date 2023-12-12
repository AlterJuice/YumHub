package com.alterjuice.domain.model.user


/**
 * These activity levels are used in the Total Daily Energy Expenditure (TDEE) formula, where:
 * TDEE = BMR * PAL
 *
 * - BMR (Basal Metabolic Rate): The amount of energy expended while at rest in a neutrally temperate environment, in the post-absorptive state.
 * - PAL (Physical Activity Level): The factor that accounts for the energy expended through physical activity.
 * */
enum class UserPAL(
    val palLevel: Double,
    val description: String, /* TODO: Replace with ResID Strings */
    val subDescription: String,
) {
    Sedentary(1.2, "No exercises", ""),
    LightlyActive(1.375, "Light exercises", "1-3 days/week"),
    ModeratelyActive(1.55, "Moderate exercises", "3-5 days/week"),
    VeryActive(1.725, "Hard exercises", "6-7 days a week"),
    ExtraActive(1.9, "Very hard exercises", "sports and a physical job");

    companion object {
        fun getByName(name: String?) = UserPAL.values().find {
            it.name.equals(name, true)
        }
    }

}