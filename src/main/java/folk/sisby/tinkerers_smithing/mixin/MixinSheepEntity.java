package folk.sisby.tinkerers_smithing.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class MixinSheepEntity {
	@Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;getWorld()Lnet/minecraft/world/World;"), cancellable = true)
	private void dontUseBrokenShears(CallbackInfoReturnable<ActionResult> ci, @Local ItemStack shears) {
		if (TinkerersSmithing.isBroken(shears)) {
			ci.setReturnValue(ActionResult.FAIL);
		}
	}
}
