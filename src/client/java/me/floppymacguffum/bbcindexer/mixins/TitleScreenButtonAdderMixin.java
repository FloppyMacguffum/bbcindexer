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
package me.floppymacguffum.bbcindexer.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.TitleScreen;

import me.floppymacguffum.bbcindexer.ui.BBCIndexerButton;
import me.floppymacguffum.bbcindexer.ui.BBCIndexerMainMenu;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenButtonAdderMixin {
	private boolean foundBBCButton = false;

	@Inject(at = @At("RETURN"), method = "init")
	private void init(CallbackInfo info) {
		if(MinecraftClient.getInstance().currentScreen instanceof TitleScreen) {
			TitleScreen ts = (TitleScreen) MinecraftClient.getInstance().currentScreen;
			for(Drawable d : ts.drawables)	{
				if(d instanceof BBCIndexerButton) {
					if(((BBCIndexerButton) d).getId() != -69) {
						foundBBCButton = true;
						break;
					}
				}
			}
			if(!foundBBCButton) ts.addDrawableChild(new BBCIndexerButton(-69, 0, 0, Text.literal("Break Blocks Club Servers"), button -> MinecraftClient.getInstance().setScreen(new BBCIndexerMainMenu(ts))));
		}
	}
}
