package folk.sisby.tinkerers_smithing.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Unique
	private boolean isBroken() {
		return TinkerersSmithing.isBroken((ItemStack) (Object) this);
	}

	@Unique
	private boolean isKeeper() {
		return TinkerersSmithing.isKeeper((ItemStack) (Object) this);
	}

	@Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
	private void brokenNoMiningSpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
		if (isBroken()) {
			cir.setReturnValue(1.0F);
			cir.cancel();
		}
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void brokenDontUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if (isBroken()) {
			cir.setReturnValue(TypedActionResult.fail((ItemStack) (Object) this));
			cir.cancel();
		}
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void brokenDontUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		if (isBroken()) {
			cir.setReturnValue(ActionResult.FAIL);
			cir.cancel();
		}
	}

	@Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
	private void brokenDontUseOnEntity(PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (isBroken()) {
			cir.setReturnValue(ActionResult.FAIL);
			cir.cancel();
		}
	}

	@Inject(method = "isSuitableFor", at = @At("HEAD"), cancellable = true)
	private void brokenIsNotSuitable(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (isBroken()) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "applyAttributeModifiers", at = @At("HEAD"), cancellable = true)
	private void brokenHasNoAttributes(EquipmentSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer, CallbackInfo ci) {
		if (isBroken()) {
			ci.cancel();
		}
	}

	@Inject(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
	private void brokenNoDamage(int amount, ServerWorld world, @Nullable ServerPlayerEntity player, Consumer<Item> breakCallback, CallbackInfo ci) {
		if (isKeeper() && isBroken()) ci.cancel();
	}

	@WrapWithCondition(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
	private boolean dontBreakDecrementKeepers(ItemStack instance, int amount) {
		return !isKeeper();
	}

	@ModifyArg(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V"))
	private int dontBreakResetKeepers(int damage) {
//		return isKeeper() ? ((ItemStack) (Object) this).getMaxDamage() : damage;
		return damage;
	}

	@Inject(method = "getTooltip", at = @At(value = "RETURN"), cancellable = true)
	public void brokenShowTooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
		if (isBroken()) {
			List<Text> list = new ArrayList<>(cir.getReturnValue());
			list.add(Text.translatable("item.tinkerers_smithing.broken").setStyle(Style.EMPTY.withColor(Formatting.DARK_RED)));
			cir.setReturnValue(list);
		}
	}
}
