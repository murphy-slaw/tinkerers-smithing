<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement -->
<center><img alt="tinkerer's smithing banner" src="https://cdn.modrinth.com/data/RhVpNN5O/images/a6122977eb9e1e1113a567f0e422c16960f8feaa.png" /><br/>
A sentimental and convenient gear crafting rebalance.<br/>
Server-side, but with some extra polish on the client.<br/>
Requires <a href="https://modrinth.com/mod/connector">Connector</a> and <a href="https://modrinth.com/mod/forgified-fabric-api">FFAPI</a> on (neo)forge.<br/>
</center>

---

**Tinkerer's Smithing** is a server-side, data-driven equipment crafting rebalance that:

- Allows repairing every vanilla item that has durability for no level cost (even fishing rods)
- Allows upgrading all tools and armor between material tiers (even chainmail)
- Encourages gradually improving your first set of tools and armor with materials and enchantments
- Makes all the enchanted treasure you find lying around actually useful
- Forgoes nerfing or removing mending or table-grindstone enchanting by instead buffing everything else
- Balances all of its recipe types on the vanilla cost of equipment
- Has full [EMI](https://modrinth.com/mod/emi) support for all added recipe types

---

### Anvil Recipes

|                                                                                                                 |                                                                                                                                                     |
|-----------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| ![Anvil Repair](https://cdn.modrinth.com/data/RhVpNN5O/images/f7ff03dbd4891e48f3fc31fcf8f2d013e802a34e.png)     | **Anvil Repair** (Tweaked)<br/> No level cost, no work penalty applied to the result.<br/> Netherite is rebalanced to use diamonds for repair.<br/> |
| ![Anvil Combine](https://cdn.modrinth.com/data/RhVpNN5O/images/e399470dd0d196aa877e7ff824620a2fed7d347d.png)    | **Anvil Combine** (Tweaked)<br/> No level cost for repairing.<br/> The order of inputs doesn't matter.<br/>                                         |
| ![Anvil De-Working](https://cdn.modrinth.com/data/RhVpNN5O/images/d51392e925b080a728e3fa49aaaf195cc1c55644.png) | **Anvil De-Working**<br/> Reduces the cost multiplier for future anvil crafts.<br/> (Usually called "Prior Work Penalty" or "RepairCost".)<br/>     |
|                                                                                                                 |                                                                                                                                                     |

### Shapeless Recipes

|                                                                                                                  |                                                                                              |
|:----------------------------------------------------------------------------------------------------------------:|----------------------------------------------------------------------------------------------|
| ![Shapeless Repair](https://cdn.modrinth.com/data/RhVpNN5O/images/1473dac04d7165f42200c1d14c4e9dbe146084f3.gif)  | **Shapeless Repair**<br/> Costs the same as crafting.<br/> Only works when unenchanted.<br/> |
| ![Shapeless Upgrade](https://cdn.modrinth.com/data/RhVpNN5O/images/35b23288618b4196a2c163de9d961779707f96f0.png) | **Shapeless Upgrade**<br/> Costs the same as crafting.<br/> Keeps existing damage.<br/>      |
|                                                                                                                  |                                                                                              |

### Smithing Recipes

|                                                                                                                    |                                                                                                                                                                       |
|:------------------------------------------------------------------------------------------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  ![Smithing Upgrade](https://cdn.modrinth.com/data/RhVpNN5O/images/0b3eb56aad09e5ec7bac7017541b8d244e534449.gif)   | **Smithing Upgrade**<br/> Costs the same as crafting.<br/> Use up to 4 less material for a damaged result.<br/>                                                       |
| ![Sacrificial Upgrade](https://cdn.modrinth.com/data/RhVpNN5O/images/c280850dbf642ca662523b5aa3fa0a58b7424566.png) | **Sacrificial Upgrade**<br/> Any type of netherite equipment can be used.<br/> Result damage is based on the sacrifice.<br/> For "gilded" tiers (like netherite) only |
|                                                                                                                    |                                                                                                                                                                       |

### Mechanic Changes

|                                                                                                                 |                                                                                                                                         |
|:---------------------------------------------------------------------------------------------------------------:|-----------------------------------------------------------------------------------------------------------------------------------------|
| ![Broken Equipment](https://cdn.modrinth.com/data/RhVpNN5O/images/5a0f3230a213433f2bf8d53e4833aaea8d6bcac1.png) | **Keepers (Broken Equipment)**<br/> Named or enchanted equipment won't break.<br/> Broken equipment is ineffective until repaired.<br/> |
|                                                                                                                 |                                                                                                                                         |

### Emergent Mechanics

|                                                                                                                                                                                                                                                                        |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ![Treasure Upgrading](https://cdn.modrinth.com/data/RhVpNN5O/images/aed8e9f96c645eb5dc2a608f624ca8742e4545fc.png)                                                                                                                                                      |
| **Useful Treasure**<br/> All vanilla materials are linked to eachother via the upgrade tree.<br/> It doesn't matter if a pair of Protection IV leggings are made of gold, chain, or leather - just upgrade them to the right material, then use them at an anvil.<br/> |
| ![Miscellaneous Repair](https://cdn.modrinth.com/data/RhVpNN5O/images/cbaab6458d13eb934ab237855af8c6a99c063c71.png)                                                                                                                                                    |
| **Miscellaneous Repair**<br/> All previously unrepairable items now have repair recipes - including utility tools.                                                                                                                                                     |
|                                                                                                                                                                                                                                                                        |

## Modpack Configuration

Recipes are driven by defining **Tool Materials**, **Armor Materials**, **Equipment Types**, and **Unit Cost Overrides**.

- **Materials** inherit from vanilla tool/armor materials. They define upgrade paths like Iron->Diamond.
	- By default, all vanilla materials are defined.
- **Types** define "alike" items. Items with matching types can upgrade .
	- By default, the 5 tools (e.g. `c:swords`) and 4 armor slots are defined.
- **Unit Cost** is what an item costs to upgrade to or repair. It's usually guessed from the material and a recipe.
	- By default, vanilla non-tool/armor items are made repairable by overriding this.
	- Netherite is also overridden to have appropriate diamond unit costs.

For data structure examples, check out the [built-in datapack](https://github.com/sisby-folk/tinkerers-smithing/tree/1.19/src/main/resources/data/minecraft) or the mod compatibility packs in [Tinkerer's Quilt](https://github.com/sisby-folk/tinkerers-quilt/tree/1.19_modded/resources/datapacks).

## Afterword

All mods are built on the work of many others.

This mod is included in [Tinkerer's Quilt](https://modrinth.com/modpack/tinkerers-quilt) - our modpack about rediscovering vanilla.

We're open to better ways to implement our mods. If you see something odd and have an idea, let us know!

---

<center>
<b>Tinkerer's:</b> <a href="https://modrinth.com/modpack/tinkerers-quilt">Quilt</a> - <i>Smithing</i> - <a href="https://modrinth.com/mod/origins-minus">Origins</a> - <a href="https://modrinth.com/mod/tinkerers-statures">Statures</a> - <a href="https://modrinth.com/mod/picohud">HUD</a><br/>
<b>Loveletters:</b> <a href="https://modrinth.com/mod/inventory-tabs">Tabs</a> - <a href="https://modrinth.com/mod/antique-atlas-4">Atlas</a> - <a href="https://modrinth.com/mod/portable-crafting">Portable Crafting</a> - <a href="https://modrinth.com/mod/drogstyle">Drogstyle</a><br/>
<b>Others:</b> <a href="https://modrinth.com/mod/switchy">Switchy</a> - <a href="https://modrinth.com/mod/crunchy-crunchy-advancements">Crunchy</a> - <a href="https://modrinth.com/mod/starcaller">Starcaller</a><br/>
</center>
