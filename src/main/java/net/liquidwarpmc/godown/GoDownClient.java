package net.liquidwarpmc.godown;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class GoDownClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("GoDown");

    public static FabricKeyBinding keyCrawl;

    @Override
    public void onInitializeClient() {
        keyCrawl = FabricKeyBinding.Builder.create(
                new Identifier("godown:crawl"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                "key.categories.movement"
        ).build();

        KeyBindingRegistry.INSTANCE.register(keyCrawl);

        LOGGER.info("[Go Down Client] initialized!");
    }
}
