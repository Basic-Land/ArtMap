package me.Fupery.ArtMap.Compatibility.impl;

import me.Fupery.ArtMap.api.Compatability.RegionHandler;
import me.Fupery.ArtMap.api.Easel.ClickType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.lists.Flags;

import java.util.Optional;

public class BentoBoxCompat implements RegionHandler {

    private final boolean loaded;
    private BentoBox api = null;

    public BentoBoxCompat() {
        api = BentoBox.getInstance();
        loaded = Bukkit.getPluginManager().isPluginEnabled("BentoBox");
    }

    @Override
    public boolean checkBuildAllowed(Player player, Location location) {
        Optional<Island> island = api.getIslands().getIslandAt(location);
        if (island.isPresent()) {
            return island.get().isAllowed(User.getInstance(player), Flags.BREAK_BLOCKS);
        }
        return true;
    }

    @Override
    public boolean checkInteractAllowed(Player player, Entity entity, ClickType click) {
        Optional<Island> island = api.getIslands().getIslandAt(entity.getLocation());
        if (island.isPresent()) {
            return island.get().isAllowed(User.getInstance(player), Flags.ARMOR_STAND);
        }
        return true;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }
}
