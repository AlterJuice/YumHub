package com.alterjuice.domain.model.common

enum class MealCategories {

    Cuisine,
    CuisineItalian, CuisineMexican, CuisineAsian, CuisineIndian, CuisineGreek, CuisineThai,

    DietaryPreferences,
    DietaryPreferencesVegetarian, DietaryPreferencesVegan, DietaryPreferencesGlutenFree,
    DietaryPreferencesLowCarb, DietaryPreferencesKeto, DietaryPreferencesPaleo,

    Diets,
    DietsDiabeticFriendly, DietsLowCalorie, DietsLowFat,

    CookType,
    CookTypeBoiled, CookTypeFried, CookTypeReadyToEat, CookTypeGrilled, CookTypeBaked,

    Flavors,
    FlavorsSpicy, FlavorsSweet, FlavorsSavory, FlavorsSour,

    Occasions,
    OccasionsParty, OccasionsHoliday, OccasionsPicnic, OccasionsBarbecue,

    Type,
    TypeMeat, TypeSeafood, TypeChicken, TypeBeef, TypePizza, TypePasta, TypeSalad, TypeSoup,
    TypeSandwich, TypeFastFood, TypeFruits, TypeSweets, TypeDessert, TypeSnack,
    TypeChocolate, TypePie, TypePanini, TypeSauce, TypeVegetables, TypeMuffins, TypeDrink,

    Serving,
    ServingMain, ServingDessert, ServingSecond, ServingSides, ServingComponents,
    ServingDressing,

    MealType,
    MealTypeBreakfast, MealTypeDinner, MealTypeSnack, MealTypeLunch, MealTypeSupper,
    MealTypeDessert, MealTypeAppetizer,

    Category,
    CategoryBeef, CategoryBreakfast, CategoryChicken, CategoryDessert, CategoryGoat,
    CategoryLamb, CategoryMiscellaneous, CategoryPast, CategoryPork, CategorySeafood,
    CategorySide, CategoryStarter, CategoryVegan, CategoryVegetarian, CategoryDressing,


    ;
    fun isSpecifiedCuisine() = this.name.startsWith(Cuisine.name)
    fun isDietaryPreferences() = this.name.startsWith(DietaryPreferences.name)
    fun isSpecifiedDiets() = this.name.startsWith(Diets.name)
    fun isSpecifiedCookTypes() = this.name.startsWith(CookType.name)
    fun isSpecifiedFlavors() = this.name.startsWith(Flavors.name)
    fun isSpecifiedOccasions() = this.name.startsWith(Occasions.name)
    fun isSpecifiedType() = this.name.startsWith(Type.name)
    fun isSpecifiedServing() = this.name.startsWith(Serving.name)
    fun isSpecifiedMealTypes() = this.name.startsWith(MealType.name)
    fun isSpecifiedCategories() = this.name.startsWith(Category.name)

    companion object {
        private val titles by lazy {
            listOf(
                Cuisine, DietaryPreferences, Diets,
                CookType, Flavors, Occasions,
                Type, Serving, MealType, Category,
            )
        }
        val mealCategoriesMap by lazy {
            titles.associateWith { key -> values().filter { it.name.startsWith(key.name) && it.name.length > key.name.length } }
        }
        val cuisine get() = mealCategoriesMap[Cuisine]
        val dietaryPreferences get() = mealCategoriesMap[DietaryPreferences]
        val diets get() = mealCategoriesMap[Diets]
        val cookTypes get() = mealCategoriesMap[CookType]
        val flavors get() = mealCategoriesMap[Flavors]
        val occasions get() = mealCategoriesMap[Occasions]
        val types get() = mealCategoriesMap[Type]
        val serving get() = mealCategoriesMap[Serving]
        val mealTypes get() = mealCategoriesMap[MealType]
        val categories get() = mealCategoriesMap[Category]

        fun findTagsByContent(tag: String) = values().filter {
            it.name.contains(tag, ignoreCase = true)
        }
    }

}


