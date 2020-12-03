package com.codetaylor.mc.athenaeum.gui.element;

public interface IGuiElement {

  boolean elementIsVisible(double mouseX, double mouseY);

  boolean elementIsMouseInside(double mouseX, double mouseY);

}
