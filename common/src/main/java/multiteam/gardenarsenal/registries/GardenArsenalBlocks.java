package multiteam.gardenarsenal.registries;

import me.shedaniel.architectury.registry.BlockProperties;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import me.shedaniel.architectury.registry.ToolType;
import multiteam.gardenarsenal.GardenArsenal;
import multiteam.gardenarsenal.blocks.AmmoCrate;
import multiteam.gardenarsenal.blocks.BarricadeBlock;
import multiteam.gardenarsenal.blocks.TrapCake;
import multiteam.gardenarsenal.blocks.WarTacticTable;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class GardenArsenalBlocks {

                                                                  //see this line here? ingore it cuz its just cringe ->


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(GardenArsenal.MOD_ID, Registry.BLOCK_REGISTRY);

    //Vanilla Garden Arsenal - as of v0.3.1
    public static final RegistrySupplier<Block> MACHINE_BLOCK = BLOCKS.register("machine_block", () -> new Block(BlockProperties.of(Material.METAL).tool(ToolType.PICKAXE, 2).strength(6, 6).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistrySupplier<Block> TRAP_CAKE = BLOCKS.register("trap_cake", () -> new TrapCake(BlockProperties.of(Material.CAKE).sound(SoundType.WOOL).strength(0.5F)));
    public static final RegistrySupplier<Block> WAR_TACTIC_TABLE = BLOCKS.register("war_tactic_table", () -> new WarTacticTable(BlockProperties.of(Material.WOOD).tool(ToolType.AXE, 1).strength(2, 2).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> AMMO_CRATE = BLOCKS.register("ammo_crate", () -> new AmmoCrate(BlockProperties.of(Material.WOOD).tool(ToolType.AXE, 1).strength(2, 2).sound(SoundType.WOOD).noOcclusion()));

    //Makers Shift Update - v0.4
    public static final RegistrySupplier<Block> SCRAP_WOOD_PILE = registerWithItem("scrap_wood_pile", () -> new Block(BlockProperties.of(Material.WOOD).tool(ToolType.AXE).strength(1.0f, 2.0f).sound(SoundType.WOOD)), new Item.Properties().tab(GardenArsenalItems.MISC));
    public static final RegistrySupplier<Block> MAKERS_CONCRETE_POWDER_BLOCK = registerWithItem("makers_concrete_powder", () -> new Block(BlockProperties.of(Material.STONE).tool(ToolType.SHOVEL).strength(0.5f,0.5f).sound(SoundType.SAND)), new Item.Properties().tab(GardenArsenalItems.MISC));
    public static final RegistrySupplier<Block> REINFORCED_METAL_BLOCK = registerWithItem("reinforced_metal_block", () -> new Block(BlockProperties.of(Material.HEAVY_METAL).tool(ToolType.PICKAXE).strength(10.f, 200.0f).sound(SoundType.NETHERITE_BLOCK)), new Item.Properties().tab(GardenArsenalItems.MISC));
    public static final RegistrySupplier<Block> INDUSTRIAL_BARRIER_BLOCK = registerWithItem("industrial_barrier_block", () -> new Block(BlockProperties.of(Material.HEAVY_METAL).tool(ToolType.PICKAXE).strength(10.0f, 100.0f).sound(SoundType.NETHERITE_BLOCK)), new Item.Properties().tab(GardenArsenalItems.MISC));
    public static final RegistrySupplier<Block> BARRICADE_SURVIVALIST = registerWithItem("survivalist_barricade", () -> new BarricadeBlock(BlockProperties.of(Material.WOOD).tool(ToolType.AXE).strength(2.0f, 3.0f).sound(SoundType.BAMBOO)), new Item.Properties().tab(GardenArsenalItems.MISC));
    public static final RegistrySupplier<Block> BARRICADE_MAKER = registerWithItem("maker_barricade", () -> new BarricadeBlock(BlockProperties.of(Material.STONE).tool(ToolType.PICKAXE).strength(15.0f, 100.0f).sound(SoundType.NETHER_BRICKS)), new Item.Properties().tab(GardenArsenalItems.MISC));
    public static final RegistrySupplier<Block> BARRICADE_INDUSTRIAL = registerWithItem("industrial_barricade", () -> new BarricadeBlock(BlockProperties.of(Material.HEAVY_METAL).tool(ToolType.PICKAXE).strength(40.0f, 900.0f).sound(SoundType.ANVIL)), new Item.Properties().tab(GardenArsenalItems.MISC));



    public static void init() {
        BLOCKS.register();
    }

    private static <T extends Block> RegistrySupplier<T> registerNoItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block, Item.Properties itemProperties){
        RegistrySupplier<T> ret = registerNoItem(name, block);
        GardenArsenalItems.ITEMS.register(name, () -> new BlockItem(ret.get(), itemProperties));
        return ret;
    }
}
