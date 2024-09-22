package folk.sisby.tinkerers_smithing.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeehiveBlock.class)
public class MixinBeehiveBlock {
	@Inject(
		method = "onUseWithItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V",
			ordinal = 0),
		cancellable = true
	)
	private void dontUseBrokenShears(CallbackInfoReturnable<ItemActionResult> ci, @Local ItemStack stack) {
		if (TinkerersSmithing.isBroken(stack)) {
			ci.setReturnValue(ItemActionResult.FAIL);
		}
	}
}
