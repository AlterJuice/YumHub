package com.alterjuice.domain.model.common

enum class MealCategories {
    // Allergens
    AllergensNuts, AllergensDairy, AllergensGluten, AllergensSoy, AllergensShellfish,

    // Cuisine
    CuisineItalian, CuisineMexican, CuisineAsian, CuisineIndian, CuisineGreek, CuisineThai,

    // DietaryPreferences
    DietaryPreferencesVegetarian, DietaryPreferencesVegan, DietaryPreferencesGlutenFree,
    DietaryPreferencesLowCarb, DietaryPreferencesKeto, DietaryPreferencesPaleo,

    // Diets
    DietsDiabeticFriendly, DietsLowCalorie, DietsLowFat,

    // CookType
    CookTypeBoiled, CookTypeFried, CookTypeReadyToEat, CookTypeGrilled, CookTypeBaked,

    // Flavors
    FlavorsSpicy, FlavorsSweet, FlavorsSavory, FlavorsSour,

    // Occasions
    OccasionsParty, OccasionsHoliday, OccasionsPicnic, OccasionsBarbecue,

    // Type
    TypeMeat, TypeSeafood, TypeChicken, TypeBeef, TypePizza, TypePasta, TypeSalad, TypeSoup,
    TypeSandwich, TypeFastFood, TypeFruits, TypeSweets, TypeDessert, TypeSnack,
    TypeChocolate, TypePie, TypePanini, TypeSauce, TypeVegetables, TypeMuffins, TypeDrink,

    // Serving Type
    ServingMain, ServingDessert, ServingSecond, ServingSides, ServingComponents,

    // MealType
    MealTypeBreakfast, MealTypeDinner, MealTypeSnack, MealTypeLunch, MealTypeSupper,
    MealTypeDessert, MealTypeAppetizer,


    ;

    companion object {
        fun findTagsByContent(tag: String) = values().filter {
            it.name.contains(tag, ignoreCase = true)
        }
    }

}


