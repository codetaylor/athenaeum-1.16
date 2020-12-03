package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.gui.Texture;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;

public abstract class GuiElementBase
    implements IGuiElement {

  protected GuiContainerBase<? extends Container> guiBase;
  protected int elementX;
  protected int elementY;
  protected int elementWidth;
  protected int elementHeight;

  protected boolean visible;

  public GuiElementBase(
      GuiContainerBase<? extends Container> guiBase,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight
  ) {

    this.guiBase = guiBase;
    this.elementX = elementX;
    this.elementHeight = elementHeight;
    this.elementY = elementY;
    this.elementWidth = elementWidth;
    this.visible = true;
  }

  public abstract void drawBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY);

  public abstract void drawForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY);

  public void update(float partialTicks) {
    //
  }

  @Override
  public boolean elementIsVisible(double mouseX, double mouseY) {

    return this.visible;
  }

  public void setVisible(boolean visible) {

    this.visible = visible;
  }

  @Override
  public boolean elementIsMouseInside(double mouseX, double mouseY) {

    return this.guiBase.isPointInRegion(
        this.elementX,
        this.elementY,
        this.elementWidth,
        this.elementHeight,
        mouseX,
        mouseY
    );
  }

  protected void textureBind(ResourceLocation resourceLocation) {

    Minecraft.getInstance().getTextureManager().bindTexture(resourceLocation);
  }

  protected void textureBind(Texture texture) {

    this.textureBind(texture.getResourceLocation());
  }

  protected int elementWidthModifiedGet() {

    return this.elementWidth;
  }

  protected int elementHeightModifiedGet() {

    return this.elementHeight;
  }

  protected int elementXModifiedGet() {

    return this.elementX + this.guiBase.guiContainerOffsetXGet();
  }

  protected int elementYModifiedGet() {

    return this.elementY + this.guiBase.guiContainerOffsetYGet();
  }

}
