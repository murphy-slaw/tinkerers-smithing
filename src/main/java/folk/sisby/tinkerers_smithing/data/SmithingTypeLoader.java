package folk.sisby.tinkerers_smithing.data;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import folk.sisby.tinkerers_smithing.TinkerersSmithing;
import folk.sisby.tinkerers_smithing.TinkerersSmithingLoader;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.TagFile;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SmithingTypeLoader extends MultiJsonDataLoader {
	public static final SmithingTypeLoader INSTANCE = new SmithingTypeLoader(new Gson());
	public static final Identifier ID = new Identifier(TinkerersSmithing.ID, "smithing_type_loader");
	public static final TagGroupLoader<Item> ITEM_TAG_LOADER = new TagGroupLoader<>(Registry.ITEM::getOrEmpty, "tags/items");
	public static final String AVOIDANCE_PREFIX = "tinkerers_smithing_types/";

	public SmithingTypeLoader(Gson gson) {
		super(gson, "smithing_types");
	}

	public static void addToTag(Map<Identifier, Collection<Item>> tags, String path, Item item) {
		Identifier id = new Identifier(path); // minecraft for slot stuff
		HashSet<Item> mutable = new HashSet<>(tags.computeIfAbsent(id, k -> new HashSet<>()));
		mutable.add(item);
		tags.put(id, ImmutableList.copyOf(mutable));
	}

	@Override
	public String getName() {
		return ID.toString();
	}

	@Override
	protected void apply(Map<Identifier, Collection<Pair<JsonElement, String>>> prepared, ResourceManager manager, Profiler profiler) {
		TinkerersSmithing.LOGGER.info("[Tinkerer's Smithing] Loading Types!");
		TinkerersSmithingLoader.INSTANCE.SMITHING_TYPES.clear();
		Map<Identifier, List<TagGroupLoader.TrackedEntry>> typeTags = new HashMap<>();
		prepared.forEach((id, jsons) -> {
			Identifier collisionAvoidingID = new Identifier(id.getNamespace(), AVOIDANCE_PREFIX + id.getPath());
			jsons.forEach(jsonEntry -> {
				List<TagGroupLoader.TrackedEntry> list = typeTags.computeIfAbsent(collisionAvoidingID, k -> new ArrayList<>());
				TagFile tagFile = TagFile.CODEC.parse(new Dynamic<>(JsonOps.INSTANCE, jsonEntry.getLeft())).getOrThrow(false, TinkerersSmithing.LOGGER::error);
				if (tagFile.replace()) {
					list.clear();
				}
				tagFile.entries().forEach(entry -> list.add(new TagGroupLoader.TrackedEntry(entry, jsonEntry.getRight())));
			});
		});

		Map<Identifier, List<TagGroupLoader.TrackedEntry>> itemTags = ITEM_TAG_LOADER.loadTags(manager);
		Map<Identifier, List<TagGroupLoader.TrackedEntry>> allTags = new HashMap<>();
		allTags.putAll(itemTags);
		allTags.putAll(typeTags);
		Map<Identifier, Collection<Item>> tags = ITEM_TAG_LOADER.buildGroup(allTags);
		tags.entrySet().removeIf(e -> !typeTags.containsKey(e.getKey()));
		// Strip collision avoiding ID
		tags = tags.entrySet().stream().collect(Collectors.toMap(e -> new Identifier(e.getKey().getNamespace(), StringUtils.removeStart(e.getKey().getPath(), AVOIDANCE_PREFIX)), Map.Entry::getValue));
		// Manually jam in stuff by equipment slot, false positives should wash out by having no material.
		for (Item item : Registry.ITEM) {
			if (item instanceof ArmorItem ai) {
				switch (ai.getSlotType()) {
					case FEET -> addToTag(tags, "boots", item);
					case LEGS -> addToTag(tags, "leggings", item);
					case CHEST -> addToTag(tags, "chestplate", item);
					case HEAD -> addToTag(tags, "helmet", item);
				}
			}
		}
		TinkerersSmithingLoader.INSTANCE.SMITHING_TYPES.putAll(tags);
		TinkerersSmithing.LOGGER.info("[Tinkerer's Smithing] Reloaded smithing types");
	}
}
