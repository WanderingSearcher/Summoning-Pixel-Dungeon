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
import com.trashboxbobylev.summoningpixeldungeon.items.Gold;
import com.trashboxbobylev.summoningpixeldungeon.items.armor.ConjurerArmor;
import com.trashboxbobylev.summoningpixeldungeon.items.food.Blandfruit;
import com.trashboxbobylev.summoningpixeldungeon.items.potions.PotionOfHealing;
import com.trashboxbobylev.summoningpixeldungeon.items.quest.CleanWater;
import com.trashboxbobylev.summoningpixeldungeon.items.spells.ArcaneCatalyst;
import com.trashboxbobylev.summoningpixeldungeon.items.wands.WandOfStench;
import com.trashboxbobylev.summoningpixeldungeon.items.weapon.enchantments.Vampiric;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.scenes.ChangesScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.CharSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.HeroSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSprite;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.summoningpixeldungeon.ui.Icons;
import com.trashboxbobylev.summoningpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class SummPDChanges {
	
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		
		//ChangeInfo changes = new ChangeInfo( "1.1", true, "");
		//changes.hardlight( Window.TITLE_COLOR);
		//changeInfos.add(changes);
        add_1_1_4_Changes(changeInfos);
        add_1_1_3_Changes(changeInfos);
        add_1_1_2_Changes(changeInfos);
        add_1_1_1_Changes(changeInfos);
        add_1_1_Changes(changeInfos);
        add_1_0_1_Changes(changeInfos);
        ChangeInfo changes = new ChangeInfo( "1.0", true, "");
        changes.hardlight( Window.TITLE_COLOR);
        changeInfos.add(changes);
		add_General_Changes(changeInfos);
		add_Items_Changes(changeInfos);
		add_Mobs_Changes(changeInfos);
		add_Minor_Changes(changeInfos);
	}

