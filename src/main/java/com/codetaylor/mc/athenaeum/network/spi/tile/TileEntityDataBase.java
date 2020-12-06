package com.codetaylor.mc.athenaeum.network.spi.tile;

import com.codetaylor.mc.athenaeum.network.spi.tile.data.service.ITileDataService;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This provides a default implementation of the packet update methods.
 * <p>
 * <p>
 * Call {@link com.codetaylor.mc.athenaeum.network.spi.tile.TileEntityDataBase#registerTileDataForNetwork(ITileData[])}
 * in the subclass' constructor to register tile data.
 */
public abstract class TileEntityDataBase
    extends TileDataContainerBase {

  protected final ITileDataService tileDataService;

  protected TileEntityDataBase(TileEntityType<?> tileEntityType, ITileDataService tileDataService) {

    super(tileEntityType);
    this.tileDataService = tileDataService;
  }

  // ---------------------------------------------------------------------------
  // - Network
  // ---------------------------------------------------------------------------

  protected void registerTileDataForNetwork(ITileData[] data) {

    this.tileDataService.register(this, data);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void onTileDataUpdate() {
    //
  }

  @Nonnull
  @Override
  public CompoundNBT getUpdateTag() {

    return this.write(new CompoundNBT());
  }

  @Nullable
  @Override
  public SUpdateTileEntityPacket getUpdatePacket() {

    return new SUpdateTileEntityPacket(this.pos, -1, this.getUpdateTag());
  }

}
