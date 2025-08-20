package net.satisfy.candlelight.core.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;

@SuppressWarnings("unused")
public class CandlelightIdentifier {

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(Candlelight.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Candlelight.MOD_ID + ":" + path);
    }
}

