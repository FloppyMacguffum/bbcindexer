/*
MIT License

Copyright (c) 2026 FloppyMacguffum

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

import net.minecraft.DetectedVersion;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import me.floppymacguffum.bbcindexer.BBCIndexerMainClient;
import me.floppymacguffum.bbcindexer.util.BBCRegion;

public class BBCIndexerSearchServers extends Screen {
	private Screen parentScreen;
	private EditBox motdField;
	private EditBox versionField;
	private Checkbox crackedServers;
	private BBCRegion region;

	public BBCIndexerSearchServers(Screen parentScreen) {
		super(Component.literal("Break Blocks Club Server Search"));
		this.parentScreen = parentScreen;
	}

	protected void init() {
		super.init();
		this.region = BBCRegion.NONE;
		addRenderableWidget(new BBCIndexerButton(0, 0, 20, 20, Component.literal("<-"), button -> minecraft.gui.setScreen(parentScreen)));
		motdField = new EditBox(font, width / 2 - 100, 62 + font.lineHeight, 200, 20, Component.literal("MOTD (Wildcard is supported)"));
		addRenderableWidget(motdField);
		versionField = new EditBox(font, width / 2 - 100, 100 + font.lineHeight, 200, 20, Component.literal("Version (Wildcard is supported)"));
		versionField.setValue(DetectedVersion.tryDetectVersion().id());
		addRenderableWidget(versionField);
		crackedServers = Checkbox.builder(Component.literal("Show only cracked servers"), font).pos(width / 2 - 100, 139).selected(false).build();
		addRenderableWidget(crackedServers);
		addRenderableWidget(CycleButton.builder(BBCRegion::getReadableRegion, region).withValues(BBCRegion.values()).create(width / 2 - 100, 166, 200, 20, Component.literal("Region"), (button, bbcRegion) -> region = bbcRegion));
		addRenderableWidget(new BBCIndexerButton(width / 2 - 100, 190, 200, 20, Component.literal("Search"), button -> minecraft.gui.setScreen(new BBCIndexerServerBrowser(this, motdField.getValue(), versionField.getValue(), region.getApiRegion(), crackedServers.selected(), 1))));
	}

	public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
		super.extractRenderState(context, mouseX, mouseY, deltaTicks);
		context.centeredText(font, title, width / 2, 15, -1);
		context.centeredText(font, Component.literal("MOTD (Wildcard is supported)"), width / 2, 68 - font.lineHeight, -1);
		context.centeredText(font, Component.literal("Version (Wildcard is supported)"), width / 2, 104 - font.lineHeight, -1);
	}

	public void onClose() {
		minecraft.gui.setScreen(parentScreen);
	}
}
