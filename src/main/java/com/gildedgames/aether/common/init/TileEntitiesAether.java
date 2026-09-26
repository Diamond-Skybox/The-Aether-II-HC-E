package com.gildedgames.aether.common.init;

import com.gildedgames.aether.common.entities.tiles.*;
import com.gildedgames.aether.common.entities.tiles.multiblock.TileEntityMultiblockDummy;
import net.minecraft.tileentity.TileEntity;

public class TileEntitiesAether
{

	public static final String
			ALTAR_ID = "aether.altar",
			HOLYSTONE_FURNACE_ID = "aether.holystone_furnace",
			SKYROOT_CHEST_ID = "aether.skyroot_chest",
			SKYROOT_SIGN_ID = "aether.skyroot_sign",
			MULTIBLOCK_DUMMY = "aether.multiblock_dummy",
			MOA_EGG_ID = "aether.moa_egg",
			ICESTONE_COOLER_ID = "aether.icestone_cooler",
			INCUBATOR_ID = "aether.incubator",
			PRESENT_ID = "aether.present",
			WILDCARD_ID = "aether.wildcard",
			MASONRY_BENCH_ID = "aether.masonry_bench",
			OUTPOST_CAMPFIRE_ID = "aether.outpost_campfire",
			TELEPORTER_ID = "aether.aether_teleporter",
			SKYROOT_BED_ID = "aether.skyroot_bed";

	public static void preInit()
	{
		TileEntity.register(ALTAR_ID, TileEntityAltar.class);
		TileEntity.register(HOLYSTONE_FURNACE_ID, TileEntityHolystoneFurnace.class);
		TileEntity.register(SKYROOT_CHEST_ID, TileEntitySkyrootChest.class);
		TileEntity.register(SKYROOT_SIGN_ID, TileEntitySkyrootSign.class);
		TileEntity.register(MULTIBLOCK_DUMMY, TileEntityMultiblockDummy.class);
		TileEntity.register(MOA_EGG_ID, TileEntityMoaEgg.class);
		TileEntity.register(ICESTONE_COOLER_ID, TileEntityIcestoneCooler.class);
		TileEntity.register(INCUBATOR_ID, TileEntityIncubator.class);
		TileEntity.register(PRESENT_ID, TileEntityPresent.class);
		TileEntity.register(WILDCARD_ID, TileEntityWildcard.class);
		TileEntity.register(MASONRY_BENCH_ID, TileEntityMasonryBench.class);
		TileEntity.register(OUTPOST_CAMPFIRE_ID, TileEntityOutpostCampfire.class);
		TileEntity.register(TELEPORTER_ID, TileEntityTeleporter.class);
		TileEntity.register(SKYROOT_BED_ID, TileEntitySkyrootBed.class);
	}

}
