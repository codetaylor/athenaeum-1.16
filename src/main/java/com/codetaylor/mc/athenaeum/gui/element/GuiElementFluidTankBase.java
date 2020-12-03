package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class GuiElementFluidTankBase
    extends GuiElementTiledTextureAtlasSpriteBase {

  protected final FluidTank fluidTank;
  private TextureAtlasSprite fluidSprite;

  public GuiElementFluidTankBase(GuiContainerBase<? extends Container> guiBase, int elementX, int elementY, int elementWidth, int elementHeight, FluidTank fluidTank) {

    super(guiBase, elementX, elementY, elementWidth, elementHeight);
    this.fluidTank = fluidTank;
  }

  protected float scalarPercentageGet() {

    int fluidAmount = this.fluidTank.getFluidAmount();

    if (fluidAmount > 0) {
      int capacity = this.fluidTank.getCapacity();
      return Math.max((float) fluidAmount / (float) capacity, 1 / (float) this.elementHeight);
    }

    return 0;
  }

  @Override
  protected TextureAtlasSprite textureAtlasSpriteGet() {

    FluidStack fluidStack = this.fluidTank.getFluid();

    if (fluidStack == FluidStack.EMPTY) {
      this.fluidSprite = null;

    } else if (this.fluidSprite == null) {
      ResourceLocation resourceLocation = fluidStack.getFluid().getAttributes().getStillTexture();
      this.fluidSprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(resourceLocation);
    }

    return this.fluidSprite;
  }

  @Override
  public void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

    FluidStack fluid = this.fluidTank.getFluid();

    if (fluid != FluidStack.EMPTY) {
      int color = fluid.getFluid().getAttributes().getColor();
      RenderSystem.color4f(
          ((color >> 16) & 0xFF) / 255f,
          ((color >> 8) & 0xFF) / 255f,
          (color & 0xFF) / 255f,
          ((color >> 24) & 0xFF) / 255f
      );
    }

    super.drawBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);

    if (fluid != FluidStack.EMPTY) {
      RenderSystem.color4f(1, 1, 1, 1);
    }
  }
}
