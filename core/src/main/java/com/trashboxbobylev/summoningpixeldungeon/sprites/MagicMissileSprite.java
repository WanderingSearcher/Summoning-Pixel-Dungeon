/*
 *
 *  * Pixel Dungeon
 *  * Copyright (C) 2012-2015 Oleg Dolya
 *  *
 *  * Shattered Pixel Dungeon
 *  * Copyright (C) 2014-2019 Evan Debenham
 *  *
 *  * Summoning Pixel Dungeon
 *  * Copyright (C) 2019-2020 TrashboxBobylev
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.trashboxbobylev.summoningpixeldungeon.sprites;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.actors.Actor;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.minions.stationary.MagicMissileMinion;
import com.trashboxbobylev.summoningpixeldungeon.actors.mobs.minions.stationary.StationaryMinion;
import com.trashboxbobylev.summoningpixeldungeon.effects.Beam;
import com.trashboxbobylev.summoningpixeldungeon.effects.MagicMissile;
import com.trashboxbobylev.summoningpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Callback;

public class MagicMissileSprite extends MobSprite {

	public MagicMissileSprite(){
		super();

        texture(Assets.MAGIC_MISSILE);

        idle = new Animation( 1, true );
        idle.frames(texture.uvRect(0, 0, 15, 15));

        run = idle.clone();
        attack = idle.clone();
        die = idle.clone();
	}

	@Override
	public void zap( int pos ) {
		idle();
		flash();
		emitter().burst(MagicMissile.WhiteParticle.FACTORY, 5);
		if (Actor.findChar(pos) != null){
            MagicMissile.boltFromChar( parent,
                    MagicMissile.MAGIC_MISSILE,
                    this,
                    pos,
                    new Callback() {
                        @Override
                        public void call() {
                            ((MagicMissileMinion)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.SND_ZAP );
		}
	}
	
	@Override
	public void turnTo(int from, int to) {
		//do nothing
	}
	
	@Override
	public void die() {
		super.die();
		//cancels die animation and fades out immediately
		play(idle, true);
		emitter().burst(MagicMissile.WhiteParticle.FACTORY, 20);
		parent.add( new AlphaTweener( this, 0, 2f ) {
			@Override
			protected void onComplete() {
				MagicMissileSprite.this.killAndErase();
				parent.erase( this );
			}
		} );
	}

}
