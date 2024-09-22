package folk.sisby.tinkerers_smithing.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MooshroomEntity.class)
public class MixinMooshroomEntity {
	@Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/MooshroomEntity;sheared(Lnet/minecraft/sound/SoundCategory;)V"), cancellable = true)
	private void dontUseBrokenShears(CallbackInfoReturnable<ActionResult> ci, @Local(ordinal = 0) ItemStack shears) {
		if (TinkerersSmithing.isBroken(shears)) {
			ci.setReturnValue(ActionResult.FAIL);
		}
	}
}
