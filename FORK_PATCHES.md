# Fork patches

Changes made in this fork on top of the upstream 1.12.2-0.3.1 source, and why.
Upstream is TheComputerizer's LTS fork of Gilded Games' Aether II (LGPL-3.0, see LICENSE.txt).

## Orbis `mutated_tree` identifier warning

**File:** `src/main/resources/assets/aether/orbis/aetherii/trees/skyroot/mutated_tree.metadata`

**Symptom:** every startup logged:

```
[OrbisLib]: WARNING: A mod data file (trees\skyroot\mutated_tree - <random>:31dae1da-...:Player155)
has a different identifier assigned after loading (Old identifier: 02afc608-...:8e954611-...:OscarPayn) ...
```

**Cause:** Orbis groups data files into projects. Every other tree blueprint in
`orbis/aetherii/` belongs to project `31dae1da-5d8a-4a6c-833d-0296367f5326` (creator
`Player155`, the default Forge dev-environment username). `mutated_tree.metadata` still
pointed at project `8e954611-5766-484a-8ca1-a17ad4861184` (creator `OscarPayn`), so it was
likely copied from another developer's workspace and never re-saved. At load, Orbis moved
it into the containing project, generated a new random data ID (different on every launch),
and logged the mismatch. It was harmless, but it showed up as an `ERROR` line on every start.

**Fix:** changed only the `projectIdentifier` in the metadata JSON to the containing
project (`31dae1da-...` / `Player155`), keeping the file's original `dataId`
(`02afc608-...`). This matches the files that load without warning. The `.blueprint`
file (the structure itself) is unchanged. Aether loads this file by path
(`BlueprintsAether.java`: `loadData("trees/skyroot/mutated_tree")`), not by ID, so
nothing else refers to the old identifiers.

**Why not just suppress it:** the warning comes from compiled Orbis code
(`OrbisProject.loadData`), and the only other options were a log filter or bytecode
editing. Fixing the data removes the cause instead of hiding it.

**To revert:** set `projectId` back to `8e954611-5766-484a-8ca1-a17ad4861184` and
`originalCreator` back to `OscarPayn`.

## Analytics null crash on connect and disconnect

**File:** `src/main/java/com/gildedgames/aether/client/events/listeners/network/ClientNetworkStateListener.java`

`AetherCore.ANALYTICS` can be null under Cleanroom, because client init can fail before
analytics is created. The join and leave listeners then threw a NullPointerException on
every connect and disconnect. That broke the FML handshake, blocked Orbis's disconnect
cleanup, and filled the client log. Both listeners now return early when `ANALYTICS` is null.
This handles the symptom only; the underlying init failure under Cleanroom is still open.

## Certificate fingerprint removed

**File:** `src/main/java/com/gildedgames/aether/common/AetherCore.java`

Removed `certificateFingerprint` from `@Mod`. Rebuilt jars can't carry Gilded Games'
signature, so FML logged "expecting signature" on every launch.

Known remaining: Orbis Lib (bundled, compiled only) has its own `@Mod` with the same
fingerprint, so one "orbis-lib is expecting signature" line still appears. It's cosmetic,
and removing it would mean editing Orbis bytecode.

## Tile entity registration warning

**File:** `src/main/java/com/gildedgames/aether/common/init/TileEntitiesAether.java`

Forge's `GameRegistry.registerTileEntity(Class, String)` logged "Potentially Dangerous
alternative prefix `minecraft`" for all 14 tile entities (IDs like `aether.altar`
resolve to `minecraft:aether.altar`). These now call `TileEntity.register(String, Class)`
directly: the same call Forge makes, minus the prefix check. It is made public by Forge's
and Cleanroom's own access transformers (`forge_at.cfg`, `func_190560_a`).

**Registry IDs are deliberately unchanged.** Renaming them to `aether:altar` would break
30 bundled structure and blueprint files (skyroot trees, outposts, watchtowers, houses)
and existing saves. Those files store the old IDs, and none of their load paths apply
data fixers. Tile entity registries also can't hold aliases, because they are backed by
a `HashBiMap`.

## Build setup

- Orbis Lib 0.2.0 is not published anywhere. `orbis-lib-0.2.0.jar` was extracted,
  byte-identical, from the working 0.3.1 release jar and placed in the `flatDir` repo
  (`build/libs/`). It's still pulled in through `fg.deobf(...)`, because the classes use
  SRG names and have to be remapped at compile time and reobfuscated in the release jar.
- A JDK 8 (not just a JRE) is required. ForgeGradle needs `javac` and `tools.jar`.
