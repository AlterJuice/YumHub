package com.alterjuice.domain.model.nutrition

enum class NutritionEnum(
    override val attrID: Int,
    override val usdaTag: String,
    override val nutritionName: String,
    override val unit: String,
    override val importanceKey: Int = 999,
): NutritionAttr {
    EnergyCalories(208, "ENERC_KCAL", "Energy", "kcal", 1),
    EnergyKj(268, "ENERC_KJ", "Energy", "kJ", 1),
    Protein(203, "PROCNT", "Protein", "g", 2),
    Fat(204, "FAT", "Total lipid (fat)", "g", 3),
    FatSat(606, "FASAT", "Fatty acids (saturated)", "g", 4),
    Carbs( 205, "CHOCDF", "Carbohydrate", "g", 5),
    Calcium(301, "CA", "Calcium, Ca", "mg", 6),
    Sugars(269, "SUGAR", "Sugars (total)", "g", 7),
    SugarsAdded(539, "SUGAR_ADD", "Sugars (added)", "g", 8),
    SugarAlc(299, "SUGAR_ALC", "Sugar Alcohol", "g", 9),
    SodiumNa(307, "NA", "Sodium", "mg", 10),
    Iron(303, "FE", "Iron (Fe)", "mg", 11),
    Fiber(291, "FIBTG", "Fiber", "g", 12),
    Caffeine(262, "CAFFN", "Caffeine", "mg", 13),
    Alcohol(221, "ALC", "Alcohol, ethyl", "g", 15),
    Cholesterol(601, "CHOLE", "Cholesterol", "mg"),
    Item605(605, "FATRN", "Fatty acids", "g"),
    Potassium(306, "K", "Potassium", "mg"),
    VitaminD(324, "VITD", "Vitamin D", "IU", 20),
    VitaminD2(325, "ERGCAL", "Vitamin D2", "Âµg", 21),
    VitaminD3(326, "CHOCAL", "Vitamin D3 (cholecalciferol)", "Âµg", 22),
    Item261(261, "SORB", "Sorbitol", "g"),
    Item207(207, "ASH", "Ash", "g"),
    Item514(514, "ASP_G", "Aspartic acid", "g"),
    Item322(322, "CARTA", "Carotene, (alpha)", "Âµg"),
    Item321(321, "CARTB", "Carotene (beta)", "Âµg"),
    Item421(421, "CHOLN", "Choline", "mg"),
    Glycerin(1002, "N/A", "Glycerin", "g");

    companion object {
        val map by lazy { values().associateBy { it.attrID } }
    }
}