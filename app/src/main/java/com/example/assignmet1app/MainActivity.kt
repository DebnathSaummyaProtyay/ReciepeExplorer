package com.example.assignmet1app

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var recipeTextView: TextView
    private lateinit var addToFavoritesButton: Button
    private lateinit var viewFavoritesButton: Button
    private lateinit var instructionsButton: Button

    private val recipesMap: Map<String, Array<String>> = mapOf(
        "Italian" to arrayOf("Spaghetti Carbonara", "Margherita Pizza", "Lasagna"),
        "Mexican" to arrayOf("Tacos", "Burritos", "Guacamole"),
        "Chinese" to arrayOf("Sweet and Sour Chicken", "Fried Rice", "Dumplings")
    )

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
            )
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
            )
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
            )
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
            )
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
            )
        ),
        Recipe(
            "Guacamole",
            listOf("3 ripe avocados", "1 lime, juiced", "1 teaspoon salt", "1/2 cup diced onion", "3 tablespoons chopped fresh cilantro", "2 roma tomatoes, diced", "1 teaspoon minced garlic", "1 pinch ground cayenne pepper"),
            "10 minutes",
            listOf(
                "In a medium bowl, mash together the avocados, lime juice, and salt.",
                "Mix in onion, cilantro, tomatoes, and garlic.",
                "Stir in cayenne pepper. Refrigerate 1 hour for best flavor, or serve immediately."
            )
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
            )
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
            )
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
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewBindings()
        setupSpinnerAdapter()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                displayRecipes()
                instructionsButton.visibility = if (position > 0) View.VISIBLE else View.GONE
            }
        }

        instructionsButton.setOnClickListener {
            showRecipeSelectionDialog()
        }

        addToFavoritesButton.setOnClickListener {
            val selectedRecipeName = spinner.selectedItem.toString()
            addRecipeToFavorites(selectedRecipeName)
        }

        viewFavoritesButton.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }
    }

    private fun initViewBindings() {
        spinner = findViewById(R.id.spinner)
        recipeTextView = findViewById(R.id.recipeTextView)
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton)
        viewFavoritesButton = findViewById(R.id.viewFavoritesButton)
        instructionsButton = findViewById(R.id.instructionsButton)
    }

    private fun setupSpinnerAdapter() {
        val cuisines = arrayOf("Select a cuisine...") + recipesMap.keys.toTypedArray()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuisines)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun displayRecipes() {
        val selectedPosition = spinner.selectedItemPosition
        if (selectedPosition > 0) {
            val cuisine = spinner.selectedItem.toString()
            val recipes = recipesMap[cuisine]
            recipeTextView.text = recipes?.joinToString(separator = "\n") ?: "No recipes available."
        } else {
            recipeTextView.text = "Please select a cuisine from the dropdown."
        }
    }

    private fun addRecipeToFavorites(recipeName: String) {
        val sharedPreferences = getSharedPreferences("FAVORITES", Context.MODE_PRIVATE)
        val favorites = getFavoriteRecipes(sharedPreferences)

        if (recipeName !in favorites) {
            favorites.add(recipeName)
            sharedPreferences.edit {
                putString("FAVORITE_RECIPES", Gson().toJson(favorites))
            }
            Toast.makeText(this, "$recipeName added to favorites", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "$recipeName is already in favorites", Toast.LENGTH_SHORT).show()
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

    private fun showRecipeSelectionDialog() {
        val selectedCuisine = spinner.selectedItem.toString()
        val recipes = recipesMap[selectedCuisine]

        val dialogView = LayoutInflater.from(this).inflate(R.layout.recipe_selection_dialog, null)
        val listView: ListView = dialogView.findViewById(R.id.recipeListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recipes ?: arrayOf())
        listView.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        listView.setOnItemClickListener { _, _, position, _ ->
            val recipeName = recipes?.get(position)
            recipeName?.let {
                openRecipeDetailActivity(it)
            }
            dialog.dismiss()
        }

        dialog.show()
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


