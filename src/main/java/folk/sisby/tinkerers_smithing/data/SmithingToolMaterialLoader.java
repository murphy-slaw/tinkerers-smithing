package folk.sisby.tinkerers_smithing.data;

import com.google.gson.Gson;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import folk.sisby.tinkerers_smithing.TinkerersSmithingLoader;
import folk.sisby.tinkerers_smithing.TinkerersSmithingMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.Map;

public class SmithingToolMaterialLoader extends SmithingMaterialLoader {
	public static final SmithingToolMaterialLoader INSTANCE = new SmithingToolMaterialLoader(new Gson());
	public static final Identifier ID = new Identifier(TinkerersSmithing.ID, "smithing_tool_material_loader");

	public SmithingToolMaterialLoader(Gson gson) {
		super(gson, "smithing_tool_materials", TinkerersSmithingMaterial.EQUIPMENT_TYPE.TOOL);
	}

	@Override
	public Ingredient getDefaultRepairIngredient(Item item) {
		if (item.isDamageable() && item instanceof ToolItem ti) {
			ToolMaterial material = ti.getMaterial();
			if (material != null) {
				Ingredient repairIngredient = material.getRepairIngredient();
				if (repairIngredient != null && !repairIngredient.isEmpty()) {
					return repairIngredient;
				}
			}
		}
		return null;
	}

	@Override
	public boolean matchingMaterials(Item item1, Item item2) {
		return item1 instanceof ToolItem ti1 && item2 instanceof ToolItem ti2 && ti1.getMaterial() == ti2.getMaterial();
	}

	@Override
	public Map<Identifier, TinkerersSmithingMaterial> getOutputMap() {
		return TinkerersSmithingLoader.INSTANCE.TOOL_MATERIALS;
	}

	@Override
	public String getName() {
		return ID.toString();
	}
}
