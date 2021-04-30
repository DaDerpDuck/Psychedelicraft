package com.daderpduck.hallucinocraft.client.rendering;

import com.daderpduck.hallucinocraft.Hallucinocraft;
import com.daderpduck.hallucinocraft.client.rendering.shaders.PostShaders;
import com.daderpduck.hallucinocraft.client.rendering.shaders.RenderUtil;
import com.daderpduck.hallucinocraft.client.rendering.shaders.ShaderRenderer;
import com.daderpduck.hallucinocraft.drugs.Drug;
import com.daderpduck.hallucinocraft.events.hooks.BobHurtEvent;
import com.daderpduck.hallucinocraft.mixin.client.InvokerConfigOF;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Hallucinocraft.MOD_ID)
public class DrugRenderer {
    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (RenderUtil.hasOptifine) { // Checks for optifine shaders
            if (InvokerConfigOF.callIsShaders() && ShaderRenderer.useShader) {
                ShaderRenderer.clear(true);
                ShaderRenderer.useShader = false;
                PostShaders.useShaders = false;
            } else if (!InvokerConfigOF.callIsShaders() && !ShaderRenderer.useShader) {
                ShaderRenderer.setup();
                ShaderRenderer.useShader = true;
                PostShaders.useShaders = true;
            }
        }

        if (mc.level == null) return;

        if (event.phase == TickEvent.Phase.START) {
            Map<Drug, Float> activeDrugs = Drug.getActiveDrugs(mc.player);

            activeDrugs.forEach((drug, effect) -> {
                if (effect > 0) drug.renderTick(Drug.getDrugEffects(), effect);
            });

            MouseSmootherEffect.INSTANCE.setAmplifier(Drug.getDrugEffects().CAMERA_INERTIA.getClamped());
        } else {
            Drug.getDrugEffects().reset(true);
        }
    }

    private static final CameraTrembleEffect trembleEffect = new CameraTrembleEffect();
    @SubscribeEvent
    public static void onBobHurt(BobHurtEvent event) {
        trembleEffect.setAmplitude(Drug.getDrugEffects().CAMERA_TREMBLE.getValue());
        trembleEffect.tick(event.matrixStack);
    }
}
