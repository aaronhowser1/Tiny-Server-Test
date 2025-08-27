package dev.aaronhowser.mods.tinyservertest;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(TinyServerTest.MODID)
public class TinyServerTest {
	public static final String MODID = "tinyservertest";
	public static final Logger LOGGER = LogUtils.getLogger();

	public TinyServerTest(IEventBus modEventBus, ModContainer modContainer) {
		
	}
}
