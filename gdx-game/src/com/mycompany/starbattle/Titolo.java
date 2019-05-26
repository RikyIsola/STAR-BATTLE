package com.mycompany.starbattle;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.*;
public class Titolo extends Stage implements Screen
{
	private boolean caricando=true;
	public static Nave[]risorse={Nave.enterprise,Nave.enterprise2,Nave.enterprise4,Nave.klingon,Nave.romulani,Nave.borg};
	private Sprite immagine;
	private int index;
	private ProgressBar[]barre=new ProgressBar[Nave.nomi.length];
	public Titolo()
	{
		super(new StretchViewport(300,300),MyGdxGame.game.b);
		if(!MyGdxGame.game.asset.isLoaded(risorse[0].img))
		{
			for(Nave s:risorse)
			{
				MyGdxGame.game.asset.load(s.img,Texture.class);
				MyGdxGame.game.asset.load(s.pImg,Texture.class);
			}
			MyGdxGame.game.asset.load("esplosione.png",Texture.class);
			MyGdxGame.game.asset.load("scudi.png",Texture.class);
			MyGdxGame.game.asset.load("sfondo.png",Texture.class);
			MyGdxGame.game.asset.load("scafocolpito.ogg",Sound.class);
			MyGdxGame.game.asset.load("scudicolpiti.ogg",Sound.class);
			MyGdxGame.game.asset.load("siluro.ogg",Sound.class);
		}
		
		Label label=new Label("STAR BATTLE",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(0,200,300,100);
		label.setAlignment(Align.center);
		label.setFontScale(3,5);
		
		label=new Label(">",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(200,100,100,100);
		label.setAlignment(Align.center);
		label.addListener(input(label));
		label.setFontScale(5);
		
		label=new Label("<",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(0,100,100,100);
		label.setAlignment(Align.center);
		label.addListener(input(label));
		label.setFontScale(5);
		
		label=new Label("CHOOSE YOUR SHIP WITH ARROWS AND CLICK THE IMAGE TO START THE GAME.\nCOMMANDS:\nTOUCH YOUR SHIP TO FIRE.\nTOUCH THE SCREEN TO MOVE YOUR SHIP.",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setWrap(true);
		label.setFontScale(0.7f);
		label.setColor(Color.BLUE);
		label.setBounds(150,0,150,100);
		label.setAlignment(Align.center);
		
		float spazio=100/(Nave.nomi.length+1);
		label=new Label("STATS:",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
		addActor(label);
		label.setColor(Color.BLUE);
		label.setBounds(0,100-spazio,150,spazio);
		label.setAlignment(Align.top);
		label.setFontScale(0.5f);
		for(int a=0;a<Nave.nomi.length;a++)
		{
			label=new Label(Nave.nomi[a]+":",new Label.LabelStyle(MyGdxGame.game.bf,Color.WHITE));
			addActor(label);
			label.setColor(Color.BLUE);
			label.setBounds(0,100-spazio*(a+2),75,spazio);
			label.setAlignment(Align.left);
			label.setFontScale(0.5f);
			
			ProgressBar bar=new ProgressBar(0,Nave.max[a],0.001f,false,new ProgressBar.ProgressBarStyle(MyGdxGame.game.skin.newDrawable("white",Color.RED),null));
			bar.setBounds(75,100-spazio*(a+2)+spazio/4,75,spazio/2);
			bar.getStyle().background.setMinHeight(bar.getHeight());
			bar.getStyle().knobBefore=MyGdxGame.game.skin.newDrawable("white",Color.GREEN);
			bar.getStyle().knobBefore.setMinHeight(bar.getHeight());
			bar.setAnimateDuration(0.5f);
			bar.setValue(risorse[index].valore(a));
			addActor(bar);
			barre[a]=bar;
		}
		
		if(MyGdxGame.debugLine)setDebugAll(true);
		MyGdxGame.game.setScreen(this);
	}
	public void render(float delta)
	{
		if(caricando&&MyGdxGame.game.asset.isLoaded(risorse[index].img))
		{
			caricando=false;
			Image image=new Image(new SpriteDrawable(immagine=new Sprite(MyGdxGame.game.asset.get(risorse[index].img,Texture.class))));
			addActor(image);
			image.setBounds(100,100,100,100);
			image.addListener(input(image));
		}
		act(delta);
		draw();
	}
	public void resize(int w,int h)
	{
		getViewport().update(w,h);
	}
	public void show()
	{
		Gdx.input.setInputProcessor(this);
	}
	public void hide()
	{
	}
	public void pause()
	{
	}
	public void resume()
	{
	}
	public void dispose()
	{
	}
	private InputListener input(final Actor actor)
	{
		return new InputListener()
		{
			public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
			{
				if(actor instanceof Label)
				{
					Label label=(Label)actor;
					if(label.textEquals(">"))
					{
						index++;
						if(index>=risorse.length)index=0;
						while(!MyGdxGame.game.asset.isLoaded(risorse[index].img));
						immagine.setTexture(MyGdxGame.game.asset.get(risorse[index].img,Texture.class));
						for(int a=0;a<barre.length;a++)barre[a].setValue(risorse[index].valore(a));
					}
					else
					{
						index--;
						if(index<0)index=risorse.length-1;
						while(!MyGdxGame.game.asset.isLoaded(risorse[index].img));
						immagine.setTexture(MyGdxGame.game.asset.get(risorse[index].img,Texture.class));
						for(int a=0;a<barre.length;a++)barre[a].setValue(risorse[index].valore(a));
					}
				}
				else new Gioco(risorse[index]);
				return true;
			}
		};
	}
	public boolean keyDown(int keyCode)
	{
		if(keyCode==Input.Keys.BACK)Gdx.app.exit();
		return true;
	}
}
