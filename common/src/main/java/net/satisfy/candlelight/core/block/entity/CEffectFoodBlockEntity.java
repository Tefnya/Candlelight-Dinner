package net.satisfy.candlelight.core.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.farm_and_charm.core.item.food.EffectFoodHelper;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class CEffectFoodBlockEntity extends BlockEntity {
    public static final String STORED_EFFECTS_KEY = "StoredEffects";
    private List<Pair<MobEffectInstance, Float>> effects;

    public CEffectFoodBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.EFFECT_FOOD_BLOCK_ENTITY.get(), pos, state);
    }

    public void addEffects(List<Pair<MobEffectInstance, Float>> effects) {
        this.effects = effects;
    }

    public List<Pair<MobEffectInstance, Float>> getEffects() {
        return effects != null ? effects : Lists.newArrayList();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.effects = EffectFoodHelper.fromNbt(tag.getList(STORED_EFFECTS_KEY, 10));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (effects == null) return;
        ListTag list = new ListTag();
        for (Pair<MobEffectInstance, Float> effect : effects) {
            list.add(EffectFoodHelper.createNbt((short) BuiltInRegistries.MOB_EFFECT.asHolderIdMap().getId(effect.getFirst().getEffect()), effect));
        }
        tag.put(STORED_EFFECTS_KEY, list);
    }
}
