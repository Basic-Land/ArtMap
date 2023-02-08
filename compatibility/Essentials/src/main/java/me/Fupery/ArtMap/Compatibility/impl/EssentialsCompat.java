package me.Fupery.ArtMap.Compatibility.impl;

import me.Fupery.ArtMap.api.Compatability.EventListener;
import me.Fupery.ArtMap.api.IArtMap;
import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.StateChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.logging.Level;

public class EssentialsCompat implements EventListener {
    private boolean loaded = false;
    private IArtMap artmap;

    public EssentialsCompat(IArtMap artmap) {
        this.artmap = artmap;
        this.loaded = true;
        //register listeners
        artmap.getServer().getPluginManager().registerEvents(this, artmap);
    }

    @EventHandler
    public void onAFKEvent(AfkStatusChangeEvent event) {
        try {
            Player player = event.getAffected().getBase();
            if (artmap.getArtistHandler().containsPlayer(player) && player.hasPermission("artmap.ignore.afk")) {
                event.setCancelled(true);
            }
        } catch (Exception e) {
            artmap.getLogger().log(Level.SEVERE, "Error interteracting with MarriageMaster!", e);
        }
    }

    @Override
    public void unregister() {
        StateChangeEvent.getHandlerList().unregister(this);
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }
}
