package net.satisfy.candlelight.forge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.CandlelightClient;

@EventBusSubscriber(modid = Candlelight.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class CandlelightClientForge {
    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        CandlelightClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CandlelightClient.initClient();
    }
}
