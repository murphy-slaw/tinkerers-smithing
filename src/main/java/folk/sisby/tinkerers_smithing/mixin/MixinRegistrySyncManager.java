package folk.sisby.tinkerers_smithing.mixin;

import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import net.fabricmc.fabric.impl.registry.sync.RegistrySyncManager;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(RegistrySyncManager.class)
public abstract class MixinRegistrySyncManager {
	@Redirect(method = "createAndPopulateRegistryMap", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registry;getId(Ljava/lang/Object;)Lnet/minecraft/util/Identifier;"))
	private static @Nullable <T> Identifier skipRecipes(Registry<T> instance, T t) {
		final Identifier id = instance.getId(t);
		if (
			Objects.equals(id, Identifier.of(TinkerersSmithing.ID, "repair"))
				|| Objects.equals(id, Identifier.of(TinkerersSmithing.ID, "sacrifice"))
				|| Objects.equals(id, Identifier.of(TinkerersSmithing.ID, "smithing"))
				|| Objects.equals(id, Identifier.of(TinkerersSmithing.ID, "shapeless"))
		) {
			return null;
		}
		return id;
	}
}
