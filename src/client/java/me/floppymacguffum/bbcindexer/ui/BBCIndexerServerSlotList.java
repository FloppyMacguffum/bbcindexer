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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

public class BBCIndexerServerSlotList extends AlwaysSelectedEntryListWidget<BBCIndexerServerSlotEntry> {
	public BBCIndexerServerSlotList(MinecraftClient mc, int width, int height, int top, int bottom, int entryHeight) {
		super(mc, width, height - top - bottom, top, entryHeight);
	}

	public int addEntryToList(BBCIndexerServerSlotEntry entry) {
		return addEntry(entry);
	}

	public void clearAllEntries() {
		clearEntries();
	}

	protected int getScrollbarX() {
		return client.currentScreen.width - 6;
	}

	protected void drawSelectionHighlight(DrawContext context, BBCIndexerServerSlotEntry entry, int fillColor) {
		int startX = 0;
		int endX = client.currentScreen.width + (overflows() ? -6 : -1);
		context.fill(startX, entry.getContentY() - 2, endX, entry.getContentY() + entry.getContentHeight() + 2, -1);
		context.fill(startX + 1, entry.getContentY() - 1, endX - 1, entry.getContentY() + client.textRenderer.fontHeight + 1, 0xff000000);
	}
}
