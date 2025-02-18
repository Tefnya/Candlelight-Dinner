package satisfyu.candlelight.registry;

import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.common.block.ChairBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;
import satisfyu.candlelight.Candlelight;
import satisfyu.candlelight.block.*;
import satisfyu.candlelight.block.LanternBlock;
import satisfyu.candlelight.block.crops.BroccoliCropBlock;
import satisfyu.candlelight.block.crops.TomatoCropBlock;
import satisfyu.candlelight.block.crops.WildBushTomato;
import satisfyu.candlelight.food.CandlelightFoods;
import satisfyu.candlelight.food.EffectFoodBlockItem;
import satisfyu.candlelight.item.*;
import satisfyu.candlelight.item.food.EffectFoodItem;
import satisfyu.candlelight.util.CandlelightIdentifier;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Candlelight.MOD_ID, Registry.ITEM_REGISTRY);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Candlelight.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Block> BROCCOLI_CROP = registerWithoutItem("broccoli_crop", () -> new BroccoliCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistrySupplier<Block> TOMATO_CROP = registerWithoutItem("tomato_crop", () -> new TomatoCropBlock( getBushSettings()));
    public static final RegistrySupplier<Item> TOMATO_SEEDS = registerItem("tomato_seeds", () -> new BlockItem(TOMATO_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> TOMATO = registerItem("tomato", () -> new IngredientItem(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Item> BROCCOLI_SEEDS = registerItem("broccoli_seeds", () -> new BlockItem(BROCCOLI_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> BROCCOLI = registerItem("broccoli", () -> new IngredientItem(getSettings().food(Foods.POTATO)));
    public static final RegistrySupplier<Block> TOMATO_CRATE = registerWithItem("tomato_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BROCCOLI_CRATE = registerWithItem("broccoli_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CARROT_CRATE = registerWithItem("carrot_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> POTATO_CRATE = registerWithItem("potato_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BEETROOT_CRATE = registerWithItem("beetroot_crate", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> TOMATOES_WILD = registerWithoutItem("tomatoes_wild", () -> new WildBushTomato(getBushSettings()));
    public static final RegistrySupplier<Block> WILD_BROCCOLI = registerWithoutItem("wild_broccoli", () -> new FlowerBlock(MobEffects.NIGHT_VISION, 1, BlockBehaviour.Properties.copy(Blocks.DANDELION)));
    public static final RegistrySupplier<Block> FLOORBOARD = registerWithItem("floorboard", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> DRAWER = registerWithItem("drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> CABINET = registerWithItem("cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> SIDEBOARD = registerWithItem("sideboard", () -> new SideBoardBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CHAIR = registerWithItem("chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SOFA = registerWithItem("sofa", () -> new SofaBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> TABLE = registerWithItem("table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> LAMP = registerWithItem("lamp", () -> new LanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel(s -> s.getValue(LanternBlock.LUMINANCE) ? 15 : 0).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SIDE_TABLE = registerWithItem("side_table", () -> new SideTableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> COOKING_POT = registerWithItem("cooking_pot", () -> new CookingPotBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()));
    public static final RegistrySupplier<Block> COOKING_PAN = registerWithoutItem("cooking_pan", () -> new CookingPanBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));
    public static final RegistrySupplier<Item> COOKING_PAN_ITEM = registerItem("cooking_pan", () -> new CookingPanItem(COOKING_PAN.get(), getSettings(), SoundEventsRegistry.CABINET_OPEN.get()));
    public static final RegistrySupplier<Block> TABLE_SET = registerWithItem("table_set", () -> new TableSetBlock(BlockBehaviour.Properties.copy(COOKING_POT.get())));
    public static final RegistrySupplier<Item> GLASS = registerItem("glass", () -> new TableSetItem(getSettings()));
    public static final RegistrySupplier<Item> NAPKIN = registerItem("napkin", () -> new TableSetItem(getSettings()));
    public static final RegistrySupplier<Item> BUTTER = registerItem("butter", () -> new IngredientItem(getSettings()));
    public static final RegistrySupplier<Item> MOZZARELLA = registerItem("mozzarella", () -> new IngredientItem(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> DOUGH = registerItem("dough", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> PASTA_RAW = registerItem("pasta_raw", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> TOMATO_SOUP = registerItem("tomato_soup", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.TOMATO_SOUP), 1));
    public static final RegistrySupplier<Item> MUSHROOM_SOUP = registerItem("mushroom_soup", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.MUSHROOM_SOUP), 1));
    public static final RegistrySupplier<Item> BEETROOT_SALAD = registerItem("beetroot_salad", () -> new Item(getSettings().food(Foods.BAKED_POTATO)));
    public static final RegistrySupplier<Item> PASTA = registerItem("pasta", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.PASTA), 2));
    public static final RegistrySupplier<Item> BOLOGNESE = registerItem("bolognese", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.BOLOGNESE), 1));
    public static final RegistrySupplier<Item> CHICKEN_TERIYAKI = registerItem("chicken_teriyaki", () -> new Item(getSettings().food(Foods.COOKED_BEEF)));
    public static final RegistrySupplier<Item> BROCCOLI_SALAD = registerItem("broccoli_salad", () -> new Item(getSettings().food(CandlelightFoods.BROCCOLI_SALAD)));
    public static final RegistrySupplier<Item> BEEF_TARTARE = registerItem("beef_tartare", () -> new Item(getSettings().food(Foods.COOKED_BEEF)));
    public static final RegistrySupplier<Item> COOKED_BEEF = registerItem("cooked_beef", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.COOKED_BEEF), 2));
    public static final RegistrySupplier<Item> CHICKEN_ALFREDO = registerItem("chicken_alfredo", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.VEGGIE_PLATE), 1));
    public static final RegistrySupplier<Item> BROCCOLI_BEEF = registerItem("broccoli_beef", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.BROCCOLI_BEEF), 2));
    public static final RegistrySupplier<Block> BROCCOLI_TOMATO_BLOCK = registerWithoutItem("broccoli_tomato_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.BROCCOLI_TOMATO));
    public static final RegistrySupplier<Item> BROCCOLI_TOMATO = registerItem("broccoli_tomato", () -> new EffectFoodBlockItem(BROCCOLI_TOMATO_BLOCK.get(), getSettings().food(CandlelightFoods.BROCCOLI_TOMATO), 3));
    public static final RegistrySupplier<Item> SALMON_WINE = registerItem("salmon_wine", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.SALMON_ON_WHITE_WINE), 2));
    public static final RegistrySupplier<Item> VEGGIE_PLATE = registerItem("veggie_plate", () -> new Item(getSettings().food(CandlelightFoods.VEGGIE_PLATE)));
    public static final RegistrySupplier<Block> PORK_RIBS_BLOCK = registerWithoutItem("pork_ribs_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.PORK_RIBS));
    public static final RegistrySupplier<Item> PORK_RIBS = registerItem("pork_ribs", () -> new EffectFoodBlockItem(PORK_RIBS_BLOCK.get(), getSettings().food(CandlelightFoods.PORK_RIBS), 2));
    public static final RegistrySupplier<Item> PASTA_BOLOGNESE = registerItem("pasta_bolognese", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.VEGGIE_PLATE), 1));
    public static final RegistrySupplier<Item> PASTA_BROCCOLI = registerItem("pasta_broccoli", () -> new Item(getSettings().food(CandlelightFoods.TOMATO_MOZZARELLA_SALAD)));
    public static final RegistrySupplier<Item> PANCAKE = registerItem("pancake", () -> new Item(getSettings().food(CandlelightFoods.FRICASSE)));
    public static final RegistrySupplier<Item> FRICASSE = registerItem("fricasse", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.FRICASSE), 2));
    public static final RegistrySupplier<Item> CHICKEN = registerItem("chicken", () -> new EffectFoodItem(getSettings().food(Foods.GOLDEN_CARROT), 2));
    public static final RegistrySupplier<Block> TOMATO_MOZZARELLA_BLOCK = registerWithoutItem("tomato_mozzarella_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.TOMATO_MOZZARELLA_SALAD));
    public static final RegistrySupplier<Item> TOMATO_MOZZARELLA_SALAD = registerItem("tomato_mozzarella_salad", () -> new EffectFoodBlockItem(TOMATO_MOZZARELLA_BLOCK.get(), getSettings().food(CandlelightFoods.TOMATO_MOZZARELLA_SALAD), 3));
    public static final RegistrySupplier<Block> LASAGNA_BLOCK = registerWithoutItem("lasagne_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 3, CandlelightFoods.LASAGNE));
    public static final RegistrySupplier<Item> LASAGNA = registerItem("lasagne", () -> new EffectFoodBlockItem(LASAGNA_BLOCK.get(), getSettings().food(CandlelightFoods.LASAGNE), 3));
    public static final RegistrySupplier<Item> ROASTBEEF_CARROTS = registerItem("roastbeef_carrots", () -> new EffectFoodItem(getSettings().food(CandlelightFoods.ROASTBEEF_CARROTS), 2));
    public static final RegistrySupplier<Block> BEEF_WELLINGTON_BLOCK = registerWithoutItem("beef_wellington_block", () -> new EffectFoodBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), 2, CandlelightFoods.BEEF_WELLINGTON));
    public static final RegistrySupplier<Item> BEEF_WELLINGTON = registerItem("beef_wellington", () -> new EffectFoodBlockItem(BEEF_WELLINGTON_BLOCK.get(), getSettings().food(CandlelightFoods.BEEF_WELLINGTON), 2));
    public static final RegistrySupplier<Item> PIZZA_SLICE = registerItem("pizza_slice", () -> new Item(getSettings().food(Foods.BAKED_POTATO)));
    public static final RegistrySupplier<Block> PIZZA = registerWithItem("pizza", () -> new PizzaBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), PIZZA_SLICE));
    public static final RegistrySupplier<Item> CHOCOLATE = registerItem("chocolate", () -> new IngredientItem(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> TABLE_SIGN = registerWithItem("table_sign", () -> new BoardBlock(BlockBehaviour.Properties.of(Material.DECORATION), false));
    public static final RegistrySupplier<Block> TOOL_RACK = registerWithItem("tool_rack", () -> new ToolRackBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> PAINTING = registerWithItem("painting", () -> new SmallPaintingBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission()));
    public static final RegistrySupplier<Block> HEARTH = registerWithItem("hearth", () -> new DecorationBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission()));
    public static final RegistrySupplier<Block> ROSE = registerWithItem("rose", () -> new RoseBushBlock(MobEffect.byId(6), 1, BlockBehaviour.Properties.copy(Blocks.DANDELION)));
    public static final RegistrySupplier<Block> JEWELRY_BOX = registerWithItem("jewelry_box", () -> new JewelryBoxBlock(BlockBehaviour.Properties.of(Material.DECORATION)));
    public static final RegistrySupplier<Block> CHOCOLATE_BOX = registerWithItem("chocolate_box", () -> new ChocolateBoxBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));
    public static final RegistrySupplier<Item> GOLD_RING = registerItem("gold_ring", () -> new RingItem(ArmorMaterialRegistry.RING_ARMOR, EquipmentSlot.CHEST, getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> COOKING_HAT = registerItem("cooking_hat", () -> new CookingHatItem(ArmorMaterialRegistry.COOK_ARMOR, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> CHEFS_JACKET = registerItem("chefs_jacket", () -> new CookDefaultArmorItem(ArmorMaterialRegistry.COOK_ARMOR, EquipmentSlot.CHEST, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> CHEFS_PANTS = registerItem("chefs_pants", () -> new CookDefaultArmorItem(ArmorMaterialRegistry.COOK_ARMOR, EquipmentSlot.LEGS, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> CHEFS_BOOTS = registerItem("chefs_boots", () -> new CookDefaultArmorItem(ArmorMaterialRegistry.COOK_ARMOR, EquipmentSlot.FEET, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Block> TYPEWRITER_IRON = registerWithItem("typewriter_iron", () -> new TypeWriterBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Block> TYPEWRITER_COPPER = registerWithItem("typewriter_copper", () -> new TypeWriterBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Item> NOTE_PAPER = registerItem("note_paper", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITEABLE = registerItem("note_paper_writeable", () -> new WriteablePaperItem(getSettings().stacksTo(1)));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITTEN = registerItem("note_paper_written", () -> new WrittenPaperItem(getSettingsWithoutTab()));
    public static final RegistrySupplier<Item> LETTER_OPEN = registerItem("letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Item> LETTER_CLOSED = registerItem("letter_closed", () -> new ClosedLetterItem(getSettingsWithoutTab().stacksTo(1)));
    public static final RegistrySupplier<Item> LOVE_LETTER_OPEN = registerItem("love_letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Item> LOVE_LETTER_CLOSED = registerItem("love_letter", () -> new ClosedLetterItem(getSettingsWithoutTab()));
    public static final RegistrySupplier<Block> COBBLESTONE_STOVE = registerWithItem("cobblestone_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> COBBLESTONE_KITCHEN_SINK = registerWithItem("cobblestone_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> COBBLESTONE_COUNTER = registerWithItem("cobblestone_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_CABINET = registerWithItem( "oak_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> OAK_DRAWER = registerWithItem("oak_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> OAK_TABLE = registerWithItem("oak_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> OAK_CHAIR = registerWithItem("oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> OAK_SHELF = registerWithItem("oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_BIG_TABLE = registerWithItem("oak_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> SANDSTONE_STOVE = registerWithItem("sandstone_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> SANDSTONE_KITCHEN_SINK = registerWithItem("sandstone_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> SANDSTONE_COUNTER = registerWithItem("sandstone_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_CABINET = registerWithItem("birch_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> BIRCH_DRAWER = registerWithItem("birch_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> BIRCH_TABLE = registerWithItem("birch_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> BIRCH_CHAIR = registerWithItem("birch_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BIRCH_SHELF = registerWithItem("birch_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_BIG_TABLE = registerWithItem("birch_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> STONE_BRICKS_STOVE = registerWithItem("stone_bricks_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> STONE_BRICKS_KITCHEN_SINK = registerWithItem("stone_bricks_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> STONE_BRICKS_COUNTER = registerWithItem("stone_bricks_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_CABINET = registerWithItem("spruce_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> SPRUCE_DRAWER = registerWithItem("spruce_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> SPRUCE_TABLE = registerWithItem("spruce_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> SPRUCE_CHAIR = registerWithItem("spruce_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SPRUCE_SHELF = registerWithItem("spruce_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_BIG_TABLE = registerWithItem("spruce_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> DEEPSLATE_STOVE = registerWithItem("deepslate_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> DEEPSLATE_KITCHEN_SINK = registerWithItem("deepslate_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> DEEPSLATE_COUNTER = registerWithItem("deepslate_counter", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_CABINET = registerWithItem("dark_oak_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> DARK_OAK_DRAWER = registerWithItem("dark_oak_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> DARK_OAK_TABLE = registerWithItem("dark_oak_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> DARK_OAK_CHAIR = registerWithItem("dark_oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DARK_OAK_SHELF = registerWithItem("dark_oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_BIG_TABLE = registerWithItem("dark_oak_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> GRANITE_STOVE = registerWithItem("granite_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> GRANITE_KITCHEN_SINK = registerWithItem("granite_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> GRANITE_COUNTER = registerWithItem("granite_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_CABINET = registerWithItem("acacia_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> ACACIA_DRAWER = registerWithItem("acacia_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> ACACIA_TABLE = registerWithItem("acacia_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> ACACIA_CHAIR = registerWithItem("acacia_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> ACACIA_SHELF = registerWithItem("acacia_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_BIG_TABLE = registerWithItem("acacia_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> END_STOVE = registerWithItem("end_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> END_KITCHEN_SINK = registerWithItem("end_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> END_COUNTER = registerWithItem("end_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_CABINET = registerWithItem("jungle_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> JUNGLE_DRAWER = registerWithItem("jungle_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> JUNGLE_TABLE = registerWithItem("jungle_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> JUNGLE_CHAIR = registerWithItem("jungle_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> JUNGLE_SHELF = registerWithItem("jungle_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_BIG_TABLE = registerWithItem("jungle_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> MUD_STOVE = registerWithItem("mud_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> MUD_KITCHEN_SINK = registerWithItem("mud_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> MUD_COUNTER = registerWithItem("mud_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_CABINET = registerWithItem("mangrove_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> MANGROVE_DRAWER = registerWithItem("mangrove_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> MANGROVE_TABLE = registerWithItem("mangrove_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> MANGROVE_CHAIR = registerWithItem("mangrove_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> MANGROVE_SHELF = registerWithItem("mangrove_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_BIG_TABLE = registerWithItem("mangrove_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> QUARTZ_STOVE = registerWithItem("quartz_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> QUARTZ_KITCHEN_SINK = registerWithItem("quartz_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> QUARTZ_COUNTER = registerWithItem("quartz_counter", () -> new FacingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_CABINET = registerWithItem("warped_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> WARPED_DRAWER = registerWithItem("warped_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> WARPED_TABLE = registerWithItem("warped_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> WARPED_CHAIR = registerWithItem("warped_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> WARPED_SHELF = registerWithItem("warped_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_BIG_TABLE = registerWithItem("warped_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_STOVE = registerWithItem("red_nether_bricks_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_KITCHEN_SINK = registerWithItem("red_nether_bricks_kitchen_sink", () -> new KitchenSinkBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_COUNTER = registerWithItem("red_nether_bricks_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_CABINET = registerWithItem("crimson_cabinet", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.CABINET_OPEN.get(), SoundEventsRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> CRIMSON_DRAWER = registerWithItem("crimson_drawer", () -> new StorageBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventsRegistry.DRAWER_OPEN.get(), SoundEventsRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> CRIMSON_TABLE = registerWithItem("crimson_table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> CRIMSON_CHAIR = registerWithItem("crimson_chair", () -> new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CRIMSON_SHELF = registerWithItem("crimson_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_BIG_TABLE = registerWithItem("crimson_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F, 2.0F)));
    public static final RegistrySupplier<Block> POTTED_ROSE = registerWithoutItem("potted_rose", () -> new FlowerPotBlock(ROSE.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }

    public static void registerArmor() {
        Registry.register(Registry.ITEM, new CandlelightIdentifier("cooking_hat"), COOKING_HAT.get());
    }


    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties().tab(Candlelight.CANDLELIGHT_TAB);
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettingsWithoutTab(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static Item.Properties getSettingsWithoutTab() {
        return getSettingsWithoutTab(settings -> {
        });
    }


    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }


    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F).sound(SoundType.WOOD);
    }


    private static BlockBehaviour.Properties getWineSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return registerWithItem(name, block, Candlelight.CANDLELIGHT_TAB);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block, @Nullable CreativeModeTab tab) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new CandlelightIdentifier(name), block, tab);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new CandlelightIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return Util.registerItem(ITEMS, ITEM_REGISTRAR, new CandlelightIdentifier(path), itemSupplier);
    }

}