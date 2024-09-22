package folk.sisby.tinkerers_smithing.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PumpkinBlock.class)
public class MixinPumpkinBlock {
	@Inject(method = "onUseWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/BlockHitResult;getSide()Lnet/minecraft/util/math/Direction;"), cancellable = true)
	private void dontUseBrokenShears(CallbackInfoReturnable<ItemActionResult> ci, @Local ItemStack stack) {
		if (TinkerersSmithing.isBroken(stack)) {
			ci.setReturnValue(ItemActionResult.FAIL);
		}
	}

	@Inject(method = "onUseWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ItemActionResult;success(Z)Lnet/minecraft/util/ItemActionResult;", ordinal = 0), cancellable = true)
	private void dontSwingOnClientWhenBroken(CallbackInfoReturnable<ItemActionResult> ci, @Local ItemStack stack) {
		if (TinkerersSmithing.isBroken(stack)) {
			ci.setReturnValue(ItemActionResult.FAIL);
		}
	}
}