//    public static void add_Beta_Changes( ArrayList<ChangeInfo> changeInfos ){
//        ChangeInfo changes = new ChangeInfo("Beta Information", false, "");
//        changes.hardlight(Window.TITLE_COLOR);
//        changeInfos.add(changes);
//
//        changes.addButton( new ChangeButton(Icons.get(Icons.CONJURER), "Closed Beta",
//                "_-_ Closed beta started October 18th, 2019\n" +
//                        "_-_ 42 days after beginning of development\n" +
//                        "\n" +
//                        "This is pre-release version of Summoning PD. I except to test them for 5-8 days, if nothing major will happen, that version will be released as final."));
//
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-2",
//                "Fixed:\n" +
//                        "_-_ Traps was not correctly revealed, non-hidden traps was crashing the game.\n" +
//                        "_-_ LOVE Holder was able to have negative strength.\n" +
//                        "_-_ Conjurer's icon was bit out of center\n"+
//                        "_-_ Changes buttons was not enough long.\n"+
//                        "_-_ Ring of Attunement was displaying incorrect numbers.\n\n"+
//                        "Changed:\n" +
//                        "_-_ Buffed melee damage for staffs, but lowered recharge rate by 50%."));
//
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-3",
//                "Fixed:\n" +
//                        "_-_ Rendering of cleared Tengu floor was wrong\n" +
//                        "_-_ Conjurer was starting with 10 strength\n" +
//                        "_-_ Sometimes the new badges were crashing the game\n"+
//                        "_-_ Gaster Blaster had 1 attunement requirement, but consumed the 2 attunement on summoning\n"+
//                        "_-_ In some cases staff's descriptions were crashing the game\n"+
//                        "_-_ Sewers had test tier drop rate list\n\n"+
//                        "Changed:\n" +
//                        "_-_ New sprites for Conjurer's avatar and froggit\n"+
//                        "_-_ Staff's default action were changed to SUMMON"));
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-4",
//                "Fixed:\n" +
//                        "_-_ Player was able to use staffs with negative charges\n" +
//                        "_-_ Soul Reaver's abilities were reducing minion's life to 1\n" +
//                        "_-_ Chicken staff was displaying the bonus HP from robe\n"+
//                        "_-_ Froggit were having the wrong frame animations\n"+
//                        "_-_ Love Holder doesn't crash on reading +10's description\n"+
//                        "_-_ Minions was not able to wake up after magical sleep\n\n"+
//                        "Changed:\n" +
//                        "_-_ Minions now have the stats description"));
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-5",
//                "Fixed:\n" +
//                        "_-_ Froggit was using the death animation for attacking and vice versa\n" +
//                        "_-_ Heroes were not able to open heaps standing on them\n" +
//                        "_-_ All heroes was having 9 strength\n\n"+
//                        "Changed:\n" +
//                "_-_ Changes slighty the weight of items, staffs should appear more often.\n\n"+
//                "_-_ Reworked the Vial of Perfume: now attracts enemies from whole depth, but attacking them dispells the perfume affection.\n"+
//                "_-_ Changed the Containing: chance to collect the mob depends on their HP and EXP, if not successful, the enemy will get damage, equal to 50% current HP.\n"+
//                "_-_ Rebalanced most broken or underused staffs.\n"+
//                "_-_ Removed the unstable from Worn Shortsword."));
//
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-6",
//                "Fixed:\n" +
//                        "_-_ Staffs were not transmutable\n" +
//                        "_-_ Message from stone of aggression was !!!NO TEXT FOUND!!!\n" +
//                        "_-_ Conjurer's amor had a wrong prompt on imbuing\n\n"+
//                        "Changed:\n" +
//                        "_-_ Staffs now decrease strength requirement with every upgrade.\n"+
//                        "_-_ Chaos Saber now collect a lot of soul, if hero is Soul Reaver.\n"+
//                        "_-_ Necromancer now affect their skeleton with Empowered.\n"+
//                        "_-_ Reworked the resistance for monsters: damage now get sqrted rather that halfed.\n"+
//                        "_-_ Buffed the Ring of Attunement.\n"+
//                        "_-_ Scroll of Attunement now weakens the enemies.\n"+
//                        "_-_ Minion show their base DR alongside with additional DR in description.\n"+
//                        "_-_ Necromancers now drop the random staff with 1/8 chance.\n"+
//                        "_-_ Removed nerfs from Cleaver, but adjusted the chance to behead."));
//
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-7",
//                "Changed:\n"+
//                                        "_-_ Throwing knifes and kunai doesn't consume a durability with right uses\n"+
//                                        "_-_ Added the slingshot with removal of throwing stones as standalone weapon\n"+
//                                        "_-_ Fixed weird non-opaque spots in graphics\n"+
//                                        "_-_ Reworked the runic blade\n"+
//                                        "_-_ Changed the visuals of fireball and main menu buttons\n"+
//                                        "_-_ Buffed the rattle snake's evasion and damage\n"+
//                                        "_-_ Massively adjusted charge rate of most staves; froggit staff will stay with old stats, most of staves recharges in 400 turns, tank staves recharge even longer\n" +
//                                        "_-_ Brand new icon for the mod!"
//        ));
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "BETA-7.1",
//                "Fixed:\n" +
//                        "_-_ Runic Blade's emitter were placed in wrong place and didn't updated\n" +
//                        "_-_ On shooting, Runic Blade was able to target something different from target\n\n" +
//                        "Changed:\n" +
//                        "_-_ New menu button appearance"));
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "RC-1",
//                        "Changed:\n" +
//                        "_-_ Mod's icon are more contrast now\n" +
//                                "_-_ Adjusted Soul Reaver's offensive ability: now it consumes 2x more soul\n" +
//                                "_-_ Added Slimes and Final Froggits into Demon Halls\n\n" +
//                                "We are almost finished. If nothing major will happen with new mobs, I will publish the release."));
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "RC-2",
//                "Changed:\n" +
//                        "_-_ Shops have been reworked again\n" +
//                        "_-_ You need to kill Final Froggits to proceed to next floor\n" +
//                        "_-_ Slimes now occupy cell rather that levitating\n" +
//                        "_-_ Changed the about scene\n" +
//                        "_-_ Froggit staff are included into quickslots\n" +
//                        "_-_ Added Gold Token, just for selling\n\n"+
//                        "We are almost finished. If nothing major will happen with shop, I will publish the release."));
//        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), "RC-3",
//                "Fixed:\n" +
//                        "_-_ Shops were selling the cursed items and unidentified wands and rings\n" +
//                        "_-_ You weren't able to descend, if you killed all final froggits\n" +
//                        "_-_ Minions are weren't able to awake (not sure about this one)\n" +
//                        "Changed:\n" +
//                        "_-_ Rankings score now have completely new formula\n" +
//                        "_-_ Added the article about allies into Adventurer Guide\n"+
//                        "We are almost finished. If nothing major will happen with this damn sleepy minions, I will publish the release."));
//    }

    public static void add_1_1_4_Changes(ArrayList<ChangeInfo> changeInfos) {

        ChangeInfo changes = new ChangeInfo("1.1.4", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = ChangesScene.createChangeInfo(changeInfos, "Dev", false, Window.TITLE_COLOR);
        changes.addButton(new ChangeButton(Icons.get(Icons.TRASHBOXBOBYLEV), "Developer Information",
                "_-_ Released June 2th, 2020\n" +
                        "_-_ 73 days after 1.1.3\n\n" +
                        "YEEEEEEEEEE"));
        changes = ChangesScene.createChangeInfo(changeInfos, "Changes", false, Window.SHPX_COLOR);
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RATION, null), "Reworked hunger",
                        "The hunger have been reworked.\n\n" +
                                "Instead of depleting at fixed pace, now it can be spent by different actions.\n" +
                                "Staying still spends a far less hunger than running or fighting monsters.\n\n" +
                                "Some food, like cooked meat, might give you regeneration effect. As result, natural regeneration rate have been significantly slowed.\n\n" +
                                "There will be more food types in future, but I want some feedback over mechanic in general."
            ));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_DISINTEGRATION, null), "New wand",
                "Replaced Wand of Disintegration with Wand of Shadow Beams, that bounces off the cells, increasing vulnerability of hit targets."));
        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "Fixed:\n" +
                        "_-_ Soul Wielder's buff crashing, when reloaded from save file.\n\n" +
                        "_-_ Soul refound from augmenting minions.\n\n"+
                        "_-_ Wand of Stars not saving shot from detonation.\n\n" +
                        "_-_ Arcane Bomb destroying level's in/out tiles."));
    }
