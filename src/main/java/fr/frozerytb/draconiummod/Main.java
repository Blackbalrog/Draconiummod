package fr.frozerytb.draconiummod;

import fr.frozerytb.draconiummod.init.BlockInit;
import fr.frozerytb.draconiummod.init.CapabilitiesInit;
import fr.frozerytb.draconiummod.init.FluidInit;
import fr.frozerytb.draconiummod.init.ItemInit;
import fr.frozerytb.draconiummod.network.ModNetworkHandler;
import fr.frozerytb.draconiummod.objects.enchantments.EnchantRange;
import fr.frozerytb.draconiummod.proxy.CommonProxy;
import fr.frozerytb.draconiummod.tabs.DraconiummodTab;
import fr.frozerytb.draconiummod.util.Reference;
import fr.frozerytb.draconiummod.util.handlers.PlayerJoinEventHandler;
import fr.frozerytb.draconiummod.util.handlers.RegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static CommonProxy proxy;

    public static Logger logger;

    public static EnchantRange enchantRange;

    public static final CreativeTabs DraconiummodTab = new DraconiummodTab("draconiummod") {};

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(SoundEventHandler.class);
        MinecraftForge.EVENT_BUS.register(PlayerJoinEventHandler.class);
        logger = event.getModLog();

        ModNetworkHandler.registerMessages();

        proxy.preInit();

        FluidInit.initFluids();
        RegistryHandler.preInitRegistries();
        CapabilitiesInit.preInit(event);

        MinecraftForge.EVENT_BUS.register(new ItemInit());
        MinecraftForge.EVENT_BUS.register(new BlockInit());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        RegistryHandler.initRegistries();
        proxy.init();
    }

    @Mod.EventBusSubscriber(modid = Reference.MODID)
    public static class SoundEventHandler {

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            final SoundEvent music = createSoundEvent("music.background");
            event.getRegistry().register(music);
        }

        private static SoundEvent createSoundEvent(String soundName) {
            final ResourceLocation soundID = new ResourceLocation(Reference.MODID, soundName);
            return new SoundEvent(soundID).setRegistryName(soundID);
        }
    }
}
