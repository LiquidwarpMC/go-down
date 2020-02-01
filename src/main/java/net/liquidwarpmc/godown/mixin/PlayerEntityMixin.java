package net.liquidwarpmc.godown.mixin;

import net.liquidwarpmc.godown.GoDown;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at=@At("HEAD"))
    public void onInitDataTracker(CallbackInfo ci) {
        getDataTracker().startTracking(GoDown.Shared.CRAWLING_REQUEST, false);
    }

    @Redirect(method = "updateSize", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/PlayerEntity.setPose(Lnet/minecraft/entity/EntityPose;)V", ordinal = 0), require = 1)
    public void onPreSetPose(PlayerEntity player, EntityPose pose) {
        boolean replaceSwimming = pose == EntityPose.SWIMMING && !player.isSwimming();
        boolean crawl = player.getDataTracker().get(GoDown.Shared.CRAWLING_REQUEST) && !player.isSwimming();

        if(crawl) {
            pose = EntityPose.SWIMMING;
        }

        setPose(pose);
    }
}
