package com.codetaylor.mc.athenaeum;

import com.codetaylor.mc.athenaeum.util.ConfigHelper;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(AthenaeumMod.MOD_ID)
public class AthenaeumMod {

  public static final String MOD_ID = "athenaeum";
  public static final Logger LOGGER = LogManager.getLogger();

  public static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get();
  public static final Path MOD_CONFIG_PATH = CONFIG_PATH.resolve(MOD_ID);

  public AthenaeumMod() {

    try {
      Files.createDirectories(MOD_CONFIG_PATH);

    } catch (IOException e) {
      LOGGER.error("Error creating folder: " + MOD_CONFIG_PATH, e);
    }

    ModLoadingContext modLoadingContext = ModLoadingContext.get();
    modLoadingContext.registerConfig(ModConfig.Type.CLIENT, AthenaeumModClientConfig.CONFIG_SPEC, MOD_ID + "/" + MOD_ID + ".toml");
    ConfigHelper.loadConfig(AthenaeumModClientConfig.CONFIG_SPEC, MOD_CONFIG_PATH.resolve(MOD_ID + ".toml"));
  }
}