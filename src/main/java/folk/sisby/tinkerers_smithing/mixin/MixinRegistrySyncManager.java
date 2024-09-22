package folk.sisby.tinkerers_smithing.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import folk.sisby.tinkerers_smithing.recipe.ServerRecipe;
import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RegistrySyncManager.class)
public abstract class MixinRegistrySyncManager {
	@ModifyVariable(method = "createAndPopulateRegistryMap", at = @At("STORE"), ordinal = 1, remap = false)
	private static Identifier skipRecipes(Identifier id, @Local(ordinal = 0) Object obj) {
		if (ServerRecipe.class.isAssignableFrom(obj.getClass().getEnclosingClass())) {
			return null;
		}
		return id;
	}
}
