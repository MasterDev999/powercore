package me.powercore.utils;

import me.powercore.PowerCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class RecipeManager {
    private final PowerCore plugin;
    private final Map<String, ShapedRecipe> recipes = new HashMap<>();

    public RecipeManager(PowerCore plugin) {
        this.plugin = plugin;
    }

    public void loadRecipesFromConfig() {
        ConfigurationSection recipesSection = plugin.getConfig().getConfigurationSection("recipes");
        if (recipesSection == null) {
            plugin.getLogger().warning("No recipes section found in config.yml");
            return;
        }

        for (String key : recipesSection.getKeys(false)) {
            ConfigurationSection recipeSection = recipesSection.getConfigurationSection(key);
            if (recipeSection == null || !recipeSection.getBoolean("enabled", true)) continue;

            // Result Item
            ConfigurationSection resultSection = recipeSection.getConfigurationSection("result");
            if (resultSection == null) continue;

            Material resultMat = Material.getMaterial(resultSection.getString("material", "NETHER_STAR"));
            if (resultMat == null) continue;

            ItemStack resultItem = new ItemStack(resultMat);
            ItemMeta meta = resultItem.getItemMeta();
            if (meta != null) {
                String name = ChatColor.translateAlternateColorCodes('&', resultSection.getString("name", ""));
                if (!name.isEmpty()) meta.setDisplayName(name);

                List<String> loreList = resultSection.getStringList("lore");
                if (!loreList.isEmpty()) {
                    List<String> coloredLore = new ArrayList<>();
                    for (String loreLine : loreList) {
                        coloredLore.add(ChatColor.translateAlternateColorCodes('&', loreLine));
                    }
                    meta.setLore(coloredLore);
                }
                resultItem.setItemMeta(meta);
            }

            // Shape
            List<String> shape = recipeSection.getStringList("shape");
            if (shape.isEmpty() || shape.size() > 3) {
                plugin.getLogger().warning("Invalid shape for recipe " + key);
                continue;
            }

            // Ingredients
            ConfigurationSection ingredientsSection = recipeSection.getConfigurationSection("ingredients");
            if (ingredientsSection == null) {
                plugin.getLogger().warning("No ingredients for recipe " + key);
                continue;
            }

            Map<Character, Material> ingredientsMap = new HashMap<>();
            for (String ingredientKey : ingredientsSection.getKeys(false)) {
                String matName = ingredientsSection.getString(ingredientKey);
                if (matName == null) continue;
                Material mat = Material.getMaterial(matName.toUpperCase());
                if (mat == null) {
                    plugin.getLogger().warning("Invalid material " + matName + " for recipe " + key);
                    continue;
                }
                if (ingredientKey.length() == 1) {
                    ingredientsMap.put(ingredientKey.charAt(0), mat);
                }
            }

            // Create recipe
            NamespacedKey nsKey = new NamespacedKey(plugin, key);
            ShapedRecipe recipe = new ShapedRecipe(nsKey, resultItem);
            recipe.shape(shape.toArray(new String[0]));
            for (Map.Entry<Character, Material> entry : ingredientsMap.entrySet()) {
                recipe.setIngredient(entry.getKey(), entry.getValue());
            }

            recipes.put(key, recipe);
        }
    }

    public void registerRecipes() {
        for (ShapedRecipe recipe : recipes.values()) {
            Bukkit.addRecipe(recipe);
        }
    }

    public Optional<ShapedRecipe> getRecipe(String key) {
        return Optional.ofNullable(recipes.get(key));
    }

    public String getRecipeChat(String key) {
        if (!recipes.containsKey(key)) return null;

        ShapedRecipe recipe = recipes.get(key);
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GOLD).append("Recipe for ").append(ChatColor.AQUA).append(key).append(":\n");

        for (String row : recipe.getShape()) {
            for (char c : row.toCharArray()) {
                if (c == ' ') {
                    sb.append(ChatColor.GRAY).append("[ ] ");
                } else {
                    ItemStack item = recipe.getIngredientMap().get(c);
                    if (item != null) {
                        sb.append(ChatColor.GREEN).append("[").append(item.getType().name()).append("] ");
                    } else {
                        sb.append(ChatColor.RED).append("[?] ");
                    }
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Set<String> getRecipeKeys() {
        return recipes.keySet();
    }
}
