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

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.ManageServerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;

import me.floppymacguffum.bbcindexer.util.BBCServer;
import me.floppymacguffum.bbcindexer.util.BBCApi;

public class BBCIndexerServerBrowser extends Screen {
	private Screen parentScreen;
	private BBCIndexerButton bbcBackButton, bbcNextButton, bbcAddServerButton, bbcJoinServerButton;
	private BBCServer[] bbcServers;
	private BBCIndexerServerSlotList bbcServerListSlots;
	private ServerData globServerInfo;
	private String motd, version, region;
	private int page;
	private boolean needsUpdating, erroredOut, cracked;

	public BBCIndexerServerBrowser(Screen parentScreen, String motd, String version, String region, boolean cracked, int page) {
		super(Component.literal("Break Blocks Club Server Browser"));
		this.parentScreen = parentScreen;
		this.motd = motd;
		this.version = version;
		this.region = region;
		this.page = page;
		this.bbcServers = null;
		BBCApi.getBBCServers(this, motd, version, region, cracked, page);
		this.needsUpdating = false;
		this.erroredOut = false;
	}

	protected void init() {
		super.init();
		addRenderableWidget(new BBCIndexerButton(0, 0, 20, 20, Component.literal("<-"), button -> minecraft.setScreen(parentScreen)));
		bbcBackButton = new BBCIndexerButton(0, height - 20, 60, 20, Component.literal("Back"), button -> previousPage());
		bbcBackButton.active = (bbcServers != null && page >= 2);
		addRenderableWidget(bbcBackButton);
		bbcNextButton = new BBCIndexerButton(width - 60, height - 20, 60, 20, Component.literal("Next"), button -> nextPage());
		bbcNextButton.active = (bbcServers != null && bbcServers.length > 19);
		addRenderableWidget(bbcNextButton);
		bbcAddServerButton = new BBCIndexerButton(width - 166, height - 20, 100, 20, Component.literal("Add to my servers"), button -> addToServerList());
		bbcJoinServerButton = new BBCIndexerButton(66, height - 20, 100, 20, Component.literal("Join server"), button -> joinServer());
		bbcServerListSlots = new BBCIndexerServerSlotList(minecraft, width, height, 6 + (font.lineHeight + 2) * 3, 30, font.lineHeight + 4);
		addRenderableWidget(bbcServerListSlots);
		if(bbcServers != null) {
			for(BBCServer srv : bbcServers) {
				bbcServerListSlots.addEntryToList(new BBCIndexerServerSlotEntry(srv));
			}
		}
		bbcAddServerButton.active = bbcServerListSlots.getSelected() != null;
		bbcJoinServerButton.active = bbcServerListSlots.getSelected() != null;
		addRenderableWidget(bbcAddServerButton);
		addRenderableWidget(bbcJoinServerButton);
	}

	public void render(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
		bbcBackButton.active = (bbcServers != null && page >= 2);
		bbcNextButton.active = (bbcServers != null && bbcServers.length > 19);
		bbcAddServerButton.active = bbcServerListSlots.getSelected() != null;
		bbcJoinServerButton.active = bbcServerListSlots.getSelected() != null;
		context.drawCenteredString(font, title, width / 2, 7, -1);
		context.drawCenteredString(font, Component.literal("Page: " + (erroredOut ? "\247cError" : (bbcServers == null ? "Loading..." : page))), width / 2, 20, -1);
		if(needsUpdating) {
			bbcServerListSlots.clearAllEntries();
			for(BBCServer srv : bbcServers) {
				bbcServerListSlots.addEntryToList(new BBCIndexerServerSlotEntry(srv));
			}
			needsUpdating = false;
		}
	}

	public void onClose() {
		minecraft.setScreen(parentScreen);
	}

	private void nextPage() {
		page++;
		bbcServerListSlots.clearAllEntries();
		BBCApi.getBBCServers(this, motd, version, region, cracked, page);
	}

	private void previousPage() {
		page--;
		bbcServerListSlots.clearAllEntries();
		BBCApi.getBBCServers(this, motd, version, region, cracked, page);
	}

	private void addToServerList() {
		String base = bbcServerListSlots.getSelected().getBBCServer().getAddress();
		int port = bbcServerListSlots.getSelected().getBBCServer().getPort();
		globServerInfo = new ServerData("BBC Server", base + ":" + port, ServerData.Type.OTHER);
		minecraft.setScreen(new ManageServerScreen(this, Component.literal("Add BBC Server"), this::actuallyAddServer, globServerInfo));
	}

	private void actuallyAddServer(boolean confirmation) {
		if(confirmation) {
			ServerData si = new ServerData("", "", ServerData.Type.OTHER);
			si.copyNameIconFrom(globServerInfo);
			ServerList sl = new ServerList(minecraft);
			sl.load();
			sl.add(si, false);
			sl.save();
		}
		minecraft.setScreen(this);
	}

	private void joinServer() {
		String base = bbcServerListSlots.getSelected().getBBCServer().getAddress();
		int port = bbcServerListSlots.getSelected().getBBCServer().getPort();
		ServerAddress sa = new ServerAddress(base, port);
		ServerData si = new ServerData("BBC Server", base + ":" + port, ServerData.Type.OTHER);
		ConnectScreen.startConnecting(this, minecraft, sa, si, false, null);
	}

	public void setBBCServers(BBCServer[] bbcServers) {
		this.bbcServers = bbcServers;
		if(bbcServers != null) {
			if(bbcServerListSlots == null) {
				needsUpdating = true;
				return;
			}
			bbcServerListSlots.clearAllEntries();
			for(BBCServer srv : bbcServers) {
				bbcServerListSlots.addEntryToList(new BBCIndexerServerSlotEntry(srv));
			}
		}
	}

	public void setErroredOut(boolean erroredOut) {
		this.erroredOut = erroredOut;
	}
}
