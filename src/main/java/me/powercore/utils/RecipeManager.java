package me.powercore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {
    private final Map<String, ShapedRecipe> recipes = new HashMap<>();
    private final FileConfiguration config;

    public RecipeManager(FileConfiguration config) {
        this.config = config;
        loadRecipes();
    }

    private void loadRecipes() {
        if (config.contains("recipes")) {
            for (String key : config.getConfigurationSection("recipes").getKeys(false)) {
                if (config.getBoolean("recipes." + key + ".enabled")) {
                    createRecipe(key);
                }
            }
        }
    }

    private void createRecipe(String key) {
        String resultMaterial = config.getString("recipes." + key + ".result.material");
        String resultName = config.getString("recipes." + key + ".result.name");
        java.util.List<String> lore = config.getStringList("recipes." + key + ".result.lore");
        String[] shape = config.getStringList("recipes." + key + ".shape").toArray(new String[0]);
        Map<Character, Material> ingredients = new HashMap<>();

        for (String ingredientKey : config.getConfigurationSection("recipes." + key + ".ingredients").getKeys(false)) {
            Material material = Material.getMaterial(config.getString("recipes." + key + ".ingredients." + ingredientKey));
            if (material != null) {
                ingredients.put(ingredientKey.charAt(0), material);
            }
        }

        Material mat = Material.getMaterial(resultMaterial);
        if (mat == null) return;

        ItemStack result = new ItemStack(mat);
        ItemMeta meta = result.getItemMeta();
        if (meta != null) {
            if (resultName != null) meta.setDisplayName(resultName);
            if (lore != null && !lore.isEmpty()) meta.setLore(lore);
            result.setItemMeta(meta);
        }

        ShapedRecipe recipe = new ShapedRecipe(new org.bukkit.NamespacedKey("powercore", key), result);
        recipe.shape(shape);
        for (Map.Entry<Character, Material> entry : ingredients.entrySet()) {
            recipe.setIngredient(entry.getKey(), entry.getValue());
        }

        recipes.put(key, recipe);
    }

    public ShapedRecipe getRecipe(String key) {
        return recipes.get(key);
    }

    // --- Add these methods for RecipeCommand support ---

    public boolean isRecipeDefined(String key) {
        return recipes.containsKey(key);
    }

    public String getRecipeShape(String key) {
        ShapedRecipe recipe = recipes.get(key);
        if (recipe == null) return "";
        String[] shape = recipe.getShape();
        return String.join(", ", shape);
    }

    public String getRecipeIngredients(String key) {
        ShapedRecipe recipe = recipes.get(key);
        if (recipe == null) return "";
        return recipe.getIngredientMap().toString();
    }
}