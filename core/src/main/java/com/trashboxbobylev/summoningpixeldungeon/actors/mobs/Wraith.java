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

package com.trashboxbobylev.summoningpixeldungeon.actors.mobs;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.Char;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Attunement;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Corruption;
import com.trashboxbobylev.summoningpixeldungeon.actors.buffs.Terror;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.minions.stationary.RoseWraith;
import com.trashboxbobylev.summoningpixeldungeon.effects.particles.ShadowParticle;
import com.trashboxbobylev.summoningpixeldungeon.items.weapon.enchantments.Grim;
import com.trashboxbobylev.summoningpixeldungeon.levels.Level;
import com.trashboxbobylev.summoningpixeldungeon.scenes.GameScene;
import com.trashboxbobylev.summoningpixeldungeon.sprites.WraithSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Wraith extends Mob {

	private static final float SPAWN_DELAY	= 2f;
	
	private int level;
    public RoseWraith parent = null;
	
	{
		spriteClass = WraithSprite.class;
		
		HP = HT = 1;
		EXP = 0;

		maxLvl = -2;
		
		flying = true;

		properties.add(Property.UNDEAD);
	}
	
	private static final String LEVEL = "level";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEVEL, level );
		bundle.put( "parent", parent);
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		level = bundle.getInt( LEVEL );
		parent = (RoseWraith) bundle.get("parent");
		if (parent == null) adjustStats( level );
		else adjustStatsWhenSummoned(parent);
	}
	
	@Override
	public int damageRoll() {
        int i = Random.NormalIntRange(1 + level / 2, 2 + level);
        if (parent != null) {
            i = Random.NormalIntRange(parent.minDamage, parent.maxDamage);
            if (Dungeon.hero.buff(Attunement.class) != null) i *= Attunement.empowering();
        }
        return i;
	}
	
	@Override
	public int attackSkill( Char target ) {
        int i = 10 + level;
        if (parent != null) i = parent.attackSkill(target);
        return i;
	}
	
	public void adjustStats( int level ) {
		this.level = level;
		defenseSkill = attackSkill( null ) * 5;
		enemySeen = true;
	}

    public void adjustStatsWhenSummoned( RoseWraith wraith ) {
        this.level = Dungeon.depth;
        defenseSkill = wraith.attackSkill( wraith.enemy )*5;
        enemySeen = true;
    }

	@Override
	public boolean reset() {
		state = WANDERING;
		return true;
	}
	
	public static void spawnAround( int pos ) {
		for (int n : PathFinder.NEIGHBOURS4) {
			int cell = pos + n;
			if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
				spawnAt( cell );
			}
		}
	}
	
	public static Wraith spawnAt( int pos ) {
		if (Dungeon.level.passable[pos] && Actor.findChar( pos ) == null) {
			
			Wraith w = new Wraith();
			w.adjustStats( Dungeon.depth );
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add( w, SPAWN_DELAY );
			
			w.sprite.alpha( 0 );
			w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );
			
			w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );
			
			return w;
		} else {
			return null;
		}
	}

    public static Wraith summonAt(RoseWraith wraith) {

        ArrayList<Integer> points = Level.getSpawningPoints(wraith.pos);

        if (points.size() > 0) {
            int position = points.get(Random.index(points));

                Wraith w = new Wraith();
                w.parent = wraith;
                w.adjustStatsWhenSummoned(wraith);
                w.pos = position;
                w.state = w.HUNTING;
                GameScene.add(w);
                Buff.affect(w, Corruption.class);
                w.sprite.alpha(0);
                w.sprite.parent.add(new AlphaTweener(w.sprite, 1, 0.5f));

                w.sprite.emitter().burst(ShadowParticle.CURSE, 5);

                Sample.INSTANCE.play( Assets.SND_CURSED );

                return w;
        } else {
            return null;
        }
    }
	
	{
		immunities.add( Grim.class );
		immunities.add( Terror.class );
	}
}
