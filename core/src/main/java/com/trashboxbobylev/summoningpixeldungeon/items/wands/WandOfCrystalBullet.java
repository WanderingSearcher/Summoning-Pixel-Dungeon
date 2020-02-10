/*
 * Pixel Dungeon
 *   * Copyright (C) 2012-2015 Oleg Dolya
 *   *
 *   * Shattered Pixel Dungeon
 *   * Copyright (C) 2014-2019 Evan Debenham
 *   *
 *   * Summoning Pixel Dungeon
 *   * Copyright (C) 2019-2020 TrashboxBobylev
 *   *
 *   * This program is free software: you can redistribute it and/or modify
 *   * it under the terms of the GNU General Public License as published by
 *   * the Free Software Foundation, either version 3 of the License, or
 *   * (at your option) any later version.
 *   *
 *   * This program is distributed in the hope that it will be useful,
 *   * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   * GNU General Public License for more details.
 *   *
 *   * You should have received a copy of the GNU General Public License
 *   * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.trashboxbobylev.summoningpixeldungeon.items.wands;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Challenges;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Recharging;
import com.trashboxbobylev.summoningpixeldungeon.effects.MagicMissile;
import com.trashboxbobylev.summoningpixeldungeon.effects.SpellSprite;
import com.trashboxbobylev.summoningpixeldungeon.items.Item;
import com.trashboxbobylev.summoningpixeldungeon.items.weapon.melee.MagesStaff;
import com.trashboxbobylev.summoningpixeldungeon.mechanics.Ballistica;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.sprites.ItemSpriteSheet;
import com.trashboxbobylev.summoningpixeldungeon.sprites.MissileSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class WandOfCrystalBullet extends DamageWand {

	{
		image = ItemSpriteSheet.CRYSTAL_WAND;

        collisionProperties = Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN;
	}

	public int min(int lvl){
		return 4+lvl;
	}

	public int max(int lvl){
		return 6+3*lvl;
	}

	public int shards(int lvl){
	    return Math.min(3 + lvl/2,9);
    }
	
	@Override
	protected void onZap( Ballistica bolt ) {
        Dungeon.level.pressCell(bolt.collisionPos);
        int cell = bolt.collisionPos;
        ArrayList<Integer> shardPositions = new ArrayList<>();

        for (int i: PathFinder.NEIGHBOURS8){
            if (shards(level()) != 9){
                if (!shardPositions.contains(cell + i) && Dungeon.level.passable[cell +i]){
                    if (Actor.findChar(cell + i) != null) shardPositions.add(cell + i);
                    else if (Random.Float() < 0.5f) shardPositions.add(cell + i);
                    if (shardPositions.size() >= shards(level())) break;
                }
            } else {
                shardPositions.add(cell + i);
            }
        }

        for (int k : shardPositions){
            final int target = new Ballistica(cell, k, Ballistica.MAGIC_BOLT ).collisionPos;
            MagicMissile.boltFromChar(curUser.sprite.parent,
                    MagicMissile.CRYSTAL,
                    curUser.sprite,
                    bolt.collisionPos,
                    new Callback() {
                        @Override
                        public void call() {
                            Char ch = Actor.findChar( target );
                            if (ch != null) {

                                processSoulMark(ch, chargesPerCast());
                                ch.damage(damageRoll(), this);

                                ch.sprite.burst(Random.Int(0xFFe380e3, 0xFF9485c9), level() + 3);

                            } else {
                                Dungeon.level.pressCell(target);
                            }
                        }
                    });
            Sample.INSTANCE.play( Assets.SND_SHATTER );
        }
	}

    @Override
    protected void fx(Ballistica bolt, Callback callback) {
//        MagicMissile.boltFromChar( curUser.sprite.parent,
//                MagicMissile.SHADOW,
//                curUser.sprite,
//                bolt.collisionPos,
//                callback);
        ((MissileSprite)curUser.sprite.parent.recycle( MissileSprite.class )).
                reset( curUser.pos, bolt.collisionPos, new Crystal(), callback );
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }

	@Override
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
		for (int c : PathFinder.NEIGHBOURS8){
            Char ch = Actor.findChar(defender.pos + c);
            if (ch != null){
                ch.damage((int) (damage * Random.Float(0.2f, 0.5f)), attacker);
                ch.sprite.burst(Random.Int(0xFFe380e3, 0xFF9485c9), level() + 3);
            }
        }
	}

    @Override
    public String statsDesc() {
        if (!levelKnown)
            return Messages.get(this, "stats_desc", min(0), max(0), 3);
        else
            return Messages.get(this, "stats_desc", min(), max(),  3 + level() / 2);
    }

	public static class Crystal extends Item {
        {
            image = ItemSpriteSheet.CRYSTAL;
        }
    }

}