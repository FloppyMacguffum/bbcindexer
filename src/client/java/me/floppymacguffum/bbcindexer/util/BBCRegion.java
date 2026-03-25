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
package me.floppymacguffum.bbcindexer.util;

import net.minecraft.network.chat.Component;

public enum BBCRegion {
	NORTH_AMERICA(Component.literal("North America"), "NA"),
	EUROPE(Component.literal("Europe"), "EU"),
	ASIA(Component.literal("Asia"), "AS"),
	SOUTH_AMERICA(Component.literal("South America"), "SA"),
	AFRICA(Component.literal("Africa"), "AF"),
	OCEANIA(Component.literal("Oceania"), "OC"),
	ANTARCTICA(Component.literal("Antarctica"), "AN"),
	NONE(Component.literal("None"), "");

	private final Component readableRegion;
	private final String apiRegion;

	BBCRegion(Component readableRegion, String apiRegion) {
		this.readableRegion = readableRegion;
		this.apiRegion = apiRegion;
	}

	public Component getReadableRegion() {
		return readableRegion;
	}

	public String getApiRegion() {
		return apiRegion;
	}
}