public static void add_1_1_3_Changes(ArrayList<ChangeInfo> changeInfos) {

    ChangeInfo changes = new ChangeInfo("1.1.3", true, "");
    changes.hardlight(Window.TITLE_COLOR);
    changeInfos.add(changes);

    changes = new ChangeInfo("v1.1.3c", false, null);
    changes.hardlight( Window.TITLE_COLOR );
    changeInfos.add(changes);

    changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
            "Removed dedicated weapon and armor from shops, as it was too unstable. Instead, shops have additional Scroll of Identity and missile weapon stack."));

    changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_MAGIC_MISSILE, null), "Wand Changes",
                    "_-_ Wand of Frost is reworked: nerfed damage from 5-7 (+2/+3) to 2-5 (+1/+2), but added utility in form of increasing freezing chance and Frostburn debuff.\n\n" +
                    "_-_ Wand of Living Earth is nerfed: decreased guardian's HP to 20 (+8), decreased his DR to 1-4 (+0/+0.25).\n\n" +
                    "_-_ Wand of Blast Wave is buffed: it doesn't directly damage hero at all."));

    changes = new ChangeInfo("v1.1.3b", false, null);
    changes.hardlight( Window.TITLE_COLOR );
    changeInfos.add(changes);

    changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
            "Removed gold condition for shop weapons."));

    changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_MAGIC_MISSILE, null), "Wand Changes",
            "_-_ Wand of Magic Missile is buffed from 2-8 (+1/+2) damage to 4-6 (+2/+2) damage.\n\n" +
                    "_-_ Wand of Lightning is changed: nerfed damage from 5-10 (+1/+5) to 2-6 (+2/+2), but zaps into water will do double damage (4-12 (+4/+4)).\n\n" +
                    "_-_ Wand of Frost is changed: nerfed damage from 2-8 (+1/+5) to 5-7 (+2/+3), but increased chill duration.\n\n" +
                    "_-_ Wand of Living Earth is nerfed: increased guardian's HP to 30 (+15), but decreased his DR, damage and evasion.\n\n" +
                    "_-_ Wand of Stench is buffed: added Slowness effect for a blob."));

    changes = new ChangeInfo("v1.1.3a", false, null);
    changes.hardlight( Window.TITLE_COLOR );
    changeInfos.add(changes);

    changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
            "_-_ Going to shop level will not softlock game.\n\n" +
                    "_-_ Buffed crabs and sewer minibosses.\n\n" +
                    "_-_ Bosses will instantly rise hero's level instead of giving expeirence."));

    changes = ChangesScene.createChangeInfo(changeInfos, "Dev", false, Window.TITLE_COLOR);
    changes.addButton(new ChangeButton(Icons.get(Icons.TRASHBOXBOBYLEV), "Developer Information",
            "_-_ Released March 22th, 2020\n" +
                    "_-_ 35 days after 1.1.2\n\n" +
                    "Global rework will come as soon as needed, enjoy this bugfix update."));
    changes = ChangesScene.createChangeInfo(changeInfos, "Changes", false, Window.SHPX_COLOR);
    changes.addButton( new ChangeButton(new Image(Assets.CONJURER, 0, 90, 12, 15), "Conjurer",
            "The goat boy recieved some buffs:\n\n" +
                    "_-_ Starting strength is raised from 9 to 10, and max HP scaling is increased from 3 to 4.\n\n" +
                    "_-_ Buffed knife's damage from 1-5 (+1/+1) to 1-9 (+1/+2), but removed strength bonus (nerf in late game).\n\n" +
                    "_-_ Buffed froggit's damage from 1-10 (+1/+2) to 4-10 (+2/+3), basically same damage with gray rat when leveled.\n\n" +
                    "_-_ Reworked Knight: now have to throw his knife to get soul from enemies, offensive ability is rebalanced, heal is buffed.\n\n" +
                    "_-_ Changed Soul Wielder: his empowerment lasts much longer."));
    changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
            "_-_ Buffed the Ring of Endurance\n\n" +
                    "_-_ Changed crabs to be slime-like enemies, should be less annoying for allies.\n\n" +
                    "_-_ Changed TNT mouse's retreat, less annoying now.\n\n" +
                    "_-_ Shop's equipment shouldn't feature curses\n\n" +
                    "_-_ Minions are now able to attack Yog.\n\n" +
                    "_-_ Piranhas and minions are neutral to each other."));
    changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
            "Fixed:\n" +
                    "_-_ Lack of hero message, when eradicated.\n\n" +
                    "_-_ Shops crashing the game or oftenly not loading after all\n\n" +
                    "_-_ TNT mouses being able to crash the game\n\n" +
                    "_-_ Lack of wandmaker reaction for conjurer\n\n" +
                    "_-_ Cursed Wand of Crystal Bullets crashing the game and having wrong type of damage\n\n" +
                    "_-_ Wand of Stars spending charge on detonating\n\n" +
                    "_-_ Slingshot's stone crashing the game\n\n" +
                    "_-_ Cave Spinner being able to damage with web\n\n" +
                    "_-_ Frost bomb crashing the game on non-regular floors"));

}

    public static void add_1_1_2_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = new ChangeInfo("1.1.2", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes = ChangesScene.createChangeInfo(changeInfos, "Dev", false, Window.TITLE_COLOR);
        changes.addButton(new ChangeButton(Icons.get(Icons.TRASHBOXBOBYLEV), "Developer Information",
                "_-_ Released February 16th, 2020\n" +
                        "_-_ 6 days after 1.1.1\n\n" +
                        "Sorry for such small updates, I will make more drastical changes with more feedback and better time management."));
        changes = ChangesScene.createChangeInfo(changeInfos, "Changes", false, Window.SHPX_COLOR);
        changes.addButton( new ChangeButton(new Image(Assets.CONJURER, 0, 90, 12, 15), "Conjurer",
                "_-_ AoE healing should work properly."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.CRYSTAL_WAND, null), "Changes to WoCB",
                "Wand of Crystal Bullet have been patched to be more usable:\n\n" +
                        "_-_ Shards can't go to empty tiles and to player.\n\n" +
                        "_-_ Shards can catch moving enemies now."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.STAR_WAND, null), "New Wand",
                "Added the new wand: Wand of Stars:\n\n" +
                        "This wand shoots star mines, that can be exploded by zapping into them."));
        changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
                "Fixed:\n" +
                        "_-_ Lack of fizzle string for staves"));
        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "_-_ Gnoll hunter can gain distance better.\n\n" +
                        "_-_ Increased amount of craftable Stones of Targeting from 3 to 5.\n\n" +
                        "_-_ 1 soul strength's zap heals 2 hp.\n\n" +
                        "_-_ Added funny quotes for each wand."));
    }

