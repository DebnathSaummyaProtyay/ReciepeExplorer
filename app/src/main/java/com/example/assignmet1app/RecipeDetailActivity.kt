package com.example.assignmet1app

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeIngredientsTextView: TextView
    private lateinit var recipeCookingTimeTextView: TextView
    private lateinit var recipeInstructionsTextView: TextView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        initViews()
        displayRecipeDetails()

        backButton.setOnClickListener {
            finish() // This will close the activity and return to the previous one
        }
    }

    private fun initViews() {
        recipeNameTextView = findViewById(R.id.recipeNameTextView)
        recipeIngredientsTextView = findViewById(R.id.recipeIngredientsTextView)
        recipeCookingTimeTextView = findViewById(R.id.recipeCookingTimeTextView)
        recipeInstructionsTextView = findViewById(R.id.recipeInstructionsTextView)
        backButton = findViewById(R.id.backButton)
    }

    private fun displayRecipeDetails() {
        val name = intent.getStringExtra("RECIPE_NAME")
        val ingredients = intent.getStringArrayListExtra("RECIPE_INGREDIENTS")
        val cookingTime = intent.getStringExtra("RECIPE_COOKING_TIME")
        val instructions = intent.getStringArrayListExtra("RECIPE_INSTRUCTIONS")
        val imageResId = intent.getIntExtra("RECIPE_IMAGE", 0)

        recipeNameTextView.text = name
        recipeIngredientsTextView.text = "Ingredients:\n${ingredients?.joinToString("\n")}"
        recipeCookingTimeTextView.text = "Cooking Time: $cookingTime"
        recipeInstructionsTextView.text = "Instructions:\n${instructions?.joinToString("\n")}"

    }
}
