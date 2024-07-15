package com.example.assignmet1app

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Recipe(
    val name: String,
    val ingredients: List<String>,
    val cookingTime: String,
    val instructions: List<String>,

)

class FavoritesActivity : AppCompatActivity() {

    private lateinit var favoritesListView: ListView
    private lateinit var backToHomeButton: Button

    private val recipesList = listOf(
        Recipe(
            "Spaghetti Carbonara",
            listOf("400g spaghetti", "175g pancetta, diced", "2 cloves garlic, minced", "4 large eggs", "1 cup grated Parmesan cheese", "Salt and black pepper to taste"),
            "20 minutes",
            listOf(
                "Cook spaghetti in salted boiling water according to package instructions.",
                "In a pan, fry pancetta until it is crispy. Add garlic and sauté briefly.",
                "Beat eggs and Parmesan together in a bowl.",
                "Drain spaghetti and while hot, mix with the egg mixture and pancetta. The heat from the pasta cooks the eggs.",
                "Season with salt and pepper and serve immediately."
            ),

        ),
        Recipe(
            "Margherita Pizza",
            listOf("1 pizza dough", "200g tomato sauce", "200g sliced mozzarella cheese", "Fresh basil leaves", "Olive oil for drizzling", "Salt to taste"),
            "15 minutes",
            listOf(
                "Preheat your oven to 500°F (260°C).",
                "Roll out the pizza dough and place it on a baking tray.",
                "Spread tomato sauce over the dough, then arrange mozzarella slices on top.",
                "Bake in the oven for 10-12 minutes until the crust is golden and cheese is bubbly.",
                "Once out of the oven, add fresh basil leaves and drizzle with olive oil before serving."
            ),

        ),
        Recipe(
            "Lasagna",
            listOf("500g ground beef", "1 onion, chopped", "2 cloves garlic, minced", "1 can (800g) tomato sauce", "1 can (400g) diced tomatoes", "2 cups ricotta cheese", "1 egg", "3 cups shredded mozzarella cheese", "1/2 cup grated Parmesan cheese", "12 lasagna noodles, cooked", "Salt and black pepper to taste"),
            "1 hour 30 minutes",
            listOf(
                "Preheat oven to 375°F (190°C).",
                "In a large skillet, cook ground beef, onion, and garlic over medium heat until well browned. Stir in tomato sauce and diced tomatoes. Season with salt and black pepper. Simmer for 30 minutes.",
                "In a mixing bowl, combine ricotta cheese, egg, and 1/2 of the Parmesan cheese. Mix well.",
                "Spread 1/3 of the meat sauce in the bottom of a 9x13 inch baking dish. Place 4 noodles over the sauce. Spread 1/2 of the ricotta cheese mixture over noodles."
            ),

        ),
        Recipe(
            "Tacos",
            listOf("500g ground beef", "1 packet taco seasoning", "12 taco shells", "1 cup shredded lettuce", "1 cup diced tomatoes", "1 cup shredded cheese", "1/2 cup sour cream", "1/2 cup salsa"),
            "20 minutes",
            listOf(
                "In a large skillet, cook ground beef over medium heat until browned. Drain excess fat.",
                "Add taco seasoning and water according to package instructions. Simmer for 5 minutes.",
                "Warm taco shells according to package instructions.",
                "Fill each taco shell with ground beef mixture. Top with lettuce, tomatoes, cheese, sour cream, and salsa. Serve immediately."
            ),

        ),
        Recipe(
            "Burritos",
            listOf("500g ground beef", "1 packet burrito seasoning", "8 large flour tortillas", "1 can refried beans", "1 cup shredded lettuce", "1 cup diced tomatoes", "1 cup shredded cheese", "1/2 cup sour cream", "1/2 cup salsa"),
            "25 minutes",
            listOf(
                "In a large skillet, cook ground beef over medium heat until browned. Drain excess fat.",
                "Add burrito seasoning and water according to package instructions. Simmer for 5 minutes.",
                "Warm tortillas according to package instructions.",
                "Spread a layer of refried beans on each tortilla. Add ground beef mixture, lettuce, tomatoes, cheese, sour cream, and salsa.",
                "Fold the sides of the tortilla over the filling, then roll up from the bottom. Serve immediately."
            ),

        ),
        Recipe(
            "Guacamole",
            listOf("3 ripe avocados", "1 lime, juiced", "1 teaspoon salt", "1/2 cup diced onion", "3 tablespoons chopped fresh cilantro", "2 roma tomatoes, diced", "1 teaspoon minced garlic", "1 pinch ground cayenne pepper"),
            "10 minutes",
            listOf(
                "In a medium bowl, mash together the avocados, lime juice, and salt.",
                "Mix in onion, cilantro, tomatoes, and garlic.",
                "Stir in cayenne pepper. Refrigerate 1 hour for best flavor, or serve immediately."
            ),

        ),
        Recipe(
            "Sweet and Sour Chicken",
            listOf("500g chicken breast, diced", "1 bell pepper, diced", "1 onion, diced", "1 can pineapple chunks, drained", "1/4 cup soy sauce", "1/4 cup rice vinegar", "1/4 cup ketchup", "1/4 cup brown sugar", "2 tablespoons cornstarch", "2 tablespoons water", "2 tablespoons vegetable oil"),
            "30 minutes",
            listOf(
                "In a small bowl, mix soy sauce, rice vinegar, ketchup, and brown sugar.",
                "In a separate small bowl, mix cornstarch and water.",
                "Heat oil in a large skillet over medium-high heat. Add chicken and cook until browned and cooked through.",
                "Add bell pepper and onion to the skillet. Cook for 5 minutes until vegetables are tender.",
                "Stir in pineapple chunks and soy sauce mixture. Bring to a boil.",
                "Stir in cornstarch mixture and cook until sauce is thickened. Serve immediately."
            ),

        ),
        Recipe(
            "Fried Rice",
            listOf("4 cups cooked rice, chilled", "2 tablespoons vegetable oil", "1 onion, diced", "2 cloves garlic, minced", "1 cup frozen peas and carrots, thawed", "2 eggs, beaten", "3 tablespoons soy sauce", "1 tablespoon oyster sauce", "1 teaspoon sesame oil"),
            "20 minutes",
            listOf(
                "Heat oil in a large skillet or wok over medium-high heat. Add onion and garlic, and cook until onion is translucent.",
                "Add peas and carrots and cook for 3-5 minutes.",
                "Push vegetables to one side of the skillet, and pour eggs on the other side. Scramble eggs until cooked through, then mix with vegetables.",
                "Add rice to the skillet. Pour soy sauce, oyster sauce, and sesame oil over the rice. Stir to combine and heat through. Serve immediately."
            ),

        ),
        Recipe(
            "Dumplings",
            listOf("1 pack dumpling wrappers", "300g ground pork", "1 cup shredded cabbage", "2 green onions, chopped", "2 cloves garlic, minced", "1 tablespoon ginger, grated", "2 tablespoons soy sauce", "1 tablespoon sesame oil", "1 egg, beaten"),
            "45 minutes",
            listOf(
                "In a large bowl, combine ground pork, cabbage, green onions, garlic, ginger, soy sauce, sesame oil, and egg. Mix well.",
                "Place a small spoonful of the pork mixture in the center of each dumpling wrapper. Wet the edges of the wrapper with water, fold, and seal the edges.",
                "Heat a large skillet over medium-high heat. Add a little oil and arrange dumplings in the skillet. Cook until bottoms are golden brown.",
                "Add water to the skillet until it covers the bottom. Cover and steam dumplings for 5-7 minutes until cooked through. Serve immediately with dipping sauce."
            ),

        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        favoritesListView = findViewById(R.id.favoritesListView)
        backToHomeButton = findViewById(R.id.backToHomeButton)

        val sharedPreferences = getSharedPreferences("FAVORITES", MODE_PRIVATE)
        val favoriteRecipes = getFavoriteRecipes(sharedPreferences)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, favoriteRecipes)
        favoritesListView.adapter = adapter


        backToHomeButton.setOnClickListener {
            finish()
        }
    }

    private fun getFavoriteRecipes(sharedPreferences: SharedPreferences): MutableList<String> {
        val favoriteRecipesJson = sharedPreferences.getString("FAVORITE_RECIPES", "")
        return if (!favoriteRecipesJson.isNullOrEmpty()) {
            Gson().fromJson(favoriteRecipesJson, object : TypeToken<MutableList<String>>() {}.type)
        } else {
            mutableListOf()
        }
    }


    private fun openRecipeDetailActivity(recipeName: String) {
        val recipe = recipesList.find { it.name == recipeName }
        recipe?.let {
            val intent = Intent(this, RecipeDetailActivity::class.java).apply {
                putExtra("RECIPE_NAME", it.name)
                putExtra("RECIPE_INGREDIENTS", ArrayList(it.ingredients))
                putExtra("RECIPE_COOKING_TIME", it.cookingTime)
                putExtra("RECIPE_INSTRUCTIONS", ArrayList(it.instructions))

            }
            startActivity(intent)
        }
    }
}
