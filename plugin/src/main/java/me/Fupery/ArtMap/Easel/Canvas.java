package me.Fupery.ArtMap.Easel;

import me.Fupery.ArtMap.ArtMap;
import me.Fupery.ArtMap.Exception.ArtMapException;
import me.Fupery.ArtMap.IO.Database.Map;
import me.Fupery.ArtMap.IO.MapArt;
import me.Fupery.ArtMap.Recipe.ArtItem;
import me.Fupery.ArtMap.Recipe.ArtItem.InProgressArtworkItem;
import me.Fupery.ArtMap.Utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a painting canvas. Extends ItemStack so that information can be
 * retrieved when it is pulled off the easel.
 */
public class Canvas {

    protected int mapId;

    public Canvas(Map map) {
        this(map.getMapId());
    }

    protected Canvas(int mapId) {
        this.mapId = mapId;
    }

    public static Canvas getCanvas(ItemStack item) throws SQLException, ArtMapException {
        if (item == null || item.getType() != Material.FILLED_MAP)
            return null;

        MapMeta meta = (MapMeta) item.getItemMeta();
        int mapId = meta.getMapView().getId();
        if (item.getItemMeta() != null && item.getItemMeta().getLore() != null
                && item.getItemMeta().getLore().contains(ArtItem.COPY_KEY)) {
            return new CanvasCopy(item);
        }
        return new Canvas(mapId);
    }

    public ItemStack getEaselItem() {
        return new InProgressArtworkItem(this.mapId).toItemStack();
    }

    public int getMapId() {
        return this.mapId;
    }

    public static class CanvasCopy extends Canvas {

        private MapArt original;

        public CanvasCopy(Map map, MapArt orginal) {
            super(map);
            this.original = orginal;
        }

        public CanvasCopy(ItemStack map) throws SQLException, ArtMapException {
            super(ItemUtils.getMapID(map));
            ItemMeta meta = map.getItemMeta();
            List<String> lore = meta.getLore();
            if (lore == null || !lore.contains(ArtItem.COPY_KEY)) {
                throw new ArtMapException("The Copied canvas is missing the copy key!");
            }
            String originalName = lore.get(lore.indexOf(ArtItem.COPY_KEY) + 1);
            this.original = ArtMap.instance().getArtDatabase().getArtwork(originalName);
        }

		/*
		@Override
		public ItemStack getDroppedItem() {
			return this.original.getMapItem();
		}
		*/

        @Override
        public ItemStack getEaselItem() {
            ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
            MapMeta meta = (MapMeta) mapItem.getItemMeta();
            meta.setMapView(ArtMap.getMap(this.mapId));
            // Set copy lore
            meta.setLore(Arrays.asList(ArtItem.COPY_KEY, this.original.getTitle()));
            mapItem.setItemMeta(meta);
            return mapItem;
        }

        /**
         * @return The original map id.
         */
        public int getOriginalId() {
            return this.original.getMapId();
        }
    }
}
