package fr.frozerytb.draconiummod.init;

import fr.frozerytb.draconiummod.capabilities.ExtendedPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class CapabilitiesInit {
    public static void preInit(FMLPreInitializationEvent event) {
        ExtendedPlayerData.register();
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer && !event.getObject().world.isRemote) {
            event.addCapability(ExtendedPlayerData.KEY, new ExtendedPlayerData());
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.END) {
            ExtendedPlayerData.get(event.player).tick();
        }
    }
}
