package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.ContainerBase;
import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.gui.GuiHelper;
import com.codetaylor.mc.athenaeum.gui.Texture;
import com.mojang.blaze3d.matrix.MatrixStack;

public class GuiElementTextureRectangle
    extends GuiElementTextureBase {

  public GuiElementTextureRectangle(
      GuiContainerBase<? extends ContainerBase> guiBase,
      Texture texture,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    this(guiBase, new Texture[]{texture}, elementX, elementY, elementWidth, elementHeight);
  }

  public GuiElementTextureRectangle(
      GuiContainerBase<? extends ContainerBase> guiBase,
      Texture[] textures,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    super(guiBase, elementX, elementY, elementWidth, elementHeight, textures);
  }

  @Override
  public void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

    Texture texture = this.textureGet(mouseX, mouseY);

    this.textureBind(texture);
    this.elementDraw(matrixStack, texture);

  }

  protected void elementDraw(MatrixStack matrixStack, Texture texture) {

    GuiHelper.drawModalRectWithCustomSizedTexture(
        matrixStack,
        this.elementXModifiedGet(),
        this.elementYModifiedGet(),
        0,
        this.texturePositionXModifiedGet(texture),
        this.texturePositionYModifiedGet(texture),
        this.elementWidthModifiedGet(),
        this.elementHeightModifiedGet(),
        texture.getWidth(),
        texture.getHeight()
    );
  }

  @Override
  public void drawForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
    //
  }

}
