package com.alterjuice.domain.model.user

enum class FitnessGoal(
    val description: String,
) {
    LoseWeight("Lose weight"),
    MaintainWeight("Maintain weight"),
    BuildMuscle("Build muscle");

    companion object {
        fun getByName(name: String?): FitnessGoal? {
            return values().find { it.name.equals(name, true) }
        }
    }
}