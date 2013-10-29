package Murder.RecipeKill;


import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="RecipeKill", name="Recipe Kill", version="1.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels={"RecipeKill"}, packetHandler=PacketManager.class)
public class recipeKill
{
  public static final String MOD_ID = "RecipeKill";
  public static final String MOD_NAME = "Recipe Kill";
  public static final String CHANNEL = "RecipeKill";

  @Instance("RecipeKill")
  
  public static recipeKill instance;
  private ConfigManager config;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    this.config = new ConfigManager(event.getSuggestedConfigurationFile());
    this.config.readConfig();
  }
  @EventHandler
  public void init(FMLInitializationEvent event) {
    NetworkRegistry.instance().registerConnectionHandler(new PlayerManager());
  }

  @EventHandler
  public void serverStarting(FMLServerStartingEvent e) {
    if (FMLCommonHandler.instance().getEffectiveSide().isServer())
      RecipeManager.removeRecipes();
  }
}
