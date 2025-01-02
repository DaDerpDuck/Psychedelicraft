package com.daderpduck.hallucinocraft.mixin.client;

import com.daderpduck.hallucinocraft.client.ClientUtil;
import com.daderpduck.hallucinocraft.events.hooks.RenderEvent;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BufferUploader.class)
public class MixinBufferUploader {
    @Inject(method = "upload", at = @At(value = "HEAD"))
    private static void onUpload(BufferBuilder.RenderedBuffer pBuffer, CallbackInfoReturnable<VertexBuffer> cir) {
        if (!ClientUtil.HAS_OPTIFINE) MinecraftForge.EVENT_BUS.post(new RenderEvent.BufferUploadShaderEvent());
    }
}
