package me.Fupery.ArtMap.api;

import me.Fupery.ArtMap.api.Painting.IArtistHandler;
import me.Fupery.ArtMap.api.Utils.VersionHandler;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.Reader;

public interface IArtMap extends Plugin {

    public IArtistHandler getArtistHandler();

    public VersionHandler getBukkitVersion();

    public Reader getTextResourceFile(String fileName);

    public boolean writeResource(String resourcePath, File destination);

}

