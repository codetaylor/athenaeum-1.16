package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.ContainerBase;
import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;

public abstract class GuiElementTiledTextureAtlasSpriteBase
    extends GuiElementBase {

  public GuiElementTiledTextureAtlasSpriteBase(
      GuiContainerBase<? extends ContainerBase> guiBase,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    super(guiBase, elementX, elementY, elementWidth, elementHeight);
  }

  @Override
  public void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

    this.textureBind(PlayerContainer.LOCATION_BLOCKS_TEXTURE);

    this.guiBase.drawScaledTexturedModalRectFromIconAnchorBottomLeft(
        matrixStack,
        this.elementXModifiedGet(),
        this.elementYModifiedGet(),
        this.textureAtlasSpriteGet(),
        this.elementWidthModifiedGet(),
        this.elementHeightModifiedGet()
    );
  }

  @Override
  public void drawForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
    //
  }

  protected abstract TextureAtlasSprite textureAtlasSpriteGet();
}