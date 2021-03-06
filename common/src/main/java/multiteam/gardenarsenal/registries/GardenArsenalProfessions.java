package multiteam.gardenarsenal.registries;

import com.google.common.collect.ImmutableSet;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import multiteam.gardenarsenal.GardenArsenal;
import multiteam.gardenarsenal.GardenArsenalExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;

public class GardenArsenalProfessions {

    private static DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(GardenArsenal.MOD_ID, Registry.VILLAGER_PROFESSION_REGISTRY);

    public static RegistrySupplier<VillagerProfession> GARDEN_SOLDIER_COMMANDER = PROFESSIONS.register("garden_soldier_commander",
            () -> GardenArsenalExpectPlatform.createProfession("garden_soldier_commander", GardenArsenalPois.SOLDIER_COMMANDER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
    public static RegistrySupplier<VillagerProfession> GARDEN_SOLDIER = PROFESSIONS.register("garden_soldier",
            () -> GardenArsenalExpectPlatform.createProfession("garden_soldier", GardenArsenalPois.SOLDIER_POI.get(),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));

    public static void init() {
        PROFESSIONS.register();
    }
}
