package me.Fupery.ArtMap.IO.Protocol.Channel;

import io.netty.channel.Channel;
import me.Fupery.ArtMap.ArtMap;
import org.bukkit.Bukkit;

import java.util.UUID;

class CacheablePlayerChannel {

    private final UUID player;
    private final long expiryTime;
    private final Channel channel;

    CacheablePlayerChannel(UUID player, long timeToLive) throws ReflectiveOperationException {
        this.player = player;
        expiryTime = (timeToLive == -1) ? -1 : System.currentTimeMillis() + timeToLive;
        channel = ArtMap.instance().getReflection().getPlayerChannel(Bukkit.getPlayer(player));
    }

    public void sendPacket(Object packet) {
        channel.pipeline().writeAndFlush(packet);
    }

    boolean isExpired() {
        return channel == null || !channel.isOpen() || (expiryTime != -1 && expiryTime >= System.currentTimeMillis());
    }

    Channel getChannel() {
        return channel;
    }

    public UUID getPlayer() {
        return player;
    }
}
