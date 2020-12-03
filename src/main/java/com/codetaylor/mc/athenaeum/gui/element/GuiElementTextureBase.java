package com.codetaylor.mc.athenaeum.gui.element;

import com.codetaylor.mc.athenaeum.gui.ContainerBase;
import com.codetaylor.mc.athenaeum.gui.GuiContainerBase;
import com.codetaylor.mc.athenaeum.gui.Texture;

public abstract class GuiElementTextureBase
    extends GuiElementBase {

  protected Texture[] textures;

  public GuiElementTextureBase(
      GuiContainerBase<? extends ContainerBase> guiBase,
      int elementX,
      int elementY,
      int elementWidth,
      int elementHeight,
      Texture[] textures
  ) {

    super(guiBase, elementX, elementY, elementWidth, elementHeight);
    this.textures = textures;
  }

  protected int texturePositionYModifiedGet(Texture texture) {

    return texture.getPositionY();
  }

  protected int texturePositionXModifiedGet(Texture texture) {

    return texture.getPositionX();
  }

  protected Texture textureGet(int mouseX, int mouseY) {

    return this.textures[this.textureIndexGet(mouseX, mouseY)];
  }

  protected int textureIndexGet(int mouseX, int mouseY) {

    return 0;
  }

}