public static void add_1_1_1_Changes(ArrayList<ChangeInfo> changeInfos) {
    ChangeInfo changes = new ChangeInfo("1.1.1", true, "");
    changes.hardlight(Window.TITLE_COLOR);
    changeInfos.add(changes);
    changes = ChangesScene.createChangeInfo(changeInfos, "Dev", false, Window.TITLE_COLOR);
    changes.addButton(new ChangeButton(Icons.get(Icons.TRASHBOXBOBYLEV), "Developer Information",
            "_-_ Released February 10th, 2020\n" +
                    "_-_ 2 days after 1.1.0"));
    changes = ChangesScene.createChangeInfo(changeInfos, "Changes", false, Window.SHPX_COLOR);
    changes.addButton( new ChangeButton(new Image(Assets.CONJURER, 0, 90, 12, 15), "Conjurer",
            "Have obtained the new ability: Soul Sparkling, that increases damage of your allies, when are heavily wounded."));
    changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.CRYSTAL_WAND, null), "New Wand",
            "Added the new wand: Wand of Crystal Bullet:\n\n" +
                    "This wand shoots splitting crystals, that can deal damage for several trajectories."));
    changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
            "Fixed:\n" +
                    "_-_ Slingshot's stone crashing without slingshot\n" +
                    "_-_ Lacking string for cave spinner\n" +
                    "_-_ Crash for level 16\n" +
                    "_-_ Flashbang breaking the game, when pickuped after mouse's attack\n" +
                    "_-_ Spirit Bow being a not usable with sniper special and no augment"));
    changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
            "_-_ Replaced donation button with settings button.\n\n" +
                                    "_-_ Removed upgraded missiles from shops.\n\n" +
                                    "_-_ Adjusted attunement gain from level 10 to level 6.\n\n" +
                    "_-_ Edited sentry's summon prompt to be less confusing."));
}
    public static void add_1_1_Changes(ArrayList<ChangeInfo> changeInfos){
        ChangeInfo changes = new ChangeInfo( "1.1", true, "");
        changes.hardlight( Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes = ChangesScene.createChangeInfo(changeInfos, "Dev", false, Window.TITLE_COLOR);
        changes.addButton( new ChangeButton(Icons.get(Icons.TRASHBOXBOBYLEV), "Developer Information",
                "_-_ Released February 8th, 2020\n" +
                        "_-_ 99 days after 1.0.1a\n" +
                        "_-_ 101 days after 1.0.0"));
        changes = ChangesScene.createChangeInfo(changeInfos, "Changes", false, Window.SHPX_COLOR);
        changes.addButton( new ChangeButton(new Image(Assets.CONJURER, 0, 90, 12, 15), "Conjurer",
                "Have recieved a rework:\n\n" +
                        "_-_ Has a new melee weapon instead of using staff in melee: toy knife! It's usable to get all the soul and play with new subclass.\n\n" +
                        "_-_ Deltarune robe can't be upgraded, but gains levels from attunement.\n\n" +
                        "_-_ Now is one character with starting attunement.\n\n" +
                        "_-_ Froggit Staff have rebalanced to include decrease of start attunement.\n\n" +
                        "_-_ Have recieved redesigned mastery:\n\n\n" +
                        "   _-_ Soul Wielder: in exchange of melee damage his healing abilities can buff summons, and they can tweak minion's stats. Offensive action will form a controllable boosts for minions.\n\n" +
                        "   _-_ Knight: in exchange of healing ability recieves a soul blast spell, the damaging magic bolt that consumes soul energy, the healing spell, and can collect a soul on killing the enemies with toy knife."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.GREY_RAT_STAFF, null), "Summon weapon rework",
                "This type of weapons also have recieved a rework:\n\n" +
                        "_-_ Summon weapons can't be equipped now, melee damage is also removed.\n\n" +
                        "_-_ Alongside with tiers, staves have assigned to 5 different classes: defense, melee, magic, range and support. This stat affects an effect of new category of items on the minions.\n\n" +
                        "_-_ A bunch of balance changes, accounting for different minions.\n\n" +
                        "_-_ A new minion have been added: the slime, that stuns enemies.\n\n" +
                        "_-_ Gnoll hunter have been moved to tier 3.\n\n" +
                        "_-_ Mimic staff have been removed from game.\n\n" +
                        "_-_ Charge mechanic of staves should be more stable that before.\n\n" +
                        "_-_ Minions go invisible on drinking an invisibility potion."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARCANE_BOMB, null), "Bomb rework",
                "Enchanced bombs have recieved a rework, making them a powerful AoE consumables. Most of them doesn't deal damage anymore.\n\n" +
                        "_-_ Frost Bomb now freezes every mob in room for 15 turns.\n\n" +
                        "_-_ Firebomb produces long-lasting field of fire.\n\n" +
                        "_-_ Flashbang forces enemies to lose memories about their targets.\n\n" +
                        "_-_ Woolly Bomb have been replaced by Shrinking Bomb, that decreases size and stats of every enemy in room.\n\n" +
                        "_-_ Noisemaker doesn't explode in proximity of monster, instead it alerts monsters for 20 turns, and then explodes with knockback.\n\n" +
                        "_-_ Shock Bomb have been redesigned into Electrical Explosive, the charging bomb, that electrocutes creatures in 3x3 area. This bomb can break after long using..\n\n" +
                        "_-_ Holy bombs have been redesigned into Holy Grenades, that can be used as small-AoE ranged weapon with medium damage.\n\n" +
                        "_-_ Arcane Bomb have been reworked into Arcane Nuke, which explode in 11x11 with tremendous damage, destroys obstacles and leaves a harmful cloud of miasma."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WARRIOR_POWER, null), "Power ups!",
                "5 different powers have added as ultimate spells. At cost of transumation scroll and other goodies you can make a very powerful item, that affects your enemies, minions and character.\n\n" +
                        "Each power up have different set of effects, so check for alchemy guide!"
        ));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.STONE_HAMMER, null), "Shop weapons",
                "4 different weapons have been added into shops across all dungeon. They have unique properties and can be used for different synergies.\n\n" +
                        "That weapon can appear in shop with 50% chance."
                ));
        changes.addButton( new ChangeButton(new Image(Assets.TILES_CORE, 0, 64, 16, 16 ), "Floor 21 rework!",
        "Floor 21 recieved a big changes, making them more memorable and valuable.\n\n" +
                "_-_ It's now full depth, featuring many rooms, not just shop.\n\n" +
                "_-_ It always contain 3 scrolls of passage, allowing you to rest after exploring a demon halls.\n\n" +
                "_-_ The imp shopkeeper have replaced by new mysterious merchant, that serves same purpose."));
        changes.addButton( new ChangeButton(new Image(Assets.DOG, 0, 0, 15, 15), "New mob and changes",
                "_-_ Added a new monster into the sewers: sewer dog. He is fast, dexterious and can be a problem. It drops random items with 10% chance.\n\n" +
                        "_-_ Crabs have been reworked into very tough enemy with low speed and heavy punch. You should dispatch that guys from range.\n\n" +
                        "_-_ Added a new monster into Demon Halls: inferno bat! It attacks you with fire, forcing you staying in inferno cloud.\n\n" +
                        "_-_ All Demon Halls enemies have buffed, partially because of depth 21 rework.\n\n" +
                        "_-_ Added a new monster into caves: TNT mouse! It will blind and explode you, while trying to keep distance. It can be quite devastating enemy, if you don't have any range.\n\n" +
                        "_-_ Cave Spinner have been reworked into support enemy, which usually doesn't fight in melee, but spits up a web trap around you every few turns.\n\n" +
                        "_-_ Added a new monster into Metropolis: armored vessel. He is durable, but not very powerful guard, but it releases two-three attunement spirits, which are immensely powerful, but doesn't attack first."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SWORD, new ItemSprite.Glowing( 0x660022 )), "Vampiric rework",
                "Vampiric enchantment have been reworked to be more viable with low-damage weapons and more consistent.\n\n" +
                        "_-_ Now proc always.\n\n" +
                        "_-_ Gives a heal in form of Healing buff, which scales with weapon's level and damage roll."
        ));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_STENCH, null), "Wand of Stench rework",
                "Wand of Stench have been reworked to be more strategic and viable.\n\n" +
                        "_-_ Instead of infusing enemies, it creates a unique creature, that spreads the stench gas.\n\n" +
                        "_-_ This stench gas deals constant damage, that scales with wand's level.\n\n" +
                        "_-_ Stench creature have limited lifespan and explodes on collision with monsters or expiring."
        ));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCIMITAR, null), "Scimitar rework",
                "Scimitar have been reworked to move from 'sword but faster' to charging hits.\n\n" +
                        "_-_ Damage have been reduced to 3-15 (+0.5/3).\n\n" +
                        "_-_ Every 4th strike a damage will be increased to 6-30 (+1/+6).\n\n"
        ));

        changes.addButton( new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "New challenge",
                "Swarm Intelligence have been replaced by more powerful challenge.\n\n" +
                        "_-_ All NPCs will completely diappear.\n\n" +
                        "_-_ Shops will be not accessible.\n\n" +
                        "_-_ Each floor have one additional mob, but mobs don't respawn at all.\n\n" +
                        "_-_ You can't achieve happy end, and there will be unique ending.\n\n"));

        changes.addButton( new ChangeButton(Icons.get(Icons.WARNING), "Crash report system",
                "Added a simple crash handler, that allows to capture stacktraces.\n\n" +
                                        "This feature will be disabled for Google Play version."));

        changes.addButton( new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
                "_-_ Pickaxes are melee weapons now, that can be enchanted or upgraded.\n\n" +
                                        "_-_ Fixed bug, when Imp Queen's minions can crash a game.\n\n" +
                                        "_-_ Final Froggits's level seal were working incorrectly.\n\n" +
                                        "_-_ Fixed sneaky weapons.\n\n" +
                                        "_-_ Fixed dark slime's splits, less annoying now.\n\n" +
                                        "_-_ Fixed bugs with Perfume Brew.\n\n" +
                                        "_-_ Fixed clean water being not identified and having no readable drink option.\n\n" +
                                        "_-_ Changed sprites of some bombs.\n\n" +
                                        "_-_ Removed Russian and Chinese, because I can't support it properly.\n\n" +
                                        "_-_ Added a altar locked room, which contain cursed staff that can be upgraded.\n\n" +
                                        "_-_ Reworked sniper's shot from accelerating arrow to piercing javelin.\n\n" +
                                        "_-_ Changed descriptions of some wands to be more detailed."));
    }

