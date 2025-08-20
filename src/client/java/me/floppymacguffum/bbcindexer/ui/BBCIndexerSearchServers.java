/*
MIT License

Copyright (c) 2025 FloppyMacguffum

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package me.floppymacguffum.bbcindexer.ui;

import net.minecraft.MinecraftVersion;
import net.minecraft.text.Text;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;

import me.floppymacguffum.bbcindexer.util.BBCRegion;

public class BBCIndexerSearchServers extends Screen {
	private Screen parentScreen;
	private TextFieldWidget motdField;
	private TextFieldWidget versionField;
	private CyclingButtonWidget regionField;
	private BBCRegion region;

	public BBCIndexerSearchServers(Screen parentScreen) {
		super(Text.literal("Break Blocks Club Server Search"));
		this.parentScreen = parentScreen;
		this.region = BBCRegion.NONE;
	}

	protected void init() {
		super.init();
		addDrawableChild(new BBCIndexerButton(0, 0, 0, 20, 20, Text.literal("<-"), button -> client.setScreen(parentScreen)));
		motdField = new TextFieldWidget(textRenderer, width / 2 - 100, 62 + textRenderer.fontHeight, 200, 20, Text.literal("MOTD (Wildcard is supported)"));
		addDrawableChild(motdField);
		versionField = new TextFieldWidget(textRenderer, width / 2 - 100, 100 + textRenderer.fontHeight, 200, 20, Text.literal("Version (Wildcard is supported)"));
		versionField.setText(MinecraftVersion.create().id());
		addDrawableChild(versionField);
		addDrawableChild(CyclingButtonWidget.builder(BBCRegion::getReadableRegion).values(BBCRegion.values()).initially(BBCRegion.NONE).build(width / 2 - 100, 142, 200, 20, Text.literal("Region"), (button, bbcRegion) -> region = bbcRegion));
		addDrawableChild(new BBCIndexerButton(1, width / 2 - 100, 166, 200, 20, Text.literal("Search"), button -> client.setScreen(new BBCIndexerServerBrowser(this, motdField.getText(), versionField.getText(), region.getApiRegion(), 1))));
	}

	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, -1);
		context.drawCenteredTextWithShadow(textRenderer, Text.literal("MOTD (Wildcard is supported)"), width / 2, 68 - textRenderer.fontHeight, -1);
		context.drawCenteredTextWithShadow(textRenderer, Text.literal("Version (Wildcard is supported)"), width / 2, 104 - textRenderer.fontHeight, -1);
	}

	public void close() {
		client.setScreen(parentScreen);
	}
}
