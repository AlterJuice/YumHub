package com.alterjuice.domain.model.nutrition

enum class NutritionEnum(
    override val attrID: Int,
    override val usdaTag: String,
    override val nutritionName: String,
    override val unit: String
): NutritionAttr {
    Calcium(301, "CA", "Calcium, Ca", "mg"),
    Carbs(205, "CHOCDF", "Carbohydrate, by difference", "g"),
    Cholesterol(601, "CHOLE", "Cholesterol", "mg"),
    EnergyCalories(208, "ENERC_KCAL", "Energy", "kcal"),
    FatSat(606, "FASAT", "Fatty acids, total saturated", "g"),
    Fat(204, "FAT", "Total lipid (fat)", "g"),
    Item605(605, "FATRN", "Fatty acids, total trans", "g"),
    Iron(303, "FE", "Iron, Fe", "mg"),
    Fiber(291, "FIBTG", "Fiber, total dietary", "g"),
    Potassium(306, "K", "Potassium, K", "mg"),
    SodiumNa(307, "NA", "Sodium, Na", "mg"),
    Protein(203, "PROCNT", "Protein", "g"),
    Sugars(269, "SUGAR", "Sugars, total", "g"),
    SugarsAdded(539, "SUGAR_ADD", "Sugars, added", "g"),
    VitaminD(324, "VITD", "Vitamin D", "IU"),
    SugarAlc(299, "SUGAR_ALC", "Sugar Alcohol", "g"),
    Item261(261, "SORB", "Sorbitol", "g"),
    Alcohol(221, "ALC", "Alcohol, ethyl", "g"),
    Item207(207, "ASH", "Ash", "g"),
    Item514(514, "ASP_G", "Aspartic acid", "g"),
    Caffeine(262, "CAFFN", "Caffeine", "mg"),
    Item322(322, "CARTA", "Carotene, alpha", "Âµg"),
    Item321(321, "CARTB", "Carotene, beta", "Âµg"),
    VitaminD3(326, "CHOCAL", "Vitamin D3 (cholecalciferol)", "Âµg"),
    Item421(421, "CHOLN", "Choline, total", "mg"),
    EnergyKj(268, "ENERC_KJ", "Energy", "kJ"),
    VitaminD2(325, "ERGCAL", "Vitamin D2 (ergocalciferol)", "Âµg"),
    Glycerin(1002, "N/A", "Glycerin", "g");

    companion object {
        val map by lazy { values().associateBy { it.attrID } }
    }
}