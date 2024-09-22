package folk.sisby.tinkerers_smithing.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoggedEntity.class)
public class MixinBoggedEntity {
	@Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/BoggedEntity;sheared(Lnet/minecraft/sound/SoundCategory;)V"), cancellable = true)
	private void dontUseBrokenShears(CallbackInfoReturnable<ActionResult> ci, @Local ItemStack shears) {
		if (TinkerersSmithing.isBroken(shears)) {
			ci.setReturnValue(ActionResult.FAIL);
		}
	}
}
