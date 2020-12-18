package com.codetaylor.mc.athenaeum.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public final class FluidHelper {

  private static final ItemStack WATER_BOTTLE;

  static {
    CompoundNBT waterPotion = new CompoundNBT();
    waterPotion.putString("Potion", "minecraft:water");
    WATER_BOTTLE = new ItemStack(Items.POTION, 1);
    WATER_BOTTLE.setTag(waterPotion);
  }

  public static boolean drainWaterIntoBottle(PlayerEntity player, IFluidHandler tank) {

    if (player.getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE) {

      FluidStack drain = tank.drain(250, IFluidHandler.FluidAction.SIMULATE);

      if (!drain.isEmpty()
          && drain.getFluid() != null
          && drain.getAmount() == 250
          && drain.getFluid() == Fluids.WATER) {

        if (player.addItemStackToInventory(WATER_BOTTLE.copy())) {

          if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
          }

          tank.drain(250, IFluidHandler.FluidAction.EXECUTE);
          return true;
        }
      }
    }

    return false;
  }

  public static boolean drainWaterFromBottle(PlayerEntity player, IFluidHandler fluidHandler) {

    if (player.getHeldItemMainhand().getItem() == Items.POTION
        && WATER_BOTTLE.getTag() != null
        && WATER_BOTTLE.getTag().equals(player.getHeldItemMainhand().getTag())) {

      FluidStack water = new FluidStack(Fluids.WATER, 250);

      if (fluidHandler.fill(water, IFluidHandler.FluidAction.SIMULATE) == water.getAmount()) {

        if (player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {

          if (!player.isCreative()) {
            player.getHeldItemMainhand().shrink(1);
          }

          fluidHandler.fill(water, IFluidHandler.FluidAction.EXECUTE);
          return true;
        }
      }
    }

    return false;
  }

  public static void playFluidFillSoundServer(Fluid fluid, World world, BlockPos pos) {

    if (world.isRemote) {
      return;
    }
    world.playSound(
        null,
        pos.getX() + 0.5f,
        pos.getY() + 0.5f,
        pos.getZ() + 0.5f,
        fluid.getAttributes().getFillSound(),
        SoundCategory.BLOCKS,
        0.2F + (float) Math.random() * 0.2F,
        0.9F + (float) Math.random() * 0.15F
    );
  }

  public static void playFluidEmptySoundServer(Fluid fluid, World world, BlockPos pos) {

    if (world.isRemote) {
      return;
    }

    world.playSound(
        null,
        pos.getX() + 0.5f,
        pos.getY() + 0.5f,
        pos.getZ() + 0.5f,
        fluid.getAttributes().getEmptySound(),
        SoundCategory.BLOCKS,
        0.2F + (float) Math.random() * 0.2F,
        0.9F + (float) Math.random() * 0.15F
    );
  }

  private FluidHelper() {
    //
  }

}
