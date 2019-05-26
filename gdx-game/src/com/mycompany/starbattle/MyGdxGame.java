package com.mycompany.starbattle;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.util.*;
public class MyGdxGame extends Game
{
	public static final boolean debug=true;
	public static final boolean debugLine=false;
	public static MyGdxGame game;
	public SpriteBatch b;
	public BitmapFont bf;
	public AssetManager asset;
	public ShapeRenderer sr;
	public Skin skin;
	public Random r=new Random();
	public void create()
	{
		if(debug)LogCat.begin();
		System.out.println("puzzi");
		Gdx.input.setCatchBackKey(true);
		game=this;
		b=new SpriteBatch();
		bf=new BitmapFont();
		sr=new ShapeRenderer();
		skin=new Skin();
		Pixmap pixmap=new Pixmap(1,1,Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white",new Texture(pixmap));
		asset=new AssetManager();
		new Titolo();
	}
	public void setScreen(Screen screen)
	{
		if(getScreen()!=null)((Stage)getScreen()).dispose();
		super.setScreen(screen);
	}
	public void render()
	{
		super.render();
		if(asset.getProgress()!=1f)asset.update();
		if(debug)LogCat.log();
	}
	public void dispose()
	{
		setScreen(null);
		b.dispose();
		bf.dispose();
		sr.dispose();
		asset.dispose();
		skin.dispose();
		game=null;
		super.dispose();
	}
}
