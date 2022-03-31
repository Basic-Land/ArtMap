package me.Fupery.ArtMap.api.Painting;

import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;

public interface IArtistHandler {

    public boolean containsPlayer(Player player);

    public boolean containsPlayer(UUID player);

    public void removePlayer(final Player player) throws SQLException, IOException;

    public IArtSession getCurrentSession(Player player);

    public IArtSession getCurrentSession(UUID player);

    public Set<UUID> getArtists();

    public void stop();
}