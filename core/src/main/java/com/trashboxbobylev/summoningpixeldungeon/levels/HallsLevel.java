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

package com.trashboxbobylev.summoningpixeldungeon.levels;

import com.trashboxbobylev.summoningpixeldungeon.Assets;
import com.trashboxbobylev.summoningpixeldungeon.Dungeon;
import com.trashboxbobylev.summoningpixeldungeon.items.Torch;
import com.trashboxbobylev.summoningpixeldungeon.levels.painters.HallsPainter;
import com.trashboxbobylev.summoningpixeldungeon.levels.painters.Painter;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.BlazingTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.CorrosionTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.CursingTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.DisarmingTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.DisintegrationTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.DistortionTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.ExplosiveTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.FlashingTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.FrostTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.GrimTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.GuardianTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.PitfallTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.RockfallTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.StormTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.SummoningTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.WarpingTrap;
import com.trashboxbobylev.summoningpixeldungeon.levels.traps.WeakeningTrap;
import com.trashboxbobylev.summoningpixeldungeon.messages.Messages;
import com.trashboxbobylev.summoningpixeldungeon.tiles.DungeonTilemap;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class HallsLevel extends RegularLevel {

	{
		
		viewDistance = Math.min( 26 - Dungeon.depth, viewDistance );
		
		color1 = 0x801500;
		color2 = 0xa68521;
	}
	
	@Override
	protected int standardRooms() {
		//8 to 10, average 8.67
		return 8+Random.chances(new float[]{3, 2, 1});
	}
	
	@Override
	protected int specialRooms() {
		//2 to 3, average 2.5
		return 2 + Random.chances(new float[]{1, 1});
	}
	
	@Override
	protected Painter painter() {
		return new HallsPainter()
				.setWater(feeling == Feeling.WATER ? 0.70f : 0.15f, 6)
				.setGrass(feeling == Feeling.GRASS ? 0.65f : 0.10f, 3)
				.setTraps(nTraps(), trapClasses(), trapChances());
	}
	
	@Override
	public void create() {
		addItemToSpawn( new Torch() );
		super.create();
	}
	
	@Override
	public String tilesTex() {
		return Assets.TILES_HALLS;
	}
	
	@Override
	public String waterTex() {
		return Assets.WATER_HALLS;
	}
	
	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{ FrostTrap.class, StormTrap.class, CorrosionTrap.class, BlazingTrap.class, DisintegrationTrap.class,
				ExplosiveTrap.class, RockfallTrap.class, FlashingTrap.class, GuardianTrap.class, WeakeningTrap.class,
				SummoningTrap.class, WarpingTrap.class, CursingTrap.class, GrimTrap.class,
				PitfallTrap.class, DisarmingTrap.class, DistortionTrap.class };
	}

	@Override
	protected float[] trapChances() {
		return new float[]{ 8, 8, 8, 8, 8,
				4, 4, 4, 4, 4,
				2, 2, 2, 2,
				1, 1, 1 };
	}
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_name");
			case Terrain.GRASS:
				return Messages.get(HallsLevel.class, "grass_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(HallsLevel.class, "high_grass_name");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_desc");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(HallsLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}
	
	@Override
	public Group addVisuals() {
		super.addVisuals();
		addHallsVisuals( this, visuals );
		return visuals;
	}
	
	public static void addHallsVisuals( Level level, Group group ) {
		for (int i=0; i < level.length(); i++) {
			if (level.map[i] == Terrain.WATER) {
				group.add( new Stream( i ) );
			}
		}
	}
	
	private static class Stream extends Group {
		
		private int pos;
		
		private float delay;
		
		public Stream( int pos ) {
			super();
			
			this.pos = pos;
			
			delay = Random.Float( 2 );
		}
		
		@Override
		public void update() {
			
			if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
				
				super.update();
				
				if ((delay -= Game.elapsed) <= 0) {
					
					delay = Random.Float( 2 );
					
					PointF p = DungeonTilemap.tileToWorld( pos );
					((FireParticle)recycle( FireParticle.class )).reset(
						p.x + Random.Float( DungeonTilemap.SIZE ),
						p.y + Random.Float( DungeonTilemap.SIZE ) );
				}
			}
		}
		
		@Override
		public void draw() {
			Blending.setLightMode();
			super.draw();
			Blending.setNormalMode();
		}
	}
	
	public static class FireParticle extends PixelParticle.Shrinking {
		
		public FireParticle() {
			super();
			
			color( 0xEE7722 );
			lifespan = 1f;
			
			acc.set( 0, +80 );
		}
		
		public void reset( float x, float y ) {
			revive();
			
			this.x = x;
			this.y = y;
			
			left = lifespan;
			
			speed.set( 0, -40 );
			size = 4;
		}
		
		@Override
		public void update() {
			super.update();
			float p = left / lifespan;
			am = p > 0.8f ? (1 - p) * 5 : 1;
		}
	}
}
