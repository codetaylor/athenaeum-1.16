package com.codetaylor.mc.athenaeum.network.spi.tile.data;

import com.codetaylor.mc.athenaeum.network.spi.tile.TileDataBase;
import net.minecraft.network.PacketBuffer;

public class TileDataBoolean
    extends TileDataBase {

  private boolean value;

  public TileDataBoolean(boolean initialValue) {

    this(initialValue, 1);
  }

  public TileDataBoolean(boolean initialValue, int updateInterval) {

    super(updateInterval);
    this.set(initialValue);
  }

  public void set(boolean value) {

    if (value != this.value) {
      this.value = value;
      this.setDirty(true);
    }
  }

  public boolean get() {

    return this.value;
  }

  @Override
  public void read(PacketBuffer buffer) {

    this.value = buffer.readBoolean();
  }

  @Override
  public void write(PacketBuffer buffer) {

    buffer.writeBoolean(this.value);
  }

}
