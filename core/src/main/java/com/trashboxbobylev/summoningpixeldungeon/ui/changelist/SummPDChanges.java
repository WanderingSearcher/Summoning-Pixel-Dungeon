/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * Summoning Pixel Dungeon
 * Copyright (C) 2019-2020 TrashboxBobylev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.trashboxbobylev.summoningpixeldungeon.ui.changelist;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.actors.hero.HeroClass;
import com.trashboxbobylev.summoningpixeldungeon.items.Ankh;
import com.trashboxbobylev.summoningpixeldungeon.items.armor.ConjurerArmor;
import com.trashboxbobylev.summoningpixeldungeon.items.food.Blandfruit;
import com.trashboxbobylev.summoningpixeldungeon.items.potions.PotionOfHealing;
import com.trashboxbobylev.summoningpixeldungeon.items.spells.ArcaneCatalyst;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.scenes.ChangesScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.HeroSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.summoningpixeldungeon.ui.Icons;
import com.trashboxbobylev.summoningpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class SummPDChanges {
	
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo( "Summoning PD", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		changeInfos.add(changes);
		add_Beta_Changes(changeInfos);
		add_General_Changes(changeInfos);
		add_Items_Changes(changeInfos);
		add_Minor_Changes(changeInfos);
	}

    public static void add_Beta_Changes( ArrayList<ChangeInfo> changeInfos ){
        ChangeInfo changes = new ChangeInfo("Beta Information", false, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes.addButton( new ChangeButton(Icons.get(Icons.CONJURER), "Closed Beta",
                "_-_ Closed beta started October 18th, 2019\n" +
                        "_-_ 42 days after beginning of development\n" +
                        "\n" +
                        "This is pre-release version of Summoning PD. I except to test them for 5-8 days, if nothing major will happen, that version will be released as final."));

        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-2",
                "Fixed:\n" +
                        "_-_ Traps was not correctly revealed, non-hidden traps was crashing the game.\n" +
                        "_-_ LOVE Holder was able to have negative strength.\n" +
                        "_-_ Conjurer's icon was bit out of center\n"+
                        "_-_ Changes buttons was not enough long.\n"+
                        "_-_ Ring of Attunement was displaying incorrect numbers.\n\n"+
                        "Changed:\n" +
                        "_-_ Buffed melee damage for staffs, but lowered recharge rate by 50%."));

        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-3",
                "Fixed:\n" +
                        "_-_ Rendering of cleared Tengu floor was wrong\n" +
                        "_-_ Conjurer was starting with 10 strength\n" +
                        "_-_ Sometimes the new badges were crashing the game\n"+
                        "_-_ Gaster Blaster had 1 attunement requirement, but consumed the 2 attunement on summoning\n"+
                        "_-_ In some cases staff's descriptions were crashing the game\n"+
                        "_-_ Sewers had test tier drop rate list\n\n"+
                        "Changed:\n" +
                        "_-_ New sprites for Conjurer's avatar and froggit\n"+
                        "_-_ Staff's default action were changed to SUMMON"));
    }

    public static void add_General_Changes(ArrayList<ChangeInfo> changeInfos ){
	    ChangeInfo changes = ChangesScene.createChangeInfo(changeInfos, "General", false, Window.SHPX_COLOR);
        changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.CONJURER, 6), "New Class!",
                "Asriel Dreemur joins the dungeon crawling!\n\n" +
                        "The Conjurer - new class, that are focused on new type of weapons - summon weapons. Thanks to his great soul power, here are able to control more allies and support them by his unique equipment.\n\n"+
                        "Unfortunately, Conjurer's body is composed from attunement, so he have lowered physical stats compared to other classes."));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.GREY_RAT_STAFF, null), "Summon Weapons!",
                "The new type of weapons have been added into dungeon.\n\n" +
                        "_-_ Summon weapons can be used to summon the unique allies, which type depends on type's staff.\n\n" +
                        "_-_ As all weapons, summon weapons are splitted into 5 tiers, with more unique and powerful minions as you progress.\n\n" +
                        "_-_ Summon weapons can summon unlimited smount of creatures, but you can control only few."));

        changes.addButton( new ChangeButton(new Image(Assets.BUFFS_LARGE, 112, 32, 16, 16), "Attunement",
                "_Attunement_ - the new hero stat which defines max amount of creatures, that you can control at once.\n\n"+
                                        "_-_ Any minion require some amount of attunement (very likely, 1) to be summoned.\n\n"+
                                        "_-_ More powerful minions require 2 or 3 attunement.\n\n"+
                                        "_-_ Conjurer have 2 max attunement on start and increases his attunement by 1 every 6 levels.\n\n"+
                                        "_-_ Other classes can increase their attunement with elixir of attunement."));

    }

    public static void add_Items_Changes(ArrayList<ChangeInfo> changeInfos){
        ChangeInfo changes = ChangesScene.createChangeInfo(changeInfos, "General", false, 0x5ba5ff);
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_AGATE, null), "Ring changes",
                        "_-_ Ring of Tenacity was removed. Their effect are too unnoticable and hard to benefit from them.\n\n" +
                        "_-_ Ring of Elements and Ring of Tenacity were merged into Ring of Resistance, providing the static damage reduction from most sources.\n\n" +
                        "_-_ Added the Ring of Attunement, that increases max attunement and minion's damage."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_BERKANAN, null), "Scroll changes",
                "_-_ Scroll of Terror was removed. It's was useful, but somewhat rare to be decent item.\n\n" +
                        "_-_ Added the Scroll of Attunement, that increases speed and damage reduction for all minions in sight.\n\n" +
                        "_-_ Replaced the Stone of Affection with Stone of Targeting, that can be used to setup positions for minions.\n\n"+
                        "_-_ Added the Scroll of Soul Energy, that summons powerful invincible minion at cost of all attunement."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_LOVE1, null), "New artifact!",
                "Added the new artifact: LOVE Holder.\n\n" +
                        "_-_ It's unique artifact for Conjurer: you can't get it by other means.\n\n" +
                        "_-_ LOVE Holder allows hero to collect a soul energy, that can be used for healing minions.\n\n"+
                        "_-_ As you heal a minions, artifact will grow in power and obtain the new abilities."));
        changes.addButton( new ChangeButton(new ConjurerArmor(),
                "The new armor item, exclusive for Conjurer.\n\n" +
                        "_-_ It's can't be unequipped by any means, but you can merge any identified armor with Robe to upgrade the robe.\n\n"+
                        "_-_ Deltarune Robe have several skills, two of them are available after getting the subclass.\n\n"+
                        "_-_ First skill is paralysing all enemies in sight for short time, second summons chaos saber, third cast soul spell, which effect depends on choosed subclass.\n\n"+
                        "_-_ With every upgrade it will increase newly summoned minions hp by 10% per level."));

    }

    public static void add_Minor_Changes(ArrayList<ChangeInfo> changeInfos){
        ChangeInfo changes = ChangesScene.createChangeInfo(changeInfos, "Other", false, 0x651f66);
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.GOLD, null), "Gold balance changes",
                "_-_ Because of summon weapons, gold appear a slighty rarer, but in more quantity.\n\n"+
                                        "_-_ The shops have been extended to hold more valuable items.\n\n"+
                                        "_-_ You can farm gold in Prison by killing the thieves."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.POTION_CRIMSON, null), "Healing item changes",
                "Potion of Healing and Elixir of Honeyed Healing now create the clouds of healing gases on shattering.\n\n" +
                        "When PoH cloud heal anything, EoHH are more concentrated and heal only allies."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SWORD, new ItemSprite.Glowing()), "Unstable enchantment",
                "Now have rainbow shining as glowing effect."));
        changes.addButton( new ChangeButton(new Image(Assets.NECRO, 0, 0, 16, 16), "Shattered 0.7.5",
                "_-_ Added the necromancer from Shattered 0.7.5. Their stats are buffed compared to Shattered's.\n\n"+
                                        "_-_ Added new Tengu from Shattered 0.7.5, unchanged.\n\n"+
                                        "_-_ New camera also added."));
    }

	
	public static void add_v0_1_1_Changes( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo("v0.1.1", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released August 15th, 2014\n" +
						"_-_ 10 days after Shattered v0.1.0\n" +
						"\n" +
						"Dev commentary will be added here in the future."));
		
		changes.addButton( new ChangeButton(new Blandfruit(),
				"Players who chance upon gardens or who get lucky while trampling grass may come across a new plant: the _Blandfruit._\n\n" +
						"As the name implies, the fruit from this plant is pretty unexceptional, and will barely do anything for you on its own. Perhaps there is some way to prepare the fruit with another ingredient..."));
		
		changes.addButton( new ChangeButton(new ItemSprite(new Ankh()), "Revival Item Changes",
				"When the Dew Vial was initially added to Pixel Dungeon, its essentially free revive made ankhs pretty useless by comparison. " +
						"To fix this, both items have been adjusted to combine to create a more powerful revive.\n\n" +
						"Dew Vial nerfed:\n" +
						"_-_ Still grants a full heal at full charge, but grants less healing at partial charge.\n" +
						"_-_ No longer revives the player if they die.\n\n" +
						"Ankh buffed:\n" +
						"_-_ Can now be blessed with a full dew vial, to gain the vial's old revive effect."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_BERKANAN, null), "Misc Item Changes",
				"Sungrass buffed:\n" +
						"_-_ Heal scaling now scales with max hp.\n\n" +
						"Scroll of Psionic Blast rebalanced:\n" +
						"_-_ Now deals less self damage, and damage is more consistent.\n" +
						"_-_ Duration of self stun/blind effect increased.\n\n" +
						"Scroll of lullaby reworked:\n" +
						"_-_ No longer instantly sleeps enemies, now afflicts them with drowsy, which turns into magical sleep.\n" +
						"_-_ Magically slept enemies will only wake up when attacked.\n" +
						"_-_ Hero is also affected, and will be healed by magical sleep."));
	}
	
	public static void add_v0_1_0_Changes( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo("v0.1.0", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);
		
		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Developer Commentary",
				"_-_ Released August 5th, 2014\n" +
				"_-_ 69 days after Pixel Dungeon v1.7.1\n" +
				"_-_ 9 days after v1.7.1 source release\n" +
				"\n" +
				"Dev commentary will be added here in the future."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SEED_EARTHROOT, null), "Seed Changes",
				"_-_ Blindweed buffed, now cripples as well as blinds.\n\n" +
				"_-_ Sungrass nerfed, heal scales up over time, total heal reduced by combat.\n\n" +
				"_-_ Earthroot nerfed, damage absorb down to 50% from 100%, total shield unchanged.\n\n" +
				"_-_ Icecap buffed, freeze effect is now much stronger in water."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.POTION_SILVER, null), "Potion Changes",
				"_-_ Potion of Purity buffed, immunity duration increased to 10 turns from 5, clear effect radius increased.\n\n" +
				"_-_ Potion of Frost buffed, freeze effect is now much stronger in water."));
		
		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_BERKANAN, null), "Scroll Changes",
				"_-_ Scroll of Psionic blast reworked, now rarer and much stronger, but deals damage to the hero.\n\n" +
				"_-_ Scroll of Challenge renamed to Scroll of Rage, now amoks nearby enemies."));
		
	}
	
}