public static void add_1_0_1_Changes(ArrayList<ChangeInfo> changeInfos ) {
    ChangeInfo changes = new ChangeInfo( "1.0.1", true, "");
    changes.hardlight( Window.TITLE_COLOR);
    changeInfos.add(changes);
    changes = ChangesScene.createChangeInfo(changeInfos, "Dev", false, Window.TITLE_COLOR);
    changes.addButton( new ChangeButton(Icons.get(Icons.TRASHBOXBOBYLEV), "Developer Information",
            "_-_ Released November 2nd, 2019\n" +
                    "_-_ 2 days after 1.0.0"));
    changes = ChangesScene.createChangeInfo(changeInfos, "Changes", false, Window.SHPX_COLOR);
    changes.addButton( new ChangeButton(new Gold(),
            "_-_ Adjusted gold drops from enemies and floor\n" +
                    "_-_ Increased cost of most items\n" +
                    "_-_ Gold Token is sellable now"));
    changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
               "_-_ Slingshot's stone didn't saved properly\n" +
                       "_-_ Blasted enemies were able to levitate\n" +
                       "_-_ New statistics number weren't saved properly\n" +
                       "_-_ Sneaky missiles didn't get consumed even if player can't surprise attack\n" +
                       "_-_ Badges about completing the game didn't accounted for Conjurer"));
    changes.addButton( new ChangeButton(new Image(Assets.GHOST, 0, 0, 14, 15), "Sad Ghost quest",
            "_-_ Now shows rewards names\n" +
                    "_-_ Now can give a staff\n"));
    changes.addButton( new ChangeButton(new Image(Assets.KEEPER, 0, 0, 14, 14), "Shops",
            "_-_ Adjusted shop's equipment choice to account hero's strength\n" +
                    "_-_ Guaranted wand and ring are always uncursed and upgraded; wand is also always have damage stat, so you can have weapon for Dark Matter Slimes\n" +
                    "_-_ You can find +10 artifact in imp shop"));
    changes.addButton( new ChangeButton(new CleanWater(),
            "Added a mineral water. It heals full HP, but is pretty expensive. Can be found only in shops. Very useful for Pharmacophobia challenge."));
    changes.addButton( new ChangeButton(new WandOfStench(),
            "Added a Wand of Stench. It's imbue the target's with toxic energy, allowing them to release damaging gas. Essentially it's more tricky, but more effective variant of Wand of Corrosion."));
    changes.addButton( new ChangeButton(new Image(Assets.CONJURER, 0, 90, 12, 15), "Conjurer",
            "_-_ New avatar sprite for conjurer\n" +
                    "_-_ Energy storm now takes 25% current HP and 12.5% max HP"));
    changes.addButton( new ChangeButton(Icons.get(Icons.LANGS), Messages.get(ChangesScene.class, "language"),
            "Improved the English translation"));
    changes = ChangesScene.createChangeInfo(changeInfos, "1.0.1a", false, CharSprite.DEFAULT);
    changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
            "Fixed infinity loop in Sad Ghost' quest, which prevented you from descending to new sewers depths"));
	}

    public static void add_General_Changes(ArrayList<ChangeInfo> changeInfos ){
	    ChangeInfo changes = ChangesScene.createChangeInfo(changeInfos, "Dev", false, Window.TITLE_COLOR);
        changes.addButton( new ChangeButton(Icons.get(Icons.TRASHBOXBOBYLEV), "Developer Information",
                "_-_ Released October 31nd, 2019\n" +
                        "_-_ 55 days after beginning of development\n" +
                        "_-_ 105 days after Shattered 0.7.4"));
        changes = ChangesScene.createChangeInfo(changeInfos, "General", false, Window.SHPX_COLOR);
        changes.addButton( new ChangeButton(HeroSprite.avatar(HeroClass.CONJURER, 6), "New Class!",
                "Asriel Dreemur joins the dungeon crawling!\n\n" +
                        "The Conjurer - new class, that are focused on new type of weapons - summon weapons. Thanks to his great soul power, here are able to control more allies and support them by his unique equipment.\n\n"+
                        "Unfortunately, Conjurer's body is composed from dust, so he have lowered physical stats compared to other classes.\n\n" +
                        "Conjurer have two subclasses:\n" +
                        "_-_ Soul Reaver gain 2x more soul and can tune minion stats. His offensive ability consumes soul for damaging enemies.\n" +
                        "_-_ Occultist collects HATE on killing enemies and spend them on next attack to corrupt the enemy. His offensive ability uses precise amount of HATE to corrupt enemies."));

        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.GREY_RAT_STAFF, null), "Summon Weapons!",
                "The new type of weapons have been added into dungeon.\n\n" +
                        "_-_ Summon weapons can be used to summon the unique allies, which type depends on type's staff.\n\n" +
                        "_-_ As all weapons, summon weapons are splitted into 5 tiers, with more unique and powerful minions, that can carry enchantment and level of weapon, as you progress.\n" +
                        "_-_ Summon weapons can summon unlimited smount of creatures, but you can control only few."));
        changes.addButton( new ChangeButton(new Image(Assets.BUFFS_LARGE, 112, 32, 16, 16), "Attunement",
                "_Attunement_ - the new hero stat which defines max amount of creatures, that you can control at once.\n\n"+
                        "_-_ Any minion require some amount of attunement (very likely, 1) to be summoned.\n\n"+
                        "_-_ More powerful minions require 2 or 3 attunement.\n\n"+
                        "_-_ Conjurer have 2 max attunement on start and increases his attunement by 1 every 6 levels.\n\n"+
                        "_-_ Other classes can increase their attunement with elixir of attunement."));

    }

    public static void add_Items_Changes(ArrayList<ChangeInfo> changeInfos){
        ChangeInfo changes = ChangesScene.createChangeInfo(changeInfos, "Items", false, 0x5ba5ff);
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RING_AGATE, null), "Ring changes",
                        "_-_ Ring of Tenacity was removed. Their effect are too unnoticable and hard to benefit from them.\n\n" +
                        "_-_ Ring of Elements and Ring of Tenacity were merged into Ring of Resistance, providing the static damage reduction from most sources.\n\n" +
                        "_-_ Added the Ring of Attunement, that increases max attunement and minion's damage."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SCROLL_BERKANAN, null), "Scroll changes",
                "_-_ Scroll of Terror was removed. It's was useful, but somewhat rare to be decent item.\n\n" +
                        "_-_ Added the Scroll of Attunement, that increases speed and damage reduction for all minions in sight, and inflict Weakness to enemies.\n\n" +
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
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ENRAGE, null), "New spells!",
                "_-_ Magical Porter was removed. It's almost useless.\n\n" +
                        "_-_ Added the Enrage spell, that amoks and enrages the target for some time.\n\n" +
                        "_-_ Added the Containing spell, that allows you to capture monsters and use them as temporary allies."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.BREW_PERFUME, null), "New brews!",
                "_-_ Added the Elixir of Attunement, that permamently increases user's attunement.\n\n" +
                        "_-_ Added the Elixir of Rage, that creates a adrenaline shortliving gas.\n\n" +
                        "_-_ Added the Vial of Perfume, that releases a charming and attracting cloud of perfume."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.CLEAVER, null), "New weapons!",
                "_-_ Added the cleaver, that have low damage and accuracy, but can overkill the enemy with small chance."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SLINGSHOT, null), "New missile weapon!",
                "_-_ Warrior now have the slingshot, that can shoot one stone at time, and upgrades with gaining strength."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RUNIC_BLADE, null), "Runic Blade rework",
                "Runic Blade were reworked from 't5, but t4' to totally new weapon!\n" +
                        "_-_ Damage were reduced from 4-20 (+1/+6) to 4-16 (+1/+4)\n" +
                        "_-_ Weapon now can shoot magical copies into enemies, that ignore armor and have very high accuracy\n" +
                        "_-_ After shooting, runic blade need to recharge for some time, that time equals 40 usings of weapon"));

    }

    public static void add_Mobs_Changes(ArrayList<ChangeInfo> changeInfos) {
        ChangeInfo changes = ChangesScene.createChangeInfo(changeInfos, "Mobs", false, 0xffc511);
        changes.addButton( new ChangeButton(new Image(Assets.SNAKE, 0, 0, 12, 12), "New monster in Caves",
                "The Caves now have new inhabitant: Rattle Snakes!\n\n"+
                        "_-_ They shoot the deadly darts on range, but are weak in melee.\n"+
                        "_-_ They have resistant to most of controllable magical attacks.\n" +
                        "_-_ Shakes have very high evasion, but low HP.\n"+
                        "_-_ They drop a darts for crossbows."));
        changes.addButton( new ChangeButton(new Image(Assets.NECRO, 0, 0, 16, 16), "Necromancer",
                "Added the necromancer from Shattered 0.7.5. Their stats are buffed compared to Shattered's.\n\n"+
                        "_-_ They have more HP now.\n\n"+
                        "_-_ They buff their skeleton both with Adrenaline and Empowered buffs.\n"+
                        "_-_ Drop: random staff with 12.5% chance."));
        changes.addButton( new ChangeButton(new Image(Assets.TENGU, 0, 0, 14, 16), "New Tengu",
                "Added Tengu fight from Shattered 0.7.5. Nothing are changed."));
        changes.addButton( new ChangeButton(new Image(Assets.SLIME, 0, 0, 14, 12), "New monster in Demon Halls",
                "The Demon Halls now have new inhabitant: Dark Matter Slimes!\n\n"+
                        "_-_ Their stats are pretty low for stage, but...\n"+
                        "_-_ Slimes can split! While splitting, dark matter slimes fully heal, but lose 25% of max HP.\n" +
                        "_-_ Damage wands are 2.5x more effective against slimes.\n"+
                        "_-_ They rarely drop a goo blobs."));
        changes.addButton( new ChangeButton(new Image(Assets.FINAL_FROGGIT, 0, 0, 16, 16), "Final Froggits",
                "Prepare for exit stairs defenders in Demon Halls - Final Froggits!\n\n"+
                        "_-_ They shoot a eradication bolts, that have small damage.\n"+
                        "_-_ But that bolts inflict Eradication debuff.\n" +
                        "_-_ Eradication debuff exponentially increases Final Froggit's damage, so do not stay for too long around them.\n"+
                        "_-_ They drop random items."));
    }

    public static void add_Minor_Changes(ArrayList<ChangeInfo> changeInfos){
        ChangeInfo changes = ChangesScene.createChangeInfo(changeInfos, "Other", false, 0x651f66);
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.GOLD, null), "Gold balance changes",
                "_-_ Gold appear a slighty rarer, but in a lot more quantity.\n\n"+
                                        "_-_ The shops have been extended to hold more valuable items, like upgraded items or exotic consumables.\n" +
                                        "_-_ If you do not have good equipment, shops will help by providing a better weapon and armor.\n"+
                                        "_-_ You can farm gold in Prison by killing the thieves.\n" +
                                        "_-_ You can farm gold in City by collecting the tokens."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.POTION_CRIMSON, null), "Healing item changes",
                "Potion of Healing and Elixir of Honeyed Healing now create the clouds of healing gases on shattering.\n\n" +
                        "When PoH cloud heal anything, EoHH are more concentrated and heal only allies."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.SWORD, new ItemSprite.Glowing()), "Unstable enchantment",
                "Now have rainbow shining as glowing effect."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.KUNAI, null), "Sneaky weapon changes",
                "Throwing sneaky weapons now doesn't consume a durability, while used with sneak attack"));
        changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "Shattered 0.7.5",
                                        "Added the new camera panning from new Shattered."));
        changes.addButton( new ChangeButton(new Image(Assets.BUFFS_LARGE, 64, 16, 16, 16), "Resistance",
                "If monsters are resisting the effect, effect's damage now square-rooted rather that halfing."));
        changes.addButton( new ChangeButton(Icons.get(Icons.RANKINGS), "Rankings",
                "Completely changed the way, how game places run in rankings scene.\n\nMore info is on ranking scene."));
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
