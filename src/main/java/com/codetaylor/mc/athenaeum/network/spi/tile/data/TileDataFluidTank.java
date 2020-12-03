package com.codetaylor.mc.athenaeum.network.spi.tile.data;

import com.codetaylor.mc.athenaeum.network.spi.tile.ITileDataFluidTank;
import com.codetaylor.mc.athenaeum.network.spi.tile.TileDataBase;
import com.google.common.base.Preconditions;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.io.IOException;

public class TileDataFluidTank<T extends FluidTank & ITileDataFluidTank>
    extends TileDataBase {

  private T fluidTank;

  public TileDataFluidTank(T fluidTank) {

    this(fluidTank, 1);
  }

  public TileDataFluidTank(T fluidTank, int updateInterval) {

    super(updateInterval);
    this.fluidTank = fluidTank;
    this.fluidTank.addObserver((handler, slot) -> this.setDirty(true));
    this.setDirty(true);
  }

  public FluidTank getFluidTank() {

    return this.fluidTank;
  }

  @Override
  public void setDirty(boolean dirty) {

    super.setDirty(dirty);
  }

  @Override
  public void read(PacketBuffer buffer) throws IOException {

    this.fluidTank.readFromNBT(Preconditions.checkNotNull(buffer.readCompoundTag()));
  }

  @Override
  public void write(PacketBuffer buffer) {

    buffer.writeCompoundTag(this.fluidTank.writeToNBT(new CompoundNBT()));
  }
}
