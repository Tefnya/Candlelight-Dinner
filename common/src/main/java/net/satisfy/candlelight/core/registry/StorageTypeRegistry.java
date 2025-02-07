package net.satisfy.candlelight.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.satisfy.candlelight.core.util.CandlelightIdentifier;

import java.util.Set;

public class StorageTypeRegistry {
    public static final ResourceLocation SHELF = new CandlelightIdentifier("shelf");
    public static final ResourceLocation TABLE_SET = new CandlelightIdentifier("table_set");
    public static final ResourceLocation JEWELRY_BOX = new CandlelightIdentifier("jewelry_box");

    public static Set<Block> registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.OAK_SHELF.get());
        blocks.add(ObjectRegistry.SPRUCE_SHELF.get());
        blocks.add(ObjectRegistry.BIRCH_SHELF.get());
        blocks.add(ObjectRegistry.ACACIA_SHELF.get());
        blocks.add(ObjectRegistry.JUNGLE_SHELF.get());
        blocks.add(ObjectRegistry.DARK_OAK_SHELF.get());
        blocks.add(ObjectRegistry.MANGROVE_SHELF.get());
        blocks.add(ObjectRegistry.CRIMSON_SHELF.get());
        blocks.add(ObjectRegistry.WARPED_SHELF.get());
        blocks.add(ObjectRegistry.BAMBOO_SHELF.get());
        blocks.add(ObjectRegistry.CHERRY_SHELF.get());
        blocks.add(ObjectRegistry.JEWELRY_BOX.get());
        blocks.add(ObjectRegistry.TABLE_SET.get());
        return blocks;
    }
}
