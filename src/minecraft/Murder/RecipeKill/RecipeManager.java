package Murder.RecipeKill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;

public class RecipeManager 
{
      private static final Map removeIDs = new HashMap();

      public static void addID(String idStr) {
        try {
          idStr = idStr.trim();
          if (!idStr.contains(":")) {
            addID(Integer.parseInt(idStr));
            return;
          }

          String[] split = idStr.split(":");
          int id = Integer.parseInt(split[0]);
          int metadata = Integer.parseInt(split[1]);
          addID(id, metadata);
        } catch (NumberFormatException e) {
          e.printStackTrace(System.err);
        }
      }

      public static void addID(int id) {
        addID(id, 0, false);
      }

      public static void addID(int id, int metadata) {
        addID(id, metadata, true);
      }

      private static void addID(int id, int metadata, boolean hasMetadata) {
        if ((removeIDs.containsKey(Integer.valueOf(id))) && (hasMetadata)) {
          if (((ArrayList)removeIDs.get(Integer.valueOf(id))).contains(Integer.valueOf(metadata))) return;
          ((ArrayList)removeIDs.get(Integer.valueOf(id))).add(Integer.valueOf(metadata));
          return;
        }
        if (removeIDs.containsKey(Integer.valueOf(id))) return;
        removeIDs.put(Integer.valueOf(id), new ArrayList());
        if (hasMetadata)
          ((ArrayList)removeIDs.get(Integer.valueOf(id))).add(Integer.valueOf(metadata));
      }

      public static void removeRecipes() {
        removeCrafting();
      }

      private static void removeCrafting() {
        List recipes = CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < recipes.size(); i++)
        {
          ItemStack stack;
          if ((stack = ((IRecipe)recipes.get(i)).getRecipeOutput()) != null)
          {
            int id = stack.itemID;
            if (removeIDs.containsKey(Integer.valueOf(id))) {
              ArrayList metadata = (ArrayList)removeIDs.get(Integer.valueOf(id));

              if (((ArrayList)removeIDs.get(Integer.valueOf(id))).size() == 0) {
                recipes.remove(i--);
              }
              else if (metadata.contains(Integer.valueOf(stack.getItemDamage())))
                recipes.remove(i--);
            }
          }
        }
      }
      
      private static void removeSmelting() {
            Map recipes = FurnaceRecipes.smelting().getSmeltingList();
            for (Integer id = 0; id < recipes.size();id++ )
            {
              ItemStack stack;
              if ((stack = (ItemStack)recipes.get(id)) == null) {
            	  return;
              }
              if (!removeIDs.containsKey(Integer.valueOf(stack.itemID))) {
            	  return;
              }

              ArrayList meta = (ArrayList)removeIDs.get(Integer.valueOf(stack.itemID));
              if (((ArrayList)removeIDs.get(Integer.valueOf(stack.itemID))).size() == 0) {
                recipes.remove(id);
              }
              else if (meta.contains(Integer.valueOf(stack.getItemDamage())))
                recipes.remove(id);
            }
          }

        private static void removeMetaSmelting() {
            Map<List<Integer>, ItemStack> recipes = FurnaceRecipes.smelting().getMetaSmeltingList();
            for (List<Integer> list : new HashMap<List<Integer>, ItemStack>(recipes).keySet()) {
                ItemStack stack;
                if ((stack = recipes.get(list)) == null) {
                	return;
                }
                if (!removeIDs.containsKey(stack.itemID)) {
                	return;
                }

                ArrayList<Integer> metadata = (ArrayList<Integer>) removeIDs.get(stack.itemID);
                if (((Map) removeIDs.get(stack.itemID)).size() == 0) {
                    recipes.remove(list);
                } else {
                    if (metadata.contains(stack.getItemDamage()))
                        recipes.remove(list);
                }
            }
          }
    }